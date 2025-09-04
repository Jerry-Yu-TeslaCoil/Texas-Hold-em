package pojo.impl;

import pojo.*;

import java.math.BigDecimal;

public class NormalCardTable implements CardTable {

    private int maxPlayers;
    private int minPlayers;
    private BigDecimal bigBlindValue;
    private PlayerCoil players;
    private NoJokerDeckFactory noJokerDeckFactory;

    public NormalCardTable() {
        this.maxPlayers = 22;
        this.minPlayers = 0;
        this.players = new PlayerCoil();
    }

    public NormalCardTable(int maxPlayers, int minPlayers) {
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    @Override
    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    @Override
    public int getMinPlayers() {
        return this.minPlayers;
    }

    @Override
    public TableMemberResult playerJoin(CardPlayer cardPlayer) {
        if (cardPlayer == null) {
            throw new NullPointerException("Given cardPlayer cannot be null");
        }
        if (this.players.getPlayerNum() >= this.maxPlayers) {
            return new TableMemberResult(false, "The table cannot join any more players");
        }
        this.players.addPlayer(cardPlayer);
        return new TableMemberResult(true);
    }

    @Override
    public TableMemberResult playerLeave(CardPlayer cardPlayer) {
        if (cardPlayer == null) {
            throw new NullPointerException("Removed cardPlayer cannot be null");
        }
        this.players.removePlayer(cardPlayer);
        return new TableMemberResult(true);
    }

    @Override
    public void startRounds() {
        while (this.players.getPlayerNum() > this.minPlayers) {
           startRound();
        }
    }

    private void startRound() {
        CardDeck cardDeck = noJokerDeckFactory.getCardDeck();
        System.out.println(this.players.getPlayerNum() + " players have joined the table.");
        System.out.println("Starting with big blind.");
        System.out.println("Small blind.");
        System.out.println("Deal");
        //Deal cards.
        for (CardPlayer cardPlayer : this.players.getPlayers()) {
            cardPlayer.receiveHoleCard(cardDeck.takePeekCard());
            cardPlayer.receiveHoleCard(cardDeck.takePeekCard());
        }
        //Everyone make a round decision. Will engage from small blind to the last one.
        while (!players.isTheLastPlayer()) {
            players.nextPlayer();
            System.out.println("Decided if call or not.");
            players.getCurrentPlayer().getPlayerDecision(
                    new DecisionRequest(bigBlindValue));
        }
    }
}
