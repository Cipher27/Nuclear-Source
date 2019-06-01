package com.rs.game.player.actions.thieving;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.item.Item;
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
public class MasterThieves extends Action {

	
	private NPC npc;

	public MasterThieves(NPC npc) {
		this.npc = npc;
	}
	/**
	  * Every item you can get from the master thieves
	  **/
	public Item loot[] = {new Item(168,1), new Item(162,1), new Item(150,1), new Item(242,1), new Item(1778,1), new Item(8779,1), new Item(445,1), new Item(454,1), new Item(386,1), new Item(232,1)};

	@Override
	public boolean start(Player player) {
		if (player.getInventory().getFreeSlots() < 1) {
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			player.stopAll();
			return false;
		}
		switch (npc.getId()) {
		case 17377:
			if (player.getSkills().getLevelForXp(Skills.THIEVING) < 92) {
				player.getPackets()
						.sendGameMessage(
								"You need to have a thieving level of at least 92 to pickpocket from this npc.");
				return false;
			}
			player.setNextAnimation(new Animation(24887));
			return true;
		}
		return false;
	}

	@Override
	public boolean process(Player player) {
		player.setNextAnimation(new Animation(24887));
		if (player.getX() == npc.getX() && player.getY() == npc.getY())
			player.addWalkSteps(npc.getX() - 1, npc.getY(), -1, true);
		player.addWalkSteps(npc.getX(), npc.getY() - 1, -1, true);
		player.faceEntity(npc);
		if (checkAll(player)) { 
			if (Utils.random(400) == 0){ //to make it not that afk, random stop
				player.stopAll();
			}
			if (Utils.random(player.getSkills().getLevel(Skills.THIEVING) * 15) == 0) { //higher the thieving lever, lower change 2 stop
				player.stopAll();
				npc.faceEntity(player);
				player.setNextAnimation(new Animation(424));
				player.setNextGraphics(new Graphics(80, 5, 60));
				player.getPackets().sendGameMessage("You have raised their suspicions.", true);
				player.applyHit(new Hit(player, 300, HitLook.REGULAR_DAMAGE));
				player.lock(3);
			}
			return true;
		}
		return false;
	}
	@Override
	public int processWithDelay(Player player) {
		player.getSkills().addXp(Skills.THIEVING, xpAmount());
		addItems(player);
		return Utils.random(7,15);
	}

	private void addItems(Player player) {
		if (player.getInventory().getFreeSlots() < 1) {
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			player.stopAll();
			return;
		}
		player.getMoneyPouch().setAmount(Utils.random(4350, 7460), false);
		if(Utils.random(10) > 1) //don't need an item every exp drop
		player.getInventory().addItem(loot[Utils.random(0, loot.length)].getId(), Utils.random(2,10));
	}

	/**
	 * never know if you want to add more npcs, might be usefull for prifdinas 
	 * @return
	 */
	private int xpAmount() {
		switch (npc.getId()) {
		case 17377:
			return 480;
		}
		return 10;
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