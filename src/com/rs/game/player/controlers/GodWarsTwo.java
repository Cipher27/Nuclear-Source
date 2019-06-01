package com.rs.game.player.controlers;

import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.instances.InstancedEncounter;
import com.rs.game.player.content.instances.Instances;
import com.rs.game.player.controlers.InstancedBossControler.Instance;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Colors;
import com.rs.utils.Utils;

/**
 * Handles everything Godwars dungeon related.
 * @author Vichy
 */
public class GodWarsTwo extends Controler {

    @Override
    public void start() {
    	sendInterfaces();
    }

    /**
     * Ints containing our killcount.
     */
    private int armadylKC, bandosKC, saraKC, zammyKC, zarosKC;

    /**
     * Closing all interfaces upon controller removal.
     */
    public void closeKCInterface() {
    	player.getInterfaceManager().closeOverlay(false);
    	removeControler();
    	resetKillCount();
    }

    @Override
    public void forceClose() {
    	closeKCInterface();
    }

    public int getArmadylKC() {
    	return armadylKC;
    }

    public int getBandosKC() {
    	return bandosKC;
    }

    public int getSaraKC() {
    	return saraKC;
    }

    public int getZammyKC() {
    	return zammyKC;
    }
    
    public int getZarosKC() {
    	return zarosKC;
    }

    @Override
    public boolean login() {
    	sendInterfaces();
    	return false;
    }

    @Override
    public boolean logout() {
    	return false;
    }

    @Override
    public void magicTeleported(int type) {
    	closeKCInterface();
    }
	
	 public static void passGiantBoulder(Player player, WorldObject object, boolean liftBoulder) {
		    		if (player.getSkills().getLevelForXp(liftBoulder ? Skills.STRENGTH : Skills.AGILITY) < 60) {
		    		    player.getPackets().sendGameMessage("You need a " + (liftBoulder ? "Strength" : "Agility") + " of 60 in order to " + (liftBoulder ? "lift" : "squeeze past") + " this boulder.");
		    		    return;
		    		}
		    		if (liftBoulder)
		    		    World.sendObjectAnimation(object, new Animation(318));
		    		final boolean isReturning = player.getY() >= 3709;
		    		int baseAnimation = liftBoulder ? 3725 : 3466;
		    		player.useStairs(isReturning ? baseAnimation-- : baseAnimation, new WorldTile(player.getX(), player.getY() + (isReturning ? -4 : 4), 0), liftBoulder ? 10 : 5, liftBoulder ? 11 : 6, null);
	}
    
    /**
     * Killcount required to enter any of the boss rooms.
     * @return the KC required.
     */
    private int kcRequired() {
    	if (player.getRights() == 2 || player.isDivineDonator())
    		return 0;
		if(player.isExtremeDonator() || player.isLegendaryDonator())
			return 8;
		if(player.isDonator() )
			return 15;
    	return 25;
    }

    @Override
    public boolean processObjectClick1(final WorldObject object) {
		if (player.isLocked())
			return false;
    	if (object.getId() == 57258) { //Nex bank room
    		player.lock(1);
    		if (player.getX() <= 2899) {
    			if (getZarosKC() >= kcRequired() || World.nex) {
    			    player.setNextWorldTile(new WorldTile(2900, 5203, 0));
    			    setZarosKC(0);
    			    sendInterfaces();
    			} else
    			    player.sendMessage("This door is locked by the power of Zarosian; "
    			    		+ "you'll need <col=ff0000>"+kcRequired()+"</col> KC to enter!");
    			return false;
    		}
		    player.setNextWorldTile(new WorldTile(2899, 5203, 0));
    		return false;
    	}
    	if (object.getId() == 57234) { //Ice path door to Nex's follower room
    		player.lock(2);
    		final WorldTile tile = new WorldTile(2863 + (player.getX() <= 2860 ? 0 : -3), 5219, 0);
    		player.setNextAnimation(new Animation(1133));
    		player.setNextForceMovement(new ForceMovement(tile, 2, player.getX() <= 2860 ? ForceMovement.EAST : ForceMovement.WEST));
    		    WorldTasksManager.schedule(new WorldTask() {

    			@Override
    			public void run() {
    			    player.setNextWorldTile(tile);
    			    sendInterfaces();
    			}
    		}, 1);
    		return false;
    	}
    	if (object.getId() == 57256 || object.getId() == 57260) { //Climb down/up staircase Nex chamber
    		if (object.getId() == 57256) {
    			if (player.getY() > 5277)
    				return false;
    		}
    		player.lock(3);
    		player.getInterfaceManager().sendOverlay(115, false);
    		WorldTasksManager.schedule(new WorldTask() {
    			
    		    @Override
    		    public void run() {
    				player.getPackets().sendResetCamera();
    				if (object.getId() == 57256)
    					player.setNextWorldTile(new WorldTile(2855, 5222, 0));
    				else
    					player.setNextWorldTile(new WorldTile(2887, 5276, 0));
    				player.getInterfaceManager().closeOverlay(false);
    				sendInterfaces();
    				this.stop();
    		    }
    		}, 2);
    		return false;
    	}
    	if (object.getId() == 75089) { //frozen key door
    		player.lock(1);
    		if (player.getY() <= 5277) {
    			player.setNextWorldTile(new WorldTile(2887, 5279, 0));
    			return false;
    		}
    		if (player.getInventory().containsOneItem(20120)) {
    			if (player.getFrozenKeyCharges() > 0) {
    				player.setFrozenKeyCharges((byte) (player.getFrozenKeyCharges() - 1));
    				if (player.getFrozenKeyCharges() == 0) {
    					player.sendMessage("You've used up your last Frozen Key charge; repair it at Bob.");
    				} else {
    					player.sendMessage("You've used up one Frozen Key charge; "
    							+ "charges left: "+Colors.red+player.getFrozenKeyCharges()+"</col>.");
    				}
        			player.setNextWorldTile(new WorldTile(2887, 5277, 0));
        			return false;
    			}
				player.sendMessage("Your Frozen Key has 0 charges left; repair it at Bob.");;
	    		return false;
    		}
			player.sendMessage("You will need a Frozen Key to enter this door.");
    		return false;
    	}
    	if (object.getId() == 26286 || object.getId() == 26289 || object.getId() == 26288 || object.getId() == 26287) {
    		player.lock(1);
			if (player.getAttackedByDelay() + 15000 > Utils.currentTimeMillis()) {
				player.sendMessage("You can't use this altar until 15 seconds after the end of combat.");
				return false;
			}
			final int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
			if (player.getPrayer().getPrayerpoints() < maxPrayer) {
				player.lock(1);
				player.sendMessage("The gods have recharged your prayer points.");
				player.setNextAnimation(new Animation(645));
				player.getPrayer().restorePrayer(maxPrayer);
			} else
				player.sendMessage("You already have full prayer points.");
			return false;
		} 
    	if (object.getId() == 26293) { //Rope to outside
    		player.lock(1);
    		player.useStairs(828, new WorldTile(2916, 3739, 0), 1, 1);
    		closeKCInterface();
    		return true;
    	}
    	if (object.getId() == 75463) { //Armadyl follower room
    		player.lock(1);
    		if (player.getY() >= 5280) {
		    		if (player.getSkills().getLevelForXp(Skills.RANGE) < 70) {
		    			player.sendMessage("You need a level of 70 Ranged in order to do this.");
		    			return false;
		    		}
		    		player.useStairs(-1, new WorldTile(2872, 5272, 0), 0, 0);
		    		return false;
    		}
    		player.useStairs(-1, new WorldTile(2872, 5280, 0), 0, 0);
    		return false;
    	}
    	if (object.getId() == 26426) { //Armadyl boss room
    		player.lock(3);
		    if (player.getY() <= 5294) {
				if (getArmadylKC() >= kcRequired() || World.armadyl || player.getInventory().containsItem(28128,1)) {
				  if (Instances.isValidInstance(object.getId())) {
					  final InstancedEncounter instance = Instances.VALID_INSTANCES.get(object.getId());
					if (instance == null)
						return false;
					if (instance.checkTile(player)){
						player.getDialogueManager().startDialogue("InstanceD", instance);
						return true;
					}else
						player.getPackets().sendGameMessage("The door does not budge... you must find another means to exit the boss room!");
					return false;

				  /* player.setNextWorldTile(new WorldTile(2835, 5295, 0));
				    setArmadylKC(0);
				    sendInterfaces();*/
				  }
				} else
				    player.sendMessage("This door is locked by the power of Armadyl; "
				    		+ "you'll need <col=ff0000>"+kcRequired()+"</col> KC to enter!");
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2835, 5294, 0));
		    return false;
		}
		if (object.getId() == 75462 && object.getX() == 2912) { //Saradomin follower room
			if (player.getSkills().getLevelForXp(Skills.AGILITY) < 70) {
				player.sendMessage("You need a level of 70 Agility in order to climb the rocky wall.");
				return false;
			}
    		player.lock(1);
		    if (player.getX() <= 2911) {
				
				    player.setNextWorldTile(new WorldTile(2915, 5298, 0));
		    } else 
		    player.setNextWorldTile(new WorldTile(2911, 5298, 0));
		    return false;
		}
		if (object.getId() == 75462 && (object.getX() == 2919 || object.getX() == 2920)) { //Saradomin follower room
			if (player.getSkills().getLevelForXp(Skills.AGILITY) < 70) {
				player.sendMessage("You need a level of 70 Agility in order to climb the rocky wall.");
				return false;
			}
    		player.lock(1);
		    if (player.getY() >= 5279) {
				    player.setNextWorldTile(new WorldTile(player.getX(), 5275, 0));
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(player.getX(), 5279, 0));
		    return false;
		}
		if (object.getId() == 26427) { //Saradomin boss room
    		player.lock(3);
		    if (player.getY() >= 5257) {
				if (getSaraKC() >= kcRequired() || World.saradomin || player.getInventory().containsItem(28131,1)) {
				    player.setNextWorldTile(new WorldTile(2923, 5256, 0));
				    setSaraKC(0);
				    sendInterfaces();
					
				} else
				    player.sendMessage("This door is locked by the power of Saradomin; "
				    		+ "you'll need <col=ff0000>"+kcRequired()+"</col> KC to enter!");
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2923, 5257, 0));
		    return false;
		}
		if (object.getId() == 26384) { //Bandos follower room
    		player.lock(1);
		    if (player.getX() >= 2851) {
					if (!player.getInventory().containsOneItem(2347) && ! player.getToolbelt().containsItem(2347)) {
						player.sendMessage("You will need a Hammer in order to bang on the big door.");
						return false;
					}
					if (player.getSkills().getLevelForXp(Skills.STRENGTH) < 70) {
						player.sendMessage("You need a level of 70 Strength in order to bang on the big door.");
						return false;
					}
				    player.setNextWorldTile(new WorldTile(2850, 5334, 0));
				
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2851, 5334, 0));
		    return false;
		}
		if (object.getId() == 26439) { //Zamorak follower room
    		player.lock(1);
		    if (player.getY() <= 5338) {
					if (player.getSkills().getLevelForXp(Skills.HITPOINTS) < 70) {
						player.sendMessage("You need a level of 70 Constitution in order to jump over.");
						return false;
					}
				    player.setNextWorldTile(new WorldTile(2887, 5344, 0));
				
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2887, 5338, 0));
		    return false;
		}
		if (object.getId() == 26428) { //Zamorak boss room
    		player.lock(3);
		    if (player.getY() >= 5333) {
				if (getZammyKC() >= kcRequired() || World.zamorak || player.getInventory().containsItem(28130,1)) {
				    player.setNextWorldTile(new WorldTile(2925, 5332, 0));
				    setZammyKC(0);
				    sendInterfaces();
				} else
				    player.sendMessage("This door is locked by the power of Zamorak; "
				    		+ "you'll need <col=ff0000>"+kcRequired()+"</col> KC to enter!");
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2925, 5333, 0));
		    return false;
		}
		if (object.getId() == 84024) { //Armadyl pre-boss room
    		player.lock(1);
		    if (player.getX() <= 2830) {
				player.setNextWorldTile(new WorldTile(2831, 5284, 0));
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2830, 5284, 0));
		    return false;
		}
		if (object.getId() == 84026) { //Saradomin pre-boss room
    		player.lock(1);
		    if (player.getY() >= 5265) {
				player.setNextWorldTile(new WorldTile(2919, 5264, 0));
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2919, 5265, 0));
		    return false;
		}
		if (object.getId() == 84022) { //Bandos pre-boss room
    		player.lock(1);
		    if (player.getX() <= 2856) {
				player.setNextWorldTile(new WorldTile(2857, 5357, 0));
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2856, 5357, 0));
		    return false;
		}
		if (object.getId() == 84028) { //k'ril tsutsaroth pre-boss room
    		player.lock(1);
		    if (player.getY() >= 5340) {
				player.setNextWorldTile(new WorldTile(2925, 5339, 0));
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2925, 5340, 0));
		    return false;
		}
		if (object.getId() == 26425) { //Bandos boss room
    		player.lock(3);
		    if (player.getX() <= 2862) {
				if (getBandosKC() >= kcRequired() || World.bandos || player.getInventory().containsItem(28129,1)) {
				   player.setNextWorldTile(new WorldTile(2863, 5357, 0));
				    setBandosKC(0);
				    sendInterfaces();
					
				} else
				    player.sendMessage("This door is locked by the power of Bandos; "
				    		+ "you'll need <col=ff0000>"+kcRequired()+"</col> KC to enter!");
				return false;
		    }
		    player.setNextWorldTile(new WorldTile(2862, 5357, 0));
		    return false;
		}
		if (object.getId() == 57225) { //Nex boss room
			if (player.getX() < 2900)
				return false;
		    player.getDialogueManager().startDialogue("NexEntrance");
		    return false;
		}
		return true;
    }

    /**
     * Resets all kill count.
     */
    public void resetKillCount() {
		setZammyKC(0);
		setBandosKC(0);
		setSaraKC(0);
		setArmadylKC(0);
		setZarosKC(0);
    }

    @Override
    public boolean sendDeath() {
    	closeKCInterface();
    	return true;
    }

    @Override
    public void sendInterfaces() {
    	player.getInterfaceManager().sendOverlay(601, false);
		sendKCString();
    }

    /**
     * Fills the interface with our killcount.
     */
    public void sendKCString() {
		player.getPackets().sendIComponentText(601, 8, getArmadylKC() + "");
		player.getPackets().sendIComponentText(601, 9, getBandosKC() + "");
		player.getPackets().sendIComponentText(601, 10, getSaraKC() + "");
		player.getPackets().sendIComponentText(601, 11, getZammyKC() + "");
    }

    public void setArmadylKC(int armadylKC1) {
    	armadylKC = armadylKC1;
    }
    public void reduceArmaKC(){
    	armadylKC -= kcRequired();
    }
    public void reduceBandosKC(){
    	bandosKC -= kcRequired();
    }
    
    public void reduceSaraKC(){
    	saraKC -= kcRequired();
    }
    public void reduceZammyKC(){
    	zammyKC -= kcRequired();
    }

    public void setBandosKC(int bandosKC1) {
    	bandosKC = bandosKC1;
    }

    public void setSaraKC(int saraKC1) {
    	saraKC = saraKC1;
    }

    public void setZammyKC(int zammyKC1) {
    	zammyKC = zammyKC1;
    }
    
    public void setZarosKC(int zarosKC1) {
    	zarosKC = zarosKC1;
    }
    
    /**
     * Warpriest of Zamorak equipment.
     */
    private static Item[] warpriestZamorak = {new Item(28773), new Item(28776), new Item(28779), 
			new Item(28782), new Item(28785), new Item(28788)};
    
    /**
     * Warpriest of Saradomin equipment.
     */
    private static Item[] warpriestSaradomin = {new Item(28755), new Item(28758), new Item(28761), 
			new Item(28764), new Item(28767), new Item(28770)};
    
    /**
     * Warpriest of Armadyl equipment.
     */
    private static Item[] warpriestArmadyl = {new Item(30288), new Item(30291), new Item(30294), 
			new Item(30297), new Item(30300), new Item(30303)};
    
    /**
     * Warpriest of Bandos equipment.
     */
    private static Item[] warpriestBandos = {new Item(30306), new Item(30309), new Item(30312), 
			new Item(30315), new Item(30318), new Item(30321)};
    
    /**
     * The Item array for rewards.
     */
    private static Item[] reward;
    
    /**
     * Handles the NPC killcounts / additional drops.
     * @param npc The NPC.
     * @return if Godwars NPC.
     */
    public boolean handleKC(NPC npc) {
		sendKCString();
    	if (npc.getId() >= 6210 && npc.getId() <= 6221) {
    		setZammyKC(getZammyKC() + 1);
    		if (Utils.random(250 - 10) <= 1) {
    			Item item = new Item(20123, 1);
    			if (!player.hasItem(item) && !player.hasItem(new Item(20120, 1))) {
    				World.addGroundItem(item, new WorldTile(npc), player, false, 60);
    				player.sendMessage("The "+npc.getName().toLowerCase()+" has dropped a "+item.getName()+".");
    			}
    		}
    		/** Warpriest item drops based on Defence level **/
    		if (Utils.random(250 - 10) <= 1) {
    			int defenceLvl = player.getSkills().getLevelForXp(Skills.DEFENCE);
    			if (defenceLvl >= 25) {
    				reward = new Item[] { warpriestZamorak[Utils.random(warpriestZamorak.length)] };
    				for (Item item : reward) {
    					if (player.hasItem(item))
    						continue;
    					World.updateGroundItem(item, new WorldTile(npc), player);
        				World.sendLootbeam(player, new WorldTile(npc));
    				}
    			}
    		}
    		return true;
    	}
    	else if (npc.getId() >= 6229 && npc.getId() <= 6246) {
    		setArmadylKC(getArmadylKC() + 1);
    		if (Utils.random(250 - 10) <= 1) {
    			Item item = new Item(20121, 1);
    			if (!player.hasItem(item) && !player.hasItem(new Item(20120, 1))) {
    				World.addGroundItem(new Item(20121), new WorldTile(npc), player, false, 60);
    				player.sendMessage("The "+npc.getName().toLowerCase()+" has dropped a "+item.getName()+".");
    			}
    		}
    		/** Warpriest item drops based on Defence level **/
    		if (Utils.random(250 - 10) <= 1) {
    			int defenceLvl = player.getSkills().getLevelForXp(Skills.DEFENCE);
    			if (defenceLvl >= 25) {
    				reward = new Item[] { warpriestArmadyl[Utils.random(warpriestArmadyl.length)] };
    				for (Item item : reward) {
    					if (player.hasItem(item))
    						continue;
    					World.updateGroundItem(item, new WorldTile(npc), player);
        				World.sendLootbeam(player, new WorldTile(npc));
    				}
    			}
    		}
    		return true;
    	}
    	else if (npc.getId() >= 6254 && npc.getId() <= 6259) {
    		setSaraKC(getSaraKC() + 1);
    		if (Utils.random(25) == 0) {
    			Item item = new Item(20124, 1);
    			if (!player.hasItem(item) && !player.hasItem(new Item(20120, 1))) {
    				World.addGroundItem(item, new WorldTile(npc), player, false, 60);
    				player.sendMessage("The "+npc.getName().toLowerCase()+" has dropped a "+item.getName()+".");
    			}
    		}
    		/** Warpriest item drops based on Defence level **/
    		if (Utils.random(250 - 10) <= 1) {
    			int defenceLvl = player.getSkills().getLevelForXp(Skills.DEFENCE);
    			if (defenceLvl >= 25) {
    				reward = new Item[] { warpriestSaradomin[Utils.random(warpriestSaradomin.length)] };
    				for (Item item : reward) {
    					if (player.hasItem(item))
    						continue;
    					World.updateGroundItem(item, new WorldTile(npc), player);
        				World.sendLootbeam(player, new WorldTile(npc));
    				}
    			}
    		}
    		return true;
    	}
    	else if (npc.getId() >= 6268 && npc.getId() <= 6283) {
    		setBandosKC(getBandosKC() + 1);
    		if (Utils.random(250 - 10) <= 1) {
    			Item item = new Item(20122, 1);
    			if (!player.hasItem(item) && !player.hasItem(new Item(20120, 1))) {
    				World.addGroundItem(item, new WorldTile(npc), player, false, 60);
    				player.sendMessage("The "+npc.getName().toLowerCase()+" has dropped a "+item.getName()+".");
    			}
    		}
    		/** Warpriest item drops based on Defence level **/
    		if (Utils.random(250 - 10) <= 1) {
    			int defenceLvl = player.getSkills().getLevelForXp(Skills.DEFENCE);
    			if (defenceLvl >= 25) {
    				reward = new Item[] { warpriestBandos[Utils.random(warpriestBandos.length)] };
    				for (Item item : reward) {
    					if (player.hasItem(item))
    						continue;
    					World.updateGroundItem(item, new WorldTile(npc), player);
        				World.sendLootbeam(player, new WorldTile(npc));
    				}
    			}
    		}
    		return true;
    	} else if (npc.getId() >= 13456 && npc.getId() <= 13459) {
    		setZarosKC(getZarosKC() + 1);
    		player.sendMessage("You've killed a total of "
    				+ Colors.red+Utils.getFormattedNumber(getZarosKC())+"</col> Zaros followers.");
    		return true;
    	}	
    	else return false;
    }
}