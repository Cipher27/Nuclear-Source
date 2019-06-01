package com.rs.game.player.content;

import java.util.HashMap;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.minigames.WarriorsGuild;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class ItemConstants {
	
    public static boolean isDestroy(Item item) {
	return item.getDefinitions().isDestroyItem() || item.getDefinitions().isLended();
    }

	public static int getDegradeItemWhenWear(int id) {
		// pvp armors
		if (id == 13958 || id == 13961 || id == 13964 || id == 13967
				|| id == 13970 || id == 13973 || id == 13858 || id == 13861
				|| id == 13864 || id == 13867 || id == 13870 || id == 13873
				|| id == 13876 || id == 13884 || id == 13887 || id == 13890
				|| id == 13893 || id == 13896 || id == 13899 || id == 13902
				|| id == 13905 || id == 13908 || id == 13911 || id == 13914
				|| id == 13917 || id == 13920 || id == 13923 || id == 13926
				|| id == 13929 || id == 13932 || id == 13935 || id == 13938
				|| id == 13941 || id == 13944 || id == 13947 || id == 13950
				|| id == 13958)
			return id + 2; // if you wear it it becomes corrupted LOL
		return -1;
	}
	

	// return amt of charges
	public static int getItemDefaultCharges(int id) {
		// pvp armors
		if (id == 13910 || id == 13913 || id == 13916 || id == 13919
				|| id == 13922 || id == 13925 || id == 13928 || id == 13931
				|| id == 13934 || id == 13937 || id == 13940 || id == 13943
				|| id == 13946 || id == 13949 || id == 13952)
			return 1500;
		if (id == 13960 || id == 13963 || id == 13966 || id == 13969
				|| id == 13972 || id == 13975)
			return 3000;
		if (id == 13860 || id == 13863 || id == 13866 || id == 13869
				|| id == 13872 || id == 13875 || id == 13878 || id == 13886
				|| id == 13889 || id == 13892 || id == 13895 || id == 13898
				|| id == 13901 || id == 13904 || id == 13907 || id == 13960)
			return 6000; // 1hour
		// nex armors
		if (id == 20137 || id == 20141 || id == 20145 || id == 20149
				|| id == 20153 || id == 20157 || id == 20161 || id == 20165
				|| id == 20169 || id == 20173||
		//drygore
		//        id == 26581 || id == 26589 || id == 26597|| 
		//nox items
			//	id == 31727 || id == 31731 || id == 31735|| 
			//	id == 31727 || id == 31731 || id == 31735|| 
		//chaotic items
				id == 18349|| id == 18351|| id == 18353|| id == 18355|| id == 18357|| id == 18359)
			return 60000; //10 hour
		return -1;
	}

	// return what id it degrades to, -1 for disapear which is default so we
	// dont add -1
	public static int getItemDegrade(int id) {
		if (id == 11285) // DFS
			return 11283;
		// nex armors
		if (id == 20137 || id == 20141 || id == 20145 || id == 20149
				|| id == 20153 || id == 20157 || id == 20161 || id == 20165
				|| id == 20169 || id == 20173)
			return id + 1;
	//	if (id == 31727 || id == 31731 || id == 31735) //noxious weapons
		//	return id + 1;
		if (id == 18349|| id == 18351|| id == 18353|| id == 18355|| id == 18357|| id == 18359) //chaotic
			return id + 1;
		return -1;
	}

	public static int getDegradeItemWhenCombating(int id) {
		// nex armors
		if (id == 20135 || id == 20139 || id == 20143 || id == 20147
				|| id == 20151 || id == 20155 || id == 20159 || id == 20163
				|| id == 20167 || id == 20171)/// id == 31725|| id == 31729|| id == 31733|| id == 26579|| id == 26587|| id == 26595)
			return id + 2;
		/*if (id == 18349|| id == 18351|| id == 18353|| id == 18355|| id == 18357|| id == 18359)
			return;*/
			
		return -1;
	}

	public static boolean itemDegradesWhileHit(int id) {
		if (id == 2550)
			return true;
		return false;
	}

	public static boolean itemDegradesWhileWearing(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName()
				.toLowerCase();
		if (name.contains("c. dragon") || name.contains("corrupt dragon")
				|| name.contains("vesta's") || name.contains("statius'")
				|| name.contains("morrigan's") || name.contains("zuriel's"))
			return true;
		return false;
	}

	public static boolean itemDegradesWhileCombating(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName()
				.toLowerCase();
		// nex armors
		if (name.contains("chaotic")||name.contains("torva") || name.contains("pernix")
				|| name.contains("virtux") || name.contains("zaryte"))//|| name.contains("noxious")|| name.contains("Noxious")|| name.contains("chaotic")||name.contains("drygore"))
			return true;
		return false;
	}
	
	
	public static int getAssassinModels(int slot, boolean male) {
		switch (slot) {

			case Equipment.SLOT_HANDS:
				return male ? 71809 : 71864;
			case Equipment.SLOT_FEET:
				return male ? 71804 : 71862;
			case Equipment.SLOT_LEGS:
				return male ? 71821 : 71876;
			case Equipment.SLOT_CHEST:
				return male ? 71832 : 71893;
			case Equipment.SLOT_HAT:
				return male ? 71816 : 75135;
			case Equipment.SLOT_CAPE:
				return male ? 58 : 58;
		}
		return -1;
	}
	
	/**
	  * looks if it's a dungoneering item.
	  **/
	public static boolean isDungItem(int itemId) {
		if (itemId >= 15750 && itemId <= 18329)
			return true;
		return false;
	}
	public static boolean isGrimyHerb(int itemId) {
		switch(itemId){
			case 199:
			case 201:
			case 203:
			case 205:
			case 207:
			case 209:
			case 211:
			case 213:
			case 215:
			case 217:
			case 219:
			case 12174:
			case 14836:
             return true;			
		}
		return false;
	}

	public static boolean canWear(Item item, Player player) {
		if (player.getRights() == 2)
			return true;
		if (player.getRights() == 7)
			return true;
		else if (item.getId() == 6570) {
			if (!player.isCompletedFightCaves()) {
				player.getPackets()
						.sendGameMessage(
								"You must complete the fightcaves to wear this.");
				return false;
			}
		} else if (item.getId() == 8856) {
		    if (!WarriorsGuild.inCatapultArea(player)) {
			player.getPackets().sendGameMessage("You may not equip this shield outside of the catapult room in the Warrior's Guild.");
			return false;
		    }
		} else if (item.getId() == 14642 || item.getId() == 14645
				|| item.getId() == 15433 || item.getId() == 15435
				|| item.getId() == 14641 || item.getId() == 15432
				|| item.getId() == 15434) {
		
				player.getPackets()
						.sendGameMessage(
								"");
				return true;
		
		}
		/*
		* Master cape reqs
		*/
        else if (item.getId() == 19709) {
			if (player.getSkills().getXp(Skills.DUNGEONEERING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Dungeoneering experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31282) {
			if (player.getSkills().getXp(Skills.SLAYER) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Slayer experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31272) {
			if (player.getSkills().getXp(Skills.PRAYER) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Prayer experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31292) {
			if (player.getSkills().getXp(Skills.SUMMONING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Summoning experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31275) {
			if (player.getSkills().getXp(Skills.CONSTRUCTION) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Construction experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31283) {
			if (player.getSkills().getXp(Skills.HUNTER) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Hunter experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31277) {
			if (player.getSkills().getXp(Skills.AGILITY) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Agility experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31268) {
			if (player.getSkills().getXp(Skills.ATTACK) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Attack experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31288) {
			if (player.getSkills().getXp(Skills.COOKING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Cooking experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31270) {
			if (player.getSkills().getXp(Skills.DEFENCE) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Defence experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31291) {
			if (player.getSkills().getXp(Skills.FARMING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Farming experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31289) {
			if (player.getSkills().getXp(Skills.FIREMAKING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Firemaking experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31287) {
			if (player.getSkills().getXp(Skills.FISHING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Fishing experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31273) {
			if (player.getSkills().getXp(Skills.MAGIC) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Magic experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31281) {
			if (player.getSkills().getXp(Skills.FLETCHING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Fletching experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31276) {
			if (player.getSkills().getXp(Skills.HITPOINTS) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Constitution experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31278) {
			if (player.getSkills().getXp(Skills.HERBLORE) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Herblore experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31285) {
			if (player.getSkills().getXp(Skills.MINING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Mining experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31271) {
			if (player.getSkills().getXp(Skills.RANGE) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Ranged experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31274) {
			if (player.getSkills().getXp(Skills.RUNECRAFTING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Runecrafting experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31286) {
			if (player.getSkills().getXp(Skills.SMITHING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Smithing experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31269) {
			if (player.getSkills().getXp(Skills.STRENGTH) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Strength experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31279) {
			if (player.getSkills().getXp(Skills.THIEVING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Thieving experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31280) {
			if (player.getSkills().getXp(Skills.CRAFTING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Crafting experience to wear this cape.");
				return false;
			}
		} else if (item.getId() == 31290) {
			if (player.getSkills().getXp(Skills.WOODCUTTING) <= 104273167) {
				player.getPackets().sendGameMessage("You need 104,273,167 Woodcutting experience to wear this cape.");
				return false;
			}
		}
		return true;
	}

	public static boolean isTradeable(Item item) {
		if (item.getDefinitions().getName().toLowerCase()
				.contains("flaming skull"))
			return false;
		if (item.getDefinitions().getName().toLowerCase()
				.contains("extreme"))
			return false;
		if (item.getDefinitions().getName().toLowerCase()
				.contains("super") && item.getDefinitions().getName().toLowerCase()
				.contains("saradomin"))
			return false;
		if (item.getDefinitions().isDestroyItem()
				|| item.getDefinitions().isLended()
				|| ItemConstants.getItemDefaultCharges(item.getId()) != -1)
			return false;
		String name = ItemDefinitions.getItemDefinitions(item.getId())
				.getName().toLowerCase();
		if (name.contains("fire cape") || name.contains("corrupted ore")
				|| name.contains("tokhaar") || name.contains("defender")
				|| name.contains("lucky")|| name.contains("lamp")
				|| name.contains("spin ticket") || name.contains("ceremonial")
				|| name.contains("diamond jubilee") || name.contains("souvenir")
				|| name.contains("diamond sceptre") || name.contains("diamond crown") || name.contains("token"))
			return false;
		if (name.contains("aura") || name.contains("supreme"))
			return false;
		if(name.contains("charm"))
			return false;
		if(name.contains("teleport"))
			return false;
		if (name.contains("goliath") || name.contains("swift") || name.contains("spellcaster"))
			return true;
		if(name.contains("super antifire") || name.contains("overload"))
			return false;
		switch (item.getId()) {
		case 6570:
		case 15440:
		case 15439:
		case 14936:
		case 14937:
		case 14938:
		case 14939:
		case 11323:
		case 4251:
		case 11328:
		case 11329:
		case 11330:
		case 11331:
		case 11332:
		case 11333:
		case 773:
		case 744:
		case 455:
		case 3840:
		case 3841:
		case 3842:
		case 3843:
		case 3844:
		case 19612:
		case 19613:
		case 19614:
		case 19615:
		case 19616:
		case 19617:
		case 15304:
		case 15305:
		case 15306:
		case 15307:
		return false;
		default:
			return true;
		}
	}

		public static boolean turnCoins(Item item) {
		if (item.getDefinitions().getName().toLowerCase().contains("(deg)"))
			return true;
		if (item.getDefinitions().getName().toLowerCase().contains("strength cape"))
			return true;
		if (item.getDefinitions().getName().toLowerCase().contains("max cape"))
			return true;
		if (item.getDefinitions().getName().toLowerCase().contains("max hood"))
			return true;
		if (item.getDefinitions().getName().toLowerCase().contains("completionist cape"))
			return true;
		if (item.getDefinitions().getName().toLowerCase().contains("completionist hood"))
			return true;
		switch (item.getId()) {
		case 10887:
		case 7462:
		case 7461:
		case 18349:
		case 18351:
		case 18353:
		case 18355:
		case 18357:
		case 18359:
		case 18361:
		case 18363:
		case 18335:
		case 18334:
		case 18333:
		case 31205:
			return true;
		default:
			return false;
		}
	}

		public static boolean canWear(Player player, Item item, boolean keepSake) {

			if (!keepSake) {
				/*if (!(player.getControlerManager().getControler() instanceof StealingCreationController)) {
					if (item.getName().toLowerCase().contains("(class")) {
						player.sm("You aren't allowed to have that here!");
						player.getInventory().deleteItem(item);
						return false;
					}
				}*/
			}

			if (item.getId() == 288) {
				if (!keepSake)
					player.sm("The goblin mail is too small to try and put on...");
				return false;
			}

			if (item.getName().toLowerCase().contains("bonecrusher") || item.getName().toLowerCase().contains("herbicide")) {
				if (!keepSake)
					player.sm("You can't wear this item.");
				return false;
			}

			if (item.getName().toLowerCase().contains("vanguard") && !player.isDivineDonator()) {
				if (!keepSake)
					player.sm("You must be a Divine Donator+ in order to wear this item.");
				return false;
			}

			if (item.getName().toLowerCase().contains("warlord") && !player.isAngelicDonator()) {
				if (!keepSake)
					player.sm("You must be a Angelic Donator+ in order to wear this item.");
				return false;
			}
			if (item.getDefinitions().isNoted()) {
				if (!keepSake)
					player.sm("You can't wear that.");
				return false;
			}

			if (item.getName().toLowerCase().contains("vanguard") && !player.isDivineDonator()) {
				if (!keepSake)
					player.sm("You must be a Divine Donator+ in order to wear this item.");
				return false;
			}
			if (item.getId() == 773)
				if (player.getRights() < 2)
					return false;
			/*
			 * if ((item.getId() == 4565 || item.getId() == 4084) &&
			 * player.getRights() != 2) { player.sm(
			 * "You've to be a administrator to wear this item."); return false; }
			 */
			if (item.getId() == 9813 || item.getId() == 9814) {
				if (player.questPoints < 17) {
					if (!keepSake)
						player.sm("You must have a total of 17 quest points to wear this item.");
					return false;
				}
			}
			if (item.getId() >= 20769 && item.getId() <= 20772) {
				if (!player.hasRequirements()) {
					if (!keepSake)
						player.sm("You do not meet all the requirements, for a list of them use ::requirements.");
					return false;
				}
			}
			if (item.getId() == 20767 || item.getId() == 20768) {
				if (!(player.getSkills().getLevelForXp(Skills.ATTACK) >= 99 && player.getSkills().getLevelForXp(Skills.STRENGTH) >= 99 && player.getSkills().getLevelForXp(Skills.DEFENCE) >= 99 && player.getSkills().getLevelForXp(Skills.CONSTRUCTION) >= 99 && player.getSkills().getLevelForXp(Skills.HITPOINTS) >= 99 && player.getSkills().getLevelForXp(Skills.RANGE) >= 99 && player.getSkills().getLevelForXp(Skills.MAGIC) >= 99 && player.getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99 && player.getSkills().getLevelForXp(Skills.FISHING) >= 99 && player.getSkills().getLevelForXp(Skills.AGILITY) >= 99 && player.getSkills().getLevelForXp(Skills.COOKING) >= 99 && player.getSkills().getLevelForXp(Skills.PRAYER) >= 99 && player.getSkills().getLevelForXp(Skills.THIEVING) >= 99 && player.getSkills().getLevelForXp(Skills.DUNGEONEERING) >= 99 && player.getSkills().getLevelForXp(Skills.MINING) >= 99 && player.getSkills().getLevelForXp(Skills.SMITHING) >= 99 && player.getSkills().getLevelForXp(Skills.SUMMONING) >= 99 && player.getSkills().getLevelForXp(Skills.FARMING) >= 99 && player.getSkills().getLevelForXp(Skills.HUNTER) >= 99 && player.getSkills().getLevelForXp(Skills.SLAYER) >= 99 && player.getSkills().getLevelForXp(Skills.CRAFTING) >= 99 && player.getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99 && player.getSkills().getLevelForXp(Skills.FIREMAKING) >= 99 && player.getSkills().getLevelForXp(Skills.FLETCHING) >= 99 && player.getSkills().getLevelForXp(Skills.HERBLORE) >= 99)){
						player.sm("You must have achieved the highest level in every skill to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20754) {
				if (player.getSkills().getTotalLevel() < 260) {
					if (!keepSake)
						player.sm("You must have a total level of 260 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20755) {
				if (player.getSkills().getTotalLevel() < 520) {
					if (!keepSake)
						player.sm("You must have a total level of 520 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20756) {
				if (player.getSkills().getTotalLevel() < 780) {
					if (!keepSake)
						player.sm("You must have a total level of 780 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20757) {
				if (player.getSkills().getTotalLevel() < 1040) {
					if (!keepSake)
						player.sm("You must have a total level of 1040 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20758) {
				if (player.getSkills().getTotalLevel() < 1300) {
					if (!keepSake)
						player.sm("You must have a total level of 1300 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20759) {
				if (player.getSkills().getTotalLevel() < 1560) {
					if (!keepSake)
						player.sm("You must have a total level of 1560 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20760) {
				if (player.getSkills().getTotalLevel() < 1820) {
					if (!keepSake)
						player.sm("You must have a total level of 1820 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20761) {
				if (player.getSkills().getTotalLevel() < 2080) {
					if (!keepSake)
						player.sm("You must have a total level of 2080 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 20762) {
				if (player.getSkills().getTotalLevel() < 2340) {
					if (!keepSake)
						player.sm("You must have a total level of 2340 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 22348) {
				if (player.getSkills().getLevelForXp(Skills.RANGE) < 60) {
					if (!keepSake)
						player.sm("You must have a ranged level of 60 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 22347) {
				if (player.getSkills().getLevelForXp(Skills.MAGIC) < 60) {
					if (!keepSake)
						player.sm("You must have a magic level of 60 to wear this item.");
					return false;
				}
			}
			if (item.getId() == 22346) {
				if (player.getSkills().getLevelForXp(Skills.ATTACK) < 60) {
					if (!keepSake)
						player.sm("You must have a attack level of 60 to wear this item.");
					return false;
				}
			}
			String itemName = item.getDefinitions() == null ? "" : item.getDefinitions().getName().toLowerCase();
			for (String strings : Settings.DONATOR_ITEMS) {
				if (itemName.contains(strings) && !player.isDonator()) {
					if (!keepSake)
						player.sm("You need to be a donator to equip " + itemName + ".");
					return false;
				}
			}
			for (String strings : Settings.EXTREME_DONATOR_ITEMS) {
				if (itemName.contains(strings) && !player.isExtremeDonator()) {
					if (!keepSake)
						player.sm("You need to be a extreme donator to equip " + itemName + ".");
					return false;
				}
			}
			int targetSlot = Equipment.getItemSlot(item.getId());
			if (!keepSake) {
				/*if (MonkeyGreeGrees.transform(player, item.getId()))
					targetSlot = Equipment.SLOT_WEAPON;
				if (item.getId() == 19866 || item.getId() == 19868 || item.getId() == 19889)
					targetSlot = Equipment.SLOT_SHIELD;
				// new?
				if (item.getId() == 4657 && targetSlot == Equipment.SLOT_RING) {
					player.setNextBodyGlow(new BodyGlow(2000000, 0, 0, 0, 128));
				} else if (targetSlot == Equipment.SLOT_RING && player.getEquipment().getRingId() == 4657) {
					player.setNextBodyGlow(new BodyGlow(1, 0, 0, 0, 128));
				}*/
			}

			if (targetSlot == -1) {
				if (!keepSake)
					player.sm("You can't wear that.");
				return false;
			}

			if (!ItemConstants.canWear(item, player))
				return false;

			if (!keepSake) {
				boolean isTwoHandedWeapon = targetSlot == 3 && Equipment.isTwoHandedWeapon(item);

				if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots() && player.getEquipment().hasShield()) {
					player.sm("Not enough free space in your inventory.");
					return false;
				}
			}

		
			HashMap<Integer, Integer> requiriments = item.getDefinitions().getWearingSkillRequiriments();
			boolean hasRequiriments = true;

		
			if (item.getId() == 29929) {
				// no requirements
			} else if (item.getId() >= 14936 && item.getId() <= 14939) {// agile
				// no requirements
			} else if (item.getId() == 29925) {// diamond pickaxe
				// no requirements
			} else if (item.getId() == 10551) {
				if (player.getSkills().getLevelForXp(Skills.DEFENCE) < 40) {
					if (!keepSake)
						player.sendMessage("You need atleast 40 defence to wear this item.");
					return false;
				}
			} else {
				if (requiriments != null) {
					for (int skillId : requiriments.keySet()) {
						if (skillId > 24 || skillId < 0)
							continue;
						int level = requiriments.get(skillId);
						if (level < 0 || level > 120)
							continue;
						if (player.getSkills().getLevelForXp(skillId) < level) {
							if (hasRequiriments) {
								if (!keepSake)
									player.sm("You are not high enough level to use this item.");
							}
							hasRequiriments = false;
							String name = Skills.SKILL_NAME[skillId].toLowerCase();
							if (!keepSake)
								player.sm("You need to have a" + (name.startsWith("a") ? "n" : "") + " " + name + " level of " + level + ".");
						}

					}
				}
				if (!hasRequiriments)
					return false;
			}
			return player.getControlerManager().canEquip(targetSlot, item.getId());
		}

}
