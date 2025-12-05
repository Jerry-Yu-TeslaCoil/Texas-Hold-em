package table.player.impl;

import control.player.GamePlayer;
import exception.IllegalOperationException;
import lombok.extern.log4j.Log4j2;
import table.card.PokerCard;
import table.rule.decision.DecisionRequest;
import table.rule.decision.DecisionType;
import table.rule.decision.PlayerDecision;
import table.rule.decision.ResolvedAction;
import table.rule.decision.impl.RaiseDecision;
import table.player.CardPlayer;
import table.record.entry.impl.PlayerPrivateInfoVOEntry;
import table.record.entry.impl.PublicInfoVOEntry;
import table.record.reader.RecordReader;
import table.record.recorder.GameRecorder;
import table.record.recorder.impl.SimpleGameRecorder;
import table.vo.privateinfo.PlayerPrivateVO;
import table.vo.publicinfo.PublicVO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple CardPlayer Implementation.
 *
 * <p>
 *     This is used for the first impl version, and no other possible needed version yet.
 *     SimpleCardPlayer accepts a controller for player action control,
 *     and a BigDecimal for storing stack.
 *     Call getPlayerDecision() will only pass request directly to the player.
 * </p>
 *
 * <p>
 *     THis class is for only data storing, validating and decision requesting.
 *     The validation is wide-accepting.
 *     That means even the player gives an invalid decision, it will be fixed with no exception thrown.
 *     For example, "stacks overflow" will be seen as ALLIN.
 * </p>
 *
 * <p>
 *     This is not thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
@Log4j2
public class SimpleCardPlayer implements CardPlayer {

    private final int id;
    private GamePlayer gamePlayer;

    private BigDecimal stack;
    private final List<PokerCard> pokerCards;

    private boolean isContinuingGame;

    private BigDecimal playerInvestment;
    private BigDecimal prize;

    private boolean isJoiningPot;

    private final GameRecorder gameRecorder;

    /**
     * Construct a simple card player with appointed controlling player and BigDecimal of stack.
     * @param gamePlayer Controlling player could be a real player terminal or a robot.
     * @param stack The stack the player currently have.
     */
    public SimpleCardPlayer(GamePlayer gamePlayer, BigDecimal stack, int id) {
        this.gamePlayer = gamePlayer;
        this.pokerCards = new ArrayList<>();
        this.stack = stack;
        this.isContinuingGame = true;
        this.id = id;
        this.playerInvestment = BigDecimal.ZERO;
        this.gameRecorder = new SimpleGameRecorder(gamePlayer.getPlayerIdentifier());
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    @Override
    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    @Override
    public void setStack(BigDecimal stack) {
        this.stack = stack;
    }

    @Override
    public BigDecimal getStack() {
        return this.stack;
    }

    @Override
    public void addHoleCard(PokerCard card) {
        if (this.pokerCards.size() >= 2) {
            throw new IllegalOperationException("Card stack overflow");
        }
        this.pokerCards.add(card);
    }

    @Override
    public List<PokerCard> getHoleCards() {
        return this.pokerCards;
    }

    @Override
    public void clearHoleCard() {
        this.pokerCards.clear();
    }

    @Override
    public boolean getIsContinuingGame() {
        return this.isContinuingGame;
    }

    @Override
    public void setIsContinuingGame(boolean isContinuingGame) {
        this.isContinuingGame = isContinuingGame;
    }

    @Override
    public boolean getIsAllIn() {
        return this.stack.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public BigDecimal getPlayerInvestment() {
        return this.playerInvestment;
    }

    @Override
    public void setPlayerInvestment(BigDecimal playerInvestment) {
        this.playerInvestment = playerInvestment;
    }

    @Override
    public void addPlayerInvestment(BigDecimal playerInvestment) {
        this.playerInvestment = this.playerInvestment.add(playerInvestment);
    }

    @Override
    public BigDecimal getPlayerPrize() {
        return this.prize;
    }

    @Override
    public void setPlayerPrize(BigDecimal playerPrize) {
        this.prize = playerPrize;
    }

    @Override
    public ResolvedAction getPlayerDecision(DecisionRequest decisionRequest) {
        PlayerDecision playerDecision = gamePlayer.getPlayerController().getPlayerDecision(decisionRequest);
        validateDecision(decisionRequest, playerDecision);
        return resolveDecision(decisionRequest, playerDecision);
    }

    @Override
    public void updatePublicInfo(PublicVO publicInfo) {
        this.gameRecorder.record(new PublicInfoVOEntry(Instant.now(), publicInfo));
        this.gamePlayer.getPlayerController().updatePublicInfo(publicInfo);
    }

    @Override
    public void updatePrivateInfo(PlayerPrivateVO privateInfo) {
        this.gameRecorder.record(new PlayerPrivateInfoVOEntry(Instant.now(), privateInfo));
        this.gamePlayer.getPlayerController().updatePrivateInfo(privateInfo);
    }

    private ResolvedAction resolveDecision(DecisionRequest decisionRequest, PlayerDecision playerDecision) {
        BigDecimal decisionStacks = BigDecimal.ZERO;
        DecisionType decisionType = DecisionType.CALL;
        switch (playerDecision.getDecisionType()) {
            case RAISE -> {
                RaiseDecision decision = (RaiseDecision) playerDecision;
                decisionStacks = new BigDecimal(String.valueOf(decision.bet()));
                decisionStacks = decisionStacks.add(decisionRequest.callingStacks()).min(this.stack);
                if (decisionStacks.compareTo(decisionRequest.callingStacks()) > 0) {
                    decisionType = DecisionType.RAISE;
                }
            }
            case CALL -> decisionStacks = new BigDecimal(String.valueOf(decisionRequest.callingStacks())).min(this.stack);
            case FOLD -> decisionType = DecisionType.FOLD;
            default -> throw new IllegalOperationException("Unknown decision type: " +
                    playerDecision.getDecisionType());
        }
        return new ResolvedAction(decisionType, decisionStacks);
    }

    private void validateDecision(DecisionRequest decisionRequest, PlayerDecision playerDecision) {
        BigDecimal decisionStacks;
        switch (playerDecision.getDecisionType()) {
            case RAISE -> {
                RaiseDecision decision = (RaiseDecision) playerDecision;
                decisionStacks = new BigDecimal(String.valueOf(decision.bet()));
                decisionStacks = decisionStacks.add(decisionRequest.callingStacks());
            }
            case CALL -> decisionStacks = new BigDecimal(String.valueOf(decisionRequest.callingStacks()))
                    .min(this.stack);
            case FOLD -> decisionStacks = BigDecimal.ZERO.subtract(BigDecimal.ONE);
            default -> throw new IllegalOperationException("Unknown decision type: " +
                    playerDecision.getDecisionType());
        }
        if (decisionStacks.compareTo(this.stack) > 0) {
            log.info("Player cannot cover as much as he decided to bet, " +
                    "decision stacks: {}, the player has: {}, will change to ALLIN.", decisionStacks, this.stack);
        }
    }

    @Override
    public void clearState() {
        this.pokerCards.clear();
        this.isContinuingGame = true;
        this.playerInvestment = BigDecimal.ZERO;
        this.prize = BigDecimal.ZERO;
        this.isJoiningPot = false;
    }

    @Override
    public boolean getIsJoiningPot() {
        return this.isJoiningPot;
    }

    @Override
    public void setIsJoiningPot(boolean isJoiningPot) {
        this.isJoiningPot = isJoiningPot;
    }

    @Override
    public RecordReader getRecordReader() {
        return gameRecorder.getRecordReader();
    }

    @Override
    public String toString() {
        return "{Player" + id +
                ", controller=" + gamePlayer +
                ", stack=" + stack +
                ", pokerCards=" + pokerCards +
                ", isContinuingGame=" + isContinuingGame +
                '}';
    }

    @Override
    public String toSimpleLogString() {
        return "Player" + id;
    }
}
