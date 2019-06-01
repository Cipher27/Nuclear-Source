package com.rs.game.player.robot;

import com.rs.game.player.Skills;
import com.rs.game.player.robot.methods.Banking;
import com.rs.game.player.robot.methods.Combat;
import com.rs.game.player.robot.methods.Equipping;
import com.rs.game.player.robot.methods.FoodAndPotions;
import com.rs.game.player.robot.methods.Pickup;
import com.rs.game.player.robot.methods.Prayer;
import com.rs.utils.Utils;


/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */

public class NewRobotScript {
	
	public enum SCRIPTS {
		WILDERNESS_BOT
	}
	
	public Robot robot;
	
	public int paused;
	
	public Equipping equipping;
	public Banking banking;
	public Combat combat;
	public Pickup pickup;
	public FoodAndPotions foodsAndPotions;
	public Prayer prayer;
	
	public NewRobotScript(Robot robot) {
		this.robot = robot;
		equipping = new Equipping(robot);
		banking = new Banking(robot);
		combat = new Combat(robot);
		pickup = new Pickup(robot);
		foodsAndPotions = new FoodAndPotions(robot);
		prayer = new Prayer(robot);
		setLevels();
	}
	
	public void process() {
		//debug();
		if (robot.isLocked() || robot.isDead())
			return;
		equipping.process();
		//debug();
		if (paused-- > 0) {
			return;
		}
		banking.process();
		//debug();
		if (paused > 0)
			return;
		combat.process();
		if (paused > 0)
			return;
		pickup.process();
		if (paused > 0)
			return;
		foodsAndPotions.process();
		if (paused > 0)
			return;
		prayer.process();
	}
	
	public int debugStage = 0;
	
	public void debug() {
		switch(debugStage++) {
			case 0:
			case 1:
				robot.ft("equipping stage: "+equipping.getStage().toString()+", "+paused);
				break;
			case 2:
			case 3:
				robot.ft("banking stage: "+banking.getStage().toString()+", "+paused);
				break;
			case 4:
			case 5:
				robot.ft("combat stage: "+combat.getStage().toString()+", "+paused);
				break;
			case 6:
			case 7:
				robot.ft("pickup stage: "+pickup.getStage().toString()+", "+paused);
				break;
			case 8:
			case 9:
				robot.ft("foodsAndPotions stage: "+foodsAndPotions.getStage().toString()+", "+paused);
				break;
			case 10:
			case 11:
				robot.ft("prayer stage: "+prayer.getStage().toString()+", "+paused);
				if (debugStage == 11) {
					debugStage = 0;
				}
				break;
		}
	}
	
	public void pause(int pause) {
		this.paused += pause;
	}
	
	public void setLevels() {
		switch(Utils.getRandom(9)) {
			case 0://MASTER
			case 1://MASTER
				robot.getPrayer().setPrayerBook(true);
				break;
			case 2://MASTER
			case 3://MASTER
				robot.getPrayer().setPrayerBook(true);
				for(int skill = 0; skill < 7; skill++)
					if (skill == 3)
						robot.getSkills().setXp(skill, Skills.getXPForLevel(99));
					else if (skill == 5)
						robot.getSkills().setXp(skill, Skills.getXPForLevel(95));
					else
						robot.getSkills().setXp(skill, Skills.getXPForLevel(90+Utils.getRandom(9)));
					robot.getSkills().setXp(23, Skills.getXPForLevel(Utils.getRandom(99)));
				break;
			case 4://ZERKER 1
				robot.getPrayer().setPrayerBook(true);
				robot.getSkills().setXp(Skills.DEFENCE, Skills.getXPForLevel(45));
				break;
			case 5://ZERKER 2
				robot.getPrayer().setPrayerBook(true);
				robot.getSkills().setXp(Skills.ATTACK, Skills.getXPForLevel(70+Utils.getRandom(10)));
				robot.getSkills().setXp(Skills.DEFENCE, Skills.getXPForLevel(45));
				break;
			case 6://PURE 1
				robot.setGameMode(Utils.getRandom(2));
				robot.getPrayer().setPrayerBook(false);
				robot.getSkills().setXp(Skills.PRAYER, Skills.getXPForLevel(52));
				robot.getSkills().setXp(Skills.ATTACK, Skills.getXPForLevel(70+Utils.getRandom(10)));
				robot.getSkills().setXp(Skills.DEFENCE, Skills.getXPForLevel(1));
				robot.getSkills().setXp(23, Skills.getXPForLevel(Utils.getRandom(99)));
				break;
			case 7://PURE 2
				robot.setGameMode(Utils.getRandom(2));
				robot.getPrayer().setPrayerBook(false);
				robot.getSkills().setXp(Skills.PRAYER, Skills.getXPForLevel(52));
				robot.getSkills().setXp(Skills.ATTACK, Skills.getXPForLevel(1));
				robot.getSkills().setXp(Skills.DEFENCE, Skills.getXPForLevel(1));
				robot.getSkills().setXp(Skills.RANGE, Skills.getXPForLevel(75));
				robot.getSkills().setXp(Skills.MAGIC, Skills.getXPForLevel(75));
				robot.getSkills().setXp(23, Skills.getXPForLevel(Utils.getRandom(99)));
				break;
			case 8://NOOB
				robot.setGameMode(Utils.getRandom(3));
				robot.getPrayer().setPrayerBook(false);
				for(int skill = 0; skill < 25; skill++) {
					robot.getSkills().setXp(skill, Skills.getXPForLevel(41+Utils.getRandom(30)));
				}
				break;
			case 9://NOOB 2
				robot.setGameMode(Utils.getRandom(3));
				robot.getPrayer().setPrayerBook(false);
				for(int skill = 0; skill < 25; skill++) {
					robot.getSkills().setXp(skill, Skills.getXPForLevel(Utils.getRandom(30)));
				}
				break;
		}
	}
}
