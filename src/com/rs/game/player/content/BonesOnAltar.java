package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;

@SuppressWarnings("unused")
public class BonesOnAltar extends Action {
	
	public final String MESSAGE = "The gods are very pleased with your offerings.";
	public final double MULTIPLIER = 3.5;
	
	public enum Bones {
		BONES(new Item (526, 1), 4.5),//1000),
		WOLF_BONES(new Item (2859, 1), 4.5),//850),
		BURNT_BONES(new Item (528, 1), 4.5),//1000)
		MONKEY_BONES(new Item (3183, 1), 5),//1250),
		JOGRE_BONES(new Item (3125, 1), 15),//2000),
		ZOGRE_BONES(new Item (4812, 1),22.5),//2500),
		SHAIKAHAN_BONES(new Item (3123, 1), 25),//3000),
		FAYRG_BONES(new Item (4830, 1), 84),//5250),
		RAURG_BONES(new Item (4832, 1), 96),//5500),
		BAT_BONES(new Item (530, 1), 5.3),//850),
		BIG_BONES(new Item (532, 1), 15),//2500),
		BABYDRAGON_BONES(new Item (534, 1), 30),//2500),
		WYVERN_BONES(new Item (6812, 1), 50),//7000),
		DRAGON_BONES(new Item (536, 1), 72),//7000),
		OURG_BONES(new Item (4834, 1), 140),//9000),
		FROST_DRAGON_BONES(new Item (18830, 1), 180),//11500),
		DAGANNOTH_BONES(new Item (6729, 1), 125),//9000),
		SEARING_ASHES(new Item (34159, 1), 200),//15000);
		IMPIOUS_ASHES(new Item (20264, 1), 16),
		ACCURSED_ASHES(new Item (20266, 1), 12.5),
		INFERNAL_ASHES(new Item (20268, 1), 62.5),
		TORTURED_ASHES(new Item (32945, 1), 90),
		AIRUT_BONES(new Item (30209, 1), 132.5),
		ADAMANT_BONES(new Item (35008, 1), 190),
		ANCIENT_ASHES(new Item(1502, 1), 197);
	
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
		private double xp;
	
		private Bones(Item item, double xp) {
			this.item = item;
			this.xp = xp;
		}
		
		public Item getBone() {
			return item;
		}
		
		public double getXP() {
			return xp;
		}
	}
		
	private Bones bone;
//	/private int amount;
	private Item item;
	private WorldObject object;
	private Animation USING = new Animation(24897);
	
	public BonesOnAltar(WorldObject object, Item item) {
		//this.amount = amount;
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
		/*if (!World.getRegion(object.getRegionId()).containsObjects(
						object.getId(), object))
			return false;*/
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
			player.getPackets().sendGraphics(new Graphics(624), object);
			player.getInventory().deleteItem(item.getId(), 1);
			player.getSkills().addXp(Skills.PRAYER, bone.getXP()*MULTIPLIER);
			player.getPackets().sendGameMessage(MESSAGE);
			player.getInventory().refresh();
			return 3;
	}

	@Override
	public void stop(final Player player) {
		this.setActionDelay(player, 3);
	}
	
}