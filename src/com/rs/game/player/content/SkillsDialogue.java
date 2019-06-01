package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ClientScriptMap;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.grandExchange.GrandExchange;
import com.rs.utils.ItemExamines;

public final class SkillsDialogue {

	public static final int MAKE = 0, MAKE_SETS = 1, COOK = 2, ROAST = 3,
			OFFER = 4, SELL = 5, BAKE = 6, CUT = 7, DEPOSIT = 8,
			MAKE_NO_ALL_NO_CUSTOM = 9, TELEPORT = 10, SELECT = 11, TAKE = 13;

	public static interface ItemNameFilter {

		public String rename(String name);
	}

	public static void sendSkillsDialogue(Player player, int option,
			String explanation, int maxQuantity, int[] items,
			ItemNameFilter filter) {
		sendSkillsDialogue(player, option, explanation, maxQuantity, items, filter, true);
	}
	
	public static void sendSkillsDialogue(Player player, int option,
			String explanation, int maxQuantity, int[] items,
			ItemNameFilter filter, boolean sendQuantitySelector) {
		player.getInterfaceManager().sendChatBoxInterface(905);
		player.getInterfaceManager().setInterface(true, 905, 4, 916);
		if(!sendQuantitySelector) {
			maxQuantity = -1;
			player.getPackets().sendHideIComponent(916, 4, true);
			player.getPackets().sendHideIComponent(916, 9, true);
		}else{
			if (option != MAKE_SETS && option != MAKE_NO_ALL_NO_CUSTOM)
				player.getPackets().sendUnlockIComponentOptionSlots(916, 8, -1, 0,
						0); // unlocks all option
		}
		player.getPackets().sendIComponentText(916, 1, explanation);
		player.getPackets().sendGlobalConfig(754, option);
		for (int i = 0; i < 10; i++) {
			if (i >= items.length) {
				player.getPackets().sendGlobalConfig(
						i >= 6 ? (1139 + i - 6) : 755 + i, -1);
				continue;
			}
			player.getPackets().sendGlobalConfig(
					i >= 6 ? (1139 + i - 6) : 755 + i, items[i]);
			String name = ItemDefinitions.getItemDefinitions(items[i])
					.getName();
			if (filter != null)
				name = filter.rename(name);
			player.getPackets().sendGlobalString(
					i >= 6 ? (280 + i - 6) : 132 + i, name);
		}
		setMaxQuantity(player, maxQuantity);
		setQuantity(player, maxQuantity);
		setItem(items);
	}

	public static void handleSetQuantityButtons(Player player, int componentId) {
		if (componentId == 5)
			setQuantity(player, 1, false);
		else if (componentId == 6)
			setQuantity(player, 5, false);
		else if (componentId == 7)
			setQuantity(player, 10, false);
		else if (componentId == 8)
			setQuantity(player, getMaxQuantity(player), false);
		else if (componentId == 19)
			setQuantity(player, getQuantity(player) + 1, false);
		else if (componentId == 20)
			setQuantity(player, getQuantity(player) - 1, false);
	}

	public static void setMaxQuantity(Player player, int maxQuantity) {
		player.getTemporaryAttributtes().put("SkillsDialogueMaxQuantity",
				maxQuantity);
		player.getPackets().sendConfigByFile(8094, maxQuantity);
	}

	public static void setQuantity(Player player, int quantity) {
		setQuantity(player, quantity, true);
	}

	public static void setQuantity(Player player, int quantity, boolean refresh) {
		int maxQuantity = getMaxQuantity(player);
		if (quantity > maxQuantity)
			quantity = maxQuantity;
		else if (quantity < 0)
			quantity = 0;
		player.getTemporaryAttributtes()
				.put("SkillsDialogueQuantity", quantity);
		if (refresh)
			player.getPackets().sendConfigByFile(8095, quantity);
	}

	public static int getMaxQuantity(Player player) {
		Integer maxQuantity = (Integer) player.getTemporaryAttributtes().get(
				"SkillsDialogueMaxQuantity");
		if (maxQuantity == null)
			return 0;
		return maxQuantity;
	}

	public static int getQuantity(Player player) {
		Integer quantity = (Integer) player.getTemporaryAttributtes().get(
				"SkillsDialogueQuantity");
		if (quantity == null)
			return 0;
		return quantity;
	}

	public static int getItemSlot(int componentId) {
		if (componentId < 14)
			return 0;
		return componentId - 14;
	}

	private SkillsDialogue() {

	}

	private static int[] items;

    public static void setItem(int... item) {
        items = item;
    }

    public static int getItem(int slot) {
        return items[slot];
    }

	public static void sendSkillDialogueByProduce(Player player, int produceId) {
		SkillDialogue d = findSkillDialogueByProduce(produceId);
		if(d == null) 
			return;		
		sendSkillDialogue(player, d, getCategoryWithProduce(d, produceId));
	}
	
	
	private static final int SKILL_VAR = 1168, CATEGORY_VAR = 1169, PRODUCT_VAR = 1170, MAX_QUANTITY_VAR_BIT = 1002, CURRENT_QUANTITY_VAR_BIT = 1003;
	private static void setCategory(Player player, SkillCategory sc) {
		player.getVarsManager().sendVar(CATEGORY_VAR, sc.getItemsCSMapId());
		int bestProduct = -1;
		l: for(int i = sc.getItems().length-1; i >= 0; i--) { //best product for lvl with material
			ItemDefinitions defs = ItemDefinitions.getItemDefinitions(sc.getItems()[i]);
			int skillId = ClientScriptMap.getMap(681).getIntValue(defs.getCSOpcode(2640));
			int level =  defs.getCSOpcode(2645);
			int multiplierQuantity = defs.getCSOpcode(2653);
			if((skillId == -1 || player.getSkills().getLevel(skillId) >= level)) {
				for(int i2 = 0; i2 < 3; i2++) {
					int material = defs.getCSOpcode(2655+i2);
					int amount = defs.getCSOpcode(2665+i2);
					if(material != 0 && !(player.getInventory().containsItem(material, amount * (multiplierQuantity == 0 ? 1 : multiplierQuantity))))
						continue l;
				}
				bestProduct = i;
				break;
			}
		}
		if(bestProduct == -1) { //best product for lvl if no mat
			bestProduct = 0;
			for(int i = sc.getItems().length-1; i >= 0; i--) {
				ItemDefinitions defs = ItemDefinitions.getItemDefinitions(sc.getItems()[i]);
				int skillId = ClientScriptMap.getMap(681).getIntValue(defs.getCSOpcode(2640));
				int level =  defs.getCSOpcode(2645);
				if(skillId == -1 || player.getSkills().getLevel(skillId) >= level) {
					bestProduct = i;
					break;
				}
			}
		}
		setProduct(player, sc.getItems()[bestProduct]);
	}
	
	private static void setProduct(Player player, int result) {
		player.getVarsManager().sendVar(PRODUCT_VAR, result);
		setMaxQuantity(player, result);
		Item item = new Item(result, 1);
		player.getPackets().sendGlobalString(2391, ItemExamines.getExamine(item));
		boolean tradeable = ItemConstants.isTradeable(item);
		player.getPackets().sendGlobalConfig(2223, tradeable ? 1 : 0);
		if(tradeable)
			player.getPackets().sendGlobalConfig(2224, GrandExchange.getPrice(result));
	}
	
	public static void sendSkillDialogue(final Player player, SkillDialogue sd, SkillCategory sc) {
		if(sc == null) 
			sc = sd.categories[0];
		player.getVarsManager().sendVar(SKILL_VAR, sd.menuCSMapId);
		setCategory(player, sc);
		player.getPackets().sendGlobalConfig(2222, sd.menuNamesCSMapId);
		player.getInterfaceManager().sendInterface(1370);
		player.getInterfaceManager().setInterface(true, 1370, 62, 1371);
		player.getPackets().sendIComponentSettings(1371, 62, 0, sd.categories.length, 2);
		player.getPackets().sendIComponentSettings(1371, 44, 0, 500, 2);
		player.getPackets().sendIComponentSettings(1371, 36, 0, 1, 2359296);
		player.getPackets().sendIComponentSettings(1371, 143, 0, 1, 2);
		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				player.getVarsManager().sendVar(SKILL_VAR, -1);
				player.getVarsManager().sendVar(CATEGORY_VAR, -1);
				player.getVarsManager().sendVar(PRODUCT_VAR, -1);
				player.getVarsManager().sendVarBit(MAX_QUANTITY_VAR_BIT, 0);
				player.getVarsManager().sendVarBit(CURRENT_QUANTITY_VAR_BIT, 0);
			}
		});

	}
	
	private static final Map<Integer, SkillDialogue> skillDialogues = new HashMap<Integer, SkillDialogue>();
	
	private static int[] SKILL_DIALOGUES = {-1, 5773, 6675, 6780, 6784, 6794, 6803, 6809, 6821, 6823, 6832, 6838, 6848, 6852, 6853, 6861, 6869, 6877, 6879, 6894, 6896, 6919, 6939, 6941, 6943, 6945, 6981, 6987, 7004, 7006, 7008, 7010, 7012, 7042, 7051, 7057, 7059, 7061, 7079, 7081, 7094, 7113, 7551, 7800, 7812};
	
	public static SkillDialogue getSkillDialogue(int id) {
		SkillDialogue d = skillDialogues.get(id);
		if(d == null) 
			skillDialogues.put(id, d = new SkillDialogue(id));
		return d;
	}
	
	public static SkillDialogue findSkillDialogueByProduce(int produceId) {
		for(int i = 0; i < SKILL_DIALOGUES.length; i++) {
			SkillDialogue d = getSkillDialogue(SKILL_DIALOGUES[i]);
			for(SkillCategory c : d.categories) {
				for(int id : c.getItems())
					if(produceId == id) {
						//System.out.println("here you go "+d.menuCSMapId+", "+c.itemsCSMapId);
						return d;
					}
			}
		}
		return null;
	}
	
	private static SkillCategory getCategoryWithProduce(SkillDialogue sd, int produceId) {
		for(SkillCategory sc : sd.categories) {
			for(int id : sc.getItems()) {
				if(produceId == id)
					return sc;
			}
		}
		return null;
	}
	
	private static int[] NO_MENU_CATEGORIES = {7050, 7048, 7049, 7046, 7047, 7070, 7069, 7065, 7064, 7063, 7077, 7078, 7103, 7102, 7096, 7093, 7092, 7106, 8076, 7104, 7105, 7556, 8002, 6921, 6934, 6935, 6938, 6936, 6937, 7512, 6972, 6973, 6974, 6975, 6969, 6970, 6971, 6979, 6977, 6976, 6980, 6986, 6990, 6989, 8858, 8856, 8857, 8854, 8855, 8853, 6808, 6840, 6830, 6831, 6829, 6868, 6898, 6674, 7278, 7277, 7276, 7275, 8697, 6770, 6771, 6768, 6769, 6774, 6775, 6772, 6773, 6778, 6779, 6776, 6777, 6762, 6761, 6767, 6766, 6765, 6764};
	
	
public static class SkillDialogue {
		
		private int menuCSMapId, menuNamesCSMapId;
		private SkillCategory[] categories;
		
		private SkillDialogue(int id) {
			menuCSMapId = id;

			if(id == -1) {
				menuNamesCSMapId = -1;
				categories = new SkillCategory[NO_MENU_CATEGORIES.length];
				for(int i = 0; i < categories.length; i++) 
					categories[i] = new SkillCategory(NO_MENU_CATEGORIES[i]);
			}else{
				menuNamesCSMapId = id+ (menuCSMapId == 6852 || menuCSMapId == 6853 || menuCSMapId == 7551 ? 2 : 1) ;
				ClientScriptMap csmap = ClientScriptMap.getMap(id);
				categories = new SkillCategory[csmap.getSize()];
				for(int i = 0; i < categories.length; i++) 
					categories[i] = new SkillCategory(csmap.getIntValue(i));
			}
		}
		
		public SkillCategory getCategory(int id) {
			for(SkillCategory c : categories)
				if(c.getItemsCSMapId() == id)
					return c;
			return null;
		}
		
		public SkillCategory[] getCategorys() {
			return categories;
		}
	}

public static class SkillCategory {
	
	private int itemsCSMapId;
	private int categorySpriteId;
	private int[] items;
	
	private SkillCategory(int id) {
		setItemsCSMapId(id);
		categorySpriteId = ClientScriptMap.getMap(6817).getIntValue(id);
		ClientScriptMap csmap = ClientScriptMap.getMap(id);
		setItems(new int[csmap.getSize()]);
		for(int i = 0; i < getItems().length; i++) 
			getItems()[i] = csmap.getIntValue(i);
	}

	public int[] getItems() {
		return items;
	}

	public void setItems(int[] items) {
		this.items = items;
	}

	public int getItemsCSMapId() {
		return itemsCSMapId;
	}

	public void setItemsCSMapId(int itemsCSMapId) {
		this.itemsCSMapId = itemsCSMapId;
	}
}
}
