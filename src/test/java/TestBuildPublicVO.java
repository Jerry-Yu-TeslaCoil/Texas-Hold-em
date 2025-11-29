import control.gameplayer.impl.RobotGamePlayer;
import control.playercontroller.impl.RobotGamePlayerDoPreset;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.card.CardDeck;
import table.card.impl.NoJokerDeckFactory;
import table.player.CardPlayer;
import table.player.impl.SimpleCardPlayer;
import table.state.gamestate.GameState;
import table.vo.publicinfo.PlayerPublicVO;
import table.vo.publicinfo.builder.PlayerPublicVOBuilder;
import table.vo.publicinfo.builder.impl.ClassicPlayerPublicVOBuilder;
import util.PlayerUtil;

import java.math.BigDecimal;

@Log4j2
public class TestBuildPublicVO {

    @Test
    public void testBuildPublicVO() {
        CardDeck cardDeck = NoJokerDeckFactory.getInstance().getCardDeck();
        cardDeck.shuffle();
        CardPlayer cardPlayer = new SimpleCardPlayer(new RobotGamePlayer(new RobotGamePlayerDoPreset()), new BigDecimal(24), 0);
        cardPlayer.setIsContinuingGame(true);
        PlayerPublicVOBuilder publicVOBuilder = new ClassicPlayerPublicVOBuilder();
        publicVOBuilder.setState(GameState.INIT);
        PlayerPublicVO playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("Init state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.PRE_FLOP);
        PlayerUtil.collectChipsAsMuch(cardPlayer, new BigDecimal(2));
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("PreFlop state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.FLOP);
        cardPlayer.addHoleCard(cardDeck.takePeekCard());
        cardPlayer.addHoleCard(cardDeck.takePeekCard());
        PlayerUtil.collectChipsAsMuch(cardPlayer, new BigDecimal(4));
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("Flop state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.TURN);
        PlayerUtil.collectChipsAsMuch(cardPlayer, new BigDecimal(4));
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("Turn state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.RIVER);
        PlayerUtil.collectChipsAsMuch(cardPlayer, new BigDecimal(4));
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("River state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.SHOWDOWN);
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("Showdown state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.END);
        cardPlayer.setPlayerPrize(new BigDecimal(5));
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("End state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.INIT);
        cardPlayer.clearState();
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("Init state VO: {}", playerPublicVO);
    }

    @Test
    public void testBuildPublicVOFOLD() {
        CardDeck cardDeck = NoJokerDeckFactory.getInstance().getCardDeck();
        cardDeck.shuffle();
        CardPlayer cardPlayer = new SimpleCardPlayer(new RobotGamePlayer(new RobotGamePlayerDoPreset()), new BigDecimal(24), 0);
        cardPlayer.setIsContinuingGame(true);
        PlayerPublicVOBuilder publicVOBuilder = new ClassicPlayerPublicVOBuilder();
        publicVOBuilder.setState(GameState.INIT);
        PlayerPublicVO playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("Before FOLD: Init state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.PRE_FLOP);
        PlayerUtil.collectChipsAsMuch(cardPlayer, new BigDecimal(2));
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("Before FOLD: PreFlop state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.FLOP);
        cardPlayer.addHoleCard(cardDeck.takePeekCard());
        cardPlayer.addHoleCard(cardDeck.takePeekCard());
        PlayerUtil.collectChipsAsMuch(cardPlayer, new BigDecimal(4));
        cardPlayer.setIsContinuingGame(false);
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("After FOLD: Flop state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.SHOWDOWN);
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("After FOLD: Showdown state VO: {}", playerPublicVO);
        publicVOBuilder.setState(GameState.END);
        cardPlayer.setPlayerPrize(new BigDecimal(5));
        playerPublicVO = publicVOBuilder.build(cardPlayer);
        log.info("After FOLD: End state VO: {}", playerPublicVO);
    }
}
