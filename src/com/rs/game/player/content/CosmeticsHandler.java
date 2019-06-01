package com.rs.game.player.content;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.utils.ItemIdentifiers;

/**
  * @miles & Paolo
  **/

public class CosmeticsHandler {

	/**
	 * Every Item Id You Put will show in the dialogue when clicked at the slot
	 * no matter how many it will calculate the number of pages needed
	 * automatically
	 */
	public static int[] HATS = 
	{
		34222, //darklord
		24639, //assasin
		34114, //gameblast
		31296, //hiker
	};
	public static int[] CAPES = 
	{
	 31300, //hiker
	};
	public static int[] AMULETS = 
	{
		34120, //gameblast
	};
	public static int[] WEAPONS = {};
	public static int[] CHESTS = 
	{
		34223, //darklord
		24641, //assasin
		34115, //gameblast
		31297,//hiker
	};
	public static int[] SHIELDS = {};
	public static int[] LEGS = 
	{
		34224, //darklord
		24643, //assasin
		34116, //gameblast
		31298,//hiker
	};
	public static int[] BOOTS = 
	{
		34225, //darklord
		24645, //assasin
		34117, //gameblast
		31299, //hiker
	};
	public static int[] GLOVES = 
	{
		34226, //Darklord
		24647,//assasin
		34118, // gameblast
		31301, //hiker
	};
	public static int[] WINGS = 
	{
		33596, //now generato
	};
	public static final int[][] FULL_OUTFITS = { 
	//darklord
	{34220,34222,34223,34224,34225,34226},
	//assasin
	{24637,24639,24641,24643,24645,24647}
	};
	public static String[] OUTFIT_NAMES = 
	{
		"Dark Lord"
	};
	public static final int[] LOCKED_COSTUMES_IDS = 
	{
		34220,34222,34223,34224,34225,34226, //dark lord
		33596, //snow generator
		24637,24639,24641,24643,24645,24647, //assasin outfit
		34114,34115,34116,34117,34118,34120, //gameblast
		31296,31297,31298,31299,31300,31301 //hiker
	};
	
	private final static HashMap<Integer, String> cosmeticsNames = new HashMap<>( );

	private final static String PACKED_PATH = "data/items/packedCosmeticsNames.cn";
	private final static String UNPACKED_PATH = "data/items/unpackedCosmeticsNames.txt";

	public static void init() {
		if (new File(PACKED_PATH).exists())
			loadPackeddCosmeticsNames();
		else
			loadUnpackedCosmeticsNames();
	}

	private static void loadPackeddCosmeticsNames() {
		try {
			RandomAccessFile in = new RandomAccessFile(PACKED_PATH, "r");
			FileChannel channel = in.getChannel();
			ByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, channel.size());
			while (buffer.hasRemaining())
				cosmeticsNames.put(buffer.getShort() & 0xffff, readAlexString(buffer));
			channel.close();
			in.close();
		} catch (Throwable e) {
			e.printStackTrace();

		}
	}

	private static void loadUnpackedCosmeticsNames() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(UNPACKED_PATH));
			DataOutputStream out = new DataOutputStream(new FileOutputStream(PACKED_PATH));
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				if (line.startsWith("//"))
					continue;
				line = line.replace("﻿", "");
				String[] splitedLine = line.split(" - ", 2);
				if (splitedLine.length < 2) {
					in.close();
					throw new RuntimeException("Invalid list for Cosmetic Names line: " + line);
				}
				int npcId = Integer.valueOf(splitedLine[0]);
				if (splitedLine[1].length() > 255)
					continue;
				out.writeShort(npcId);
				writeAlexString(out, splitedLine[1]);
				cosmeticsNames.put(npcId, splitedLine[1]);
			}

			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String readAlexString(ByteBuffer buffer) {
		int count = buffer.get() & 0xff;
		byte[] bytes = new byte[count];
		buffer.get(bytes, 0, count);
		return new String(bytes);
	}

	private static void writeAlexString(DataOutputStream out, String string) throws IOException {
		byte[] bytes = string.getBytes();
		out.writeByte(bytes.length);
		out.write(bytes);
	}

	int removeComps[] = {0,22,23};
	public static void openCosmeticsHandler(final Player player) {
		player.stopAll();
		player.getTemporaryAttributtes().put("Cosmetics", Boolean.TRUE);
		player.getInterfaceManager().sendInventoryInterface(670);
		player.getInterfaceManager().sendInterface(667);
		player.getPackets().sendHideIComponent(667, 0, true);
		player.getPackets().sendHideIComponent(667, 22, true);
		player.getPackets().sendHideIComponent(667, 23, true);
		for(int i = 25; i <= 45; i ++)
			player.getPackets().sendHideIComponent(667, i, true);
		player.getVarsManager().sendVarBit(8348, 1);
		player.getVarsManager().sendVarBit(4894, 0);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendInterSetItemsOptionsScript(670, 0, 93, 4, 7, "Equip", "", "", "");
		player.getPackets().sendUnlockIComponentOptionSlots(670, 0, 0, 27, 0, 1, 2, 3);
		Item[] cosmetics = player.getEquipment().getItems().getItemsCopy();
		for (int i = 0; i < cosmetics.length; i++) {
			Item item = cosmetics[i];
			if (item == null)
				cosmetics[i] = new Item(0);
		}
		player.getPackets().sendItems(94, cosmetics);
		player.getPackets().sendUnlockIComponentOptionSlots(667, 9, 0, 14, true, 0, 1, 2, 3);
		ButtonHandler.refreshEquipBonuses(player);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getVarsManager().sendVarBit(8348, 1);
				player.getPackets().sendRunScriptBlank(2319);
			}
		});
		player.setCloseInterfacesEvent(() -> {
			player.getDialogueManager().finishDialogue();
			player.getTemporaryAttributtes().remove("Cosmetics");
			for (int i = 0; i < 15; i++) {
				player.getEquipment().refresh(i);
			}
		});
	}

	public static boolean keepSakeItem(Player player, Item itemUsed, Item itemUsedWith) {
		if (itemUsed.getId() != ItemIdentifiers.KEEP_SAKE_KEY && itemUsedWith.getId() != ItemIdentifiers.KEEP_SAKE_KEY)
			return false;
		if (itemUsed.getId() == ItemIdentifiers.KEEP_SAKE_KEY && itemUsedWith.getId() == ItemIdentifiers.KEEP_SAKE_KEY)
			return false;
		Item keepSakeKey = itemUsed.getId() == ItemIdentifiers.KEEP_SAKE_KEY ? itemUsed : itemUsedWith;
		Item keepSakeItem = itemUsed.getId() == ItemIdentifiers.KEEP_SAKE_KEY ? itemUsedWith : itemUsed;
		if (keepSakeItem == null || keepSakeKey == null)
			return false;
		if (player.getEquipment().getKeepSakeItems().size() >= 50) {
			player.getPackets().sendGameMessage("You can only keep sake 50 items.");
			return false;
		}
		int equipSlot = keepSakeItem.getDefinitions().getEquipSlot();
		if (equipSlot == Equipment.SLOT_ARROWS || equipSlot == Equipment.SLOT_AURA || equipSlot == Equipment.SLOT_RING) {
			player.getPackets().sendGameMessage("You can only keep sake items that goes into head, cape, neck, body, legs, gloves, main hand, off-hand, or boots slots.");
			return false;
		}
		if (equipSlot == -1) {
			player.getPackets().sendGameMessage("You can't keep sake this item as its not wearable.");
			return false;
		}
		if (!ItemConstants.canWear(player, keepSakeItem, true)) {
			player.getPackets().sendGameMessage("You don't have enough requirements to keep sake this item.");
			return false;
		}
		if (keepSakeItem.getDefinitions().isBindItem() || keepSakeItem.getDefinitions().isLended() || keepSakeItem.getDefinitions().isStackable())
			return false;
		String name = keepSakeItem.getName().toLowerCase();
		if (name.contains("broken")) {
			player.getPackets().sendGameMessage("You can't keep sake broken items.");
			return false;
		}
		for (Item item : player.getEquipment().getKeepSakeItems()) {
			if (item == null)
				continue;
			if (item.getId() == keepSakeItem.getId()) {
				player.getPackets().sendGameMessage("You already have that item in your keepsake box.");
				return false;
			}
		}
		player.stopAll();
		player.getDialogueManager().startDialogue(new Dialogue() {

			@Override
			public void start() {
				sendOptionsDialogue("DO YOU WANT TO KEEP SAKE THIS ITEM?", "Yes, keep sake this item.(You won't be able to retrieve key)", "No, I would like to keep it.");
			}

			@Override
			public void run(int interfaceId, int componentId) {
				if (componentId == OPTION_1) {
					player.getEquipment().getKeepSakeItems().add(keepSakeItem);
					player.getPackets().sendGameMessage("You have added " + keepSakeItem.getName() + " to keepsakes. It will appear along with cosmetics in ;;cosmetics");
					player.getInventory().deleteItem(ItemIdentifiers.KEEP_SAKE_KEY, 1);
					player.getInventory().deleteItem(keepSakeItem);
				}
				end();
			}

			@Override
			public void finish() {

			}

		});
		return true;
	}

	/*
	 * Change the names of certain items of the costumes
	 */

	public static String getNameOnDialogue(int itemId) {
		String cosmeticName = cosmeticsNames.get(itemId);
		if (cosmeticName != null)
			return cosmeticName;
		return ItemDefinitions.getItemDefinitions(itemId).getName();
	}

	/*
	 * Restricts player from using certain items if they doesn't have the
	 * requirement needed, if the player doesn't have the requirment needed it
	 * wont show the item in the dialogue
	 */

	public static boolean isRestrictedItem(Player player, int itemId) {
		for (int LOCKED_COSTUMES_ID : LOCKED_COSTUMES_IDS) {
			if (itemId == LOCKED_COSTUMES_ID && player.isLockedCostume(itemId))
				return true;
		}
		switch (itemId) {
		case 995:
			player.getPackets().sendGameMessage("Error message here.");
			return true;
		}
		return false;
	}

	public static void UnlockCostumeId(Player player, int itemId) {
		if (player.getUnlockedCostumesIds().contains(itemId))
			return;
		player.getUnlockedCostumesIds().add(itemId);
		player.getPackets().sendGameMessage("<col=00ff00>You have unlocked " + getNameOnDialogue(itemId) + "!");
	}

	public static void UnlockOutfit(Player player, int index) {
		for (int itemId : FULL_OUTFITS[index]) {
			UnlockCostumeId(player, itemId);
		}
	}

	public static boolean isRestrictedOutfit(Player player, int index) {
		for (int itemId : FULL_OUTFITS[index]) {
			if (isRestrictedItem(player, itemId))
				return true;
		}
		return false;
	}

}
