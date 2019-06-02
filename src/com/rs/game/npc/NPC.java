package com.rs.game.npc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.SecondaryBar;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.communication.discord.DiscordHandler;
import com.rs.game.item.Item;
import com.rs.game.npc.combat.NPCCombat;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.player.InterfaceManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.SlayerManager;
import com.rs.game.player.actions.HerbCleaning;
import com.rs.game.player.content.BossPetHandler;
import com.rs.game.player.content.BossPetHandler.BossPets;
import com.rs.game.player.content.BrawlingGManager.BrawlingGloves;
import com.rs.game.player.content.Burying;
import com.rs.game.player.content.Deaths;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.Hunter.FlyingEntities;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Seedicider;
import com.rs.game.player.content.SlayerMasks.Masks;
import com.rs.game.player.content.SpringCleaner;
import com.rs.game.player.content.WildernessArtefacts;
import com.rs.game.player.content.contracts.ContractHandler;
import com.rs.game.player.content.instances.Instances.RespawnDelay;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.GodWars;
import com.rs.game.player.controlers.WGuildControler;
import com.rs.game.player.controlers.WG2Controler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.server.fameHall.HallOfFame;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.world.GlobalBossCounter;
import com.rs.game.world.GlobalItemCounter;
import com.rs.game.world.RecordHandler;
import com.rs.io.OutputStream;
import com.rs.utils.Colors;
import com.rs.utils.Logger;
import com.rs.utils.MapAreas;
import com.rs.utils.Misc;
import com.rs.utils.NPCBonuses;
import com.rs.utils.NPCCombatDefinitionsL;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

public class NPC extends Entity implements Serializable {

	private static final long serialVersionUID = -4794678936277614443L;

	private int id;
	private WorldTile respawnTile;
	protected int mapAreaNameHash;
	private boolean canBeAttackFromOutOfArea;
	private boolean randomwalk;
	private int[] bonuses; // 0 stab, 1 slash, 2 crush,3 mage, 4 range, 5 stab
	// def, blahblah till 9
	private boolean spawned;
	private transient NPCCombat combat;
	public WorldTile forceWalk;
	public int whatToSay = 0;
	private long lastAttackedByTarget;
	private boolean cantInteract;
	private int capDamage;
	private int lureDelay;
	private boolean cantFollowUnderCombat;
	private boolean forceAgressive;
	private int forceTargetDistance;
	private boolean forceFollowClose;
	private boolean forceMultiAttacked;
	private boolean noDistanceCheck;

	// npc masks
	private transient Transformation nextTransformation;
	private transient SecondaryBar nextSecondaryBar;
	// name changing masks
	private String name;
	private transient boolean changedName;
	private transient boolean changedOption;
	private int combatLevel;
	private transient boolean changedCombatLevel;
	private transient boolean locked;

	public NPC(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		this(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, false);
	}

	/*
	 * creates and adds npc
	 */
	public NPC(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(tile);
		this.id = id;
		this.respawnTile = new WorldTile(tile);
		this.mapAreaNameHash = mapAreaNameHash;
		this.canBeAttackFromOutOfArea = canBeAttackFromOutOfArea;
		this.setSpawned(spawned);
		combatLevel = -1;
		setHitpoints(getMaxHitpoints());
		setDirection(getRespawnDirection());
		for (int i : Settings.NON_WALKING_NPCS1) {
			if (i == id)
				setRandomWalk(false);
			else
				setRandomWalk((getDefinitions().walkMask & 0x2) != 0 || forceRandomWalk(id));
		}
		bonuses = NPCBonuses.getBonuses(id);
		combat = new NPCCombat(this);
		capDamage = -1;
		lureDelay = 12000;
		BossTimeUpdate = new ArrayList<>();
		// npc is inited on creating instance
		initEntity();
		World.addNPC(this);
		World.updateEntityRegion(this);
		// npc is started on creating instance
		loadMapRegions();
		checkMultiArea();
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || nextSecondaryBar != null || nextTransformation != null || changedCombatLevel || changedName || changedOption;
	}

	public void transformIntoNPC(int id) {
		setNPC(id);
		nextTransformation = new Transformation(id);
	}

	public void setNextNPCTransformation(int id) {
		setNPC(id);
		nextTransformation = new Transformation(id);
		if (getCustomCombatLevel() != -1)
			changedCombatLevel = true;
		if (getCustomName() != null)
			changedName = true;
	}

	public static void moo() {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				String[] mooing = { "Moo", "Moooo", "MOOOOOOOOO", "derp", "Mooooooooo", "Neigh" };
				int i = Misc.random(1, 5);
				for (NPC n : World.getNPCs()) {
					if (!n.getName().equalsIgnoreCase("Cow")) {
						continue;
					}
					n.setNextForceTalk(new ForceTalk(mooing[i]));
				}
			}
		}, 0, 5); // time in seconds
	}

	public void setNPC(int id) {
		this.id = id;
		bonuses = NPCBonuses.getBonuses(id);
	}

	@Override
	public void resetMasks() {
		super.resetMasks();
		nextTransformation = null;
		changedCombatLevel = false;
		changedName = false;
		changedOption = false;
		nextSecondaryBar = null;
	}

	public int getMapAreaNameHash() {
		return mapAreaNameHash;
	}

	/**
	 * Necrolord locations spawns
	 */
	public enum Locations {
		KBD_LADDER(new WorldTile(3464, 3739, 0)), MAGIC_ARENA(new WorldTile(3458, 3740, 0)), DAEMONHEIM(new WorldTile(3463, 3746, 0)), RUNITE_ROCKS(new WorldTile(3458, 3747, 0));

		private WorldTile tile;

		private Locations(WorldTile tile) {
			this.tile = tile;
		}

		public WorldTile getTile() {
			return tile;
		}
	}

	public void handleBossKillCount(Player p) {
		for (int bossId : Settings.BOSS_IDS) {
			if (getId() == bossId) {
				handleTriskelion(p);
				int i = 1;
				if (p.getBossCount().containsKey(getId())) {
					i = p.getBossCount().get(getId()) + 1;
				}
				RecordHandler.getRecord().handelBossKills(p);
				p.getBossCount().put(getId(), i);
				p.sm("Your " + getName() + " killcount is: <col=ff0000>" + i + "</col>.");
			}
		}
	}

	public void setCanBeAttackFromOutOfArea(boolean b) {
		canBeAttackFromOutOfArea = b;
	}

	public boolean canBeAttackFromOutOfArea() {
		return canBeAttackFromOutOfArea;
	}

	public void setBonuses(int[] bonuses) {
		this.bonuses = bonuses;
	}

	public NPCDefinitions getDefinitions() {
		return NPCDefinitions.getNPCDefinitions(id);
	}

	public NPCCombatDefinitions getCombatDefinitions() {
		return NPCCombatDefinitionsL.getNPCCombatDefinitions(id);
	}

	@Override
	public int getMaxHitpoints() {
		return getCombatDefinitions().getHitpoints();
	}

	public int getId() {
		return id;
	}

	public void processNPC() {
		if (isDead() || locked)
			return;
		if (!combat.process()) { // if not under combat
			if (!isForceWalking()) {// combat still processed for attack delay
				// go down
				// random walk
				if (!cantInteract) {
					if (!checkAgressivity()) {
						if (getFreezeDelay() < Utils.currentTimeMillis()) {
							if (((hasRandomWalk()) && World.getRotation(getPlane(), getX(), getY()) == 0) // temporary
									// fix
									&& Math.random() * 1000.0 < 100.0) {
								int moveX = (int) Math.round(Math.random() * 10.0 - 5.0);
								int moveY = (int) Math.round(Math.random() * 10.0 - 5.0);
								resetWalkSteps();
								if (getMapAreaNameHash() != -1) {
									if (!MapAreas.isAtArea(getMapAreaNameHash(), this)) {
										forceWalkRespawnTile();
										return;
									}
									addWalkSteps(getX() + moveX, getY() + moveY, 5);
								} else
									addWalkSteps(respawnTile.getX() + moveX, respawnTile.getY() + moveY, 5);
							}
						}
					}
				}
			}
		}
		if (isBoss(this) && BossTimeUpdate.size() > 0) {
			ArrayList<Entity> PossibleTargs = this.getPossibleTargets();
			for (Iterator<Entity> iter = BossTimeUpdate.iterator(); iter.hasNext();) {
				Entity e = iter.next();
				if (e instanceof Player) {
					if (!PossibleTargs.contains(e)) {
						iter.remove();
					}
				}
			}
		}

		if (id == 6137 && Utils.random(10) == 0) {
			setNextAnimation(new Animation(6083));
			if (whatToSay == 0) {
				setNextForceTalk(new ForceTalk("Come try your luck agaisnt the Dreadnaut!"));
				whatToSay = 1;
			} else {
				setNextForceTalk(new ForceTalk("Kill the Dreadnaut and get your very own fighter torso!"));
				whatToSay = 0;
			}
		}
		// Changing npc combat levels
		if (id == 5219) {
			setCombatLevel(125);
		}
		if (id == 549) {
			setRandomWalk(false);
			setName("Item switcher");
		if (id == 2824) {
			setRandomWalk(false);
			setName("Tanner");
		}
		} if(id == 13119|| id == 13131|| id == 13130|| id == 13142 || id == 13143 || id == 13154 || id == 13155 )
			setRandomWalk(true);
		if (id == 519) {
			setRandomWalk(false);
			setName("Skilling supplies");
		}
		if (id == 14945) {
			setRandomWalk(false);
			setName("Riddle and vote helper");
		}if(id == 558)
			setName("Fishing shop");
		if (id == 5218) {
			setCombatLevel(110);
		}
		if (id == 15581) {// Party Demon
			setCombatLevel(1500);
		}
		if (id == 3064) {// Lesser Demon Champion
			setCombatLevel(900);
		}
		if (id == 3058) {// Giant Champion
			setCombatLevel(800);
		}
		if (id == 3063) {// Jogre Champion
			setCombatLevel(850);
		}
		if (id == 15187) {// TokHaar-Ket Champion
			setCombatLevel(1100);
		}
		if (id == 10495) {// High level Lesser Demon
			setCombatLevel(550);
		}
		if (id == 4706) {// High Level Moss giant
			setCombatLevel(600);
		}
		if (id == 10769) {// High level ice giant
			setCombatLevel(650);
		}
		if (id == 10717) {// High level Hill giant
			setCombatLevel(550);
		}
		if (id == 10761) {// High level Fire giant
			setCombatLevel(680);
		}
		if (id == 3450) {// High level Jogre
			setCombatLevel(600);
		}
		if (id == 999) {// Doomion
			setCombatLevel(900);
		}
		if (id == 998) {// Othainian
			setCombatLevel(900);
		}
		if (id == 1000) {// Holthion
			setCombatLevel(900);
		}
		if (id == 14550) {// Chronozon
			setCombatLevel(950);
		}
		if (id == 14503) {// Agrith Naar
			setCombatLevel(850);
		}
		if (id == 9356) {// Agrith Naar
			setCombatLevel(100);
		}
		// Renaming basic npcs
		if (id == 3547) {
			setRandomWalk(false);
		}
		if (id == 758) {
			setRandomWalk(false);
			if (Utils.random(30) == 0)
				setNextForceTalk(new ForceTalk("Buy your farming tools here!"));
		}
		if (id == 515 || id == 9085 || id == 558) {
			setRandomWalk(false);
		}
		if (id == 578) {
			setRandomWalk(false);
			setName("Herblore Shop");
		}
		if (id == 13295) {
			setRandomWalk(false);
			setName("Player shops");
		}
		if (id == 563) {
			setRandomWalk(false);
			setName("General store");
		}
		if (id == 12) {
			setRandomWalk(false);
			setName("Bart the Achiever");
		}
		if (id == 7892) {
			setName("Server manager");
		}
		if (id == 4988) {
			setRandomWalk(false);
			setName("Slave seller");
		}
		if (id == 70267) {
			setRandomWalk(false);
			setName("Wilderness manager");
		}
		if (id == 8452) {
			setRandomWalk(false);
			setName("Boss helper");
		}
		if (id == 2732) {
			setName("Crafting Shop");
			setRandomWalk(false);
		}
		if (id == 6334 && Utils.random(10) == 0) {
			setNextForceTalk(new ForceTalk("Someone help please!"));
		} // intro girl
		if (id == 5985 && Utils.random(10) == 0) {
			setRandomWalk(false);
			setNextForceTalk(new ForceTalk("Please don't hurt me!"));
		}
		if (id == 220) {

			setName("Donator Points Shop");
		}
		if (id == 5445)
			setName("Cosmetic shop");
		if (id == 9234)
			setName("Consumable shop");
		if (id == 541) {

			setName("Donator Points Shop 2");
		}
		if (id == 11254) {
			setRandomWalk(false);
			setName("Consumables Shop");
		}
		if (id == 13324) {
			setRandomWalk(false);
			setName("Pets Shop");
		}
		if (id == 455) {
			setRandomWalk(false);
			setName("Herblore Shop");
		}
		if (id == 6539) {
			setName("Level Reset");
			setRandomWalk(false);
		}
		if (id == 12862) {
			setRandomWalk(true);
		}
		if (id == 11882) {
			setRandomWalk(true);
		}
		if (id == 8031) {
			setName("Dark Invasion");
			setRandomWalk(false);
		}

		if (id == 2262) {
			setName("Prestige");
		}

		if (id == 558) {
			setName("Fishing Shop");
		}

		if (id == 2262) {
			setName("Prestige");
		}

		if (id == 3709) {
			setName("Point Shop");
		}

		if (id == 1917) {
			setName("Player Owned Shops");
		}
		// new shop npcs
		if (id == 564) {
			setName("Master Capes");
			setRandomWalk(false);
		}
		if (id == 12192) {
			setName("Runes Shop");
			setRandomWalk(false);
		}
		if (id == 12306) {
			setName("Armour Shop");
			setRandomWalk(false);
		}
		if (id == 13172) {
			setName("Magic Shop");
			setRandomWalk(false);
		}
		if (id == 1847) {
			setName("Mining Shop");
			setRandomWalk(false);
		}
		if (id == 13464) {
			setName("Weapons Shop");
			setRandomWalk(false);
		}
		if (id == 13789) {
			setName("Skilling Shop");
			setRandomWalk(false);
		}
		if (id == 14160) {
			setName("Pure's Shop");
			setRandomWalk(false);
		}
		if (id == 15151) {
			setName("Archer Shop");
			setRandomWalk(false);
		}
		if (id == 15582) {
			setName("Outfit Shop");
			setRandomWalk(false);
		}
		if (id == 13389) {
			setName("Donator Shop");
			setRandomWalk(false);
		}
		if (id == 576 || id == 558) {
			setName("Fishing Shop");
			setRandomWalk(false);
		}
		if (id == 3373) {
			setName("The Maxer");
			setRandomWalk(false);
			setNextAnimation(new Animation(621));
		}
		// edit npc's movement

		if (isForceWalking()) {
			if (getFreezeDelay() < Utils.currentTimeMillis()) {
				if (getX() != forceWalk.getX() || getY() != forceWalk.getY()) {
					if (!hasWalkSteps())
						addWalkSteps(forceWalk.getX(), forceWalk.getY(), getSize(), true);
					if (!hasWalkSteps()) { // failing finding route
						setNextWorldTile(new WorldTile(forceWalk));
						forceWalk = null;
					}
				} else
					// walked till forcewalk place
					forceWalk = null;
			}
		}
	}

	protected boolean isBoss(NPC npc) {
		for (int bossId : Settings.BOSS_IDS) {
			if (npc.getId() == bossId) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void processEntity() {
		super.processEntity();
		processNPC();
	}

	public int getRespawnDirection() {
		NPCDefinitions definitions = getDefinitions();
		if (definitions.anInt853 << 32 != 0 && definitions.respawnDirection > 0 && definitions.respawnDirection <= 8)
			return (4 + definitions.respawnDirection) << 11;
		return 0;
	}

	private static final Item[] slayerDrops = { new Item(29863, 1), new Item(30027, 1), new Item(28627, 1) };

	public void sendSlayerDrop(Player player) {
		getSlayerDrops(player, slayerDrops[Utils.random(slayerDrops.length)]);
	}

	public void getSlayerDrops(Player player, Item item) {
		if (!player.hasNewSlayerDrops)
			return;
		int size = getSize();
		if (getCombatLevel() >= 200) {
			if (Misc.random(50) == 1) {
				player.sm("You have received a " + item.getName() + " as drop.");
				World.sendLootbeam(player, this);
				World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
			}
		} else if (this.getCombatLevel() >= 100 && this.getCombatLevel() < 200) {
			if (Misc.random(60) == 1) {
				player.sm("You have received a " + item.getName() + " as drop.");
				World.sendLootbeam(player, this);
				World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
			}
		} else if (this.getCombatLevel() < 100) {
			if (Misc.random(70) == 1) {
				World.sendLootbeam(player, this);
				player.sm("You have received a " + item.getName() + " as drop.");
				World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
			}
		}

	}

	/*
	 * forces npc to random walk even if cache says no, used because of fake
	 * cache information
	 */
	private static boolean forceRandomWalk(int npcId) {
		switch (npcId) {
		case 11226:
		case 14301:
			return true;
		case 3341:
		case 3342:
		case 3343:
			return true;
		default:
			return false;
		/*
		 * default: return NPCDefinitions.getNPCDefinitions(npcId).name
		 * .equals("Icy Bones");
		 */
		}
	}
	
	private boolean intelligentRouteFinder;

	public boolean isIntelligentRouteFinder() {
		return intelligentRouteFinder;
	}

	public void setIntelligentRouteFinder(boolean intelligentRouteFinder) {
		this.intelligentRouteFinder = intelligentRouteFinder;
	}

	private void sendSoulSplit( final Hit hit, final Entity user ) {
		final NPC target = this;
		if (hit.getDamage() > 0)
			World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
		if (user instanceof Player && ((Player) user).getEquipment().getAmuletId() == 31875 && Misc.random(1, 2) == 1)
			user.heal(hit.getDamage() / Misc.random(2, 4));
		else
			user.heal(hit.getDamage() / 5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				setNextGraphics(new Graphics(2264));
				if (hit.getDamage() > 0)
					World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0, 0);
			}
		}, 1);
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (capDamage != -1 && hit.getDamage() > capDamage)
			hit.setDamage(capDamage);
		if (hit.getLook() != HitLook.MELEE_DAMAGE && hit.getLook() != HitLook.RANGE_DAMAGE && hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		Entity source = hit.getSource();
		if (source == null)
			return;
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (BossTimeUpdate == null) {
				BossTimeUpdate = new ArrayList<>();
			}
			if (getHitpoints() > getMaxHitpoints() - getMaxHitpoints() / 12) {
				if (!BossTimeUpdate.contains(source)) {
					if (((Player) source).getEquipment().getWeaponId() != 25202) {
						BossTimeUpdate.add(source);
					}
				}
			}
			if (p2.getPrayer().hasPrayersOn()) {
				if (p2.getPrayer().usingPrayer(1, 18))
					sendSoulSplit(hit, p2);
				if (hit.getDamage() == 0)
					return;
				if (!p2.getPrayer().isBoostedLeech()) {
					if (hit.getLook() == HitLook.MELEE_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 19)) {
							p2.getPrayer().setBoostedLeech(true);
							return;
						} else if (p2.getPrayer().usingPrayer(1, 1)) { // sap
							// att
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(0)) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your sap curse has no effect.", true);
								} else {
									p2.getPrayer().increaseLeechBonus(0);
									p2.getPackets().sendGameMessage("Your curse drains Attack from the enemy, boosting your Attack.", true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2214));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2215, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2216));
									}
								}, 1);
								return;
							}
						} else {
							if (p2.getPrayer().usingPrayer(1, 10)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.getPrayer().reachedMax(3)) {
										p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
									} else {
										p2.getPrayer().increaseLeechBonus(3);
										p2.getPackets().sendGameMessage("Your curse drains Attack from the enemy, boosting your Attack.", true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.getPrayer().setBoostedLeech(true);
									World.sendProjectile(p2, this, 2231, 35, 35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2232));
										}
									}, 1);
									return;
								}
							}
							if (p2.getPrayer().usingPrayer(1, 14)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.getPrayer().reachedMax(7)) {
										p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
									} else {
										p2.getPrayer().increaseLeechBonus(7);
										p2.getPackets().sendGameMessage("Your curse drains Strength from the enemy, boosting your Strength.", true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.getPrayer().setBoostedLeech(true);
									World.sendProjectile(p2, this, 2248, 35, 35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2250));
										}
									}, 1);
									return;
								}
							}

						}
					}
					if (hit.getLook() == HitLook.RANGE_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 2)) { // sap range
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(1)) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your sap curse has no effect.", true);
								} else {
									p2.getPrayer().increaseLeechBonus(1);
									p2.getPackets().sendGameMessage("Your curse drains Range from the enemy, boosting your Range.", true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2217));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2218, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2219));
									}
								}, 1);
								return;
							}
						} else if (p2.getPrayer().usingPrayer(1, 11)) {
							if (Utils.getRandom(7) == 0) {
								if (p2.getPrayer().reachedMax(4)) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
								} else {
									p2.getPrayer().increaseLeechBonus(4);
									p2.getPackets().sendGameMessage("Your curse drains Range from the enemy, boosting your Range.", true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2236, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2238));
									}
								});
								return;
							}
						}
					}
					if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 3)) { // sap mage
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(2)) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your sap curse has no effect.", true);
								} else {
									p2.getPrayer().increaseLeechBonus(2);
									p2.getPackets().sendGameMessage("Your curse drains Magic from the enemy, boosting your Magic.", true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2220));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2221, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2222));
									}
								}, 1);
								return;
							}
						} else if (p2.getPrayer().usingPrayer(1, 12)) {
							if (Utils.getRandom(7) == 0) {
								if (p2.getPrayer().reachedMax(5)) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
								} else {
									p2.getPrayer().increaseLeechBonus(5);
									p2.getPackets().sendGameMessage("Your curse drains Magic from the enemy, boosting your Magic.", true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2240, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2242));
									}
								}, 1);
								return;
							}
						}
					}

					// overall

					if (p2.getPrayer().usingPrayer(1, 13)) { // leech defence
						if (Utils.getRandom(10) == 0) {
							if (p2.getPrayer().reachedMax(6)) {
								p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
							} else {
								p2.getPrayer().increaseLeechBonus(6);
								p2.getPackets().sendGameMessage("Your curse drains Defence from the enemy, boosting your Defence.", true);
							}
							p2.setNextAnimation(new Animation(12575));
							p2.getPrayer().setBoostedLeech(true);
							World.sendProjectile(p2, this, 2244, 35, 35, 20, 5, 0, 0);
							WorldTasksManager.schedule(new WorldTask() {
								@Override
								public void run() {
									setNextGraphics(new Graphics(2246));
								}
							}, 1);
							return;
						}
					}
				}
			}
		}

	}

	@Override
	public void reset() {
		super.reset();
		setDirection(getRespawnDirection());
		combat.reset();
		bonuses = NPCBonuses.getBonuses(id); // back to real bonuses
		forceWalk = null;
	}

	@Override
	public void finish() {
		if (hasFinished())
			return;
		setFinished(true);
		World.updateEntityRegion(this);
		World.removeNPC(this);
	}

	public RespawnDelay respawnDelay;

	public int getRespawnDelay() {
		int delay = 600;
		if (this.respawnDelay != null) {
			switch (respawnDelay) {
			case FAST:
				delay = 200;
				break;
			case MEDIUM:
				delay = 400;
				break;
			case SLOW:
				delay = 800;
				break;
			}
		}
		return delay;
	}

	public void setRespawnTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 600, TimeUnit.MILLISECONDS);
	}

	public void setLongRespawnTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 2000, TimeUnit.MILLISECONDS);
	}

	public void removespring() {
		if (!hasFinished()) {
			World.removeNPC(null);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					// spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 2000, TimeUnit.MILLISECONDS);
	}

	public void setLongerRespawnTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 5000, TimeUnit.MILLISECONDS);
	}

	public void setremovenpcTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * -1, TimeUnit.MILLISECONDS);
	}

	public void deserialize() {
		if (combat == null)
			combat = new NPCCombat(this);
		spawn();
	}

	public void spawn() {
		setFinished(false);
		World.addNPC(this);
		setLastRegionId(0);
		World.updateEntityRegion(this);
		loadMapRegions();
		checkMultiArea();
	}

	public NPCCombat getCombat() {
		return combat;
	}

	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		resetWalkSteps();
		combat.removeTarget();
		drop();
		setNextAnimation(null);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(defs.getDeathEmote()));
				} else if (loop >= defs.getDeathDelay()) {
					reset();
					setLocation(respawnTile);
					finish();
					BossTimeUpdate = new ArrayList<>();
					if (!isSpawned())
						setRespawnTask();
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}

	static int mult = (World.quadcharms ? 2 : 1);
	private static final Item[] CHARMS = { new Item(12158, 5 * mult), new Item(12159, 4 * mult), new Item(12160, 3 * mult), new Item(12163, 3 * mult), };

	public void dropCharm(Player player, Item item) {
		int size = getSize();
		/**
		 * Blue, green, gold, crimson
		 */
		final int[] charms = { 12163, 12159, 12158, 12160 };
		if (player.selectedCharms == null)
			player.selectedCharms = new boolean[charms.length];
		Item dropItem = new Item(item.getId(), World.quadcharms ? 2 : 1);
		if (dropItem != null) {
			for (int i = 0; i < charms.length; i++) {
				if (dropItem.getId() == charms[i]) {
					if ((player.getInventory().containsItem(27996, 1) || player.getEquipment().containsOneItem(27996)) && player.selectedCharms[i] && (player.getInventory().getFreeSlots() > 0 || player.getInventory().containsItem(dropItem.getId(), 1)))
						player.getInventory().addItem(dropItem);
					else if (this.getId() == 3847)
						World.addGroundItem(item, new WorldTile(1622, 4580, 0), player, false, 180, true);
					else
						World.addGroundItem(dropItem, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
					break;
				}
			}
		}
	}

	public void sendCharms(Player player, NPC npcs) {
		if (Utils.random(npcs.getId() == 1471 ? 12 : 5) == 0)
			dropCharm(player, CHARMS[Utils.random(CHARMS.length)]);
	}

	public void drop() {
		try {
			Player killer1 = getMostDamageReceivedSourcePlayer();
			final int size = getSize();
			Drop[] drops = NPCDrops.getDrops(id);

			if(killer1 == null) //since npc killing eachother aswell xd 
				return;
		//	if (Utils.getRandom(1) == 0)
			//	killer1.getBGloves().dropItem(BrawlingGloves.values()[Utils.random(BrawlingGloves.values().length)], this);

			/*
			 * increases player killcount and server killcount
			 */
			handleBossKillCount(killer1);
			
			GlobalBossCounter.handleBossCount(this, killer1);
			/*
			 * Handles boss pet drops. handled before controller check
			 */
			BossPets pet = BossPets.forId(this.getId());
			if (pet != null) {
				BossPetHandler.check(killer1, this);
			}
			/*
			 * double check for controllers
			 */
			if ((killer1.getControlerManager().getControler() != null && killer1.getControlerManager().getControler() instanceof FightCaves || killer1.getControlerManager().getControler() instanceof FightKiln)) {
				return;
			}
			/**
			 * power tokens 
			 */
			if (this.getCombatLevel() < 50 && this != null) {
				killer1.getPointsManager().setPowerTokens(killer1.getPointsManager().getPowerTokens() + Utils.random(2, 8));

			} else if (this.getCombatLevel() >= 50 && this.getCombatLevel() < 85 && this != null) {
				killer1.getPointsManager().setPowerTokens(killer1.getPointsManager().getPowerTokens() + Utils.random(5, 10));

			} else if (this.getCombatLevel() >= 85 && this.getCombatLevel() < 130 && this != null) {

				killer1.getPointsManager().setPowerTokens(killer1.getPointsManager().getPowerTokens() + Utils.random(5, 15));

			} else if (this.getCombatLevel() >= 130 && this.getCombatLevel() < 230 && this != null) {

				killer1.getPointsManager().setPowerTokens(killer1.getPointsManager().getPowerTokens() + Utils.random(10, 20));

			} else if (this.getCombatLevel() >= 230 && this.getCombatLevel() < 400 && this != null) {
				killer1.getPointsManager().setPowerTokens(killer1.getPointsManager().getPowerTokens() + Utils.random(20, 40));

			} else if (this.getCombatLevel() >= 400 && this != null) {
				killer1.getPointsManager().setPowerTokens(killer1.getPointsManager().getPowerTokens() + Utils.random(20, 70));
			}
			/*
			 * chance of dropping an effigy
			 */
			int effigy = Misc.random(750);
			if (effigy == 1){
				killer1.sm(Colors.red+"You received an effigy drop.");
				World.addGroundItem(new Item(18778, 1), new WorldTile(this.getCoordFaceX(size), getCoordFaceY(size), getPlane()), killer1, true, 180);
			}
			else if (this.getId() == 9356) {
				killer1.VS = 4;
			}
			if(Utils.random(200 - getCombatLevel()/10) == 0){
				if(Utils.random(2) == 0)
					World.addGroundItem(new Item(987, 1), new WorldTile(this.getCoordFaceX(size), getCoordFaceY(size), getPlane()), killer1, true, 180);
				else
					World.addGroundItem(new Item(985, 1), new WorldTile(this.getCoordFaceX(size), getCoordFaceY(size), getPlane()), killer1, true, 180);
			killer1.sm(Colors.red+"You received a key part as drop.");
			}
			/*
			 * revenants dropping brawlers handled here
			 */
			if (isRevenantNPC(getDefinitions().name)) {
				if (Utils.random(80) == 0)
					killer1.getBGloves().dropItem(BrawlingGloves.values()[Utils.random(BrawlingGloves.values().length)], this);
			}
			Player killer = getMostDamageReceivedSourcePlayer();
			if (killer == null)
				return;
			Player otherPlayer = killer.getSlayerManager().getSocialPlayer();
			SlayerManager manager = killer.getSlayerManager();
			if (manager.isValidTask(getName())) {
				manager.checkCompletedTask(getDamageReceived(killer), otherPlayer != null ? getDamageReceived(otherPlayer) : 0);
			}

			Deaths manager3 = killer.getDeathsManager();
			if (manager3.getTask() != null) {
				if (getId() == manager3.getNpcId()) {

					manager3.completeTask();
				}
			}
			/*
			 * darklord his drops get dropped outside the room
			 */
			if (this.getId() == 19553) {
				killer1.sm("<col=FF0000>Run south and use the stairs to see your drops.");
			}
			if (this.getId() == 18496){
				killer1.lock();
				killer1.getDialogueManager().startDialogue("ServerIntro3");
				killer1.getHintIconsManager().removeUnsavedHintIcon();
			}
				
			/*
			 * sevrer intro npc (removed atm)
			 */
			/*
			 * else if (this.getId() == 18496){ killer1.lock();
			 * killer1.getDialogueManager().startDialogue("ServerIntro3");
			 * killer1.getHintIconsManager().removeUnsavedHintIcon(); }
			 */

			if (this.getId() == 14416) {
				if (killer1.completedPvmQuest == false) {
					killer1.completedPvmQuest = true;
					Magic.sendCustomTeleportSpell2(killer1, 0, 0, new WorldTile(3676, 2990, 0));
					killer1.getInterfaceManager();
					InterfaceManager.sendPvmMasterComplete(killer1);
					World.sendWorldMessage("News: " + killer1.getDisplayName() + " has completed the Pvm Master Quest I.", false);
					killer.sm("Goodjob on helping the General, if  you are ready for another challange talk to him again. This time you'll get a reward.");
				}
				if (killer1.completedPvmQuest) {
					killer.sm("Killing it a second time won't matter, you've already shown how good you are.");
					Magic.sendCustomTeleportSpell2(killer1, 0, 0, new WorldTile(3676, 2990, 0));
				}
			}
			double mult = World.getSpotLightSkill() == Skills.DUNGEONEERING ? 1.6 : 1;
			if(getId() == 10509){
				killer1.getSkills().addXpLamp(Skills.DUNGEONEERING, killer1.getSkills().getLevel(Skills.DUNGEONEERING)* 1000 *mult);
				killer1.getPointsManager().setDungeoneeringTokens(killer1.getPointsManager().getDungeoneeringTokens() + killer1.getSkills().getLevel(Skills.DUNGEONEERING)*15);
			} else if(getId() == 11751){
				killer1.getSkills().addXpLamp(Skills.DUNGEONEERING, killer1.getSkills().getLevel(Skills.DUNGEONEERING)* 3000 *mult);
				killer1.getPointsManager().setDungeoneeringTokens(killer1.getPointsManager().getDungeoneeringTokens() + killer1.getSkills().getLevel(Skills.DUNGEONEERING)*35);
			}else if (getId() == 14256) {
				killer1.getDialogueManager().startDialogue("EndQuest");
				// player.completedZariaPartI = true;
				killer1.ZariaQueststage = 6;
			}
			 if (this.getId() == 17149) {
				killer1.sendLegionDeath(id, 1025, 632, "Primus");
				
			}
			else if (this.getId() == 17150) {
				killer1.sendLegionDeath(id, 1106, 671, "Secundus");
				
			}
			if (this.getId() == 17153) {
				killer1.sendLegionDeath(id, 1191, 634, "Quintus");
				
			}
			if (this.getId() == 17151) {
				killer1.sendLegionDeath(id, 1099, 666, "Tertius");
				
			}
			if (this.getId() == 17152) {
				killer1.sendLegionDeath(id, 1177, 634, "Quartus");
				
			}
			if (this.getId() == 17154) {
				killer1.sendLegionDeath(id, 1183, 626, "Sextus");
				
			}
			if (this.getId() == 17146) {
				killer.resetLegions();
			}

			if (this.getId() == 19553) {
				killer1.setPAdamage(killer1.getPAdamage() + 10);
				killer1.sm("The dark lord has now increased damage on you.");
				RecordHandler.getRecord().handelDarkLord(killer1);
			} else if (this.getId() == 13450) {
				if (HallOfFame.firstNexKil[0] == null) {
					HallOfFame.firstNexKil[0] = killer1.getDisplayName();
					HallOfFame.firstNexKil[1] = java.time.LocalDate.now().toString();
					killer1.isInHOF = true;
					HallOfFame.save();
					killer1.sm("Congratz you are now in the hall of fame.");
				}
			} else if (this.getId() == 20616) {
				if (HallOfFame.firstAvaryss[0] == null) {
					HallOfFame.firstAvaryss[0] = killer1.getDisplayName();
					HallOfFame.firstAvaryss[1] = java.time.LocalDate.now().toString();
					killer1.isInHOF = true;
					HallOfFame.save();
					killer1.sm("Congratz you are now in the hall of fame.");
				}
			}
			if (this.getId() == 11751) {
				for (NPC n : World.getNPCs()) {
					if (n == null || n.getId() != 2037)
						continue;
					n.sendDeath(n);
				}
			}

			if (this.getId() == 5044 || this.getId() == 5045) {
				killer1.Nstage1++;
				killer1.sendMessage("<col=00F5FF>You now have " + killer1.Nstage1 + "/10 of the required kills to continue.</col>");
				if (killer1.Nstage1 >= 10) {
					killer1.setNextWorldTile(new WorldTile(2649, 9393, 0));
					killer1.sendMessage("You have advanced to the next stage! Good luck!");
					killer1.Nstage1 = 0;
				}
			}
			if (this.getId() == 5218 || this.getId() == 5219) {
				killer1.Nstage2++;
				killer1.sendMessage("<col=00F5FF>You now have " + killer1.Nstage2 + "/10 of the required kills to continue.</col>");
				if (killer1.Nstage2 >= 10) {
					// killer1.setNextWorldTile(new WorldTile(3174, 9766, 0));
					killer1.getControlerManager().startControler("DreadnautControler");
					killer1.Nstage2 = 0;
				}
			}
			if (this.getId() == 12862) {
				killer1.Nstage3 = 1;
				killer1.sendMessage("<col=00F5FF>You have killed the Dreadnaut! Talk to Cassie at home for your reward!</col>");
				killer1.setNextWorldTile(new WorldTile(2966, 3397, 0));
			}
			

			// SlayerTask task = killer.getSlayerTask();
			if (killer.currentSlayerTask.getTaskMonstersLeft() > 0) {
				for (String m : killer.currentSlayerTask.getTask().slayable) {
					if (getDefinitions().name.toLowerCase().contains(m.toLowerCase())) {
						//slayer mask extra
						Masks mask = Masks.hasMask(killer.getEquipment().getHatId());
						if(mask != null){
							if(mask.task.simpleName.toLowerCase().contains(m.toLowerCase())){
							int i = 1;
							if (killer.getSlayerMaskCreaturesCount().containsKey(killer.currentSlayerTask.getTask().simpleName)) {
								i = killer.getSlayerMaskCreaturesCount().get(killer.currentSlayerTask.getTask().simpleName) + 1;
								}
							killer.getSlayerMaskCreaturesCount().put(killer.currentSlayerTask.getTask().simpleName, i);
							if(i % 10 == 0){
								Drop[] possibleDrops = new Drop[drops.length];
								int possibleDropsCount = 0;
								for (Drop drop : drops) {
									if (drop.getRate() == 100)
										sendDrop(killer, drop);
									else {
										double dropRate = 1.5;
										if (killer.isDonator())
											dropRate += 0.4;
										if (killer.isLegendaryDonator())
											dropRate += 0.4;
										if (killer.isDivineDonator())
											dropRate += 0.6;
										if (killer.getPrestigeLevel() >= 1)
											dropRate += killer.getPrestigeLevel() * 0.01;
										if ((Utils.getRandomDouble(99) + 1) <= drop.getRate() * dropRate)
											possibleDrops[possibleDropsCount++] = drop;
									}
								}
								if (possibleDropsCount > 0)
									sendDrop(killer, possibleDrops[Utils.getRandom(possibleDropsCount - 1)]);
							}
								
							
							}
						}
						//special slayer drops
						sendSlayerDrop(killer);
						//slayer count
						int i = 1;
						if (killer.getSlayerCreaturesCount().containsKey(killer.currentSlayerTask.getTask().simpleName)) {
							i = killer.getSlayerCreaturesCount().get(killer.currentSlayerTask.getTask().simpleName) + 1;
						}
						killer.getSlayerCreaturesCount().put(killer.currentSlayerTask.getTask().simpleName, i);
						killer.sm("You have killed : " + killer.getSlayerCreaturesCount().get(killer.currentSlayerTask.getTask().simpleName) + " "+Colors.darkRed+getDefinitions().name+"s</col>.");
						killer.currentSlayerTask.onMonsterDeath(killer, this);
						break;
					}
				}
			}
			if (drops == null)
				return;
			Drop[] possibleDrops = new Drop[drops.length];
			int possibleDropsCount = 0;
			int ringId = killer.getEquipment().getRingId();
			for (Drop drop : drops) {
				if (drop.getRate() == 100)
					sendDrop(killer, drop);
				else {
					double dropRate = 1.5;
					if (ItemDefinitions.getItemDefinitions(ringId).getName().toLowerCase().contains("ring of wealth"))
						dropRate += 0.04;
					if (killer.isDonator())
						dropRate += 0.4;
					if (killer.getPrestigeLevel() >= 1)
						dropRate += killer.getPrestigeLevel() * 0.01;
					if ((Utils.getRandomDouble(99) + 1) <= drop.getRate() * dropRate)
						possibleDrops[possibleDropsCount++] = drop;
				}
			}
			if (possibleDropsCount > 0)
				sendDrop(killer, possibleDrops[Utils.getRandom(possibleDropsCount - 1)]);
			ContractHandler.checkContract(killer, id, this);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	/*
	 * handles random tris key part drops
	 */
	private void handleTriskelion(Player killer1) {
		if(Utils.random(100) == 0){
		World.sendLootbeam(killer1, this);
		World.addGroundItem(new Item(Utils.random(28547,28549), 1), new WorldTile(this.getCoordFaceX(1), getCoordFaceY(1), getPlane()), killer1, true, 180);
		}
	}

	protected ArrayList<Entity> BossTimeUpdate;

	public void sendDrop(final Player player, Drop drop) {
		final int size = getSize();
		int bonus = 1;
		int random = Utils.random(1, 100);
		if (World.doubledrops == true) {
			bonus = 2;
		}
		ArrayList<Entity> PossibleTargs = this.getPossibleTargets();
		for (Iterator<Entity> iter = BossTimeUpdate.iterator(); iter.hasNext();) {
			Entity e = iter.next();
			// for(Entity e: BossTimeUpdate){
			if (e instanceof Player) {
				if (PossibleTargs.contains(e)) {
					Player p = (Player) e;
					p.getBossTimerManager().recieveDeath(this);
					// BossTimeUpdate.remove(iter);
					iter.remove();
				}
			}
		}
		sendCharms(player, this);
		String dropName = ItemDefinitions.getItemDefinitions(drop.getItemId()).getName().toLowerCase();
		Item item = ItemDefinitions.getItemDefinitions(drop.getItemId()).isStackable() ? new Item(drop.getItemId(), (drop.getMinAmount() * Settings.DROP_RATE * bonus) + Utils.getRandom(drop.getExtraAmount() * Settings.DROP_RATE * bonus)) : new Item(drop.getItemId(), drop.getMinAmount() + Utils.getRandom(drop.getExtraAmount()));
		/**
		 * row / lucky potion are handled here
		 */
		Random RareDropTableChance = new Random();
		int ringId = player.getEquipment().getRingId();
		int RareDropTable = RareDropTableChance.nextInt(120);
		if (RareDropTable <= 2 && (ItemDefinitions.getItemDefinitions(ringId).getName().toLowerCase().contains("ring of wealth") || !player.luckPotionTimer.finished())) {// checks
																																											// chance
																																											// and
																																											// checks
																																											// for
																																											// the
																																											// ring
																																											// of
																																											// wealth
																																											// /
																																											// lucky
																																											// potion
			final Item[] RARE_DROP_TABLE = { new Item(995, Utils.random(10000, 250000)), new Item(995, 50000), new Item(995, 100000), new Item(995, 150000), new Item(995, 200000), new Item(220, Utils.random(10, 100)), new Item(1624, Utils.random(10, 100)), new Item(1622, Utils.random(10, 100)), new Item(1620, Utils.random(10, 100)), new Item(1618, Utils.random(10, 100)), new Item(1632, Utils.random(10, 50)), new Item(1616, Utils.random(10, 100)), new Item(985, 1), new Item(987, 1), new Item(5698, 1), new Item(5698, 1), new Item(1374, Utils.random(1, 5)), new Item(1320, Utils.random(1, 5)), new Item(1248, Utils.random(1, 5)), new Item(1249, Utils.random(1, 3)), new Item(1186, Utils.random(1, 5)), new Item(1202, Utils.random(1, 5)), new Item(2366, 1), new Item(1149, 1), new Item(9143, Utils.random(1, 350)), new Item(9342, Utils.random(1, 175)), new Item(892, Utils.random(200, 300)), new Item(1392, 200), new Item(7937, Utils.random(200, 800)), new Item(561, Utils.random(20, 80)), new Item(566, Utils.random(1, 750)), new Item(563, Utils.random(1, 750)), new Item(560, Utils.random(1, 750)), new Item(560, Utils.random(1, 750)), new Item(560, Utils.random(1, 750)), new Item(565, Utils.random(1, 750)), new Item(372, Utils.random(100, 250)), new Item(384, Utils.random(150, 300)), new Item(533, Utils.random(30, 180)), new Item(454, Utils.random(150, 700)), new Item(450, Utils.random(150, 250)), new Item(452, 150), new Item(2362, Utils.random(50, 500)), new Item(2364, 50), new Item(2364, Utils.random(1, 100)), new Item(2999, Utils.random(10, 100)), new Item(3001, Utils.random(5, 100)), new Item(270, Utils.random(5, 30)), new Item(5321, 3), new Item(5316, Utils.random(7)), new Item(270, Utils.random(16, 33)), new Item(5289, 10), new Item(5304, Utils.random(1, 15)), new Item(1516, Utils.random(100, 200)), new Item(744, 1000), new Item(744, 1000) };
			World.addGroundItem(RARE_DROP_TABLE[Utils.random(RARE_DROP_TABLE.length)], new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
			player.getPackets().sendGameMessage("<col=ff8c38>Your ring of wealth shines more brightly!");
		}
		/**
		 * start npc checks
		 */
		if (this.getId() == 19553) {
			World.addGroundItem(item, new WorldTile(3810, 4688, 0), player, false, 180, true);
			return;
		} else if(getId() == 5363) //mithril dragon bad way, should be done otherwise
			World.addGroundItem(new Item(2359,1),new WorldTile( getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
		/*else if(getId() == 1591) //iron dragon
			World.addGroundItem(new Item(2351,1),new WorldTile( getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
		else if(getId() == 1592) //steel dragon
			World.addGroundItem(new Item(2353,1),new WorldTile( getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
		*//*
		 * For item drop couting
		 */
		for (int itemId : Settings.RARE_DROP_IDS) {
			if (this.getId() == itemId) {
				int i = 1;
				if (player.getdropCount().containsKey(this.getId())) {
					i = player.getdropCount().get(this.getId()) + 1;
				}
				player.getdropCount().put(this.getId(), i);
			}
		}
		if(player.getRights() != 2)
		GlobalItemCounter.handleDropCount(item);

		if (this.getId() == 3847) {
			World.addGroundItem(item, new WorldTile(1622, 4580, 0), player, false, 180, true);
			return;
		}
		/**
		 * legendary pet pickup
		 */
		/*if (player.getLegendaryPet().getLevel() >= 45 && player.getInventory().getFreeSlots() > 2) {
			player.getInventory().addItem(item.getId(), item.getAmount());
			return;
		}*/
		/**
		 * Coin magnet
		 **/
		if (item.getId() == 995) {
			if (player.getInventory().containsItem(31453, 1) || player.getToolbelt().containsItem(31453)) {
				player.getInventory().addItemMoneyPouch(item);
			} else {
				World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
			}
			return;
		}
		if (Wilderness.isAtWild(player)) {
			WildernessArtefacts.handelDrop(player, this);
		}
		// seedicider
		if (Seedicider.isSeed(item.getId()) && (player.getInventory().containsItem(31188, 1))) {
			Seedicider.convertXp(player, item);
			return;
		}
		if (SpringCleaner.isConvertable(player, item)) {
			return;
		}
		final int neckId = player.getEquipment().getAmuletId();
		final boolean hasNeck = neckId >= 19886 && neckId <= 19888; // demonhorn
																	// etc.
		if ((player.getInventory().containsItem(18337, 1) ||player.getEquipment().containsOneItem(18337)|| player.getToolbelt().containsItem(18337))// Bonecrusher
				&& item.getDefinitions().getName().toLowerCase().contains("bones")) {
			player.getSkills().addXp(Skills.PRAYER, Burying.Bone.forId(drop.getItemId()).getExperience());
			if (hasNeck)
				Burying.handleNecklaces(player, drop.getItemId());
			return;
		}
		if ((player.getInventory().containsItem(19675, 1) || player.getToolbelt().containsItem(19675))// Herbicide
				&& item.getDefinitions().getName().toLowerCase().contains("grimy")) {
			if (player.getSkills().getLevelForXp(Skills.HERBLORE) >= HerbCleaning.getHerb(item.getId()).getLevel()) {
				player.getSkills().addXp(Skills.HERBLORE, HerbCleaning.getHerb(drop.getItemId()).getExperience() * 2);
				return;
			}
		}
		if (random >= 95 && random <= 100) {
			if (this.getId() == 116 || this.getId() == 4292 || this.getId() == 6078 || this.getId() == 6079 || this.getId() == 6080 && drop.getItemId() == 8844) {
				WGuildControler.dropDefender(player, this);
				return;
			}
		}
		//offhands (werken bij alle npcs atm? -bug)
		/*if (random >= 98 && random <= 100) {
			if (this.getId() == 4291 && player.getInventory().containsItem(20072, 1) || player.getBank().containsItem(20072, 1)) {
				WG2Controler.dropOffhand(player, this);
				return;
			}
		}*/
		if(player.getControlerManager().getControler() instanceof GodWars)
			((GodWars)player.getControlerManager().getControler()).handleKC(this);
		/*if (this.getId() == 6230 || this.getId() == 6231 || this.getId() == 6229 || this.getId() == 6232 || this.getId() == 6240 || this.getId() == 6241 || this.getId() == 6242 || this.getId() == 6233 || this.getId() == 6234 || this.getId() == 6243 || this.getId() == 6244 || this.getId() == 6245 || this.getId() == 6246 || this.getId() == 6238 || this.getId() == 6239 || this.getId() == 6227 || this.getId() == 6625 || this.getId() == 6223 || this.getId() == 6222) {
			player.setArmadyl(player.getArmadyl() + 1);
			player.getPackets().sendIComponentText(601, 8, "" + player.getArmadyl() + "");
		}
		if (this.getId() == 6278 || this.getId() == 6277 || this.getId() == 6276 || this.getId() == 6283 || this.getId() == 6282 || this.getId() == 6280 || this.getId() == 6281 || this.getId() == 6279 || this.getId() == 6275 || this.getId() == 6271 || this.getId() == 6272 || this.getId() == 6273 || this.getId() == 6274 || this.getId() == 6269 || this.getId() == 6270 || this.getId() == 6268 || this.getId() == 6265 || this.getId() == 6263 || this.getId() == 6261 || this.getId() == 6260) {
			player.setBandos(player.getBandos() + 1);
			player.getPackets().sendIComponentText(601, 9, "" + player.getBandos() + "");
		}
		if (this.getId() == 6257 || this.getId() == 6255 || this.getId() == 6256 || this.getId() == 6258 || this.getId() == 6259 || this.getId() == 6254 || this.getId() == 6252 || this.getId() == 6250 || this.getId() == 6248 || this.getId() == 6247) {
			player.setSaradomin(player.getSaradomin() + 1);
			player.getPackets().sendIComponentText(601, 10, "" + player.getSaradomin() + "");
		}
		if (this.getId() == 6221 || this.getId() == 6219 || this.getId() == 6220 || this.getId() == 6217 || this.getId() == 6216 || this.getId() == 6215 || this.getId() == 6214 || this.getId() == 6213 || this.getId() == 6212 || this.getId() == 6211 || this.getId() == 6218 || this.getId() == 6208 || this.getId() == 6206 || this.getId() == 6204 || this.getId() == 6203) {
			player.setZamorak(player.getZamorak() + 1);
			player.getPackets().sendIComponentText(601, 11, "" + player.getZamorak() + "");
		}*/
		/* LootShare/CoinShare */
		FriendChatsManager fc = player.getCurrentFriendChat();
		if (player.lootshareEnabled()) {
			if (fc != null) {
				CopyOnWriteArrayList<Player> players = fc.getPlayers();
				CopyOnWriteArrayList<Player> playersWithLs = new CopyOnWriteArrayList<Player>();
				for (Player p : players) {
					if (p.lootshareEnabled() && p.getRegionId() == player.getRegionId()) // If
																							// players
																							// in
																							// FC
																							// have
																							// LS
																							// enabled
																							// and
																							// are
																							// also
																							// in
																							// the
																							// same
																							// map
																							// region.
						playersWithLs.add(p);
				}
				if (item.getDefinitions().getTipitPrice() >= 1000000) {
					int playeramount = playersWithLs.size();
					int dividedamount = (item.getDefinitions().getTipitPrice() / playeramount);
					for (Player p : playersWithLs) {
						p.getInventory().addItemMoneyPouch(new Item(995, dividedamount));
						p.sendMessage(String.format("<col=115b0d>You received: %sx coins from a split of the item %s.</col>", dividedamount, dropName));
						return;
					}
				} else {
					Player luckyPlayer = playersWithLs.get((int) (Math.random() * playersWithLs.size())); // Choose
																											// a
																											// random
																											// player
																											// to
																											// get
																											// the
																											// drop.
					World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), luckyPlayer, true, 180);
					luckyPlayer.sendMessage(String.format("<col=115b0d>You received: %sx %s.</col>", item.getAmount(), dropName));
					for (Player p : playersWithLs) {
						if (!p.equals(luckyPlayer))
							p.sendMessage(String.format("%s received: %sx %s.", luckyPlayer.getDisplayName(), item.getAmount(), dropName));
					}
				}
				return;
			}
		}
		/* End of LootShare/CoinShare */
		player.npcLog(player, item.getId(), item.getAmount(), item.getName(), this.getName(), this.getId());
		if (!player.isPker) {
			World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, true, 180);
		} else {
			World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, true, 1800000000);
		}
		/**
		 * every item that should have a world message & lootbeam
		 */
		if (dropName.contains("pernix") || dropName.contains("torva") || dropName.contains("virtus") 
			|| dropName.contains("bandos_chest") || dropName.contains("bandos_helmet") || dropName.contains("bandos_gloves") || dropName.contains("bandos_tassets") 
		|| dropName.contains("bandos_warshield") || dropName.contains("bandos_boots") || dropName.contains("hilt") || dropName.contains("hati") 
		|| dropName.contains("korasi") || dropName.contains("divine") || (dropName.contains("saradomin") && !dropName.contains("brew")&& !dropName.contains("cape") ) 
		|| dropName.contains("visage") || dropName.contains("zamorakian") || dropName.contains("spectral") || dropName.contains("elysian") 
		|| dropName.contains("steadfast") || dropName.contains("armadyl chest") || dropName.contains("armadyl plate") || dropName.contains("armadyl boots") 
		|| dropName.contains("armadyl_helmet") || dropName.contains("armadyl_gloves") || dropName.contains("armadyl_chest") || dropName.contains("armadyl_plate") 
		|| dropName.contains("armadyl_boots") || dropName.contains("armadyl_helmet") || dropName.contains("armadyl_gloves") || dropName.contains("armadyl_chainskirt") 
		|| dropName.contains("armadyl_chainskirt") || dropName.contains("buckler") || dropName.contains("glaiven") || dropName.contains("ragefire") || dropName.contains("spirit shield") 
		|| dropName.contains("spirit_shield") || dropName.contains("elixer") || dropName.contains("fury") || dropName.contains("arcane") || dropName.contains("vine") 
		|| dropName.contains("goliath") || dropName.contains("swift") || dropName.contains("spellcaster") || dropName.contains("gorgonite") || dropName.contains("promethium") 
		|| dropName.contains("primal") || dropName.contains("polypore_stick") || dropName.contains("polypore stick")  
		|| dropName.contains("vesta") || dropName.contains("pneumatic") || dropName.contains("tracking") || dropName.contains("static")
		|| dropName.contains("vanguard") || dropName.contains("battle-mage") || dropName.contains("trickster")
		|| dropName.contains("statius") || dropName.contains("zuriel") || dropName.contains("morrigan") || dropName.contains("spider_leg") || dropName.contains("araxxi")
		|| dropName.contains("deathtouched") || dropName.contains("dragon_chain") || dropName.contains("dragon chain") || dropName.contains("dragon_full") || dropName.contains("dragon full") 
		|| dropName.contains("dragon_kite") || dropName.contains("dragon kite") || dropName.contains("dragon_rider") || dropName.contains("dragon rider") || dropName.contains("inferno") 
		|| dropName.contains("gilded") || dropName.contains("mask") || dropName.contains("chaotic") || dropName.contains("seismic_wand") || dropName.contains("seismic_singularity") 
		|| dropName.contains("abyssal") || dropName.contains("drygore") || dropName.contains("staff of light") ) {
			World.sendLootbeam(player, this);
			DiscordHandler.sendMessage( player.getDisplayName() + " has just recieved a " + dropName + " drop!" );
			World.sendWorldMessage("<col=ff8c38><img=7>News: " + player.getDisplayName() + " has just recieved a " + dropName + " drop!" + "</col> ", false);
		}
		/**
		 * for items which need a lootbeam but no world message
		 */
		if( dropName.contains("sirenic") || dropName.contains("malevolent")|| dropName.contains("tectonic") || dropName.contains("whip"))
			World.sendLootbeam(player, this);
	}

	@Override
	public int getSize() {
		return getDefinitions().size;
	}

	public int getMaxHit() {
		return getCombatDefinitions().getMaxHit();
	}

	public int[] getBonuses() {
		return bonuses;
	}

	@Override
	public double getMagePrayerMultiplier() {
		return 0;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0;
	}

	public WorldTile getRespawnTile() {
		return respawnTile;
	}

	@Override
	public boolean isUnderCombat() {
		return combat.underCombat();
	}

	@Override
	public void setAttackedBy(Entity target) {
		super.setAttackedBy(target);
		if (target == combat.getTarget() && !(combat.getTarget() instanceof Familiar))
			lastAttackedByTarget = Utils.currentTimeMillis();
	}

	public boolean canBeAttackedByAutoRelatie() {
		return Utils.currentTimeMillis() - lastAttackedByTarget > lureDelay;
	}

	public boolean isForceWalking() {
		return forceWalk != null;
	}

	public void setTarget(Entity entity) {
		if (isForceWalking()) // if force walk not gonna get target
			return;
		combat.setTarget(entity);
		lastAttackedByTarget = Utils.currentTimeMillis();
	}

	public void removeTarget() {
		if (combat.getTarget() == null)
			return;
		combat.removeTarget();
	}

	public void forceWalkRespawnTile() {
		setForceWalk(respawnTile);
	}

	public void setForceWalk(WorldTile tile) {
		resetWalkSteps();
		forceWalk = tile;
	}

	public boolean hasForceWalk() {
		return forceWalk != null;
	}

	//private static final int[] BRAWLING_REWARDS = { 13845, 13846, 13848, 13847, 13857, 13850, 13856, 13851, 13855, 13852, 13849, 13854, 13853, };

	public boolean isWearingZamorak(Player player) {
		for (Item item : player.getEquipment().getItems().getItems()) {
			if (item == null)
				continue; // shouldn't happen
			String name = item.getDefinitions().getName();
			int id = item.getDefinitions().getId();
			// using else as only one item should count
			if (name.contains("Zamorak") || name.contains("Subjugation") || name.contains("Torva") || name.contains("Pernix") || name.contains("Virtus") || name.contains("Unholy book") || name.contains("Damaged book") || name.contains("Zaryte") || id == 20771 || id == 28302)
				return true;
		}
		return false;
	}

	public boolean isWearingSaradomin(Player player) {
		for (Item item : player.getEquipment().getItems().getItems()) {
			if (item == null)
				continue; // shouldn't happen
			String name = item.getDefinitions().getName();
			int id = item.getDefinitions().getId();
			// using else as only one item should count
			if (name.contains("Saradomin") || name.contains("Torva") || name.contains("Pernix") || name.contains("Virtus") || name.contains("Holy book") || name.contains("Damaged book") || name.contains("Ring of devotion") || name.contains("Zaryte") || id == 20771 || id == 28302)
				return true;
		}
		return false;
	}

	public boolean isWearingBandos(Player player) {
		for (Item item : player.getEquipment().getItems().getItems()) {
			if (item == null)
				continue; // shouldn't happen
			String name = item.getDefinitions().getName();
			int id = item.getDefinitions().getId();
			// using else as only one item should count
			if (name.contains("Bandos") || name.contains("Torva") || name.contains("Pernix") || name.contains("Virtus") || name.contains("Book of War") || name.contains("Damaged book") || name.contains("Ancient mace") || name.contains("Granite mace") || name.contains("Zaryte") || id == 20771 || id == 28302)
				return true;
		}
		return false;
	}

	public boolean isWearingArmadyl(Player player) {
		for (Item item : player.getEquipment().getItems().getItems()) {
			if (item == null)
				continue; // shouldn't happen
			String name = item.getDefinitions().getName();
			int id = item.getDefinitions().getId();
			// using else as only one item should count
			if (name.contains("Armadyl") || name.contains("Torva") || name.contains("Pernix") || name.contains("Virtus") || name.contains("Book of Law") || name.contains("Damaged book") || name.contains("Zaryte") || id == 20771 || id == 28302)
				return true;
		}
		return false;
	}

	public boolean isZamorakNpc() {
		switch (id) {
		case 6210: // hell hound
		case 6221: // mage
		case 6219: // warior
		case 6220: // ranger
		case 6217: // ice fiend
		case 6216: // pyre fiend
		case 6215: // bloodveld
		case 6214: // vampire
		case 6213: // werewolf
		case 6212: // werewolf
		case 6211: // imp
		case 6218: // gorak
			return true;
		}
		return false;
	}

	public boolean isSaradominNpc() {
		switch (id) {
		case 6257: // mage
		case 6255: // warior
		case 6256: // range
		case 6258: // knight 103
		case 6259: // knight 101
		case 6254: // priest
			return true;
		}
		return false;
	}

	public boolean isBandosNpc() {
		switch (id) {
		case 6278: // mage
		case 6277: // warior
		case 6276: // range
		case 6283: // goblin 13
		case 6282: // goblin 15
		case 6280: // goblin 12
		case 6281: // goblin 12
		case 6279: // goblin 17
		case 6275: // hobgoblin
		case 6271: // ork
		case 6272: // ork
		case 6273: // ork
		case 6274: // ork
		case 6269: // cyclops
		case 6270: // cyclops
		case 6268: // jogre
		case 9185:
			return true;
		}
		return false;
	}

	public boolean isArmadylNpc() {
		switch (id) {
		case 6230: // range
		case 6231: // mage
		case 6229: // warior
		case 6232: // avi 69
		case 6240: // avi 71
		case 6241: // avi 73
		case 6242: // avi 79
		case 6233: // avi 79
		case 6234: // avi 84
		case 6236: // avi 92
		case 6243: // avi 89
		case 6244: // avi 94
		case 6245: // avi 97
		case 6246: // avi 131
		case 6238: // avi 137
		case 6239: // avi 148
		case 6223:
			return true;
		}
		return false;
	}

	public ArrayList<Entity> getPossibleTargets() {
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>();
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playerIndexes = World.getRegion(regionId).getPlayerIndexes();
			if (playerIndexes != null) {
				for (int playerIndex : playerIndexes) {
					Player player = World.getPlayers().get(playerIndex);
					if (player.getControlerManager().getControler() instanceof GodWars) {
						if (isBandosNpc() && isWearingBandos(player))
							continue;
						else if (isArmadylNpc() && isWearingArmadyl(player))
							continue;
						else if (isZamorakNpc() && isWearingZamorak(player))
							continue;
						else if (isSaradominNpc() && isWearingSaradomin(player))
							continue;
					}

					if (player == null || player.isDead() || player.hasFinished() || !player.isRunning() || !player.withinDistance(this, forceTargetDistance > 0 ? forceTargetDistance : (getCombatDefinitions().getAttackStyle() == NPCCombatDefinitions.MELEE ? 4 : getCombatDefinitions().getAttackStyle() == NPCCombatDefinitions.SPECIAL ? 64 : 8)) || (!forceMultiAttacked && (!isAtMultiArea() || !player.isAtMultiArea()) && player.getAttackedBy() != this && (player.getAttackedByDelay() > Utils.currentTimeMillis() || player.getFindTargetDelay() > Utils.currentTimeMillis())) || !clipedProjectile(player, false) || (!forceAgressive && !Wilderness.isAtWild(this) && immunity(player)))
						continue;
					possibleTarget.add(player);
				}
			}
		}
		return possibleTarget;
	}

	public boolean immunity(Player player) {

		if (player.getSkills().getCombatLevelWithSummoning() >= getCombatLevel() * 2)
			return true;
		else
			return false;
	}

	public boolean checkAgressivity() {
		if (!forceAgressive) {
			NPCCombatDefinitions defs = getCombatDefinitions();
			if (defs.getAgressivenessType() == NPCCombatDefinitions.PASSIVE) {
				return false;
			}
		}
		ArrayList<Entity> possibleTarget = getPossibleTargets();
		if (!possibleTarget.isEmpty()) {
			Entity target = possibleTarget.get(Utils.random(possibleTarget.size()));
			if (target instanceof Player && target != null) {// forinthy brace
																// for future
																// reference
				Player t = (Player) target;
				if (isRevenantNPC(getDefinitions().name) && t.getForinthyRepel() > Utils.currentTimeMillis())
					return false;
			}
			setTarget(target);
			target.setAttackedBy(target);
			target.setFindTargetDelay(Utils.currentTimeMillis() + 10000);
			return true;
		}
		return false;
	}

	public final boolean isRevenantNPC(String npcName) {
		switch (npcName) {
		case "Revenant icefiend":
		case "Revenant knight":
		case "Revenant goblin":
		case "Revenant werewolf":
		case "Revenant hobgoblin":
		case "Revenant hellhound":
		case "Revenant vampyre":
		case "Revenant dragon":
		case "Revenant demon":
		case "Revenant pyrefiend":
		case "Revenant dark beast":
		case "Revenant imp":
		case "Revenant ork":
		case "Chaos Elemental":// for pvp drops
			return true;
		}
		return false;
	}

	public boolean isCantInteract() {
		return cantInteract;
	}

	public void setCantInteract(boolean cantInteract) {
		this.cantInteract = cantInteract;
		if (cantInteract)
			combat.reset();
	}

	public int getCapDamage() {
		return capDamage;
	}

	public void setCapDamage(int capDamage) {
		this.capDamage = capDamage;
	}

	public int getLureDelay() {
		return lureDelay;
	}

	public void setLureDelay(int lureDelay) {
		this.lureDelay = lureDelay;
	}

	public boolean isCantFollowUnderCombat() {
		return cantFollowUnderCombat;
	}

	public void setCantFollowUnderCombat(boolean canFollowUnderCombat) {
		this.cantFollowUnderCombat = canFollowUnderCombat;
	}

	public Transformation getNextTransformation() {
		return nextTransformation;
	}

	@Override
	public String toString() {
		return getDefinitions().name + " - " + id + " - " + getX() + " " + getY() + " " + getPlane();
	}

	public boolean isForceAgressive() {
		return forceAgressive;
	}

	public void setForceAgressive(boolean forceAgressive) {
		this.forceAgressive = forceAgressive;
	}

	public int getForceTargetDistance() {
		return forceTargetDistance;
	}

	public void setForceTargetDistance(int forceTargetDistance) {
		this.forceTargetDistance = forceTargetDistance;
	}

	public boolean isForceFollowClose() {
		return forceFollowClose;
	}

	public void setForceFollowClose(boolean forceFollowClose) {
		this.forceFollowClose = forceFollowClose;
	}

	public boolean isForceMultiAttacked() {
		return forceMultiAttacked;
	}

	public void setForceMultiAttacked(boolean forceMultiAttacked) {
		this.forceMultiAttacked = forceMultiAttacked;
	}

	public boolean hasRandomWalk() {
		return randomwalk;
	}

	public void setRandomWalk(boolean forceRandomWalk) {
		this.randomwalk = forceRandomWalk;
	}

	public String getCustomName() {
		return name;
	}

	public void setName(String string) {
		this.name = getDefinitions().name.equals(string) ? null : string;
		changedName = true;
	}

	public void setOptions(String string) {
		// this.opt
		this.name = getDefinitions().name.equals(string) ? null : string;
		changedOption = true;
	}

	public int getCustomCombatLevel() {
		return combatLevel;
	}

	public int getCombatLevel() {
		return combatLevel >= 0 ? combatLevel : getDefinitions().combatLevel;
	}

	public void write() {
		OutputStream stream = new OutputStream();
		stream.writeByte(0x2); // 0x1 = reset to orig npc models,textures and
								// color, 0x2 = model update, 0x4 = texture or
								// color(not sure the order), 0x8 = texture or
								// color(not sure the order)

		int[] models = new int[] { 65988, -1, -1, -1, -1, -1, -1, -1 }; // model
																		// ids
																		// to
																		// send,
																		// Man
																		// has 8
																		// models
																		// originally
																		// so we
																		// need
																		// to
																		// send
																		// 8 ids
																		// to
																		// client

		for (int i = 0; i < models.length; i++) {
			stream.writeBigSmart(models[i]);
		}
	}

	public String getName() {
		return name != null ? name : getDefinitions().name;
	}

	public String getName2() {
		return getDefinitions().name != null ? getDefinitions().name : name;
	}

	public void setCombatLevel(int level) {
		combatLevel = getDefinitions().combatLevel == level ? -1 : level;
		changedCombatLevel = true;
	}

	public boolean hasChangedName() {
		return changedName;
	}

	public boolean hasChangedCombatLevel() {
		return changedCombatLevel;
	}

	public WorldTile getMiddleWorldTile() {
		int size = getSize();
		return new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane());
	}

	public boolean isSpawned() {
		return spawned;
	}

	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}

	public boolean isNoDistanceCheck() {
		return noDistanceCheck;
	}

	public void setNoDistanceCheck(boolean noDistanceCheck) {
		this.noDistanceCheck = noDistanceCheck;
	}

	public boolean withinDistance(Player tile, int distance) {
		return super.withinDistance(tile, distance);
	}

	/**
	 * Gets the locked.
	 * 
	 * @return The locked.
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Sets the locked.
	 * 
	 * @param locked
	 *            The locked to set.
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public SecondaryBar getNextSecondaryBar() {
		return nextSecondaryBar;
	}

	public void setNextSecondaryBar(SecondaryBar secondaryBar) {
		this.nextSecondaryBar = secondaryBar;
	}

	public ArrayList<Entity> getPossibleTargets(boolean checkNPCs, boolean checkPlayers) {
		int size = getSize();
		// int agroRatio = getCombatDefinitions().getAgroRatio();
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>();
		for (int regionId : getMapRegionsIds()) {
			if (checkPlayers) {
				List<Integer> playerIndexes = World.getRegion(regionId).getPlayerIndexes();
				if (playerIndexes != null) {
					for (int playerIndex : playerIndexes) {
						Player player = World.getPlayers().get(playerIndex);
						if (player == null || player.isDead() || player.hasFinished() || !player.isRunning() || player.getAppearence().isHidden() || !Utils.isOnRange(getX(), getY(), size, player.getX(), player.getY(), player.getSize(), forceTargetDistance) || (!forceMultiAttacked && (!isAtMultiArea() || !player.isAtMultiArea()) && (player.getAttackedBy() != this && (player.getAttackedByDelay() > Utils.currentTimeMillis() || player.getFindTargetDelay() > Utils.currentTimeMillis()))) || !clipedProjectile(player, false) || (!forceAgressive && !Wilderness.isAtWild(this) && player.getSkills().getCombatLevelWithSummoning() >= getCombatLevel() * 2))
							continue;
						possibleTarget.add(player);
					}
				}
			}
			if (checkNPCs) {
				List<Integer> npcsIndexes = World.getRegion(regionId).getNPCsIndexes();
				if (npcsIndexes != null) {
					for (int npcIndex : npcsIndexes) {
						NPC npc = World.getNPCs().get(npcIndex);
						if (npc == null || npc == this || npc.isDead() || npc.hasFinished() || !Utils.isOnRange(getX(), getY(), size, npc.getX(), npc.getY(), npc.getSize(), forceTargetDistance) || !npc.getDefinitions().hasAttackOption() || ((!isAtMultiArea() || !npc.isAtMultiArea()) && npc.getAttackedBy() != this && npc.getAttackedByDelay() > Utils.currentTimeMillis()) || !clipedProjectile(npc, false))
							continue;
						possibleTarget.add(npc);
					}
				}
			}
		}
		return possibleTarget;
	}

	public int getAttackSpeed() {
		Map<Integer, Object> data = getDefinitions().parameters;
		if (data != null) {
			Integer speed = (Integer) data.get(14);
			if (speed != null)
				return speed;
		}
		return 4;
	}

	@Override
	public boolean canMove(int dir) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * TODO crashes npcs without maxhit
	 * @param style
	 * @return
	 */
	public int getMaxHit(int style) {
		/* Fix for npc bonusees */
		if(bonuses == null)
			return 0;
		int maxHit = bonuses[0];
		if (style == 1)
			maxHit = bonuses[1];
		else if (style == 2)
			maxHit = bonuses[2];
		return maxHit / 10;
	}

	public int getWeaknessStyle() {
		Map<Integer, Object> data = getDefinitions().parameters;
		if (data != null) {
			Integer weakness = (Integer) data.get(2848);
			if (weakness != null)
				return weakness;
		}
		return 0;
	}

	/**
	 * Exclusively used for the Impetuous Impulses minigame.
	 */
	public void setRespawnTaskImpling() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
			if (Settings.DEBUG)
				System.out.println("Finishing NPC: [" + toString() + "].");
		}
		id = FlyingEntities.values()[Utils.random(FlyingEntities.values().length)].getNpcId();
		setLocation(new WorldTile(Utils.random(2558 + 3, 2626 - 3), Utils.random(4285 + 3, 4354 - 3), 0));
		long respawnDelay = getCombatDefinitions().getRespawnDelay() * 600;
		if (Settings.DEBUG)
			System.out.println("Respawn task initiated: [" + toString() + "]; time: [" + respawnDelay + "].");
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, respawnDelay, TimeUnit.MILLISECONDS);
	}

}
