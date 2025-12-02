package util;

import exception.IllegalOperationException;
import lombok.extern.log4j.Log4j2;
import table.card.PokerCard;
import table.mechanism.decision.DecisionRequest;
import table.mechanism.decision.DecisionType;
import table.mechanism.decision.ResolvedAction;
import table.player.CardPlayer;
import table.player.PlayerIterator;
import table.player.PlayerList;
import table.pot.PlayerRanking;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
import table.vo.publicinfo.PlayerPublicVO;
import table.vo.publicinfo.PotPublicVO;
import table.vo.publicinfo.PublicVO;
import table.vo.publicinfo.TablePublicVO;
import table.vo.publicinfo.builder.PlayerPublicVOBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Player util class.
 *
 * <p>
 *     This class is for proceeding various actions about a player.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
@Log4j2
public class PlayerUtil {

    /**
     * Charge the amount of stacks from a player as much as given amount, and count in the player's investment.
     * If the stacks the player have cannot cover the request, charge all the rest.
     * @param player The player who needs to be charged.
     * @param amount The charging amount.
     * @return The actual charging amount.
     */
    public static BigDecimal collectChipsAsMuch(CardPlayer player, BigDecimal amount) {
        if (player.getStack().compareTo(amount) < 0) {
            BigDecimal stack = player.getStack();
            player.setStack(BigDecimal.ZERO);
            player.addPlayerInvestment(stack);
            return stack;
        } else {
            player.setStack(player.getStack().subtract(amount));
            player.addPlayerInvestment(amount);
            return amount;
        }
    }

    /**
     * Charge the amount of stacks from a player as exact, and count in the player's investment.
     * If player's stacks cannot cover the requirement, throw an exception.
     * @param player The player to be charged.
     * @param amount The charging amount.
     * @throws IllegalOperationException When the player cannot cover the requirement.
     */
    public static void collectChipsExactly(CardPlayer player, BigDecimal amount) throws IllegalOperationException {
        if (player.getStack().compareTo(amount) < 0) {
            throw new IllegalOperationException("Player's stack cannot cover the requirement. Player's stack: " +
                    player.getStack() + ", charging " + amount);
        } else {
            player.setStack(player.getStack().subtract(amount));
            player.addPlayerInvestment(amount);
        }
    }

    /**
     * Build VO by context and publish VO to players.
     * <b>DO finish the necessary settings of the builders of the context before calling this.</b>
     * @param context The GameStateContext of the game.
     */
    public static void buildAndPublishVO(GameStateContext context) {
        PlayerPublicVOBuilder playerPublicVOBuilder = context.getPlayerPublicVOBuilder();
        TablePublicVO.Builder tablePublicVOBuilder = context.getTablePublicVOBuilder();

        PlayerPublicVO[] playerPublicVOs = PlayerUtil.getPlayerPublicVOS(context, playerPublicVOBuilder);

        PlayerUtil.integrateAndPublishPublicInfo(context, tablePublicVOBuilder, playerPublicVOs);
    }
    
    private static PlayerPublicVO[] getPlayerPublicVOS(GameStateContext context,
                                                      PlayerPublicVOBuilder playerPublicVOBuilder) {
        PlayerPublicVO[] playerPublicVOs = new PlayerPublicVO[context.getPlayers().getPlayerNum()];
        PlayerIterator iterator = context.getPlayers().getIterator();
        int index = 0;
        while (iterator.hasNext()) {
            playerPublicVOs[index++] = playerPublicVOBuilder.build(iterator.next());
        }
        return playerPublicVOs;
    }
    
    private static void integrateAndPublishPublicInfo(GameStateContext context,
                                                      TablePublicVO.Builder tablePublicVOBuilder,
                                                      PlayerPublicVO[] playerPublicVOs) {
        PlayerIterator iterator;
        PublicVO publicVO = new PublicVO(
                tablePublicVOBuilder.build(),
                playerPublicVOs,
                new PotPublicVO(context.getPotManager().getTotalInvestment()));
        iterator = context.getPlayers().getIterator();
        while (iterator.hasNext()) {
            iterator.next().updatePublicInfo(publicVO);
        }
        log.trace(publicVO.toString());
    }

    public static GameState startBet(GameStateContext context, GameState endState) {
        PlayerList players = context.getPlayers();
        int pointer = players.getButtonPosition() + 1;
        return startBet(pointer, context, endState);
    }

    public static GameState startBet(int startPos, GameStateContext context, GameState endState) {
        PlayerList players = context.getPlayers();
        PlayerIterator iterator = players.getIterator(startPos);
        while (iterator.hasNext()) {
            CardPlayer next = iterator.next();
            if (!next.getIsContinuingGame() || next.getIsAllIn()) {
                continue;
            }
            if (context.getActivePlayerNum() <= 1) {
                break;
            }

            context.getTablePublicVOBuilder().setCurrentDecisionMakerId(next.getID());
            context.getTablePublicVOBuilder().setMadeDecision(null);
            PlayerUtil.buildAndPublishVO(context);

            log.info("Requiring CardPlayer {} to make a decision", next.toSimpleLogString());
            ResolvedAction playerDecision = getResolvedAction(context, next, context.getBetBasisLine());
            iterator = processResolvedAction(context, playerDecision, iterator, players, next);

            if (Objects.requireNonNull(playerDecision.decisionType()) == DecisionType.RAISE) {
                context.setBetBasisLine(next.getPlayerInvestment());
            }

            context.getTablePublicVOBuilder().setMadeDecision(playerDecision);
            PlayerUtil.buildAndPublishVO(context);
        }
        if (context.getActivePlayerNum() <= 1) {
            log.info("End game as FOLD out");
            return GameState.END;
        } else if (context.getDecidingPlayerNum() <= 1) {
            log.info("End game as player ALLIN");
            return GameState.SHOWDOWN;
        }
        return endState;
    }

    private static PlayerIterator processResolvedAction(GameStateContext context, ResolvedAction playerDecision,
                                                        PlayerIterator iterator, PlayerList players, CardPlayer next) {
        switch (playerDecision.decisionType()) {
            case RAISE -> {
                iterator = players.getIterator(iterator.getCurrentPosition());
                iterator.next();

                PlayerUtil.collectChipsExactly(next, playerDecision.value());
                if (next.getIsAllIn()) {
                    context.setDecidingPlayerNum(context.getDecidingPlayerNum() - 1);
                }
            }
            case FOLD -> {
                context.setActivePlayerNum(context.getActivePlayerNum() - 1);
                context.setDecidingPlayerNum(context.getDecidingPlayerNum() - 1);
                next.setIsContinuingGame(false);
            }
            case CALL -> {
                PlayerUtil.collectChipsExactly(next, playerDecision.value());
                if (next.getIsAllIn()) {
                    context.setDecidingPlayerNum(context.getDecidingPlayerNum() - 1);
                }
            }
            default -> throw new IllegalOperationException("Unexpected decision type: " +
                    playerDecision.decisionType());
        }
        return iterator;
    }

    private static ResolvedAction getResolvedAction(GameStateContext context, CardPlayer next, BigDecimal currentBet) {
        DecisionRequest request = new DecisionRequest(
                currentBet.subtract(next.getPlayerInvestment()),
                context.getTableConfig().halfMinimumBet().multiply(new BigDecimal(2)));
        log.trace(request);
        ResolvedAction playerDecision = next.getPlayerDecision(request);


        context.getPotManager().action(next, playerDecision);
        return playerDecision;
    }

    public static void flopAllCards(GameStateContext context) {
        PokerCard[] cards = context.getPublicCards();
        for (int i = 0; i < 5; i++) {
            if (cards[i] == null) {
                cards[i] = context.getCardDeck().takePeekCard();
                context.getCardDeck().shuffle();
            }
        }
        context.setPublicCards(cards);
    }

    public static PriorityQueue<PlayerRanking> getPlayerRankings(GameStateContext context) {
        PriorityQueue<PlayerRanking> playerRankings;
        playerRankings = new PriorityQueue<>();
        PlayerList players = context.getPlayers();
        PlayerIterator iterator = players.getIterator();
        while (iterator.hasNext()) {
            CardPlayer cardPlayer = iterator.next();
            PlayerRanking playerRanking = new PlayerRanking(cardPlayer, List.of(context.getPublicCards()));
            playerRankings.add(playerRanking);
        }
        return playerRankings;
    }
}
