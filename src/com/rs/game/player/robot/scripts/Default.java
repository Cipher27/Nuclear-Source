package com.rs.game.player.robot.scripts;

import java.util.List;

import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.Foods.Food;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Pots;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.robot.RobotScript;
import com.rs.game.route.pathfinder.PathFinder;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Misc;
import com.rs.utils.Utils;



/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public abstract class Default extends RobotScript {
	
	public boolean foundTarget;
	
	public abstract void processCombat();
	
	public abstract void processMovement();
	
	@Override
	public abstract int[][] getSetId();
	
	@Override
	public WorldTile[] getHomes() {
		return new WorldTile[] {
				new WorldTile(3081, 3475, 0),
				new WorldTile(3224, 3218, 0)
		};
	}

	@Override
	public void bank() {
	//	if (robot.getControlerManager().getControler() instanceof ArenaControler)
	//		return;
		if (objective.equals("")) {
			if ((robot.getInventory().getItems().getNumberOf(15272) <= 4 && robot.getInventory().getItems().getNumberOf(385) <= 4)
					|| needsToBank) {
				startObjective("banking");
			}
		} else if (objective.equals("banking") && !needsToBank) {
			if (robot.getInventory().getItems().getNumberOf(15272) > 4 || robot.getInventory().getItems().getNumberOf(385) > 4) {
				finishObjective();
			} else if (robot.getTeleBlockDelay() > Utils.currentTimeMillis()) {
				startObjective("getoutofwild");
			}
		}
	}
	
	@Override
	public void transportation() {
	//	if (robot.getControlerManager().getControler() instanceof ArenaControler)
		//	return;
		if (fixedPathIndex != -1 && !robot.hasWalkSteps()) {
				wait(5);
				walkFixedPath();
				finishObjective();
		} else if (objective.equals("getoutofwild")) {
			wait(5);
			if (!Magic.useTeleTab(robot, getHome())) {
				if (robot.getFreezeDelay() < Utils.currentTimeMillis()) {
					robot.addWalkSteps(robot.getX(), robot.getY()-10);
					if (!robot.hasWalkSteps())
						robot.addWalkSteps(robot.getX()-Misc.getRandom(5)+Misc.getRandom(5), robot.getY());
				}
			} else {
				startObjective("banking");
			}
		} else if (objective.equals("banking")) {
			wait(5);
			walkToObjective(BANK);
		} else if (objective.equals("bank")) {
			wait(5);
			WorldTile bank = findNearObject("bank");
			if (bank == null)
				bank = findNearObject("counter");
			if (bank == null)
				bank = findNearNPC("banker");
			if (bank == null) {
				//Magic.sendNormalTeleportSpell(robot, 1, 0,
				//		getHome());
				homeTeleport();
			} else
				useBank(bank);
		} else if (objective.equals("praying")) {
			wait(5);
			walkToObjective(PRAYER);
		} else if (objective.equals("pray")) {
			useAlter();
		} else if (objective.equals("max")) {
			//Magic.sendNormalTeleportSpell(robot, 1, 0,
			//		getHome());
			homeTeleport();
			startObjective("edgeville");
		} else if (objective.equals("edgeville")) {
			if (getPathIndex() != -1)
				fixedPathIndex = getPathIndex();
			else if (findIndex() != -1 && fixedPathIndex == -1)
				useMax();
		}
		if (objective.equals("")) {
			if (getPathIndex() != -1)
				fixedPathIndex = getPathIndex();
			else if (findIndex() != -1 && fixedPathIndex == -1)
				startObjective("max");
		}
	}

	@Override
	public void combat() {
	/*	if (robot.getControlerManager().getControler() instanceof ArenaControler) {
				if ((!(robot.getActionManager().getAction() instanceof PlayerCombat)) &&
						getAttackedBy() != null) {
					Player attackedBy = (Player) getAttackedBy();
					/*if (!robot.checkWalkStep(attackedBy.getX() > 0 ? 1 : (attackedBy.getX() == 0 ? 0 : -1), attackedBy.getY() > 0 ? 1 : (attackedBy.getY() == 0 ? 0 : -1), robot.getX(), robot.getY(), true)) {
						if (robot.getFreezeDelay() < Utils.currentTimeMillis()) {
							if (Utils.getRandom(2) == 0)
								publicMessage(messages[5][Utils.getRandom(messages[3].length-1)]);
							if (!robot.addWalkSteps(Utils.getRandom(3)+Utils.getRandom(3), Utils.getRandom(3)+Utils.getRandom(3)))
								startObjective("getoutofwild");
						} else {
							if (Utils.getRandom(2) == 0)
								publicMessage(messages[6][Utils.getRandom(messages[6].length-1)]);
							startObjective("getoutofwild");
						}
						return;
						//robot.addWalkSteps(Utils.getRandom(3)+Utils.getRandom(3), Utils.getRandom(3)+Utils.getRandom(3));
					}
					//if (World.checkWalkStep(0, attackedBy.getX(), attackedBy.getY(), Utils.getFaceDirection(robot.getX()-attackedBy.getX(), robot.getY()-attackedBy.getY()), 1))
					robot.getActionManager().setAction(new PlayerCombat(attackedBy));
				}
				processCombat();
		} else {*/
			if (robot.getControlerManager().getControler()
					instanceof Wilderness && !Wilderness.isAtWild(robot)) {
		    		robot.getControlerManager().removeControlerWithoutCheck();
			}
			if (objective.equals("") || robot.getControlerManager().getControler() == null) {
				if (Wilderness.isAtWild(robot)) {
			    	if (robot.getControlerManager().getControler() == null) {
			    		robot.getControlerManager().startControler("Wilderness");
			    	}
					startObjective("attack");
				}
			}
			if (objective.equals("attack")) {
				if (robot.getAttackedByDelay() + 10000 < Utils.currentTimeMillis() &&
						getAttackedBy() == null) {
				if (Utils.getRandom(3) == 0 && robot.getControlerManager().getControler() instanceof Wilderness) {
					Player player = findAttackablePlayer();
					if (player != null) {
						player.stopAll(false);
						setAttackedBy(player);
						robot.getActionManager().setAction(new PlayerCombat(player));
						startObjective("combat");
					}
				} else if (!robot.hasWalkSteps()) {
					wait(5);
					int x = Utils.getRandom(15)-Utils.getRandom(15);
					int y = Utils.getRandom(15)-Utils.getRandom(2);
					if (Wilderness.getWildLevel(robot) > 10)
						y = Utils.getRandom(2)-Utils.getRandom(8);
					if (robot.getX() < 3028)
						x = Utils.getRandom(2)-Utils.getRandom(8);
					else if (robot.getX() > 3120)
						x = Utils.getRandom(2)-Utils.getRandom(8);
					robot.addWalkSteps(robot.getX()+x, 
							robot.getY()+y);
				}
				} else {//is under attack
					startObjective("combat");
				}
			} else if (objective.equals("combat")) {//
				Entity entity = getAttackedBy();
				if ((!(robot.getActionManager().getAction() instanceof PlayerCombat)) &&
						entity != null) {
					robot.getActionManager()
					.setAction(new PlayerCombat(entity));
				} else if (entity == null) {
					if (robot.getInventory().getFreeSlots() > 5)
						startObjective("getoutofwild");
					else
						startObjective("attack");
					return;
				}
				boolean frozen = robot.getFreezeDelay() > System.currentTimeMillis();
				if (!frozen && 
						entity.getAttackedByDelay() + 4000 > Utils.currentTimeMillis() && 
						!entity.checkWalkStep(robot.getY(), robot.getY(), entity.getX(), entity.getY(), true))//opponent is hiding
					PathFinder.simpleWalkTo(robot, entity);
				if ((getAttackedBy() instanceof Player && ((Player) getAttackedBy()).getCombatDefinitions().isUsingSpecialAttack() || robot.getHitpoints() <= 400) && 
						!fightLastedFor(10))// && getAttackedBy() != null && getAttackedBy() instanceof Player && ((Player) getAttackedBy()).getCombatDefinitions().isUsingSpecialAttack())
					robot.getPrayer().switchPrayer(9);
				processCombat();
			}
	//	}
	}
	
	@Override
	public void buttons() {
		if (wearingSpecialWeapon()) {
			if (!robot.getCombatDefinitions().isUsingSpecialAttack()) {
				robot.getCombatDefinitions().switchUsingSpecialAttack();
			}
		} else {
			if (robot.getCombatDefinitions().isUsingSpecialAttack()) {
				robot.getCombatDefinitions().switchUsingSpecialAttack();
			}
		}
	}
	
	public String[] potionNames = {"attack","saradomin","strength","","ranging","restore","restore"};
	
	//public int[][] potionStat = {{99,114},{99,114},{99,114},{-1,-1},{99,108},{0,500},{50,98}};
	
	public int lastHP;
	
	@Override
	public void inventory() {
		if (lastHP <= 100) {
			lastHP = robot.getHitpoints();
		}
		if (objective.equals("combat") || objective.equals("attack") /*|| robot.getControlerManager().getControler() instanceof ArenaControler*/) {
			Item[] items2 = robot.getInventory().getItems().getItemsCopy();
			if (Utils.getRandom(lastHP) <= 200 && lastHP <= 600 && robot.getFoodDelay() <= Utils.currentTimeMillis()) {
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
					publicMessage(messages[3][Utils.getRandom(messages[3].length-1)]);
					//if (!(robot.getControlerManager().getControler() instanceof ArenaControler))
						startObjective("getoutofwild");
				} else if (foodLeft <= 5) {
					publicMessage(messages[1][Utils.getRandom(messages[1].length-1)]);
					}
				}
				if (Utils.getRandom(lastHP) <= 200 && lastHP <= 450) {
					for (int i = items2.length - 1; i >= 16; i--) {
						if (items2[i] == null)
							continue;//saradomin brew
						if (items2[i].getId() >= 6685 && items2[i].getId() <= 6691) {
							Pots.pot(robot, items2[i], i);
						break;
						}
					}
				}
			}
			boolean potted = false;
			/*for (int i = items2.length - 1; i >= 0; i--) {
				if (items2[i] == null)
					continue;
				for (int skillId = 0; skillId < 3; skillId++) {
					int normal = robot.getSkills().getLevelForXp(skillId);
					int level = robot.getSkills().getLevel(skillId);
					if ((level < normal) || (skillId < 3 && level < 115)) {
						if (items2[i].getDefinitions().getName().toLowerCase().contains(potionNames[skillId])) {
							Pots.pot(robot, items2[i], i);
							potted = true;
							break;
						}
					}
				}
			}*/
			//if (robot.getPotDelay() <= Utils.currentTimeMillis()) {
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
						level = robot.getPrayer().getPrayerpoints();
						min *= 5;//50% of maximum
					}
					
					//System.out.println(skillId+", "+min+", "+level);
					
					//--- brew ---
					if (robot.getSkills().getLevel(1) <= (int)(robot.getSkills().getLevelForXp(1)*1.05) ||
							(skillId == 6 && level <= min && Utils.getRandom(1) == 0 && robot.getHitpoints() <= (int)(robot.getSkills().getLevelForXp(3)*0.90))) {
						for (int i = items2.length - 1; i >= 16; i--) {
							if (items2[i] == null)
								continue;
							if (items2[i].getDefinitions().getName().toLowerCase().contains(potionNames[1])) {
								Pots.pot(robot, items2[i], i);
								potted = true;
							break;
							}
						}
					}
					if (potted)
						break;
					//int max = (int)(robot.getSkills().getLevel(skillId) * 1.1);
					//if (skillId <= 2) {
					//	min = robot.getSkills().getLevelForXp(skillId);
						//max = (int) (level + (level * 0.15));
					//}
					if (level <= min) {
					//if (level >= min && level <= max) {
						//if (!potionNames[skillId].equals("")) {
							for (int i = items2.length - 1; i >= 16; i--) {
								if (items2[i] == null)
									continue;
								if (items2[i].getDefinitions().getName().toLowerCase().contains(potionNames[skillId])) {
									Pots.pot(robot, items2[i], i);
									potted = true;
								break;
								}
							}
						//}
					}
					if (potted)
						break;
				}
			//}
			if (potted)
				robot.stopAll(false);
			
			/*WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					Entity entity = getAttackedBy();
					if (entity != null) {
						robot.getActionManager()
						.setSkill(new PlayerCombat(entity));
				}
				}
			}, 3);*/
		}
		if (Utils.getRandom(3) == 0)
			lastHP = robot.getHitpoints();
	}
	
	
	public final static int[] MAGIC_PRAYERS = new int[] {
		29, 21, 12, 4
	};
	
	public final static int[] RANGE_PRAYERS = new int[] {
		28, 20, 11, 3
	};
	
	public final static int[] MELEE_PRAYERS = new int[] {
		27, 25
	};
	
	public final static int[] DEFENCE_PRAYERS = new int[] {
		8, 5, 1
	};
	
	public final static int[] STRENGTH_PRAYERS = new int[] {
		9, 6, 2
	};
	
	public final static int[] ATTACK_PRAYERS = new int[] {
		10, 7, 3
	};

	@Override
	public void prayer() {
		if (robot.getPrayer().outOfPrayer())
			return;
		/*if (robot.getControlerManager().getControler() instanceof ArenaControler) {
			boolean usingCurses = robot.getPrayer().isAncientCurses();
			boolean ranging = PlayerCombat.isRanging(robot) != 0;
			if (usingCurses) {
				//if (!robot.getPrayer().usingPrayer(18)) // soul split
				//	robot.getPrayer().switchPrayer(18);
				if (this instanceof Melee || lastType.toLowerCase().contains("melee") || 
						(lastType.toLowerCase().equalsIgnoreCase("special") && !ranging)) {
					if (!robot.getPrayer().usingPrayer(5)) // turmoil
						robot.getPrayer().switchPrayer(5);
					if (!robot.getPrayer().usingPrayer(19)) // turmoil
						robot.getPrayer().switchPrayer(19);
				} else {
					if (!robot.getPrayer().usingPrayer(11)) // leech
						robot.getPrayer().switchPrayer(11);
					if (!robot.getPrayer().usingPrayer(12)) // leech
						robot.getPrayer().switchPrayer(12);
					if (!robot.getPrayer().usingPrayer(13)) // leech
						robot.getPrayer().switchPrayer(13);
				}
			} else {
				if (this instanceof Melee || lastType.toLowerCase().contains("melee") || 
						(lastType.toLowerCase().equalsIgnoreCase("special") && !ranging)) {
					if (robot.getSkills().getLevel(Skills.DEFENCE) >= 70) {
						prayerLoop(MELEE_PRAYERS);
					} else {
						prayerLoop(DEFENCE_PRAYERS);
						prayerLoop(STRENGTH_PRAYERS);
						prayerLoop(ATTACK_PRAYERS);
					}
				} else if (this instanceof Range || lastType.equalsIgnoreCase("range") ||
						(lastType.toLowerCase().equalsIgnoreCase("special") && ranging)) {
					prayerLoop(RANGE_PRAYERS);
				} else if (this instanceof RegularMage || lastType.equalsIgnoreCase("barrage")) {
					prayerLoop(MAGIC_PRAYERS);
				}
			}
			//if (!robot.getPrayer().hasPrayersOn())
			//	robot.getPrayer().switchQuickPrayers();
			return;
		} */
		if (objective.equals("")) {
			if (robot.getPrayer().getPrayerpoints() == 0) {
				if (findIndex() != -1 && fixedPathIndex == -1)
					startObjective("praying");
			}
		}
		if ((objective.equals("combat") && getAttackedBy() != null) || objective.equals("getoutofwild") || needsToPray) {
			if (!robot.getPrayer().hasPrayersOn())
				robot.getPrayer().switchQuickPrayers();
		} else {
			if (robot.getPrayer().hasPrayersOn())
				robot.getPrayer().closeAllPrayers();
		}
		if (robot.getControlerManager().getControler() instanceof Wilderness && robot.getPrayer().getPrayerpoints() == 0)
			startObjective("getoutofwild");
	}
	
	public void prayerLoop(int[] prayerIds) {
		boolean usingPrayer = false;
		for(int prayerId : prayerIds) {
			if (robot.getPrayer().usingPrayer(prayerId))
				usingPrayer = true;
		}
		if (!usingPrayer) {
			for(int prayerId : prayerIds) {
				if (robot.getPrayer().canUsePrayer(prayerId))
					robot.getPrayer().switchPrayer(prayerId);
				break;
			}
		}
	}

	@Override
	public void summoning() {
		
	}
	
	int checkPickup = 0;

	@Override
	public void pickup() {
		if (objective.equals("attack") && getAttackedBy() == null) {
			if (checkPickup++ > 5)
				checkPickup = 0;
			else
				return;
			List<FloorItem> items = World.getRegion(robot.getRegionId()).getGroundItems();
			if (items == null)
				return;
			for (final FloorItem item : items) {
				if (item == null)
					return;
				if (!item.isInvisible()) {
					if ((item.getDefinitions().getValue() * item.getAmount()) > 100000) {
						if (robot.getInventory().getFreeSlots() == 0) {
							boolean hasFreeSpace = false;
							for (int slot = 0; slot < robot.getInventory().getItems().getItems().length; slot++) {
								Item toDrop = robot.getInventory().getItem(slot);
								if (Food.forId(toDrop.getId()) != null) {
									World.addGroundItem(toDrop, new WorldTile(robot), robot, false, 180, true);
									robot.getInventory().deleteItem(slot, toDrop);
									hasFreeSpace = true;
									break;
								}
							}
							if (!hasFreeSpace)
								return;
						}
						objective = "pickup";
						robot.addWalkSteps(item.getTile());
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								World.removeGroundItem(robot, item);
								objective = "attack";
								checkPickup = 0;
							}
						}, 2);
						break;
					}
				}
			}
		}
	}
	
	public String[][] messages = {
			{"Safer", "Wow, quit safing", "Stop safing", "Stop eating", "Safer!", "Safe"},
			{"I'm almost out", "Food left?", "Almost out", "Dm?", "Dm", "3 food left", "Dm me?"},
			{"You asked for it", "Let's hope you're ready for this", "You're going down", "Good luck", "Gl", "Ask?", "Ask next time.", "Good luck, I guess", "Wow alright", "Stranger danger!", "Sure, I'll fight", "Let's fight"},
			{"I'm out", "Out", "Out, gf", "No food left"},
			{"Sorry", "I'll get off", "Oops", "Misclick", "If you insist", "Fine", "Wow", "Fight?"},
			{"Stop running", "Get over here", "Come over here", "Quit running", "Are you hiding?", "Stop hiding", "You can't run from me", "You'll never get away"},
			{"Farcaster", "You farcasted", "Goodbye", "Bye bye"},
			{"I don't know", "yeah right", "now you're asking for it", "don't say that"},
			{"stop trying so hard", "Some people misunderstand me<split> i just like killing, okay?", "I'll wreck you if it's the last thing I do", "i'm a pretty little princess", "my daddy says that i should get off now<split> jk, I killed my daddy and you're next.", "one day you'll understand", "i dare you to click F<useless>", "Why do you play Anarchy?<split> I play it to kill you", "is someone playing dubstep?<split> nope, that's just your body wobbling in fear", "one day, you'll win.<split> or maybe you won't", "it took me a long time to figure out your name.<split> it's noob, isn't it?", "I've got a million things to say<split> and they're all really mean", "Sometimes I stare up at the sky<split>and the sky is red", "When I'm done with you<split> you'll be a null pointer", "let's ramp it up", "have you had enough yet?", "Sometimes I think about things<split> like your body on the floor", "I'm gonna drop you so hard<split>they'll start calling you the bass", "Looks like it's all over for you", "What are you afraid of.. <split>could it be... death?", "I'll have you know that I have a pHD in kicking your ass.", "i bet you couldn't kill a cow<split> so why try and kill me?", "Once upon a time, there was a character named <name><split> then <heshe> died. The end.", "my mommy says that killing is wrong<split> what do you think about dieing?", "Get ready to bow to me, your overlord", "unfortunately, I've run out of 'please don't kill me' cards<split> I guess I'll have to kill you.", "The red ball bounced and bounced and wait<split> it stopped bouncing. It'll bounce again, I'm sure.", "I have no idea what I'm doing<split> do you?", "If I killed you<split> I would probably quit bouncing my red ball.", "The voices in my head tell me that you're nice<split> i don't believe them.", "Fifty seven years from now they'll remember your name.<split> <name>, may <heshe> rest in pieces.", "Sometimes I dream<split> they're usually nightmares.", "Don't look now, but i think <name> is gonna die", "You know that feeling when you don't feel anything?<split> Okay, just checking.", "I'll never lose to you", "my daddy wants me to grow up<split>he says i'm a little baby", "once a day, i go outside<split>but them i'm like, nah time to go back in", "be ready for death", "I'm graping you so hard right now<split>I really mean that", "When the birds fly in the sky<split>I can't help but wonder why", "Let's go!", "Let's get this party started"},
	};
	
	public String[][][] messages2 = {
			{{"", "fat", "safing", "safe", "eat"}, {"i eat like a boss", "my eating level is 99", "safing is safe", "as if", "sure i am, noob", "now you're asking for it"}},
			{{"attack", "off", "stop"}, {"i'll attack you forever", "you don't wanna fight? too bad", "I'll wreck you if it's the last thing I do", "now you're asking for it", "just for that, i'm gonna destroy you"}},
	};

	@Override
	public void speak() {
		/*safing*/
		if (Utils.getRandom(2) >= 1)
			return;
		if (getAttackedBy() != null) {
			if (!(getAttackedBy() instanceof Player))
				return;
			Player target = (Player) getAttackedBy();
		if (target.lastChatMessage() != null &&
				target.getLastPublicMessage()+6000 > Utils.currentTimeMillis()) {
			for(int i = 0; i < messages2.length; i++) {
				for(int i3 = 0; i3 < messages2[i][0].length; i3++) {
					if (target.lastChatMessage().toLowerCase().contains(messages2[i][0][i3]))
						publicMessage(messages2[i][1][Utils.getRandom(messages2[i][1].length-1)]);
				}
			}
			publicMessage(messages[7][Utils.getRandom(messages[7].length-1)]);
		}
		if (!target.withinDistance(target)) {
			publicMessage(messages[5][Utils.getRandom(messages[5].length-1)]);
	}
		if (target.getHitpoints() >= 800 && 
				target.getFoodDelay() > Utils.currentTimeMillis()) {
				publicMessage(messages[0][Utils.getRandom(messages[0].length-1)]);
		}
		if (Utils.getRandom(10) == 0) {
			String message = messages[8][Utils.getRandom(messages[8].length-1)].replace("<useless>", ""+(6+Utils.getRandom(4))).replace("<name>", target.getDisplayName()).replace("<heshe>", target.getAppearence().isMale() ? "he" : "she");
			String[] m = message.split("<split>");
			if (m.length == 2)
				publicMessage(m[0], m[1]);
			else
				publicMessage(m[0]);
		}
		//if (robot.getControlerManager().getControler() instanceof ArenaControler)
		//	return;
		if (robot.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
			if (target.getAttackedBy() != robot) {//stolen
				publicMessage(messages[2][Utils.getRandom(messages[2].length-1)]);
			}
		}
		if (target.lastChatMessage() != null && target.lastChatMessage().toLowerCase().contains("off")) {
			if (target.getAttackedBy() == robot && robot.getAttackedByDelay() + 5000 < Utils.currentTimeMillis()) {//stolen
				publicMessage(messages[4][Utils.getRandom(messages[2].length-1)]);
				robot.setAttackedBy(null);
				robot.stopAll(true);
			}
		}
		}
	}

	@Override
	public int[][][] getFixedPath() {
		return new int[][][] {
				{{3091,3488,3098,3499,0},
					{3092,3496,3094,3498,0},
					{3097,3498,3099,3500,0},
					{3093,3497,3093,3497,0},
					{3093,3500,3093,3500,0},
					{3091,3502,3093,3504,0},
					{3085,3502,3087,3504,0},
					{3086,3506,3088,3508,0},
					{3087,3514,3087,3514,0},
					{3084,3516,3084,3516,0},
					{3084,3519,3084,3519,0},
					{3079,3523,3081,3525,0},
					{3079,3527,3079,3527,0}},//bank to wild
					{{3091,3488,3098,3499,0},
						{3093,3497,3093,3497,0},
						//{3099,3497,3099,3497,0},
						//{3100,3498,3100,3498,0},
						{3100,3498,3100,3498,0},
						//{3099,3503,3101,3505,0},
						//{3103,3505,3103,3505,0},
						{3108,3504,3110,3506,0},
						{3112,3504,3114,3506,0},
						{3114,3508,3114,3508,0},
						{3113,3514,3115,3516,0},
						{3113,3519,3115,3521,0},
						{3114,3525,3114,3525,0},
						{3113,3527,3113,3527,0}},//banktowild2
						{{3091,3488,3098,3499,0},
							{3093,3497,3093,3497,0},
							//{3099,3497,3099,3497,0},
							//{3100,3498,3100,3498,0},
							{3100,3498,3100,3498,0},
							{3099,3503,3101,3505,0},
							{3103,3505,3103,3505,0},
							{3108,3504,3110,3506,0},
							{3112,3504,3114,3506,0},
							{3114,3508,3114,3508,0},
							{3113,3514,3115,3516,0},//
							{3119,3516,3121,3518,0},
							{3130,3516,3132,3518,0},
							{3138,3518,3138,3518,0},
							{3141,3519,3143,3521,0},
							{3142,3527,3145,3530,0}},
						{{3091,3488,3098,3499,0},
							{3093,3497,3093,3497,0},
							{3093,3503,3093,3503,0},
							{3079,3502,3081,3504,0},
							{3079,3499,3081,3501,0},
							{3076,3501,3076,3501,0},
							{3072,3504,3072,3504,0},
							{3069,3504,3069,3504,0},
							{3069,3511,3069,3511,0},
							{3062,3511,3062,3511,0},
							{3057,3514,3059,3516,0},
							{3043,3515,3045,3517,0},
							{3043,3525,3045,3527,0}},//banktowild3
								{{3079,3474,3083,3478,0},
								{3079,3479,3079,3479,0},
								{3078,3482,3080,3484,0},
								{3080,3485,3082,3487,0},
								{3087,3489,3089,3491,0},
								{3090,3491,3090,3491,0},
								{3092,3490,3093,3491,0}},//edgevilletobank
		};
	}
}
