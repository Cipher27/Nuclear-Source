package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.impl.PyromanicAchievement;
import com.rs.game.player.actions.Action;

/**
  *  @Paolo, crematation
  **/

@SuppressWarnings("unused")
public class BonesOnFire extends Action {
	
	public final String MESSAGE = "The gods are very pleased with your offerings.";
	
	public enum Bones {
		
		BONES(new Item (526, 1), 11.2, 9),
		WOLF_BONES(new Item (2859, 1), 11.2, 9),
		BURNT_BONES(new Item (528, 1), 11.2, 9),
		MONKEY_BONES(new Item (3183, 1), 12.5, 10),
		BAT_BONES(new Item (530, 1), 13.2,10.6),
		JOGRE_BONES(new Item (3125, 1), 37.5,30),
		ZOGRE_BONES(new Item (4812, 1),56.2,45),
		SHAIKAHAN_BONES(new Item (3123, 1), 62.5,50),
		FAYRG_BONES(new Item (4830, 1), 210,168),
		RAURG_BONES(new Item (4832, 1), 240,192),
		BIG_BONES(new Item (532, 1), 37.5,30),
		BABYDRAGON_BONES(new Item (534, 1), 75,60),
		WYVERN_BONES(new Item (6812, 1),125,100),
		DRAGON_BONES(new Item (536, 1), 180,144),
		OURG_BONES(new Item (4834, 1), 350,280),
		FROST_DRAGON_BONES(new Item (18830, 1), 450,360),
		DAGANNOTH_BONES(new Item (6729, 1),312.5,250),
		AIRUT_BONES(new Item (30209, 1), 331.2,265);
	
		private static Map<Short, Bones> bones = new HashMap<Short, Bones>();
	
		public static Bones forId(short itemId) {
			return bones.get(itemId);
		}
	
		static {
			for (Bones bone: Bones.values()) {
				bones.put((short) bone.getBone().getId(), bone);
			}
		}
	
		private Item item;
		private double prayerExp;
		private double firemakingExp;
	
		private Bones(Item item, double prayerExp, double firemakingExp) {
			this.item = item;
			this.prayerExp = prayerExp;
			this.firemakingExp = firemakingExp;
		}
		
		public Item getBone() {
			return item;
		}
		
		public double getprayerExp() {
			return prayerExp;
		}
		public double getfiremakingExp() {
			return firemakingExp;
		}
	}
		
	private Bones bone;
	private int amount;
	private Item item;
	private WorldObject object;
	private Animation USING = new Animation(897);
	
	public BonesOnFire(WorldObject object, Item item, int amount) {
		this.amount = amount;
		this.item = item;
		this.object = object;
	}
	
	public static Bones isGood(Item item) {
		return Bones.forId((short) item.getId());
	}
	
	@Override
	public boolean start(Player player) {
		if((this.bone = Bones.forId((short) item.getId())) == null) {
			return false;
		}
		player.faceObject(object);
		return true;
	}
	
	@Override
	public boolean process(Player player) {
		if (!player.getInventory().containsItem(item.getId(), 1)) {
			return false;
		}
		if (!player.getInventory().containsItem(bone.getBone().getId(), 1)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int processWithDelay(Player player) {
			player.setNextAnimation(USING);
			player.getInventory().deleteItem(item.getId(), 1);
			player.getAchievementManager().notifyUpdate(PyromanicAchievement.class);
			player.getSkills().addXp(Skills.PRAYER, bone.getprayerExp());
			player.getSkills().addXp(Skills.FIREMAKING, bone.getfiremakingExp());
			player.getPackets().sendGameMessage(MESSAGE);
			player.getInventory().refresh();
			return 3;
	}

	@Override
	public void stop(final Player player) {
		this.setActionDelay(player, 3);
	}
	
}