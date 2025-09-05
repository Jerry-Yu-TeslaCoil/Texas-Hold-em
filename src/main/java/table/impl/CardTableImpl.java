package table.impl;

import table.CardTable;
import table.card.CardDeck;
import table.card.impl.NoJokerDeckFactory;
import table.player.CardPlayer;
import table.player.PlayerList;
import table.player.impl.PlayerCoil;
import util.ApplicationResult;

import java.math.BigDecimal;

public class CardTableImpl implements CardTable {
    private BigDecimal bigBlindValue;
    private final PlayerList players;
    private NoJokerDeckFactory noJokerDeckFactory;

    public CardTableImpl() {
        this(22);
    }

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
    public void startRound() {
        CardDeck cardDeck = noJokerDeckFactory.getCardDeck();
        System.out.println(this.players.getPlayerNum() + " players have joined the table.");
        System.out.println("Starting with small blind.");
        System.out.println("Big blind.");
        System.out.println("Deal");
        //Deal cards.
        for (CardPlayer cardPlayer : this.players.getPlayers()) {
            cardPlayer.receiveHoleCard(cardDeck.takePeekCard());
            cardPlayer.receiveHoleCard(cardDeck.takePeekCard());
        }
        //Everyone make a round decision. Will engage from small blind to the last one.
    }
}
