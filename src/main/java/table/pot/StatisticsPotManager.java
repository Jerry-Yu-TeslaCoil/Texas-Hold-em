package table.pot;

import java.math.BigDecimal;

/**
 * Extra interface for chips countable PotManager.
 *
 * <p>
 *     This is used for the circumstances where PotManager needs a method to get total investment.
 *     It's very suitable for decoration pattern.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface StatisticsPotManager extends PotManager {

    BigDecimal getTotalInvestment();
}
