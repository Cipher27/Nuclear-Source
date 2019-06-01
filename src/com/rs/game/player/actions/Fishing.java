package com.rs.game.player.actions;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.impl.TheRealFishermanAchievement;
import com.rs.game.player.actions.BarbarianFishing.CatchSpots;
import com.rs.game.player.content.FishingSpotsHandler;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.game.player.content.wildyrework.WildyHandler;
import com.rs.utils.Misc;
import com.rs.utils.Utils;

public class Fishing extends Action {

	public enum Fish {

		ANCHOVIES(321, 15, 40, 0),

		BASS(363, 46, 100, 0),

		COD(341, 23, 45, 0),

		CAVE_FISH(15264, 85, 300, 0),

		HERRING(345, 10, 30, 0),

		LOBSTER(377, 40, 90, 0),

		MACKEREL(353, 16, 20, 0),

		MANTA(389, 81, 46, 0),

		MONKFISH(7944, 62, 120, 0),

		PIKE(349, 25, 60, 0),

		SALMON(331, 30, 70, 0),

		SARDINES(327, 5, 20, 0),

		SEA_TURTLE(395, 79, 38, 0),

		SEAWEED(401, 30, 0, 0),

		OYSTER(407, 30, 0, 0),

		SHARK(383, 76, 110, 11),

		SHRIMP(317, 1, 10, 0),

		SWORDFISH(371, 50, 100, 10),

		TROUT(335, 20, 50, 0),

		TUNA(359, 35, 80, 8),

		CAVEFISH(15264, 85, 300, 0),

		ROCKTAIL(15270, 90, 385, 0),

		LEAPING_TROUT(11328, 48, 75, 5),

		LEAPING_SALMON(11330, 58, 125, 6),

		LEAPING_STURGEON(11332, 70, 225, 7),

		CRAYFISH(13435, 1, 10, 0),

		LAVA_SHARK(20429, 95, 392, 0);

		private final int id, level;
		private final double xp;
		private final double strengthXp;

		private Fish(int id, int level, double xp, double strengthXp) {
			this.id = id;
			this.level = level;
			this.xp = xp;
			this.strengthXp = strengthXp;
		}

		public int getId() {
			return id;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}

		public double getStrengthXp() {
			return strengthXp;
		}
	}

	public enum FishingSpots {
		CAVEFISH_SHOAL(8841, 1, 307, 313, new Animation(622), Fish.CAVE_FISH),

		ROCKTAIL_SHOAL(8842, 1, 307, 15263, new Animation(622), Fish.ROCKTAIL),

		NET(327, 1, 303, -1, new Animation(621), Fish.SHRIMP, Fish.ANCHOVIES), LURE(328, 1, 309, 314, new Animation(622), Fish.TROUT, Fish.SALMON),

		LURE2(329, 1, 309, 314, new Animation(622), Fish.TROUT, Fish.SALMON),

		BAIT2(328, 2, 307, 313, new Animation(622), Fish.PIKE),

		BAIT3(329, 2, 307, 313, new Animation(622), Fish.PIKE, Fish.CAVE_FISH),

		CAGE(6267, 1, 301, -1, new Animation(619), Fish.LOBSTER),

		LURE3(2722, 1, 11323, 314, new Animation(622), Fish.LEAPING_TROUT, Fish.LEAPING_SALMON, Fish.LEAPING_STURGEON),

		LURE4(3019, 1, 11323, 314, new Animation(622), Fish.LEAPING_TROUT, Fish.LEAPING_SALMON, Fish.LEAPING_STURGEON),

		LURE5(317, 1, 11323, 314, new Animation(622), Fish.LEAPING_TROUT, Fish.LEAPING_SALMON, Fish.LEAPING_STURGEON),

		CAGE2(312, 1, 301, -1, new Animation(619), Fish.LOBSTER), HARPOON(312, 2, 311, -1, new Animation(618), Fish.TUNA, Fish.SWORDFISH, Fish.SHARK),

		BIG_NET(313, 1, 305, -1, new Animation(620), Fish.MACKEREL, Fish.COD, Fish.BASS, Fish.SEAWEED, Fish.OYSTER),

		HARPOON1(312, 2, 311, -1, new Animation(618), Fish.TUNA, Fish.SWORDFISH), HARPOON2(313, 2, 311, -1, new Animation(618), Fish.SHARK),

		NET2(952, 1, 303, -1, new Animation(621), Fish.SHRIMP),

		HARPOON3(14907, 1, 311, -1, new Animation(618), Fish.LAVA_SHARK),

		NET3(7045, 1, 303, -1, new Animation(24931), Fish.MONKFISH);

		private final Fish[] fish;
		private final int id, option, tool, bait;
		private final Animation animation;

		static final Map<Integer, FishingSpots> spot = new HashMap<Integer, FishingSpots>();

		public static FishingSpots forId(int id) {
			return spot.get(id);
		}

		static {
			for (FishingSpots spots : FishingSpots.values())
				spot.put(spots.id | spots.option << 24, spots);
		}

		private FishingSpots(int id, int option, int tool, int bait, Animation animation, Fish... fish) {
			this.id = id;
			this.tool = tool;
			this.bait = bait;
			this.animation = animation;
			this.fish = fish;
			this.option = option;
		}

		public Fish[] getFish() {
			return fish;
		}

		public int getId() {
			return id;
		}

		public int getOption() {
			return option;
		}

		public int getTool() {
			return tool;
		}

		public int getBait() {
			return bait;
		}

		public Animation getAnimation() {
			return animation;
		}
	}

	private FishingSpots spot;

	private NPC npc;
	private WorldTile tile;
	private int fishId;

	private final int[] BONUS_FISH = { 341, 349, 401, 407 };

	private boolean multipleCatch;

	public Fishing(FishingSpots spot, NPC npc) {
		this.spot = spot;
		this.npc = npc;
		tile = new WorldTile(npc);
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		fishId = getRandomFish(player);
		if (spot.getFish()[fishId] == Fish.TUNA || spot.getFish()[fishId] == Fish.SHARK || spot.getFish()[fishId] == Fish.SWORDFISH) {
			if (Utils.getRandom(50) <= 5) {
				if (player.getSkills().getLevel(Skills.AGILITY) >= spot.getFish()[fishId].getLevel())
					multipleCatch = true;
			}
		}
		player.getPackets().sendGameMessage("You attempt to capture a fish...", true);
		setActionDelay(player, getFishingDelay(player));
		return true;
	}

	@Override
	public boolean process(Player player) {
		player.setNextAnimation(spot.getAnimation());
		return checkAll(player);
	}

	private int getFishingDelay(Player player) {
		int playerLevel = player.getSkills().getLevel(Skills.FISHING);
		int fishLevel = spot.getFish()[fishId].getLevel() * 2;
		int modifier = spot.getFish()[fishId].getLevel();
		int randomAmt = Utils.random(4);
		double cycleCount = 1, otherBonus = 0;
		if (player.getFamiliar() != null)
			otherBonus = getSpecialFamiliarBonus(player.getFamiliar().getId());
		cycleCount = Math.ceil(((fishLevel + otherBonus) * 50 - playerLevel * 10) / modifier * 0.25 - randomAmt * 4);
		if (cycleCount < 1)
			cycleCount = 1;
		int delay = (int) cycleCount + 1;
		delay /= player.getAuraManager().getFishingAccurayMultiplier();
		return delay;

	}

	private int getSpecialFamiliarBonus(int id) {
		switch (id) {
		case 6796:
		case 6795:// rock crab
			return 1;
		}
		return -1;
	}

	private int getRandomFish(Player player) {
		int random = Utils.random(spot.getFish().length);
		int difference = player.getSkills().getLevel(Skills.FISHING) - spot.getFish()[random].getLevel();
		if (difference < -1)
			return random = 0;
		if (random < -1)
			return random = 0;
		return random;
	}

	@Override
	public int processWithDelay(Player player) {
		addFish(player);
		return getFishingDelay(player);
	}

	private void addFish(Player player) {
		Item fish = new Item(spot.getFish()[fishId].getId(), multipleCatch ? 2 : 1);
		if (fish.getId() == 371)
			player.setNextAnimation(new Animation(6707));
		player.getInventory().deleteItem(spot.getBait(), 1);
		double totalXp = spot.getFish()[fishId].getXp();
		if (player.getDailyTask() != null)
			player.getDailyTask().incrementTask(player, 3,fish.getId(), Skills.FISHING);
		if (hasFishingSuit(player))
			totalXp *= 1.025;
		player.getSkills().addXp(Skills.FISHING, totalXp);
		if (fish.getId() == 20429) {
			player.setNextGraphics(new Graphics(2989));
			if (player.getEquipment().getGlovesId() != 15870) {
				player.applyHit(new Hit(player, 25, HitLook.REGULAR_DAMAGE, 0));
			}
			player.sm("You burned yourself while catching the shark.");
			player.LavaShark++;
			WildyHandler.generatePoints(player);
		}
		player.getAchievementManager().notifyUpdate(TheRealFishermanAchievement.class);
		if (fish.getId() == 15270)
			player.rockTails++;
		else if (fish.getId() == 7944)
			player.monkfish++;
		else if (fish.getId() == 383)
			player.sharks++;
		player.fished++;
		int bigFish = Misc.random(2000);
		if (bigFish == 1) {
			if (fish.getId() == 363)
				player.getInventory().addItem(7989, 1);
			else if (fish.getId() == 371)
				player.getInventory().addItem(7991, 1);
			else if (fish.getId() == 383)
				player.getInventory().addItem(7993, 1);
			player.sm("You catch a enormous fish!");
		} else
			player.getInventory().addItem(fish);
		player.getPackets().sendGameMessage(getMessage(fish), true);
		player.randomevent(player);
		if (player.getFamiliar() != null) {
			if (Utils.getRandom(50) == 0 && getSpecialFamiliarBonus(player.getFamiliar().getId()) > 0) {
				player.getInventory().addItem(new Item(BONUS_FISH[Utils.random(BONUS_FISH.length)]));
				player.getSkills().addXp(Skills.FISHING, 5.5);
			}
		}
		
		fishId = getRandomFish(player);
		if (Utils.getRandom(50) == 0 && FishingSpotsHandler.moveSpot(npc))
			player.setNextAnimation(new Animation(-1));
		if(player.getPerkHandler().perks.contains(Perk.CRYSTAL_KEY_PERK))
			player.getPerkHandler().handelKeys();
	}

	private boolean hasFishingSuit(Player player) {
		if (player.getEquipment().getHatId() == 24427 && player.getEquipment().getChestId() == 24428 && player.getEquipment().getLegsId() == 24429 && player.getEquipment().getBootsId() == 24430)
			return true;
		return false;
	}

	private String getMessage(Item fish) {
		if (spot.getFish()[fishId] == Fish.ANCHOVIES || spot.getFish()[fishId] == Fish.SHRIMP)
			return "You manage to catch some " + fish.getDefinitions().getName().toLowerCase() + ".";
		else if (multipleCatch)
			return "Your quick reactions allow you to catch two " + fish.getDefinitions().getName().toLowerCase() + ".";
		else
			return "You manage to catch a " + fish.getDefinitions().getName().toLowerCase() + ".";
	}

	private boolean checkAll(Player player) {
		//player.sm("check.");
		if (player.getSkills().getLevel(Skills.FISHING) < spot.getFish()[fishId].getLevel()) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need a fishing level of " + spot.getFish()[fishId].getLevel() + " to fish here.");
			return false;
		}
		if ((spot.getFish()[fishId] == Fish.TUNA || spot.getFish()[fishId] == Fish.SHARK || spot.getFish()[fishId] == Fish.SWORDFISH && !player.getInventory().containsOneItem(381))) {
			CatchSpots spot = CatchSpots.forId(npc.getId() | 1 << 24);
			if (spot != null && player.canBarbarianFish){
				player.getActionManager().setAction(new BarbarianFishing(spot, npc));
				return false;
			}
		}
		if (!player.getInventory().containsItemToolBelt(spot.getTool())) {
			player.getPackets().sendGameMessage("You need a " + new Item(spot.getTool()).getDefinitions().getName().toLowerCase() + " to fish here.");
			return false;
		}
		if (!player.getInventory().containsOneItem(spot.getBait()) && spot.getBait() != -1) {
			player.getPackets().sendGameMessage("You don't have " + new Item(spot.getBait()).getDefinitions().getName().toLowerCase() + " to fish here.");
			return false;
		}
		//player.sm("Check2");
		if (!player.getInventory().hasFreeSlots()) {
			player.setNextAnimation(new Animation(-1));
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have enough inventory space.");
			return false;
		}
		if (tile.getX() != npc.getX() || tile.getY() != npc.getY())
			return false;
		return true;
	}

	@Override
	public void stop(final Player player) {
		setActionDelay(player, 3);
	}
}
