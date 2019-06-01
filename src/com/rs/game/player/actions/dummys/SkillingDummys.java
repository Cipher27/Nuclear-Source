package com.rs.game.player.actions.dummys;

import com.rs.game.Animation;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * 
 * @author paolo
 *
 */
public class SkillingDummys extends Action {

	
	private NPC npc;
    private final int SLAYER_DUMMY = 22055;
	private final int THIEVING_DUMMY = 22056;
	public SkillingDummys(NPC npc) {
		this.npc = npc;
	}

	@Override
	public boolean start(Player player) {
		if (player.getInventory().getFreeSlots() < 1) {
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			player.stopAll();
			return false;
		}
		switch (npc.getId()) {
		case THIEVING_DUMMY: //thieving 
			player.setNextAnimation(new Animation(24887));
			return true;
		}
		return false;
	}
    public static int times = 0;
	@Override
	public boolean process(Player player) {
		player.setNextAnimation(new Animation(24887));
		if (player.getX() == npc.getX() && player.getY() == npc.getY())
			player.addWalkSteps(npc.getX() - 1, npc.getY(), -1, true);
		player.addWalkSteps(npc.getX(), npc.getY() - 1, -1, true);
		player.faceEntity(npc);
		if (checkAll(player)) { 
			if(times == 20){
				stop(player);
				npc.sendDeath(npc);
			}
			return true;
		}
		return false;
	}

	@Override
	public int processWithDelay(Player player) {
		player.getSkills().addXp(Skills.THIEVING, xpAmount());
		return Utils.random(10, 30);
	}

	/**
	 * amount of exp, todo amount increased by level
	 * @return
	 */
	private int xpAmount() {
			return 530;
	}

	private boolean checkAll(Player player) {
		switch (npc.getId()) {
		case 17377:
			if (player.getSkills().getLevelForXp(Skills.THIEVING) < 92) {
				player.getPackets()
						.sendGameMessage(
								"You need to have a thieving level of at least 92 to pickpocket from this npc.");
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void stop(final Player player) {
		npc.setNextFaceEntity(null);
		player.getEmotesManager().setNextEmoteEnd(2400);
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.setNextAnimation(new Animation(-1));
				player.getAppearence().setRenderEmote(-1);
			}
		}, 3);
	}
}