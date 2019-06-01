package com.rs.game.player.content;

import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.RunespanControler;
import com.rs.utils.Colors;
import com.rs.utils.ItemExamines;
import com.rs.utils.ItemSetsKeyGenerator;
import com.rs.utils.Utils;

public class Shop {

    private static final int MAIN_STOCK_ITEMS_KEY = ItemSetsKeyGenerator.generateKey();

    private static final int MAX_SHOP_ITEMS = 40;
    public static final int COINS = 995, TOKKUL = 6529;
	
	public static int[][] auraShops = { { 22889, 200 }, { 22895, 200 },
			// Tier 1
			{ 22897, 200 }, { 20966, 200 }, { 22905, 200 }, { 22891, 200 }, { 22294, 200 }, { 23848, 200 },
			{ 22296, 200 }, { 22280, 200 }, { 22300, 200 }, { 20958, 200 }, { 22284, 200 }, { 22893, 200 },
			{ 22292, 200 }, { 20965, 200 }, { 20962, 200 }, { 22899, 200 }, { 20967, 200 }, { 20964, 200 },
			{ 22927, 200 }, { 22298, 200 }, { 30784, 200 },
			// Tier 2
			{ 22268, 400 }, { 22270, 400 }, { 22274, 400 }, { 22276, 400 }, { 22278, 400 }, { 22282, 400 },
			{ 22286, 400 }, { 22290, 400 }, { 22885, 400 }, { 22901, 400 }, { 22907, 400 }, { 22929, 400 },
			{ 23842, 400 }, { 23850, 400 }, { 22302, 400 }, { 22272, 400 }, { 30786, 400 },
			// Tier 3
			{ 22887, 800 }, { 22903, 800 }, { 22909, 800 }, { 22911, 800 }, { 22917, 800 }, { 22919, 800 },
			{ 22921, 800 }, { 22923, 800 }, { 22925, 800 }, { 22931, 800 }, { 22933, 800 }, { 23844, 800 },
			{ 23852, 800 }, { 22913, 800 }, { 22915, 800 }, { 30788, 800 },
			// Tier 4
			{ 23854, 1300 }, { 23856, 1300 }, { 23858, 1300 }, { 23860, 1300 }, { 23862, 1300 }, { 23864, 1300 },
			{ 23866, 1300 }, { 23868, 1300 }, { 23870, 1300 }, { 23872, 1300 }, { 23874, 1300 }, { 23876, 1300 },
			{ 23878, 1300 }, { 30790, 1300 },
			// Tier 5
			{ 30792, 2600 }, { 30794, 2600 }, { 30796, 2600 }, { 30798, 2600 }, { 30800, 2600 }, { 30802, 2600 },
			{ 30804, 2600 }, };
	
	public static int[][] powershop = 
	{{31455, 25000},{31453,15000}, 
	{34928,42000}, {34927, 10000}, //lootboxes
	{27996,25000},//charm imp
	{31612,25000}, {31613, 50},//springcleaner
	{28128, 20000},{28129, 20000},{28130, 20000},{28131, 20000},//soulstones
	{30372, 50},//notepaper
	{31350, 100},{33740,100}, {34528, 100},//protean stuff
	{25205, 5000}, //bank
	{25304, 5000}, {25310, 5000}, {25300,500},
	{33294,100000},{33296,125000},{33298,150000},//dyes
	{30920,25000},{30915,50}
	
	};
	
	public static int[][] dungShop = { { 18349, 50000 }, { 18351, 50000 }, { 18353, 50000 }, { 18355, 50000 },
			{ 18357, 50000 }, { 18359, 50000 }, { 18361, 50000 }, { 19669, 50000 }, { 18335, 50000 }, { 18346, 50000 },
			{ 18347, 50000 }, { 18839, 50000 }, { 18343, 50000 }, { 18344, 50000 },

			{ 18363, 50000 }, { 19893, 50000 }, { 19675, 50000 }, { 19892, 50000 }, { 18340, 50000 }, { 18337, 100000 },

			{ 15584, 100000 }, { 15585, 50000 } };
	
	public static int[][] runespanStore = {{13599,100},{13600,100},{13601,100},{13602,100},{13603,100},{13604,100},{13605,100},{13606,100},{13607,100},{13608,100},{13609,100},{13610,100},{13611,100}, //tabs
	{5509,3000},{5510,6000},{5512,12000},{5514,20000}}; //pouches
	
	public static int[][] riddleStore = {{6857,100}, {6863 ,100},{6861,100} ,{6859,100}, {28597,5000}};
	
	public static int[][] lividStore = {{31186,500},{30770,2000},{5501, 5000}, {5502,7500}, {26947,4000},{26948,5500},{5304,2500}}; //pouches

    private String name;
    private Item[] mainStock;
    private int[] defaultQuantity;
    private Item[] generalStock;
    private int money;
    private CopyOnWriteArrayList<Player> viewingPlayers;

    public Shop(String name, int money, Item[] mainStock, boolean isGeneralStore) {
	viewingPlayers = new CopyOnWriteArrayList<Player>();
	this.name = name;
	this.money = money;
	this.mainStock = mainStock;
	defaultQuantity = new int[mainStock.length];
	for (int i = 0; i < defaultQuantity.length; i++)
	    defaultQuantity[i] = mainStock[i].getAmount();
	if (isGeneralStore && mainStock.length < MAX_SHOP_ITEMS)
	    generalStock = new Item[MAX_SHOP_ITEMS - mainStock.length];
    }

    public boolean isGeneralStore() {
	return generalStock != null;
    }

    public void addPlayer(final Player player) {
	viewingPlayers.add(player);
	player.getTemporaryAttributtes().put("Shop", this);
	player.setCloseInterfacesEvent(new Runnable() {
	    @Override
	    public void run() {
		viewingPlayers.remove(player);
		player.getTemporaryAttributtes().remove("Shop");
		player.getTemporaryAttributtes().remove("shop_transaction");
		player.getTemporaryAttributtes().remove("isShopBuying");
		player.getTemporaryAttributtes().remove("ShopSelectedSlot");
		player.getTemporaryAttributtes().remove("ShopSelectedInventory");
	    }
	});
	player.refreshVerboseShopDisplayMode();
	player.getVarsManager().sendVar(118, generalStock != null ? 139 : MAIN_STOCK_ITEMS_KEY); 
	player.getVarsManager().sendVar(1496, -1); // sample items container id (TODO: add support for it)
	player.getVarsManager().sendVar(532, money);
	resetSelected(player);
	sendStore(player);
	player.getInterfaceManager().sendInterface(1265); // opens shop
	resetTransaction(player);
	setBuying(player, true);
	if (generalStock != null)
	    player.getPackets().sendHideIComponent(1265, 19, false); // unlocks general store icon
	player.getPackets().sendIComponentSettings(1265, 20, 0, getStoreSize(), 1150); // unlocks stock slots
	sendInventory(player);
	player.getPackets().sendIComponentText(1265, 85, name);
    }

    public void resetTransaction(Player player) {
	setTransaction(player, 1);
    }

    public void increaseTransaction(Player player, int amount) {
	setTransaction(player, getTransaction(player) + amount);
    }

    public int getTransaction(Player player) {
	Integer transaction = (Integer) player.getTemporaryAttributtes().get("shop_transaction");
	return transaction == null ? 1 : transaction;
    }

    public void pay(Player player) {
	Integer selectedSlot = (Integer) player.getTemporaryAttributtes().get("ShopSelectedSlot");
	Boolean inventory = (Boolean) player.getTemporaryAttributtes().get("ShopSelectedInventory");
	if (selectedSlot == null || inventory == null)
	    return;
	int amount = getTransaction(player);
	if (inventory)
	    sell(player, selectedSlot, amount);
	else
	    buy(player, selectedSlot, amount);
    }

    public int getSelectedMaxAmount(Player player) {
	Integer selectedSlot = (Integer) player.getTemporaryAttributtes().get("ShopSelectedSlot");
	Boolean inventory = (Boolean) player.getTemporaryAttributtes().get("ShopSelectedInventory");
	if (selectedSlot == null || inventory == null)
	    return 1;
	if (inventory) {
	    Item item = player.getInventory().getItem(selectedSlot);
	    if (item == null)
		return 1;
	    return player.getInventory().getAmountOf(item.getId());
	} else {
	    if (selectedSlot >= getStoreSize())
		return 1;
	    Item item = selectedSlot >= mainStock.length ? generalStock[selectedSlot - mainStock.length] : mainStock[selectedSlot];
	    if (item == null)
		return 1;
	    return item.getAmount();
	}
    }

    public void setTransaction(Player player, int amount) {
	int max = getSelectedMaxAmount(player);
	if (amount > max)
	    amount = max;
	else if (amount < 1)
	    amount = 1;
	player.getTemporaryAttributtes().put("shop_transaction", amount);
	player.getVarsManager().sendVar(2564, amount);
    }

    public static void setBuying(Player player, boolean buying) {
	player.getTemporaryAttributtes().put("isShopBuying", buying);
	player.getVarsManager().sendVar(2565, buying ? 0 : 1);
    }

    public static boolean isBuying(Player player) {
	Boolean isBuying = (Boolean) player.getTemporaryAttributtes().get("isShopBuying");
	return isBuying != null && isBuying;
    }

    public void sendInventory(Player player) {
	player.getInterfaceManager().sendInventoryInterface(1266);
	player.getPackets().sendItems(93, player.getInventory().getItems());
	player.getPackets().sendUnlockIComponentOptionSlots(1266, 0, 0, 27, 0, 1, 2, 3, 4, 5);
	player.getPackets().sendInterSetItemsOptionsScript(1266, 0, 93, 4, 7, "Value", "Sell 1", "Sell 5", "Sell 10", "Sell 50", "Examine");
    }

    public void buyAll(Player player, int slotId) {
	if (slotId >= getStoreSize())
	    return;
	Item item = slotId >= mainStock.length ? generalStock[slotId - mainStock.length] : mainStock[slotId];
	buy(player, slotId, item.getAmount());
    }

	/**
	* buying method
	**/
    public void buy(Player player, int slotId, int quantity) {
	if (slotId >= getStoreSize())
	    return;
	Item item = slotId >= mainStock.length ? generalStock[slotId - mainStock.length] : mainStock[slotId];
	if (item == null)
	    return;
	if (item.getAmount() == 0) {
	    player.getPackets().sendGameMessage("There is no stock of that item at the moment.");
	    return;
	}
	int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
	int price = item.getDefinitions().getValue();
		if (price < 1)
			price = 1;
	int amountCoins = money == COINS ? player.getInventory().getCoinsAmount() : player.getInventory().getItems().getNumberOf(money);
	int maxQuantity = amountCoins / price;
	int buyQ = item.getAmount() > quantity ? quantity : item.getAmount();
	if (money != 995) {
	for (int i11 = 0; i11 < dungShop.length; i11++) {
				if (item.getId() == dungShop[i11][0] && name.contains("Dungeoneering")) {
					if (player.getPointsManager().getDungeoneeringTokens() < dungShop[i11][1] * quantity) {
						player.sendMessage("You need: " + Colors.red + Utils.getFormattedNumber(dungShop[i11][1])
								+ "</col> dungeoneering tokens to buy " + Colors.red + Utils.formatAorAn(item.getName())
								+ " " + item.getName() + "</col>.");
						return;
					} else {
						player.sendMessage("You have bought " + Colors.red + Utils.formatAorAn(item.getName()) + " "
								+ item.getName() + "</col> from the " + name + ".");
						player.getPackets().sendMusicEffect(28);
						player.getInventory().addItem(dungShop[i11][0], 1);
						player.getPointsManager().setDungeoneeringTokens(player.getPointsManager().getDungeoneeringTokens() - dungShop[i11][1]);
						player.getPackets().sendMusicEffect(28);
						item.setAmount(item.getAmount() - 1);
						refreshShop();
						sendInventory(player);
						return;
					}
				}
			}
	for (int i11 = 0; i11 < auraShops.length; i11++) {
	
				if (item.getId() == auraShops[i11][0] && name.contains("Aura")) {
					if (player.getPointsManager().getLoyaltyTokens() < auraShops[i11][1] * quantity) {
						player.sendMessage(
								"You need: " + Colors.red + Utils.getFormattedNumber(auraShops[i11][1])
										+ "</col> loyalty points to buy " + Colors.red + Utils.formatAorAn(item.getName())
										+ " " + item.getName() + "</col>, you "
										+ (((player.getPointsManager().getLoyaltyTokens() == 0) ? "have 0."
												: "only have: " + Colors.red
														+ Utils.getFormattedNumber(player.getPointsManager().getLoyaltyTokens())+ "</col>.")));
						return;
					} else {
						player.sendMessage("You have bought " + Colors.red + Utils.formatAorAn(item.getName())
								+ item.getName() + " " + "</col>from the Aura shop!");
						player.getInventory().addItem(auraShops[i11][0], 1);
						player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - auraShops[i11][1]);
						player.getPackets().sendMusicEffect(28);
						item.setAmount(item.getAmount() - 1);
						refreshShop();
						sendInventory(player);
						return;
					}
				}
			}	
	
	for (int i11 = 0; i11 < runespanStore.length; i11++) {
				if (item.getId() == runespanStore[i11][0] && name.contains("Runespan")) {
					if (player.getPointsManager().runespanStore < runespanStore[i11][1] * quantity) {
						player.sendMessage(
								"You need: " + Colors.red + Utils.getFormattedNumber(runespanStore[i11][1] * quantity)
										+ "</col> Runespan points to buy "+quantity+"x " + Colors.red + Utils.formatAorAn(item.getName())
										+ " " + item.getName() + "</col>, you "
										+ (((player.getPointsManager().runespanStore == 0) ? "have 0."
												: "only have: " + Colors.red
														+ Utils.getFormattedNumber(player.getPointsManager().runespanStore)+ "</col>.")));
						return;
					} else {
						player.sendMessage("You have bought "+quantity+"x " + Colors.red + Utils.formatAorAn(item.getName())
								+ item.getName() + " " + "</col>from the Runespa, shop!");
						player.getInventory().addItem(runespanStore[i11][0], quantity);
						player.getPointsManager().runespanStore = (player.getPointsManager().runespanStore - runespanStore[i11][1]* quantity);
						((RunespanControler) player.getControlerManager().getControler()).refreshInventoryPoints();
						player.getPackets().sendMusicEffect(28);
						refreshShop();
						sendInventory(player);
						return;
					}
				}
			}	
	for (int i11 = 0; i11 < riddleStore.length; i11++) {
				if (item.getId() == riddleStore[i11][0] && name.contains("Riddle")) {
					if (player.getPointsManager().getRiddelTokens() < riddleStore[i11][1] * quantity) {
						player.sendMessage(
								"You need: " + Colors.red + Utils.getFormattedNumber(riddleStore[i11][1] * quantity)
										+ "</col> Riddle points to buy "+quantity+"x " + Colors.red + Utils.formatAorAn(item.getName())
										+ " " + item.getName() + "</col>, you "
										+ (((player.getPointsManager().getRiddelTokens() == 0) ? "have 0."
												: "only have: " + Colors.red
														+ Utils.getFormattedNumber(player.getPointsManager().getRiddelTokens())+ "</col>.")));
						return;
					} else {
						player.sendMessage("You have bought "+quantity+"x " + Colors.red + Utils.formatAorAn(item.getName())
								+ item.getName() + " " + "</col>from the Runespa, shop!");
						player.getInventory().addItem(riddleStore[i11][0], quantity);
						player.getPointsManager().setRiddelTokens((player.getPointsManager().getRiddelTokens() - riddleStore[i11][1]* quantity));
						player.getPackets().sendMusicEffect(28);
						refreshShop();
						sendInventory(player);
						return;
					}
				}
			}
	for (int i11 = 0; i11 < lividStore.length; i11++) {
				if (item.getId() == lividStore[i11][0] && name.contains("Livid")) {
					if (player.getPointsManager().getLividPoints() < lividStore[i11][1] * quantity) {
						player.sendMessage(
								"You need: " + Colors.red + Utils.getFormattedNumber(lividStore[i11][1] * quantity)
										+ "</col> Livid points to buy "+quantity+"x " + Colors.red + Utils.formatAorAn(item.getName())
										+ " " + item.getName() + "</col>, you "
										+ (((player.getPointsManager().getLividPoints() == 0) ? "have 0."
												: "only have: " + Colors.red
														+ Utils.getFormattedNumber(player.getPointsManager().getLividPoints())+ "</col>.")));
						return;
					} else {
						player.sendMessage("You have bought "+quantity+"x " + Colors.red + Utils.formatAorAn(item.getName())
								+ item.getName() + " " + "</col>from the Livid shop!");
						player.getInventory().addItem(lividStore[i11][0], quantity);
						player.getPointsManager().removeLividPoint(lividStore[i11][1]* quantity);
						player.getPackets().sendMusicEffect(28);
						refreshShop();
						sendInventory(player);
						return;
					}
				}
			}	
	for (int i11 = 0; i11 < powershop.length; i11++) {
				if (item.getId() == powershop[i11][0] && name.contains("Power")) {
					if (player.getPointsManager().getPowerTokens() < powershop[i11][1] * quantity) {
						player.sendMessage(
								"You need: " + Colors.red + Utils.getFormattedNumber(powershop[i11][1] * quantity)
										+ "</col> power tokens to buy "+quantity+"x " + Colors.red + Utils.formatAorAn(item.getName())
										+ " " + item.getName() + "</col>, you "
										+ (((player.getPointsManager().getLoyaltyTokens() == 0) ? "have 0."
												: "only have: " + Colors.red
														+ Utils.getFormattedNumber(player.getPointsManager().getPowerTokens())+ "</col>.")));
						return;
					} else {
						player.sendMessage("You have bought "+quantity+"x " + Colors.red + Utils.formatAorAn(item.getName())
								+" "+ item.getName() + " " + "</col>from the Power token shop!");
						player.getInventory().addItem(powershop[i11][0], quantity);
						player.getPointsManager().setPowerTokens(player.getPointsManager().getPowerTokens() - powershop[i11][1] * quantity);
						player.getPackets().sendMusicEffect(28);
						item.setAmount(item.getAmount() - 1);
						refreshShop();
						sendInventory(player);
						return;
					}
				}
			}	
	}
	boolean enoughCoins = maxQuantity >= buyQ;
	
	if (!enoughCoins) {
	    player.getPackets().sendGameMessage("You don't have enough " + ItemDefinitions.getItemDefinitions(money).getName().toLowerCase() + ".");
	    buyQ = maxQuantity;
	} else if (quantity > buyQ)
	    player.getPackets().sendGameMessage("The shop has run out of stock.");
	if (item.getDefinitions().isStackable()) {
	    if (player.getInventory().getFreeSlots() < 1) {
		player.getPackets().sendGameMessage("Not enough space in your inventory.");
		return;
	    }
	} else {
	    int freeSlots = player.getInventory().getFreeSlots();
	    if (buyQ > freeSlots) {
		buyQ = freeSlots;
		player.getPackets().sendGameMessage("Not enough space in your inventory.");
	    }
	}
	if (buyQ != 0) {
	    int totalPrice = price * buyQ;
	    if (player.getInventory().removeItemMoneyPouch(new Item(money, totalPrice))) {
	    	player.shopLog(player, item.getId(), item.getAmount(), false);
		player.getInventory().addItem(item.getId(), buyQ);
		item.setAmount(item.getAmount() - buyQ);
		if (item.getAmount() <= 0 && slotId >= mainStock.length)
		    generalStock[slotId - mainStock.length] = null;
		refreshShop();
		resetSelected(player);
	    }
	}
    }

    public void restoreItems() {
	boolean needRefresh = false;
	for (int i = 0; i < mainStock.length; i++) {
	    if (mainStock[i].getAmount() < defaultQuantity[i]) {
		mainStock[i].setAmount(mainStock[i].getAmount() + 1);
		needRefresh = true;
	    } else if (mainStock[i].getAmount() > defaultQuantity[i]) {
		mainStock[i].setAmount(mainStock[i].getAmount() + -1);
		needRefresh = true;
	    }
	}
	if (generalStock != null) {
	    for (int i = 0; i < generalStock.length; i++) {
		Item item = generalStock[i];
		if (item == null)
		    continue;
		item.setAmount(item.getAmount() - 1);
		if (item.getAmount() <= 0)
		    generalStock[i] = null;
		needRefresh = true;
	    }
	}
	if (needRefresh)
	    refreshShop();
    }

    private boolean addItem(int itemId, int quantity) {
	for (Item item : mainStock) {
	    if (item.getId() == itemId) {
		item.setAmount(item.getAmount() + quantity);
		refreshShop();
		return true;
	    }
	}
	if (generalStock != null) {
	    for (Item item : generalStock) {
		if (item == null)
		    continue;
		if (item.getId() == itemId) {
		    item.setAmount(item.getAmount() + quantity);
		    refreshShop();
		    return true;
		}
	    }
	    for (int i = 0; i < generalStock.length; i++) {
		if (generalStock[i] == null) {
		    generalStock[i] = new Item(itemId, quantity);
		    refreshShop();
		    return true;
		}
	    }
	}
	return false;
    }

    public void sell(Player player, int slotId, int quantity) {
	if (player.getInventory().getItemsContainerSize() < slotId)
	    return;
	Item item = player.getInventory().getItem(slotId);
	if (item == null)
	    return;
	int originalId = item.getId();
	if (item.getDefinitions().isNoted() && item.getDefinitions().getCertId() != -1)
	    item = new Item(item.getDefinitions().getCertId(), item.getAmount());
	if (!ItemConstants.isTradeable(item) || item.getId() == money) {
	    player.getPackets().sendGameMessage("You can't sell this item.");
	    return;
	}
	int dq = getDefaultQuantity(item.getId());
	if (dq == -1 && generalStock == null) {
	    player.getPackets().sendGameMessage("You can't sell this item to this shop.");
	    return;
	}
		int price = item.getDefinitions().getValue();
		if (price < 1)
			price = 1;
	int numberOff = player.getInventory().getItems().getNumberOf(originalId);
	if (quantity > numberOff)
	    quantity = numberOff;
	if (!addItem(item.getId(), quantity) && !isGeneralStore()) {
		player.getPackets().sendGameMessage("Shop is currently full.");
		return;
	}
	if (player.isPker && isGeneralStore()) {
		player.sm("Pkers are not allowed to use the general store.");
		return;
	}
	player.shopLog(player, item.getId(), item.getAmount(), true);
	player.getInventory().deleteItem(originalId, quantity);
	refreshShop();
	resetSelected(player);
	if (price == 0)
	    return;
	player.getInventory().addItemMoneyPouch(new Item(995, price * quantity));
    }

   public void sendValue(Player player, int slotId) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)
			return;
		if (!ItemConstants.isTradeable(item) || item.getId() == money) {
			player.getPackets().sendGameMessage(item.getName() + " cannot be sold to shops.");
			return;
		}
		int dq = getDefaultQuantity(item.getId());
		if (dq == -1 && generalStock == null) {
			player.getPackets().sendGameMessage("You can't sell " + item.getName() + " to this shop.");
			return;
		}
		int price = item.getDefinitions().getValue();
		if (item.getDefinitions().isNoted()) {
			Item newItem = new Item(item.getDefinitions().getCertId());
			price = newItem.getDefinitions().getValue();
		}
		if (money == 995)
			player.sendMessage(item.getDefinitions().getName() + ": shop will buy for: "
					+ Utils.getFormattedNumber(price) + " "
					+ ItemDefinitions.getItemDefinitions(money).getName().toLowerCase() + "; right-click to sell.");

	}
    public int getDefaultQuantity(int itemId) {
	for (int i = 0; i < mainStock.length; i++)
	    if (mainStock[i].getId() == itemId)
		return defaultQuantity[i];
	return -1;
    }

    public void resetSelected(Player player) {
	player.getTemporaryAttributtes().remove("ShopSelectedSlot");
	player.getVarsManager().sendVar(2563, -1);
    }

    public void sendInfo(Player player, int slotId, boolean inventory) {
	if (!inventory && slotId >= getStoreSize())
	    return;
	Item item = inventory ? player.getInventory().getItem(slotId) : slotId >= mainStock.length ? generalStock[slotId - mainStock.length] : mainStock[slotId];
	if (item == null)
	    return;
	if (item.getDefinitions().isNoted())
	    item = new Item(item.getDefinitions().getCertId(), item.getAmount());
	if (inventory && (!ItemConstants.isTradeable(item) || item.getId() == money)) {
	    player.getPackets().sendGameMessage("You can't sell this item.");
	    resetSelected(player);
	    return;
	}
	resetTransaction(player);
	player.getTemporaryAttributtes().put("ShopSelectedSlot", slotId);
	player.getTemporaryAttributtes().put("ShopSelectedInventory", inventory);
	player.getVarsManager().sendVar(2561, inventory ? 93 : generalStock != null ? 139 : MAIN_STOCK_ITEMS_KEY); // inv key
	player.getVarsManager().sendVar(2562, item.getId());
	player.getVarsManager().sendVar(2563, slotId);
	player.getPackets().sendGlobalString(362, ItemExamines.getExamine(item));
	player.getPackets().sendGlobalConfig(1876, ItemDefinitions.getEquipType(item.getName()));
	int price = item.getDefinitions().getValue();
	for (int i = 0; i < dungShop.length; i++) {
				if (item.getId() == dungShop[i][0] && !isGeneralStore() && name.contains("Dungeoneering")) {
					player.sendMessage(Colors.red + item.getDefinitions().getName() + "</col> costs " + Colors.red
							+ Utils.getFormattedNumber(dungShop[i][1]) + "</col> " + "dungeoneering tokens, you have "
							+ Colors.red + Utils.getFormattedNumber(player.getPointsManager().getDungeoneeringTokens()) + "</col>.");
					player.getPackets().sendIComponentText(1265, 205, "" + dungShop[i][1]);
					return;
				}
			}
	for (int i = 0; i < auraShops.length; i++) {
				if (item.getId() == auraShops[i][0] && !isGeneralStore() && name.contains("Aura")) {
					player.sendMessage(Colors.red + item.getDefinitions().getName() + "</col> aura costs " + Colors.red
							+ Utils.getFormattedNumber(auraShops[i][1]) + " </col>Loyalty points.");
					player.getPackets().sendIComponentText(1265, 205, "" + auraShops[i][1]);
					return;
				}		
	}for (int i = 0; i < riddleStore.length; i++) {
		if (item.getId() == riddleStore[i][0] && !isGeneralStore() && name.contains("Riddle")) {
			player.sendMessage(Colors.red + item.getDefinitions().getName() + "</col> costs " + Colors.red
					+ Utils.getFormattedNumber(riddleStore[i][1]) + " </col>Riddle points.");
			player.getPackets().sendIComponentText(1265, 205, "" + riddleStore[i][1]);
			return;
		}		
	}for (int i = 0; i < powershop.length; i++) {
				if (item.getId() == powershop[i][0] && !isGeneralStore() && name.contains("Power")) {
					player.sendMessage(Colors.red + item.getDefinitions().getName() + "</col> items costs " + Colors.red
							+ Utils.getFormattedNumber(powershop[i][1]) + " </col>Power tokens.");
					player.getPackets().sendIComponentText(1265, 205, "" + powershop[i][1]);
					return;
				}		
	}for (int i = 0; i < runespanStore.length; i++) {
				if (item.getId() == runespanStore[i][0] && !isGeneralStore() && name.contains("Runespan")) {
					player.sendMessage(Colors.red + item.getDefinitions().getName() + "</col> items costs " + Colors.red
							+ Utils.getFormattedNumber(runespanStore[i][1]) + " </col>Runespan points.");
					player.getPackets().sendIComponentText(1265, 205, "" + runespanStore[i][1]);
					return;
				}		
	}
	for (int i = 0; i < lividStore.length; i++) {
		if (item.getId() == lividStore[i][0] && !isGeneralStore() && name.contains("Livid")) {
			player.sendMessage(Colors.red + item.getDefinitions().getName() + "</col> items costs " + Colors.red
					+ Utils.getFormattedNumber(lividStore[i][1]) + " </col>Livid points.");
			player.getPackets().sendIComponentText(1265, 205, "" + lividStore[i][1]);
			return;
		}		
}
	player.getPackets().sendGameMessage(item.getDefinitions().getName() + ": shop will " + (inventory ? "buy" : "sell") + " for: " + price + " " + ItemDefinitions.getItemDefinitions(money).getName().toLowerCase());
    }

    public static int getSellPrice(Item item, int dq) {
		int price = item.getDefinitions().getTipitPrice();
		return price;
	
    }

    public void sendExamine(Player player, int slotId) {
	if (slotId >= getStoreSize())
	    return;
	Item item = slotId >= mainStock.length ? generalStock[slotId - mainStock.length] : mainStock[slotId];
	if (item == null)
	    return;
	player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
    }

    public void refreshShop() {
	for (Player player : viewingPlayers) {
	    sendStore(player);
	    player.getPackets().sendIComponentSettings(620, 25, 0, getStoreSize() * 6, 1150);
	}
    }

    public int getStoreSize() {
	return mainStock.length + (generalStock != null ? generalStock.length : 0);
    }

    public void sendStore(Player player) {
	Item[] stock = new Item[mainStock.length + (generalStock != null ? generalStock.length : 0)];
	System.arraycopy(mainStock, 0, stock, 0, mainStock.length);
	if (generalStock != null)
	    System.arraycopy(generalStock, 0, stock, mainStock.length, generalStock.length);
	player.getPackets().sendItems(generalStock != null ? 139 : MAIN_STOCK_ITEMS_KEY, stock);
    }

}