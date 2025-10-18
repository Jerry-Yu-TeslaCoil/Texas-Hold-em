package table.vo.publicinfo;

/**
 * View Object of all public information class.
 *
 * <p>
 * This class is for sending public info to the player panel.
 * As for a VO, all its parameters are final to make sure no invalid modification exists.
 * </p>
 *
 * <p>
 * When the player is playing a round, he needs to know:
 *     <ul>
 *         <li>
 *             Table information:
 *             <ul>
 *                 <li>Basic bet</li>
 *                 <li>Initial bet</li>
 *                 <li>Current game state</li>
 *                 <li>Current decision maker</li>
 *                 <li>Made decision</li>
 *                 <li>Public cards(Some revealed)</li>
 *             </ul>
 *         </li>
 *         <li>
 *             All players' info(An array):
 *             <ul>
 *                 <li>Player's public info</li>
 *                 <li>Two cards(Unrevealed until the end of round)</li>
 *                 <li>Chips in hand</li>
 *                 <li>Chips already invested</li>
 *                 <li>If still continuing the game</li>
 *             </ul>
 *         </li>
 *         <li>
 *             Pot's info:
 *             <ul>
 *                 <li>All invested chips</li>
 *             </ul>
 *         </li>
 *     </ul>
 * </p>
 *
 * @author jerry
 * @version 1.0
 */
public record PublicVO(TablePublicVO tablePublicVO, PlayerPublicVO[] playerPublicVO,
                       PotPublicVO potPublicVO) {

    @Override
    public String toString() {
        return "\n=========================== PublicVO ===========================\n" +
                "Table: " + (tablePublicVO != null ? tablePublicVO : "null") + "\n" +
                "Pot: " + (potPublicVO != null ? potPublicVO : "null") + "\n" +
                "Players (" + (playerPublicVO != null ? playerPublicVO.length : 0) + "):\n" +
                getPlayersSummary();
    }

    private String getPlayersSummary() {
        if (playerPublicVO == null || playerPublicVO.length == 0) {
            return "  No players";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < playerPublicVO.length; i++) {
            sb.append("  ").append(i + 1).append(". ").append(playerPublicVO[i]).append("\n");
        }
        return sb.toString();
    }
}
