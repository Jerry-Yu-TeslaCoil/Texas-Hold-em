import control.player.impl.RobotGamePlayer;
import control.player.controller.impl.RobotGamePlayerRandom;
import exception.IllegalOperationException;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import table.player.CardPlayer;
import table.player.impl.SimpleCardPlayer;
import util.PlayerUtil;

import java.math.BigDecimal;

@Log4j2
public class TestPlayerUtil {

    @Test
    public void testChargingPlayerChips() {
        CardPlayer player = new SimpleCardPlayer(new RobotGamePlayer(new RobotGamePlayerRandom()), new BigDecimal(10), 0);
        BigDecimal charged = PlayerUtil.collectChipsAsMuch(player, new BigDecimal(9));
        log.info("Charge player with 9 chips, actually charged: {}", charged);
        charged = PlayerUtil.collectChipsAsMuch(player, new BigDecimal(8));
        log.info("Charge player with 8 chips, actually charged: {}", charged);
        player.setStack(new BigDecimal(10));
        Assert.assertThrows(IllegalOperationException.class, () -> PlayerUtil.collectChipsExactly(player, new BigDecimal(11)));
    }
}
