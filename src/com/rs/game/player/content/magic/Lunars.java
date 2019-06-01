package com.rs.game.player.content.magic;

import java.util.ArrayList;
import java.util.List;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Region;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.FarmingManager;
import com.rs.game.player.FarmingManager.FarmingSpot;
//import com.rs.game.minigames.ectofuntus.Ectofuntus;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.game.player.actions.Cooking.Cookables;
import com.rs.game.player.actions.FillAction.Filler;
import com.rs.utils.Utils;

public class Lunars {
	
	public static int[] logs =   { 1511, 1521, 6333, 6332  };
	public static int[] planks = { 960,  8778, 8780, 8782 };
	
	public static int[] unstrung = { 1673, 1675, 1677, 1679, 1681, 1683, 1714, 1720, 6579 };
	public static int[] strung   = { 1692, 1694, 1696, 1698, 1700, 1702, 1716, 1722, 6581 };
	
	public static Player[] getNearPlayers(Player player, int distance, int maxTargets) {
		List<Entity> possibleTargets = new ArrayList<Entity>();
		stop: for (int regionId : player.getMapRegionsIds()) {
			Region region = World.getRegion(regionId);
				List<Integer> playerIndexes = region.getPlayerIndexes();
				if (playerIndexes == null)
					continue;
				for (int playerIndex : playerIndexes) {
					Player p2 = World.getPlayers().get(playerIndex);
					if (p2 == null || p2 == player || p2.isDead() || !p2.hasStarted() || p2.hasFinished() 
					 || !p2.withinDistance(player, distance))
						continue;
					possibleTargets.add(p2);
					if (possibleTargets.size() == maxTargets)
						break stop;
				}
		}
		return possibleTargets.toArray(new Player[possibleTargets.size()]);
	}
	
	public static boolean hasUnstrungs(Player player) {
		for (Item item : player.getInventory().getItems().getItems()) {
			if (item == null)
				continue;
			if (getStrungIndex(item.getId()) != -1)
				return true;
		}
		return false;
	}
	
	public static int getStrungIndex(int ammy) {
		for(int i = 0;i < unstrung.length;i++) {
			if (unstrung[i] == ammy)
				return i;
		}
		return -1;
	}
	
	public static int getPlankIdx(int logId) {
		for(int i = 0;i < logs.length;i++) {
			if (logs[i] == logId)
				return i;
		}
		return -1;
	}
	
	public static void openRemoteFarm(Player player) {
		if (!player.canAlch()) {
			player.getPackets().sendGameMessage("There is a 5 second delay to this.");
			return;
		}
		player.setAlchDelay(5000L);
	int[] names = new int[] { 30, 32, 34, 36, 38, 49, 51, 53, 55, 57, 59, 62, 64, 66, 68, 70, 72, 74, 76, 190, 79, 81, 83, 85, 88, 90, 92, 94, 97, 99, 101, 104, 106, 108, 110, 115, 117, 119, 121, 123, 125, 131, 127, 129, 2, 173, 175, 177, 182, 184, 186, 188 };

		player.getInterfaceManager().sendInterface(1082);
		for (int i = 0; i < names.length; i++) {
			if (i < FarmingManager.SpotInfo.values().length && FarmingManager.SpotInfo.values()[i].getType() != FarmingManager.COMPOST) {
				player.getPackets().sendIComponentText(1082, names[i], Utils.formatPlayerNameForDisplay(FarmingManager.SpotInfo.values()[i].name().replace("_", " ")));
			} else {
				player.getPackets().sendIComponentText(1082, names[i], "");
			}
		}
		for (int i = 0; i < names.length; i++) {
			String message = "";
			if (i < FarmingManager.SpotInfo.values().length && FarmingManager.SpotInfo.values()[i].getType() != FarmingManager.COMPOST) {
				FarmingSpot spot = player.getFarmingManager().getSpot(FarmingManager.SpotInfo.values()[i]);
				message = "Full of Weeds";
				if (spot != null) {
					if (!spot.isCleared())
						message = "Full of Weeds!";
					else if (spot.isCleared())
						message = "Currently Empty!";
					else if (spot.isDead())
						message = "<col=8f13b5>Currently Dead!";
					else if (spot.isDiseased())
						message = "<col=FF0000>Currently Diseased!";
					else if (spot.reachedMaxStage() && !spot.hasChecked())
						message = "<col=00FF00>Ready for Health Check!";
					else if (spot.reachedMaxStage() && !spot.isEmpty())
						message = "<col=00FF00>Fully Grown w/ Produce Available!";
					else if (spot.reachedMaxStage() && spot.isEmpty())
						message = "<col=00FF00>Fully Grown w/ No Produce Available!";
					else if (!spot.reachedMaxStage())
						message = "<col=00FF00>Currently Growing Healthy!";
				}
			}
			player.getPackets().sendIComponentText(1082, names[i] + 1, message);
		}
	}
	
	public static void handlePlankMake(Player player, Item item) {
		player.getInterfaceManager().openGameTab(7);
		if (!player.canAlch()) {
			return;
		}
		int index = getPlankIdx(item.getId());
		if (index == -1) {
			player.getPackets().sendGameMessage("You can only cast this spell on a log.");
			return;
		}
		
		if (!player.getInventory().containsItem(logs[index], 1))
			return;
		
		if (!Magic.checkSpellRequirements(player, 86, true, Magic.NATURE_RUNE, 1, Magic.ASTRAL_RUNE, 2, Magic.EARTH_RUNE, 15))
			return;
	
		player.setNextAnimation(new Animation(6298));
		player.setNextGraphics(new Graphics(1063, 0, 50));
		player.getInventory().deleteItem(logs[index], 1);
		player.getInventory().addItem(planks[index], 1);
		player.getSkills().addXp(Skills.MAGIC, 90);
		player.setAlchDelay(1100L);
	}
	
	public static void handleVengeance(Player player) {
		if (player.getSkills().getLevel(Skills.MAGIC) < 94) {
			player.getPackets().sendGameMessage("Your Magic level is not high enough for this spell.");
			return;
		} else if (player.getSkills().getLevel(Skills.DEFENCE) < 40) {
			player.getPackets().sendGameMessage("You need a Defence level of 40 for this spell");
			return;
		}
		Long lastVeng = (Long) player.getTemporaryAttributtes().get("LAST_VENG");
		if (lastVeng != null && lastVeng + 30000 > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage("You may only cast vengeance once every 30 seconds.");
			return;
		}
		if (!Magic.checkRunes(player, true, Magic.ASTRAL_RUNE, 4, Magic.DEATH_RUNE, 2, Magic.EARTH_RUNE, 10))
			return;
		player.setNextGraphics(new Graphics(726, 0, 100));
		player.setNextAnimation(new Animation(4410));
		player.setCastVeng(true);
		player.getSkills().addXp(Skills.MAGIC, 112);
		player.getTemporaryAttributtes().put("LAST_VENG", Utils.currentTimeMillis());
	}
	
	public static void handleHumidify(Player player) {
		if (hasFillables(player)) {
			if (Magic.checkSpellRequirements(player, 68, true, Magic.ASTRAL_RUNE, 1, Magic.WATER_RUNE, 3, Magic.FIRE_RUNE, 1)) {
				player.setNextGraphics(new Graphics(1061));
				player.setNextAnimation(new Animation(6294));
				player.getSkills().addXp(Skills.MAGIC, 65);
				fillFillables(player);
			}
		} else {
			player.getPackets().sendGameMessage("You need to have something to humidify before using this spell.");
		}
	}
	
	public static void fillFillables(Player player) {
		for (Item item : player.getInventory().getItems().getItems()) {
			if (item == null)
				continue;
			Filler fill = Filler.forId((short) item.getId());
			if (fill != null) {
				if (player.getInventory().containsItem(fill.getEmptyItem().getId(), 1)) {
					player.getInventory().deleteItem(fill.getEmptyItem());
					player.getInventory().addItem(fill.getFilledItem());
				}
			}
		}
	}
	
	public static boolean hasFillables(Player player) {
		for (Item item : player.getInventory().getItems().getItems()) {
			if (item == null)
				continue;
			Filler fill = Filler.forId((short) item.getId());
			if (fill != null) {
				return true;
			}
		}
		return false;
	}

	public static void handleStringJewelry(Player player) {
		if (hasUnstrungs(player)) {
			if (Magic.checkSpellRequirements(player, 80, true, Magic.ASTRAL_RUNE, 2, Magic.EARTH_RUNE, 10, Magic.WATER_RUNE, 5)) {
				player.setNextGraphics(new Graphics(728, 0, 100));
				player.setNextAnimation(new Animation(4412));
				player.getSkills().addXp(Skills.MAGIC, 87);
				for (Item item : player.getInventory().getItems().getItems()) {
					if (item == null)
						continue;
					int strungId = getStrungIndex(item.getId());
					if (strungId != -1) {
						player.getInventory().deleteItem(item.getId(), 1);
						player.getInventory().addItem(strung[strungId], 1);
					}
				}
			}
		} else {
			player.getPackets().sendGameMessage("You need to have unstrung jewelry to cast this spell.");
		}
	}

	public static void handleRestorePotionShare(Player player, Item item) {
		// TODO Auto-generated method stub
		
	}

	public static void handleLeatherMake(Player player, Item item) {
		// TODO Auto-generated method stub
		
	}

	public static void handleBoostPotionShare(Player player, Item item) {
		// TODO Auto-generated method stub
		
	}

	public static void handleBakePie(Player player) {
		for (Cookables food : Cookables.values()) {
			if (food.toString().toLowerCase().contains("_pie")) {
				if (player.getSkills().getLevel(Skills.COOKING) < food.getLvl())
					continue;
				Item item = food.getRawItem();
				if (player.getInventory().containsItem(item.getId(), 1)) {
					for (int i = 0; i < player.getInventory().getAmountOf(item.getId()); i++) {
						if (!Magic.checkRunes(player, true, 9075, 1, 554, 5, 555, 4))
							return;
						player.lock(2);
						player.getInventory().replaceItem(food.getProduct().getId(), item.getAmount(), player.getInventory().getItems().getThisItemSlot(item.getId()));
						player.getSkills().addXp(Skills.MAGIC, 60);
						player.getSkills().addXp(Skills.COOKING, food.getXp());
						player.setNextAnimation(new Animation(4413));
						player.setNextGraphics(new Graphics(746));
						return;
					}
				}
			}
		}
		player.getPackets().sendGameMessage("You do not have any pie.");
		
	}

	public static void handleCureMe(Player player) {
		if (player.getPoison().isPoisoned()) {
			if (Magic.checkSpellRequirements(player, 71, true, Magic.ASTRAL_RUNE, 2, 564, 2)) {
				player.setNextGraphics(new Graphics(729, 0, 100));
				player.setNextAnimation(new Animation(4409));
				player.getSkills().addXp(Skills.MAGIC, 69);
				player.addPoisonImmune(1000);
			}
		} else {
			player.getPackets().sendGameMessage("You are not poisoned.");
		}
	}

	public static void handleHunterKit(Player player) {
		if (!player.canAlch())
			return;
		if (player.getActionManager().getActionDelay() <= 0 && Magic.checkSpellRequirements(player, 71, true, Magic.ASTRAL_RUNE, 2, 557, 2)) {
			player.getActionManager().addActionDelay(4);
			player.setNextGraphics(new Graphics(1074, 0, 100));
			player.setNextAnimation(new Animation(6303));
			player.setAlchDelay(1100L);
			player.getInventory().addItem(11159, 1);
		}
		
	}

	public static void handleCureGroup(Player player) {
		if (!player.canAlch())
			return;
		if (Magic.checkSpellRequirements(player, 74, true, Magic.ASTRAL_RUNE, 2, 564, 2)) {
			player.getActionManager().addActionDelay(4);
			player.setNextGraphics(new Graphics(729, 0, 100));
			player.setNextAnimation(new Animation(4411));
			player.addPoisonImmune(1000);
			player.setAlchDelay(1100L);
			for (Player other : getNearPlayers(player, 1, 10)) {
				if (other.getPoison().isPoisoned()) {
					player.setNextGraphics(new Graphics(729, 0, 100));
					player.addPoisonImmune(1000);
					player.getPackets().sendGameMessage("Your poison has been cured!");
				}
			}
		}
	}

	public static void handleSuperGlassMake(Player player) {
		if (!player.getInventory().containsItem(401, 1) ||
			!player.getInventory().containsItem(1783, 1)) {
			player.getPackets().sendGameMessage("You need seaweed and buckets of sand to make molten glass.");
			return;
		}
		if (Magic.checkSpellRequirements(player, 77, true, Magic.ASTRAL_RUNE, 2, Magic.FIRE_RUNE, 6, Magic.AIR_RUNE, 10)) {
			player.setNextGraphics(new Graphics(729, 0, 100));
			player.setNextAnimation(new Animation(4413));
			player.getSkills().addXp(Skills.MAGIC, 78);
			for(int i = 0;i < 15;i++) {
				if (player.getInventory().containsItem(401, 1) && player.getInventory().containsItem(1783, 1)) {
					player.getInventory().deleteItem(401, 1);
					player.getInventory().deleteItem(1783, 1);
					player.getInventory().addItem(1775, 1);
					//player.getInventory().addItem(Ectofuntus.EMPTY_BUCKET, 1);
				}
			}
		}
	}

	public static void handleRemoteFarm(Player player) {
		if (Magic.checkSpellRequirements(player, 78, true, Magic.ASTRAL_RUNE, 2, Magic.EARTH_RUNE, 2, Magic.NATURE_RUNE, 3)) {
			openRemoteFarm(player);
		}
	}

	public static void handleDream(Player player) {
		if (player.isUnderCombat()) {
			player.getPackets().sendGameMessage("You can't cast dream until 10 seconds after the end of combat.");
			return;
		} else if (player.getHitpoints() == player.getMaxHitpoints()) {
			player.getPackets().sendGameMessage("You have no need to cast this spell since your life points are already full.");
			return;
		} else if (!Magic.checkRunes(player, true, 9075, 2, 564, 1, 559, 5))
			return;
		player.getActionManager().setAction(new Action() {

			private boolean doneCycle;

			@Override
			public boolean start(Player player) {
				if (!process(player))
					return false;
				player.setNextAnimation(new Animation(6295));
				setActionDelay(player, 6);
				return true;
			}

			@Override
			public boolean process(Player player) {
				return player.getHitpoints() == player.getMaxHitpoints();
			}

			@Override
			public int processWithDelay(Player player) {
				if (!doneCycle) {
					doneCycle = !doneCycle;
				}
				player.setNextAnimation(new Animation(6296));
				player.setNextGraphics(new Graphics(277, 0, 80));
				return 3;
			}

			@Override
			public void stop(Player player) {
				setActionDelay(player, 1);
				player.setNextAnimation(new Animation(6297));// reset it.
				player.getEmotesManager().setNextEmoteEnd();
			}
		});
		
	}

	public static void handleMagicImbue(Player player) {
		if (Magic.checkSpellRequirements(player, 82, true, Magic.ASTRAL_RUNE, 2, Magic.FIRE_RUNE, 7, Magic.WATER_RUNE, 7)) {
			if (player.getInventory().containsItem(Magic.AIR_RUNE, 1) && player.getInventory().containsItem(Magic.WATER_RUNE, 1)) {
				player.getInventory().deleteItem(Magic.AIR_RUNE, 1);
				player.getInventory().deleteItem(Magic.WATER_RUNE, 1);
				player.getInventory().addItem(Magic.MIST_RUNE, 1);
			}
			if (player.getInventory().containsItem(Magic.AIR_RUNE, 1) && player.getInventory().containsItem(Magic.EARTH_RUNE, 1)) {
				player.getInventory().deleteItem(Magic.AIR_RUNE, 1);
				player.getInventory().deleteItem(Magic.EARTH_RUNE, 1);
				player.getInventory().addItem(Magic.DUST_RUNE, 1);
			}
			if (player.getInventory().containsItem(Magic.AIR_RUNE, 1) && player.getInventory().containsItem(Magic.EARTH_RUNE, 1)) {
				player.getInventory().deleteItem(Magic.EARTH_RUNE, 1);
				player.getInventory().deleteItem(Magic.WATER_RUNE, 1);
				player.getInventory().addItem(Magic.MUD_RUNE, 1);
			}
			if (player.getInventory().containsItem(Magic.AIR_RUNE, 1) && player.getInventory().containsItem(Magic.FIRE_RUNE, 1)) {
				player.getInventory().deleteItem(Magic.AIR_RUNE, 1);
				player.getInventory().deleteItem(Magic.FIRE_RUNE, 1);
				player.getInventory().addItem(Magic.SMOKE_RUNE, 1);
			}
			if (player.getInventory().containsItem(Magic.WATER_RUNE, 1) && player.getInventory().containsItem(Magic.FIRE_RUNE, 1)) {
				player.getInventory().deleteItem(Magic.WATER_RUNE, 1);
				player.getInventory().deleteItem(Magic.FIRE_RUNE, 1);
				player.getInventory().addItem(Magic.STEAM_RUNE, 1);
			}
			if (player.getInventory().containsItem(Magic.EARTH_RUNE, 1) && player.getInventory().containsItem(Magic.FIRE_RUNE, 1)) {
				player.getInventory().deleteItem(Magic.EARTH_RUNE, 1);
				player.getInventory().deleteItem(Magic.FIRE_RUNE, 1);
				player.getInventory().addItem(Magic.LAVA_RUNE, 1);
			}
		}
		
	}

	public static void handleDisruptionShield(Player player) {
		if (player.getSkills().getLevel(Skills.DEFENCE) < 40) {
			player.getPackets().sendGameMessage("You need a Defence level of 40 for this spell");
			return;
		}
		Long lastDis = (Long) player.getTemporaryAttributtes().get("LAST_DIS");
		if (lastDis != null && lastDis + 60000 > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage("Players may only cast disruption shield once every minute.");
			return;
		}
		if (Magic.checkSpellRequirements(player, 90, true, Magic.ASTRAL_RUNE, 3, Magic.BLOOD_RUNE, 3, Magic.BODY_RUNE, 10)) {
			player.setNextGraphics(new Graphics(1320, 0, 100));
			player.setNextAnimation(new Animation(8770));
			//player.addDisDelay(5000);
			player.getTemporaryAttributtes().put("LAST_DIS", Utils.currentTimeMillis());
			player.getPackets().sendGameMessage("You cast a Disruption Shield.");
		}
		
	}

	public static void handleGroupVengeance(Player player) {
		Long lastVeng = (Long) player.getTemporaryAttributtes().get("LAST_VENG");
		if (lastVeng != null && lastVeng + 30000 > Utils.currentTimeMillis())  {
			player.getPackets().sendGameMessage("You may only cast vengeance spells once every 30 seconds.");
			return;
		}
		if (Magic.checkSpellRequirements(player, 95, true, Magic.ASTRAL_RUNE, 4, Magic.DEATH_RUNE, 3, Magic.EARTH_RUNE, 11)) {
			player.setNextGraphics(new Graphics(725, 0, 100));
			player.setNextAnimation(new Animation(4411));
			player.setCastVeng(true);
			player.getSkills().addXp(Skills.MAGIC, 112);
			player.getTemporaryAttributtes().put("LAST_VENG", Utils.currentTimeMillis());
			for (Player other : getNearPlayers(player, 3, 10)) {
				Long otherVeng = (Long) other.getTemporaryAttributtes().get("LAST_VENG");
				if (otherVeng != null && otherVeng + 30000 > Utils.currentTimeMillis()) 
					continue;
				other.setNextGraphics(new Graphics(725, 0, 100));
				other.setCastVeng(true);
				other.getTemporaryAttributtes().put("LAST_VENG", Utils.currentTimeMillis());
			}
		}
	}

	public static void handleHealGroup(Player player) {
		// TODO Auto-generated method stub
		
	}

	public static void handleSpellbookSwap(Player player) {
		player.sm("Spell has been disbled since you can do it for free.");
		
	}
	
}
