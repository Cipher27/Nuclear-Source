package com.rs.game.player.actions.divination;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Action;
import com.rs.utils.Utils;
public class HarvestAction extends Action {

    public enum Wisps 
    {
	PALE_ENERGY(18150, 29313, 29384, 1, 1),
	FLICKERING_ENERGY(18151, 29314, 29385, 10, 1);
	
	private static Map<Short, Wisps> wisps = new HashMap<Short, Wisps>();
	
	public static Wisps forId(short npcId) {
		return wisps.get(npcId);
	}

	static {
		for (Wisps wisp: Wisps.values()) {
			wisps.put((short) wisp.getNpcId(), wisp);
		}
	}

	public int npcId;
	public int energyId;
	public int memoryId;
	public int level;
	public double xp;

	private Wisps(int npcId, int energyId, int memoryId, int level, double xp)
	{
	    this.npcId = npcId;
	    this.energyId = energyId;
	    this.memoryId = memoryId;
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
	public int getId() {
	    return npcId;
	}

	public int getEnergyId() {
	    return energyId;
	}

	public int getMemoryId() {
	    return memoryId;
	}
	 public static Wisps canHarvest(NPC npc) {
		return Wisps.forId((short) npc.getId());
	}


    }
 private static final Animation HARVEST_ANIMATION = new Animation(21231);

    private Wisp wisps;
    private NPC wisp;
    private boolean enriched;

    public HarvestAction(Wisp wisp) {
	this.wisp = wisp;
	this.setEnriched(wisp.getDefinitions().getName().contains("Enriched"));
    }

    @Override
    public boolean start(Player player) {
	if(!check(player))
	    return false;
	setActionDelay(player, 2);
	player.setNextAnimation(HARVEST_ANIMATION);
	return true;
    }
    
    
    public HarvestAction(Wisp wisps, NPC wisp)
    {
	this.wisps = wisps;
	this.wisp = wisp;
    }

    @Override
    public boolean process(Player player) {
	return check(player);
    }

    @Override
    public int processWithDelay(Player player) { 
	    int gfxId = 4235;
	    for (Wisps w : Wisps.values())
	    {
		  
			player.setNextGraphics(new Graphics(gfxId));
			player.getInventory().addItem(w.getMemoryId(), 1);
			if (Utils.random(3) == 1)
			{
			    player.getInventory().addItem(w.getEnergyId(), Utils.random(1, 2));
			}
		    
	    }

	return 2;
    }

    @Override
    public void stop(Player player) {
	player.setNextAnimation(new Animation(21229));
    }


    public boolean check(Player player) {
	return wisps.getLifeTime() > 0;
    }

    /**
     * @return the enriched
     */
    public boolean isEnriched() {
	return enriched;
    }

    /**
     * @param enriched the enriched to set
     */
    public void setEnriched(boolean enriched) {
	this.enriched = enriched;
    }
}