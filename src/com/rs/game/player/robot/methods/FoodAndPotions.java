package com.rs.game.player.robot.methods;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.Foods.Food;
import com.rs.game.player.content.Pots;
import com.rs.game.player.robot.Robot;
import com.rs.utils.Utils;

/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public class FoodAndPotions extends Method {
	
	public FoodAndPotions(Robot robot) {
		super(robot);
		stage = Stage.Running;
	}
	
	int lastHP = 0;
	
	int lastWeaponId;
	
	@Override
	public void process() {
		if (lastHP <= scaleHP(300)) {
			lastHP = robot.getHitpoints();
		}
		boolean scared = false;
		if (robot.getHitpoints() < scaleHP(750)) {
			if (getScript().combat.target instanceof Player) {
				int weaponId = ((Player) getScript().combat.target).getEquipment().getWeaponId();
				if (weaponId != lastWeaponId) {
					scared = true;
				}
				lastWeaponId = weaponId;
			}
		}
		if (Utils.getRandom(lastHP) <= scaleHP(250) && lastHP <= scaleHP(600) && robot.getFoodDelay() <= Utils.currentTimeMillis()) {
			scared = true;
		}
		if (robot.getHitpoints() > scaleHP(700)) {
			scared = false;
		}
		Item[] items2 = robot.getInventory().getItems().getItemsCopy();
		if (scared) {
			int food = -1;
			for (int i = 0; i < items2.length; i++) {
				if (items2[i] == null)
					continue;//food
				if (Food.forId(items2[i].getId()) != null) {
					Foods.eat(robot, items2[i], i);
					food = items2[i].getId();
					robot.stopAll(false);
					break;
				}
			}
			if (Utils.getRandom(5) == 0) {
				int foodLeft = robot.getInventory().getItems().getNumberOf(food);
				if (foodLeft == 0) {
					getScript().combat.setStage(Stage.Running);
					//publicMessage(messages[3][Utils.getRandom(messages[3].length-1)]);
					//startObjective("getoutofwild");
				} else if (foodLeft <= 5) {
					//publicMessage(messages[1][Utils.getRandom(messages[1].length-1)]);
				}
			}
			if (Utils.getRandom(lastHP) <= scaleHP(150) && lastHP <= scaleHP(350)) {
				for (int i = 0; i < robot.getInventory().getItemsContainerSize(); i++) {
					if (items2[i] == null)
						continue;//saradomin brew for food
					if (items2[i].getId() >= 6685 && items2[i].getId() <= 6691) {
						Pots.pot(robot, items2[i], i);
						break;
					}
				}
			}
		}
		boolean potted = false;
		if (getScript().combat.stage == Stage.Fighting || getScript().combat.stage == Stage.Looking) {
			for (int skillId = 6; skillId >= 0; skillId--) {
				if (skillId == 3)
					continue;
				int level = robot.getSkills().getLevel(skillId);
				int normal = robot.getSkills().getLevelForXp(skillId);
				int min = (int)(normal * 1.05);
				if (skillId == 6) {
					min = (int)(normal * 0.95);
				}
				if (skillId == 5) {
					level = robot.getPrayer().getPrayerpoints() / 10;
					min /= 2;//50% of maximum
					//robot.setNextForceTalk(new ForceTalk("Prayer: "+level+" and "+min));
				}
				// SARADOMIN BREW
				boolean needsDefense = robot.getSkills().getLevel(1) > 1 && robot.getSkills().getLevel(1) <= (int)(robot.getSkills().getLevelForXp(1)*1.05);
				boolean drinkSecondBrew = skillId == 6 && level < min && Utils.getRandom(1) == 0
						&& robot.getHitpoints() <= (int)(robot.getSkills().getLevelForXp(3) * 0.70);
				if (needsDefense || drinkSecondBrew) {
					for (int i = 0; i < robot.getInventory().getItemsContainerSize(); i++) {
						if (items2[i] == null)
							continue;
						if (items2[i].getDefinitions().getName().toLowerCase().contains(potionNames[1])) {
							Pots.pot(robot, items2[i], i);
							potted = true;
							break;
						}
					}
				}
				//if (skillId == 5) {
					//min *= 5;//50% of maximum
				//	robot.setNextForceTalk(new ForceTalk("Prayer2: "+level+" and "+min));
				//}
				if (potted)
					break;
				//if (skillId == 5) {
					//min *= 5;//50% of maximum
				//	robot.setNextForceTalk(new ForceTalk("Prayer3: "+level+" and "+min));
				//}
				if (level <= min) {
					for (int i = 0; i < robot.getInventory().getItemsContainerSize(); i++) {
						if (items2[i] == null)
							continue;
						if ((skillId == 0 && items2[i].getDefinitions().getName().toLowerCase().contains("overload"))
								|| items2[i].getDefinitions().getName().toLowerCase().contains(potionNames[skillId])) {
							Pots.pot(robot, items2[i], i);
							potted = true;
							break;
						}
					}
				}
				if (potted)
					break;
			}
		}
		//prayer
		if (!potted && robot.getPrayer().getPrayerpoints() < scalePrayer(300)) {
			for (int i = 0; i < robot.getInventory().getItemsContainerSize(); i++) {
				if (items2[i] == null)
					continue;
				if (items2[i].getDefinitions().getName().toLowerCase().contains("restore")) {
					Pots.pot(robot, items2[i], i);
					potted = true;
					break;
				}
			}
		}
		if (potted)
			robot.stopAll(false);
		// attack
		if (Utils.getRandom(2) == 0)
			lastHP = robot.getHitpoints();
	}
	
	public int scalePrayer(int prayer) {
		int level = robot.getSkills().getLevelForXp(5);
		return prayer * (99 / level);
	}
	
	public int scaleHP(int hp) {
		int level = robot.getSkills().getLevelForXp(3);
		return hp * (99 / level);
	}
	
	public String[] potionNames = {"attack","saradomin","strength","","ranging","restore","restore"};
	
}
