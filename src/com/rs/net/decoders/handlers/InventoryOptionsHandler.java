package com.rs.net.decoders.handlers;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cores.CoresManager;
import com.rs.cores.WorldThread;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.item.MagicOnItem;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.npc.others.ConditionalDeath;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.ClueScrolls;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Equipment;
import com.rs.game.player.FarmingManager;
import com.rs.game.player.Inventory;
import com.rs.game.player.LendingManager;
import com.rs.game.player.PendantManager.Pendants;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.SlayerTabs.TaksTeleports;
import com.rs.game.player.achievements.impl.TheCreatorIAchievement;
import com.rs.game.player.achievements.impl.TheCreatorIIAchievement;
import com.rs.game.player.achievements.impl.TriskelionKeyAchievement;
import com.rs.game.player.actions.BoltTipFletching;
import com.rs.game.player.actions.BoltTipFletching.BoltTips;
import com.rs.game.player.actions.Firemaking;
import com.rs.game.player.actions.Fletching;
import com.rs.game.player.actions.Fletching.Fletch;
import com.rs.game.player.actions.FruitCutting.CuttableFruit;
import com.rs.game.player.actions.GemCutting;
import com.rs.game.player.actions.GemCutting.Gem;
import com.rs.game.player.actions.HerbCleaning;
import com.rs.game.player.actions.Herblore;
import com.rs.game.player.actions.LeatherCrafting;
import com.rs.game.player.actions.LeatherCrafting.LeatherData;
import com.rs.game.player.actions.crafting.CrystalGlassBlowing;
import com.rs.game.player.actions.crafting.Jewelry;
import com.rs.game.player.actions.crafting.SirenicScaleCrafting;
import com.rs.game.player.actions.crafting.TectonicEnergyCrafting;
import com.rs.game.player.actions.divination.DivinePlacing;
import com.rs.game.player.actions.divination.HarvestWisp;
import com.rs.game.player.actions.herblore.CombinationPotions;
import com.rs.game.player.actions.herblore.FlaskDecanting;
import com.rs.game.player.actions.herblore.PotionDecanting;
import com.rs.game.player.actions.hunter.TrapAction;
import com.rs.game.player.actions.slayer.SlayerHelmets;
import com.rs.game.player.actions.summoning.Summoning;
import com.rs.game.player.actions.summoning.Summoning.Pouches;
import com.rs.game.player.content.AncientEffigies;
//import com.rs.game.player.content.ArmourSets;
//import com.rs.game.player.content.ArmourSets.Sets;
import com.rs.game.player.content.Burying.Bone;
import com.rs.game.player.content.CosmeticsHandler;
import com.rs.game.player.content.Dicing;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.GodswordCreating;
import com.rs.game.player.content.HolidayEvents;
import com.rs.game.player.content.Hunter;
import com.rs.game.player.content.Hunter.FlyingEntities;
import com.rs.game.player.content.ItemCombining;
import com.rs.game.player.content.ItemCombining.Combinings;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.ItemConverter;
import com.rs.game.player.content.ItemConverter.Convertables;
import com.rs.game.player.content.ItemSets;
import com.rs.game.player.content.ItemUpgradeKits;
import com.rs.game.player.content.ItemUpgradeKits.Upgradables;
import com.rs.game.player.content.JewelryTransformation;
import com.rs.game.player.content.Lamps;
import com.rs.game.player.content.Lend;
import com.rs.game.player.content.LividFarmHandler;
import com.rs.game.player.content.PickAxeGilding;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.PrayerBooks;
import com.rs.game.player.content.PresentsManager;
import com.rs.game.player.content.RepairItems.BrokenItems;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.RunecraftingTeletabs.AltarLocations;
import com.rs.game.player.content.Scattering.Ash;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SkillingCrates.Crate;
import com.rs.game.player.content.SkillingUrnsManager;
import com.rs.game.player.content.SkillingUrnsManager.SkillingUrns;
import com.rs.game.player.content.Slayer;
import com.rs.game.player.content.SlayerMasks;
import com.rs.game.player.content.SlayerMasks.Masks;
import com.rs.game.player.content.ToyHorsey;
import com.rs.game.player.content.TreeSaplings;
import com.rs.game.player.content.WeaponPoison;
import com.rs.game.player.content.contracts.ContractHandler;
import com.rs.game.player.content.dungeoneering.DungFoods;
import com.rs.game.player.content.items.BirdNests;
import com.rs.game.player.content.items.CrystalItemCreation;
import com.rs.game.player.content.items.CrystalItemCreation.Tools;
import com.rs.game.player.content.items.Portables;
import com.rs.game.player.content.items.RareBossBoxes;
import com.rs.game.player.content.items.RareCosmeticBoxes;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.content.wildyrework.WildyHandler;
import com.rs.game.player.controlers.Barrows;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.SorceressGarden;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.dialogues.SqirkFruitSqueeze;
import com.rs.game.player.dialogues.SqirkFruitSqueeze.SqirkFruit;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.utils.Colors;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class InventoryOptionsHandler {

	public static void handleItemOption2(final Player player, final int slotId, final int itemId, Item item) {
		if (Firemaking.isFiremaking(player, itemId))
			return;
		CuttableFruit fruit = CuttableFruit.forId(item.getId());
		if (fruit != null) {
			player.getDialogueManager().startDialogue("FruitCuttingD", fruit);
		}
		// slayer gem
		if (itemId == 4155) {
			if (player.hasTask == false) {
				player.sm("You don't have a active slayer task, ask the slayer master for one.");
			} else {
				player.sm(" You need to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
			}
		}
		if (itemId == 31846) {
			if (player.getContract() == null)
				player.sendMessage("You don't have a contract.");
			else {
				String npcName = NPCDefinitions.getNPCDefinitions(player.getContract().getNpcId()).getName();
				player.sendMessage(Colors.orange + "You have " + player.getContract().getKillAmount() + " x " + npcName + "'s left to kill.");
			}
			return;
		}

		// quick slayertab teleport
		else if (itemId == 13723) {
			TaksTeleports.handleTeleport(player, item);
		} else if (itemId == 31612)
			player.sm("Your springcleaner has " + player.getPointsManager().springs + " charges left.");
		else if (itemId == 6583 || itemId == 7927) {
			JewelryTransformation.ringTransformation(player, itemId);
		} else if(itemId == 28550)
			Magic.sendNormalTeleportSpell(player, 1, 0, new WorldTile(2786,3585,0), null);
		if (itemId == 18338) {
			if (player.sapphires <= 0 && player.rubies <= 0 && player.emeralds <= 0 && player.diamonds <= 0) {
				player.sm("Your gem pouch is currently empty.");
			} else {
				player.getInventory().addItem(1622, player.emeralds);
				player.getInventory().addItem(1624, player.sapphires);
				player.getInventory().addItem(1620, player.rubies);
				player.getInventory().addItem(1619, player.diamonds);
				player.emeralds = 0;
				player.sapphires = 0;
				player.rubies = 0;
				player.diamonds = 0;
				player.sm("You have successfully emptied your gem bag.");
			}
		}

		if (itemId == 18339) {
			if (player.coal <= 0) {
				player.sm("Your coal pouch is currently empty.");
			} else {
				player.getInventory().addItem(453, 1);
				player.coal--;
				player.sm("You have successfully taken a piece of coal from your coal bag.");
			}
		}
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.emptyPouch(player, pouch);
			player.stopAll(false);
		} else if (itemId == 29981) {
			player.setHitpoints(Short.MAX_VALUE);
		} else if (itemId == 15262)
			ItemSets.openSkillPack(player, itemId, 12183, 5000, player.getInventory().getAmountOf(itemId));
		else if (itemId == 15362)
			ItemSets.openSkillPack(player, itemId, 230, 50, player.getInventory().getAmountOf(itemId));
		else if (itemId == 15363)
			ItemSets.openSkillPack(player, itemId, 228, 50, player.getInventory().getAmountOf(itemId));
		else if (itemId == 15364)
			ItemSets.openSkillPack(player, itemId, 222, 50, player.getInventory().getAmountOf(itemId));
		else if (itemId == 15365)
			ItemSets.openSkillPack(player, itemId, 9979, 50, player.getInventory().getAmountOf(itemId));
		else {
			if (player.isEquipDisabled())
				return;
			long passedTime = Utils.currentTimeMillis() - WorldThread.LAST_CYCLE_CTM;
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					List<Integer> slots = player.getSwitchItemCache();
					int[] slot = new int[slots.size()];
					for (int i = 0; i < slot.length; i++)
						slot[i] = slots.get(i);
					player.getSwitchItemCache().clear();
					ButtonHandler.sendWear(player, slot);
					player.stopAll(false, true, false);
				}
			}, passedTime >= 600 ? 0 : passedTime > 330 ? 1 : 0);
			if (player.getSwitchItemCache().contains(slotId))
				return;
			player.getSwitchItemCache().add(slotId);
		}
	}

	public static void dig(final Player player) {
		player.resetWalkSteps();
		player.setNextAnimation(new Animation(830));
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.unlock();
				if (Barrows.digIntoGrave(player))
					return;
				if (player.getX() == 3005 && player.getY() == 3376 || player.getX() == 2999 && player.getY() == 3375 || player.getX() == 2996 && player.getY() == 3377 || player.getX() == 2989 && player.getY() == 3378 || player.getX() == 2987 && player.getY() == 3387 || player.getX() == 2984 && player.getY() == 3387) {
					// mole
					player.setNextWorldTile(new WorldTile(1752, 5137, 0));
					player.getPackets().sendGameMessage("You seem to have dropped down into a network of mole tunnels.");
					return;

				}
				if (player.getX() >= 2747 && player.getX() <= 2750 && player.getY() >= 3733 && player.getY() <= 3736) {
					// Dungeon brine rats
					player.setNextWorldTile(new WorldTile(2697, 10120, 0));
					player.getPackets().sendGameMessage("You seem to have dropped down into a strange cave.");
					return;

				}
				if (ClueScrolls.digSpot(player)) {
					return;
				}
				player.getPackets().sendGameMessage("You find nothing.");
			}

		});
		Logger.log("ItemHandler 2", "option 2 clicked");
	}

	public static void handleItemOption1(final Player player, final int slotId, final int itemId, Item item) {
		for (int i : ClueScrolls.ScrollIds) {
			if (itemId == i) {
				if (ClueScrolls.Scrolls.getMap(itemId) != null) {
					ClueScrolls.showMap(player, ClueScrolls.Scrolls.getMap(itemId));
					return;
				}
				if (ClueScrolls.Scrolls.getObjMap(itemId) != null) {
					ClueScrolls.showObjectMap(player, ClueScrolls.Scrolls.getObjMap(itemId));
					return;
				}
				if (ClueScrolls.Scrolls.getRiddles(itemId) != null) {
					ClueScrolls.showRiddle(player, ClueScrolls.Scrolls.getRiddles(itemId));
					return;
				}
			}

		}
		if (itemId == 15707) {
			player.getDungManager().openPartyInterface();
		}
		else if (itemId == 24952)
			player.getDialogueManager().startDialogue("ExpToken");
		else if (itemId == 31846) {
			player.getDialogueManager().startDialogue("GrimGem");
		}

		else if (itemId == 30915) {
			if (!player.getInventory().containsItem(30915, 1)) // you never know
				return;
			int amount = player.getInventory().getAmountOf(30915);
			player.getInventory().deleteItem(30915, amount);
			player.setSilverhawkFeathers(player.getSilverhawkFeathers() + amount);
			player.sm("You added " + amount + " charges to your boots, you now have a total of " + player.getSilverhawkFeathers() + " charges.");
		} else if (itemId == 32662)
			player.getDialogueManager().startDialogue("SimpleMessage", "This dust can be used to upgrade skilling tools.");
		else if (itemId == 20429)
			WildyHandler.handleShred(player);
		else if (itemId == 407) {
			player.getInventory().deleteItem(407, 1);
			player.getInventory().addItem(411, 1);
		}
		 else if (itemId == 985 || itemId == 987) {
			if (player.getInventory().containsItem(985, 1) && player.getInventory().containsItem(987, 1)) {
				player.getInventory().deleteItem(985, 1);
				player.getInventory().deleteItem(987, 1);
				player.getInventory().addItem(989, 1);
				player.sendMessage("You succesfully make a crytal key.");
			} else
				player.sendMessage("You need both parts to make this key.");
		}
		Masks mask = Masks.forId(item.getId());
		if (mask != null) {
			player.setNextForceTalk(new ForceTalk(SlayerMasks.getKillsDone(mask, player)));
			return;
		}
		Masks helm = Masks.forHelm(item.getId());
		if (helm != null) {
			player.setNextForceTalk(new ForceTalk(SlayerMasks.getKillsDone(helm, player)));
			return;
		}

		Pouches pouches = Pouches.forId(itemId);
		if (pouches != null)
			Summoning.spawnFamiliar(player, pouches);
		if (itemId == 2700 || itemId == 13080 || itemId == 13010 || itemId == 19064) {
			if (!player.finishedClue) {
				if (itemId == 2700)
					player.clueLevel = 0;
				else if (itemId == 13080)
					player.clueLevel = 1;
				else if (itemId == 13010)
					player.clueLevel = 2;
				else if (itemId == 19064)
					player.clueLevel = 3;
				player.getInventory().deleteItem(itemId, 1);
				player.getInventory().addItem(2717, 1);
				player.clueChance = 0;
				player.finishedClue = true;
			} else {
				player.sm("You must finish your current clue in order to start a new one.");
			}
			return;
		} else if (itemId == 2717) {
			ClueScrolls.giveReward(player);
		}
		// charm upgrades
		else if (itemId == 30831)
			HolidayEvents.sendLoveNote(player);
		// spirit gems
		else if (itemId == 30453) {
			player.sm("Your spirit sapphire has " + player.getSapphireCharges() + "/10 charges left.");
		} else if (itemId == 30454) {
			player.sm("Your spirit emerald has " + player.getEmeraldCharges() + "/20 charges left.");
		} else if (itemId == 30455) {
			player.sm("Your spirit ruby has " + player.getRubyCharges() + "/30 charges left.");
		} else if (itemId == 30456) {
			player.sm("Your spirit diamond has " + player.getDiamondCharges() + "/40 charges left.");
		} else if(itemId == 28547 ||itemId == 28548 || itemId == 28549){
			if(player.getInventory().contains(28547) && player.getInventory().contains(28548) && player.getInventory().contains(28549)){
				player.getInventory().addItem(28550,1);
				player.getInventory().deleteItem(28547,1);
				player.getInventory().deleteItem(28548,1);
				player.getInventory().deleteItem(28549,1);
				player.sm("You succesfully assembled the key.");
				player.getAchievementManager().notifyUpdate(TriskelionKeyAchievement.class);
			} else {
				player.sm("You need 3 parts to assemble this key.");
			}
		}
		if (itemId == 30457) {
			player.sm("Your spirit dragonstone has " + player.getDragonstoneCharges() + "/50 charges left.");
		}
		if (itemId == 30458) {
			player.sm("Your spirit onyx has " + player.getOnyxCharges() + "/60 charges left.");
		} else if (itemId == 30139 || itemId == 30140 || itemId == 30141 || itemId == 30142 || itemId == 30143 || itemId == 30144) {
			if (player.getSpiritGemCharges() > 0) {
				player.sm("You can only have one active spirit gem.");
			} else {
				player.getInventory().deleteItem(itemId, 1);
				player.getInventory().addItem(itemId + 314, 1);
				player.setSpiritGemCharges(itemId);
			}
		} else if (itemId == 30915 && (player.getInventory().containsItem(30920, 1) || player.getEquipment().getBootsId() == 30920)) {
			int addAmount = player.getInventory().getAmountOf(30915);
			player.getInventory().deleteItem(30915, addAmount);
			player.setSilverhawkFeathers(player.getSilverhawkFeathers() + addAmount);
			player.sm("You have succesfully added the feathers, you now have a total of " + player.getSilverhawkFeathers() + " charges on your silverhawk boots.");
		} else if (itemId == 31613) {
			int addAmount = player.getInventory().getAmountOf(31612);
			player.getInventory().deleteItem(31612, addAmount);
			player.getPointsManager().springs += addAmount;
			player.sm("You have succesfully added the springs, you now have a total of " + player.getPointsManager().springs + " springs.");
		}
		Ash ash = Ash.forId(itemId);
		if (ash != null) {
			Ash.scatter(player, slotId);
			return;
		}

		Crate crate = Crate.forCrateId(itemId);
		if (crate != null) {
			crate.open(player, item);
			return;
		}

		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (Foods.eat(player, item, slotId))
			return;
		if (DungFoods.eat(player, item, slotId))
			return;
		if (itemId >= 15086 && itemId <= 15100) {
			Dicing.handleRoll(player, itemId, false);
			return;
		} else if (itemId == 29441) {
			// check food bag
			player.isInFoodBag = true;
			player.getFoodBag().open();
		} else if (itemId == 13723)
			TaksTeleports.handleTeleport(player, item);
		else if (itemId == 27996)
			player.getDialogueManager().startDialogue("CharmingImp", 17082, 9827);
		else if (itemId == 24952)
			player.getDialogueManager().startDialogue("ExpToken");
		 else if(itemId == RareBossBoxes.BOX)
				player.getDialogueManager().startDialogue("RareBossBoxesD");
			else if(itemId == RareCosmeticBoxes.BOX_ID)
				RareCosmeticBoxes.openBox(player);
		else if (itemId == 31846) {
			player.getDialogueManager().startDialogue("GrimGem");
		} else if (itemId == 31188)
			player.getDialogueManager().startDialogue("SeediciderD");
		else if (itemId == 28436) 
			player.getDialogueManager().startDialogue("AscensionBoltsD");
		else if (itemId == 34927) 
				player.getDialogueManager().startDialogue("BossLootBoxesD", 1);
		 else if (itemId == 34928) {
			player.getDialogueManager().startDialogue("BossLootBoxesD", 2);
		} else if (itemId == 34528)
			player.getDialogueManager().startDialogue("ProteanLogD");
		else if (itemId == 14464)
			player.getDialogueManager().startDialogue("SuperSaradominD");
		else if (itemId == 22451) {// gano crafting
			player.getDialogueManager().startDialogue("GanocraftingD");
		}else if (itemId == 22450) {// gano crafting
			player.getDialogueManager().startDialogue("GrifoCraftingD");
		}
		// Location crystal
		else if (itemId == 6948)
			player.getDialogueManager().startDialogue("Orb");
		else if (itemId == 15484) {
			player.getInterfaceManager().gazeOrbOfOculus();
			return;
		}
		// money bags
		else if (itemId == 27153) {
			int amount = Utils.random(50000, 100000);
			player.getDialogueManager().startDialogue("SimpleItemMessage", new Item(995, amount), "You received " + amount + " gold from the bag.");
			player.getInventory().addItem(995, amount);
			player.getInventory().deleteItem(itemId, 1);
		} else if (itemId == 27154) {
			int amount = Utils.random(100000, 300000);
			player.getDialogueManager().startDialogue("SimpleItemMessage", new Item(995, amount), "You received " + amount + " gold from the bag.");
			player.getInventory().addItem(995, amount);
			player.getInventory().deleteItem(itemId, 1);
		} else if (itemId == 27155) {
			int amount = Utils.random(300000, 1000000);
			player.getDialogueManager().startDialogue("SimpleItemMessage", new Item(995, amount), "You received " + amount + " gold from the bag.");
			player.getInventory().addItem(995, amount);
			player.getInventory().deleteItem(itemId, 1);
		} else if (itemId == 28311) {
			player.getFarmingManager().handelWishingWellFruit(item);
		} else if(itemId == 29492)
			player.getDialogueManager().startDialogue("BondD");
		 else if (itemId == 26358)
			player.getPorts().openNoticeboard();
		// Power crystals
		if (itemId == 744) {
			player.getDialogueManager().startDialogue("PowerCrystal");
		} /*
			 * if (itemId == 31770) { NPC npc = World.spawnNPC(16071, player,
			 * -1, true); npc.getCombat().setTarget(player);
			 * player.getInventory().deleteItem(31770,1); }/*if (itemId ==
			 * 31771) { NPC npc = World.spawnNPC(16076, player, -1, true);
			 * npc.getCombat().setTarget(player);
			 * player.getInventory().deleteItem(31771,1); }
			 *//*
			 * if (itemId == 31772) { //magic NPC npc = World.spawnNPC(16713,
			 * player, -1, true); npc.getCombat().setTarget(player);
			 * player.getInventory().deleteItem(31772,1); }
			 */

		// depo bank
		else if (item.getId() == 25205) {
			World.spawnTemporaryObject(new WorldObject(26969, 10, 0, player.getX(), player.getY(), player.getPlane()), 360000);
			player.getInventory().deleteItem(25205, 1);
		}
		/**
		 * blood neck fragments
		 **/
		else if (item.getId() == 18653 || item.getId() == 18654 || item.getId() == 18655 || item.getId() == 18656 || item.getId() == 18657 || item.getId() == 18658 || item.getId() == 18659) {
			if (player.getInventory().containsItem(18656, 1) && player.getInventory().containsItem(18653, 1) && player.getInventory().containsItem(18654, 1) && player.getInventory().containsItem(18655, 1) && player.getInventory().containsItem(18657, 1) && player.getInventory().containsItem(18658, 1) && player.getInventory().containsItem(18659, 1)) {
				player.getInventory().deleteItem(18653, 1);
				player.getInventory().deleteItem(18654, 1);
				player.getInventory().deleteItem(18655, 1);
				player.getInventory().deleteItem(18656, 1);
				player.getInventory().deleteItem(18657, 1);
				player.getInventory().deleteItem(18658, 1);
				player.getInventory().deleteItem(18659, 1);
				player.getInventory().addItem(17291, 1);
				player.sm("You created a blood Necklace!");
			} else {
				player.sm("You need al 7 pieces to create a bloodneck.");
			}
		}
		// DIVINATION BLOON CREATEION
		else if (itemId == 29313) {
			HarvestWisp.createboon(player, 29313, 100, 29373, 6, 10);
		} else if (itemId == 29190) {
			HarvestWisp.createboon(player, 29190, 300, 29374, 10, 20);
		} else if (itemId == 29315) {
			HarvestWisp.createboon(player, 29315, 500, 29375, 14, 30);
		} else if (itemId == 29316) {
			HarvestWisp.createboon(player, 29316, 600, 29376, 18, 40);
		} else if (itemId == 29193) {
			HarvestWisp.createboon(player, 29193, 800, 29377, 22, 50);
		}
		if (itemId == 29194) {
			HarvestWisp.createboon(player, 29194, 1000, 29378, 26, 60);
		}
		if (itemId == 29195) {
			HarvestWisp.createboon(player, 29195, 1250, 29379, 30, 70);
		}
		if (itemId == 29196) {
			HarvestWisp.createboon(player, 29196, 1500, 29380, 34, 80);
		}
		if (itemId == 29197) {
			HarvestWisp.createboon(player, 29197, 1750, 29381, 38, 85);
		}
		if (itemId == 29198) {
			HarvestWisp.createboon(player, 29198, 2000, 29382, 42, 90);
		}
		if (itemId == 29323) {
			HarvestWisp.createboon(player, 29323, 2250, 29383, 46, 95);
		}
		if (itemId == 29324) {
			HarvestWisp.createboon(player, 29324, 2250, 29383, 46, 95);
		}
		if (itemId == 4613) {
			player.setNextAnimation(new Animation(1902));
			player.spinTimer = 8;
			World.spinPlate(player);
		} else if (itemId == 20702) {
			if (player.getControlerManager().getControler() instanceof LividFarmHandler) {
				LividFarmHandler.makePlank(player);
			} else {
				player.sm("You can only do this is livid farm.");
			}
		}
		if (itemId == 20121 || itemId == 20122 || itemId == 20123 || itemId == 20124) {
			if (player.getInventory().containsItem(20121, 1) && player.getInventory().containsItem(20122, 1) && player.getInventory().containsItem(20123, 1) && player.getInventory().containsItem(20124, 1)) {
				player.getInventory().deleteItem(20121, 1);
				player.getInventory().deleteItem(20122, 1);
				player.getInventory().deleteItem(20123, 1);
				player.getInventory().deleteItem(20124, 1);
				player.getInventory().addItem(20120, 1);
				player.getPackets().sendGameMessage("You place the parts together to create a Frozen Key.");
			} else {
				player.getPackets().sendGameMessage("You need all four parts to create the key.");
			}
		}
		if (itemId == 7509) {// rock cake
			if (player.getControlerManager().getControler() instanceof DuelArena || player.getControlerManager().getControler() instanceof DuelControler) {
				player.out("You cannot use this here.");
				return;
			} else {
				player.applyHit(new Hit(player, 10, HitLook.REGULAR_DAMAGE));
				player.setNextForceTalk(new ForceTalk("Ow! I nearly broke a tooth!"));
			}
			return;
		}
		if (itemId == 10952) {
			if (Slayer.isUsingBell(player))
				return;
		} else if (itemId == 12844)
			player.setNextAnimation(new Animation(8990, 0));
		else if (itemId == 6199) {
			Random r = new Random();
			int RandomItems[] = { 10548, 10551, 4151, 6655, 6656, 6654, 10055, 10057, 10059, 10061, 10063, 10065, 10067 };
			// int[] RandomItems = {6654, 6655, 6656, 10053, 10055, 10057,
			// 10059, 10061, 10063, 10065, 10067, 6180, 6181, 6182, 6188, 6184,
			// 6185, 6186, 6187, 3057, 3058, 3059, 3060, 3061, 8950, 2997, };
			// //Other ids go in there as well
			player.getInventory().deleteItem(6199, 1);
			player.getInventory().addItem(RandomItems[r.nextInt(RandomItems.length)], 1);
			// World.sendWorldMessage(Utils.formatPlayerNameForDisplay(player.getUsername())
			// + " <img=3><shad=7401DF> has just opened a godwars box</shad>
			// <img=3>", false);
			player.getPackets().sendGameMessage("You've recieved an item from the Mystery Box!");
			return;
		}

		if (itemId == 18768) {
			Random t = new Random();
			Random a = new Random();
			int RandomItems[] = { 1514, 1516, 537, 15271, 384, 3001, 218, 2482, 2999, 220, 208, 454, 452, 450 };
			int RandomAmount[] = { 25, 50, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400 };

			if (player.setskillbox == 10) {
				player.getInventory().deleteItem(18768, 1);
				player.getInventory().addItem(RandomItems[t.nextInt(RandomItems.length)], RandomAmount[a.nextInt(RandomAmount.length)]);
				player.getPackets().sendGameMessage("You've recieved a reward from the Mystery Box!");
				player.getInventory().deleteItem(18768, 1);
				player.setskillbox = 0;
			} else if (player.setskillbox == 100) {
				Random b = new Random();
				int RandomItemss[] = { 4151, 6585, 6737, 6735, 6733, 6731, 11732, 4716, 4720, 4722, 4718, 4732, 4736, 4738, 4734, 4708, 4712, 4714, 4710, 4745, 4749, 4751, 4747, 4724, 4728, 4730, 4726, 4753, 4757, 4759, 4755, 10828, 1128, 1080, 4132, 4588, 5680, 4087, 4585, 1149, 1187, 6569, 6524 };
				player.getInventory().deleteItem(18768, 1);
				player.getInventory().addItem(RandomItemss[b.nextInt(RandomItemss.length)], 1);
				player.getPackets().sendGameMessage("You've recieved a reward from the Mystery Box!");
				player.setskillbox = 0;
			}
			return;
		}
		/*
		 * int randomnesttt = Utils.getRandom(34); int[] randomnestt = {14589,
		 * 5100,5099,5098, 5097,5096, 5321, 5323, 5320, 5322, 5324, 5319, 5318,
		 * 5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298, 14870, 5299, 5300,
		 * 5301, 5302, 5303, 5304, 21621 , 5374, 5373, 5372, 5371, 5370}; int
		 * randomnestID = randomnestt[randomnesttt]; if (itemId == 5070) {
		 * //player.sendMessage("Test"); player.getInventory().deleteItem(5070,
		 * 1); player.getInventory().addItem(randomnestID, 3); }
		 */
		if (itemId == 2520 || itemId == 2522 || itemId == 2524 || itemId == 2526) {
			ToyHorsey.play(player);
		}

		if (itemId == 6950) {
			player.getDialogueManager().startDialogue("LividOrb");
		}
		if (itemId == 18336) {
			player.sm("Keep this in your inventory while farming for a chance to recieve seeds back.");
		}
		if (itemId == 19670) {
			player.sm("Keep this in your inventory while smithing for a chance on recieving an extra bar.");
		}
		if (itemId == 19890) {
			player.sm("Keep this in your inventory while doing herblore for a chance of not using your secondary.");
		}
		if (itemId == 15262) {
			if (!player.getInventory().containsOneItem(12183) && !player.getInventory().hasFreeSlots()) {
				player.getPackets().sendGameMessage("You don't have enough space in your inventory.");
				return;
			}
			player.getInventory().deleteItem(15262, 1);
			player.getInventory().addItem(12183, 5000);
		}

		if (itemId == 771) {// Dramen branch
			player.getInventory().deleteItem(771, 1);
			player.getInventory().addItem(772, 1);
			player.getInventory().refresh();
			return;
		}
		if (itemId == 18338) {
			player.sm("You currently have:");
			player.sm("" + player.sapphires + "xSapphires");
			player.sm("" + player.emeralds + "xEmeralds");
			player.sm("" + player.rubies + "xRubies");
			player.sm("" + player.diamonds + "xDiamonds");
		}
		if (itemId == 18339) {
			player.sm("You currently have " + player.coal + " pieces of coal in your coal bag.");
		}
		if (itemId == 6542)
			PresentsManager.Presents(player);
		if (Pots.pot(player, item, slotId))
			return;
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.fillPouch(player, pouch);
			return;
		}
		/*
		 * if (itemId == 22370) { Summoning.openDreadnipInterface(player); }
		 */
		if (itemId == 11949) { // GLOBE SNOWGLOBE
			player.lock(3);
			World.spawnObject(new WorldObject(28297, 10, 0, player.getX(), player.getY(), player.getPlane()), true);
			player.setNextAnimation(new Animation(1745));
			player.getInterfaceManager().sendInterface(659);
			player.getInventory().addItem(11951, 27);
			player.sm("The snow globe fills your inventory with snow!");
		}

		if (itemId == 1535 || itemId == 1536 || itemId == 1537) { // Map Pieces
			if (player.getInventory().containsItem(1535, 1) && player.getInventory().containsItem(1536, 1) && player.getInventory().containsItem(1537, 1)) {
				player.getInventory().deleteItem(1535, 1);
				player.getInventory().deleteItem(1536, 1);
				player.getInventory().deleteItem(1537, 1);
				player.getInventory().addItem(1538, 1);
				player.sm("You fit the pieces together and make map!");
			} else {
				player.sm("You need all three pieces to create a map!");
			}
		}

		if (itemId == 952) {// spade
			dig(player);
			return;
		}
		if (itemId == 6) // Cannon
			player.getDwarfCannon().checkLocation();
		if (itemId == 20494) {// Gold Cannon
			player.getDwarfCannon().checkGoldLocation();
		}
		if (itemId == 20498) {// Royal Cannon

			player.getDwarfCannon().checkRoyalLocation();
		}
		if (HerbCleaning.clean(player, item, slotId))
			return;
		Bone bone = Bone.forId(itemId);
		if (bone != null) {
			Bone.bury(player, slotId);
			return;
		}

		if (itemId == 28627) {
			player.getDialogueManager().startDialogue("TectonicEnergyCraftingD", null, TectonicEnergyCrafting.ARMOUR);
			return;
		}
		if (itemId == 29863) {
			player.getDialogueManager().startDialogue("SirenicScaleCraftingD", null, SirenicScaleCrafting.ARMOUR);
			return;
		}
		if (itemId == 32843) {
			player.getDialogueManager().startDialogue("CombinationPotionsD", null, CombinationPotions.POTS);
			return;
		}
		/*
		 * flask making
		 */
		if (itemId == 23193 || itemId == 32845) {
			player.getActionManager().setAction(new CrystalGlassBlowing());
			return;
		}
		AltarLocations altar = AltarLocations.forId(itemId);
		if (altar != null) {
			AltarLocations.Teleport(player, slotId);
			return;
		}
		if (itemId == 4597)
			player.sm("Let's not be nosey and read Santa's note...");
		else if (itemId == 31453)
			player.sm("This item will collect all the coin drops for you.");
		else if (itemId == 31612)
			player.sm("This item will convert every armour drop you get into bars, from mithril to runite.");
		else if (itemId == 31924)
			player.sm("This item will collect all Power token drops for you.");
		else if (itemId == 299) {
			// FlowerGame.plant(player);
		}
		if (itemId == 19832) {
			player.setNextWorldTile(new WorldTile(3048, 5837, 1));
		}
		if (Magic.useTabTeleport(player, itemId))
			return;
		if (itemId == AncientEffigies.SATED_ANCIENT_EFFIGY || itemId == AncientEffigies.GORGED_ANCIENT_EFFIGY || itemId == AncientEffigies.NOURISHED_ANCIENT_EFFIGY || itemId == AncientEffigies.STARVED_ANCIENT_EFFIGY)
			player.getDialogueManager().startDialogue("AncientEffigiesD", itemId);
		else if (itemId == 4251)
			player.getEctophial().ProcessTeleportation(player);
		/**
		 * Glassblowing
		 **/
		else if (itemId == 1775) {
			if (!player.getInventory().containsItemToolBelt(1785, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a blowpipe to complete the action.", 1785);
				return;
			}
			player.getDialogueManager().startDialogue("GlassblowingD");
			return;
		}
		/**
		 * Protean hides
		 **/
		else if (itemId == 33740) {
			if (!player.getInventory().containsItemToolBelt(1733, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a needle to complete the action.", 1733);
				return;
			} else
				player.getDialogueManager().startDialogue("LeatherCraftingD", LeatherData.forId(33741));
			return;

		}
		/**
		 * Noxious item crafting
		 */
		if (item.getId() == 31718 || item.getId() == 31719 || item.getId() == 31720) { // leg
			if (player.getInventory().containsItem(31718, 1) && player.getInventory().containsItem(31719, 1) && player.getInventory().containsItem(31720, 1)) {
				player.getInventory().addItem(31721, 1);
				player.getInventory().deleteItem(31718, 1);
				player.getInventory().deleteItem(31719, 1);
				player.getInventory().deleteItem(31720, 1);
				player.getDialogueManager().startDialogue("ItemMessage", "You succesfully created a spider leg!", 31721);
			} else {
				player.getDialogueManager().startDialogue("ItemMessage", "You need the top,middle and bottom to create a leg.", 31721);
			}
			return;
		}
		if (item.getId() == 31722) { // fang
			if (player.getInventory().containsItem(31722, 1) && player.getInventory().containsItem(31721, 1)) {
				player.getInventory().addItem(31725, 1);
				player.getInventory().deleteItem(31722, 1);
				player.getInventory().deleteItem(31721, 1);
				player.getDialogueManager().startDialogue("ItemMessage", "You successfully created a noxious scythe!", 31721);
			} else {
				player.getDialogueManager().startDialogue("ItemMessage", "You need another a spider leg to complete this item.", 31721);
			}
			return;
		}
		if (item.getId() == 31723) { // eye
			if (player.getInventory().containsItem(31723, 1) && player.getInventory().containsItem(31721, 1)) {
				player.getInventory().addItem(31729, 1);
				player.getInventory().deleteItem(31723, 1);
				player.getInventory().deleteItem(31721, 1);
				player.getDialogueManager().startDialogue("ItemMessage", "You successfully created a noxious staff!", 31729);
			} else {
				player.getDialogueManager().startDialogue("ItemMessage", "You need another a spider leg to complete this item.", 31721);
			}
			return;
		}
		if (item.getId() == 31724) { // web
			if (player.getInventory().containsItem(31724, 1) && player.getInventory().containsItem(31721, 1)) {
				player.getInventory().addItem(31733, 1);
				player.getInventory().deleteItem(31724, 1);
				player.getInventory().deleteItem(31721, 1);
				player.getDialogueManager().startDialogue("ItemMessage", "You successfully created a noxious longbow", 31721);
			} else {
				player.getDialogueManager().startDialogue("ItemMessage", "You need another a spider leg to complete this item.", 31721);
			}
			return;
		}
		/**
		 * gem crafting
		 */
		if (itemId == Gem.OPAL.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.OPAL);
			return;
		}
		if (itemId == Gem.JADE.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.JADE);
			return;
		}
		if (itemId == Gem.RED_TOPAZ.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.RED_TOPAZ);
			return;
		}
		if (itemId == Gem.SAPPHIRE.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.SAPPHIRE);
			return;
		}
		if (itemId == Gem.EMERALD.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.EMERALD);
			return;
		}
		if (itemId == Gem.RUBY.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.RUBY);
			return;
		}
		if (itemId == Gem.DIAMOND.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.DIAMOND);
			return;
		}
		if (itemId == Gem.DRAGONSTONE.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.DRAGONSTONE);
			return;
		}
		if (itemId == Gem.ONYX.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.ONYX);
			return;
		}
		if (itemId == Gem.HYDRIX.getUncut()) {
			if (!player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
				return;
			}
			GemCutting.cut(player, Gem.HYDRIX);
			return;
		}
		/**
		 * Fletching
		 */

		 Fletch fletch3 = Fletching.isFletching( new Item(Fletching.BOW_STRING),(
				 player.getInventory().contains(70) ? new Item(70) : 
					 player.getInventory().contains(70) ?  new Item(72) :
						 player.getInventory().contains(67) ?  new Item(67) :
							 player.getInventory().contains(66) ?  new Item(66) :
									 player.getInventory().contains(62) ?  new Item(62) :
										 player.getInventory().contains(64) ?  new Item(64) :new Item(0)
							 ));
			if (fletch3 != null) {
				player.getDialogueManager().startDialogue("FletchingD", fletch3);
				return;
		}
		Fletch fletch4 = Fletching.isFletching(item, new Item(Fletching.BOW_STRING));
				if (fletch4 != null) {
					player.getDialogueManager().startDialogue("FletchingD", fletch4);
					return;
			}
		Fletch fletch = Fletching.isFletching(item, new Item(946));
		if (fletch != null) {
			if (player.getInventory().containsItemToolBelt(946, 1)) {
				player.getDialogueManager().startDialogue("FletchingD", fletch);
				return;
			} else {
				player.getDialogueManager().startDialogue("ItemMessage", "You need a knife to complete the action.", 946);
			}
		}
		Fletch fletch2 = Fletching.isFletching(item, new Item(1755));
		if (fletch2 != null) {
			if (player.getInventory().containsItemToolBelt(1755, 1)) {
				player.getDialogueManager().startDialogue("FletchingD", fletch2);
				return;
			} else
				player.getDialogueManager().startDialogue("ItemMessage", "You need a chisle to complete the action.", 1755);
		}
		/**
		 * slayers
		 */
		if (itemId == 4155)
			player.getDialogueManager().startDialogue("EnchantedGemDialouge", player.getSlayerManager().getCurrentMaster().getNPCId());
		else if (itemId == 14057) // broomstick
			player.setNextAnimation(new Animation(10532));
		else if (itemId == SqirkFruitSqueeze.SqirkFruit.AUTUMM.getFruitId())
			player.getDialogueManager().startDialogue("SqirkFruitSqueeze", SqirkFruit.AUTUMM);
		else if (itemId == SqirkFruitSqueeze.SqirkFruit.SPRING.getFruitId())
			player.getDialogueManager().startDialogue("SqirkFruitSqueeze", SqirkFruit.SPRING);
		else if (itemId == SqirkFruitSqueeze.SqirkFruit.SUMMER.getFruitId())
			player.getDialogueManager().startDialogue("SqirkFruitSqueeze", SqirkFruit.SUMMER);
		else if (itemId == SqirkFruitSqueeze.SqirkFruit.WINTER.getFruitId())
			player.getDialogueManager().startDialogue("SqirkFruitSqueeze", SqirkFruit.WINTER);
		else if (itemId == 15262)
			ItemSets.openSkillPack(player, itemId, 12183, 5000, 1);
		else if (itemId == 15362)
			ItemSets.openSkillPack(player, itemId, 230, 50, 1);
		else if (itemId == 15363)
			ItemSets.openSkillPack(player, itemId, 228, 50, 1);
		else if (itemId == 15364)
			ItemSets.openSkillPack(player, itemId, 222, 50, 1);
		else if (itemId == 15365)
			ItemSets.openSkillPack(player, itemId, 9979, 50, 1);
		else if (itemId == 1917) {
			player.getPackets().sendGameMessage("You Drink the Beer.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1917, 1);

		}

		// armadyl orb creatin
		else if (itemId == 21776) {
			if (player.getSkills().getLevel(Skills.CRAFTING) < 77) {
				player.getPackets().sendGameMessage("You need a Crafting level of at least 77 in order to combine the shards.");
				return;
			} else if (player.getInventory().containsItem(itemId, 100)) {
				player.setNextAnimation(new Animation(713));
				player.setNextGraphics(new Graphics(1383));
				player.getInventory().deleteItem(new Item(itemId, 100));
				player.getInventory().addItem(new Item(21775, 1));
				player.getSkills().addXp(Skills.CRAFTING, 150);
				player.getPackets().sendGameMessage("You combine the shards into an orb.");
			} else
				player.getPackets().sendGameMessage("You need at least 100 shards in order to create an orb of armadyl.");
		}
		// dragonbone upgrade kit info option
		else if (itemId == 24352) {
			player.getDialogueManager().startDialogue("DragonBoneUpgradeKiteInfoD");
		} else if (Herblore.isRawIngredient(player, itemId))
			return;
		// DIVINATION \\
		// mining \\

		else if (itemId == 29294) {
			DivinePlacing.placeDivine(player, itemId, 87285, 34107, 1, 14);
			player.getPackets().sendGameMessage("You spawn a divine bronze rock.");
		} else if (itemId == 29295) {
			player.getPackets().sendGameMessage("You spawn a divine iron rock.");
			DivinePlacing.placeDivine(player, itemId, 87286, 57572, 15, 14);
		} else if (itemId == 29296) {
			DivinePlacing.placeDivine(player, itemId, 87287, 87266, 30, 14);
			player.getPackets().sendGameMessage("You spawn a divine coal rock.");
		} else if (itemId == 29297) {
			DivinePlacing.placeDivine(player, itemId, 87288, 87267, 55, 14);
			player.getPackets().sendGameMessage("You spawn a divine mithril rock.");
		} else if (itemId == 29298) {
			DivinePlacing.placeDivine(player, itemId, 87289, 87268, 70, 14);
			player.getPackets().sendGameMessage("You spawn a divine adamantite rock.");
		} else if (itemId == 29299) {
			DivinePlacing.placeDivine(player, itemId, 87290, 87269, 85, 14);
			player.getPackets().sendGameMessage("You spawn a divine runite rock.");
		}
		// divine trees
		else if (itemId == 29304) {
			DivinePlacing.placeDivine(player, itemId, 87295, 87274, 1, 8);
			player.getPackets().sendGameMessage("You spawn a divine tree.");
		} else if (itemId == 29305) {
			DivinePlacing.placeDivine(player, itemId, 87296, 87275, 15, 8);
			player.getPackets().sendGameMessage("You spawn a divine oak tree.");
		} else if (itemId == 29306) {
			DivinePlacing.placeDivine(player, itemId, 87297, 87276, 30, 8);
			player.getPackets().sendGameMessage("You spawn a divine willow tree.");
		} else if (itemId == 29307) {
			DivinePlacing.placeDivine(player, itemId, 87298, 87277, 45, 8);
			player.getPackets().sendGameMessage("You spawn a divine maple tree.");
		} else if (itemId == 29308) {
			DivinePlacing.placeDivine(player, itemId, 87299, 87278, 60, 8);
			player.getPackets().sendGameMessage("You spawn a divine yew tree.");
		} else if (itemId == 29309) {
			DivinePlacing.placeDivine(player, itemId, 87300, 87279, 75, 8);
			player.getPackets().sendGameMessage("You spawn a divine magic tree.");
		}
		// herbolore
		else if (itemId == 29310) {
			DivinePlacing.placeDivine(player, itemId, 87301, 87280, 9, 15);
			player.getPackets().sendGameMessage("You spawn a divine herb patch I.");
		} else if (itemId == 29311) {
			DivinePlacing.placeDivine(player, itemId, 87302, 87281, 44, 15);
			player.getPackets().sendGameMessage("You spawn a divine herb patch II.");
		} else if (itemId == 29312) {
			DivinePlacing.placeDivine(player, itemId, 87303, 87282, 67, 15);
			player.getPackets().sendGameMessage("You spawn a divine herb patch III.");
		}
		// hunting
		else if (itemId == 29300) {
			DivinePlacing.placeDivine(player, itemId, 87291, 87270, 1, 21);
			player.getPackets().sendGameMessage("You spawn a divine kebbit burrow.");
		} else if (itemId == 29301) {
			DivinePlacing.placeDivine(player, itemId, 87292, 87271, 1, 21);
			player.getPackets().sendGameMessage("You spawn a divine bird snare.");
		} else if (itemId == 29302) {
			DivinePlacing.placeDivine(player, itemId, 87293, 87272, 23, 21);
			player.getPackets().sendGameMessage("You spawn a divine deadfall trap.");
		} else if (itemId == 29303) {
			DivinePlacing.placeDivine(player, itemId, 87294, 87273, 53, 21);
			player.getPackets().sendGameMessage("You spawn a divine box trap.");
		}
		// fishing
		else if (itemId == 31080) {
			DivinePlacing.placeDivine(player, itemId, 90232, 90223, 10, 10);
			player.getPackets().sendGameMessage("You spawn a divine crayfish bubble.");
		} else if (itemId == 31081) {
			DivinePlacing.placeDivine(player, itemId, 90233, 90224, 10, 10);
			player.getPackets().sendGameMessage("You spawn a divine herring bubble.");
		} else if (itemId == 31082) {
			DivinePlacing.placeDivine(player, itemId, 90234, 90225, 20, 10);
			player.getPackets().sendGameMessage("You spawn a divine trout bubble.");
		} else if (itemId == 31083) {
			DivinePlacing.placeDivine(player, itemId, 90235, 90226, 30, 10);
			player.getPackets().sendGameMessage("You spawn a divine salmon bubble.");
		} else if (itemId == 31084) {
			DivinePlacing.placeDivine(player, itemId, 90236, 90227, 40, 10);
			player.getPackets().sendGameMessage("You spawn a divine lobster bubble.");
		} else if (itemId == 31085) {
			DivinePlacing.placeDivine(player, itemId, 90237, 90228, 50, 10);
			player.getPackets().sendGameMessage("You spawn a divine swordfish bubble.");
		} else if (itemId == 31086) {
			DivinePlacing.placeDivine(player, itemId, 90238, 90229, 76, 10);
			player.getPackets().sendGameMessage("You spawn a divine shark bubble.");
		} else if (itemId == 31087) {
			DivinePlacing.placeDivine(player, itemId, 90239, 90230, 85, 10);
			player.getPackets().sendGameMessage("You spawn a divine cavefish bubble.");
		} else if (itemId == 31088) {
			DivinePlacing.placeDivine(player, itemId, 90240, 90231, 90, 10);
			player.getPackets().sendGameMessage("You spawn a divine rocktail bubble.");
		}
		// div not being used
		else if (itemId == 31310) {
			DivinePlacing.placeDivine(player, itemId, 66526, 66528, 65, 1);
			player.getPackets().sendGameMessage("You spawn a divine simulacrum I.");
		} else if (itemId == 31311) {
			DivinePlacing.placeDivine(player, itemId, 66529, 66531, 65, 1);
			player.getPackets().sendGameMessage("You spawn a divine simulacrum II.");
		}
		// end of divination \\
		// portbale items
		if (item.getDefinitions().containsOption("Deploy")) {
			if (itemId == 31041)
				Portables.placePortable(player, item, 89767);
			if (itemId == 31042)
				Portables.placePortable(player, item, 89768);
			if (itemId == 31043)
				Portables.placePortable(player, item, 89769);
			if (itemId == 31044)
				Portables.placePortable(player, item, 89770);
			return;
		}

		if (itemId >= 5070 && itemId <= 5074) {
			BirdNests.searchNest(player, itemId);
			return;
		}

		else if (itemId == 5763) {
			player.getPackets().sendGameMessage("You Drink the Cider.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(5763, 1);

		} else if (itemId == 1905) {
			player.getPackets().sendGameMessage("You Drink the Asgarnian Ale.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1905, 1);
			player.getAppearence().setRenderEmote(52);

		} else if (itemId == 1909) {
			player.getPackets().sendGameMessage("You Drink the Greenman's Ale.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1909, 1);
			player.getAppearence().setRenderEmote(52);

		} else if (itemId == 5755) {
			player.getPackets().sendGameMessage("You Drink the Chef's Delight.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(5755, 1);
			player.getAppearence().setRenderEmote(52);

		} else if (itemId == 1911) {
			player.getPackets().sendGameMessage("You Drink the Dragon Bitter.");
			player.setNextForceTalk(new ForceTalk("Holy shit that was intense!"));
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1911, 1);
			player.getAppearence().setRenderEmote(290);

		} else if (itemId == 1907) {
			player.getPackets().sendGameMessage("You Drink the Wizard's MindBomb");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1907, 1);

		} else if (itemId == 3801) {
			player.getPackets().sendGameMessage("You Drink the Keg of Beer...And feel Quite Drunk...");
			player.setNextAnimation(new Animation(1330));
			player.getInventory().deleteItem(3801, 1);

		} else if (itemId == 3803) {
			player.getPackets().sendGameMessage("You drink the Beer");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(3803, 1);

		} else if (itemId == 431) {
			player.getPackets().sendGameMessage("You drink the rum");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(431, 1);

		} else if (itemId == 21576) {
			player.getDialogueManager().startDialogue("DrakansMedallion");
		} else if (itemId == 11328) {
			player.getInventory().deleteItem(11328, 1);
			player.getInventory().addItem(11324, 1);
		} else if (itemId == 11330) {
			player.getInventory().deleteItem(11330, 1);
			player.getInventory().addItem(11324, 1);
		} else if (itemId == 11332) {
			player.getInventory().deleteItem(11332, 1);
			player.getInventory().addItem(11324, 1);
		} else if (itemId == 5733) {
			player.getDialogueManager().startDialogue("TeleportCrystal");
		}
		

		// Armour sets Opening
		else if (itemId == 11814) {// Bronze (l)
			if (player.getInventory().getFreeSlots() >= 4) {
				player.getInventory().deleteItem(11814, 1);
				player.getInventory().addItem(1075, 1);
				player.getInventory().addItem(1117, 1);
				player.getInventory().addItem(1155, 1);
				player.getInventory().addItem(1189, 1);
			} else
				player.sm("You need 4 empty inventory spots before you can open this.");
		} else if (itemId == 11818) {// iron (l)
			player.getInventory().deleteItem(11818, 1);
			player.getInventory().addItem(1067, 1);
			player.getInventory().addItem(1115, 1);
			player.getInventory().addItem(1153, 1);
			player.getInventory().addItem(1191, 1);
		} else if (itemId == 11822) {// steel (l)
			player.getInventory().deleteItem(11822, 1);
			player.getInventory().addItem(1069, 1);
			player.getInventory().addItem(1119, 1);
			player.getInventory().addItem(1157, 1);
			player.getInventory().addItem(1193, 1);
		} else if (itemId == 11826) {// black (l)
			player.getInventory().deleteItem(11826, 1);
			player.getInventory().addItem(1077, 1);
			player.getInventory().addItem(1125, 1);
			player.getInventory().addItem(1165, 1);
			player.getInventory().addItem(1195, 1);
		} else if (itemId == 11830) {// mithril (l)
			player.getInventory().deleteItem(11830, 1);
			player.getInventory().addItem(1071, 1);
			player.getInventory().addItem(1121, 1);
			player.getInventory().addItem(1159, 1);
			player.getInventory().addItem(1197, 1);
		} else if (itemId == 11834) {// adamant (l)
			if (player.getInventory().getFreeSlots() >= 4) {
				player.getInventory().deleteItem(11834, 1);
				player.getInventory().addItem(1073, 1);
				player.getInventory().addItem(1123, 1);
				player.getInventory().addItem(1161, 1);
				player.getInventory().addItem(1199, 1);
			} else
				player.sm("You need 4 empty inventory spots before you can open this.");
		} else if (itemId == 11838) {// Rune (l)
			if (player.getInventory().getFreeSlots() >= 4) {
				player.getInventory().deleteItem(11838, 1);
				player.getInventory().addItem(1079, 1);
				player.getInventory().addItem(1127, 1);
				player.getInventory().addItem(1163, 1);
				player.getInventory().addItem(1201, 1);
			} else
				player.sm("You need 4 empty inventory spots before you can open this.");
		} else if (itemId == 14527) {// elite black night
			player.getInventory().deleteItem(14527, 1);
			player.getInventory().addItem(14490, 1);
			player.getInventory().addItem(14492, 1);
			player.getInventory().addItem(14494, 1);
		} else if (itemId == 11942) {// Rockshell
			player.getInventory().deleteItem(11942, 1);
			player.getInventory().addItem(6128, 1);
			player.getInventory().addItem(6129, 1);
			player.getInventory().addItem(6130, 1);
			player.getInventory().addItem(6145, 1);
			player.getInventory().addItem(6151, 1);
		} else if (itemId == 11842) {// dragon chain-mail (l)
			player.getInventory().deleteItem(11842, 1);
			player.getInventory().addItem(4087, 1);
			player.getInventory().addItem(3140, 1);
			player.getInventory().addItem(1149, 1);
		} else if (itemId == 11844) {// dragon chain-mail (sk)
			player.getInventory().deleteItem(11844, 1);
			player.getInventory().addItem(4585, 1);
			player.getInventory().addItem(3140, 1);
			player.getInventory().addItem(1149, 1);
		} else if (itemId == 14529) {// dragon Plate armour (l)
			player.getInventory().deleteItem(14529, 1);
			player.getInventory().addItem(4087, 1);
			player.getInventory().addItem(14479, 1);
			player.getInventory().addItem(11335, 1);
		} else if (itemId == 14529) {// dragon Plate armour (sk)
			player.getInventory().deleteItem(14529, 1);
			player.getInventory().addItem(4585, 1);
			player.getInventory().addItem(14479, 1);
			player.getInventory().addItem(11335, 1);
		}

		// Dhide armour

		else if (itemId == 11864) {// green dhide
			player.getInventory().deleteItem(11864, 1);
			player.getInventory().addItem(1135, 1);
			player.getInventory().addItem(1099, 1);
			player.getInventory().addItem(1065, 1);
		} else if (itemId == 11866) {// blue dhide
			player.getInventory().deleteItem(11866, 1);
			player.getInventory().addItem(2499, 1);
			player.getInventory().addItem(2493, 1);
			player.getInventory().addItem(2487, 1);
		} else if (itemId == 11868) {// Red dhide
			player.getInventory().deleteItem(11868, 1);
			player.getInventory().addItem(2501, 1);
			player.getInventory().addItem(2495, 1);
			player.getInventory().addItem(2489, 1);
		} else if (itemId == 11870) {// Black Dhide
			player.getInventory().deleteItem(11870, 1);
			player.getInventory().addItem(2503, 1);
			player.getInventory().addItem(2497, 1);
			player.getInventory().addItem(2491, 1);
		} else if (itemId == 11944) {// Spined armour set
			player.getInventory().deleteItem(11944, 1);
			player.getInventory().addItem(6131, 1);
			player.getInventory().addItem(6133, 1);
			player.getInventory().addItem(6135, 1);
			player.getInventory().addItem(6143, 1);
			player.getInventory().addItem(6149, 1);
		} else if (itemId == 11920) {// Blessed Green dhide
			player.getInventory().deleteItem(11920, 1);
			player.getInventory().addItem(10376, 1);
			player.getInventory().addItem(10378, 1);
			player.getInventory().addItem(10380, 1);
			player.getInventory().addItem(10382, 1);
		} else if (itemId == 11922) {// Blessed Blue dhide
			player.getInventory().deleteItem(11922, 1);
			player.getInventory().addItem(10384, 1);
			player.getInventory().addItem(10386, 1);
			player.getInventory().addItem(10388, 1);
			player.getInventory().addItem(10390, 1);
		} else if (itemId == 11924) {// Blessed red dhide
			player.getInventory().deleteItem(11924, 1);
			player.getInventory().addItem(10368, 1);
			player.getInventory().addItem(10370, 1);
			player.getInventory().addItem(10372, 1);
			player.getInventory().addItem(10374, 1);
		} else if (itemId == 19582) {// Blessed dyed brown dhide
			player.getInventory().deleteItem(19582, 1);
			player.getInventory().addItem(19451, 1);
			player.getInventory().addItem(19453, 1);
			player.getInventory().addItem(19455, 1);
			player.getInventory().addItem(19457, 1);
		} else if (itemId == 19584) {// Blessed dyed purple dhide
			player.getInventory().deleteItem(19584, 1);
			player.getInventory().addItem(19443, 1);
			player.getInventory().addItem(19445, 1);
			player.getInventory().addItem(19447, 1);
			player.getInventory().addItem(19449, 1);
		} else if (itemId == 19586) {// Blessed dyed silver dhide
			player.getInventory().deleteItem(19586, 1);
			player.getInventory().addItem(19459, 1);
			player.getInventory().addItem(19461, 1);
			player.getInventory().addItem(19463, 1);
			player.getInventory().addItem(19465, 1);
		} else if (itemId == 24386) {// Royal Dhide
			player.getInventory().deleteItem(24386, 1);
			player.getInventory().addItem(24382, 1);
			player.getInventory().addItem(24379, 1);
			player.getInventory().addItem(24376, 1);
		}

		// Mage
		else if (itemId == 11902) {// Enchanted
			player.getInventory().deleteItem(11902, 1);
			player.getInventory().addItem(7398, 1);
			player.getInventory().addItem(7399, 1);
			player.getInventory().addItem(7400, 1);
		} else if (itemId == 11874) {// Infinity robes
			player.getInventory().deleteItem(11874, 1);
			player.getInventory().addItem(6916, 1);
			player.getInventory().addItem(6918, 1);
			player.getInventory().addItem(6920, 1);
			player.getInventory().addItem(6922, 1);
			player.getInventory().addItem(6924, 1);
		} else if (itemId == 14525) {// Dragon 'hai
			player.getInventory().deleteItem(14525, 1);
			player.getInventory().addItem(14497, 1);
			player.getInventory().addItem(14499, 1);
			player.getInventory().addItem(14501, 1);
		} else if (itemId == 11876) {// Splitbark
			player.getInventory().deleteItem(11876, 1);
			player.getInventory().addItem(3385, 1);
			player.getInventory().addItem(3387, 1);
			player.getInventory().addItem(3389, 1);
			player.getInventory().addItem(3391, 1);
			player.getInventory().addItem(3393, 1);
		} else if (itemId == 11946) {// skeletal
			player.getInventory().deleteItem(11946, 1);
			player.getInventory().addItem(6137, 1);
			player.getInventory().addItem(6139, 1);
			player.getInventory().addItem(6141, 1);
			player.getInventory().addItem(6147, 1);
			player.getInventory().addItem(6153, 1);
		}

		else if (itemId == 11872) {// Blue mystic
			player.getInventory().deleteItem(11872, 1);
			player.getInventory().addItem(4089, 1);
			player.getInventory().addItem(4091, 1);
			player.getInventory().addItem(4093, 1);
			player.getInventory().addItem(4095, 1);
			player.getInventory().addItem(4097, 1);
		}

		else if (itemId == 11962) {// dark mystic
			player.getInventory().deleteItem(11962, 1);
			player.getInventory().addItem(4099, 1);
			player.getInventory().addItem(4101, 1);
			player.getInventory().addItem(4103, 1);
			player.getInventory().addItem(4105, 1);
			player.getInventory().addItem(4107, 1);
		} else if (itemId == 11960) {// Light Mystic
			player.getInventory().deleteItem(11960, 1);
			player.getInventory().addItem(4109, 1);
			player.getInventory().addItem(4111, 1);
			player.getInventory().addItem(4113, 1);
			player.getInventory().addItem(4115, 1);
			player.getInventory().addItem(4117, 1);
		}

		// Third age armour

		else if (itemId == 11858) {// Melee
			player.getInventory().deleteItem(11858, 1);
			player.getInventory().addItem(10350, 1);
			player.getInventory().addItem(10348, 1);
			player.getInventory().addItem(10346, 1);
			player.getInventory().addItem(10352, 1);
		} else if (itemId == 11860) {// Range
			player.getInventory().deleteItem(11860, 1);
			player.getInventory().addItem(10334, 1);
			player.getInventory().addItem(10330, 1);
			player.getInventory().addItem(10332, 1);
			player.getInventory().addItem(10336, 1);
		} else if (itemId == 11862) {// mage
			player.getInventory().deleteItem(11862, 1);
			player.getInventory().addItem(10342, 1);
			player.getInventory().addItem(10338, 1);
			player.getInventory().addItem(10340, 1);
			player.getInventory().addItem(10344, 1);
		} else if (itemId == 19580) {// Druidic
			player.getInventory().deleteItem(19580, 1);
			player.getInventory().addItem(19309, 1);
			player.getInventory().addItem(19311, 1);
			player.getInventory().addItem(19314, 1);
			player.getInventory().addItem(19317, 1);
			player.getInventory().addItem(19320, 1);
		}

		// Barrows sets
		else if (itemId == 11846) {// Ahrims
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11846, 1);
			player.getInventory().addItem(4708, 1);
			player.getInventory().addItem(4710, 1);
			player.getInventory().addItem(4712, 1);
			player.getInventory().addItem(4714, 1);
		} else if (itemId == 11848) {// Dharoks
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11848, 1);
			player.getInventory().addItem(4716, 1);
			player.getInventory().addItem(4718, 1);
			player.getInventory().addItem(4720, 1);
			player.getInventory().addItem(4722, 1);
		} else if (itemId == 11850) {// Guthans
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11850, 1);
			player.getInventory().addItem(4724, 1);
			player.getInventory().addItem(4726, 1);
			player.getInventory().addItem(4728, 1);
			player.getInventory().addItem(4730, 1);
		} else if (itemId == 11852) {// Karils
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11852, 1);
			player.getInventory().addItem(4732, 1);
			player.getInventory().addItem(4734, 1);
			player.getInventory().addItem(4736, 1);
			player.getInventory().addItem(4738, 1);
		} else if (itemId == 11854) {// Torags
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11854, 1);
			player.getInventory().addItem(4745, 1);
			player.getInventory().addItem(4747, 1);
			player.getInventory().addItem(4749, 1);
			player.getInventory().addItem(4751, 1);
		} else if (itemId == 11856) {// Veracs
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11856, 1);
			player.getInventory().addItem(4753, 1);
			player.getInventory().addItem(4755, 1);
			player.getInventory().addItem(4757, 1);
			player.getInventory().addItem(4759, 1);
		} else if (itemId == 21768) {// Akrisaes
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(21768, 1);
			player.getInventory().addItem(21736, 1);
			player.getInventory().addItem(21744, 1);
			player.getInventory().addItem(21752, 1);
			player.getInventory().addItem(21760, 1);
		} else if (itemId >= 23653 && itemId <= 23658)
			FightKiln.useCrystal(player, itemId);
		if (itemId == 405) {
			int[] reward = { 20000, 30000, 40000, 50000, 75000 };
			int won = reward[Utils.random(reward.length - 1)];
			player.getInventory().deleteItem(405, 1);
			player.getInventory().addItemMoneyPouch(new Item(995, won));
			player.getPackets().sendGameMessage("The casket slowly opens... You receive " + won + " coins!");
		} else if (itemId == 24154 || itemId == 24155) {
			player.getSquealOfFortune().processItemClick(slotId, itemId, item);
		} else if (Lamps.isSelectable(itemId) || Lamps.isSkillLamp(itemId) || Lamps.isOtherLamp(itemId)) {
			Lamps.processLampClick(player, slotId, itemId);
		}
		// pendants
		boolean pendant = ItemDefinitions.getItemDefinitions(itemId).name.contains("Prized");
		Pendants pents = player.getPrizedPendants().forItem(itemId, pendant);
		if (pents != null) {
			player.sm("Exp left: " + player.getPrizedPendants().getExpLeft(pents));
		} else if (itemId == 25468 || itemId == 25469) {
			player.getPrizedPendants().openSelectableDialog(player, slotId, itemId);
		} else if (itemId == 1856) {// Information Book
			player.getInterfaceManager().sendHelpBook();

			// for (int i = 31; i < 300; i++)
			// player.getPackets().sendIComponentText(275, i, "");
		} /*
			 * else if (itemId == HunterEquipment.BOX.getId()) // almost done
			 * player.getActionManager().setAction(new
			 * BoxAction(HunterEquipment.BOX)); else if (itemId ==
			 * HunterEquipment.BRID_SNARE.getId())
			 * player.getActionManager().setAction( new
			 * BoxAction(HunterEquipment.BRID_SNARE));
			 */
		else if (TrapAction.isTrap(player, new WorldTile(player), itemId))
			return;
		else if (item.getDefinitions().getName().startsWith("Burnt"))
			player.getDialogueManager().startDialogue("SimplePlayerMessage", "Ugh, this is inedible.");
		Logger.log("ItemHandler 1", "Item Select:" + itemId + ", Slot Id:" + slotId);
	}

	/*
	 * returns the other
	 */
	public static Item contains(int id1, Item item1, Item item2) {
		if (item1.getId() == id1)
			return item2;
		if (item2.getId() == id1)
			return item1;
		return null;
	}

	public static boolean contains(int id1, int id2, Item... items) {
		boolean containsId1 = false;
		boolean containsId2 = false;
		for (Item item : items) {
			if (item.getId() == id1)
				containsId1 = true;
			else if (item.getId() == id2)
				containsId2 = true;
		}
		return containsId1 && containsId2;
	}

	public static void handleItemOnItem(final Player player, InputStream stream) {
		int itemUsedWithId = stream.readInt();
		int toSlot = stream.readUnsignedShortLE128();
		int interfaceId = stream.readUnsignedShort();
		int interfaceComponent = stream.readUnsignedShort();
		int interfaceId2 = stream.readInt() >> 16;
		int fromSlot = stream.readUnsignedShort();
		int itemUsedId = stream.readInt();
		Item fromItem = player.getInventory().getItem(fromSlot);
		Item toItem = player.getInventory().getItem(toSlot);
		player.stopAll();
		if (interfaceId2 == 679) {
			if (interfaceId == 192 || interfaceId == 430) {
				MagicOnItem.handleMagic(player, interfaceComponent, player.getInventory().getItem(toSlot));
				return;
			}
		}
		if ((interfaceId == 747 || interfaceId == 662) && interfaceId2 == Inventory.INVENTORY_INTERFACE) {
			if (player.getFamiliar() != null) {
				player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.ITEM) {
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(toSlot);
				}
			}
			return;
		}
		if (interfaceId == Inventory.INVENTORY_INTERFACE && interfaceId == interfaceId2 && !player.getInterfaceManager().containsInventoryInter()) {
			if (toSlot >= 28 || fromSlot >= 28)
				return;
			Item usedWith = player.getInventory().getItem(toSlot);
			Item itemUsed = player.getInventory().getItem(fromSlot);
			if (itemUsed == null || usedWith == null || itemUsed.getId() != itemUsedId || usedWith.getId() != itemUsedWithId)
				return;
			player.stopAll();
			if (!player.getControlerManager().canUseItemOnItem(itemUsed, usedWith))
				return;
			Fletch fletch = Fletching.isFletching(usedWith, itemUsed);
			if (fletch != null) {
				player.getDialogueManager().startDialogue("FletchingD", fletch);
				return;
			}
			SlayerHelmets.handleItemOnItem(player, itemUsed, usedWith);
			SkillingUrns urns = SkillingUrnsManager.urns(itemUsed.getId(), usedWith.getId());
			if (urns != null) {
				player.getUrns().runeOnUrn(itemUsed, usedWith);
				return;
			}
			if ((itemUsed.getId() == 6573 && usedWith.getId() == 1759) || (itemUsed.getId() == 1759 && usedWith.getId() == 6573)) {
				if (player.getInventory().containsItem(6573, 1) && player.getInventory().containsItem(1759, 1)) {
					player.getInventory().deleteItem(6573, 1);
					player.getInventory().deleteItem(1759, 1);
					player.getInventory().addItem(6581, 1);
					player.sendMessage("You succesfully make a Onyx amulet.");
				} else {
					player.sendMessage("You need both parts to make this amulet.");
				}
				return;
			}
			if (Upgradables.byItems(itemUsedId, itemUsedWithId) != null) {
				if (ItemUpgradeKits.AttachKitToItem(player, itemUsedId, itemUsedWithId)) {
					return;
				}
			}
			if (Combinings.forItem(itemUsedId, itemUsedWithId) != null) {
				if (ItemCombining.CombineItems(player, itemUsedId, itemUsedWithId)) {
					return;
				}
			}
			if (Combinings.forProduct(itemUsedId) != null) {
				if (ItemCombining.RemoveDyeFromItems(player, itemUsedId, itemUsedWithId)) {
					return;
				}
			} else if (Combinings.forProduct(itemUsedWithId) != null) {
				if (ItemCombining.RemoveDyeFromItems(player, itemUsedWithId, itemUsedId)) {
					return;
				}
			}
			Tools tools = Tools.forId(itemUsedId);
			if (tools != null && usedWith.getId() == 32622) {
				CrystalItemCreation.convertTool(player, itemUsedId);
				return;
			}
			if (TreeSaplings.hasSaplingRequest(player, itemUsedId, itemUsedWithId)) {
				if (itemUsedId == 5354)
					TreeSaplings.plantSeed(player, itemUsedWithId, fromSlot);
				else
					TreeSaplings.plantSeed(player, itemUsedId, toSlot);
			}
			if (itemUsed.getId() == 32703 && usedWith.getId() == 565 || itemUsed.getId() == 565 && usedWith.getId() == 32703) {
				int amount = player.getInventory().getAmountOf(565);
				player.getInventory().deleteItem(565, amount);
				player.setBloodcharges(player.getBloodcharges() + amount);
				player.sm("You have added " + amount + " charges to your amulet, it has now a total of " + player.getBloodcharges() + " charges.");

			}
			if (PrayerBooks.isGodBook(itemUsed.getId(), false) || PrayerBooks.isGodBook(usedWith.getId(), false)) {
				PrayerBooks.bindPages(player, itemUsed.getName().contains(" page ") ? usedWith.getId() : itemUsed.getId());
			}
			if ((itemUsed.getId() == 985 && usedWith.getId() == 987) || (itemUsed.getId() == 987 && usedWith.getId() == 985)) {
				if (player.getInventory().containsItem(985, 1) && player.getInventory().containsItem(987, 1)) {
					player.getInventory().deleteItem(985, 1);
					player.getInventory().deleteItem(987, 1);
					player.getInventory().addItem(989, 1);
					player.sendMessage("You succesfully make a crytal key.");
				} else {
					player.sendMessage("You need both parts to make this key.");
				}
				return;
			}

			if (itemUsed.getId() == 187 || itemUsed.getId() == 5937 || itemUsed.getId() == 5940 || usedWith.getId() == 187 || usedWith.getId() == 5937 || usedWith.getId() == 5940)
				WeaponPoison.handleItemInteract(player, itemUsed, usedWith);
			if ((itemUsed.getId() >= 1617 && itemUsed.getId() <= 1624) && usedWith.getId() == 18338) {
				if (player.gembagspace < 100) {
					if (itemUsed.getId() == 1617) {
						player.diamonds++;
						player.getInventory().deleteItem(1617, 1);
					} else if (itemUsed.getId() == 1619) {
						player.rubies++;
						player.getInventory().deleteItem(1619, 1);
					} else if (itemUsed.getId() == 1621) {
						player.emeralds++;
						player.getInventory().deleteItem(1621, 1);
					} else if (itemUsed.getId() == 1623) {
						player.sapphires++;
						player.getInventory().deleteItem(1623, 1);
					}
					player.gembagspace++;
				} else {
					player.sm("Your gem bag is too full to carry anymore uncut gems.");
				}
			}
			if (itemUsed.getId() == 4155 && usedWith.getId() == 8007) {
				if (player.getSkills().getLevel(6) > 65) {
					player.getInventory().deleteItem(4155, 1);
					player.getInventory().deleteItem(8007, 1);
					player.getSkills().addXp(6, 200);
					player.getInventory().addItem(11014, 1);
					player.sm("You infuse the items together to make a Slayer Teleporter.");
				} else {
					player.sm("You need a magic level of 65 to infuse these items together.");
				}
			}

			if (CosmeticsHandler.keepSakeItem(player, itemUsed, usedWith))
				return;
			if (itemUsed.getId() == 453 && usedWith.getId() == 18339) {
				if (player.coal < 27) {
					player.getInventory().deleteItem(453, 1);
					player.coal++;
					player.sm("You add a piece of coal to your coal bag.");
				} else {
					player.sm("Your coal bag is too full to carry anymore coal.");
				}
			}
			if ((itemUsed.getId() == 1785 && usedWith.getId() == 18330) || (itemUsed.getId() == 18330 && usedWith.getId() == 1785)) {
				player.getInventory().deleteItem(18330, 1);
				player.getInventory().deleteItem(851, 1);
				player.getInventory().addItem(18331, 1);
				player.sm("You attach the two pieces together to create a maple sheildbow (sighted).");
			}
			if ((itemUsed.getId() == 851 && usedWith.getId() == 23193) || (itemUsed.getId() == 23193 && usedWith.getId() == 851)) {
				if (player.getSkills().getLevel(Skills.CRAFTING) >= 89) {
					player.getInventory().deleteItem(23193, 1);
					player.getInventory().addItem(23191, 1);
					player.getSkills().addXp(Skills.CRAFTING, 40);
					player.setNextAnimation(new Animation(884));
					player.sm("You succesfully create an empty potion flask.");
				} else {
					player.sm("You need a crafting level of atleast 89 to create a potion flask.");
				}
			}
			// Dragon Sq shield
			if (itemUsed.getId() == 2368 || usedWith.getId() == 2366) {
				if (player.getInventory().containsItem(2366, 1) && player.getInventory().containsItem(2368, 1)) {
					player.getInventory().deleteItem(2366, 1);
					player.getInventory().deleteItem(2368, 1);
					player.getInventory().addItem(1187, 1);
					player.getPackets().sendGameMessage("You create a Dragon sq shield.");
				}
			}
			if (itemUsed.getId() == 2366 || usedWith.getId() == 2368) {
				if (player.getInventory().containsItem(2366, 1) && player.getInventory().containsItem(2368, 1)) {
					player.getInventory().deleteItem(2366, 1);
					player.getInventory().deleteItem(2368, 1);
					player.getInventory().addItem(1187, 1);
					player.getPackets().sendGameMessage("You create a Dragon sq shield.");
				}
			}
			// doogle sardine
			if (itemUsed.getId() == 327 || usedWith.getId() == 1573) {
				if (player.getInventory().containsItem(1573, 1) && player.getInventory().containsItem(327, 1)) {
					player.getInventory().deleteItem(1573, 1);
					player.getInventory().deleteItem(327, 1);
					player.getInventory().addItem(1552, 1);
					player.getPackets().sendGameMessage("You wrap the sardine in the doogle leaf.");
				}
			}
			if (itemUsed.getId() == 1573 || usedWith.getId() == 327) {
				if (player.getInventory().containsItem(1573, 1) && player.getInventory().containsItem(327, 1)) {
					player.getInventory().deleteItem(1573, 1);
					player.getInventory().deleteItem(327, 1);
					player.getInventory().addItem(1552, 1);
					player.getPackets().sendGameMessage("You wrap the sardine in the doogle leaf.");
				}
			}

			//

			// Dragonfire shield
			if (itemUsed.getId() == 11286 || usedWith.getId() == 1540) {
				if (player.getInventory().containsItem(11286, 1) && player.getInventory().containsItem(1540, 1)) {
					player.getInventory().deleteItem(11286, 1);
					player.getInventory().deleteItem(1540, 1);
					player.getInventory().addItem(11283, 1);
					player.getPackets().sendGameMessage("You create a Dragonfire Shield!");
				}
			}
			if (itemUsed.getId() == 1540 || usedWith.getId() == 11286) {
				if (player.getInventory().containsItem(11286, 1) && player.getInventory().containsItem(1540, 1)) {
					player.getInventory().deleteItem(11286, 1);
					player.getInventory().deleteItem(1540, 1);
					player.getInventory().addItem(11283, 1);
					player.getPackets().sendGameMessage("You create a Dragonfire Shield!");
				}
			}
			// Fire Arrows
			if (itemUsed.getId() == 1485 || usedWith.getId() == 882) {
				if (player.getInventory().containsItem(1485, 1) && player.getInventory().containsItem(882, 1)) {
					player.getInventory().deleteItem(1485, 1);
					player.getInventory().deleteItem(882, 1);
					player.getInventory().addItem(942, 1);
					player.getPackets().sendGameMessage("You create a bronze fire arrow.");
				}
			}
			if (itemUsed.getId() == 882 || usedWith.getId() == 1485) {
				if (player.getInventory().containsItem(1485, 1) && player.getInventory().containsItem(882, 1)) {
					player.getInventory().deleteItem(1485, 1);
					player.getInventory().deleteItem(882, 1);
					player.getInventory().addItem(942, 1);
					player.getPackets().sendGameMessage("You create a bronze fire arrow.");
				}
			}
			/**
			 * fruit cutting
			 */
			if (usedWith.getId() == 946 || itemUsed.getId() == 946) {
				CuttableFruit fruit = CuttableFruit.forId(itemUsed.getId());
				if (fruit != null && usedWith.getId() == 946) {
					player.getDialogueManager().startDialogue("FruitCuttingD", fruit);
				}

				fruit = CuttableFruit.forId(usedWith.getId());
				if (fruit != null && itemUsed.getId() == 946) {
					player.getDialogueManager().startDialogue("FruitCuttingD", fruit);
				}
			}
			// Roe
			if (itemUsed.getId() == 946 || usedWith.getId() == 11328) {
				if (player.getInventory().containsItem(946, 1) && player.getInventory().containsItem(11328, 1)) {

					player.getInventory().deleteItem(11328, 1);
					player.getInventory().addItem(11324, 1);
				}
			}
			if (itemUsed.getId() == 11328 || usedWith.getId() == 946) {
				if (player.getInventory().containsItem(946, 1) && player.getInventory().containsItem(11328, 1)) {

					player.getInventory().deleteItem(11328, 1);
					player.getInventory().addItem(11324, 1);
				}
			}

			if (itemUsed.getId() == 946 || usedWith.getId() == 11330) {
				if (player.getInventory().containsItem(946, 1) && player.getInventory().containsItem(11330, 1)) {

					player.getInventory().deleteItem(11330, 1);
					player.getInventory().addItem(11324, 1);
				}
			}
			else if(itemUsed.getId() == 1759)
				Jewelry.stringAmulet(player, usedWith);
			if (itemUsed.getId() == 11330 || usedWith.getId() == 946) {
				if (player.getInventory().containsItem(946, 1) && player.getInventory().containsItem(11330, 1)) {

					player.getInventory().deleteItem(11330, 1);
					player.getInventory().addItem(11324, 1);
				}
			}
			if (contains(272, 273, itemUsed, usedWith) || (contains(273, 272, itemUsed, usedWith))) {
				player.getInventory().deleteItem(272, 1);
				player.getInventory().deleteItem(273, 1);
				player.getInventory().addItem(274, 1);
			}
			if (itemUsed.getId() == 946 || usedWith.getId() == 11332) {
				if (player.getInventory().containsItem(946, 1) && player.getInventory().containsItem(11332, 1)) {

					player.getInventory().deleteItem(11332, 1);
					player.getInventory().addItem(11324, 1);
				}
			}
			if (itemUsed.getId() == 11332 || usedWith.getId() == 946) {
				if (player.getInventory().containsItem(946, 1) && player.getInventory().containsItem(11332, 1)) {

					player.getInventory().deleteItem(11332, 1);
					player.getInventory().addItem(11324, 1);
				}
			}
			// Godswords
			if (itemUsed.getId() == 11710 || usedWith.getId() == 11712 || usedWith.getId() == 11714) {
				if (player.getInventory().containsItem(11710, 1) && player.getInventory().containsItem(11712, 1) && player.getInventory().containsItem(11714, 1)) {
					player.getInventory().deleteItem(11710, 1);
					player.getInventory().deleteItem(11712, 1);
					player.getInventory().deleteItem(11714, 1);
					player.getInventory().addItem(11690, 1);
					player.getPackets().sendGameMessage("You made a godsword blade.");
				}
			}
			if (itemUsed.getId() == 11690 || usedWith.getId() == 11702) {
				if (player.getInventory().containsItem(11690, 1) && player.getInventory().containsItem(11702, 1)) {
					player.getInventory().deleteItem(11690, 1);
					player.getInventory().deleteItem(11702, 1);
					player.getInventory().addItem(11694, 1);
					player.getAchievementManager().notifyUpdate(TheCreatorIAchievement.class);
					player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Armadyl godsword.");
				}
			}
			if (itemUsed.getId() == 11690 || usedWith.getId() == 11704) {
				if (player.getInventory().containsItem(11690, 1) && player.getInventory().containsItem(11704, 1)) {
					player.getInventory().deleteItem(11690, 1);
					player.getInventory().deleteItem(11704, 1);
					player.getAchievementManager().notifyUpdate(TheCreatorIAchievement.class);
					player.getInventory().addItem(11696, 1);
					player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Bandos godsword.");
				}
			}
			if (itemUsed.getId() == 11690 || usedWith.getId() == 11706) {
				if (player.getInventory().containsItem(11690, 1) && player.getInventory().containsItem(11706, 1)) {
					player.getInventory().deleteItem(11690, 1);
					player.getInventory().deleteItem(11706, 1);
					player.getAchievementManager().notifyUpdate(TheCreatorIAchievement.class);
					player.getInventory().addItem(11698, 1);
					player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Saradomin godsword.");
				}
			}
			if (itemUsed.getId() == 11690 || usedWith.getId() == 11708) {
				if (player.getInventory().containsItem(11690, 1) && player.getInventory().containsItem(11708, 1)) {
					player.getInventory().deleteItem(11690, 1);
					player.getInventory().deleteItem(11708, 1);
					player.getInventory().addItem(11700, 1);
					player.getAchievementManager().notifyUpdate(TheCreatorIAchievement.class);
					player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Zamorak godsword.");
				}
			}

			switch (itemUsed.getId()) {
			case 1391:// battlestaff
				switch (usedWith.getId()) {
				case 571:// water
				case 569:// fire
				case 575:// earth
				case 573:// air
					if (!player.getInventory().containsItem(itemUsedWithId, 1) || !player.getInventory().containsItem(itemUsedId, 1))
						return;
					if (player.getSkills().getLevel(Skills.CRAFTING) < getStaffLevel(itemUsedWithId + 824)) {
						player.getPackets().sendGameMessage("You need a crafting level of " + getStaffLevel(itemUsedWithId + 824) + " to make this.");
						return;
					}
					player.getInventory().deleteItem(itemUsedId, 1);
					player.getInventory().deleteItem(itemUsedWithId, 1);
					player.getInventory().addItem(itemUsedWithId + 824, 1);
					player.getPackets().sendGameMessage("You attach the orb onto the battlestaff, producing " + Utils.formatAorAn(new Item(itemUsedWithId + 824)) + " " + ItemDefinitions.getItemDefinitions(itemUsedWithId + 824).getName().toLowerCase() + ".", true);
					player.getSkills().addXp(Skills.CRAFTING, getStaffExp(itemUsedWithId + 824));
					break;
				}
				break;
			case 571:// water
			case 569:// fire
			case 575:// earth
			case 573:// air
				switch (usedWith.getId()) {
				case 1391:
					if (!player.getInventory().containsItem(itemUsedWithId, 1) || !player.getInventory().containsItem(itemUsedId, 1))
						return;
					if (player.getSkills().getLevel(Skills.CRAFTING) < getStaffLevel(itemUsedId + 824)) {
						player.getPackets().sendGameMessage("You need a crafting level of " + getStaffLevel(itemUsedId + 824) + " to make this.");
						return;
					}
					player.getInventory().deleteItem(itemUsedId, 1);
					player.getInventory().deleteItem(itemUsedWithId, 1);
					player.getInventory().addItem(itemUsedId + 824, 1);
					player.getPackets().sendGameMessage("You attach the orb onto the battlestaff, producing " + Utils.formatAorAn(new Item(itemUsedId + 824)) + " " + ItemDefinitions.getItemDefinitions(itemUsedId + 824).getName().toLowerCase() + ".", true);
					player.getSkills().addXp(Skills.CRAFTING, getStaffExp(itemUsedId + 824));
					break;
				}
				break;
			}
			/*
			 * Note papers
			 */
			if (itemUsed.getId() == 30372) {
				if (usedWith.getDefinitions().isNoted() || usedWith.getDefinitions().isStackable() || usedWith.getDefinitions().getCertId() == -1)
					player.sm("You can't note this item.");
				else if (usedWith.getDefinitions().getCertId() != -1) {
					if (!player.getInventory().containsItem(usedWith.getId(), 1)) // just
																					// to
																					// be
																					// sure
						return;
					if (player.getInventory().getFreeSlots() >= 1) {
						player.getInventory().deleteItem(30372, 1);
						player.getInventory().addItem((usedWith.getDefinitions().getCertId()), 1);
						player.getInventory().deleteItem(usedWith.getId(), 1);
					} else
						player.sm("You need atleast one free inventory space to do this.");
				}

			}

			// Polypore staff
			if (itemUsed.getId() == 22498 || usedWith.getId() == 22448 || usedWith.getId() == 554) {
				if (player.getInventory().containsItem(22498, 1) && player.getInventory().containsItem(22448, 3000) && player.getInventory().containsItem(554, 15000) && (player.getSkills().getLevel(Skills.FARMING) > 79)) {
					player.getInventory().deleteItem(22498, 1);
					player.getInventory().deleteItem(22448, 3000);
					player.getInventory().deleteItem(554, 15000);
					player.getInventory().addItem(22494, 1);
					player.getPackets().sendGameMessage("You craft a Polypore Staff.");
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "You must have 80 farming, 15k fire runes, 3k polypore spore and 1 polypore stick to make this.");
					return;
				}
			}
			if (itemUsed.getId() == 22448 || usedWith.getId() == 22498 || usedWith.getId() == 554) {
				if (player.getInventory().containsItem(22498, 1) && player.getInventory().containsItem(22448, 3000) && player.getInventory().containsItem(554, 15000) && (player.getSkills().getLevel(Skills.FARMING) > 79)) {
					player.getInventory().deleteItem(22498, 1);
					player.getInventory().deleteItem(22448, 3000);
					player.getInventory().deleteItem(554, 15000);
					player.getInventory().addItem(22494, 1);
					player.getPackets().sendGameMessage("You craft a Polypore Staff.");
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "You must have 80 farming, 15k fire runes, 3k polypore spore and 1 polypore stick to make this.");
					return;
				}
			}
			// Ganodermic robes
			if (itemUsed.getId() == 22451 && usedWith.getId() == 22452) {// Visor
				
				player.getDialogueManager().startDialogue("GanocraftingD");
			}
			else if (itemUsed.getId() == 22451 && usedWith.getId() == 22454) {// Leggings
				player.getDialogueManager().startDialogue("GanocraftingD");
			}
			else if (itemUsed.getId() == 22451 && usedWith.getId() == 22456) {// poncho
				player.getDialogueManager().startDialogue("GanocraftingD");
			}
			// Grifolic Robes
			if (itemUsed.getId() == 22450 && usedWith.getId() == 22452) {// Visor
				player.getDialogueManager().startDialogue("GrifoCraftingD");
			}
			if (itemUsed.getId() == 22450 && usedWith.getId() == 22454) {// Leggings
				player.getDialogueManager().startDialogue("GrifoCraftingD");
			}
			if (itemUsed.getId() == 22450 && usedWith.getId() == 22456) {// poncho
				player.getDialogueManager().startDialogue("GrifoCraftingD");
			}
			

			int herblore = Herblore.isHerbloreSkill(itemUsed, usedWith);
			if (herblore > -1) {
				player.getDialogueManager().startDialogue("HerbloreD", herblore, itemUsed, usedWith);
				return;
			}
			if (itemUsedId == LeatherCrafting.NEEDLE.getId() || itemUsedWithId == LeatherCrafting.NEEDLE.getId()) {
				if (LeatherCrafting.handleItemOnItem(player, itemUsed, usedWith)) {
					return;
				}
			}
			/*
			 * Sets set = ArmourSets.getArmourSet(itemUsedId, itemUsedWithId);
			 * if (set != null) { ArmourSets.exchangeSets(player, set); return;
			 * }
			 */
			/**
			 * Flask Making
			 */

			// Attack (4)
			if (usedWith.getId() == 2428 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(121, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(121, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23201, 1);
					return;
				}
			}
			// Attack (3)
			if (usedWith.getId() == 121 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(121, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(121, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23201, 1);
					return;
				}
			}
			// Attack (2)
			if (usedWith.getId() == 123 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(123, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(123, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23203, 1);
					return;
				}
			}
			// Attack (1)
			if (usedWith.getId() == 125 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(125, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(125, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23205, 1);
					return;
				}
			}
			// Super Attack (1) into Super Attack Flask (5)
			if (usedWith.getId() == 149 || usedWith.getId() == 23257) {
				if (player.getInventory().containsItem(149, 1) && player.getInventory().containsItem(23257, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23257, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (1) into Super Attack Flask (4)
			if (usedWith.getId() == 149 || usedWith.getId() == 23259) {
				if (player.getInventory().containsItem(149, 1) && player.getInventory().containsItem(23259, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23259, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23257, 1);
					return;
				}
			}
			// Super Attack (1) into Super Attack Flask (3)
			if (usedWith.getId() == 149 || usedWith.getId() == 23261) {
				if (player.getInventory().containsItem(149, 1) && player.getInventory().containsItem(23261, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23261, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23259, 1);
					return;
				}
			}
			// Super Attack (1) into Super Attack Flask (2)
			if (usedWith.getId() == 149 || usedWith.getId() == 23263) {
				if (player.getInventory().containsItem(149, 1) && player.getInventory().containsItem(23263, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23263, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23261, 1);
					return;
				}
			}
			// Super Attack (1) into Super Attack Flask (1)
			if (usedWith.getId() == 149 || usedWith.getId() == 23265) {
				if (player.getInventory().containsItem(149, 1) && player.getInventory().containsItem(23265, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23265, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23263, 1);
					return;
				}
			}
			// Super Attack (2) into Super Attack Flask (5)
			if (usedWith.getId() == 147 || usedWith.getId() == 23257) {
				if (player.getInventory().containsItem(147, 1) && player.getInventory().containsItem(23257, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23257, 1);
					player.getInventory().addItem(149, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (2) into Super Attack Flask (4)
			if (usedWith.getId() == 147 || usedWith.getId() == 23259) {
				if (player.getInventory().containsItem(147, 1) && player.getInventory().containsItem(23259, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23259, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (2) into Super Attack Flask (3)
			if (usedWith.getId() == 147 || usedWith.getId() == 23261) {
				if (player.getInventory().containsItem(147, 1) && player.getInventory().containsItem(23261, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23261, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23257, 1);
					return;
				}
			}
			// Super Attack (2) into Super Attack Flask (2)
			if (usedWith.getId() == 147 || usedWith.getId() == 23263) {
				if (player.getInventory().containsItem(147, 1) && player.getInventory().containsItem(23263, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23263, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23259, 1);
					return;
				}
			}
			// Super Attack (2) into Super Attack Flask (1)
			if (usedWith.getId() == 147 || usedWith.getId() == 23265) {
				if (player.getInventory().containsItem(147, 1) && player.getInventory().containsItem(23265, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23265, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23261, 1);
					return;
				}
			}
			// Super Attack (3) into Super Attack Flask (5)
			if (usedWith.getId() == 145 || usedWith.getId() == 23257) {
				if (player.getInventory().containsItem(145, 1) && player.getInventory().containsItem(23257, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23257, 1);
					player.getInventory().addItem(147, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (3) into Super Attack Flask (4)
			if (usedWith.getId() == 145 || usedWith.getId() == 23259) {
				if (player.getInventory().containsItem(145, 1) && player.getInventory().containsItem(23259, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23259, 1);
					player.getInventory().addItem(149, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (3) into Super Attack Flask (3)
			if (usedWith.getId() == 145 || usedWith.getId() == 23261) {
				if (player.getInventory().containsItem(145, 1) && player.getInventory().containsItem(23261, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23261, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (3) into Super Attack Flask (2)
			if (usedWith.getId() == 145 || usedWith.getId() == 23263) {
				if (player.getInventory().containsItem(145, 1) && player.getInventory().containsItem(23263, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23263, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23257, 1);
					return;
				}
			}
			// Super Attack (3) into Super Attack Flask (1)
			if (usedWith.getId() == 145 || usedWith.getId() == 23265) {
				if (player.getInventory().containsItem(145, 1) && player.getInventory().containsItem(23265, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23265, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23259, 1);
					return;
				}
			}
			// Super Attack (4) into Super Attack Flask (5)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23257) {
				if (player.getInventory().containsItem(2436, 1) && player.getInventory().containsItem(23257, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23257, 1);
					player.getInventory().addItem(145, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (4) into Super Attack Flask (4)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23259) {
				if (player.getInventory().containsItem(2436, 1) && player.getInventory().containsItem(23259, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23259, 1);
					player.getInventory().addItem(147, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (4) into Super Attack Flask (3)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23261) {
				if (player.getInventory().containsItem(2436, 1) && player.getInventory().containsItem(23261, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23261, 1);
					player.getInventory().addItem(149, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (4) into Super Attack Flask (2)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23263) {
				if (player.getInventory().containsItem(2436, 1) && player.getInventory().containsItem(23263, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23263, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23255, 1);
					return;
				}
			}
			// Super Attack (4) into Super Attack Flask (1)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23265) {
				if (player.getInventory().containsItem(2436, 1) && player.getInventory().containsItem(23265, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23265, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23257, 1);
					return;
				}
			}
			// Super Attack (4) into Empty Flask
			if (usedWith.getId() == 2436 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(2436, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23259, 1);
					return;
				}
			}
			// Super Attack (3) into Empty Flask
			if (usedWith.getId() == 145 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(145, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23261, 1);
					return;
				}
			}
			// Super Attack (2) into Empty Flask
			if (usedWith.getId() == 147 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(147, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23263, 1);
					return;
				}
			}
			// Super Attack (1) into Empty Flask
			if (usedWith.getId() == 149 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(149, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23265, 1);
					return;
				}
			}
			// Super Strength (1) into Super Strength Flask (5)
			if (usedWith.getId() == 161 || usedWith.getId() == 23281) {
				if (player.getInventory().containsItem(161, 1) && player.getInventory().containsItem(23281, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23281, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (1) into Super Strength Flask (4)
			if (usedWith.getId() == 161 || usedWith.getId() == 23283) {
				if (player.getInventory().containsItem(161, 1) && player.getInventory().containsItem(23283, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23283, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23281, 1);
					return;
				}
			}
			// Super Strength (1) into Super Strength Flask (3)
			if (usedWith.getId() == 161 || usedWith.getId() == 23285) {
				if (player.getInventory().containsItem(161, 1) && player.getInventory().containsItem(23285, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23285, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23283, 1);
					return;
				}
			}
			// Super Strength (1) into Super Strength Flask (2)
			if (usedWith.getId() == 161 || usedWith.getId() == 23287) {
				if (player.getInventory().containsItem(161, 1) && player.getInventory().containsItem(23287, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23287, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23285, 1);
					return;
				}
			}
			// Super Strength (1) into Super Strength Flask (1)
			if (usedWith.getId() == 161 || usedWith.getId() == 23289) {
				if (player.getInventory().containsItem(161, 1) && player.getInventory().containsItem(23289, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23289, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23287, 1);
					return;
				}
			}
			// Super Strength (2) into Super Strength Flask (5)
			if (usedWith.getId() == 159 || usedWith.getId() == 23281) {
				if (player.getInventory().containsItem(159, 1) && player.getInventory().containsItem(23281, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23281, 1);
					player.getInventory().addItem(161, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (2) into Super Strength Flask (4)
			if (usedWith.getId() == 159 || usedWith.getId() == 23283) {
				if (player.getInventory().containsItem(159, 1) && player.getInventory().containsItem(23283, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23283, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (2) into Super Strength Flask (3)
			if (usedWith.getId() == 159 || usedWith.getId() == 23285) {
				if (player.getInventory().containsItem(159, 1) && player.getInventory().containsItem(23285, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23285, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23281, 1);
					return;
				}
			}
			// Super Strength (2) into Super Strength Flask (2)
			if (usedWith.getId() == 159 || usedWith.getId() == 23287) {
				if (player.getInventory().containsItem(159, 1) && player.getInventory().containsItem(23287, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23287, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23283, 1);
					return;
				}
			}
			// Super Strength (2) into Super Strength Flask (1)
			if (usedWith.getId() == 159 || usedWith.getId() == 23289) {
				if (player.getInventory().containsItem(159, 1) && player.getInventory().containsItem(23289, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23289, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23285, 1);
					return;
				}
			}
			// Super Strength (3) into Super Strength Flask (5)
			if (usedWith.getId() == 157 || usedWith.getId() == 23281) {
				if (player.getInventory().containsItem(157, 1) && player.getInventory().containsItem(23281, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23281, 1);
					player.getInventory().addItem(159, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (3) into Super Strength Flask (4)
			if (usedWith.getId() == 157 || usedWith.getId() == 23283) {
				if (player.getInventory().containsItem(157, 1) && player.getInventory().containsItem(23283, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23283, 1);
					player.getInventory().addItem(161, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (3) into Super Strength Flask (3)
			if (usedWith.getId() == 157 || usedWith.getId() == 23285) {
				if (player.getInventory().containsItem(157, 1) && player.getInventory().containsItem(23285, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23285, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (3) into Super Strength Flask (2)
			if (usedWith.getId() == 157 || usedWith.getId() == 23287) {
				if (player.getInventory().containsItem(157, 1) && player.getInventory().containsItem(23287, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23287, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23281, 1);
					return;
				}
			}
			// Super Strength (3) into Super Strength Flask (1)
			if (usedWith.getId() == 157 || usedWith.getId() == 23289) {
				if (player.getInventory().containsItem(157, 1) && player.getInventory().containsItem(23289, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23289, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23283, 1);
					return;
				}
			}
			// Super Strength (4) into Super Strength Flask (5)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23281) {
				if (player.getInventory().containsItem(2440, 1) && player.getInventory().containsItem(23281, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23281, 1);
					player.getInventory().addItem(157, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (4) into Super Strength Flask (4)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23283) {
				if (player.getInventory().containsItem(2440, 1) && player.getInventory().containsItem(23283, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23283, 1);
					player.getInventory().addItem(159, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (4) into Super Strength Flask (3)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23285) {
				if (player.getInventory().containsItem(2440, 1) && player.getInventory().containsItem(23285, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23285, 1);
					player.getInventory().addItem(161, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (4) into Super Strength Flask (2)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23287) {
				if (player.getInventory().containsItem(2440, 1) && player.getInventory().containsItem(23287, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23287, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23279, 1);
					return;
				}
			}
			// Super Strength (4) into Super Strength Flask (1)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23289) {
				if (player.getInventory().containsItem(2440, 1) && player.getInventory().containsItem(23289, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23289, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23281, 1);
					return;
				}
			}
			// Super Strength (4) into Empty Flask
			if (usedWith.getId() == 2440 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(2440, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23283, 1);
					return;
				}
			}
			// Super Strength (3) into Empty Flask
			if (usedWith.getId() == 157 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(157, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23285, 1);
					return;
				}
			}
			// Super Strength (2) into Empty Flask
			if (usedWith.getId() == 159 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(159, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23287, 1);
					return;
				}
			}
			// Super Strength (1) into Empty Flask
			if (usedWith.getId() == 161 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(161, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23289, 1);
					return;
				}
			}
			// Super Defence (1) into Super Defence Flask (5)
			if (usedWith.getId() == 167 || usedWith.getId() == 23293) {
				if (player.getInventory().containsItem(167, 1) && player.getInventory().containsItem(23293, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23293, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (1) into Super Defence Flask (4)
			if (usedWith.getId() == 167 || usedWith.getId() == 23295) {
				if (player.getInventory().containsItem(167, 1) && player.getInventory().containsItem(23295, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23295, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23293, 1);
					return;
				}
			}
			// Super Defence (1) into Super Defence Flask (3)
			if (usedWith.getId() == 167 || usedWith.getId() == 23297) {
				if (player.getInventory().containsItem(167, 1) && player.getInventory().containsItem(23297, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23297, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23295, 1);
					return;
				}
			}
			// Super Defence (1) into Super Defence Flask (2)
			if (usedWith.getId() == 167 || usedWith.getId() == 23299) {
				if (player.getInventory().containsItem(167, 1) && player.getInventory().containsItem(23299, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23299, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23297, 1);
					return;
				}
			}
			// Super Defence (1) into Super Defence Flask (1)
			if (usedWith.getId() == 167 || usedWith.getId() == 23301) {
				if (player.getInventory().containsItem(167, 1) && player.getInventory().containsItem(23301, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23301, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23299, 1);
					return;
				}
			}
			// Super Defence (2) into Super Defence Flask (5)
			if (usedWith.getId() == 165 || usedWith.getId() == 23293) {
				if (player.getInventory().containsItem(165, 1) && player.getInventory().containsItem(23293, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23293, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (2) into Super Defence Flask (4)
			if (usedWith.getId() == 165 || usedWith.getId() == 23295) {
				if (player.getInventory().containsItem(165, 1) && player.getInventory().containsItem(23295, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23295, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (2) into Super Defence Flask (3)
			if (usedWith.getId() == 165 || usedWith.getId() == 23297) {
				if (player.getInventory().containsItem(165, 1) && player.getInventory().containsItem(23297, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23297, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23293, 1);
					return;
				}
			}
			// Super Defence (2) into Super Defence Flask (2)
			if (usedWith.getId() == 165 || usedWith.getId() == 23299) {
				if (player.getInventory().containsItem(165, 1) && player.getInventory().containsItem(23299, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23299, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23295, 1);
					return;
				}
			}
			// Super Defence (2) into Super Defence Flask (1)
			if (usedWith.getId() == 165 || usedWith.getId() == 23301) {
				if (player.getInventory().containsItem(165, 1) && player.getInventory().containsItem(23301, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23301, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23297, 1);
					return;
				}
			}
			// Super Defence (3) into Super Defence Flask (5)
			if (usedWith.getId() == 163 || usedWith.getId() == 23293) {
				if (player.getInventory().containsItem(163, 1) && player.getInventory().containsItem(23293, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23293, 1);
					player.getInventory().addItem(165, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (3) into Super Defence Flask (4)
			if (usedWith.getId() == 163 || usedWith.getId() == 23295) {
				if (player.getInventory().containsItem(163, 1) && player.getInventory().containsItem(23295, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23295, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (3) into Super Defence Flask (3)
			if (usedWith.getId() == 163 || usedWith.getId() == 23297) {
				if (player.getInventory().containsItem(163, 1) && player.getInventory().containsItem(23297, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23297, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (3) into Super Defence Flask (2)
			if (usedWith.getId() == 163 || usedWith.getId() == 23299) {
				if (player.getInventory().containsItem(163, 1) && player.getInventory().containsItem(23299, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23299, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23293, 1);
					return;
				}
			}
			// Super Defence (3) into Super Defence Flask (1)
			if (usedWith.getId() == 163 || usedWith.getId() == 23301) {
				if (player.getInventory().containsItem(163, 1) && player.getInventory().containsItem(23301, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23301, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23295, 1);
					return;
				}
			}
			// Super Defence (4) into Super Defence Flask (5)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23293) {
				if (player.getInventory().containsItem(2442, 1) && player.getInventory().containsItem(23293, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23293, 1);
					player.getInventory().addItem(163, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (4) into Super Defence Flask (4)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23295) {
				if (player.getInventory().containsItem(2442, 1) && player.getInventory().containsItem(23295, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23295, 1);
					player.getInventory().addItem(165, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (4) into Super Defence Flask (3)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23297) {
				if (player.getInventory().containsItem(2442, 1) && player.getInventory().containsItem(23297, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23297, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (4) into Super Defence Flask (2)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23299) {
				if (player.getInventory().containsItem(2442, 1) && player.getInventory().containsItem(23299, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23299, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23291, 1);
					return;
				}
			}
			// Super Defence (4) into Super Defence Flask (1)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23301) {
				if (player.getInventory().containsItem(2442, 1) && player.getInventory().containsItem(23301, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23301, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23293, 1);
					return;
				}
			}
			// Super Defence (4)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(2442, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23295, 1);
					return;
				}
			}
			// Super Defence (3)
			if (usedWith.getId() == 163 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(163, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23297, 1);
					return;
				}
			}
			// Super Defence (2)
			if (usedWith.getId() == 165 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(165, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23299, 1);
					return;
				}
			}
			// Super Defence (1)
			if (usedWith.getId() == 167 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(167, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23301, 1);
					return;
				}
			}
			// Prayer pot (1)
			if (usedWith.getId() == 143 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(143, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(143, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23253, 1);
					return;
				}
			}
			// Prayer pot (2)
			if (usedWith.getId() == 141 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(141, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(141, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23251, 1);
					return;
				}
			}
			// Prayer pot (3)
			if (usedWith.getId() == 139 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(139, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(139, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23249, 1);
					return;
				}
			}
			// Prayer pot (4)
			if (usedWith.getId() == 2434 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(2434, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(2434, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23247, 1);
					return;
				}
			}
			// Prayer pot (1) into prayer flask (1)
			if (usedWith.getId() == 143 || usedWith.getId() == 23253) {
				if (player.getInventory().containsItem(143, 1) && player.getInventory().containsItem(23253, 1)) {
					player.getInventory().deleteItem(143, 1);
					player.getInventory().deleteItem(23253, 1);
					player.getInventory().addItem(23251, 1);
					return;
				}
			}
			// Prayer pot (2) into prayer flask (1)
			if (usedWith.getId() == 141 || usedWith.getId() == 23253) {
				if (player.getInventory().containsItem(141, 1) && player.getInventory().containsItem(23253, 1)) {
					player.getInventory().deleteItem(141, 1);
					player.getInventory().deleteItem(23253, 1);
					player.getInventory().addItem(23249, 1);
					return;
				}
			}
			// Prayer pot (3) into prayer flask (1)
			if (usedWith.getId() == 139 || usedWith.getId() == 23253) {
				if (player.getInventory().containsItem(139, 1) && player.getInventory().containsItem(23253, 1)) {
					player.getInventory().deleteItem(139, 1);
					player.getInventory().deleteItem(23253, 1);
					player.getInventory().addItem(23247, 1);
					return;
				}
			}
			// Prayer pot (4) into prayer flask (1)
			if (usedWith.getId() == 2434 || usedWith.getId() == 23253) {
				if (player.getInventory().containsItem(2434, 1) && player.getInventory().containsItem(23253, 1)) {
					player.getInventory().deleteItem(2434, 1);
					player.getInventory().deleteItem(23253, 1);
					player.getInventory().addItem(23245, 1);
					return;
				}
			}
			// Prayer pot (1) into prayer flask (2)
			if (usedWith.getId() == 143 || usedWith.getId() == 23251) {
				if (player.getInventory().containsItem(143, 1) && player.getInventory().containsItem(23251, 1)) {
					player.getInventory().deleteItem(143, 1);
					player.getInventory().deleteItem(23251, 1);
					player.getInventory().addItem(23249, 1);
					return;
				}
			}
			// Prayer pot (2) into prayer flask (2)
			if (usedWith.getId() == 141 || usedWith.getId() == 23251) {
				if (player.getInventory().containsItem(141, 1) && player.getInventory().containsItem(23251, 1)) {
					player.getInventory().deleteItem(141, 1);
					player.getInventory().deleteItem(23251, 1);
					player.getInventory().addItem(23247, 1);
					return;
				}
			}
			// Prayer pot (3) into prayer flask (2)
			if (usedWith.getId() == 139 || usedWith.getId() == 23251) {
				if (player.getInventory().containsItem(139, 1) && player.getInventory().containsItem(23251, 1)) {
					player.getInventory().deleteItem(139, 1);
					player.getInventory().deleteItem(23251, 1);
					player.getInventory().addItem(23245, 1);
					return;
				}
			}
			// Prayer pot (4) into prayer flask (2)
			if (usedWith.getId() == 2434 || usedWith.getId() == 23251) {
				if (player.getInventory().containsItem(2434, 1) && player.getInventory().containsItem(23251, 1)) {
					player.getInventory().deleteItem(2434, 1);
					player.getInventory().deleteItem(23251, 1);
					player.getInventory().addItem(23243, 1);
					return;
				}
			}
			// Prayer pot (1) into prayer flask (3)
			if (usedWith.getId() == 143 || usedWith.getId() == 23249) {
				if (player.getInventory().containsItem(143, 1) && player.getInventory().containsItem(23249, 1)) {
					player.getInventory().deleteItem(143, 1);
					player.getInventory().deleteItem(23249, 1);
					player.getInventory().addItem(23247, 1);
					return;
				}
			}
			// Prayer pot (2) into prayer flask (3)
			if (usedWith.getId() == 141 || usedWith.getId() == 23249) {
				if (player.getInventory().containsItem(141, 1) && player.getInventory().containsItem(23249, 1)) {
					player.getInventory().deleteItem(141, 1);
					player.getInventory().deleteItem(23249, 1);
					player.getInventory().addItem(23245, 1);
					return;
				}
			}
			// Prayer pot (3) into prayer flask (3)
			if (usedWith.getId() == 139 || usedWith.getId() == 23249) {
				if (player.getInventory().containsItem(139, 1) && player.getInventory().containsItem(23249, 1)) {
					player.getInventory().deleteItem(139, 1);
					player.getInventory().deleteItem(23249, 1);
					player.getInventory().addItem(23243, 1);
					return;
				}
			}
			// Prayer pot (1) into prayer flask (4)
			if (usedWith.getId() == 143 || usedWith.getId() == 23247) {
				if (player.getInventory().containsItem(143, 1) && player.getInventory().containsItem(23247, 1)) {
					player.getInventory().deleteItem(143, 1);
					player.getInventory().deleteItem(23247, 1);
					player.getInventory().addItem(23245, 1);
					return;
				}
			}
			// Prayer pot (2) into prayer flask (4)
			if (usedWith.getId() == 141 || usedWith.getId() == 23247) {
				if (player.getInventory().containsItem(141, 1) && player.getInventory().containsItem(23247, 1)) {
					player.getInventory().deleteItem(141, 1);
					player.getInventory().deleteItem(23247, 1);
					player.getInventory().addItem(23243, 1);
					return;
				}
			}
			// Prayer pot (1) into prayer flask (5)
			if (usedWith.getId() == 143 || usedWith.getId() == 23245) {
				if (player.getInventory().containsItem(143, 1) && player.getInventory().containsItem(23245, 1)) {
					player.getInventory().deleteItem(143, 1);
					player.getInventory().deleteItem(23245, 1);
					player.getInventory().addItem(23243, 1);
					return;
				}
			}

			/**
			 * Overloads
			 */

			// Overload (1) into Overload Flask (5)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23532) {
				if (player.getInventory().containsItem(15335, 1) && player.getInventory().containsItem(26751, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23532, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (1) into Overload Flask (4)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23533) {
				if (player.getInventory().containsItem(15335, 1) && player.getInventory().containsItem(23533, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23533, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23532, 1); // id for (5)
					return;
				}
			}
			// Overload (1) into Overload Flask (3)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23534) {
				if (player.getInventory().containsItem(15335, 1) && player.getInventory().containsItem(23534, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23534, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23533, 1); // id for (4)
					return;
				}
			}
			// Overload (1) into Overload Flask (2)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23535) {
				if (player.getInventory().containsItem(15335, 1) && player.getInventory().containsItem(23535, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23535, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23534, 1); // id for (3)
					return;
				}
			}
			// Overload (1) into Overload Flask (1)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23536) {
				if (player.getInventory().containsItem(15335, 1) && player.getInventory().containsItem(23536, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23536, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23535, 1); // id for (2)
					return;
				}
			}
			// Overload (2) into Overload Flask (5)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23532) {
				if (player.getInventory().containsItem(15334, 1) && player.getInventory().containsItem(23532, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23532, 1);
					player.getInventory().addItem(15335, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (2) into Overload Flask (4)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23533) {
				if (player.getInventory().containsItem(15334, 1) && player.getInventory().containsItem(23533, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23533, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (2) into Overload Flask (3)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23534) {
				if (player.getInventory().containsItem(15334, 1) && player.getInventory().containsItem(23534, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23534, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23532, 1); // id for (5)
					return;
				}
			}
			// Overload (2) into Overload Flask (2)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23535) {
				if (player.getInventory().containsItem(15334, 1) && player.getInventory().containsItem(23535, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23535, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23533, 1); // id for (4)
					return;
				}
			}
			// Overload (2) into Overload Flask (1)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23536) {
				if (player.getInventory().containsItem(15334, 1) && player.getInventory().containsItem(23536, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23536, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23534, 1); // id for (3)
					return;
				}
			}
			// Overload (3) into Overload Flask (5)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23532) {
				if (player.getInventory().containsItem(15333, 1) && player.getInventory().containsItem(23532, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23532, 1);
					player.getInventory().addItem(15334, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (3) into Overload Flask (4)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23533) {
				if (player.getInventory().containsItem(15333, 1) && player.getInventory().containsItem(23533, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23533, 1);
					player.getInventory().addItem(15335, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (3) into Overload Flask (3)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23534) {
				if (player.getInventory().containsItem(15333, 1) && player.getInventory().containsItem(23534, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23534, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (3) into Overload Flask (2)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23535) {
				if (player.getInventory().containsItem(15333, 1) && player.getInventory().containsItem(23535, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23535, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23532, 1); // id for (5)
					return;
				}
			}
			// Overload (3) into Overload Flask (1)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23536) {
				if (player.getInventory().containsItem(15333, 1) && player.getInventory().containsItem(23536, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23536, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23533, 1); // id for (4)
					return;
				}
			}
			// Overload (4) into Overload Flask (5)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23532) {
				if (player.getInventory().containsItem(15332, 1) && player.getInventory().containsItem(23532, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23532, 1);
					player.getInventory().addItem(15333, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (4) into Overload Flask (4)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23533) {
				if (player.getInventory().containsItem(15332, 1) && player.getInventory().containsItem(23533, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23533, 1);
					player.getInventory().addItem(15334, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (4) into Overload Flask (3)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23534) {
				if (player.getInventory().containsItem(15332, 1) && player.getInventory().containsItem(23534, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23534, 1);
					player.getInventory().addItem(15335, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (4) into Overload Flask (2)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23535) {
				if (player.getInventory().containsItem(15332, 1) && player.getInventory().containsItem(23535, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23535, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
					return;
				}
			}
			// Overload (4) into Overload Flask (1)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23536) {
				if (player.getInventory().containsItem(15332, 1) && player.getInventory().containsItem(23536, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23536, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23532, 1); // id for (5)
					return;
				}
			}
			// Overload (1)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(15335, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23536, 1);
					return;
				}
			}
			// Overload (2)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(15334, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23535, 1);
					return;
				}
			}
			// Overload (3)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(15333, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23534, 1);
					return;
				}
			}
			// Overload (4)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(15332, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23533, 1);
					return;
				}
			}
			// Sarabrews (4)
			if (usedWith.getId() == 6685 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(6685, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(6685, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23355, 1);
					return;
				}
			}
			// Sarabrews (3)
			if (usedWith.getId() == 6687 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(6687, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(6687, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23357, 1);
					return;
				}
			}
			// Sarabrews (2)
			if (usedWith.getId() == 6689 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(6689, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(6689, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23359, 1);
					return;
				}
			}
			// Sarabrews (1)
			if (usedWith.getId() == 6691 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(6691, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(6691, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23361, 1);
					return;
				}
			}
			// sara (1) into sara Flask (5)
			if (usedWith.getId() == 6691 || usedWith.getId() == 23353) {
				if (player.getInventory().containsItem(6691, 1) && player.getInventory().containsItem(23353, 1)) {
					player.getInventory().deleteItem(6691, 1);
					player.getInventory().deleteItem(23353, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (1) into Sara Flask (4)
			if (usedWith.getId() == 6691 || usedWith.getId() == 23355) {
				if (player.getInventory().containsItem(6691, 1) && player.getInventory().containsItem(23355, 1)) {
					player.getInventory().deleteItem(6691, 1);
					player.getInventory().deleteItem(23355, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23353, 1);
					return;
				}
			}
			// Sara (1) into Sara (3)
			if (usedWith.getId() == 6691 || usedWith.getId() == 23357) {
				if (player.getInventory().containsItem(6691, 1) && player.getInventory().containsItem(23357, 1)) {
					player.getInventory().deleteItem(6691, 1);
					player.getInventory().deleteItem(23357, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23355, 1);
					return;
				}
			}
			// Sara (1) into Sara flask (2)
			if (usedWith.getId() == 6691 || usedWith.getId() == 23359) {
				if (player.getInventory().containsItem(6691, 1) && player.getInventory().containsItem(23359, 1)) {
					player.getInventory().deleteItem(6691, 1);
					player.getInventory().deleteItem(23359, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23357, 1);
					return;
				}
			}
			// Sara(1) into Sara Flask (1)
			if (usedWith.getId() == 6691 || usedWith.getId() == 23361) {
				if (player.getInventory().containsItem(6691, 1) && player.getInventory().containsItem(23361, 1)) {
					player.getInventory().deleteItem(6691, 1);
					player.getInventory().deleteItem(23361, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23359, 1);
					return;
				}
			}
			// sara (2) into sara Flask (5)
			if (usedWith.getId() == 6689 || usedWith.getId() == 23353) {
				if (player.getInventory().containsItem(6689, 1) && player.getInventory().containsItem(23353, 1)) {
					player.getInventory().deleteItem(6689, 1);
					player.getInventory().deleteItem(23353, 1);
					player.getInventory().addItem(6691, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (2) into Sara Flask (4)
			if (usedWith.getId() == 6689 || usedWith.getId() == 23355) {
				if (player.getInventory().containsItem(6689, 1) && player.getInventory().containsItem(23355, 1)) {
					player.getInventory().deleteItem(6689, 1);
					player.getInventory().deleteItem(23355, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (2) into Sara (3)
			if (usedWith.getId() == 6689 || usedWith.getId() == 23357) {
				if (player.getInventory().containsItem(6689, 1) && player.getInventory().containsItem(23357, 1)) {
					player.getInventory().deleteItem(6689, 1);
					player.getInventory().deleteItem(23357, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23353, 1);
					return;
				}
			}
			// Sara (2) into Sara flask (2)
			if (usedWith.getId() == 6689 || usedWith.getId() == 23359) {
				if (player.getInventory().containsItem(6689, 1) && player.getInventory().containsItem(23359, 1)) {
					player.getInventory().deleteItem(6689, 1);
					player.getInventory().deleteItem(23359, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23355, 1);
					return;
				}
			}
			// Sara(2) into Sara Flask (1)
			if (usedWith.getId() == 6689 || usedWith.getId() == 23361) {
				if (player.getInventory().containsItem(6689, 1) && player.getInventory().containsItem(23361, 1)) {
					player.getInventory().deleteItem(6689, 1);
					player.getInventory().deleteItem(23361, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23358, 1);
					return;
				}
			}
			// sara (3) into sara Flask (5)
			if (usedWith.getId() == 6687 || usedWith.getId() == 23353) {
				if (player.getInventory().containsItem(6687, 1) && player.getInventory().containsItem(23353, 1)) {
					player.getInventory().deleteItem(6687, 1);
					player.getInventory().deleteItem(23353, 1);
					player.getInventory().addItem(6689, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (3) into Sara Flask (4)
			if (usedWith.getId() == 6687 || usedWith.getId() == 23355) {
				if (player.getInventory().containsItem(6687, 1) && player.getInventory().containsItem(23355, 1)) {
					player.getInventory().deleteItem(6687, 1);
					player.getInventory().deleteItem(23355, 1);
					player.getInventory().addItem(6691, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (3) into Sara (3)
			if (usedWith.getId() == 6687 || usedWith.getId() == 23357) {
				if (player.getInventory().containsItem(6687, 1) && player.getInventory().containsItem(23357, 1)) {
					player.getInventory().deleteItem(6687, 1);
					player.getInventory().deleteItem(23357, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (3) into Sara flask (2)
			if (usedWith.getId() == 6687 || usedWith.getId() == 23359) {
				if (player.getInventory().containsItem(6687, 1) && player.getInventory().containsItem(23359, 1)) {
					player.getInventory().deleteItem(6687, 1);
					player.getInventory().deleteItem(23359, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23353, 1);
					return;
				}
			}
			// Sara(3) into Sara Flask (1)
			if (usedWith.getId() == 6687 || usedWith.getId() == 23361) {
				if (player.getInventory().containsItem(6687, 1) && player.getInventory().containsItem(23361, 1)) {
					player.getInventory().deleteItem(6687, 1);
					player.getInventory().deleteItem(23361, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23355, 1);
					return;
				}
			}
			// sara (4) into sara Flask (5)
			if (usedWith.getId() == 6685 || usedWith.getId() == 23353) {
				if (player.getInventory().containsItem(6685, 1) && player.getInventory().containsItem(23353, 1)) {
					player.getInventory().deleteItem(6685, 1);
					player.getInventory().deleteItem(23353, 1);
					player.getInventory().addItem(6687, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (4) into Sara Flask (4)
			if (usedWith.getId() == 6685 || usedWith.getId() == 23355) {
				if (player.getInventory().containsItem(6685, 1) && player.getInventory().containsItem(23355, 1)) {
					player.getInventory().deleteItem(6685, 1);
					player.getInventory().deleteItem(23355, 1);
					player.getInventory().addItem(6689, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (4) into Sara (3)
			if (usedWith.getId() == 6685 || usedWith.getId() == 23357) {
				if (player.getInventory().containsItem(6685, 1) && player.getInventory().containsItem(23357, 1)) {
					player.getInventory().deleteItem(6685, 1);
					player.getInventory().deleteItem(23357, 1);
					player.getInventory().addItem(6691, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara (4) into Sara flask (2)
			if (usedWith.getId() == 6685 || usedWith.getId() == 23359) {
				if (player.getInventory().containsItem(6685, 1) && player.getInventory().containsItem(23359, 1)) {
					player.getInventory().deleteItem(6685, 1);
					player.getInventory().deleteItem(23359, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23351, 1);
					return;
				}
			}
			// Sara(4) into Sara Flask (1)
			if (usedWith.getId() == 6685 || usedWith.getId() == 23361) {
				if (player.getInventory().containsItem(6685, 1) && player.getInventory().containsItem(23361, 1)) {
					player.getInventory().deleteItem(6685, 1);
					player.getInventory().deleteItem(23361, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23353, 1);
					return;
				}
			}
			// Super restore (4)
			if (usedWith.getId() == 3024 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(3024, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(3024, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23403, 1);
					return;
				}
			}
			// Super restore (3)
			if (usedWith.getId() == 3026 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(3026, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(3026, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23405, 1);
					return;
				}
			}
			// Super restore (2)
			if (usedWith.getId() == 3028 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(3028, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(3028, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23407, 1);
					return;
				}
			}
			// Super restore (1)
			if (usedWith.getId() == 3030 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(3030, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(3030, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23409, 1);
					return;
				}
			}
			// Super restore (1) into flask (1)
			if (usedWith.getId() == 3030 || usedWith.getId() == 23409) {
				if (player.getInventory().containsItem(3030, 1) && player.getInventory().containsItem(23409, 1)) {
					player.getInventory().deleteItem(3030, 1);
					player.getInventory().deleteItem(23409, 1);
					player.getInventory().addItem(23407, 1);
					return;
				}
			}
			// Super restore (2) into flask (1)
			if (usedWith.getId() == 3028 || usedWith.getId() == 23409) {
				if (player.getInventory().containsItem(3028, 1) && player.getInventory().containsItem(23409, 1)) {
					player.getInventory().deleteItem(3028, 1);
					player.getInventory().deleteItem(23409, 1);
					player.getInventory().addItem(23405, 1);
					return;
				}
			}
			// Super restore (3) into flask (1)
			if (usedWith.getId() == 3026 || usedWith.getId() == 23409) {
				if (player.getInventory().containsItem(3026, 1) && player.getInventory().containsItem(23409, 1)) {
					player.getInventory().deleteItem(3026, 1);
					player.getInventory().deleteItem(23409, 1);
					player.getInventory().addItem(23403, 1);
					return;
				}
			}
			// Super restore (4) into flask (1)
			if (usedWith.getId() == 3024 || usedWith.getId() == 23409) {
				if (player.getInventory().containsItem(3024, 1) && player.getInventory().containsItem(23409, 1)) {
					player.getInventory().deleteItem(3024, 1);
					player.getInventory().deleteItem(23409, 1);
					player.getInventory().addItem(23401, 1);
					return;
				}
			}
			// Super restore (1) into flask (2)
			if (usedWith.getId() == 3030 || usedWith.getId() == 23407) {
				if (player.getInventory().containsItem(3030, 1) && player.getInventory().containsItem(23407, 1)) {
					player.getInventory().deleteItem(3030, 1);
					player.getInventory().deleteItem(23407, 1);
					player.getInventory().addItem(23405, 1);
					return;
				}
			}
			// Super restore (2) into flask (2)
			if (usedWith.getId() == 3028 || usedWith.getId() == 23407) {
				if (player.getInventory().containsItem(3028, 1) && player.getInventory().containsItem(23407, 1)) {
					player.getInventory().deleteItem(3028, 1);
					player.getInventory().deleteItem(23407, 1);
					player.getInventory().addItem(23403, 1);
					return;
				}
			}
			// Super restore (3) into flask (2)
			if (usedWith.getId() == 3026 || usedWith.getId() == 23407) {
				if (player.getInventory().containsItem(3026, 1) && player.getInventory().containsItem(23407, 1)) {
					player.getInventory().deleteItem(3026, 1);
					player.getInventory().deleteItem(23407, 1);
					player.getInventory().addItem(23401, 1);
					return;
				}
			}
			// Super restore (4) into flask (2)
			if (usedWith.getId() == 3024 || usedWith.getId() == 23407) {
				if (player.getInventory().containsItem(3024, 1) && player.getInventory().containsItem(23407, 1)) {
					player.getInventory().deleteItem(3024, 1);
					player.getInventory().deleteItem(23407, 1);
					player.getInventory().addItem(23399, 1);
					return;
				}
			}
			// Super restore (1) into flask (3)
			if (usedWith.getId() == 3030 || usedWith.getId() == 23405) {
				if (player.getInventory().containsItem(3030, 1) && player.getInventory().containsItem(23405, 1)) {
					player.getInventory().deleteItem(3030, 1);
					player.getInventory().deleteItem(23405, 1);
					player.getInventory().addItem(23403, 1);
					return;
				}
			}
			// Super restore (2) into flask (3)
			if (usedWith.getId() == 3028 || usedWith.getId() == 23405) {
				if (player.getInventory().containsItem(3028, 1) && player.getInventory().containsItem(23405, 1)) {
					player.getInventory().deleteItem(3028, 1);
					player.getInventory().deleteItem(23405, 1);
					player.getInventory().addItem(23401, 1);
					return;
				}
			}
			// Super restore (3) into flask (3)
			if (usedWith.getId() == 3026 || usedWith.getId() == 23405) {
				if (player.getInventory().containsItem(3026, 1) && player.getInventory().containsItem(23405, 1)) {
					player.getInventory().deleteItem(3026, 1);
					player.getInventory().deleteItem(23405, 1);
					player.getInventory().addItem(23399, 1);
					return;
				}
			}
			// Super restore (1) into flask (4)
			if (usedWith.getId() == 3030 || usedWith.getId() == 23403) {
				if (player.getInventory().containsItem(3030, 1) && player.getInventory().containsItem(23403, 1)) {
					player.getInventory().deleteItem(3030, 1);
					player.getInventory().deleteItem(23403, 1);
					player.getInventory().addItem(23401, 1);
					return;
				}
			}
			// Super restore (2) into flask (4)
			if (usedWith.getId() == 3028 || usedWith.getId() == 23403) {
				if (player.getInventory().containsItem(3028, 1) && player.getInventory().containsItem(23403, 1)) {
					player.getInventory().deleteItem(3028, 1);
					player.getInventory().deleteItem(23403, 1);
					player.getInventory().addItem(23399, 1);
					return;
				}
			}
			// Super restore (1) into flask (5)
			if (usedWith.getId() == 3030 || usedWith.getId() == 23401) {
				if (player.getInventory().containsItem(3030, 1) && player.getInventory().containsItem(23401, 1)) {
					player.getInventory().deleteItem(3030, 1);
					player.getInventory().deleteItem(23401, 1);
					player.getInventory().addItem(23399, 1);
					return;
				}
			}
			// Prayer renewal (1)
			if (usedWith.getId() == 21636 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(21636, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(21636, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23619, 1);
					return;
				}
			}
			// Prayer renewal (2)
			if (usedWith.getId() == 21634 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(21634, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(21634, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23617, 1);
					return;
				}
			}
			// Prayer renewal (3)
			if (usedWith.getId() == 21632 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(21632, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(21632, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23615, 1);
					return;
				}
			}
			// Prayer renewal (4)
			if (usedWith.getId() == 21630 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(21630, 1) && player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(21630, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23613, 1);
					return;
				}
			}
			// Prayer renewal (1) into flask (1)
			if (usedWith.getId() == 21636 || usedWith.getId() == 23619) {
				if (player.getInventory().containsItem(21636, 1) && player.getInventory().containsItem(23619, 1)) {
					player.getInventory().deleteItem(21636, 1);
					player.getInventory().deleteItem(23619, 1);
					player.getInventory().addItem(23617, 1);
					return;
				}
			}
			// Prayer renewal (2) into flask (1)
			if (usedWith.getId() == 21634 || usedWith.getId() == 23619) {
				if (player.getInventory().containsItem(21634, 1) && player.getInventory().containsItem(23619, 1)) {
					player.getInventory().deleteItem(21634, 1);
					player.getInventory().deleteItem(23619, 1);
					player.getInventory().addItem(23615, 1);
					return;
				}
			}
			// Prayer renewal (3) into flask (1)
			if (usedWith.getId() == 21632 || usedWith.getId() == 23619) {
				if (player.getInventory().containsItem(21632, 1) && player.getInventory().containsItem(23619, 1)) {
					player.getInventory().deleteItem(21632, 1);
					player.getInventory().deleteItem(23619, 1);
					player.getInventory().addItem(23613, 1);
					return;
				}
			}
			// Prayer renewal (4) into flask (1)
			if (usedWith.getId() == 21630 || usedWith.getId() == 23619) {
				if (player.getInventory().containsItem(21630, 1) && player.getInventory().containsItem(23619, 1)) {
					player.getInventory().deleteItem(21630, 1);
					player.getInventory().deleteItem(23619, 1);
					player.getInventory().addItem(23611, 1);
					return;
				}
			}
			// Prayer renewal (1) into flask (2)
			if (usedWith.getId() == 21636 || usedWith.getId() == 23617) {
				if (player.getInventory().containsItem(21636, 1) && player.getInventory().containsItem(23617, 1)) {
					player.getInventory().deleteItem(21636, 1);
					player.getInventory().deleteItem(23617, 1);
					player.getInventory().addItem(23615, 1);
					return;
				}
			}
			// Prayer renewal (2) into flask (2)
			if (usedWith.getId() == 21634 || usedWith.getId() == 23617) {
				if (player.getInventory().containsItem(21634, 1) && player.getInventory().containsItem(23617, 1)) {
					player.getInventory().deleteItem(21634, 1);
					player.getInventory().deleteItem(23617, 1);
					player.getInventory().addItem(23613, 1);
					return;
				}
			}
			// Prayer renewal (3) into flask (2)
			if (usedWith.getId() == 21632 || usedWith.getId() == 23617) {
				if (player.getInventory().containsItem(21632, 1) && player.getInventory().containsItem(23617, 1)) {
					player.getInventory().deleteItem(21632, 1);
					player.getInventory().deleteItem(23617, 1);
					player.getInventory().addItem(23611, 1);
					return;
				}
			}
			// Prayer renewal (4) into flask (2)
			if (usedWith.getId() == 21630 || usedWith.getId() == 23617) {
				if (player.getInventory().containsItem(21630, 1) && player.getInventory().containsItem(23617, 1)) {
					player.getInventory().deleteItem(21630, 1);
					player.getInventory().deleteItem(23617, 1);
					player.getInventory().addItem(23609, 1);
					return;
				}
			}
			// Prayer renewal (1) into flask (3)
			if (usedWith.getId() == 21636 || usedWith.getId() == 23615) {
				if (player.getInventory().containsItem(21636, 1) && player.getInventory().containsItem(23615, 1)) {
					player.getInventory().deleteItem(21636, 1);
					player.getInventory().deleteItem(23615, 1);
					player.getInventory().addItem(23613, 1);
					return;
				}
			}
			// Prayer renewal (2) into flask (3)
			if (usedWith.getId() == 21634 || usedWith.getId() == 23615) {
				if (player.getInventory().containsItem(21634, 1) && player.getInventory().containsItem(23615, 1)) {
					player.getInventory().deleteItem(21634, 1);
					player.getInventory().deleteItem(23615, 1);
					player.getInventory().addItem(23611, 1);
					return;
				}
			}
			// Prayer renewal (3) into flask (3)
			if (usedWith.getId() == 21632 || usedWith.getId() == 23615) {
				if (player.getInventory().containsItem(21632, 1) && player.getInventory().containsItem(23615, 1)) {
					player.getInventory().deleteItem(21632, 1);
					player.getInventory().deleteItem(23615, 1);
					player.getInventory().addItem(23609, 1);
					return;
				}
			}
			// Prayer renewal (1) into flask (4)
			if (usedWith.getId() == 21636 || usedWith.getId() == 23613) {
				if (player.getInventory().containsItem(21636, 1) && player.getInventory().containsItem(23613, 1)) {
					player.getInventory().deleteItem(21636, 1);
					player.getInventory().deleteItem(23613, 1);
					player.getInventory().addItem(23611, 1);
					return;
				}
			}
			// Prayer renewal (2) into flask (4)
			if (usedWith.getId() == 21634 || usedWith.getId() == 23613) {
				if (player.getInventory().containsItem(21634, 1) && player.getInventory().containsItem(23613, 1)) {
					player.getInventory().deleteItem(21634, 1);
					player.getInventory().deleteItem(23613, 1);
					player.getInventory().addItem(23609, 1);
					return;
				}
			}
			// Prayer renewal (1) into flask (5)
			if (usedWith.getId() == 21636 || usedWith.getId() == 23611) {
				if (player.getInventory().containsItem(21636, 1) && player.getInventory().containsItem(23611, 1)) {
					player.getInventory().deleteItem(21636, 1);
					player.getInventory().deleteItem(23611, 1);
					player.getInventory().addItem(23609, 1);
					return;
				}
			}
			if (PotionDecanting.mixPot(player, fromItem, toItem, fromSlot, toSlot))
				return;
			if (FlaskDecanting.mixPot(player, fromItem, toItem, fromSlot, toSlot))
				return;
			if (Firemaking.isFiremaking(player, itemUsed, usedWith))
				return;
			// Gem Cutting
			else if (contains(1755, Gem.LIMESTONE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.LIMESTONE);
			else if (contains(1755, Gem.OPAL.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.OPAL);
			else if (contains(1755, Gem.JADE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.JADE);
			else if (contains(1755, Gem.RED_TOPAZ.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.RED_TOPAZ);
			else if (contains(1755, Gem.SAPPHIRE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.SAPPHIRE);
			else if (contains(1755, Gem.EMERALD.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.EMERALD);
			else if (contains(1755, Gem.RUBY.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.RUBY);
			else if (contains(1755, Gem.DIAMOND.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.DIAMOND);
			else if (contains(1755, Gem.DRAGONSTONE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.DRAGONSTONE);
			else if (contains(1755, Gem.ONYX.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.ONYX);
			else if (contains(1755, Gem.SLAYER_TABLET.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.SLAYER_TABLET);
			else if (contains(1755, Gem.HYDRIX.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.HYDRIX);
			// Bolt Tip Making
			else if (contains(1755, BoltTips.OPAL.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.OPAL);
			else if (contains(1755, BoltTips.JADE.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.JADE);
			else if (contains(1755, BoltTips.RED_TOPAZ.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.RED_TOPAZ);
			else if (contains(1755, BoltTips.SAPPHIRE.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.SAPPHIRE);
			else if (contains(1755, BoltTips.EMERALD.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.EMERALD);
			else if (contains(1755, BoltTips.RUBY.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.RUBY);
			else if (contains(1755, BoltTips.DIAMOND.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.DIAMOND);
			else if (contains(1755, BoltTips.DRAGONSTONE.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.DRAGONSTONE);
			else if (contains(1755, BoltTips.ONYX.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.ONYX);

			else if (itemUsed.getId() == 21369 && usedWith.getId() == 4151) {
				player.getInventory().deleteItem(21369, 1);
				player.getInventory().deleteItem(4151, 1);
				player.getInventory().addItem(21371, 1);
				player.getPackets().sendGameMessage("Good job, you have succesfully combined a whip and vine into a vine whip.");
			} else if (itemUsed.getId() == 4151 && usedWith.getId() == 21369) {
				player.getInventory().deleteItem(21369, 1);
				player.getInventory().deleteItem(4151, 1);
				player.getInventory().addItem(21371, 1);
				player.getPackets().sendGameMessage("Good job, you have succesfully combined a whip and vine into a vine whip.");
			} else if (itemUsed.getId() == 13734 && usedWith.getId() == 13754) {
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().addItem(13736, 1);
				player.getPackets().sendGameMessage("You have poured the holy elixir on a spirit shield making it unleash Blessed powers.");
			} else if (itemUsed.getId() == 13754 && usedWith.getId() == 13734) {
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().addItem(13736, 1);
				player.getPackets().sendGameMessage("You have poured the holy elixir on a spirit shield making it unleash Blessed powers.");
			} else if (itemUsed.getId() == 13736 && usedWith.getId() == 13748) {
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13748, 1);
				player.getInventory().addItem(13740, 1);
				player.getAchievementManager().notifyUpdate(TheCreatorIIAchievement.class);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Divine Powers.");
			} else if (itemUsed.getId() == 13736 && usedWith.getId() == 13750) {
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13750, 1);
				player.getInventory().addItem(13742, 1);
				player.getAchievementManager().notifyUpdate(TheCreatorIIAchievement.class);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Elysian Powers.");
			} else if (itemUsed.getId() == 13736 && usedWith.getId() == 13746) {
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().addItem(13738, 1);
				player.getAchievementManager().notifyUpdate(TheCreatorIIAchievement.class);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Arcane Powers.");
			} else if (itemUsed.getId() == 13746 && usedWith.getId() == 13736) {
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().addItem(13738, 1);
				player.getAchievementManager().notifyUpdate(TheCreatorIIAchievement.class);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Arcane Powers.");
			} else if (itemUsed.getId() == 13736 && usedWith.getId() == 13752) {
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().addItem(13744, 1);
				player.getAchievementManager().notifyUpdate(TheCreatorIIAchievement.class);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Spectral Powers.");
			} else if (itemUsed.getId() == 13752 && usedWith.getId() == 13736) {
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().addItem(13744, 1);
				player.getAchievementManager().notifyUpdate(TheCreatorIIAchievement.class);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Spectral Powers.");
			} else
				;
			// player.getPackets().sendGameMessage(
			// "Nothing interesting happens.");
			Logger.log("ItemHandler", "Used:" + itemUsed.getId() + ", With:" + usedWith.getId());
		}
	}

	public static void handleItemOption3(Player player, int slotId, int itemId, Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (itemId == 20767 || itemId == 20769 || itemId == 20771 || itemId == 32151)
			SkillCapeCustomizer.startCustomizing(player, itemId);
		// else if(itemId >= 15084 && itemId <= 15100)
		// player.getDialogueManager().startDialogue("DiceBag", itemId);
		if (itemId == 4155) {
			player.getSlayerManager().checkKillsLeft();
		}
		if (Upgradables.byProduct(itemId) != null) {
			if (ItemUpgradeKits.RemoveKitFromItem(player, itemId)) {
				return;
			}
		}
		Masks mask = Masks.forId(item.getId());
		if (mask != null) {
			SlayerMasks.handleTeleport(mask, player);
			return;
		}
		Masks helm = Masks.forHelm(item.getId());
		if (helm != null) {
			SlayerMasks.handleTeleport(helm, player);
			return;
		}
		FlyingEntities impJar = FlyingEntities.forItem((short) itemId);
		if (impJar != null)
			Hunter.openJar(player, impJar, slotId);

		if (item.getDefinitions().isBindItem())
			player.getDungManager().bind(item, slotId);
		if (itemId == 31846) {
			String npcName = "";
			if (!(player.getContract() != null)) {
				ContractHandler.getNewContract(player);
				npcName = NPCDefinitions.getNPCDefinitions(player.getContract().getNpcId()).getName();
				player.sendMessage(Colors.orange + "Reaper Contract: " + player.getContract().getKillAmount() + "x " + npcName + ".");
			} else
				player.sendMessage("You already have an active Reaper contract.");
			return;
		}else if(itemId == 26358)
			player.sm("Your are now in the "+player.getPorts().getCurrentRegion().name()+ " region.");
		else if (itemId == 12160) {
			if (player.getInventory().containsItem(31312, 1) && player.getInventory().containsItem(12160, 5)) {
				player.getInventory().deleteItem(31312, 1);
				player.getInventory().deleteItem(12160, 5);
				player.getInventory().addItem(12163, 1);
				player.sm("You succesfully converted your crimson charms into a blue charm.");
			} else if (!player.getInventory().containsItem(31312, 1)) {
				player.sm("You need 1 elder energy to upgrade your crimson charms.");
			} else if (!player.getInventory().containsItem(12160, 5)) {
				player.sm("You need 5 crimson charms to upgrade it into a blue charm.");
			}
		}
		Crate crate = Crate.forCrateId(itemId);
		if (crate != null) {
			crate.getCrateMessage(player);
			return;
		}
		if (itemId == 32703) {
			player.sm("Your amulet has " + player.getBloodcharges() + " charges.");
		}
		// if(item instanceof DegradeAbleItem)
		// player.sm(" "+((DegradeAbleItem)(item)).getCharges());
		else if (PrayerBooks.isGodBook(itemId, true))
			PrayerBooks.sermanize(player, itemId);
		else if (ItemConstants.itemDegradesWhileCombating(itemId) == true) {
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);
			for (Number n : Arrays.asList(player.getCharges().getCurrentCharges(itemId) / 600)) {
				Double d = n.doubleValue();
				player.sm("Your item has " + df.format(d) + "% left.");
			}
		} else if (itemId == 21371) {
			player.getInventory().replaceItem(4151, 1, slotId);
			player.getInventory().addItem(21369, 1);
			player.getPackets().sendGameMessage("You split the whip vine from the abbysal whip.");
		} else if (itemId == 11694 || itemId == 11696 || itemId == 11698 || itemId == 11700)
			GodswordCreating.dismantleGS(player, item, slotId);
		else if (itemId == 24437 || itemId == 24439 || itemId == 24440 || itemId == 24441)
			player.getDialogueManager().startDialogue("FlamingSkull", item, slotId);
		else if (Equipment.getItemSlot(itemId) == Equipment.SLOT_AURA)
			player.getAuraManager().sendTimeRemaining(itemId);

		else if (itemId == 18338) {
			if (player.sapphires <= 0 && player.rubies <= 0 && player.emeralds <= 0 && player.diamonds <= 0) {
				player.sm("Your gem pouch is currently empty.");
			} else {
				player.emeralds = 0;
				player.sapphires = 0;
				player.rubies = 0;
				player.diamonds = 0;
				player.sm("You have successfully emptied your gem bag.");
			}
		} else if (itemId == 18339) {
			if (player.coal <= 0) {
				player.sm("Your coal pouch is currently empty.");
			} else {
				player.getInventory().addItem(454, player.coal);
				player.coal = 0;
				player.sm("You have successfully emptied your coal bag.");
			}
		} else if (itemId == 21576) {

			player.sm("The medallion currently has: " + player.drakanCharges + " charges.");

		} else if (itemId == 11014) {
			// player.sendMessage("Test3");
			player.getDialogueManager().startDialogue("SummonSlayerportal");
			return;

		} else if (itemId == 15440 || itemId == 15439) {
			if (player.horn == 0)
				player.getPackets().sendGameMessage("Your penance horn is out of charges.");
			else
				player.getPackets().sendGameMessage("Your penance horn currently holds " + player.horn + " charges.");
		} else if (itemId == 29981)
			player.setPrayerDelay(Short.MAX_VALUE);
		else if (itemId == 4151) {
			if (player.getInventory().containsItem(29932, 1)) {
				player.getInventory().deleteItem(29932, 1);
				player.getInventory().deleteItem(4151, 1);
				player.getInventory().addItem(29974, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have one upgrade token to upgrade this weapon.");
			}
		} else if (itemId == 1275) {
			if (player.getInventory().containsItem(29932, 1)) {
				player.getInventory().deleteItem(29932, 1);
				player.getInventory().deleteItem(1275, 1);
				player.getInventory().addItem(29925, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have one upgrade token to upgrade this weapon.");
			}
		} else if (itemId == 14484) {
			if (player.getInventory().containsItem(29932, 2)) {
				player.getInventory().deleteItem(29932, 2);
				player.getInventory().deleteItem(14484, 1);
				player.getInventory().addItem(29969, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
			}
		}

		// player.sm("Charges:"+item.getCharges());
		Logger.log("ItemHandler 3", "Item Select:" + itemId + ", Slot Id:" + slotId);
	}

	public static void handleItemOption4(Player player, int slotId, int itemId, Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);

		if (itemId == 21371) {
			if (player.getInventory().containsItem(29932, 3)) {
				player.getInventory().deleteItem(29932, 3);
				player.getInventory().deleteItem(21371, 1);
				player.getInventory().addItem(29962, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have three upgrade token to upgrade this weapon.");
			}
		} else if (itemId == 11694) {
			if (player.getInventory().containsItem(29932, 2)) {
				player.getInventory().deleteItem(29932, 2);
				player.getInventory().deleteItem(11694, 1);
				player.getInventory().addItem(29973, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
			}
		} else if (itemId == 11696) {
			if (player.getInventory().containsItem(29932, 2)) {
				player.getInventory().deleteItem(29932, 2);
				player.getInventory().deleteItem(11696, 1);
				player.getInventory().addItem(29972, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
			}
		} else if (itemId == 11698) {
			if (player.getInventory().containsItem(29932, 2)) {
				player.getInventory().deleteItem(29932, 2);
				player.getInventory().deleteItem(11698, 1);
				player.getInventory().addItem(29971, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
			}
		} else if (itemId == 11700) {
			if (player.getInventory().containsItem(29932, 2)) {
				player.getInventory().deleteItem(29932, 2);
				player.getInventory().deleteItem(11700, 1);
				player.getInventory().addItem(29970, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
			}
		}
		Logger.log("ItemHandler 4", "Item Select:" + itemId + ", Slot Id:" + slotId);
	}

	public static void handleItemOption5(Player player, int slotId, int itemId, Item item) {
		System.out.println("Item: " + item.getId() + " Option 5.");
		Logger.log("ItemHandler 5", "Item Select:" + itemId + ", Slot Id:" + slotId);
	}

	private static String getFormattedNumber(int amount) {
		return new DecimalFormat("#,###,##0").format(amount).toString();
	}

	public static void handleItemOption6(Player player, int slotId, int itemId, Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (player.getToolbelt().addItem(slotId, item))
			return;
		if(ItemDefinitions.getItemDefinitions(item.getId()).containsOption("Drink")){
			player.getInventory().replaceItem(229, 1, slotId);
		}
		/*
		 * if (SlayerMasks.isMask(itemId)){
		 * SlayerMasks.convertMaskToHelm(player,item); }
		 */
		CuttableFruit fruit = CuttableFruit.forId(item.getId());
		if (fruit != null) {
			player.getDialogueManager().startDialogue("FruitCuttingD", fruit);
		}
		Masks mask = Masks.forId(itemId);
		if(mask != null)
			SlayerMasks.transform(player,mask,true);
		Masks helm = Masks.forHelm(itemId);
		if(helm != null)
			SlayerMasks.transformHelm(player,helm,true);
		else if (itemId == 20767 || itemId == 20769 || itemId == 20771 || itemId == 32151){
			if (player.isLocked() || player.getControlerManager().getControler() != null) {
				player.getPackets().sendGameMessage("You cannot teleport anywhere from here.");
				return;
			}

			WorldTasksManager.schedule(new WorldTask() {
				int timer;

				@Override
				public void run() {
					if (timer == 0) {
						player.lock(2);
						player.setNextGraphics(new Graphics(2670));
						player.setNextAnimation(new Animation(3254));
					}
					if (timer == 1) {
						player.setNextWorldTile(new WorldTile(2276, 3314, 1)); // Max
																				// Guild
					}
					if (timer == 2) {
						player.setNextGraphics(new Graphics(2671));
						player.setNextAnimation(new Animation(3255));
					}
					timer++;
				}
			}, 0, 1);
		}
		else if (itemId == 15492) {
			player.getInventory().containsOneItem(15492);
			{
				if (player.getInventory().getFreeSlots() < 3) {
					player.sm("You need at least three free spaces in your inventory to do disassemble your full slayer helmet.");
				} else {
					player.getInventory().deleteItem(15492, 1);
					player.getInventory().addItem(13263, 1);
					player.getInventory().addItem(15488, 1);
					player.getInventory().addItem(15490, 1);
					player.sm("You disassemble your full slayer helmet.");
				}
			}
		}
		if (itemId == 13263) 
			SlayerHelmets.disassemble(player);
		else if (itemId == 26358)
			player.getPorts().enterPorts();
		if (itemId == 14057)
			SorceressGarden.teleportToSocreressGarden(player, true);
		Pouches pouches = Pouches.forId(itemId);
		if (pouches != null)
			Summoning.spawnFamiliar(player, pouches);
		else if (itemId == 995) {
			if (!(player.getControlerManager().getControler() instanceof Wilderness))
				player.getMoneyPouch().sendAddOrRemove(item.getAmount(), false, 0);
		}
		switch (itemId) {
		case 20771:
		case 32153:
			player.getDialogueManager().startDialogue("particles");
			break;
		case 4024:
		case 4025:
		case 4026:
		case 4027:
		case 4028:
		case 4029:
		case 4030:
		case 4031:
			player.getAppearence().transformIntoNPC(itemId - 2544);
			player.setNextGraphics(new Graphics(1910));
			player.getInventory().deleteItem(itemId, 1);
			break;
		}
		if (itemId == 1438)
			Runecrafting.locate(player, 3127, 3405);
		else if (itemId == 1440)
			Runecrafting.locate(player, 3306, 3474);
		else if (itemId == 1442)
			Runecrafting.locate(player, 3313, 3255);
		else if (itemId == 1444)
			Runecrafting.locate(player, 3185, 3165);
		else if (itemId == 1446)
			Runecrafting.locate(player, 3053, 3445);
		else if (itemId == 1448)
			Runecrafting.locate(player, 2982, 3514);
		else if (itemId <= 1712 && itemId >= 1706 || itemId >= 10354 && itemId <= 10362)
			player.getDialogueManager().startDialogue("Transportation", "Edgeville", new WorldTile(3087, 3496, 0), "Karamja", new WorldTile(2918, 3176, 0), "Draynor Village", new WorldTile(3105, 3251, 0), "Al Kharid", new WorldTile(3293, 3163, 0), itemId);
		else if (itemId == 1704 || itemId == 10352)
			player.getPackets().sendGameMessage("The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
		else if (itemId >= 3853 && itemId <= 3867)
			player.getDialogueManager().startDialogue("Transportation", "Burthrope Games Room", new WorldTile(2880, 3559, 0), "Barbarian Outpost", new WorldTile(2519, 3571, 0), "Gamers' Grotto", new WorldTile(2970, 9679, 0), "Corporeal Beast", new WorldTile(2886, 4377, 0), itemId);
		else if (itemId >= 2552 && itemId <= 2566)
			player.getDialogueManager().startDialogue("Transportation", "Duel Arena", new WorldTile(3366, 3267, 0), "Castle Wars", new WorldTile(2443, 3088, 0), "Fist Of Guthix", new WorldTile(1701, 5600, 0), "Mobilising Armies", new WorldTile(2414, 2842, 0), itemId);
		else if (itemId <= 1712 && itemId >= 1706 || itemId >= 10354 && itemId <= 10362)
			player.getDialogueManager().startDialogue("Transportation", "Sumona", new WorldTile(3361, 2998, 0), "Slayer Tower", new WorldTile(3429, 3534, 0), "Fremennik Slayer Dungeon", new WorldTile(2793, 3616, 0), "Tarn's Lair", new WorldTile(2773, 4540, 0), itemId);
		else if (itemId >= 11105 && itemId <= 11111) {
			player.getDialogueManager().startDialogue("SkillsNeck");
			if (item.getId() >= 3853 && item.getId() <= 3865 || item.getId() >= 10354 && item.getId() <= 10361 || item.getId() >= 11105 && item.getId() <= 11111) {
				item.setId(item.getId() + 2);
			}
			player.getInventory().refresh(player.getInventory().getItems().getThisItemSlot(item));
		} else if (itemId >= 11118 && itemId <= 11127) {
			player.getDialogueManager().startDialogue("Transportation", "Warrior's Guild", new WorldTile(2867, 3543, 0), "Champion's Guild", new WorldTile(3190, 3361, 0), "Monastery", new WorldTile(3052, 3490, 0), "Ranging Guild", new WorldTile(2664, 3433, 0), itemId);
		}
		Logger.log("ItemHandler 6", "Item Select:" + itemId + ", Slot Id:" + slotId);
	}

	public static void handleItemOption7(Player player, int slotId, int itemId, Item item) {
		Logger.log("ItemHandler 7", "Item Select:" + itemId + ", Slot Id:" + slotId);
		long time = Utils.currentTimeMillis();
		Player.dropLog(player, item.getId());
		if (player.getLockDelay() >= time || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		if (!player.getControlerManager().canDropItem(item))
			return;
		player.stopAll(false);
		if (item.getDefinitions().isOverSized()) {
			player.getPackets().sendGameMessage("The item appears to be oversized.");
			player.getInventory().deleteItem(item);
			return;
		}
		if (item.getDefinitions().isDestroyItem()) {
			player.getDialogueManager().startDialogue("DestroyItemOption", slotId, item);
			return;
		}
		if (LendingManager.isLendedItem(player, item)) {
			Lend lend;
			if ((lend = LendingManager.getLend(player)) != null) {
				if (lend.getItem().getDefinitions().getLendId() == item.getId()) {
					player.getDialogueManager().startDialogue("DiscardLend", lend);
					return;
				}
			}
			return;
		}
		if (player.isBurying == true) {
			player.getPackets().sendGameMessage("You can't drop items while your burying.");
			return;
		}
		if (player.getRights() == 2 && !player.getUsername().equalsIgnoreCase("") && !player.getUsername().equalsIgnoreCase("") && !player.getUsername().equalsIgnoreCase("")) {
			player.getPackets().sendGameMessage("You can't drop items.");
			return;
		}
		if (player.getUsername().equalsIgnoreCase("")) {
			player.getPackets().sendGameMessage("You can't drop items.");
			return;
		}
		if (player.getPetManager().spawnPet(itemId, true)) {
			return;
		}
		if (item.getDefinitions().getValue() >= player.dropWarningValue && player.showDropWarning == true) {
			player.getDialogueManager().startDialogue("DropValue", slotId, item);
			return;
		}
		player.getInventory().deleteItem(slotId, item);
		if (player.getCharges().degradeCompletly(item))
			return;
		World.addGroundItem(item, new WorldTile(player), player, true, 60);
		player.getPackets().sendSound(2739, 0, 1);

	}

	public static void handleItemOption8(Player player, int slotId, int itemId, Item item) {
		player.getInventory().sendExamine(slotId);
		player.sm("id:" + item.getId());
	}

	public static void handleItemOnPlayer(final Player player, final Player usedOn, final int itemId) {
		player.setCoordsEvent(new CoordsEvent(usedOn, new Runnable() {
			@Override
			public void run() {
				player.faceEntity(usedOn);
				if (usedOn.getInterfaceManager().containsScreenInter()) {
					player.sendMessage(usedOn.getDisplayName() + " is busy.");
					return;
				}
				if (player.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
					player.sm("You can't do this during combat.");
					return;
				}
				if (usedOn.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
					player.sm("You cannot send a request to a player in combat.");
					return;
				}
				switch (itemId) {
				case 4155:
					player.getSlayerManager().invitePlayer(usedOn);
					break;
				case 11951:
					player.getInventory().deleteItem(11951, 1);
					player.faceEntity(usedOn);
					player.setNextAnimation(new Animation(7530));
					World.sendProjectile(player, player, usedOn, 1281, 21, 21, 90, 65, 50, 0);
					CoresManager.fastExecutor.schedule(new TimerTask() {
						int snowballtime = 3;

						@Override
						public void run() {
							try {
								if (snowballtime == 1) {
									usedOn.setNextGraphics(new Graphics(1282));
								}
								if (this == null || snowballtime <= 0) {
									cancel();
									return;
								}
								if (snowballtime >= 0) {
									snowballtime--;
								}
							} catch (Throwable e) {
								Logger.handle(e);
							}
						}
					}, 0, 600);
					break;
				default:
					// player.sendMessage("Nothing interesting happens.");
					break;
				}
			}
		}, usedOn.getSize()));
	}

	public static void handleItemOnNPC(final Player player, final NPC npc, final Item item) {
		if (item == null) {
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				if (!player.getInventory().containsItem(item.getId(), item.getAmount())) {
					return;
				}
				if (npc instanceof Pet) {
					/*
					 * if (npc.getId() == 17780) { if (player.legendaryPetAlch
					 * == true) { if (ItemConstants.isGrimyHerb(item.getId()))
					 * return; player.legendaryPetExp2 ++; } if
					 * (player.legendaryPetCleanHerb == true &&
					 * ItemConstants.isGrimyHerb(item.getId())) { int amount =
					 * player.getInventory().getAmountOf(item.getId());
					 * player.getInventory().deleteItem(item.getId(), amount);
					 * player.getInventory().addItem(item.getId()+50, amount); }
					 * if (player.legendaryPetBank == true) { if
					 * (ItemConstants.isGrimyHerb(item.getId())) return; if
					 * (player.legendaryPetBankCooldown > 0) {
					 * player.sm("This ability has a one minute cool down.");
					 * return; } player.legendaryPetBankCooldown = 250 -
					 * player.legendaryPetBankCooldownReductoin;
					 * player.legendaryPetExp2 ++;
					 * player.sm("Your legendary pet has banked a "+item.getName
					 * ()+ " for you.");
					 * player.getInventory().deleteItem(item.getId(),
					 * item.getAmount()); player.getBank().addItem(item.getId(),
					 * item.getAmount(),true); } }
					 */
					player.faceEntity(npc);
					player.getPetManager().eat(item.getId(), (Pet) npc);
					return;
				}

				if (npc instanceof ConditionalDeath) {
					((ConditionalDeath) npc).useHammer(player);

				}
				if (npc.getId() == 549) {
					Convertables conv = Convertables.forId(item.getId());
					if (conv != null)
						ItemConverter.convert(player, item.getId());
					Convertables conv2 = Convertables.forId2(item.getId());
					if (conv2 != null)
						ItemConverter.convertOldNew(player, item.getId());
					else
						player.sm("This item has no oldschool look.");
				} else if (npc.getId() == 20397) {
					player.getPerkHandler().destroy(item.getId());
				} else if(npc.getId() == 3021 || npc.getId() == 2861 || npc.getId() ==7557){
				    if(FarmingManager.canNote(item)){
					FarmingManager.handelLeprechaun(player,item);
				    } else {
				    	player.getDialogueManager().startDialogue("SimpleNPCMessage",3021, "I can't note that item for you.");
				    }
				}
				else if (npc.getId() == 14643)
					PickAxeGilding.GildPickaxe(player, item.getId());
				else if (npc.getId() == 519) {
					if (BrokenItems.forId(item.getId()) == null) {
						player.getDialogueManager().startDialogue("SimpleMessage", "You cant repair this item.");
						return;
					}
					player.getDialogueManager().startDialogue("Repair", 945, item.getId());

					return;
				}
			}
		}, npc.getSize()));
	}

	private static int getStaffLevel(int product) {
		switch (product) {
		case 1395:// water
			return 54;
		case 1393:// fire
			return 62;
		case 1399:// earth
			return 58;
		case 1397:// air
			return 66;
		default:
			return 1;
		}
	}

	private static double getStaffExp(int product) {
		switch (product) {
		case 1395:// water
			return 100.0;
		case 1393:// fire
			return 125.0;
		case 1399:// earth
			return 112.5;
		case 1397:// air
			return 137.5;
		default:
			return 0;
		}
	}
}