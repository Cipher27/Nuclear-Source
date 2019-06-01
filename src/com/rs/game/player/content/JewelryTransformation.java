package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.utils.Utils;

/**
 * 
 * @author paolo
 *
 */
public class JewelryTransformation {


    private static final int[] EGG_IDS = { 3689, 3690, 3691, 3692, 3693, 3694 };

    public static void ringTransformation(Player player, final int itemId) {
	if (player.getActionManager().getAction() != null) {
	    player.getPackets().sendGameMessage("Please finish what you are doing before transforming.");
	    return;
	}
	player.getActionManager().setAction(new Action() {

	    @Override
	    public boolean start(Player player) {
		player.stopAll();
		player.lock(2);
		int transformationId = EGG_IDS[Utils.random(EGG_IDS.length)];
		if (itemId == 6583)
		    transformationId = 2626;
		player.getAppearence().transformIntoNPC(transformationId);
		player.getInterfaceManager().sendInventoryInterface(375);
		return true;
	    }

	    @Override
	    public boolean process(Player player) {
		return true;
	    }

	    @Override
	    public int processWithDelay(Player player) {
		return 0;
	    }

	    @Override
	    public void stop(Player player) {
		setActionDelay(player, 3);
		resetTransformation(player);
	    }
	});
    }

    public static void resetTransformation(Player player) {
	player.getInterfaceManager().closeInventoryInterface();
	player.getInventory().init();
	player.setNextAnimation(new Animation(14884));
	player.getAppearence().transformIntoNPC(-1);
    }
}
