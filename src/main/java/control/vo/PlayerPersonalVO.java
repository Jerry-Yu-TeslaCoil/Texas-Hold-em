package control.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * View object class for player's personal public info.
 *
 * <p>
 *     This should include:
 *     <ul>
 *         <li>Name</li>
 *         <li>Avatar</li>
 *         <li>ID(For detail information)</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     WARNING: Do remember to use deep-copy and final elements to ensure no insecure modification.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
@AllArgsConstructor
@Data
@ToString
public class PlayerPersonalVO <T> {
    private final T info;
}
