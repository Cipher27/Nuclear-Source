package com.rs.game.player.actions.thieving;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;

/**
 * 
 * @author Paolo || omglolomghi || Jul 15, 2016
 *
 */

public class PrifindasThieving extends Action {

    public enum Guardians 
    {
	IORWERTH_WORKER(20129, 91, 125),
	ITHELL_WORKER(20328, 92, 130),
	CADARN_WORKER(20145, 93, 135),
	AMLODD_WORKER(20336, 94, 140),
	TRAHAEARN_WORKER(20153, 95, 145),
	HEFIN_WORKER(20344, 96, 150),
	CRWYS_WORKER(20137, 97, 155),
	MEILYR_WORKER(20352, 98, 170);
	
	private static Map<Short, Guardians> guardians = new HashMap<Short, Guardians>();
	
	public static Guardians forId(short npcId) {
		return guardians.get(npcId);
	}

	static {
		for (Guardians guardian: Guardians.values()) {
			guardians.put((short) guardian.getNpcId(), guardian);
		}
	}

	public int npcId;
	public int level;
	public double xp;

	private Guardians (int npcId , int level, double xp){
	    this.npcId = npcId;
	    this.level = level;
	    this.xp = xp;
	}

	public int getLevel() {
	    return level;
	}
	
	public int getNpcId() {
	    return npcId;
	}
	public double getXP() {
	    return xp;
	}


    }
    public static Guardians isPickpocketAble(NPC npc) {
		return Guardians.forId((short) npc.getId());
	}
    
    private Guardians npc2;
    private NPC npc;

	@Override
	public boolean start(Player player) {
	 if (player.getSkills().getLevel(Skills.THIEVING) < npc2.getLevel()) {
			player.getDialogueManager().startDialogue(
					"SimpleMessage",
					"You need a thieving level of " + npc2.getLevel()
							+ " to pickpocket this npc.");
			return false;
		}
	 if (player.getInventory().getFreeSlots() <= 0) {
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.", true);
			return false;
		}
		player.setNextAnimation(getAnimation());
		player.setNextGraphics(getGraphics());
		player.getPackets().sendGameMessage("You attempt to pick the "+ npc.getDefinitions().name.toLowerCase()+ "'s pocket...");
		player.faceEntity(npc);
		return true;
	}

	@Override
	public boolean process(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int processWithDelay(Player player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stop(Player player) {
		// TODO Auto-generated method stub
		
	}
	private int getFishingDelay(Player player) {
		int delay = 0;
		return delay;

	}
	private Animation getAnimation() {
		switch (index) {
		case 0:
			return PICKPOCKETING_ANIMATION;
		case 1:
			return DOUBLE_LOOT_ANIMATION;
		case 2:
			return TRIPLE_LOOT_ANIMATION;
		case 3:
			return QUADRUPLE_LOOT_ANIMATION;
		}
		return null;
	}

	/**
	 * Gets the graphic to perform.
	 * 
	 * @param player
	 *            The player.
	 * @return The graphic.
	 */
	private Graphics getGraphics() {
		switch (index) {
		case 0:
			return null;
		case 1:
			return DOUBLE_LOOT_GFX;
		case 2:
			return TRIPLE_LOOT_GFX;
		case 3:
			return QUADRUPLE_LOOT_GFX;
		}
		return null;
	}

	private static final Animation STUN_ANIMATION = new Animation(422),PICKPOCKETING_ANIMATION = new Animation(881),DOUBLE_LOOT_ANIMATION = new Animation(5074),TRIPLE_LOOT_ANIMATION = new Animation(5075),QUADRUPLE_LOOT_ANIMATION = new Animation(5078);
	private static final Graphics DOUBLE_LOOT_GFX = new Graphics(873),TRIPLE_LOOT_GFX = new Graphics(874),QUADRUPLE_LOOT_GFX = new Graphics(875);
	private int index;



}