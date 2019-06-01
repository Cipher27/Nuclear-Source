package com.rs.game.player.actions;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author Hc747 / harrison - Hyperion-rsps.co.uk
 *
 */

public class CrystalRobustGlassMachine extends Action {
	public static final int MACHINE = 67968;
	//public static final WorldObject object = new WorldObject(MACHINE, 10, 0, new WorldTile(2608, 3097, 0));
	public void sendUseMachine(Player player, WorldObject object) {
		player.getPackets().sendObjectAnimation(object, new Animation(1));
	}

	public final Animation LOADING_ANIMATION = new Animation(8528);
	public static final Item SANDSTONE = new Item(32847, 1);
	public static final Item CRYSTAL_GLASS = new Item(32845, 1);
	private int quantity;
	private MachineData2 data;

	public static final int INGREDIENTS[] = { 32847 };

	public static final int PRODUCTS[][] = { 
		{32845}
		};

	public enum MachineData2 {
		ROBUST_GLASS(32847, 1, 32845, 150)
		;

		private int ingredientId, ingredientAmount, finalProduct;
		private double experience;
		private String name;

		private static Map<Integer, MachineData2> MachineItems = new HashMap<Integer, MachineData2>();

		public static MachineData2 forId(int id) {
			return MachineItems.get(id);
		}

		static {
			for (MachineData2 machine : MachineData2.values()) {
				MachineItems.put(machine.finalProduct, machine);
			}
		}

		private MachineData2(int ingredientId, int ingredientAmount, int finalProduct, double experience) {
			this.ingredientId = ingredientId;
			this.ingredientAmount = ingredientAmount;
			this.finalProduct = finalProduct;
			this.experience = experience;
			this.name = ItemDefinitions.getItemDefinitions(getFinalProduct())
					.getName();
		}

		public int getIngredientId() {
			return ingredientId;
		}

		public int getIngredientAmount() {
			return ingredientAmount;
		}

		public int getFinalProduct() {
			return finalProduct;
		}

		public double getExperience() {
			return experience;
		}

		public String getName() {
			return name;
		}
	}
	
	public static WorldObject objects = null;

	public static boolean handleItemOnObject(Player player, Item item,
			WorldObject object) {
		for (int i = 0; i < INGREDIENTS.length; i++) {
			if (item.getId() == INGREDIENTS[i]
					&& object.getId() == MACHINE) {
				objects = object;
				if (objects == null)
					return false;
				player.getTemporaryAttributtes().put("GlassType", INGREDIENTS[i]);
				int index = getIndex(player);
				if (index == -1)
					return true;
				int glass = (Integer) player.getTemporaryAttributtes().get(
						"GlassType");
				if (glass == INGREDIENTS[0]) {
				player.getDialogueManager().startDialogue("CrystalRobustGlassMachineD",
						MachineData2.forId(PRODUCTS[index][0]));
				return true;
				}
			}
		}
		return false;
	}

	public static int getIndex(Player player) {
		int glass = (Integer) player.getTemporaryAttributtes().get(
				"GlassType");
		if (glass == INGREDIENTS[0])
			return 0;
		return -1;
	}

	public CrystalRobustGlassMachine(MachineData2 data, int quantity) {
		this.data = data;
		this.quantity = quantity;
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		player.setNextAnimation(LOADING_ANIMATION);
		setActionDelay(player, 7);
		return true;
	}

	private boolean checkAll(Player player) {
		if (player.getInventory().getItems().getNumberOf(data.getIngredientId()) < data
				.getIngredientAmount()) {
			player.getPackets().sendGameMessage(
					"You don't have enough "+ ItemDefinitions
					.getItemDefinitions(data.getIngredientId())
					.getName().toLowerCase() +" to do make any "
					+ItemDefinitions.getItemDefinitions(data.getFinalProduct())
					.getName().toLowerCase()+
					".");
			return false;
		}
		if (!player.getInventory().containsOneItem(data.getIngredientId())) {
			player.getPackets().sendGameMessage(
					"You've ran out of "
							+ ItemDefinitions
							.getItemDefinitions(data.getIngredientId())
							.getName().toLowerCase() + " blocks.");
			return false;
		}
		return true;
	}

	@Override
	public boolean process(Player player) {
		return checkAll(player);
	}

	@Override
	public int processWithDelay(Player player) {
		player.setNextAnimation(LOADING_ANIMATION);
		player.getInventory().deleteItem(data.getIngredientId(),
				data.getIngredientAmount());
		player.getInventory().addItem(data.getFinalProduct(), 1);
		player.getSkills().addXp(Skills.CRAFTING,
				data.getExperience());
		player.getPackets().sendGameMessage(
				"The machine converts the "+ItemDefinitions.getItemDefinitions(data.getIngredientId()).getName().toLowerCase()+ " into a piece of "
		+ ItemDefinitions.getItemDefinitions(data.getFinalProduct())
				.getName().toLowerCase() + ".", true);
		quantity--;
		if (quantity <= 0)
			return -1;
		if (objects == null)
			return -1;
		sendUseMachine(player, objects);
		stop(player);
		return 0;
	}

	@Override
	public void stop(final Player player) {
		setActionDelay(player, 3);
	}

}
