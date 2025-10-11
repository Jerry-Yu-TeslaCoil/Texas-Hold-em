package table.impl;

import table.CardTable;
import table.card.CardDeck;
import table.card.impl.NoJokerDeckFactory;
import table.player.CardPlayer;
import table.player.PlayerList;
import table.player.impl.PlayerCoil;
import util.ApplicationResult;

import java.math.BigDecimal;

/**
 * The simplest card table, conducting usual game rounds.
 *
 * <p>
 *     Simply construct an instance and call startRound() would start a game.
 * </p>
 *
 * <p>
 *     Currently, this is considered not thread-safe.
 *     TODO: Do reconstruct this class to thead-safe version to support multi-table platform.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class CardTableImpl implements CardTable {
    private BigDecimal bigBlindValue;
    private final PlayerList players;
    private NoJokerDeckFactory noJokerDeckFactory;

    /**
     * Construct a table with no max player num limit appointed.
     * The max player num will be set as 22 players same as usual rule.
     */
    public CardTableImpl() {
        this(22);
    }

    /**
     * Appoint a max player num to construct a table.
     * @param maxPlayers The max number of card players.
     */
    public CardTableImpl(int maxPlayers) {
        this.players = new PlayerCoil();
        this.players.setMaxPlayers(maxPlayers);
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        this.players.setMaxPlayers(maxPlayers);
    }

    @Override
    public int getMaxPlayers() {
        return this.players.getMaxPlayers();
    }

    @Override
    public ApplicationResult playerJoin(CardPlayer cardPlayer) {
        if (cardPlayer == null) {
            throw new NullPointerException("Given cardPlayer cannot be null");
        }
        return this.players.addPlayer(cardPlayer);
    }

    @Override
    public ApplicationResult playerLeave(CardPlayer cardPlayer) {
        if (cardPlayer == null) {
            throw new NullPointerException("Given cardPlayer cannot be null");
        }
        return this.players.removePlayer(cardPlayer);
    }

    @Override
    public void startRounds() {
        CardDeck cardDeck = noJokerDeckFactory.getCardDeck();
        System.out.println(this.players.getPlayerNum() + " players have joined the table.");
        System.out.println("Starting with small blind.");
        System.out.println("Big blind.");
        System.out.println("Deal");
        //Deal cards.
        //TODO: Directly operate raw list of the players is not recommended. Do switch to an iterator.
        for (CardPlayer cardPlayer : this.players.getPlayers()) {
            cardPlayer.addHoleCard(cardDeck.takePeekCard());
            cardPlayer.addHoleCard(cardDeck.takePeekCard());
        }
        //Everyone make a round decision. Will engage from small_blind to the last one.
    }
}
