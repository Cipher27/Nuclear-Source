package com.rs.net.decoders.handlers;

import java.util.HashMap;
import java.util.TimerTask;

import com.rs.Settings;
import com.rs.cache.loaders.ClientScriptMap;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.content.custom.playershop.CustomisedShop.MyShopItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.CastleWars;
import com.rs.game.minigames.Crucible;
import com.rs.game.minigames.PuroPuro;
import com.rs.game.minigames.pest.CommendationExchange;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.npc.others.GraveStone;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.EmotesManager;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.Types;
import com.rs.game.player.actions.FightPitsViewingOrb;
import com.rs.game.player.actions.Rest;
import com.rs.game.player.actions.crafting.Jewelry;
import com.rs.game.player.actions.smithing.Smithing.ForgingInterface;
import com.rs.game.player.actions.summoning.Summoning;
import com.rs.game.player.content.AdventurersLog;
import com.rs.game.player.content.BossTimerManager;
import com.rs.game.player.content.EnchantingBolts;
import com.rs.game.player.content.GnomeGlider;
import com.rs.game.player.content.GraveStoneSelection;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.ItemSets;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.Pots.Pot;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SandwichLady;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.content.SlayerShop;
import com.rs.game.player.content.SpiritTree;
import com.rs.game.player.content.TeleTabs;
import com.rs.game.player.content.TeleportSystem;
//import com.rs.game.player.content.achievements.AchievementsHandler.Types;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.custom.MasterCapesInterface;
import com.rs.game.player.content.custom.PetsInterface;
import com.rs.game.player.content.custom.TitleInterface;
import com.rs.game.player.content.dungeoneering.DungeonRewardShop;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.content.summoning.SummoningScroll;
import com.rs.game.player.controlers.SorceressGarden;
import com.rs.game.player.controlers.events.DeathEvent;
import com.rs.game.player.dialogues.Transportation;
import com.rs.game.server.fameHall.HallOfFame;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.utils.ItemBonuses;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public class ButtonHandler {

	public static void handleButtons(final Player player, InputStream stream, int packetId) {
		int interfaceHash = stream.readIntV2();
		int interfaceId = interfaceHash >> 16;
		if (Utils.getInterfaceDefinitionsSize() <= interfaceId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		if (!World.containsLobbyPlayer(player.getUsername())) {
			if (player.isDead() || !player.getInterfaceManager().containsInterface(interfaceId)) {
				return;
			}
		}
		final int componentId = interfaceHash - (interfaceId << 16);
		if (componentId != 65535 && Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		final int itemId = stream.readInt();
		final int slotId = stream.readUnsignedShortLE128();
		if (!player.getControlerManager().processButtonClick(interfaceId, componentId, slotId, packetId))
			return;
		if (!player.getControlerManager().processButtonClick(interfaceId, componentId, slotId, itemId, packetId))
			return;
		// Player Owned Shops
		if (interfaceId == 1266 && componentId == 0 && player.getInterfaceManager().containsInterface(1171)) {
			// if (player.getInterfaceManager().containsInterface(1171)) {
			Item item = player.getInventory().getItem(slotId);
			if (item == null) {
				System.out.println("[NOTICE] " + player.getDisplayName() + " may have just tried to dupe in POS. Item came up null.");
				player.sendMessage("Item is 'null' meaning you don't have an item there. Are you duping? :3");
				return;
			}
			player.getPlayerShop().addItem(item);
			player.getPlayerShop().sendOwnShop();
			return;
			// }
			// return;
		}

		// Player Owned Shops
		if (interfaceId == 1171) {
			if (componentId == 7) {
				if (player.getAttributes().get("editting_own_store") == Boolean.TRUE) {
					if (packetId == 14) {
						if (slotId > player.getPlayerShop().getShopItems().size())
							return;
						player.getPlayerShop().remove(null, player.getPlayerShop().getShopItems().get(slotId));
						player.getPlayerShop().sendOwnShop();
						return;
					} else if (packetId == 5) {
						if (slotId > player.getPlayerShop().getShopItems().size()) {
							return;
						}
						MyShopItem item = player.getPlayerShop().getShopItems().get(slotId);
						player.getPlayerShop().getValue(item);
						return;
					} else if (packetId == 55) {
						player.getAttributes().put("editing_item", Boolean.TRUE);
						player.getAttributes().put("editingSlot", slotId);
						player.getPackets().sendRunScript(108, new Object[] { "What would you like the price to be? Note: the prices is for <u>1</u> unit" });
						return;
					} else if (packetId == 67) {
						if (slotId > player.getPlayerShop().getShopItems().size())
							return;
						MyShopItem item = player.getPlayerShop().getShopItems().get(slotId);
						player.sendMessage("The original value of " + item.getItem().getName() + " is " + Utils.formatNumber(new Item(item.getItem().getId()).getDefinitions().getValue()) + "");
					}
					return;
				}
				if (player.getAttributes().get("viewing_player_shop") != null) {
					Player other = (Player) player.getAttributes().get("viewing_player_shop");
					if (other == null) {
						player.sendMessage("Invalid player. Has he gone offline?");
						return;
					}
					if (packetId == 14) {
						if (slotId > other.getPlayerShop().getShopItems().size()) {
							player.sendMessage("This item may have been removed already.");
							return;
						}
						MyShopItem item = other.getPlayerShop().getShopItems().get(slotId);
						if (item == null)
							return;
						other.getPlayerShop().sendExamine(player, item);
					} else if (packetId == 67) {
						if (slotId > other.getPlayerShop().getShopItems().size()) {
							player.sendMessage("This item may have been removed already.");
							return;
						}
						MyShopItem items = other.getPlayerShop().getShopItems().get(slotId);
						other.getPlayerShop().remove(player, items);
						other.getPlayerShop().open(player);
						return;
					} else if (packetId == 5) {
						if (slotId > other.getPlayerShop().getShopItems().size()) {
							player.sendMessage("This item may have been removed already.");
							return;
						}
						MyShopItem item = other.getPlayerShop().getShopItems().get(slotId);
						player.sendMessage("The original value of " + item.getItem().getName() + " is " + Utils.formatNumber(new Item(item.getItem().getId()).getDefinitions().getValue()) + "");
					}
				}
			} else if (componentId == 9) {
				if (player.getAttributes().get("editting_own_store") == Boolean.TRUE) {
					boolean isLocked = player.getPlayerShop().isLocked();
					player.getPlayerShop().setLock(!isLocked);
					player.getPlayerShop().sendOwnShop();
					isLocked = player.getPlayerShop().isLocked();
					player.sendMessage("Buying from your shop has been " + (isLocked == true ? "<col=FF0000>LOCKED</col>" : "<col=00DD00>UNLOCKED</col>") + "");
				}
			} else if (componentId == 8) {
				Player other = (Player) player.getAttributes().get("viewing_player_shop");
				if (other != null) {
					other.getPlayerShop().open(player);
				}
			}
			return;
		}
		if (interfaceId == 939) {
			if (componentId >= 59 && componentId <= 72) {
				int playerIndex = (componentId - 59) / 3;
				if ((componentId & 0x3) != 0)
					player.getDungManager().pressOption(playerIndex, packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET ? 0 : packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET ? 1 : 2);
				else
					player.getDungManager().pressOption(playerIndex, 3);
			} else if (componentId == 45)
				player.getDungManager().formParty();
			else if (componentId == 36 || componentId == 33)
				player.getDungManager().checkLeaveParty();
			else if (componentId == 43)
				player.getDungManager().invite();
			else if (componentId == 102)
				player.getDungManager().changeComplexity();
			else if (componentId == 108)
				player.getDungManager().changeFloor();
			else if (componentId == 87)
				player.getDungManager().openResetProgress();
			else if (componentId == 94)
				player.getDungManager().switchGuideMode();
			else if (componentId == 112)
				player.getInterfaceManager().closeDungPartyInterface();
		} else if (interfaceId == 949) {
			if (componentId == 65)
				player.getDungManager().acceptInvite();
			else if (componentId == 61 || componentId == 63)
				player.closeInterfaces();
		} else if (interfaceId == 938) {
			if (componentId >= 56 && componentId <= 81)
				player.getDungManager().selectComplexity((componentId - 56) / 5 + 1);
			else if (componentId == 39)
				player.getDungManager().confirmComplexity();
		} else if (interfaceId == 947) {
			if (componentId >= 48 && componentId <= 107)
				player.getDungManager().selectFloor((componentId - 48) + 1);
			else if (componentId == 766)
				player.getDungManager().confirmFloor();
		}
		else if (interfaceId == 940) {
			DungeonRewardShop.handleButtons(player, componentId, slotId, packetId);
			return;
		}
		else if (interfaceId == 297) {
			SandwichLady.getInstance().handleButtons(player, interfaceId, componentId);
		} else if (interfaceId == 21) {
			if (componentId == 21)
				player.closeInterfaces();
		} else if (interfaceId == 1011) {
			CommendationExchange.handleButtonOptions(player, componentId);
		}
		// puro puro interface
		else if (interfaceId == 540) {
			if (componentId == 69)
				PuroPuro.confirmPuroSelection(player);
			else if (componentId == 71)
				ShopsHandler.openShop(player, 32);
			else
				PuroPuro.handlePuroInterface(player, componentId);
			return;
		}
		/**
		 * Slave interface
		 **/
		else if (interfaceId == 3046) {
			player.getSlaveTripHandler().handleButtons(player, componentId);
		}
		/**
		 * Artisan workshop
		 **/
		else if (interfaceId == 1072) {
			player.getArtisanWorkshop().handelButtons(componentId);
		}
		/**
		 * item counting
		 */
		else if (interfaceId == 3030) {
			if (componentId == 29) {
				player.closeInterfaces();
			} else if (componentId == 32) {
				if (player.viewGlobalCount)
					player.getInterfaceManager().sendGlobalItemCounting4();
				else
					player.getInterfaceManager().sendPersonalItemCounting4();
			} else if (componentId == 31) {
				if (player.viewGlobalCount)
					player.getInterfaceManager().sendGlobalItemCounting2();
				else
					player.getInterfaceManager().sendPersonalItemCounting2();
			}
		}

		else if (interfaceId == 3031) {
			if (componentId == 29) {
				player.closeInterfaces();
			} else if (componentId == 32) {
				if (player.viewGlobalCount)
					player.getInterfaceManager().sendGlobalItemCounting();
				else
					player.getInterfaceManager().sendPersonalItemCounting1();
			} else if (componentId == 31) {
				if (player.viewGlobalCount)
					player.getInterfaceManager().sendGlobalItemCounting3();
				else
					player.getInterfaceManager().sendPersonalItemCounting3();
			}
		} else if (interfaceId == 3028) {
			if (componentId == 29) {
				player.closeInterfaces();
			} else if (componentId == 32) {
				if (player.viewGlobalCount)
					player.getInterfaceManager().sendGlobalItemCounting2();
				else
					player.getInterfaceManager().sendPersonalItemCounting2();
			} else if (componentId == 31) {
				if (player.viewGlobalCount)
					player.getInterfaceManager().sendGlobalItemCounting4();
				else
					player.getInterfaceManager().sendPersonalItemCounting4();
			}
		}

		else if (interfaceId == 3029) {
			if (componentId == 29) {
				player.closeInterfaces();
			} else if (componentId == 32) {
				if (player.viewGlobalCount)
					player.getInterfaceManager().sendGlobalItemCounting3();
				else
					player.getInterfaceManager().sendPersonalItemCounting3();
			} else if (componentId == 31) {
				if (player.viewGlobalCount)
					player.getInterfaceManager().sendGlobalItemCounting();
				else
					player.getInterfaceManager().sendPersonalItemCounting1();
			}
		} else if (interfaceId == 3024) {
			if (componentId == 1) {
				player.closeInterfaces();
			} else if (componentId == 2) {
				if(player.timerPage == 0){
				player.timerPage = 1;
				BossTimerManager.sendInterface(player, 1);
				} else if(player.timerPage == 1){
					player.timerPage = 2;
					BossTimerManager.sendInterface(player, 2);
				}
			} else if(componentId == 3){
				 if(player.timerPage == 0){
				BossTimerManager.sendInterface(player, 2);
				player.timerPage = 2;
				} else if(player.timerPage == 1){
					BossTimerManager.sendInterface(player, 0);
					player.timerPage = 0;
				} if(player.timerPage == 2){
					BossTimerManager.sendInterface(player, 1);
					player.timerPage = 1;
					}
			}
		}
		/**
		 * custom perk inter
		 */
		else if (interfaceId == 3048) {
			if (componentId == 4)
				player.getPerkHandler().sendCombatPerks();
			else if (componentId == 5)
				player.getPerkHandler().sendSkillingPerks();
			else if (componentId == 7)
				player.getPerkHandler().sendValueInterface();
			else
				player.getPerkHandler().handleButtons(componentId);

		}
		/**
		 * Recipes inter
		 **/
		else if (interfaceId == 3049) {

			player.getRecipeHandler().handleButtons(componentId);

		} else if (interfaceId == 3045) {
			if (componentId == 7)
				player.closeInterfaces();
			else if (componentId == 6)
				player.getPerkHandler().sendSkillingPerks();
			else if (componentId == 5)
				player.getPerkHandler().sendCombatPerks();
		}

		// recordinterface
		else if (interfaceId == 3009) {
			if (componentId == 23) {
				player.closeInterfaces();
			}
		} else if (interfaceId == 3026) {
			if (componentId == 7) {
				player.closeInterfaces();
			} else if (componentId == 1) {
				if (player.getPointsManager().getVoteTokens() >= 6) {
					player.getInventory().addItem(995, 10000000);
					player.getPointsManager().setVoteTokens(player.getPointsManager().getVoteTokens() - 5);
					player.sm("You claimed your 10 mil as a reward.");
				} else
					player.sm("You need 6 vote point to buy this.");
			} else if (componentId == 2) {
				if (player.getPointsManager().getVoteTokens() >= 6) {
					player.getInventory().addItem(18831, 50);
					player.getPointsManager().setVoteTokens(player.getPointsManager().getVoteTokens() - 5);
					player.sm("You claimed your 50 frost dragon bones.");
				} else
					player.sm("You need 6 vote point to buy this.");
			} else if (componentId == 3) {
				if (player.getPointsManager().getVoteTokens() >= 6) {
					player.getInventory().addItem(33723, 2);
					player.getPointsManager().setVoteTokens(player.getPointsManager().getVoteTokens() - 5);
					player.sm("You claimed your 2 exp lamps.");
				} else
					player.sm("You need 6 vote point to buy this.");
			} else if (componentId == 4) {
				if (player.getPointsManager().getVoteTokens() >= 6) {
					player.getInventory().addItem(565, 500);
					player.getInventory().addItem(560, 500);
					player.getPointsManager().setVoteTokens(player.getPointsManager().getVoteTokens() - 5);
					player.sm("You claimed your 500 blood and death.");
				} else
					player.sm("You need 6 vote point to buy this.");
			} else if (componentId == 5) {
				if (player.getPointsManager().getVoteTokens() >= 6) {
					player.getInventory().addItem(220, 20);
					player.getPointsManager().setVoteTokens(player.getPointsManager().getVoteTokens() - 5);
					player.sm("You claimed your 20 torstols.");
				} else
					player.sm("You need 6 vote point to buy this.");
			}
		} else if (interfaceId == 17) {
			if (componentId == 28)
				sendItemsKeptOnDeath(player, player.getVarsManager().getBitValue(9226) == 0);
		} else if (interfaceId == 3025) {
			if (player.hofPage == 1) {
				if (componentId == 2)
					HallOfFame.sendSkillingInterface(player);
				else if (componentId == 3) {
					HallOfFame.sendMiscInterface(player);
				}
			} else if (player.hofPage == 2) {
				if (componentId == 2)
					HallOfFame.sendMiscInterface(player);
				else if (componentId == 3) {
					HallOfFame.sendPvmInterface(player);
				}
			} else if (player.hofPage == 3) {
				if (componentId == 2)
					HallOfFame.sendPvmInterface(player);
				else if (componentId == 3) {
					HallOfFame.sendSkillingInterface(player);
				}
			}
			if (componentId == 1)
				player.closeInterfaces();
		}

		else if (interfaceId == 1072) {
			// ArtisanWorkshop.handleButtons(player, componentId);
		} else if (interfaceId == 3007) {
			MasterCapesInterface.handleButtons(player, componentId);
		}
		if (interfaceId == 3022) {
			if (componentId == 1) {
				player.getDialogueManager().startDialogue("teleportWarningD", new WorldTile(3219, 3677, 0), "Yes I want to teleport into the wildy for Blink.");
			} else if (componentId == 2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2909, 3731, 0));
				// player.getControlerManager().startControler("GodWars");
				player.closeInterfaces();
			} else if (componentId == 3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3067, 10254, 0));
				player.getPackets().sendGameMessage("Careful, make sure to have an anti-dragon shield, you're going to need it!");
				player.closeInterfaces();
			} else if (componentId == 4) {
				player.getControlerManager().startControler("QueenBlackDragonControler");
				player.closeInterfaces();
			} else if (componentId == 5) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966, 4383, 2));
				player.closeInterfaces();
			} else if (componentId == 6) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3466, 9497, 0));
				player.sendMessage("Welcome to the Kalphite Queen Lair.");
				player.closeInterfaces();
			} else if (componentId == 7) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1908, 4367, 0));
				player.sendMessage("Welcome to the Dagganoth Lair, click on the ladder enter the dks.");
				player.closeInterfaces();
			} else if (componentId == 8) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1618, 4578, 0));
				player.sendMessage("Welcome to the sea troll queen.");
				player.closeInterfaces();
			} else if (componentId == 9) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2808, 10002, 0));
				player.sendMessage("Welcome to the Fremmenik Slayer Dungeon.");
				player.closeInterfaces();
			} else if (componentId == 11) { // kuradels dungeon
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1736, 5313, 1));
				player.sendMessage("Welcome to Kuradel's Slayer Dungeon.");
				player.closeInterfaces();
			} else if (componentId == 12) { // tav dungeon
				player.sm("You teleport to the Taverley Dungeon!");
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2885, 9801, 0));
				player.closeInterfaces();
			} else if (componentId == 13) { // polypore
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4646, 5405, 0));
				player.sm("Welcome to the Polypore Dungeon.");
				player.closeInterfaces();
			} else if (componentId == 14) { // glacore
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4182, 5731, 0));
				player.sm("Welcome to the Glacors.");
				player.closeInterfaces();
			} else if (componentId == 15) { // jadinkio
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2947, 2954, 0));
				player.sm("Welcome to Jadinkos.");
				player.closeInterfaces();
			} else if (componentId == 16) { // skeletal w
				player.sm("Welcome to the skeletal wyverns dungeon!");
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3044, 9557, 0));
				player.closeInterfaces();
			} else if (componentId == 17) {
				player.sendMessage("Have fun training!");
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2328, 3796, 0));
				player.closeInterfaces();
			} else if (componentId == 18) {
				player.sendMessage("Have fun training!");
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2712, 3715, 0));
				player.closeInterfaces();
			} else if (componentId == 19) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3173, 2980, 0));
				player.closeInterfaces();
			} else if (componentId == 20) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1493, 4704, 0));
				player.sm("Good luck with your adventure on finding a dragon pickaxe!");
				player.closeInterfaces();
			} else if (componentId == 21) { // green drags
			player.getDialogueManager().startDialogue("teleportWarningD", new WorldTile(2987, 3615, 0), "Yes I want to teleport into the wilderness for Green Dragons.");
			} else if (componentId == 22) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1313, 4527, 0));
				player.closeInterfaces();
			} else if (componentId == 23) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2932, 9848, 0));
				player.closeInterfaces();
			} else if (componentId == 24) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1206, 6372, 0));
				player.closeInterfaces();
			} else if (componentId == 27) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3424, 3533, 0));
				player.getPackets().sendObjectAnimation(new WorldObject(82733, 20, 0, 3418, 3533, 0), new Animation(19709));
				player.closeInterfaces();
			} else if (componentId == 25) {
				player.getInterfaceManager().sendInterface(3044);
			} else if (componentId == 26) {
				player.closeInterfaces();
			}
		} else if (interfaceId == 3044) {
			if (componentId == 3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3809, 4727, 0));
				player.sendMessage("Welcome to Dark lord lair.");
				player.closeInterfaces();
			} else if (componentId == 1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2904, 5203, 0));
				player.sendMessage("Welcome to Nex, goodluck!.");
				player.closeInterfaces();
			} else if (componentId == 2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3243, 9355, 0));
				player.closeInterfaces();
			} else if (componentId == 4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1109, 588, 1));
				player.closeInterfaces();
			} else if (componentId == 5) {
				player.getDialogueManager().startDialogue("SimpleMessage", "This teleport has been disabled.");
				//Gedisabled omdat rise of the six toch niet werkt en wel nog teleporteerd -thomas
				//Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3541, 3311, 0));
				//player.sm("Comming soon.");
				//player.closeInterfaces();
			} else if (componentId == 6) {
				player.getDialogueManager().startDialogue("SimpleMessage", "This teleport has been disabled.");
				//Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3505, 6164, 0));
				//player.closeInterfaces();
			} else if (componentId == 9) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3206, 9379, 0));
				player.closeInterfaces();
			} else if (componentId == 27) { // Eddimu
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1365, 4637, 0));
				player.closeInterfaces();
			} else if (componentId == 11) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1826, 5985, 0));
				player.closeInterfaces();
			} else if (componentId == 12) { // done
				player.getDialogueManager().startDialogue("Stryke");
			} else if (componentId == 13) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4765, 6045, 1));
				player.sendMessage("Welcome.");
				player.closeInterfaces();
			} else if (componentId == 14) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4512, 6039, 0));
				player.sendMessage("Welcome.");
				player.closeInterfaces();
			} else if (componentId == 15) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2289,5972, 0));
				player.closeInterfaces();
			} else if (componentId == 11) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1826, 5985, 0));
				player.sendMessage("Welcome.");
				player.closeInterfaces();
			} else if (componentId == 16) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2709, 9564, 0));
				player.closeInterfaces();
			} else if (componentId == 22) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3654, 5116, 0));
				player.closeInterfaces();
			} else if (componentId == 21) {
				player.getDialogueManager().startDialogue("KalphiteTeleportD");
			} else if (componentId == 17) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2442, 10146, 0));
				player.closeInterfaces();
			} else if (componentId == 18) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2364, 5242, 0));
				player.closeInterfaces();
			} else if (componentId == 19) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2721, 9106, 0));
				player.closeInterfaces();
			} else if (componentId == 20) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4668, 5060, 0));
				player.closeInterfaces();
			} else if (componentId == 25) {
				player.getInterfaceManager().sendInterface(3022);
			} else if (componentId == 26) {
				player.closeInterfaces();
			}
		} else if (interfaceId == 3014) {
			if (componentId == 29) {
				player.closeInterfaces();
			}
		} else if (interfaceId == 1412) {
			TitleInterface.handleButtons(componentId, player);
		} else if (interfaceId == 3015) {
			if (componentId == 18) {
				if(player.maxCapeInterface == 0){
				if (player.isMaxed()) {
					if (player.getInventory().containsItem(995, 10000000)) {
						if (player.getInventory().getFreeSlots() >= 2) {
							player.getInventory().removeItemMoneyPouch(995, 10000000);
							player.getInventory().addItem(20767, 1);
							player.getInventory().addItem(20768, 1);
							player.sm("You bought a max cape.");
						} else
							player.sm("You need 2 free slots.");
					} else
						player.sm("You need 10 mil to claim this cape.");

				} else
					player.sm("You don't have the right requirements for this cape.");
				}
				else if(player.maxCapeInterface == 1){
					if(player.completedCompletionistCape()){
						
					}
				} else {
					if(player.isCompletedTrim()){
						
					} 
				}
			} else if (componentId == 17) {
				player.closeInterfaces();
			}
		} else if (interfaceId == 3005) {
			if (componentId == 8)
				player.closeInterfaces();
			else if (componentId == 5) {
				player.setHouseLook(3);
			} else if (componentId == 2) {
				player.setHouseLook(6);
			} else if (componentId == 3) {
				player.setHouseLook(4);
			} else if (componentId == 4) {
				player.setHouseLook(5);
			} else if (componentId == 1) {
				player.setHouseLook(1);
			} else if (componentId == 6) {
				ShopsHandler.openShop(player, 83);
			}

		}
		else if (interfaceId == 3013) {
			if (componentId == 1) {
				player.unlock();
				 player.closeInterfaces();  
				 player.getDialogueManager().startDialogue("HomeTour");
				 player.sm("Have fun in normal mode.");
			 }if (componentId == 2) {
				 player.unlock();
				 player.isSkiller = true;
				 player.closeInterfaces();  
				  player.getDialogueManager().startDialogue("HomeTour");
				 player.sm("Have fun skilling.");
			 }if (componentId == 3) {
				 player.unlock();
				 player.closeInterfaces();
				player.getDialogueManager().startDialogue("HomeTour");			  
				 player.IronMan = true;
				 player.sm("Have fun in iron man mode.");
			 }
			 if (componentId == 8) {
				 player.sm("You can't close this, pick a mode.");
			 }
			 }else if (interfaceId == 3038) {
			if (componentId == 8)
				player.closeInterfaces();
		}
		if (interfaceId == 3010) {
			if (componentId == 20) {
				player.closeInterfaces();
			} else if (componentId == 1) {
				player.getDialogueManager().startDialogue("FishingD");
			} else if (componentId == 3) {
				player.getDialogueManager().startDialogue("MiningD");
			} else if (componentId == 4) {
				player.getDialogueManager().startDialogue("SmithingD");
			} else if (componentId == 5) {
				player.getDialogueManager().startDialogue("HunterD");
			} else if (componentId == 6) {
				player.getDialogueManager().startDialogue("WoodcuttingD");
			} else if (componentId == 9 || componentId == 7 || componentId == 2) {
				player.sm("There is no teleport for this skill available,yet.");
			} else if (componentId == 8) {
				player.getDialogueManager().startDialogue("FarmingD");
			} else if (componentId == 10) {
				player.getDialogueManager().startDialogue("RunecraftingD");
			} else if (componentId == 11) {
				player.closeInterfaces();
				Magic.sendDaemonheimTeleport(player, 0, 0, new WorldTile(3449, 3698, 0));
			} else if (componentId == 12) {
				player.closeInterfaces();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2317, 3695, 0));
			} else if (componentId == 13) {
				player.getDialogueManager().startDialogue("CraftingD");
			} else if (componentId == 15) {
				player.getDialogueManager().startDialogue("AgilityD");
			} else if (componentId == 17) {
				player.getDialogueManager().startDialogue("ThievingD");
			} else if (componentId == 18 || componentId == 16) {
				player.getInterfaceManager().sendInterface(3022);
			} else if (componentId == 19) {
				player.closeInterfaces();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2329, 3684, 0));
			}
		} else if (interfaceId == 3033) {
			player.getPorts().handelButtons(componentId);
		} else if (interfaceId == 3043) {
			
			player.getAchievementManager().handelButtons(componentId);
		} else if (interfaceId == 3037) {
			player.getPorts().handelShipButton(componentId);
		}
		if (interfaceId == 3032) {
			//if(player.starter >= 5){
			// home teleport
			if (componentId == 4) {
				player.getDialogueManager().startDialogue("HomeTeleportD");
			}
			// prayer switch
			else if (componentId == 5) {
				player.getDialogueManager().startDialogue("ZarosAltar");
			}
			// Admin consolo
			
			  else if (componentId == 4) { if (player.getRights() == 2) {
			  player.getDialogueManager().startDialogue("AdminPanel"); } else {
			  player.sm("This is for admins only."); } }
			 
			// achievemnts
			else if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getAchievementManager().sendTestInterface(Types.EASY);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getAchievementManager().sendTestInterface(Types.EASY);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getAchievementManager().sendTestInterface(Types.MEDIUM);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getAchievementManager().sendTestInterface(Types.HARD);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					player.getAchievementManager().sendTestInterface(Types.ELITE);
			}
			// quests
			else if (componentId == 2) {
				//player.sm("Quest will come soon.");
				player.getDialogueManager().startDialogue("Quests");
			}
			// donator
			else if (componentId == 11) {
				if (player.isDonator()) {
					player.getDialogueManager().startDialogue("DonatorTab");
				} else {
					player.getDialogueManager().startDialogue("DonatorInfo");
				}
			}
			// mage book swap
			else if (componentId == 3) {
				player.getDialogueManager().startDialogue("SwapSpellBook");
			} else if (componentId == 7) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getDialogueManager().startDialogue("MemberShipD");
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (player.isAtHome())
						ShopsHandler.openShop(player, 152);
					else
						player.sm("You need to be at home to open this shop.");
				}

			}
			// bond
			else if (componentId == 8) {
				if (player.getRights() == 2) {
					player.getDialogueManager().startDialogue("AdminPanel");
				} else {
					player.sm("You need to be staff before you can do this.");
				}
			}
			// info
			else if (componentId == 6) {
				player.getDialogueManager().startDialogue("InfoPanel");
			} else if (componentId == 9) {
				player.getDialogueManager().startDialogue("TicketHelp");
			//}
			}
		}
		if (interfaceId == 936) {
			if (componentId == 137) {
				player.getInterfaceManager().sendPlayerExamineArmor(player);
			} else if (componentId == 146) {
				player.setExamined("");
				player.getInterfaceManager().sendTab(player.getInterfaceManager().resizableScreen ? 125 : 185, 187); 
				//player.getInterfaceManager().sendPlayerCustom();
			}
		} else if (interfaceId == 746) {
			if (componentId == 77)
				player.getInterfaceManager().sendPlayerCustom();
		} else if (interfaceId == 954) {
			if (componentId == 59) {
				player.getInterfaceManager().sendPlayerExamine(player);
			} else if (componentId == 6) {
				player.setExamined("");
				player.getInterfaceManager().sendTab(player.getInterfaceManager().resizableScreen ? 125 : 185, 187); 
			}
		}
		if (interfaceId == 72) {
			if (componentId == 68)
				player.getPackets().sendOpenURL("https://login.skype.com/account/signup-form");
			else if (componentId == 67)
				player.getPackets().sendOpenURL(Settings.RAGE_SKYPE);
			else if (componentId == 66)
				player.getPackets().sendOpenURL("skype:liam.jay67");
			else if (componentId == 65)
				player.getPackets().sendOpenURL("skype:dayinthenight");
			else if (componentId == 64)
				player.getPackets().sendOpenURL("skype:");
			else if (componentId == 73)
				player.getPackets().sendOpenURL("http://www.skype.com/en/download-skype/skype-for-computer/");
			else if (componentId == 72)
				player.getPackets().sendOpenURL("skype:");
			else if (componentId == 71)
				player.getPackets().sendOpenURL("skype:");
			else if (componentId == 70)
				player.getPackets().sendOpenURL("skype:");
			else if (componentId == 69)
				player.getPackets().sendOpenURL("skype:");
		}
		if ((interfaceId == 548 && componentId == 194) || (interfaceId == 746 && componentId == 204)) {
			player.getMoneyPouch().switchPouch();
		}
		if ((interfaceId == 746 && componentId == 207) || (interfaceId == 548 && componentId == 159)) {

			if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
				player.getMoneyPouch().switchPouch();
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				player.getMoneyPouch().withdrawPouch();
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				player.getPointsManager().sendInterface();
			// player.getMoneyPouch().examine();
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
				if (player.getInterfaceManager().containsScreenInter() || player.isLocked()) {
					player.getPackets().sendGameMessage("Please finish what you're doing before opening the price checker.");
					return;
				}
				player.stopAll();
				player.getDialogueManager().startDialogue("PriceChecker");
			}
		}
		if (interfaceId == 548 || interfaceId == 746) {
			if ((interfaceId == 548 && componentId == 148) || (interfaceId == 746 && componentId == 199)) {
				if (player.getInterfaceManager().containsScreenInter() || player.getInterfaceManager().containsInventoryInter()) {
					// TODO cant open sound
					player.getPackets().sendGameMessage("Please finish what you're doing before opening the world map.");
					return;
				}
				// world map open
				player.getPackets().sendWindowsPane(755, 0);
				int posHash = player.getX() << 14 | player.getY();
				player.getPackets().sendGlobalConfig(622, posHash); // map open
				// center
				// pos
				player.getPackets().sendGlobalConfig(674, posHash); // player
				// position
				// Quests
			} else if ((interfaceId == 548 && componentId == 17) || (interfaceId == 746 && componentId == 54)) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getSkills().switchXPDisplay();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getSkills().switchXPPopup();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getSkills().setupXPCounter();
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
				if (player.isInDung()) {
					player.getPackets().sendGameMessage("No.");
					return;
				}
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets().sendGameMessage("Please finish what you're doing before opening the price checker.");
					return;
				}
				player.stopAll();
				player.getDialogueManager().startDialogue("PriceChecker");
			}
		}

		// Dark Invasion
		else if (interfaceId == 267) {
			switch (componentId) {

			case 24:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.ATTACK, 5);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased attack xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 49:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.ATTACK, 25);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased attack xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 56:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.ATTACK, 50);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased attack xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 35:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.STRENGTH, 5);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased strength xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 50:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.STRENGTH, 25);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased strength xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 57:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.STRENGTH, 50);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased strength xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * DEFENCE XP
			 */
			case 36:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.DEFENCE, 5);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased defence xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 51:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.DEFENCE, 25);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased defence xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 58:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.DEFENCE, 50);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased defence xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * RANGE XP
			 */
			case 37:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.RANGE, 5);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased range xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 52:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.RANGE, 25);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased range xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 59:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.RANGE, 50);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased range xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * MAGIC XP
			 */
			case 38:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.MAGIC, 5);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased magic xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 53:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.MAGIC, 25);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased magic xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 60:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.MAGIC, 50);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased magic xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * HITPOINTS XP
			 */
			case 39:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.HITPOINTS, 5);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased hitpoints(hp) xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 54:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.HITPOINTS, 25);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased hitpoints(hp) xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 61:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.HITPOINTS, 50);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased hitpoints(hp) xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * PRAYER XP
			 */
			case 40:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.PRAYER, 5);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased prayer xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 55:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.PRAYER, 25);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased prayer xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 62:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.PRAYER, 50);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased prayer xp from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * HERBS
			 */
			case 45:
				if (player.getPlayerData().getInvasionPoints() >= 15) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 15);
					int[] herbNoted = { 995, 960 }; // CHANGE THIS LATER ON TODO
					int[] amount = { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
					int herbReward = herbNoted[Utils.random(herbNoted.length - 1)];
					int herbAmount = amount[Utils.random(amount.length - 1)];
					player.getBank().addItem(herbReward, herbAmount, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased some herbs from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * ORE
			 */
			case 46:
				if (player.getPlayerData().getInvasionPoints() >= 15) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 15);
					int[] oreNoted = { 995, 960 }; // CHANGE THIS LATER ON TODO
					int[] amounts = { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
					int oreReward = oreNoted[Utils.random(oreNoted.length - 1)];
					int oreAmount = amounts[Utils.random(amounts.length - 1)];
					player.getBank().addItem(oreReward, oreAmount, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased some ores from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * SEEDS
			 */
			case 48:
				if (player.getPlayerData().getInvasionPoints() >= 15) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 15);
					int[] seeds = { 995, 960 }; // CHANGE THIS LATER ON TODO
					int[] amountOfSeed = { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
					int seedsReward = seeds[Utils.random(seeds.length - 1)];
					int seedAmount = amountOfSeed[Utils.random(amountOfSeed.length - 1)];
					player.getBank().addItem(seedsReward, seedAmount, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased some seeds from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT MACE
			 */
			case 41:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(8841, 1, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased a Void Knight Mace from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT TOP
			 */
			case 42:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(8839, 1, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased a Void Knight Top from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT ROBE
			 */
			case 43:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(8840, 1, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased a Void Knight Robe from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT GLOVES
			 */
			case 44:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(8842, 1, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased a Void Knight Gloves from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID MAGE HELM
			 */
			case 67:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(11663, 1, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased a Void Mage Helm from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID RANGE HELM
			 */
			case 68:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(11664, 1, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased a Void Range Helm from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID MELEE HELM
			 */
			case 69:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(11665, 1, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased a Void Melee Helm from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT COMMENDATION
			 */
			case 70:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() - 5);
					player.getBank().addItem(19642, 1, true);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have purchased a Void Knight Commendation from the Invasion shop and now have " + player.getPlayerData().getInvasionPoints() + " points.");
				} else {
					player.getPackets().sendGameMessage("Sorry, you don't have enough points to purchase this.");
				}
				break;
			/* END OF SHOP LIST */
			}
		}
		if (interfaceId == 497) {
			if (componentId == 4)
				player.closeInterfaces();
		}

		if (interfaceId == 95) { // Sailing
			if (componentId == 23) {
				player.getPackets().sendGameMessage("You sail to the port of Tyras.");
				player.setNextWorldTile(new WorldTile(2268, 3244, 0));
			} else if (componentId == 33) {
				player.getPackets().sendGameMessage("You sail to the port of Ooglog.");
				player.setNextWorldTile(new WorldTile(2623, 2857, 0));
			} else if (componentId == 29) {
				player.getPackets().sendGameMessage("You sail to the port of Khazard.");
				player.setNextWorldTile(new WorldTile(2623, 2857, 0));
			} else if (componentId == 28) {
				player.getPackets().sendGameMessage("You sail to the port of Brimhaven.");
				player.setNextWorldTile(new WorldTile(2760, 3238, 0));
			} else if (componentId == 30) {
				player.getPackets().sendGameMessage("You sail to the port of Sarim.");
				player.setNextWorldTile(new WorldTile(3038, 3192, 0));
			} else if (componentId == 27) {
				player.getPackets().sendGameMessage("You sail to the port of Karamja.");
				player.setNextWorldTile(new WorldTile(2954, 3158, 0));
			} else if (componentId == 26) {
				player.getPackets().sendGameMessage("You sail to the port of the Shipyard.");
				player.setNextWorldTile(new WorldTile(3001, 3032, 0));
			} else if (componentId == 25) {
				player.getPackets().sendGameMessage("You sail to the port of Catherby.");
				player.setNextWorldTile(new WorldTile(2792, 3414, 0));
			} else if (componentId == 32) {
				if (player.DS < 7) {
					player.getPackets().sendGameMessage("You must have completed Dragon Slayer to sail to Crandor.");
				} else {
					player.getPackets().sendGameMessage("You sail to the port of Ooglog.");
					player.setNextWorldTile(new WorldTile(2623, 2857, 0));
				}
			} else if (componentId == 24) {
				player.getPackets().sendGameMessage("You sail to the port of Phasmatys.");
				player.setNextWorldTile(new WorldTile(3702, 3503, 0));
			} else if (componentId == 31) {
				player.getPackets().sendGameMessage("You sail to the port of Mos Le'Harmless.");
				player.setNextWorldTile(new WorldTile(3671, 2931, 0));
			}

		}
		else if(interfaceId == 3056){
			TeleportSystem.handleButtons(componentId,player);
		}
		if (interfaceId == 813) {
			if (player.inter813Stage == 1) {//Training Teleports
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 55) {//General Page 1
					player.inter813Stage = 2;
					player.getInterfaceManager().sendGeneralTeleports();
				} else if (componentId == 56) {//Dungeon Teles
					player.inter813Stage = 4;
					player.getInterfaceManager().sendDungeonTeleports();
				} else if (componentId == 60) {//Slayer Teles
					player.inter813Stage = 5;
					player.getInterfaceManager().sendSlayernTeleports();
				} else if (componentId == 57) {//Wilderness Teles
					player.inter813Stage = 6;
					player.getInterfaceManager().sendWildernessTeleports();
				}
			} else if (player.inter813Stage == 2) {//General Page 1
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 52) {//Option 1
					//godwars
				} else if (componentId == 53) {//Option 2
//sunfreet
				} else if (componentId == 54) {//Option 3
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1368, 6623, 0));
					player.sendMessage("Welcome to Blink.");
				} else if (componentId == 55) {//yk
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2521, 5232, 0));
					player.sendMessage("Welcome to Yk'Lagor the Thunderous.");
				} else if (componentId == 56) {//Option 5
					//leeuni
				} else if (componentId == 60) {//darklord
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3809, 4727, 0));
					player.sendMessage("Welcome to the Darklord.");
				} else if (componentId == 57) {//qbd
					if (player.getSkills().getLevelForXp(Skills.SUMMONING) < 60) {
						player.getPackets().sendGameMessage("You need a summoning level of 60 to go to this monster.");
						player.getControlerManager().removeControlerWithoutCheck();
					} else {
					player.lock();
					player.getControlerManager().startControler("QueenBlackDragonControler");
					}
				} else if (componentId == 58) {//kbd
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3067, 10255, 0));
					player.sendMessage("Welcome to the King Black Dragon.");
					
				} else if (componentId == 59) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 61) {//General Page 2
					player.inter813Stage = 3;
					player.getInterfaceManager().sendGeneralTeleports2();
				}
			} else if (player.inter813Stage == 3) {//General Page 2
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 52) {//Option 1
					
				} else if (componentId == 53) {//Option 2
					
				} else if (componentId == 54) {//Option 3
					
				} else if (componentId == 55) {//Option 4
					
				} else if (componentId == 56) {//Option 5
					
				} else if (componentId == 60) {//Option 6
					
				} else if (componentId == 57) {//Option 7
					
				} else if (componentId == 58) {//Option 8
					
				} else if (componentId == 59) {//Option 9
					
				} else if (componentId == 61) {//General Page 1
					player.inter813Stage = 2;
					player.getInterfaceManager().sendGeneralTeleports();
				}
			} else if (player.inter813Stage == 4) {//Dungeon Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 52) {//Option 1
					
				} else if (componentId == 53) {//Option 2
					
				} else if (componentId == 54) {//Option 3
					
				} else if (componentId == 55) {//Option 4
					
				} else if (componentId == 56) {//Option 5
					
				} else if (componentId == 60) {//Option 6
					
				} else if (componentId == 57) {//Option 7
					
				} else if (componentId == 58) {//Option 8
					
				} else if (componentId == 59) {//Option 9
					
				} else if (componentId == 61) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				}
			} else if (player.inter813Stage == 5) {//Slayer Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 52) {//Option 1
					
				} else if (componentId == 53) {//Option 2
					
				} else if (componentId == 54) {//Option 3
					
				} else if (componentId == 55) {//Option 4
					
				} else if (componentId == 56) {//Option 5
					
				} else if (componentId == 60) {//Option 6
					
				} else if (componentId == 57) {//Option 7
					
				} else if (componentId == 58) {//Option 8
					
				} else if (componentId == 59) {//Option 9
					
				} else if (componentId == 61) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				}
			} else if (player.inter813Stage == 6) {//Wilderness Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 52) {//Option 1
					Magic.sendCustomTeleportSpell(player, 0, 0, new WorldTile(2394, 3856, 0));
				} else if (componentId == 53) {//Option 2
					
				} else if (componentId == 54) {//Option 3
					
				} else if (componentId == 55) {//Option 4
					
				} else if (componentId == 56) {//Option 5
					
				} else if (componentId == 60) {//Option 6
					
				} else if (componentId == 57) {//Option 7
					
				} else if (componentId == 58) {//Option 8
					
				} else if (componentId == 59) {//Option 9
					
				} else if (componentId == 61) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				}
			} else if (player.inter813Stage == 7) {//Boss Teleports
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 45) {//High Boss Teles
					player.inter813Stage = 8;
					player.getInterfaceManager().sendHighBossTeleports();
				} else if (componentId == 46) {//Med Boss Teles
					player.inter813Stage = 9;
					player.getInterfaceManager().sendMedBossTeleports();
				} else if (componentId == 47) {//Low Boss Teles
					player.inter813Stage = 10;
					player.getInterfaceManager().sendLowBossTeleports();
				} else if (componentId == 48) {//Team Boss Teles
					player.inter813Stage = 11;
					player.getInterfaceManager().sendTeamBossTeleports();
				}
			} else if (componentId == 8) {//High Boss Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 43) {//Option 1
					
				} else if (componentId == 44) {//Option 2
					
				} else if (componentId == 45) {//Option 3
					
				} else if (componentId == 46) {//Option 4
					
				} else if (componentId == 47) {//Option 5
					
				} else if (componentId == 48) {//Option 6
					
				} else if (componentId == 49) {//Option 7
					
				} else if (componentId == 50) {//Option 8
					
				} else if (componentId == 51) {//Option 9
					
				}
			} else if (player.inter813Stage == 9) {//Med Boss Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 43) {//Option 1
					
				} else if (componentId == 44) {//Option 2
					
				} else if (componentId == 45) {//Option 3
					
				} else if (componentId == 46) {//Option 4
					
				} else if (componentId == 47) {//Option 5
					
				} else if (componentId == 48) {//Option 6
					
				} else if (componentId == 49) {//Option 7
					
				} else if (componentId == 50) {//Option 8
					
				} else if (componentId == 51) {//Option 9
					
				}
			} else if (player.inter813Stage == 10) {//Low Boss Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 43) {//Option 1
					
				} else if (componentId == 44) {//Option 2
					
				} else if (componentId == 45) {//Option 3
					
				} else if (componentId == 46) {//Option 4
					
				} else if (componentId == 47) {//Option 5
					
				} else if (componentId == 48) {//Option 6
					
				} else if (componentId == 49) {//Option 7
					
				} else if (componentId == 50) {//Option 8
					
				} else if (componentId == 51) {//Option 9
					
				}
			} else if (player.inter813Stage == 11) {//Team Boss Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 43) {//Option 1
					
				} else if (componentId == 44) {//Option 2
					
				} else if (componentId == 45) {//Option 3
					
				} else if (componentId == 46) {//Option 4
					
				} else if (componentId == 47) {//Option 5
					
				} else if (componentId == 48) {//Option 6
					
				} else if (componentId == 49) {//Option 7
					
				} else if (componentId == 50) {//Option 8
					
				} else if (componentId == 51) {//Option 9
					
				}
			} else if (player.inter813Stage == 12) {//Skilling Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 37) {//Runecrafting Teles
					player.inter813Stage = 13;
					player.getInterfaceManager().sendRunecraftingTeleports();
				} else if (componentId == 38) {//Agility Teles
					player.inter813Stage = 14;
					player.getInterfaceManager().sendAgilityTeleports();
				} else if (componentId == 39) {//Option 3
					
				} else if (componentId == 40) {//Option 4
					
				} else if (componentId == 41) {//Option 5
					
				} else if (componentId == 42) {//Option 6
					
				}
			} else if (player.inter813Stage == 13) {//Runecrafting Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 37) {//Option 1
					
				} else if (componentId == 38) {//Option 2
					
				} else if (componentId == 39) {//Option 3
					
				} else if (componentId == 40) {//Option 4
					
				} else if (componentId == 41) {//Option 5
					
				} else if (componentId == 42) {//Option 6
					
				}
			} else if (player.inter813Stage == 14) {//Agility Teles
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 37) {//Option 1
					
				} else if (componentId == 38) {//Option 2
					
				} else if (componentId == 39) {//Option 3
					
				} else if (componentId == 40) {//Option 4
					
				} else if (componentId == 41) {//Option 5
					
				} else if (componentId == 42) {//Option 6
					
				}
			} else if (player.inter813Stage == 15) {//Minigame Page 1
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 64) {//Option 1
					
				} else if (componentId == 65) {//Option 2
					
				} else if (componentId == 66) {//Option 3
					
				} else if (componentId == 67) {//Option 4
					
				} else if (componentId == 68) {//Option 5
					
				} else if (componentId == 69) {//Option 6
					
				} else if (componentId == 70) {//Option 7
					
				} else if (componentId == 71) {//Minigame Page 2
					player.inter813Stage = 16;
					player.getInterfaceManager().sendMinigameTeleports2();
				}
			} else if (player.inter813Stage == 16) {//Minighame Page 2
				if (componentId == 29) {//Training Teleports
					player.inter813Stage = 1;
					player.getInterfaceManager().sendTrainingTeleports();
				} else if (componentId == 30) {//Boss Teleports
					player.inter813Stage = 7;
					player.getInterfaceManager().sendBossingTeleports();
				} else if (componentId == 31) {//Skilling Teles
					player.inter813Stage = 12;
					player.getInterfaceManager().sendSkillingTeleports();
				} else if (componentId == 32) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				} else if (componentId == 64) {//Option 1
					
				} else if (componentId == 65) {//Option 2
					
				} else if (componentId == 66) {//Option 3
					
				} else if (componentId == 67) {//Option 4
					
				} else if (componentId == 68) {//Option 5
					
				} else if (componentId == 69) {//Option 6
					
				} else if (componentId == 70) {//Option 7
					
				} else if (componentId == 71) {//Minigame Page 1
					player.inter813Stage = 15;
					player.getInterfaceManager().sendMinigameTeleports();
				}
			}
		}

		if (interfaceId == 506) {
			if (componentId == 2) { // Player Rankings
				player.getInterfaceManager().sendInterface(275);
				player.getPackets().sendIComponentText(275, 1, "Player Rankings");
				player.getPackets().sendIComponentText(275, 10, "<img=1><col=FF0000><shad=000000>Main Developer: Astro");
				player.getPackets().sendIComponentText(275, 11, "<img=1><col=FF0000><shad=000000>Co Owners: Time");
				// player.getPackets().sendIComponentText(275, 12,
				// "<img=1><col=0000ff><shad=00ffff>Co Developers: Nick");
				player.getPackets().sendIComponentText(275, 13, "");
				player.getPackets().sendIComponentText(275, 14, "<img=1><col=FFFF00><shad=000000>Administrators: ");
				player.getPackets().sendIComponentText(275, 15, "<img=0><col=FFFFFF><shad=000000>Moderators: , ");
				player.getPackets().sendIComponentText(275, 16, "<img=14><col=3299CC>Player Support: ");
				player.getPackets().sendIComponentText(275, 17, "");
				player.getPackets().sendIComponentText(275, 18, "");
				player.getPackets().sendIComponentText(275, 19, "<img=5><col=FF0000><shad=000000>Staff Applications Are Current Open On Forums!<img=5>");
				player.getPackets().sendIComponentText(275, 20, "");
				player.getPackets().sendIComponentText(275, 21, "");
			}
			if (componentId == 4) { // Appearance/Settings
				player.getDialogueManager().startDialogue("PickOne");
			}
			if (componentId == 6) { // Teleports
				player.inter813Stage = 1;
				player.getInterfaceManager().sendTrainingTeleports();
			}
			if (componentId == 8) { // Donator Information
				player.getPackets().sendOpenURL("http://hellion718.com");
				player.getInterfaceManager().sendInterface(275);
				player.getPackets().sendIComponentText(275, 1, "<img=8><col=00FF00><shad=000000>Donator Information<img=8>");
				player.getPackets().sendIComponentText(275, 2, "");
				player.getPackets().sendIComponentText(275, 10, "");
				player.getPackets().sendIComponentText(275, 11, "<img=13><col=0000FF>Prices for ranks/spins/points below!<img=13>");
				player.getPackets().sendIComponentText(275, 12, "<img=11><col=FF00>Regular Donator Features<img=11>");
				player.getPackets().sendIComponentText(275, 13, "Access to the extremely awesome Donator Zone.");
				player.getPackets().sendIComponentText(275, 14, "Ability to set a custom title. ::title 1-60");
				player.getPackets().sendIComponentText(275, 15, "Ability to change the color of your skin. ::blueskin ::greenskin");
				player.getPackets().sendIComponentText(275, 16, "Ability to bank anywhere EXCEPT in minigames. ::bank");
				player.getPackets().sendIComponentText(275, 17, "Increased EXP Rates from 1 to 1.2.");
				player.getPackets().sendIComponentText(275, 18, "An epic rank and yell title, and a red donator icon.");
				player.getPackets().sendIComponentText(275, 19, "Access to beta features before they are released!");
				player.getPackets().sendIComponentText(275, 20, "Be able to Summon Pak-Yak and Bunyip.");
				player.getPackets().sendIComponentText(275, 21, "Access to a custom minigame Refuge of Fear.");
				player.getPackets().sendIComponentText(275, 22, "Increased Loyalty Points per 30 minutes of play.");
				player.getPackets().sendIComponentText(275, 23, "Access to about 8+ extra shops.");
				player.getPackets().sendIComponentText(275, 24, "Ability to yell. ::yell");

				player.getPackets().sendIComponentText(275, 25, "<img=8><col=006600><shad=000000>Extreme Donator Features<img=8>");
				player.getPackets().sendIComponentText(275, 26, "Get all the features of a Regular Donator.");
				player.getPackets().sendIComponentText(275, 27, "Increased EXP Rates from 1 to 1.4");
				player.getPackets().sendIComponentText(275, 28, "Ability to use 5 new pets.");
				player.getPackets().sendIComponentText(275, 29, "Ability to use wing auras and special tier auras.");
				player.getPackets().sendIComponentText(275, 30, "Slightly increased drop rates.");
				player.getPackets().sendIComponentText(275, 31, "A Green donator symbol.");

				player.getPackets().sendIComponentText(275, 32, "<img=12><col=0000FF><shad=000000>Legendary Donator Features<img=12>");
				player.getPackets().sendIComponentText(275, 33, "Get all the features of a Regular Donator and Extreme.");
				player.getPackets().sendIComponentText(275, 34, "Ultimate priority over other players.");
				player.getPackets().sendIComponentText(275, 35, "Better chances at the Squeal of Fortune");
				player.getPackets().sendIComponentText(275, 36, "To be able to have the ultimate pet, TzRek Jad");
				player.getPackets().sendIComponentText(275, 37, "Increased EXP Rates from 1 to 1.75");
				player.getPackets().sendIComponentText(275, 38, "Mithril Seeds, to play Hot/Cold");
				player.getPackets().sendIComponentText(275, 39, "Drop rates increase giving you a better chance for rare items!");
				player.getPackets().sendIComponentText(275, 40, "::Setdisplay/::RemoveDisplay - Changes your in-game name");
				player.getPackets().sendIComponentText(275, 41, "A Blue donator symbol.");
				player.getPackets().sendIComponentText(275, 42, "Ability to request custom titles.");

				player.getPackets().sendIComponentText(275, 43, "<img=13><col=ffa34c><shad=000000>Supreme Donator Features<img=13>");
				player.getPackets().sendIComponentText(275, 44, "Get all the features of a Regular, Extreme, and Legendary Donator.");
				player.getPackets().sendIComponentText(275, 45, "Increased EXP Rates from 1 to 2.");
				player.getPackets().sendIComponentText(275, 46, "Access to the Supreme Donator Shop");
				player.getPackets().sendIComponentText(275, 47, "Ability to purchase and use the Golden and Royal Cannons");
				player.getPackets().sendIComponentText(275, 48, "The good feeling of helping us pay for our server costs ($50+ per month)");
				player.getPackets().sendIComponentText(275, 49, "A bonus of 500 Donator Tokens");
				player.getPackets().sendIComponentText(275, 50, "Access to an exclusive training area");
				player.getPackets().sendIComponentText(275, 51, "Ability to have a custom yell tag coded for you");
				player.getPackets().sendIComponentText(275, 52, "A Gold donator symbol.");
				player.getPackets().sendIComponentText(275, 53, "Suggest more benefits for this rank to Nosz!");

				player.getPackets().sendIComponentText(275, 54, "<img=16><col=6C21ED><shad=000000>Divine Donator Features<img=14>");
				player.getPackets().sendIComponentText(275, 55, "Get all the features of a Regular, Extreme, Legendary, and Supreme Donator.");
				player.getPackets().sendIComponentText(275, 56, "Increased EXP Rates from 2 to 2.5.");
				player.getPackets().sendIComponentText(275, 57, "Access to the Divine Donator Shop");
				player.getPackets().sendIComponentText(275, 58, "Unlimited supply of Supreme Corruption for wings");
				player.getPackets().sendIComponentText(275, 59, "Praise from all the players, you will stand out");
				player.getPackets().sendIComponentText(275, 60, "A bonus of 750 Donator Tokens");
				player.getPackets().sendIComponentText(275, 61, "A custom pet, coded just for you (or for others if you like)");
				player.getPackets().sendIComponentText(275, 62, "Ability to have a custom yell tag/title coded for you");
				player.getPackets().sendIComponentText(275, 63, "A Purple donator symbol.");
				player.getPackets().sendIComponentText(275, 64, "Suggest more benefits for this rank to Nosz!");

				player.getPackets().sendIComponentText(275, 65, "<img=15><col=ffffff><shad=000000>Angelic Donator Features<img=14>");
				player.getPackets().sendIComponentText(275, 66, "Get all the features of a Regular, Extreme, Legendary, Divine, and Supreme Donator.");
				player.getPackets().sendIComponentText(275, 67, "Increased EXP Rates from 2.5 to 3.");
				player.getPackets().sendIComponentText(275, 68, "Access to the Angelic Donator Shop");
				player.getPackets().sendIComponentText(275, 69, "Unlimited supply of Supreme Salvation for wings");
				player.getPackets().sendIComponentText(275, 70, "Praise from all the players, you will stand out");
				player.getPackets().sendIComponentText(275, 71, "A bonus of 1000 Donator Tokens");
				player.getPackets().sendIComponentText(275, 72, "");
				player.getPackets().sendIComponentText(275, 73, "Access to new items such as Godcrusher and Tokhaar Warlord");
				player.getPackets().sendIComponentText(275, 74, "A White donator symbol.");
				player.getPackets().sendIComponentText(275, 75, "Suggest more benefits for this rank to Nosz!");

				player.getPackets().sendIComponentText(275, 76, "<img=11><col=0000FF><shad=000000>Donation Options<img=11>");
				player.getPackets().sendIComponentText(275, 77, "<col=FF0000>Regular Donator<col=000000>-<col=FFA500>10$/20m EoC/5m 07");
				player.getPackets().sendIComponentText(275, 78, "<col=006600>Extreme Donator<col=000000>-<col=FFA500>15$/30m EoC/7m 07");
				player.getPackets().sendIComponentText(275, 79, "<col=0000FF>Legendary Donator<col=000000>-<col=FFA500>25$/50m EoC/10m 07");
				player.getPackets().sendIComponentText(275, 80, "<col=ffa34c>Supreme Donator<col=000000>-<col=FFA500>50$/100m EoC/20m 07");
				player.getPackets().sendIComponentText(275, 81, "<col=6C21ED>Divine Donator<col=000000>-<col=FFA500>100$/200m EoC/40m 07");
				player.getPackets().sendIComponentText(275, 82, "<col=ffffff>Angelic Donator<col=000000>-<col=FFA500>200$/400m EoC/80m 07");

				player.getPackets().sendIComponentText(275, 83, "<img=8><col=0000FF><shad=000000>Spin Purchases<img=8>");
				player.getPackets().sendIComponentText(275, 84, "Spins are priced at 0.25$/1m EoC/125k 07");
				player.getPackets().sendIComponentText(275, 85, "For every 10 spins you buy, you get 5 free!");
				player.getPackets().sendIComponentText(275, 86, "<img=12><col=0000FF><shad=000000>Donator Tokens<img=12>");
				player.getPackets().sendIComponentText(275, 87, "You recieve 100 donator tokens per 1$/4m EoC/500k 07 you donate");
			}
			if (componentId == 10) { // Vote
				player.getPackets().sendGameMessage("Once your votes go through you will recieve your reward!");
				player.getPackets().sendOpenURL("http://www.exylum.org/vote/");
			}
			if (componentId == 14) { // Highscores
				player.getPackets().sendOpenURL("Http://Hellion718.com");
			}
			if (componentId == 12) { // Quests
				player.getDialogueManager().startDialogue("ZariaQuestsD");
			}
		}

		
		/*
		 * if (interfaceId == 1308) if (componentId == 65) if
		 * (player.getSlayerPoints() >= 400) {
		 * player.getSkills().addXp(Skills.SLAYER, 10);
		 * player.setSlayerPoints(player.getSlayerPoints() - 400); } if
		 * (componentId == 192) if (player.getSlayerPoints() >= 75) {
		 * player.getInventory().addItem(13281, 1);
		 * player.setSlayerPoints(player.getSlayerPoints() - 75); } if
		 * (componentId == 197) if (player.getSlayerPoints() >= 35) {
		 * player.getInventory().addItem(560, 250);
		 * player.getInventory().addItem(556, 750);
		 * player.setSlayerPoints(player.getSlayerPoints() - 35); } if
		 * (componentId == 205) if (player.getSlayerPoints() >= 35) {
		 * player.getInventory().addItem(13280, 250);
		 * player.setSlayerPoints(player.getSlayerPoints() - 35); } if
		 * (componentId == 213) if (player.getSlayerPoints() >= 35) {
		 * player.getInventory().addItem(4160, 250);
		 * player.setSlayerPoints(player.getSlayerPoints() - 35); }
		 */

		if (interfaceId == 1253 || interfaceId == 1252 || interfaceId == 1139) {
			player.getSquealOfFortune().processClick(packetId, interfaceId, componentId, slotId, itemId);
		}
		if (interfaceId == 548 && componentId == 68) {
			player.getPackets().sendIComponentText(1139, 10, " " + player.getSpins() + " ");
		}
		if (interfaceId == 1264) {
			if (componentId == 0) {
				player.closeInterfaces();
				player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, 0);
			}

		
		} else if (interfaceId == 182) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6 || componentId == 13)
				if (!player.hasFinished())
					player.logout(componentId == 6);
			// Hiscores.saveHighScore(player);
		} else if (interfaceId == 1310) {
			if (componentId == 0) {
				player.getSlayerManager().createSocialGroup(true);
				player.setCloseInterfacesEvent(null);
			}
			player.closeInterfaces();
		} else if (interfaceId == 1309) {
			if (componentId == 20)
				player.getPackets().sendGameMessage("Use your enchanted stone ring onto the player that you would like to invite.", true);
			else if (componentId == 22) {
				Player p2 = player.getSlayerManager().getSocialPlayer();
				if (p2 == null)
					player.getPackets().sendGameMessage("You have no slayer group, invite a player to start one.");
				else
					player.getPackets().sendGameMessage("Your current slayer group consists of you and " + p2.getDisplayName() + ".");
			} else if (componentId == 24)
				player.getSlayerManager().resetSocialGroup(true);
			player.closeInterfaces();
		} else if (interfaceId == 53) {
			if (componentId == 47) {
				player.setNextWorldTile(new WorldTile(3232, 3252, 0));
				player.getPackets().sendGameMessage("You travel using your canoe.");
				player.closeInterfaces();
			} else if (componentId == 48) {
				player.setNextWorldTile(new WorldTile(3202, 3343, 0));
				player.getPackets().sendGameMessage("You travel using your canoe.");
				player.closeInterfaces();
			} else if (componentId == 3) {
				if (player.usingLog) {
					player.getPackets().sendGameMessage("You must be using atleast a dugout canoe.");
				} else {
					player.setNextWorldTile(new WorldTile(3112, 3411, 0));
					player.getPackets().sendGameMessage("You travel using your canoe.");
					player.closeInterfaces();
				}
			} else if (componentId == 6) {
				if (player.usingLog || player.usingDugout) {
					player.getPackets().sendGameMessage("You must be using atleast a stable dugout canoe.");
				} else {
					player.setNextWorldTile(new WorldTile(3132, 3510, 0));
					player.getPackets().sendGameMessage("You travel using your canoe.");
					player.closeInterfaces();
				}
			} else if (componentId == 49) {
				if (!player.usingWaka) {
					player.getPackets().sendGameMessage("You must be using atleast a waka canoe.");
				} else {
					player.setNextWorldTile(new WorldTile(3145, 3791, 0));
					player.getPackets().sendGameMessage("You travel using your canoe.");
					player.closeInterfaces();
				}
			}
		} else if (interfaceId == 403) {
			if (componentId == 12)
				if (!player.getInventory().containsItem(1511, 1)) {
				} else if (player.getInventory().getCoinsAmount() > 100) {
				} else {
					player.getInventory().deleteItem(1511, 1);
					player.getInventory().removeItemMoneyPouch(new Item(995, 100));
					player.getInventory().addItem(960, 1);
					player.getSkills().addXp(Skills.CONSTRUCTION, 1200);
				}
			if (componentId == 13)
				if (!player.getInventory().containsItem(1521, 1)) {
				} else if (player.getInventory().getCoinsAmount() > 250) {
				} else {
					player.getInventory().deleteItem(1521, 1);
					player.getInventory().removeItemMoneyPouch(new Item(995, 250));
					player.getInventory().addItem(8778, 1);
					player.getSkills().addXp(Skills.CONSTRUCTION, 2400);
				}
			if (componentId == 14)
				if (!player.getInventory().containsItem(6333, 1)) {
				} else if (player.getInventory().getCoinsAmount() > 500) {
				} else {
					player.getInventory().deleteItem(6333, 1);
					player.getInventory().removeItemMoneyPouch(new Item(995, 500));
					player.getInventory().addItem(8780, 1);
					player.getSkills().addXp(Skills.CONSTRUCTION, 3600);
				}
			if (componentId == 15)
				if (!player.getInventory().containsItem(6332, 1)) {
				} else if (player.getInventory().getCoinsAmount() > 1500) {
				} else {
					player.getInventory().deleteItem(6332, 1);
					player.getInventory().removeItemMoneyPouch(new Item(995, 1500));
					player.getInventory().addItem(8782, 1);
					player.getSkills().addXp(Skills.CONSTRUCTION, 4800);
				}
		} else if (interfaceId == 880) {
			if (componentId >= 7 && componentId <= 19)
				Familiar.setLeftclickOption(player, (componentId - 7) / 2);
			else if (componentId == 21)
				Familiar.confirmLeftOption(player);
			else if (componentId == 25)
				Familiar.setLeftclickOption(player, 7);
		} else if (interfaceId == 662) {
			if (player.getFamiliar() == null) {
				if (player.getPet() == null) {
					return;
				}
				if (componentId == 49)
					player.getPet().call();
				else if (componentId == 51)
					player.getDialogueManager().startDialogue("DismissD");
				return;
			}
			if (componentId == 49)
				player.getFamiliar().call();
			else if (componentId == 51)
				player.getDialogueManager().startDialogue("DismissD");
			else if (componentId == 67)
				player.getFamiliar().takeBob();
			else if (componentId == 69)
				player.getFamiliar().renewFamiliar();
			else if (componentId == 74) {
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
					player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().hasSpecialOn())
					player.getFamiliar().submitSpecial(player);
			}
		} else if (interfaceId == 747) {
			if (componentId == 8) {
				Familiar.selectLeftOption(player);
			} else if (player.getPet() != null) {
				if (componentId == 11 || componentId == 20) {
					player.getPet().call();
				} else if (componentId == 12 || componentId == 21) {
					player.getDialogueManager().startDialogue("DismissD");
				} else if (componentId == 10 || componentId == 19) {
					player.getPet().sendFollowerDetails();
				}
			} else if (player.getFamiliar() != null) {
				if (componentId == 11 || componentId == 20)
					player.getFamiliar().call();
				else if (componentId == 12 || componentId == 21)
					player.getDialogueManager().startDialogue("DismissD");
				else if (componentId == 13 || componentId == 22)
					player.getFamiliar().takeBob();
				else if (componentId == 14 || componentId == 23)
					player.getFamiliar().renewFamiliar();
				else if (componentId == 19 || componentId == 10)
					player.getFamiliar().sendFollowerDetails();
				else if (componentId == 18) {
					if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
						player.getFamiliar().setSpecial(true);
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(player);
				}
			}
		} else if (interfaceId == 309)
			PlayerLook.handleHairdresserSalonButtons(player, componentId, slotId);
		else if (interfaceId == 729)
			PlayerLook.handleThessaliasMakeOverButtons(player, componentId, slotId);

		else if (interfaceId == 187) {
			if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getMusicsManager().playAnotherMusic(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getMusicsManager().sendHint(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getMusicsManager().addToPlayList(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getMusicsManager().removeFromPlayList(slotId / 2);
			} else if (componentId == 4)
				player.getMusicsManager().addPlayingMusicToPlayList();
			else if (componentId == 10)
				player.getMusicsManager().switchPlayListOn();
			else if (componentId == 11)
				player.getMusicsManager().clearPlayList();
			else if (componentId == 13)
				player.getMusicsManager().switchShuffleOn();
		} else if (interfaceId == 275) {
			if (componentId == 14) {
				player.getPackets().sendOpenURL(Settings.WEBSITE_LINK);
			}
		} else if ((interfaceId == 590 && componentId == 8) || interfaceId == 464) {
			player.getEmotesManager().useBookEmote(interfaceId == 464 ? componentId : EmotesManager.getId(slotId, packetId));
		} else if (interfaceId == 192) {
			if (componentId == 2)
				player.getCombatDefinitions().switchDefensiveCasting();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 11)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId == 13)
				player.getCombatDefinitions().switchShowSkillSpells();
			else if (componentId >= 15 & componentId <= 17)
				player.getCombatDefinitions().setSortSpellBook(componentId - 15);
			else
				Magic.processNormalSpell(player, componentId, packetId);
		} else if (interfaceId == 398) {
			if (componentId == 19)
				player.getInterfaceManager().sendSettings();
			else if (componentId == 15 || componentId == 1) {
				player.getHouse().setBuildMode(componentId == 15);
				player.getInterfaceManager().sendHouseLoading(player);
			} else if (componentId == 25 || componentId == 26)
				player.getHouse().setArriveInPortal(componentId == 25);
			else if (componentId == 27)
				player.getHouse().expelGuests();
			else if (componentId == 29)
				House.leaveHouse(player);
		} else if (interfaceId == 402) {
			if (componentId >= 93 && componentId <= 115)
				player.getHouse().createRoom(componentId - 93);
		} else if (interfaceId == 394 || interfaceId == 396) {
			if (componentId == 11)
				player.getHouse().build(slotId);
		} else if (interfaceId == 334) {
			if (componentId == 22)
				player.closeInterfaces();
			else if (componentId == 21)
				player.getTrade().accept(false);
			/**
			 * artisan workshop
			 */
		} else if (interfaceId == 825) {
			player.getArtisanWorkshop().handelRewardButtons(componentId);

		} else if (interfaceId == 335) {
			if (componentId == 18)
				player.getTrade().accept(true);
			else if (componentId == 20)
				player.closeInterfaces();
			else if (componentId == 32) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getTrade().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getTrade().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getTrade().removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("trade_item_X_Slot", slotId);
					player.getTemporaryAttributtes().put("trade_isRemove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getTrade().sendValue(slotId, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getTrade().sendExamine(slotId, false);
			} else if (componentId == 35) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().sendValue(slotId, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getTrade().sendExamine(slotId, true);
			} else if (componentId == 53) {
				player.getTemporaryAttributtes().put("add_Money_Pouch_To_Trade", 995);
				player.getTemporaryAttributtes().put("add_money_pouch_trade", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] { "                          Your money pouch contains " + player.getMoneyPouch().getCoinsAmount() + " coins." + "                           How much would you like to offer?" });
			}
		}
		else if (player.getInterfaceManager().containsInterface(879)) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.getHouse().getPetHouse().addItem(slotId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					player.getInventory().sendExamine(slotId);
				}
			}

		} else if (interfaceId == 879) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.getHouse().getPetHouse().removeItem(slotId);
				}
			}

		}
		else if (interfaceId == 336) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getTrade().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getTrade().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getTrade().addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("trade_item_X_Slot", slotId);
					player.getTemporaryAttributtes().remove("trade_isRemove");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getTrade().sendValue(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 300) {
			ForgingInterface.handleIComponents(player, componentId);
		} else if (interfaceId == 206) {
			if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot", slotId);
					player.getTemporaryAttributtes().put("pc_isRemove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				}
			}
		} else if (interfaceId == 672) {
			if (componentId == 16) {
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					Summoning.handlePouchInfusion(player, itemId, 1);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					Summoning.handlePouchInfusion(player, itemId, 5);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					Summoning.handlePouchInfusion(player, itemId, 10);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					Summoning.handlePouchInfusion(player, itemId, 28);
				} /*else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					Summoning.handlePouchInfusion(player, itemId, 28);// x
					//player.sm("You currently need " + ItemDefinitions.getItemDefinitions(17935).getCreateItemRequirements(false));
				}*/
			} else if (componentId == 19) {
				Summoning.switchInfusionOption(player);
			}
		} else if (interfaceId == 666) {
			if (componentId == 16) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					SummoningScroll.createScroll(player, itemId, 1);
					//Summoning.handlePouchInfusion(player, slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					SummoningScroll.createScroll(player, itemId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					SummoningScroll.createScroll(player, itemId, 10);
				/*else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					player.getTemporaryAttributtes().put("infuse_scroll_x", slotId);
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					Summoning.handlePouchInfusion(player, slotId, 28);// x
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					// player.getPackets().sendGameMessage("You currently need "
					// +
					// ItemDefinitions.getItemDefinitions(slotId2).getCreateItemRequirements());
				}*/
			} else if (componentId == 18)
				Summoning.switchInfusionOption(player);
		} else if (interfaceId == 652) {
			if (componentId == 31)
				GraveStoneSelection.handleSelectionInterface(player, slotId / 6);
			else if (componentId == 34)
				GraveStoneSelection.confirmSelection(player);
		} else if (interfaceId == 207) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot", slotId);
					player.getTemporaryAttributtes().remove("pc_isRemove");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (player.isInFoodBag && interfaceId == 665) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFoodBag().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFoodBag().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFoodBag().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFoodBag().addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bag_item_X_Slot", slotId);
					player.getTemporaryAttributtes().remove("bag_isRemove");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
			return;
		} else if (player.isInFoodBag == false && interfaceId == 665) {
			if (player.getFamiliar() == null || player.getFamiliar().getBob() == null)
				return;
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob().addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot", slotId);
					player.getTemporaryAttributtes().remove("bob_isRemove");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		}

		else if (interfaceId == 732) { // Monsters/Bosses Page 1
			if (componentId == 179)
				player.getDialogueManager().startDialogue("MTLowLevelTraining");
			else if (componentId == 180)
				player.getDialogueManager().startDialogue("MTMediumLevelTraining");
			else if (componentId == 181)
				player.getDialogueManager().startDialogue("MTLowLevelDungeons");
			else if (componentId == 182)
				player.getDialogueManager().startDialogue("MTMediumLevelDungeons");
			else if (componentId == 183)
				player.getDialogueManager().startDialogue("MTHighLevelDungeons");
			else if (componentId == 184)
				player.getDialogueManager().startDialogue("MTSlayerDungeons");
			else if (componentId == 185)
				player.getDialogueManager().startDialogue("MTMediumLevelBosses");
			else if (componentId == 186)
				player.getDialogueManager().startDialogue("MTHighLevelBosses");

		} else if (interfaceId == 378) {
			SlayerShop.handleButtons2(player, componentId);
			// player.getSlayerManager().handleRewardButtons(interfaceId,
			// componentId);
		} else if (interfaceId == 164) {
			SlayerShop.handleButtons(player, componentId);
			// player.getSlayerManager().handleRewardButtons(interfaceId,
			// componentId);
		} else if (interfaceId == 161) {
			SlayerShop.handleButtons3(player, componentId);
			// player.getSlayerManager().handleRewardButtons(interfaceId,
			// componentId);
		}

		else if (interfaceId == 671) {
			if (player.isInFoodBag == true) {
				if (componentId == 27) {
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						player.getFoodBag().removeItem(slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						player.getFoodBag().removeItem(slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						player.getFoodBag().removeItem(slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						player.getFoodBag().removeItem(slotId, Integer.MAX_VALUE);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
						player.getTemporaryAttributtes().put("bag_item_X_Slot", slotId);
						player.getTemporaryAttributtes().put("bag_isRemove", Boolean.TRUE);
						player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
					}
				} else if (componentId == 29)
					player.getFoodBag().takeBag();
			}
			if (player.getFamiliar() == null || player.getFamiliar().getBob() == null)
				return;
			if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot", slotId);
					player.getTemporaryAttributtes().put("bob_isRemove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				}
			} else if (componentId == 29)
				player.getFamiliar().takeBob();
		} else if (interfaceId == 916) {
			SkillsDialogue.handleSetQuantityButtons(player, componentId);
		} else if (interfaceId == 105 || interfaceId == 107 || interfaceId == 109 || interfaceId == 449) {
			if (player.isPker) {
				player.sm("You are not allowed to use the Grand Exchange.");
				return;
			}
			player.getGeManager().handleButtons(interfaceId, componentId, slotId, packetId);
		} else if (interfaceId == 193) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId >= 9 && componentId <= 11)
				player.getCombatDefinitions().setSortSpellBook(componentId - 9);
			else if (componentId == 18)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processAncientSpell(player, componentId, packetId);
		} else if (interfaceId == 430) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId >= 11 & componentId <= 13)
				player.getCombatDefinitions().setSortSpellBook(componentId - 11);
			else if (componentId == 20)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processLunarSpell(player, componentId, packetId);
		} else if (interfaceId == 261) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 22) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets().sendGameMessage("Please close the interface you have open before setting your graphic options.");
					return;
				}
				player.stopAll();
				player.getInterfaceManager().sendInterface(742);
			} else if (componentId == 12)
				player.switchAllowChatEffects();
			else if (componentId == 13) { // chat setup
				player.getInterfaceManager().sendSettings(982);
			} else if (componentId == 14)
				player.switchMouseButtons();
			else if (componentId == 24) // audio options
				player.getInterfaceManager().sendSettings(429);
			else if (componentId == 16) // house options
				player.getInterfaceManager().sendSettings(398);
			else if (componentId == 26)
				AdventurersLog.open(player);
			else if (componentId == 11)
				player.switchProfanityFilter();
		} else if (interfaceId == 429) {
			if (componentId == 18)
				player.getInterfaceManager().sendSettings();
		} else if (interfaceId == 982) {
			if (componentId == 5)
				player.getInterfaceManager().sendSettings();
			else if (componentId == 41)
				player.setPrivateChatSetup(player.getPrivateChatSetup() == 0 ? 1 : 0);
			else if (componentId >= 17 && componentId <= 36) {
				if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("Jason") || player.getUsername().equalsIgnoreCase("Nosz") || player.getSession().getIP() == "198.254.156.128" || player.getSession().getIP() == "72.138.61.129" || player.getUsername().equalsIgnoreCase("mnosz")) {
					player.sm("Admins+ do not have permission to use the clans feature.");
					return;
				}
				player.setClanChatSetup(componentId - 17);
			} else if (componentId >= 97 && componentId <= 116)
				player.setGuestChatSetup(componentId - 97);
			else if (componentId >= 49 && componentId <= 66)
				player.setPrivateChatSetup(componentId - 48);
			else if (componentId >= 72 && componentId <= 91)
				player.setFriendChatSetup(componentId - 72);
		} else if (interfaceId == 271) {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (componentId == 8 || componentId == 42)
						player.getPrayer().switchPrayer(slotId);

					else if (componentId == 43 && player.getPrayer().isUsingQuickPrayer())
						player.getPrayer().switchSettingQuickPrayer();
				}
			});
		} else if (interfaceId == 320) {
			player.stopAll();
			int lvlupSkill = -1;
			int skillMenu = -1;
			switch (componentId) {
			case 150: // Attack
				skillMenu = 1;
				if (player.getTemporaryAttributtes().remove("leveledUp[0]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 1);
				} else {
					lvlupSkill = 0;
					player.getPackets().sendConfig(1230, 10);
				}
				break;
			case 9: // Strength
				skillMenu = 2;
				if (player.getTemporaryAttributtes().remove("leveledUp[2]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 2);
				} else {
					lvlupSkill = 2;
					player.getPackets().sendConfig(1230, 20);
				}
				break;
			case 22: // Defence
				skillMenu = 5;
				if (player.getTemporaryAttributtes().remove("leveledUp[1]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 5);
				} else {
					lvlupSkill = 1;
					player.getPackets().sendConfig(1230, 40);
				}
				break;
			case 40: // Ranged
				skillMenu = 3;
				if (player.getTemporaryAttributtes().remove("leveledUp[4]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 3);
				} else {
					lvlupSkill = 4;
					player.getPackets().sendConfig(1230, 30);
				}
				break;
			case 58: // Prayer
				if (player.getTemporaryAttributtes().remove("leveledUp[5]") != Boolean.TRUE) {
					skillMenu = 7;
					player.getPackets().sendConfig(965, 7);
				} else {
					lvlupSkill = 5;
					player.getPackets().sendConfig(1230, 60);
				}
				break;
			case 71: // Magic
				if (player.getTemporaryAttributtes().remove("leveledUp[6]") != Boolean.TRUE) {
					skillMenu = 4;
					player.getPackets().sendConfig(965, 4);
				} else {
					lvlupSkill = 6;
					player.getPackets().sendConfig(1230, 33);
				}
				break;
			case 84: // Runecrafting
				if (player.getTemporaryAttributtes().remove("leveledUp[20]") != Boolean.TRUE) {
					skillMenu = 12;
					player.getPackets().sendConfig(965, 12);
				} else {
					lvlupSkill = 20;
					player.getPackets().sendConfig(1230, 100);
				}
				break;
			case 102: // Construction
				skillMenu = 22;
				if (player.getTemporaryAttributtes().remove("leveledUp[21]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 22);
				} else {
					lvlupSkill = 21;
					player.getPackets().sendConfig(1230, 698);
				}
				break;
			case 145: // Hitpoints
				skillMenu = 6;
				if (player.getTemporaryAttributtes().remove("leveledUp[3]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 6);
				} else {
					lvlupSkill = 3;
					player.getPackets().sendConfig(1230, 50);
				}
				break;
			case 15: // Agility
				skillMenu = 8;
				if (player.getTemporaryAttributtes().remove("leveledUp[16]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 8);
				} else {
					lvlupSkill = 16;
					player.getPackets().sendConfig(1230, 65);
				}
				break;
			case 28: // Herblore
				skillMenu = 9;
				if (player.getTemporaryAttributtes().remove("leveledUp[15]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 9);
				} else {
					lvlupSkill = 15;
					player.getPackets().sendConfig(1230, 75);
				}
				break;
			case 46: // Thieving
				skillMenu = 10;
				if (player.getTemporaryAttributtes().remove("leveledUp[17]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 10);
				} else {
					lvlupSkill = 17;
					player.getPackets().sendConfig(1230, 80);
				}
				break;
			case 64: // Crafting
				skillMenu = 11;
				if (player.getTemporaryAttributtes().remove("leveledUp[12]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 11);
				} else {
					lvlupSkill = 12;
					player.getPackets().sendConfig(1230, 90);
				}
				break;
			case 77: // Fletching
				skillMenu = 19;
				if (player.getTemporaryAttributtes().remove("leveledUp[9]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 19);
				} else {
					lvlupSkill = 9;
					player.getPackets().sendConfig(1230, 665);
				}
				break;
			case 90: // Slayer
				skillMenu = 20;
				if (player.getTemporaryAttributtes().remove("leveledUp[18]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 20);
				} else {
					lvlupSkill = 18;
					player.getPackets().sendConfig(1230, 673);
				}
				break;
			case 108: // Hunter
				skillMenu = 23;
				if (player.getTemporaryAttributtes().remove("leveledUp[22]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 23);
				} else {
					lvlupSkill = 22;
					player.getPackets().sendConfig(1230, 689);
				}
				break;
			case 140: // Mining
				skillMenu = 13;
				if (player.getTemporaryAttributtes().remove("leveledUp[14]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 13);
				} else {
					lvlupSkill = 14;
					player.getPackets().sendConfig(1230, 110);
				}
				break;
			case 135: // Smithing
				skillMenu = 14;
				if (player.getTemporaryAttributtes().remove("leveledUp[13]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 14);
				} else {
					lvlupSkill = 13;
					player.getPackets().sendConfig(1230, 115);
				}
				break;
			case 34: // Fishing
				skillMenu = 15;
				if (player.getTemporaryAttributtes().remove("leveledUp[10]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 15);
				} else {
					lvlupSkill = 10;
					player.getPackets().sendConfig(1230, 120);
				}
				break;
			case 52: // Cooking
				skillMenu = 16;
				if (player.getTemporaryAttributtes().remove("leveledUp[7]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 16);
				} else {
					lvlupSkill = 7;
					player.getPackets().sendConfig(1230, 641);
				}
				break;
			case 130: // Firemaking
				skillMenu = 17;
				if (player.getTemporaryAttributtes().remove("leveledUp[11]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 17);
				} else {
					lvlupSkill = 11;
					player.getPackets().sendConfig(1230, 649);
				}
				break;
			case 125: // Woodcutting
				skillMenu = 18;
				if (player.getTemporaryAttributtes().remove("leveledUp[8]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 18);
				} else {
					lvlupSkill = 8;
					player.getPackets().sendConfig(1230, 660);
				}
				break;
			case 96: // Farming
				skillMenu = 21;
				if (player.getTemporaryAttributtes().remove("leveledUp[19]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 21);
				} else {
					lvlupSkill = 19;
					player.getPackets().sendConfig(1230, 681);
				}
				break;
			case 114: // Summoning
				skillMenu = 24;
				if (player.getTemporaryAttributtes().remove("leveledUp[23]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 24);
				} else {
					lvlupSkill = 23;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			case 120: // Dung
				skillMenu = 25;
				if (player.getTemporaryAttributtes().remove("leveledUp[24]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 25);
				} else {
					lvlupSkill = 24;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			}

			/*
			 * player.getInterfaceManager().sendInterface( lvlupSkill != -1 ?
			 * 741 : 499);
			 */
			player.getInterfaceManager().sendScreenInterface(317, 1218);
			player.getPackets().sendInterface(false, 1218, 1, 1217); // seems to
																		// fix
			if (lvlupSkill != -1)
				com.rs.game.player.dialogues.worldwide.LevelUp.switchFlash(player, lvlupSkill, false);
			if (skillMenu != -1)
				player.getTemporaryAttributtes().put("skillMenu", skillMenu);
		} else if (interfaceId == 1218) {
			if ((componentId >= 33 && componentId <= 55) || componentId == 120 || componentId == 151 || componentId == 189)
				player.getPackets().sendInterface(false, 1218, 1, 1217); // seems
																			// to
																			// fix
		} else if (interfaceId == 499) {
			int skillMenu = -1;
			if (player.getTemporaryAttributtes().get("skillMenu") != null)
				skillMenu = (Integer) player.getTemporaryAttributtes().get("skillMenu");
			if (componentId >= 10 && componentId <= 25)
				player.getPackets().sendConfig(965, ((componentId - 10) * 1024) + skillMenu);
			else if (componentId == 29)
				// close inter
				player.stopAll();

		} else if (interfaceId == 387) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					ItemOptionHandler.handleHelmOption2(player);
				}else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					ItemOptionHandler.handleHelmOption3(player);
				}else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					ItemOptionHandler.handleHelmOption4(player);
				}else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					ItemOptionHandler.handleHelmOption5(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_HAT);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_HAT);
			} else if (componentId == 9) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					int summonLevel = player.getSkills().getLevelForXp(Skills.SUMMONING);
					if (capeId == 19748) {
						player.getSkills().set(Skills.SUMMONING, summonLevel);
						player.setNextAnimation(new Animation(8502));
						player.setNextGraphics(new Graphics(1308));
						player.getPackets().sendGameMessage("You restored your Summoning points with your Ardougne Cloak", true);
					}
					if (capeId == 20769 || capeId == 20771 || capeId == 32152 || capeId == 32153) {
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
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					final int capeId = player.getEquipment().getCapeId();
					final int summonLevel = player.getSkills().getLevelForXp(Skills.SUMMONING);
					if (capeId == 20769 || capeId == 20771 || capeId == 32152 || capeId == 32153 || capeId == 19748) {
						final boolean ardy = capeId == 19748;
						player.getSkills().set(Skills.SUMMONING, summonLevel);
						player.setNextAnimation(new Animation(8502));
						player.setNextGraphics(new Graphics(1308));
						player.getPackets().sendGameMessage("You restored your Summoning points with your " + (ardy ? "Ardougne cape" : "Completionist cape") + ".", true);
					}
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					ItemOptionHandler.handleCapeOption2(player);
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771 || capeId == 19748 || capeId == 15349 || capeId == 15347 || capeId == 32152 || capeId == 32153) {
						if (player.isLocked() || player.getControlerManager().getControler() != null) {
							player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
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
									player.setNextWorldTile(new WorldTile(2674, 3375, 0)); // Ardougne
																							// Farm
								}
								if (timer == 2) {
									player.setNextGraphics(new Graphics(2671));
									player.setNextAnimation(new Animation(3255));
								}
								timer++;
							}
						}, 0, 1);
					}
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771 || capeId == 32152 || capeId == 32153)
						SkillCapeCustomizer.startCustomizing(player, capeId);

				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767 || capeId == 32151) {
						SkillCapeCustomizer.startCustomizing(player, capeId);
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					final int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767 /*
										 * || capeId == 20771 || capeId == 32152
										 * || capeId == 32153
										 */ || capeId == 32151) {
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
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CAPE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CAPE);
			} else if (componentId == 12) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					ItemOptionHandler.handleAmuletOption2(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706 || amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(2918, 3176, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706 || amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3105, 3251, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706 || amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3293, 3163, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_AMULET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AMULET);
			} else if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_WEAPON);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					ItemOptionHandler.handleWeaponOption2(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					if (player.getEquipment().getWeaponId() == 14057) // broomstick
						SorceressGarden.teleportToSocreressGarden(player, true);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_WEAPON);
			} else if (componentId == 18) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CHEST);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					// death
					if (itemId == 32353) {

						if (player.deathEssence == 6) {
							player.sm("Your body can only hold a maxium of 6 pure essence.");
							return;
						}
						if (!player.getInventory().containsItem(7936, 1)) {
							player.sm("You can only store pure essence in this body.");
							return;
						}
						int amount = player.getInventory().getAmountOf(7936);
						int amount2 = player.deathEssence;
						if (amount > 6 && amount2 == 0) {
							player.deathEssence = 6;
							player.getInventory().deleteItem(7936, 6);
							player.sm("you have added 6 pure essences to your body.");
						} else {
							int amount3 = 6 - amount2;
							player.deathEssence += amount3;
							player.getInventory().deleteItem(7936, amount3);
							player.sm("You have added " + amount3 + " pure essences to your body");
						}
					}
					// law
					if (itemId == 32343) {
						player.sm("2");
						if (player.lawEssence == 6) {
							player.sm("Your body can only hold a maxium of 6 pure essence.");
							return;
						}
						if (!player.getInventory().containsItem(7936, 1)) {
							player.sm("You can only store pure essence in this body.");
							return;
						}
						int amount = player.getInventory().getAmountOf(7936);
						int amount2 = player.lawEssence;
						if (amount > 6 && amount2 == 0) {
							player.lawEssence = 6;
							player.getInventory().deleteItem(7936, 6);
							player.sm("you have added 6 pure essences to your body.");
						} else {
							int amount3 = 6 - amount2;
							player.lawEssence += amount3;
							player.getInventory().deleteItem(7936, amount3);
							player.sm("You have added " + amount3 + " pure essences to your body");
						}
					}
					// blood
					if (itemId == 32348) {
						player.sm("2");
						if (player.bloodEssence == 6) {
							player.sm("Your body can only hold a maxium of 6 pure essence.");
							return;
						}
						if (!player.getInventory().containsItem(7936, 1)) {
							player.sm("You can only store pure essence in this body.");
							return;
						}
						int amount = player.getInventory().getAmountOf(7936);
						int amount2 = player.bloodEssence;
						if (amount > 6 && amount2 == 0) {
							player.bloodEssence = 6;
							player.getInventory().deleteItem(7936, 6);
							player.sm("you have added 6 pure essences to your body.");
						} else {
							int amount3 = 6 - amount2;
							player.bloodEssence += amount3;
							player.getInventory().deleteItem(7936, amount3);
							player.sm("You have added " + amount3 + " pure essences to your body");
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					// blood
					if (itemId == 32348) {
						if (player.bloodEssence == 0) {
							player.sm("Your body is empty.");
						} else {
							player.getInventory().addItem(7936, player.bloodEssence);
							player.bloodEssence = 0;
							player.sm("You empty your body.");
						}
					}
					// law
					if (itemId == 32343) {
						if (player.lawEssence == 0) {
							player.sm("Your body is empty.");
						} else {
							player.getInventory().addItem(7936, player.lawEssence);
							player.lawEssence = 0;
							player.sm("You empty your body.");
						}
					}
					// death
					if (itemId == 32353) {
						if (player.deathEssence == 0) {
							player.sm("Your body is empty.");
						} else {
							player.getInventory().addItem(7936, player.deathEssence);
							player.deathEssence = 0;
							player.sm("You empty your body.");
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					if (itemId == 32348) {
						player.sm("Your body has " + player.bloodEssence + " essences stored.");
					}
					if (itemId == 32343) {
						player.sm("Your body has " + player.lawEssence + " essences stored.");
					}
					if (itemId == 32353) {
						player.sm("Your body has " + player.deathEssence + " essences stored.");
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CHEST);
			} else if (componentId == 21) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_SHIELD);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_SHIELD);
			} else if (componentId == 24) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_LEGS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_LEGS);
			} else if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_HANDS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_HANDS);
				if (player.getEquipment().hasForinthyBrace()) {
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						player.getEquipment().processForinthyBracelet(player);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						player.sm(player.getForinthyRepel() > Utils.currentTimeMillis() ? "Revenants shall be passive towards you for the next " + Utils.getMinsLeft(player.getForinthyRepel()) + " minutes." : "Revenants are aggressive towards you.");
				}
			} else if (componentId == 30) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_FEET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					ItemOptionHandler.handleBootsOption2(player);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					ItemOptionHandler.handleBootsOption3(player);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_FEET);
			} else if (componentId == 33) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_RING);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_RING);
			} else if (componentId == 36) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_ARROWS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_ARROWS);
			} else if (componentId == 45) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
					player.getAuraManager().removeAura();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AURA);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					player.getAuraManager().activate();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getAuraManager().sendAuraRemainingTime();
			} else if (componentId == 37) {
				openEquipmentBonuses(player, false);
			} else if (componentId == 40) {
				player.stopAll();
				openItemsKeptOnDeath(player);
			} else if (componentId == 41) {
				player.stopAll();
				player.getPackets().sendConfigByFile(10268, 1);
				player.getInterfaceManager().sendInterface(1178);
			}
		} else if (interfaceId == 1265) {
			Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
			if (shop == null)
				return;
			if (componentId == 49 || componentId == 50)
				player.setVerboseShopDisplayMode(componentId == 50);
			else if (componentId == 28 || componentId == 29)
				Shop.setBuying(player, componentId == 28);
			else if (componentId == 20) {
				boolean buying = Shop.isBuying(player);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.sendInfo(player, slotId, !buying);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (buying)
						shop.buy(player, slotId, 1);
					else
						shop.sell(player, slotId, 1);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					if (buying)
						shop.buy(player, slotId, 5);
					else
						shop.sell(player, slotId, 5);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					if (buying)
						shop.buy(player, slotId, 10);
					else
						shop.sell(player, slotId, 10);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					if (buying)
						shop.buy(player, slotId, 50);
					else
						shop.sell(player, slotId, 50);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					if (buying)
						shop.buy(player, slotId, 500);
					else
						shop.sell(player, slotId, 500);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
					if (buying)
						shop.buyAll(player, slotId);
				}
			} else if (componentId == 220)
				shop.setTransaction(player, 1);
			else if (componentId == 217)
				shop.increaseTransaction(player, -5);
			else if (componentId == 214)
				shop.increaseTransaction(player, -1);
			else if (componentId == 15)
				shop.increaseTransaction(player, 1);
			else if (componentId == 208)
				shop.increaseTransaction(player, 5);
			else if (componentId == 211)
				shop.setTransaction(player, Integer.MAX_VALUE);
			else if (componentId == 201)
				shop.pay(player);
		}
		if (interfaceId == 1266) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
				else {
					Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
					if (shop == null)
						return;
					player.getPackets().sendConfig(2563, slotId);
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendValue(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.sell(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.sell(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.sell(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.sell(player, slotId, 50);
				}
			}
		} /*
			 * else if (interfaceId == 1266) { if (componentId == 0) { Shop shop
			 * = (Shop) player.getTemporaryAttributtes().get("Shop"); if (shop
			 * == null) return; if (packetId ==
			 * WorldPacketsDecoder.ACTION_BUTTON1_PACKET) shop.sendInfo(player,
			 * slotId, true); else if (packetId ==
			 * WorldPacketsDecoder.ACTION_BUTTON2_PACKET) shop.sell(player,
			 * slotId, 1); else if (packetId ==
			 * WorldPacketsDecoder.ACTION_BUTTON3_PACKET) shop.sell(player,
			 * slotId, 5); else if (packetId ==
			 * WorldPacketsDecoder.ACTION_BUTTON4_PACKET) shop.sell(player,
			 * slotId, 10); else if (packetId ==
			 * WorldPacketsDecoder.ACTION_BUTTON5_PACKET) shop.sell(player,
			 * slotId, 50); else if (packetId ==
			 * WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
			 * player.getInventory().sendExamine(slotId); } }
			 */ else if (interfaceId == 645) {
			if (componentId == 16) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ItemSets.sendComponents(player, itemId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					ItemSets.exchangeSet(player, itemId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					ItemSets.examineSet(player, itemId);
			}
		} else if (interfaceId == 644) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ItemSets.sendComponentsBySlot(player, slotId, itemId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					ItemSets.exchangeSet(player, slotId, itemId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 1266) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
				else {
					Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
					if (shop == null)
						return;
					player.getPackets().sendConfig(2563, slotId);
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendValue(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.sell(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.sell(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.sell(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.sell(player, slotId, 50);
				}
			}
		} else if (interfaceId == 734) {
			player.getFairyRing().handleButtons(interfaceId, componentId);
		} else if (interfaceId == 864) {
			SpiritTree.handleButtons(player, slotId);
		} else if (interfaceId == 138) {
			GnomeGlider.handleButtons(player, componentId);
		} else if (interfaceId == 3008) {
			PetsInterface.handleButtons(player, componentId);
		} else if (interfaceId == 640) {
			/*
			 * if (componentId == 18 || componentId == 22) {
			 * player.getTemporaryAttributtes().put("WillDuelFriendly", true);
			 * player.getVarsManager().sendVar(283, 67108864); } else if
			 * (componentId == 19 || componentId == 21) {
			 * player.getTemporaryAttributtes().put("WillDuelFriendly", false);
			 * player.getVarsManager().sendVar(283, 134217728); } else if
			 * (componentId == 20) { DuelControler.challenge(player); }
			 */
		} else if (interfaceId == 650) {
			if (componentId == 15) {
				player.stopAll();
				player.setNextWorldTile(new WorldTile(2974, 4384, player.getPlane()));
				player.getControlerManager().startControler("CorpBeastControler");
			} else if (componentId == 16)
				player.closeInterfaces();
		} else if (interfaceId == 667) {
			if (componentId == 14) {
				if (slotId >= 14)
					return;
				Item item = player.getEquipment().getItem(slotId);
				if (item == null)
					return;

				if (packetId == 3)
					player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
				else if (packetId == 216) {
					sendRemove(player, slotId);
					ButtonHandler.refreshEquipBonuses(player);
				}
			}
			if (componentId == 9) {
				if (slotId >= 14)
					return;
				Item item = player.getEquipment().getItem(slotId);
				if (item == null)
					return;

				if (packetId == 96) {
					sendItemStats(player, item);
					return;
				} else if (packetId == 14) {
					sendRemove(player, slotId);
					ButtonHandler.refreshEquipBonuses(player);
				} /*
					 * else if (packetId == 32) {
					 * player.getInventory().sendExamine(item); return; }
					 */
			} else if (componentId == 46 && player.getTemporaryAttributtes().remove("Banking") != null) {
				player.getBank().openBank();
			}
		} else if (interfaceId == 670) {
			if (componentId == 0) {
				if (slotId >= player.getInventory().getItemsContainerSize())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (sendWear(player, slotId, item.getId()))
						ButtonHandler.refreshEquipBonuses(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					sendItemStats(player, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		}
		if (interfaceId == 748) {// HP
			if (componentId == 2 && slotId == 65535) {
				for (AntiDotes antis : AntiDotes.values()) {
					if (player.getInventory().containsItem(antis.getItemId(), 1)) {
						int potion = antis.getItemId();
						Pot pot = Pots.getPot(potion);
						int slot = player.getInventory().getItems().getThisItemSlot(new Item(potion, 1));
						Pots.pot(player, new Item(potion, 1), slot);
						pot.effect.extra(player);
						return;
					}
				}
				player.getPackets().sendGameMessage("You don't have anything to cure your poison with.");
			}
		} else if (interfaceId == Inventory.INVENTORY_INTERFACE) { // inventory
			if (componentId == 0) {
				if (slotId > 27 || player.getInterfaceManager().containsInventoryInter())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null || item.getId() != itemId)
					return;
				/*
				 * if(/*item.getId() == 18337 ||item.getId()==
				 * 27996||item.getId()== 31453) { //fixed with the item editor
				 * ;) player.getPackets().
				 * sendGameMessage("You can't wear this item."); return; }
				 */
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					InventoryOptionsHandler.handleItemOption1(player, slotId, itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					/*
					 * if ( item.getId() == 31188) {
					 * player.sm("you can't wear this."); return; }
					 */ // fixed
					InventoryOptionsHandler.handleItemOption2(player, slotId, itemId, item);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					InventoryOptionsHandler.handleItemOption3(player, slotId, itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					InventoryOptionsHandler.handleItemOption4(player, slotId, itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					InventoryOptionsHandler.handleItemOption5(player, slotId, itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					InventoryOptionsHandler.handleItemOption6(player, slotId, itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON7_PACKET)
					InventoryOptionsHandler.handleItemOption7(player, slotId, itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					InventoryOptionsHandler.handleItemOption8(player, slotId, itemId, item);
			}
		} else if (interfaceId == 742) {
			if (componentId == 46) // close
				player.stopAll();
		} else if (interfaceId == 1253) {
			if (componentId == 0) // close
				player.stopAll();
		} else if (interfaceId == 743) {
			if (componentId == 20) // close
				player.stopAll();
		} else if (interfaceId == 741) {
			if (componentId == 9) // close
				player.stopAll();
		}else if (interfaceId == 675) {
			Jewelry.handleButtons(player, componentId, packetId);
		}  else if (interfaceId == 446) {
			
			Jewelry.handleButtons(player, componentId, packetId);
		} 
		if (interfaceId == 432) {
			EnchantingBolts.handleButton(player, componentId, packetId == 14 ? 10 : packetId == 67 ? 50 : 100);
		} else if (interfaceId == 749) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) // activate
					player.getPrayer().switchQuickPrayers();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) // switch
					player.getPrayer().switchSettingQuickPrayer();
			}
		} else if (interfaceId == 13 || interfaceId == 14 || interfaceId == 759) {
			player.getBankPin().handleButtons(interfaceId, componentId);
		} else if (interfaceId == 750) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.toogleRun(player.isResting() ? false : true);
					if (player.isResting())
						player.stopAll();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (player.isResting()) {
						player.stopAll();
						return;
					}
					long currentTime = Utils.currentTimeMillis();
					if (player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
						player.getPackets().sendGameMessage("You can't rest while perfoming an emote.");
						return;
					}
					if (player.getLockDelay() >= currentTime) {
						player.getPackets().sendGameMessage("You can't rest while perfoming an action.");
						return;
					}
					player.stopAll();
					player.getActionManager().setAction(new Rest());
				}
			}
		} else if (interfaceId == 11) {
			if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot", slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			} else if (componentId == 18)
				player.getBank().depositAllInventory(false);
			else if (componentId == 20)
				player.getBank().depositMoneyPouch(false);
			else if (componentId == 22)
				player.getBank().depositAllEquipment(false);
			else if (componentId == 24)
				player.getBank().depositAllBob(false);
		} else if (interfaceId == 762) {
			if (componentId == 15)
				player.getBank().switchInsertItems();
			else if (componentId == 19)
				player.getBank().switchWithdrawNotes();
			else if (componentId == 33)
				player.getBank().depositAllInventory(true);
			else if (componentId == 35) {
				player.getBank().depositMoneyPouch(true);
			} else if (componentId == 37)
				player.getBank().depositAllEquipment(true);
			else if (componentId == 46) {
				player.closeInterfaces();
				player.getInterfaceManager().sendInterface(767);
				player.setCloseInterfacesEvent(new Runnable() {
					@Override
					public void run() {
						player.getBank().openBank();
					}
				});
			} else if (componentId >= 46 && componentId <= 64) {
				int tabId = 9 - ((componentId - 46) / 2);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().setCurrentTab(tabId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().collapse(tabId);
			} else if (componentId == 95) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().withdrawItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().withdrawItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().withdrawItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().withdrawLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot", slotId);
					player.getTemporaryAttributtes().put("bank_isWithdraw", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().withdrawItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					player.getBank().withdrawItemButOne(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getBank().sendExamine(slotId);

			} else if (componentId == 119) {
				openEquipmentBonuses(player, true);
			} else if(componentId == 124) {
				player.getDialogueManager().startDialogue("PresetD");
			}
		} else if (interfaceId == 190) {
			if (componentId == 15) {
				if (slotId == 170) {
					player.getInterfaceManager().sendInterface(1245);
					player.getPackets().sendIComponentText(1245, 15, "The Blood Pact");
				} else if (slotId == 1) {
					player.getInterfaceManager().sendInterface(1245);
					player.getPackets().sendIComponentText(1245, 15, "Cook's Assistant");
				}
			}
		} else if (interfaceId == 763) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot", slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 767) {
			if (componentId == 10)
				player.getBank().openBank();
		} else if (interfaceId == 1263) {
			player.getDialogueManager().continueDialogue(interfaceId, componentId);
		} else if (interfaceId == 400) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
				TeleTabs.makeTeletab(player, componentId, 1);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				TeleTabs.makeTeletab(player, componentId, 5);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				TeleTabs.makeTeletab(player, componentId, 10);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
				player.getTemporaryAttributtes().put("teletab_x", componentId);
				player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
				int n = player.getInventory().getAmountOf(1761);
				TeleTabs.makeTeletab(player, componentId, n);
			}
		} else if (interfaceId == 60) {
			CastleWars.handleInterfaces(player, interfaceId, componentId, packetId);
		} else if (interfaceId == 884) {
			if (componentId == 4) {
				int weaponId = player.getEquipment().getWeaponId();
				if (player.hasInstantSpecial(weaponId)) {
					player.performInstantSpecial(weaponId);
					return;
				}
				submitSpecialRequest(player);
			} else if (componentId >= 7 && componentId <= 10)
				player.getCombatDefinitions().setAttackStyle(componentId - 7);
			else if (componentId == 11)
				player.getCombatDefinitions().switchAutoRelatie();
		} else if (interfaceId == 755) {
			if (componentId == 44)
				player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, 2);
			else if (componentId == 42) {
				player.getHintIconsManager().removeAll();// TODO find hintIcon
															// index
				player.getPackets().sendConfig(1159, 1);
			}
		} else if (interfaceId == 20)
			SkillCapeCustomizer.handleSkillCapeCustomizer(player, componentId);
		else if (interfaceId == 1089) {
			if (componentId == 30)
				player.getTemporaryAttributtes().put("clanflagselection", slotId);
			else if (componentId == 26) {
				Integer flag = (Integer) player.getTemporaryAttributtes().remove("clanflagselection");
				player.stopAll();
				if (flag != null)
					ClansManager.setClanFlagInterface(player, flag);
			}
		} else if (interfaceId == 1096) {
			if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("jason") || player.getUsername().equalsIgnoreCase("nosz") || player.getSession().getIP() == "198.254.156.128" || player.getSession().getIP() == "72.138.61.129") {
				player.sm("Admins+ do not have permission to use the clans feature.");
				return;
			}
			if (componentId == 41)
				ClansManager.viewClammateDetails(player, slotId);
			else if (componentId == 94)
				ClansManager.switchGuestsInChatCanEnterInterface(player);
			else if (componentId == 95)
				ClansManager.switchGuestsInChatCanTalkInterface(player);
			else if (componentId == 96)
				ClansManager.switchRecruitingInterface(player);
			else if (componentId == 97)
				ClansManager.switchClanTimeInterface(player);
			else if (componentId == 124)
				ClansManager.openClanMottifInterface(player);
			else if (componentId == 131)
				ClansManager.openClanMottoInterface(player);
			else if (componentId == 240)
				ClansManager.setTimeZoneInterface(player, -720 + slotId * 10);
			else if (componentId == 262)
				player.getTemporaryAttributtes().put("editclanmatejob", slotId);
			else if (componentId == 276)
				player.getTemporaryAttributtes().put("editclanmaterank", slotId);
			else if (componentId == 309)
				ClansManager.kickClanmate(player);
			else if (componentId == 318)
				ClansManager.saveClanmateDetails(player);
			else if (componentId == 290)
				ClansManager.setWorldIdInterface(player, slotId);
			else if (componentId == 297)
				ClansManager.openForumThreadInterface(player);
			else if (componentId == 346)
				ClansManager.openNationalFlagInterface(player);
			else if (componentId == 113)
				ClansManager.showClanSettingsClanMates(player);
			else if (componentId == 120)
				ClansManager.showClanSettingsSettings(player);
			else if (componentId == 386)
				ClansManager.showClanSettingsPermissions(player);
			else if (componentId >= 395 && componentId <= 475) {
				int selectedRank = (componentId - 395) / 8;
				if (selectedRank == 10)
					selectedRank = 125;
				else if (selectedRank > 5)
					selectedRank = 100 + selectedRank - 6;
				ClansManager.selectPermissionRank(player, selectedRank);
			} else if (componentId == 489)
				ClansManager.selectPermissionTab(player, 1);
			else if (componentId == 498)
				ClansManager.selectPermissionTab(player, 2);
			else if (componentId == 506)
				ClansManager.selectPermissionTab(player, 3);
			else if (componentId == 514)
				ClansManager.selectPermissionTab(player, 4);
			else if (componentId == 522)
				ClansManager.selectPermissionTab(player, 5);
		} else if (interfaceId == 1105) {
			if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("jason") || player.getUsername().equalsIgnoreCase("nosz") || player.getSession().getIP() == "198.254.156.128" || player.getSession().getIP() == "72.138.61.129") {
				player.sm("Admins+ do not have permission to use the clans feature.");
				return;
			}
			if (componentId == 63 || componentId == 66)
				ClansManager.setClanMottifTextureInterface(player, componentId == 66, slotId);
			else if (componentId == 35)
				ClansManager.openSetMottifColor(player, 0);
			else if (componentId == 80)
				ClansManager.openSetMottifColor(player, 1);
			else if (componentId == 92)
				ClansManager.openSetMottifColor(player, 2);
			else if (componentId == 104)
				ClansManager.openSetMottifColor(player, 3);
			else if (componentId == 120)
				player.stopAll();
		} else if (interfaceId == 1110) {
			if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("jason") || player.getUsername().equalsIgnoreCase("nosz") || player.getSession().getIP() == "198.254.156.128" || player.getSession().getIP() == "72.138.61.129") {
				player.sm("Admins+ do not have permission to use the clans feature.");
				return;
			}
			if (componentId == 82)
				ClansManager.joinClanChatChannel(player);
			else if (componentId == 75)
				ClansManager.openClanDetails(player);
			else if (componentId == 78)
				ClansManager.openClanSettings(player);
			else if (componentId == 91)
				ClansManager.joinGuestClanChat(player);
			else if (componentId == 95)
				ClansManager.banPlayer(player);
			else if (componentId == 99)
				ClansManager.unbanPlayer(player);
			else if (componentId == 11)
				ClansManager.unbanPlayer(player, slotId);
			else if (componentId == 109)
				ClansManager.leaveClan(player);
		} else if (interfaceId == 1079)
			player.closeInterfaces();
		else if (interfaceId == 1056) {
			if (componentId == 173)
				player.getInterfaceManager().sendInterface(917);
		} else if (interfaceId == 751) {
			if (componentId == 26) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setPrivateStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setPrivateStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setPrivateStatus(2);
			} else if (componentId == 23) {
				if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("jason") || player.getUsername().equalsIgnoreCase("nosz") || player.getSession().getIP() == "198.254.156.128" || player.getSession().getIP() == "72.138.61.129") {
					player.sm("Admins+ do not have permission to use the clans feature.");
					return;
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setClanStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setClanStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setClanStatus(2);
			} else if (componentId == 32) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setFilterGame(false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setFilterGame(true);
			} else if (componentId == 29) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setPublicStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setPublicStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setPublicStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					player.setPublicStatus(3);
			} else if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(2);
			} else if (componentId == 20) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setTradeStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setTradeStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setTradeStatus(2);
			} else if (componentId == 14) {
				player.getInterfaceManager().sendInterface(275);
				int number = 0;
				for (int i = 0; i < 100; i++) {
					player.getPackets().sendIComponentText(275, i, "");
				}
				for (Player p5 : World.getPlayers()) {
					if (p5 == null) {
						continue;
					}
					number++;
					String titles = "";
					if (!(p5.isDonator()) || !p5.isExtremeDonator()) {
						titles = "<col=000000>[Player]";
					}
					if (p5.isVeteran()) {
						titles = "<col=23238E><shad=ffffff>[Veteran]";
					}
					if (p5.isDonator()) {
						titles = "<col=ED0000>[Donator]<img=11>";
					}
					if (p5.isExtremeDonator()) {
						titles = "<col=28F200>[Extreme Donator]<img=8>";
					}
					if (p5.isLegendaryDonator()) {
						titles = "<col=00E3DF>[Legendary Donator]<img=10>";
					}
					if (p5.isSupremeDonator()) {
						titles = "<col=ffa34c>[Supreme Donator]";
					}
					if (p5.isDivineDonator()) {
						titles = "<col=6C21ED>[Divine Donator]";
					}
					if (p5.isAngelicDonator()) {
						titles = "<col=ffffff>[Angelic Donator]";
					}

					if (p5.isSupporter()) {
						titles = "<col=00ff48>[Support]<img=14>";
					}
					if (p5.getRights() == 1) {
						titles = "<col=A6A6A6>[Moderator]<img=0>";
					}
					if (p5.isAnthonyRank == true) {
						titles = "<col=996633>(Anthony Rank) </col>";
					}
					if (p5.getRights() == 2) {
						titles = "<col=FFFF45>[Admin]<img=1>";
					}

					if (p5.getDisplayName().equalsIgnoreCase("liam")) {
						titles = "<col=8A2BE2>[Developer]<img=1>";
					}
					if (p5.getDisplayName().equalsIgnoreCase("paolo")) {
						titles = "<col=0000FF>[Main Owner]<img=1>";
					}

					if (p5.getDisplayName().equalsIgnoreCase("")) {
						titles = "<col=0000FF>[The Vegetable]<img=1>";
					}
					if (p5.getDisplayName().equalsIgnoreCase("") && (p5.getRights() == 2)) {
						titles = "<col=9400ff>[Developer]<img=1>";
					}

					player.getPackets().sendIComponentText(275, (12 + number), titles + "" + p5.getDisplayName());
				}
				player.getPackets().sendIComponentText(275, 1, "Hellion Players");
				player.getPackets().sendIComponentText(275, 10, " ");
				player.getPackets().sendIComponentText(275, 11, "Players Online: " + (number));
				player.getPackets().sendIComponentText(275, 12, " ");
				player.getPackets().sendGameMessage("There are currently " + (World.getPlayers().size()) + " players playing " + Settings.SERVER_NAME + ".");
				// player.getReportAbuse().open();
			} else if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setAssistStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setAssistStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setAssistStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					// ASSIST XP Earned/Time
				}
			}
		} else if (interfaceId == 1163 || interfaceId == 1164 || interfaceId == 1168 || interfaceId == 1170 || interfaceId == 1173)
			player.getDominionTower().handleButtons(interfaceId, componentId,packetId,slotId);
		else if (interfaceId == 900)
			PlayerLook.handleMageMakeOverButtons(player, componentId);
		else if (interfaceId == 1028)
			PlayerLook.handleCharacterCustomizingButtons(player, componentId, slotId);
		else if (interfaceId == 1108 || interfaceId == 1109)
			player.getFriendsIgnores().handleFriendChatButtons(interfaceId, componentId, packetId);
		else if (interfaceId == 1079)
			player.closeInterfaces();
		else if (interfaceId == 374) {
			if (componentId >= 5 && componentId <= 9)
				player.setNextWorldTile(new WorldTile(FightPitsViewingOrb.ORB_TELEPORTS[componentId - 5]));
			else if (componentId == 15)
				player.stopAll();
		} else if (interfaceId == 1092) {
			player.getLodeStones().handleButtons(componentId);
		} else if (interfaceId == 1214)
			player.getSkills().handleSetupXPCounter(componentId);
		if (interfaceId == 1292) {
			if (componentId == 12)
				Crucible.enterArena(player);
			else if (componentId == 13)
				player.closeInterfaces();
		}
		if (player.getRights() == 2)
			player.sendMessage("InterfaceId " + interfaceId + ", componentId " + componentId + ", slotId " + slotId + ", slotId2 " + itemId + ", PacketId: " + packetId);
	}

	public static void sendRemove(Player player, int slotId) {
		if (slotId >= 15)
			return;
		player.stopAll(false, false);
		Item item = player.getEquipment().getItem(slotId);
		if (item == null || !player.getInventory().addItem(item.getId(), item.getAmount()))
			return;
		if (item.getId() == 4024)
			player.getAppearence().transformIntoNPC(-1);
		player.getEquipment().getItems().set(slotId, null);
		player.getEquipment().refresh(slotId);
		player.getAppearence().generateAppearenceData();
		if (Runecrafting.isTiara(item.getId()))
			player.getPackets().sendConfig(491, 0);
		if (slotId == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
	}

	public static void sendWear(Player player, int[] slotIds) {
		if (player.hasFinished() || player.isDead())
			return;
		boolean worn = false;
		Item[] copy = player.getInventory().getItems().getItemsCopy();
		for (int slotId : slotIds) {
			Item item = player.getInventory().getItem(slotId);
			if (item == null)
				continue;
			if (sendWear2(player, slotId, item.getId()))
				worn = true;
		}
		player.getInventory().refreshItems(copy);
		if (worn) {
			player.getAppearence().generateAppearenceData();
			player.getPackets().sendSound(2240, 0, 1);
		}
	}

	public static boolean sendWear(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		String itemName = item.getDefinitions() == null ? "" : item.getDefinitions().getName().toLowerCase();
		if (item == null || item.getId() != itemId)
			return false;
		int targetSlot = Equipment.getItemSlot(itemId);
		// Just a placeholder for now
		if (itemName.contains("wings") && item.getId() >= 30000)
			targetSlot = Equipment.SLOT_CAPE;
		if (targetSlot == -1 || item.getDefinitions().isNoted() || targetSlot == 17) {
			// player.getPackets().sendGameMessage("You can't wear this item; if
			// it's a bug, report it on the forums.");
			return true;
		}
		if (!ItemConstants.canWear(item, player))
			return true;
		boolean isTwoHandedWeapon = targetSlot == 3 && Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots() && player.getEquipment().getWeaponId() != -1 && player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage("Not enough free space in your inventory.");
			return false;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions().getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments)
						player.sendMessage("You are not high enough level to use this item.");
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.sendMessage("You need to have a" + (name.startsWith("a") ? "n" : "") + " " + name + " level of " + level + ".");
				}
			}
		}
		if (!hasRequiriments)
			return true;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.stopAll(false, false);
		player.getInventory().deleteItem(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().addItem(player.getEquipment().getItem(5).getId(), player.getEquipment().getItem(5).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null && Equipment.isTwoHandedWeapon(player.getEquipment().getItem(3))) {
				if (!player.getInventory().addItem(player.getEquipment().getItem(3).getId(), player.getEquipment().getItem(3).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(3, null);
			}
		}
		if (player.getEquipment().getItem(targetSlot) != null && (itemId != player.getEquipment().getItem(targetSlot).getId() || !item.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory().getItems().set(slotId, new Item(player.getEquipment().getItem(targetSlot).getId(), player.getEquipment().getItem(targetSlot).getAmount()));
				player.getInventory().refresh(slotId);
			} else
				player.getInventory().addItem(new Item(player.getEquipment().getItem(targetSlot).getId(), player.getEquipment().getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if (targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null)
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot, targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		player.getAppearence().generateAppearenceData();
		player.getPackets().sendSound(2240, 0, 1);
		// if (!player.getPerkManager().chargeBefriender)
		player.getCharges().wear(targetSlot);
		if (player.getHitpoints() > (player.getMaxHitpoints() * 1.15)) {
			player.setHitpoints(player.getMaxHitpoints());
			player.refreshHitPoints();
		}
		if (targetSlot == Equipment.SLOT_WEAPON && itemId != 15486) {
			if (player.polDelay > Utils.currentTimeMillis()) {
				player.setPolDelay(0);
				player.sendMessage("The power of the light fades. Your resistance to melee attacks return to normal.");
			}
		}
		return true;
	}

	public static boolean sendWear2(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		if (item == null || item.getId() != itemId)
			return false;
		String itemName = item.getDefinitions() == null ? "" : item.getDefinitions().getName().toLowerCase();
		int targetSlot = Equipment.getItemSlot(itemId);
		if (itemName.contains("wings") && item.getId() >= 30000)
			targetSlot = Equipment.SLOT_CAPE;
		if (targetSlot == -1 || item.getDefinitions().isNoted() || targetSlot == 17) {
			// player.getPackets().sendGameMessage("You can't wear this item; if
			// it's a bug, report it on the forums.");
			return true;
		}
		if (!ItemConstants.canWear(item, player))
			return false;
		boolean isTwoHandedWeapon = targetSlot == 3 && Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots() && player.getEquipment().getWeaponId() != -1 && player.getEquipment().hasShield()) {
			player.sendMessage("Not enough free space in your inventory.");
			return false;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions().getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments)
						player.sendMessage("You are not high enough level to use this item.");
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.sendMessage("You need to have a" + (name.startsWith("a") ? "n" : "") + " " + name + " level of " + level + ".");
				}
			}
		}
		if (!hasRequiriments)
			return false;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.getInventory().getItems().remove(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().getItems().add(player.getEquipment().getItem(5))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null && Equipment.isTwoHandedWeapon(player.getEquipment().getItem(3))) {
				if (!player.getInventory().getItems().add(player.getEquipment().getItem(3))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null && (itemId != player.getEquipment().getItem(targetSlot).getId() || !item.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory().getItems().set(slotId, new Item(player.getEquipment().getItem(targetSlot).getId(), player.getEquipment().getItem(targetSlot).getAmount()));
			} else
				player.getInventory().getItems().add(new Item(player.getEquipment().getItem(targetSlot).getId(), player.getEquipment().getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);

		}
		if (targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null)
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot, targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		// if (!player.getPerkManager().chargeBefriender)
		player.getCharges().wear(targetSlot);
		if (player.getHitpoints() > (player.getMaxHitpoints() * 1.15)) {
			player.setHitpoints(player.getMaxHitpoints());
			player.refreshHitPoints();
		}
		if (targetSlot == Equipment.SLOT_WEAPON && itemId != 15486) {
			if (player.polDelay > Utils.currentTimeMillis()) {
				player.setPolDelay(0);
				player.sendMessage("The power of the light fades. Your resistance to melee attacks return to normal.");
			}
		}
		return true;
	}

	public static void submitSpecialRequest(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							player.getCombatDefinitions().switchUsingSpecialAttack();
						}
					}, 0);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 200);
	}

	public static void sendItemStats(Player player, Item item) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < 17; i++) {
			int bonus = ItemBonuses.getItemBonuses(item.getId())[i];
			String label = CombatDefinitions.BONUS_LABELS[i];
			String sign = bonus > 0 ? "+" : "";
			b.append(label + ": " + (sign + bonus) + ((label == "Magic Damage" || label == "Absorb Melee" || label == "Absorb Magic" || label == "Absorb Ranged") ? "%" : "") + "<br>");
		}
		player.getPackets().sendGlobalString(321, "Stats for " + item.getName());
		player.getPackets().sendGlobalString(324, b.toString());
		player.getPackets().sendHideIComponent(667, 49, false);
	}

	public static void openEquipmentBonuses(final Player player, boolean banking) {
		player.stopAll();
		player.getInterfaceManager().closeInterface(11, 0);
		player.getInterfaceManager().sendInventoryInterface(670);
		player.getInterfaceManager().sendInterface(667);
		player.getPackets().sendConfigByFile(8348, banking ? 0 : 1);
		player.getPackets().sendConfigByFile(4894, banking ? 1 : 0);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendInterSetItemsOptionsScript(670, 0, 93, 4, 7, "Equip", "Compare", "Stats", "Examine");
		player.getPackets().sendUnlockIComponentOptionSlots(670, 0, 0, 27, 0, 1, 2, 3);
		player.getPackets().sendUnlockIComponentOptionSlots(667, 9, 0, 24, 0, 8, 9);
		player.getPackets().sendIComponentSettings(667, 14, 0, 13, 1030);
		refreshEquipBonuses(player);
		player.getPackets().sendGlobalConfig(779, player.getEquipment().getWeaponRenderEmote());
		if (banking) {
			player.getTemporaryAttributtes().put("Banking", Boolean.TRUE);
			player.setCloseInterfacesEvent(new Runnable() {
				@Override
				public void run() {
					player.getTemporaryAttributtes().remove("Banking");
					player.getPackets().sendConfigByFile(8348, 0);
					// player.getPackets().sendRunScript(2319);
				}

			});
		}
	}

	public static void openItemsKeptOnDeath(Player player) {
		player.getInterfaceManager().sendInterface(17);
		sendItemsKeptOnDeath(player, false);
	}

	public static void sendItemsKeptOnDeath(Player player, boolean wilderness) {
		boolean skulled = player.hasSkull();
		Integer[][] slots = GraveStone.getItemSlotsKeptOnDeath(player, wilderness, skulled, player.getPrayer().isProtectingItem());
		Item[][] items = GraveStone.getItemsKeptOnDeath(player, slots);
		long riskedWealth = 0;
		long carriedWealth = 0;
		for (Item item : items[1])
			carriedWealth = riskedWealth += item.getDefinitions().getTipitPrice() * item.getAmount();
		for (Item item : items[0])
			carriedWealth += item.getDefinitions().getTipitPrice() * item.getAmount();
		if (slots[0].length > 0) {
			for (int i = 0; i < slots[0].length; i++)
				player.getVarsManager().sendVarBit(9222 + i, slots[0][i]);
			player.getVarsManager().sendVarBit(9227, slots[0].length);
		} else {
			player.getVarsManager().sendVarBit(9222, -1);
			player.getVarsManager().sendVarBit(9227, 1);
		}
		player.getVarsManager().sendVarBit(9226, wilderness ? 1 : 0);
		player.getVarsManager().sendVarBit(9229, skulled ? 1 : 0);
		StringBuffer text = new StringBuffer();
		text.append("The number of items kept on").append("<br>").append("death is normally 3.").append("<br>").append("<br>").append("<br>");
		if (wilderness) {
			text.append("Your gravestone will not").append("<br>").append("appear.");
		} else {
			int time = GraveStone.getMaximumTicks(player.getGraveStone());
			int seconds = (int) (time * 0.6);
			int minutes = seconds / 60;
			seconds -= minutes * 60;

			text.append("Gravestone:").append("<br>").append(ClientScriptMap.getMap(1099).getStringValue(player.getGraveStone())).append("<br>").append("<br>").append("Initial duration:").append("<br>").append(minutes + ":" + (seconds < 10 ? "0" : "") + seconds).append("<br>");
		}
		text.append("<br>").append("<br>").append("Carried wealth:").append("<br>").append(carriedWealth > Integer.MAX_VALUE ? "Too high!" : Utils.getFormattedNumber((int) carriedWealth)).append("<br>").append("<br>").append("Risked wealth:").append("<br>").append(riskedWealth > Integer.MAX_VALUE ? "Too high!" : Utils.getFormattedNumber((int) riskedWealth)).append("<br>").append("<br>");
		if (wilderness) {
			text.append("Your hub will be set to:").append("<br>").append("Edgeville.");
		} else {
			text.append("Current hub: " + ClientScriptMap.getMap(3792).getStringValue(DeathEvent.getCurrentHub(player)));
		}
		player.getPackets().sendGlobalString(352, text.toString());
	}

	public enum AntiDotes {
		ANTIDOTE_PLUSPLUS_FLASK_6(23591), ANTIDOTE_PLUSPLUS_FLASK_5(23593), ANTIDOTE_PLUSPLUS_FLASK_4(23595), ANTIDOTE_PLUSPLUS_FLASK_3(23597), ANTIDOTE_PLUSPLUS_FLASK_2(23599), ANTIDOTE_PLUSPLUS_FLASK_1(23601), ANTIDOTE_PLUSPLUS_4(5952), ANTIDOTE_PLUSPLUS_3(5954), ANTIDOTE_PLUSPLUS_2(5956), ANTIDOTE_PLUSPLUS_1(5958), ANTIDOTE_PLUS__FLASK_6(23579), ANTIDOTE_PLUS__FLASK_5(23581), ANTIDOTE_PLUS__FLASK_4(23583), ANTIDOTE_PLUS__FLASK_3(23585), ANTIDOTE_PLUS__FLASK_2(23587), ANTIDOTE_PLUS__FLASK_1(23589), ANTIDOTE_PLUS_4(5943), ANTIDOTE_PLUS_3(5945), ANTIDOTE_PLUS_2(5947), ANTIDOTE_PLUS_1(5949), SANFEW_SERUM_FLASK_6(23567), SANFEW_SERUM_FLASK_5(23569), SANFEW_SERUM_FLASK_4(23571), SANFEW_SERUM_FLASK_3(23573), SANFEW_SERUM_FLASK_2(23575), SANFEW_SERUM_FLASK_1(23577), SANFEW_SERUM_4(10925), SANFEW_SERUM_3(10927), SANFEW_SERUM_2(10929), SANFEW_SERUM_1(10931), SUPER_ANTIPOISON_FLASK_6(23327), SUPER_ANTIPOISON_FLASK_5(23329), SUPER_ANTIPOISON_FLASK_4(23331), SUPER_ANTIPOISON_FLASK_3(23333), SUPER_ANTIPOISON_FLASK_2(23335), SUPER_ANTIPOISON_FLASK_1(23337), SUPER_ANTIPOISON_4(2448), SUPER_ANTIPOISON_3(181), SUPER_ANTIPOISON_2(183), SUPER_ANTIPOISON_1(185), ANTI_POISON_FLASK_6(23315), ANTI_POISON_FLASK_5(23317), ANTI_POISON_FLASK_4(23319), ANTI_POISON_FLASK_3(23321), ANTI_POISON_FLASK_2(23323), ANTI_POISON_FLASK_1(23325), ANTI_POISON_4(2446), ANTI_POISON_3(175), ANTI_POISON_2(177), ANTI_POISON_1(179);

		private int id;

		private AntiDotes(int id) {
			this.id = id;
		}

		public int getItemId() {
			return id;
		}
	}

	public static void refreshEquipBonuses(Player player) {
		int strBonus = player.getCombatDefinitions().getBonuses()[14];
		if (strBonus >= 205)
			strBonus = 205;
		player.getPackets().sendIComponentText(667, 28, "Stab: +" + player.getCombatDefinitions().getBonuses()[0]);
		player.getPackets().sendIComponentText(667, 29, "Slash: +" + player.getCombatDefinitions().getBonuses()[1]);
		player.getPackets().sendIComponentText(667, 30, "Crush: +" + player.getCombatDefinitions().getBonuses()[2]);
		player.getPackets().sendIComponentText(667, 31, "Magic: +" + player.getCombatDefinitions().getBonuses()[3]);
		player.getPackets().sendIComponentText(667, 32, "Range: +" + player.getCombatDefinitions().getBonuses()[4]);
		player.getPackets().sendIComponentText(667, 33, "Stab: +" + player.getCombatDefinitions().getBonuses()[5]);
		player.getPackets().sendIComponentText(667, 34, "Slash: +" + player.getCombatDefinitions().getBonuses()[6]);
		player.getPackets().sendIComponentText(667, 35, "Crush: +" + player.getCombatDefinitions().getBonuses()[7]);
		player.getPackets().sendIComponentText(667, 36, "Magic: +" + player.getCombatDefinitions().getBonuses()[8]);
		player.getPackets().sendIComponentText(667, 37, "Range: +" + player.getCombatDefinitions().getBonuses()[9]);
		player.getPackets().sendIComponentText(667, 38, "Summoning: +" + player.getCombatDefinitions().getBonuses()[10]);
		player.getPackets().sendIComponentText(667, 39, "Absorb Melee: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 40, "Absorb Magic: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 41, "Absorb Ranged: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 42, "Strength: " + strBonus);
		player.getPackets().sendIComponentText(667, 43, "Ranged Str: " + player.getCombatDefinitions().getBonuses()[15]);
		player.getPackets().sendIComponentText(667, 44, "Prayer: +" + player.getCombatDefinitions().getBonuses()[16]);
		player.getPackets().sendIComponentText(667, 45, "Magic Damage: +" + player.getCombatDefinitions().getBonuses()[17] + "%");
	}

	public static void sendMissionBoard(Player player) {
		// TODO Auto-generated method stub

	}

}