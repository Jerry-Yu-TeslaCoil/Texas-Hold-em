package table.vo.publicinfo;

import java.math.BigDecimal;

/**
 * View object class for Pot info.
 * <p>
 * Pot's info should include:
 *     <ul>
 *         <li>All invested chips</li>
 *     </ul>
 * </p>
 */
public record PotPublicVO(BigDecimal allInvestedChips) {
}
