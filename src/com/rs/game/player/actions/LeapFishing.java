package com.rs.game.player.actions;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public class LeapFishing extends Action {

	public enum Fish {


		LEAPING_TROUT(383, 76, 110,11,6705),
		LEAPING_SALMON(371, 50, 100,10,6707),
		LEAPING_STURGEON(359, 35, 80,8, 6711);

		private final int id, fishingLevel;
		private final double xp;
		private final double strengthXp;
		private int catchAnimation;

		private Fish(int id, int level, double xp, double strengthXp,int _catchAnimation) {
			this.id = id;
			this.fishingLevel = level;
			this.xp = xp;
			this.strengthXp = strengthXp;
			this.catchAnimation = _catchAnimation;
		}

		public int getId() {
			return id;
		}

		public int getLevel() {
			return fishingLevel;
		}

		public double getXp() {
			return xp;
		}
		public double getStrengthXp() {
			return strengthXp;
		}
		public int getCatchAnim() {
			return catchAnimation;
		}
	}

	public enum CatchSpots {
		

		SWORDFISH_SPOT(312, 2),
		SHARK_SPOT(313, 2 );
		
	

		private final Fish[] fish;
		private final int id, option;

		static final Map<Integer, CatchSpots> spot = new HashMap<Integer, CatchSpots>();

		public static CatchSpots forId(int id) {
			return spot.get(id);
		}

		static {
			for (CatchSpots spots : CatchSpots.values())
				spot.put(spots.id | spots.option << 24, spots);
		}

		private CatchSpots(int id, int option, Fish... fish) {
			this.id = id;
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

		
	}

	private CatchSpots spot;

	private NPC npc;
	private WorldTile tile;
	private int fishId;

	private final int[] BONUS_FISH = { 341, 349, 401, 407 };

	private boolean multipleCatch;
	
	public LeapFishing(CatchSpots spot, NPC npc) {
		this.spot = spot;
		this.npc = npc;
		tile = new WorldTile(npc);
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		fishId = getRandomFish(player);
		player.getPackets().sendGameMessage("You attempt to capture a fish...",  true);
		setActionDelay(player, getFishingDelay(player));
		return true;
	}

	@Override
	public boolean process(Player player) {
		player.setNextAnimationNoPriority(new Animation(9980));
		return checkAll(player);
	}

	private int getFishingDelay(Player player) {
		int playerLevel = player.getSkills().getLevel(Skills.FISHING);
		int fishLevel = spot.getFish()[fishId].getLevel();
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
		int difference = player.getSkills().getLevel(Skills.FISHING)
				- spot.getFish()[random].getLevel();
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
		Item fish = new Item(spot.getFish()[fishId].getId(), multipleCatch ? 2
				: 1);
		double totalXp = spot.getFish()[fishId].getXp();
		if (hasFishingSuit(player))
			totalXp *= 1.025;
		player.setNextAnimation(new Animation(spot.getFish()[fishId].getCatchAnim()));
		player.getSkills().addXp(Skills.FISHING, totalXp);
		player.getSkills().addXp(Skills.STRENGTH, spot.getFish()[fishId].getStrengthXp());
		player.getInventory().addItem(fish);
		player.getPackets().sendGameMessage(getMessage(fish),  true);
		player.randomevent(player);
		if (player.getFamiliar() != null) {
			if (Utils.getRandom(50) == 0 && getSpecialFamiliarBonus(player.getFamiliar().getId()) > 0) {
				player.getInventory().addItem(new Item(BONUS_FISH[Utils.random(BONUS_FISH.length)]));
				player.getSkills().addXp(Skills.FISHING, 5.5);
			}
		}
	}
	
	private boolean hasFishingSuit(Player player) {
		if (player.getEquipment().getHatId() == 24427 && player.getEquipment().getChestId() == 24428
				&& player.getEquipment().getLegsId() == 24429 && player.getEquipment().getBootsId() == 24430)
			return true;
		return false;
	}

	private String getMessage(Item fish) {
			return "You manage to catch a " + fish.getDefinitions().getName().toLowerCase() + ".";
	}

	private boolean checkAll(Player player) {
		if (player.getSkills().getLevel(Skills.FISHING) < spot.getFish()[fishId].getLevel()) {
			player.getDialogueManager().startDialogue("SimpleMessage","You need a fishing level of " + spot.getFish()[fishId].getLevel() + " to fish here.");
			return false;
		}
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
