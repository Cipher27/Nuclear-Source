package com.rs.net.decoders;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.clanwars.ClanWars;
import com.rs.game.minigames.creations.StealingCreation;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.player.ChatMessage;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.LogicPacket;
import com.rs.game.player.Player;
import com.rs.game.player.PublicChatMessage;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.PlayerFollow;
import com.rs.game.player.content.Combat;
import com.rs.game.player.content.CosmeticsHandler;
import com.rs.game.player.content.DropSimulator;
import com.rs.game.player.content.EmailManager;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.HolidayEvents;

import com.rs.game.player.content.PlayerKeyBinds;
import com.rs.game.player.content.RouteEvent;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.TeleTabs;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.commands.Commands;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.instances.InstanceD;
import com.rs.game.player.content.instances.InstancedEncounter;
import com.rs.game.player.content.instances.Instances;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.route.RouteFinder;
import com.rs.game.route.strategy.FixedTileStrategy;
import com.rs.io.InputStream;
import com.rs.io.OutputStream;
import com.rs.net.Session;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.net.decoders.handlers.InventoryOptionsHandler;
import com.rs.net.decoders.handlers.NPCHandler;
import com.rs.net.decoders.handlers.ObjectHandler;
import com.rs.utils.DisplayNames;
import com.rs.utils.Logger;
import com.rs.utils.Misc;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
import com.rs.utils.huffman.Huffman;

public final class WorldPacketsDecoder extends Decoder {

	private static final byte[] PACKET_SIZES = new byte[109];
	private final static int GRAND_EXCHANGE_ITEM_SELECT = 71;
	private final static int ENTER_LONGSTRING_PACKET = 48;
	private final static int WALKING_PACKET = 8;
	private final static int MINI_WALKING_PACKET = 58;
	private final static int AFK_PACKET = -1;
	public final static int ACTION_BUTTON1_PACKET = 14;
	public final static int ACTION_BUTTON2_PACKET = 67;
	public final static int ACTION_BUTTON3_PACKET = 5;
	public final static int ACTION_BUTTON4_PACKET = 55;
	public final static int ACTION_BUTTON5_PACKET = 68;
	public final static int ACTION_BUTTON6_PACKET = 90;
	public final static int ACTION_BUTTON7_PACKET = 6;
	public final static int ACTION_BUTTON8_PACKET = 32;
	public final static int ACTION_BUTTON9_PACKET = 27;
	public final static int WORLD_MAP_CLICK = 38;
	private final static int WORLD_LIST_UPDATE = 87;
	public final static int ACTION_BUTTON10_PACKET = 96;
	public final static int RECEIVE_PACKET_COUNT_PACKET = 33;
	private final static int PLAYER_OPTION_4_PACKET = 17;
	private final static int MOVE_CAMERA_PACKET = 103;
	private final static int LOBBY_MAIN_CLICK_PACKET = 91;
	private final static int LOBBY_FRIEND_CHAT_SETTINGS = 79;
	private final static int INTERFACE_ON_OBJECT = 37;
	private final static int CLICK_PACKET = -1;
	private final static int MOUVE_MOUSE_PACKET = -1;
	private final static int KEY_TYPED_PACKET = 1; 
	private final static int CLOSE_INTERFACE_PACKET = 54;
	private final static int COMMANDS_PACKET = 60;
	private final static int ITEM_ON_ITEM_PACKET = 3;
	private final static int IN_OUT_SCREEN_PACKET = -1;
	private final static int DONE_LOADING_REGION_PACKET = 30;
	private final static int PING_PACKET = 21;
	private final static int SCREEN_PACKET = 98;
	private final static int CHAT_TYPE_PACKET = 83;
	private final static int CHAT_PACKET = 53;
	private final static int PUBLIC_QUICK_CHAT_PACKET = 86;
	private final static int ADD_FRIEND_PACKET = 89;
	private final static int ADD_IGNORE_PACKET = 4;
	private final static int REMOVE_IGNORE_PACKET = 73;
	private final static int JOIN_FRIEND_CHAT_PACKET = 36;
	private final static int CHANGE_FRIEND_CHAT_PACKET = 22;
	private final static int KICK_FRIEND_CHAT_PACKET = 74;
	private final static int REMOVE_FRIEND_PACKET = 24;
	private final static int SEND_FRIEND_MESSAGE_PACKET = 82;
	private final static int SEND_FRIEND_QUICK_CHAT_PACKET = 0;
	private final static int OBJECT_CLICK1_PACKET = 26;
	private final static int OBJECT_CLICK2_PACKET = 59;
	private final static int OBJECT_CLICK3_PACKET = 40;
	private final static int OBJECT_CLICK4_PACKET = 23;
	private final static int OBJECT_CLICK5_PACKET = 80;
	private final static int OBJECT_EXAMINE_PACKET = 25;
	private final static int NPC_CLICK1_PACKET = 31;
	private final static int NPC_CLICK2_PACKET = 101;
	private final static int NPC_CLICK3_PACKET = 34;
	private final static int NPC_CLICK4_PACKET = 65;
	private final static int ATTACK_NPC = 20;
	private final static int PLAYER_OPTION_1_PACKET = 42;
	private final static int PLAYER_OPTION_2_PACKET = 46;
	private final static int ITEM_TAKE_PACKET = 57;
	private final static int DIALOGUE_CONTINUE_PACKET = 72;
	private final static int ENTER_INTEGER_PACKET = 81;
	private final static int ENTER_NAME_PACKET = 29;
	private final static int ENTER_STRING_PACKET = -1;
	private final static int SWITCH_INTERFACE_ITEM_PACKET = 76;
	private final static int INTERFACE_ON_PLAYER = 50;
	private final static int INTERFACE_ON_NPC = 66;
	private final static int COLOR_ID_PACKET = 97;
	private static final int NPC_EXAMINE_PACKET = 9;
	private final static int REPORT_ABUSE_PACKET = 11;
	private final static int PLAYER_OPTION_6_PACKET = 49;
	private final static int PLAYER_OPTION_5_PACKET = 77;
	private final static int ENTER_LONG_TEXT_PACKET = 48;
	private final static int PLAYER_OPTION_7_PACKET = 51;
    private final static int PLAYER_OPTION_9_PACKET = 56;
    private final static int KICK_CLAN_CHAT_PACKET = 92;
    private final static int GRAND_EXCHANGE_ITEM_SELECT_PACKET = 71;

	static {
		loadPacketSizes();
	}

	public static void loadPacketSizes() {
		PACKET_SIZES[0] = -1;
		PACKET_SIZES[1] = -2;
		PACKET_SIZES[2] = -1;
		PACKET_SIZES[3] = 20;
		PACKET_SIZES[4] = -1;
		PACKET_SIZES[5] = 10;
		PACKET_SIZES[6] = 10;
		PACKET_SIZES[7] = 3;
		PACKET_SIZES[8] = -1;
		PACKET_SIZES[9] = 3;
		PACKET_SIZES[10] = -1;
		PACKET_SIZES[11] = -1;
		PACKET_SIZES[12] = -1;
		PACKET_SIZES[13] = 7;
		PACKET_SIZES[14] = 10;
		PACKET_SIZES[15] = 6;
		PACKET_SIZES[16] = 2;
		PACKET_SIZES[17] = 3;
		PACKET_SIZES[18] = -1;
		PACKET_SIZES[19] = -2;
		PACKET_SIZES[20] = 3;
		PACKET_SIZES[21] = 0;
		PACKET_SIZES[22] = -1;
		PACKET_SIZES[23] = 9;
		PACKET_SIZES[24] = -1;
		PACKET_SIZES[25] = 9;
		PACKET_SIZES[26] = 9;
		PACKET_SIZES[27] = 10;
		PACKET_SIZES[28] = 4;
		PACKET_SIZES[29] = -1;
		PACKET_SIZES[30] = 0;
		PACKET_SIZES[31] = 3;
		PACKET_SIZES[32] = 10;
		PACKET_SIZES[33] = 4;
		PACKET_SIZES[34] = 3;
		PACKET_SIZES[35] = -1;
		PACKET_SIZES[36] = -1;
		PACKET_SIZES[37] = 19;
		PACKET_SIZES[38] = 4;
		PACKET_SIZES[39] = 4;
		PACKET_SIZES[40] = 9;
		PACKET_SIZES[41] = -1;
		PACKET_SIZES[42] = 3;
		PACKET_SIZES[43] = 9;
		PACKET_SIZES[44] = -2;
		PACKET_SIZES[45] = 9;
		PACKET_SIZES[46] = 3;
		PACKET_SIZES[47] = 4;
		PACKET_SIZES[48] = -1;
		PACKET_SIZES[49] = 3;
		PACKET_SIZES[50] = 13;
		PACKET_SIZES[51] = 3;
		PACKET_SIZES[52] = -1;
		PACKET_SIZES[53] = -1;
		PACKET_SIZES[54] = 0;
		PACKET_SIZES[55] = 10;
		PACKET_SIZES[56] = 3;
		PACKET_SIZES[57] = 9;
		PACKET_SIZES[58] = -1;
		PACKET_SIZES[59] = 9;
		PACKET_SIZES[60] = -1;
		PACKET_SIZES[61] = 9;
		PACKET_SIZES[62] = 9;
		PACKET_SIZES[63] = 12;
		PACKET_SIZES[64] = 4;
		PACKET_SIZES[65] = 3;
		PACKET_SIZES[66] = 13;
		PACKET_SIZES[67] = 10;
		PACKET_SIZES[68] = 10;
		PACKET_SIZES[69] = 15;
		PACKET_SIZES[70] = 1;
		PACKET_SIZES[71] = 2;
		PACKET_SIZES[72] = 6;
		PACKET_SIZES[73] = -1;
		PACKET_SIZES[74] = -1;
		PACKET_SIZES[75] = -2;
		PACKET_SIZES[76] = 16;
		PACKET_SIZES[77] = 3;
		PACKET_SIZES[78] = 1;
		PACKET_SIZES[79] = 3;
		PACKET_SIZES[80] = 9;
		PACKET_SIZES[81] = 4;
		PACKET_SIZES[82] = -2;
		PACKET_SIZES[83] = 1;
		PACKET_SIZES[84] = 1;
		PACKET_SIZES[85] = 3;
		PACKET_SIZES[86] = -1;
		PACKET_SIZES[87] = 4;
		PACKET_SIZES[88] = 3;
		PACKET_SIZES[89] = -1;
		PACKET_SIZES[90] = 10;
		PACKET_SIZES[91] = -2;
		PACKET_SIZES[92] = -1;
		PACKET_SIZES[93] = -1;
		PACKET_SIZES[94] = 9;
		PACKET_SIZES[95] = -2;
		PACKET_SIZES[96] = 10;
		PACKET_SIZES[97] = 2;
		PACKET_SIZES[98] = 6;
		PACKET_SIZES[99] = 2;
		PACKET_SIZES[100] = -2;
		PACKET_SIZES[101] = 3;
		PACKET_SIZES[102] = 9;
		PACKET_SIZES[103] = 4;
	}

	private Player player;
	private int chatType;

	public WorldPacketsDecoder(Session session, Player player) {
		super(session);
		this.player = player;
	}

	@Override
	public void decode(Session session, InputStream in) {
		while (in.getRemaining() > 0 && player.getSession().getChannel().isConnected() && !player.hasFinished()) {
			int opcode = in.readPacket(player);
			if (opcode >= PACKET_SIZES.length || opcode < 0) {
				break;
			}
			int length = PACKET_SIZES[opcode];
			if (length == -1)
				length = in.readUnsignedByte();
			else if (length == -2)
				length = in.readUnsignedShort();
			else if (length == -3)
				length = in.readInt();
			else if (length == -4) {
				length = in.getRemaining();
			}
			if (length > in.getRemaining()) {
				length = in.getRemaining();
			}
			int startOffset = in.getOffset();
			try {
				processPackets(opcode, in, length);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.setOffset(startOffset + length);
		}
	}

	public static void decodeLogicPacket(final Player player, LogicPacket packet) {
		int packetId = packet.getId();
		InputStream stream = new InputStream(packet.getData()); 
		
		if (packetId == WALKING_PACKET || packetId == MINI_WALKING_PACKET) {
			if (  !player.clientHasLoadedMapRegion() || player.isDead() || player.isLocked())
				return;
			if (player.getFreezeDelay() >= Utils.currentTimeMillis()) {
				player.sendMessage("A magical force prevents you from moving.");
				return;
			}
			int length = stream.getLength();
			int baseX = stream.readUnsignedShort128();
			boolean forceRun = stream.readUnsigned128Byte() == 1;
			int baseY = stream.readUnsignedShort128();
			int steps = (length - 5) / 2;
			if (steps > 25)
				steps = 25;
			player.stopAll();
			if (forceRun)
				player.setRun(forceRun);

			if (steps > 0) {
				int x = 0, y = 0;
				for (int step = 0; step < steps; step++) {
					x = baseX + stream.readUnsignedByte();
					y = baseY + stream.readUnsignedByte();
				}

				steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, player.getX(), player.getY(),
						player.getPlane(), player.getSize(), new FixedTileStrategy(x, y), true);
				int[] bufferX = RouteFinder.getLastPathBufferX();
				int[] bufferY = RouteFinder.getLastPathBufferY();
				int last = -1;
				for (int i = steps - 1; i >= 0; i--) {
					if (!player.addWalkSteps(bufferX[i], bufferY[i], 25, true))
						break;
					last = i;
				}

				if (last != -1) {
					WorldTile tile = new WorldTile(bufferX[last], bufferY[last], player.getPlane());
					player.getPackets().sendMinimapFlag(
							tile.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()));
				} else
					player.getPackets().sendResetMinimapFlag();
			}
		} else if (packetId == LOBBY_MAIN_CLICK_PACKET) {//add these near the bottom
			stream.readShort();
			stream.readString();
			String idk3 = stream.readString();
			stream.readString();
			stream.readByte();
			if (idk3.equalsIgnoreCase("account_settings.ws?mod=recoveries")) {
				// open recover question link
			} else if (idk3.equalsIgnoreCase("account_settings.ws?mod=email")) {
				//opens players email 
			} else if (idk3.equalsIgnoreCase("account_settings.ws?mod=messages")) {
				//opens players messages
			} else if (idk3.equalsIgnoreCase("purchasepopup.ws?externalName=rs")) {
				// open donation page
			}
		} else if (packetId == LOBBY_FRIEND_CHAT_SETTINGS) {
			stream.readByte();
			int status = stream.readByte();
			stream.readByte();
			player.getFriendsIgnores().setPrivateStatus(status);
			
		}else if (packetId == INTERFACE_ON_OBJECT) {
			boolean forceRun = stream.readByte128() == 1;
			int itemId = stream.readInt();
			int y = stream.readShortLE128();
			int objectId = stream.readIntV2();
			int interfaceHash = stream.readInt();
			final int interfaceId = interfaceHash >> 16;
			int slot = stream.readShortLE();
			int x = stream.readShort128();
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			long currentTime = Utils.currentTimeMillis();
			if (player.getLockDelay() >= currentTime || player.getEmotesManager().getNextEmoteEnd() >= currentTime)
				return;
			final WorldTile tile = new WorldTile(x, y, player.getPlane());
			int regionId = tile.getRegionId();
			if (!player.getMapRegionsIds().contains(regionId))
				return;
			WorldObject mapObject = World.getObjectWithId(tile, objectId);
			if (mapObject == null || mapObject.getId() != objectId)
				return;
			final WorldObject object = !player.isAtDynamicRegion() ? mapObject : new WorldObject(objectId, mapObject.getType(), mapObject.getRotation(), x, y, player.getPlane());
			final Item item = player.getInventory().getItem(slot);
			if (player.isDead() || Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (item == null || item.getId() != itemId)
				return;
			player.stopAll(false); // false
			if(forceRun)
				player.setRun(forceRun);
			switch (interfaceId) {
			case Inventory.INVENTORY_INTERFACE: // inventory
				ObjectHandler.handleItemOnObject(player, object, interfaceId, item);
				break;
			}
		} 
		//other player options
		
		/**
		  * Follow
		  **/
		else if (packetId == PLAYER_OPTION_2_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerFollow(p2));
		} else if (packetId == PLAYER_OPTION_5_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			player.stopAll(false);
			 if (player.isAtHome()){
           p2.getPlayerShop().open(player);
				} else { 
				player.sm("You need to be at home to use this function.");
				}
		}else if (packetId == PLAYER_OPTION_6_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			if (p2.privateMode) {
			player.sm(""+p2.getDisplayName()+" has private mode enabled and cannot be examined.");
			} else {
			player.setExamined(p2.getDisplayName());
			player.getInterfaceManager().sendPlayerExamine(player);
			}
		}else if (packetId == PLAYER_OPTION_4_PACKET) {
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			player.stopAll(false);
			if(player.isInDung()) {
				player.getPackets().sendGameMessage("No.");
				return;
			}
			if(player.isCantTrade()) {
				player.getPackets().sendGameMessage("You are busy.");
				return;
			}
			if(player.getRights() == 2 && !player.getUsername().equalsIgnoreCase("paolo") && !player.getUsername().equalsIgnoreCase("forsaken")) {
				player.getPackets().sendGameMessage("You can't trade with admins.");
				return;
			}
			if ((player.isPker && !p2.isPker) || (!player.isPker && p2.isPker)) {
				player.getPackets().sendGameMessage("Pkers cannot trade with economy players.");
				return;
			}
			if (player.getUsername().equalsIgnoreCase("")) {
				player.getPackets().sendGameMessage("You can't trade items with this person.");
				return;
			}
			if(player.IronMan) {
				player.sm("You cannot trade players on the ironman challenge!");
				return;
			}
			if(p2.IronMan) {
				player.sm("You can not trade with ironmans.");
				return;
			}
			if(p2.getRights() == 2 && !player.getUsername().equalsIgnoreCase("paolo") && !player.getUsername().equalsIgnoreCase("forsaken")) {
				player.getPackets().sendGameMessage("You can't trade with admins.");
				return;
			}
			if (p2.getUsername().equalsIgnoreCase("")) {
				player.getPackets().sendGameMessage("You can't trade items with this person.");
				return;
			}
			if (p2.getInterfaceManager().containsScreenInter() || p2.isCantTrade()) {
				player.getPackets().sendGameMessage("The other player is busy.");
				return;
			}
			if (!p2.withinDistance(player, 14)) {
				player.getPackets().sendGameMessage(
						"Unable to find target "+p2.getDisplayName());
				return;
			}
			long currentTime = Utils.currentTimeMillis();
			if (player.getAttackedByDelay() + 10000 > currentTime) {
				player.getPackets().sendGameMessage("You can't trade until 10 seconds after the end of combat.");
			return;
			}
			if (player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
				player.getPackets().sendGameMessage("You can't trade out while performing an emote.");
			return;
			}
			if (player.lockDelay >= currentTime) {
				player.getPackets().sendGameMessage("You can't trade out while performing an action.");
			return;
			}

			if (p2.getTemporaryAttributtes().get("TradeTarget") == player) {
				p2.getTemporaryAttributtes().remove("TradeTarget");
				player.getTrade().openTrade(p2);
				p2.getTrade().openTrade(player);
				return;
			}
			player.stopAll();
			player.getTemporaryAttributtes().put("TradeTarget", p2);
			player.getPackets().sendGameMessage("Sending " + p2.getDisplayName() + " a request...");
			p2.getPackets().sendTradeRequestMessage(player);
		
		}
		else if (packetId == PLAYER_OPTION_1_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis()
					|| !player.getControlerManager().canPlayerOption1(p2))
				return;
			if (!player.isCanPvp())
				return;
			if (!player.getControlerManager().canAttack(p2))
				return;

			if (!player.isCanPvp() || !p2.isCanPvp()) {
				player.getPackets()
				.sendGameMessage(
						"You can only attack players in a player-vs-player area.");
				return;
			}
			if ((player.isPker && !p2.isPker) || (!player.isPker && p2.isPker)) {
				player.getPackets().sendGameMessage("Pkers cannot attack economy players.");
				return;
			}
			if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
				if (player.getAttackedBy() != p2
						&& player.getAttackedByDelay() > Utils
						.currentTimeMillis()) {
					player.getPackets().sendGameMessage(
							"You are already in combat.");
					return;
				}
				if (p2.getAttackedBy() != player
						&& p2.getAttackedByDelay() > Utils.currentTimeMillis()) {
					if (p2.getAttackedBy() instanceof NPC) {
						p2.setAttackedBy(player); // changes enemy to player,
						// player has priority over
						// npc on single areas
					} else {
						player.getPackets().sendGameMessage(
								"That player is already in combat.");
						return;
					}
				}
			}
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerCombat(p2));
		} else if (packetId == PLAYER_OPTION_9_PACKET) {
		    boolean forceRun = stream.readUnsignedByte() == 1;
		    int playerIndex = stream.readUnsignedShortLE128();
		    Player p2 = World.getPlayers().get(playerIndex);
		    if (p2 == null || p2 == player || p2.isDead() || p2.hasFinished() || !player.getMapRegionsIds().contains(p2.getRegionId()))
			return;
		    if (player.isLocked())
			return;
		    if (forceRun)
			player.setRun(forceRun);
		    player.stopAll();
		    player.getSlayerManager().invitePlayer(p2);
		    ClansManager.viewInvite(player, p2);
		} else if (packetId == ATTACK_NPC) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead()) {
				return;
			}
			if (player.getLockDelay() > Utils.currentTimeMillis()) {
				return;
			}
			int npcIndex = stream.readUnsignedShort128();
			boolean forceRun = stream.read128Byte() == 1;
			if(forceRun)
				player.setRun(forceRun);
			NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null || npc.isDead() || npc.hasFinished()
					|| !player.getMapRegionsIds().contains(npc.getRegionId())
					|| !npc.getDefinitions().hasAttackOption()) {
				return;
			}
			if(npc.getId() == 13457){
				player.sm("It's not a good idea to kill our guards...");
				return;
			}
			if (!player.getControlerManager().canAttack(npc)) {
				return;
			}
			int slayerLevel = Combat.getSlayerLevelForNPC(npc.getId());
			if (slayerLevel > player.getSkills().getLevel(Skills.SLAYER)) {
			player.getPackets().sendGameMessage("You need at least a slayer level of "+ slayerLevel + " to fight this.");
							return;
						}
			if (player.isPker) {
				if (player.getControlerManager().getControler() instanceof Wilderness) {
					;
				} else {
					return;
				}
			}
			if (npc instanceof Familiar) {
				Familiar familiar = (Familiar) npc;
				if (familiar == player.getFamiliar()) {
					player.getPackets().sendGameMessage(
							"You can't attack your own familiar.");
					return;
				}
				if (!familiar.canAttack(player)) {
					player.getPackets().sendGameMessage(
							"You can't attack this npc.");
					return;
				}
			} else if (!npc.isForceMultiAttacked()) {
				if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
					if (player.getAttackedBy() != npc
							&& player.getAttackedByDelay() > Utils
							.currentTimeMillis()) {
						player.getPackets().sendGameMessage(
								"You are already in combat.");
						return;
					}
					if (npc.getAttackedBy() != player
							&& npc.getAttackedByDelay() > Utils
							.currentTimeMillis()) {
						player.getPackets().sendGameMessage(
								"This npc is already in combat.");
						return;
					}
				}
			}
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerCombat(npc));
		} else if (packetId == INTERFACE_ON_PLAYER) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			@SuppressWarnings("unused")
			int junk1 = stream.readUnsignedShort();
			int playerIndex = stream.readUnsignedShort();
			int interfaceHash = stream.readIntV2();
			@SuppressWarnings("unused")
			int junk2 = stream.readUnsignedShortLE128();
			@SuppressWarnings("unused")
			boolean unknown = stream.read128Byte() == 1;
			int interfaceId = interfaceHash >> 16;
			int componentId = interfaceHash - (interfaceId << 16);
			int slot = stream.readUnsignedShortLE128();
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (componentId == 65535)
				componentId = -1;
			if (componentId != -1
					&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId)
				return;
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			player.stopAll(false);
			switch (interfaceId) {
			case 1110:
			    if (componentId == 87)
				ClansManager.invite(player, p2);
			    break;
			case Inventory.INVENTORY_INTERFACE:// Item on player
				if (!player.getControlerManager()
						.processItemOnPlayer(p2, junk1))
					return;
				InventoryOptionsHandler.handleItemOnPlayer(player, p2, junk1);
				break;
			case 662:
			case 747:
				if (player.getFamiliar() == null)
					return;
				player.resetWalkSteps();
				if ((interfaceId == 747 && componentId == 15)
						|| (interfaceId == 662 && componentId == 65)
						|| (interfaceId == 662 && componentId == 74)
						|| interfaceId == 747 && componentId == 18) {
					if ((interfaceId == 662 && componentId == 74
							|| interfaceId == 747 && componentId == 24 || interfaceId == 747
							&& componentId == 18)) {
						if (player.getFamiliar().getSpecialAttack() != SpecialAttack.ENTITY)
							return;
					}
					
					if ((player.isPker && !p2.isPker) || (!player.isPker && p2.isPker)) {
						player.getPackets().sendGameMessage("Pkers cannot attack economy players.");
						return;
					}
					if (!player.isCanPvp() || !p2.isCanPvp()) {
						player.getPackets()
						.sendGameMessage(
								"You can only attack players in a player-vs-player area.");
						return;
					}
					if (!player.getFamiliar().canAttack(p2)) {
						player.getPackets()
						.sendGameMessage(
								"You can only use your familiar in a multi-zone area.");
						return;
					} else {
						player.getFamiliar().setSpecial(
								interfaceId == 662 && componentId == 74
								|| interfaceId == 747
								&& componentId == 18);
						player.getFamiliar().setTarget(p2);
					}
				}
				break;
			case 193:
				switch (componentId) {
				case 28:
				case 32:
				case 24:
				case 20:
				case 30:
				case 34:
				case 26:
				case 22:
				case 29:
				case 33:
				case 25:
				case 21:
				case 31:
				case 35:
				case 27:
				case 23:
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(p2
								.getCoordFaceX(p2.getSize()), p2
								.getCoordFaceY(p2.getSize()), p2.getPlane()));
						if (!player.getControlerManager().canAttack(p2))
							return;
						if ((player.isPker && !p2.isPker) || (!player.isPker && p2.isPker)) {
							player.getPackets().sendGameMessage("Pkers cannot attack economy players.");
							return;
						}
						if (!player.isCanPvp() || !p2.isCanPvp()) {
							player.getPackets()
							.sendGameMessage(
									"You can only attack players in a player-vs-player area.");
							return;
						}
						if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
							if (player.getAttackedBy() != p2
									&& player.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								player.getPackets()
								.sendGameMessage(
										"That "
												+ (player
														.getAttackedBy() instanceof Player ? "player"
																: "npc")
																+ " is already in combat.");
								return;
							}
							if (p2.getAttackedBy() != player
									&& p2.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								if (p2.getAttackedBy() instanceof NPC) {
									p2.setAttackedBy(player); // changes enemy
									// to player,
									// player has
									// priority over
									// npc on single
									// areas
								} else {
									player.getPackets()
									.sendGameMessage(
											"That player is already in combat.");
									return;
								}
							}
						}
						player.getActionManager()
						.setAction(new PlayerCombat(p2));
					}
					break;
				}
			case 192:
				switch (componentId) {
				case 25: // air strike
				case 28: // water strike
				case 30: // earth strike
				case 32: // fire strike
				case 34: // air bolt
				case 39: // water bolt
				case 42: // earth bolt
				case 45: // fire bolt
				case 49: // air blast
				case 52: // water blast
				case 56: // magic dart
				case 58: // earth blast
				case 63: // fire blast
				case 70: // air wave
				case 73: // water wave
				case 77: // earth wave
				case 80: // fire wave
				case 86: // teleblock
				case 84: // air surge
				case 87: // water surge
				case 89: // earth surge
				case 91: // fire surge
				case 99: // storm of armadyl
				case 36: // bind
				case 66: // Sara Strike
				case 67: // Guthix Claws
				case 68: // Flame of Zammy
				case 55: // snare
				case 81: // entangle
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(p2
								.getCoordFaceX(p2.getSize()), p2
								.getCoordFaceY(p2.getSize()), p2.getPlane()));
						if (!player.getControlerManager().canAttack(p2))
							return;
						if ((player.isPker && !p2.isPker) || (!player.isPker && p2.isPker)) {
							player.getPackets().sendGameMessage("Pkers cannot attack economy players.");
							return;
						}
						if (!player.isCanPvp() || !p2.isCanPvp()) {
							player.getPackets()
							.sendGameMessage(
									"You can only attack players in a player-vs-player area.");
							return;
						}
						if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
							if (player.getAttackedBy() != p2
									&& player.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								player.getPackets()
								.sendGameMessage(
										"That "
												+ (player
														.getAttackedBy() instanceof Player ? "player"
																: "npc")
																+ " is already in combat.");
								return;
							}
							if (p2.getAttackedBy() != player
									&& p2.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								if (p2.getAttackedBy() instanceof NPC) {
									p2.setAttackedBy(player); // changes enemy
									// to player,
									// player has
									// priority over
									// npc on single
									// areas
								} else {
									player.getPackets()
									.sendGameMessage(
											"That player is already in combat.");
									return;
								}
							}
						}
						player.getActionManager()
						.setAction(new PlayerCombat(p2));
					}
					break;
				}
				break;
			}
			String message = "Spell:" + componentId;
			Logger.logMessage(message);
			if (Settings.DEBUG)
				System.out.println(message);
		} else if (packetId == INTERFACE_ON_NPC) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int interfaceHash = stream.readInt();
			int npcIndex = stream.readUnsignedShortLE();
			int interfaceSlot = stream.readUnsignedShortLE128();
			@SuppressWarnings("unused")
			int junk2 =stream.readUnsignedShortLE();
			int interfaceId = interfaceHash >> 16;
			int componentId = interfaceHash - (interfaceId << 16);
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (componentId == 65535)
				componentId = -1;
			if (componentId != -1
					&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId)
				return;
			NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null || npc.isDead() || npc.hasFinished()
					|| !player.getMapRegionsIds().contains(npc.getRegionId()))
				return;
			player.stopAll(false);
			if (interfaceId != Inventory.INVENTORY_INTERFACE) {
				if (player.isPker) {
					if (player.getControlerManager().getControler() instanceof Wilderness) {
						;
					} else {
						return;
					}
				}
				if (!npc.getDefinitions().hasAttackOption()) {
					player.getPackets().sendGameMessage(
							"You can't attack this npc.");
					return;
				}
			}
			switch (interfaceId) {
			case Inventory.INVENTORY_INTERFACE:
				Item item = player.getInventory().getItem(interfaceSlot);
				if (item == null || !player.getControlerManager().processItemOnNPC(npc, item))
					return;
				InventoryOptionsHandler.handleItemOnNPC(player, npc, item);
				break;
			case 1165:
				//Summoning.attackDreadnipTarget(npc, player);
				break;
			case 662:
			case 747:
				if (player.getFamiliar() == null)
					return;
				player.resetWalkSteps();
				if ((interfaceId == 747 && componentId == 15)
						|| (interfaceId == 662 && componentId == 65)
						|| (interfaceId == 662 && componentId == 74)
						|| interfaceId == 747 && componentId == 18
						|| interfaceId == 747 && componentId == 24) {
					if ((interfaceId == 662 && componentId == 74 || interfaceId == 747
							&& componentId == 18)) {
						if (player.getFamiliar().getSpecialAttack() != SpecialAttack.ENTITY)
							return;
					}
					if(npc instanceof Familiar) {
						Familiar familiar = (Familiar) npc;
						if (familiar == player.getFamiliar()) {
							player.getPackets().sendGameMessage("You can't attack your own familiar.");
							return;
						}
						if (!player.getFamiliar().canAttack(familiar.getOwner())) {
							player.getPackets().sendGameMessage("You can only attack players in a player-vs-player area.");
							return;
						}
					}
					if (!player.getFamiliar().canAttack(npc)) {
						player.getPackets()
						.sendGameMessage(
								"You can only use your familiar in a multi-zone area.");
						return;
					} else {
						player.getFamiliar().setSpecial(
								interfaceId == 662 && componentId == 74
								|| interfaceId == 747
								&& componentId == 18);
						player.getFamiliar().setTarget(npc);
					}
				}
				break;
			case 193:
				switch (componentId) {
				case 28:
				case 32:
				case 24:
				case 20:
				case 30:
				case 34:
				case 26:
				case 22:
				case 29:
				case 33:
				case 25:
				case 21:
				case 31:
				case 35:
				case 27:
				case 23:
					if (player.isPker) {
						if (player.getControlerManager().getControler() instanceof Wilderness) {
							;
						} else {
							return;
						}
					}
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(npc
								.getCoordFaceX(npc.getSize()), npc
								.getCoordFaceY(npc.getSize()), npc.getPlane()));
						if (!player.getControlerManager().canAttack(npc))
							return;
						if (npc instanceof Familiar) {
							Familiar familiar = (Familiar) npc;
							if (familiar == player.getFamiliar()) {
								player.getPackets().sendGameMessage(
										"You can't attack your own familiar.");
								return;
							}
							if (!familiar.canAttack(player)) {
								player.getPackets().sendGameMessage(
										"You can't attack this npc.");
								return;
							}
						} else if (!npc.isForceMultiAttacked()) {
							if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
								if (player.getAttackedBy() != npc
										&& player.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.getPackets().sendGameMessage(
											"You are already in combat.");
									return;
								}
								if (npc.getAttackedBy() != player
										&& npc.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.getPackets().sendGameMessage(
											"This npc is already in combat.");
									return;
								}
							}
						}
						player.getActionManager().setAction(
								new PlayerCombat(npc));
					}
					break;
				}
			case 192:
				switch (componentId) {
				case 25: // air strike
				case 28: // water strike
				case 30: // earth strike
				case 32: // fire strike
				case 34: // air bolt
				case 39: // water bolt
				case 42: // earth bolt
				case 45: // fire bolt
				case 49: // air blast
				case 52: // water blast
				case 58: // earth blast
				case 63: // fire blast
				case 70: // air wave
				case 73: // water wave
				case 77: // earth wave
				case 80: // fire wave
				case 84: // air surge
				case 87: // water surge
				case 89: // earth surge
				case 66: // Sara Strike
				case 67: // Guthix Claws
				case 68: // Flame of Zammy
				case 93:
				case 91: // fire surge
				case 99: // storm of Armadyl
				case 36: // bind
				case 55: // snare
				case 81: // entangle
					if (player.isPker) {
						if (player.getControlerManager().getControler() instanceof Wilderness) {
							;
						} else {
							return;
						}
					}
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(npc
								.getCoordFaceX(npc.getSize()), npc
								.getCoordFaceY(npc.getSize()), npc.getPlane()));
						if (!player.getControlerManager().canAttack(npc))
							return;
						if (npc instanceof Familiar) {
							Familiar familiar = (Familiar) npc;
							if (familiar == player.getFamiliar()) {
								player.getPackets().sendGameMessage(
										"You can't attack your own familiar.");
								return;
							}
							if (!familiar.canAttack(player)) {
								player.getPackets().sendGameMessage(
										"You can't attack this npc.");
								return;
							}
						} else if (!npc.isForceMultiAttacked()) {
							if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
								if (player.getAttackedBy() != npc
										&& player.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.getPackets().sendGameMessage(
											"You are already in combat.");
									return;
								}
								if (npc.getAttackedBy() != player
										&& npc.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.getPackets().sendGameMessage(
											"This npc is already in combat.");
									return;
								}
							}
						}
						player.getActionManager().setAction(
								new PlayerCombat(npc));
					}
					break;
				}
				break;
			}
			String message = "Spell:" + componentId;
			Logger.logMessage(message);
			if (Settings.DEBUG)
				System.out.println(message);
		} else if (packetId == NPC_CLICK1_PACKET){
			NPCHandler.handleOption1(player, stream);
			return;
		}else if (packetId == NPC_CLICK2_PACKET){
			NPCHandler.handleOption2(player, stream);
			return;
		}else if (packetId == NPC_CLICK3_PACKET){
			NPCHandler.handleOption3(player, stream);
			return;
		}else if (packetId == NPC_CLICK4_PACKET)
			NPCHandler.handleOption4(player, stream);
		else if (packetId == OBJECT_CLICK1_PACKET)
			ObjectHandler.handleOption(player, stream, 1);
		else if (packetId == OBJECT_CLICK2_PACKET)
			ObjectHandler.handleOption(player, stream, 2);
		else if (packetId == OBJECT_CLICK3_PACKET)
			ObjectHandler.handleOption(player, stream, 3);
		else if (packetId == OBJECT_CLICK4_PACKET)
			ObjectHandler.handleOption(player, stream, 4);
		else if (packetId == OBJECT_CLICK5_PACKET)
			ObjectHandler.handleOption(player, stream, 5);
		else if (packetId == ITEM_TAKE_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			long currentTime = Utils.currentTimeMillis();
			if (player.getLockDelay() > currentTime)
				// || player.getFreezeDelay() >= currentTime)
				return;
			int y = stream.readUnsignedShort();
			int x = stream.readUnsignedShortLE();
			final int id = stream.readInt();
			boolean forceRun = stream.read128Byte() == 1;
			final WorldTile tile = new WorldTile(x, y, player.getPlane());
			final int regionId = tile.getRegionId();
			if (!player.getMapRegionsIds().contains(regionId))
				return;
			
			final FloorItem item = World.getRegion(regionId).getGroundItem(id,
					tile, player);
			if (item == null)
				return;
			player.stopAll(false);
			if(forceRun)
				player.setRun(forceRun);
			player.setRouteEvent(new RouteEvent(item, new Runnable() {
				@Override
				public void run() {
					final FloorItem item = World.getRegion(regionId)
							.getGroundItem(id, tile, player);
					if (item == null)
						return;
					if (item.getOwner() != null && (player.getRights() > 0 || player.isSupporter())) 
						player.getPackets().sendGameMessage("This item was dropped by [Username] "+item.getOwner()+ " [DiplayName] "+item.getOwner());
					player.setNextFaceWorldTile(tile);
					player.addWalkSteps(tile.getX(), tile.getY(), 1);
					World.removeGroundItem(player, item);
				}
			}));
		}
	}
	
	
	public Player getPlayer() {
		return player;
	}
	public void sendInterface(int i, int windowId,
			int windowComponentId, int interfaceId) {
		// currently fixes the inter engine.. not ready for same component
		// ids(tabs), different inters
		if (!(windowId == 752 && (windowComponentId == 9 || windowComponentId == 12))) { // if
																							// chatbox
			if (player.getInterfaceManager().containsInterface(
					windowComponentId, interfaceId))
				closeInterface(windowComponentId);
			if (!player.getInterfaceManager().addInterface(windowId,
					windowComponentId, interfaceId)) {
				Logger.log(this, "Error adding interface: " + windowId + " , "
						+ windowComponentId + " , " + interfaceId);
				return;
			}
		}
	}
	public void closeInterface(int windowComponentId) {
		closeInterface(
				player.getInterfaceManager().getTabWindow(windowComponentId));
		player.getInterfaceManager().removeTab(windowComponentId);
	}

	public void processPackets(final int packetId, InputStream stream,int length) throws IOException {
		player.setPacketsDecoderPing(Utils.currentTimeMillis());
		if (packetId == PING_PACKET) {
			// kanker we ping :) NO ALEX
			OutputStream packet = new OutputStream(0);
			packet.writePacket(player, 153);
			player.getSession().write(packet);
		} else if (packetId == MOUVE_MOUSE_PACKET) {
			// USELESS PACKET
		} else if (packetId == KEY_TYPED_PACKET) {
            if(player.useKeyBinds = true) {
			PlayerKeyBinds.getKeyBinds(stream, player);
		 }
		} else if (packetId == RECEIVE_PACKET_COUNT_PACKET) {
			// interface packets
			stream.readInt();
		} else if (packetId == ITEM_ON_ITEM_PACKET) {
			InventoryOptionsHandler.handleItemOnItem(player, stream);
		}  else if (packetId == AFK_PACKET) {
			player.getSession().getChannel().close();
		} else if (packetId == CLOSE_INTERFACE_PACKET) {
			if (player.hasStarted() && !player.hasFinished() && !player.isRunning()) { //used for old welcome screen
				player.run();
				return;
			}
			player.stopAll();
		} else if (packetId == MOVE_CAMERA_PACKET) {
			// not using it atm
			stream.readUnsignedShort();
			stream.readUnsignedShort();
		} else if (packetId == IN_OUT_SCREEN_PACKET) {
			// not using this check because not 100% efficient
			@SuppressWarnings("unused")
			boolean inScreen = stream.readByte() == 1;
		} else if (packetId == SCREEN_PACKET) {
			int displayMode = stream.readUnsignedByte();
			player.setScreenWidth(stream.readUnsignedShort());
			player.setScreenHeight(stream.readUnsignedShort());
			@SuppressWarnings("unused")
			boolean switchScreenMode = stream.readUnsignedByte() == 1;
			if (!player.hasStarted() || player.hasFinished()
					|| displayMode == player.getDisplayMode()
					|| !player.getInterfaceManager().containsInterface(742))
				return;
			player.setDisplayMode(displayMode);
			player.getInterfaceManager().removeAll();
			player.getInterfaceManager().sendInterfaces();
			player.getInterfaceManager().sendInterface(742);
		} else if (packetId == CLICK_PACKET) {
			int mouseHash = stream.readShortLE128();
			int mouseButton = mouseHash >> 15;
			int time = mouseHash - (mouseButton << 15); // time
			int positionHash = stream.readIntV1();
			int y = positionHash >> 16; // y;
			int x = positionHash - (y << 16); // x
			@SuppressWarnings("unused")
			boolean clicked;
			// mass click or stupid autoclicker, lets stop lagg
			if (time <= 1 || x < 0 || x > player.getScreenWidth() || y < 0
					|| y > player.getScreenHeight()) {
				// player.getSession().getChannel().close();
				clicked = false;
				return;
			}
			clicked = true;
		} else if (packetId == DIALOGUE_CONTINUE_PACKET) {
			int interfaceHash = stream.readInt();
			int junk = stream.readShort128();
			int interfaceId = interfaceHash >> 16;
			int buttonId = (interfaceHash & 0xFF);
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId) { //do not remove 
				return;
			}
			if (!player.isRunning()|| !player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if(Settings.DEBUG)
				Logger.log(this, "Dialogue: "+interfaceId+", "+buttonId+", "+junk);
			int componentId = interfaceHash - (interfaceId << 16);
			if (interfaceId == 667) {
				CosmeticsHandler.openCosmeticsHandler(player);
				if (junk == Equipment.SLOT_SHIELD) {
					Item weapon = player.getEquipment().getCosmeticItems().get(Equipment.SLOT_WEAPON);
					if (weapon != null) {
						boolean isTwoHandedWeapon = Equipment.isTwoHandedWeapon(weapon);
						if (isTwoHandedWeapon) {
							player.getPackets().sendGameMessage("You have a two handed cosmetic weapon. You can't edit shield slot unless you remove it.");
							return;
						}
					}
				}
				if (junk == Equipment.SLOT_RING && player.getEquipment().getSavedCosmetics().isEmpty()) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any saved costumes. To save your current costume do ::savecurrentcostume or ::savecurrentcosmetic .");
					return;
				}
				player.getDialogueManager().startDialogue((player.isShowSearchOption() && player.getEquipment().getCosmeticItems().get(junk) == null) ? "CosmeticsSelect" : "CosmeticsDialogue", junk);
			} else
				player.getDialogueManager().continueDialogue(interfaceId, componentId);
		} else if (packetId == WORLD_MAP_CLICK) {
			int coordinateHash = stream.readInt();
			int x = coordinateHash >> 14;
			int y = coordinateHash & 0x3fff;
			int plane = coordinateHash >> 28;
            Integer hash  =  (Integer)player.getTemporaryAttributtes().get("worldHash");
			if (hash == null || coordinateHash != hash)
				player.getTemporaryAttributtes().put("worldHash", coordinateHash);
			else {
				player.getTemporaryAttributtes().remove("worldHash");
				player.getHintIconsManager().addHintIcon(x, y, plane, 20, 0, 2, -1, true);
				player.getPackets().sendConfig(1159, coordinateHash);
            }
		} else if (packetId == WORLD_LIST_UPDATE) {
			int updateType = stream.readInt();
			player.getPackets().sendWorldList(updateType == 0);
		} else if (packetId == ACTION_BUTTON1_PACKET
				|| packetId == ACTION_BUTTON2_PACKET
				|| packetId == ACTION_BUTTON4_PACKET
				|| packetId == ACTION_BUTTON5_PACKET
				|| packetId == ACTION_BUTTON6_PACKET
				|| packetId == ACTION_BUTTON7_PACKET
				|| packetId == ACTION_BUTTON8_PACKET
				|| packetId == ACTION_BUTTON3_PACKET
				|| packetId == ACTION_BUTTON9_PACKET
				|| packetId == ACTION_BUTTON10_PACKET) {
			ButtonHandler.handleButtons(player, stream, packetId);
		}   else if (packetId == ENTER_LONG_TEXT_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			String value = stream.readString();
			if (value.equals(""))
				return;
			
			if (player.getTemporaryAttributtes().get("email_input") == Boolean.TRUE) {
				 player.getEmailManager();
				EmailManager.isValidEmailAddress(value,player);
				//EmailManager.isValidEmailAddress(value,player);
				player.getTemporaryAttributtes().put("email_input", Boolean.FALSE);
			}
			if (player.getTemporaryAttributtes().get("email_code") == Boolean.TRUE) {
			     player.getEmailManager().validateCode(value,player);
				//EmailManager.validateCode(value,player);
				player.getTemporaryAttributtes().put("email_code", Boolean.FALSE);
			}
			
			else if (player.getTemporaryAttributtes().get("npcName_lookup") == Boolean.TRUE) {	   
	        	 player.getTemporaryAttributtes().put("npcName_lookup", Boolean.FALSE);
	        	 StringBuilder npcNameSB = new StringBuilder(value);
					if (value.length() > 1) {
						for (int i = 2; i < value.length(); i++) {
							npcNameSB.append(" ").append(value);
						}
					}
				String npcName = npcNameSB.toString().toLowerCase().replace("[", "(").replace("]", ")").replaceAll(",", "'");
				for (int i = 0; i < Utils.getNPCDefinitionsSize(); i++) {
					NPCDefinitions def = NPCDefinitions.getNPCDefinitions(i);
					if (def.name.toLowerCase().equalsIgnoreCase(npcName)) {
						player.stopAll();
						player.getInterfaceManager().sendNPCDrops(def);
					}
				}
				player.sendMessage("Could not find any NPC by the name of ''" + npcName + "''.");
	 }
			
			/**
			  * skilling competitions
			  **/
			// To cherch the item in your inventory
			if (player.getTemporaryAttributtes().get("tourn_item") == Boolean.TRUE) {
				player.getDialogueManager().startDialogue("SearchItem", value);
				player.getTemporaryAttributtes().put("tourn_item", Boolean.FALSE);
			}
			//to pick a skill.
			if (player.getTemporaryAttributtes().get("tourn_skill") == Boolean.TRUE) {
				int skillId = -1;
				for(int i=0;i<Skills.SKILL_NAME.length;i++) {
					skillId++;
					if(Skills.SKILL_NAME[i].toLowerCase().contains(value.toLowerCase())) {
						player.setSkill = skillId;
						player.getDialogueManager().startDialogue("TournamentsD");
						return;
					}
				}
				player.getDialogueManager().startDialogue("SimpleMessage", "Keyword: "+value, "Could not find a skill for your keyword");
				player.getTemporaryAttributtes().put("tourn_skill", Boolean.FALSE);
			}
			
			//names of presets
			else if (player.getTemporaryAttributtes().get("preset1") == Boolean.TRUE) {
				if(value.length() > 20) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Names are limited to 20 characters due to spam.");
				} if(value.length() <= 20) {
					player.preset1 = value;
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this name, "+player.preset1+".");
				}
				player.getTemporaryAttributtes().put("preset1", Boolean.FALSE);
			}
			else if (player.getTemporaryAttributtes().get("preset2") == Boolean.TRUE) {
				if(value.length() > 20) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Names are limited to 20 characters due to spam.");
				} if(value.length() <= 20) {
					player.preset2 = value;
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this name, "+player.preset2+".");
				}
				player.getTemporaryAttributtes().put("preset2", Boolean.FALSE);
			}
			else if (player.getTemporaryAttributtes().get("preset3") == Boolean.TRUE) {
				if(value.length() > 20) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Names are limited to 20 characters due to spam.");
				} if(value.length() <= 20) {
					player.preset3 = value;
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this name, "+player.preset3+".");
				}
				player.getTemporaryAttributtes().put("preset3", Boolean.FALSE);
			}
			else if (player.getTemporaryAttributtes().get("preset4") == Boolean.TRUE) {
				if(value.length() > 20) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Names are limited to 20 characters due to spam.");
				} if(value.length() <= 20) {
					player.preset4 = value;
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this name, "+player.preset4+".");
				}
				player.getTemporaryAttributtes().put("preset4", Boolean.FALSE);
			}
			else if (player.getTemporaryAttributtes().get("preset5") == Boolean.TRUE) {
				if(value.length() > 20) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Names are limited to 20 characters due to spam.");
				} if(value.length() <= 20) {
					player.preset5 = value;
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this name, "+player.preset5+".");
				}
				player.getTemporaryAttributtes().put("preset5", Boolean.FALSE);
			}
			
			/**
			  * Start pin secure rooms.
			  **/
			else if (player.getTemporaryAttributtes().get("roompin") == Boolean.TRUE) {
				if(value.length() > 8) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Pins are limted to eight characters due to spam.");
				} if(value.length() <= 8) {
					player.setRoompin(value);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this pin, "+player.getRoompin()+".");
					player.sm("Your pin: "+player.getRoompin()+". do ::tellpin if you forgot it.");
					player.getControlerManager().startControler("BandosControler");
				}
				player.getTemporaryAttributtes().put("roompin", Boolean.FALSE);
			}
	    /**
		  * Armadyl
		  **/
		  else if (player.getTemporaryAttributtes().get("roompin_armadyl") == Boolean.TRUE) {
				if(value.length() > 8) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Pins are limted to eight characters due to spam.");
				} if(value.length() <= 8) {
					player.setRoompin(value);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this pin, "+player.getRoompin()+".");
					player.sm("Your pin: "+player.getRoompin()+". do ::tellpin if you forgot it.");
					player.getControlerManager().startControler("ArmadylControler");
				}
				player.getTemporaryAttributtes().put("roompin_armadyl", Boolean.FALSE);
			}
		  else if (player.getTemporaryAttributtes().get("find_player_armadylroom") == Boolean.TRUE) {	
			  String name = value;
	            Player target = World.getPlayerByDisplayName(name);
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find " + name + ".");
	            player.getTemporaryAttributtes().put("find_player_armadylroom", Boolean.FALSE);
	            } else
	            	player.setRoomplayer(value);
	            	 player.getTemporaryAttributtes().put("find_player_armadylroom", Boolean.FALSE);
	            	 player.getDialogueManager().startDialogue("SimpleMessage", "You succesfully entered his pin.");
	            	 player.getAttributes().put("enter_roompin_other_armadyl", true);
	     			player.getPackets().sendInputLongTextScript("Enter his/her pin.");
	            	 return;
		 }
		 else if (player.getTemporaryAttributtes().get("enter_roompin_other_armadyl") == Boolean.TRUE) {	
			  String entered_pin = value;
	            Player target = World.getPlayerByDisplayName(player.getRoomplayer());
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find target.");
	            player.getTemporaryAttributtes().put("enter_roompin_other_armadyl", Boolean.FALSE);
	            }
	            if (entered_pin.equals(target.getRoompin())){
	            	player.getControlerManager().startControler("ArmadylControler");
	            	player.setRoomplayer(value);
	            	player.getTemporaryAttributtes().put("enter_roompin_other_armadyl", Boolean.FALSE);
	            	player.setNextWorldTile(target);
	            return;
	            } else {
	            	 player.getDialogueManager().startDialogue("SimpleMessage", "Wrong pin/name.");	
	            	 player.getTemporaryAttributtes().put("enter_roompin_other_armadyl", Boolean.FALSE);
	            }
	            	
		 }
		 //love notes
		  else if (player.getTemporaryAttributtes().get("lovenote_player") == Boolean.TRUE) {	
			    String name = value;
	            Player target = World.getPlayerByDisplayName(name);
	            player.sendNoteTo = value;
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find " + name + ". Make sure that your target is online.");
	            player.getTemporaryAttributtes().put("lovenote_player", Boolean.FALSE);
	            } else
					 player.getTemporaryAttributtes().put("lovenote_player" ,Boolean.FALSE);
	            	 player.getTemporaryAttributtes().put("lovenote_message", Boolean.TRUE);
					  player.getPackets().sendInputLongTextScript("Now enter your note.");	
	            	
	            	 return;
		 } else if (player.getTemporaryAttributtes().get("lovenote_message") == Boolean.TRUE) {	
			    String message = value;
			    if(value.length() == 0) {
			    	player.getDialogueManager().startDialogue("SimpleMessage", "You can't send an empty note.");
			    } else 
					 player.getTemporaryAttributtes().put("lovenote_message" ,Boolean.FALSE);
	            	 HolidayEvents.receiveLoveNote(player, message, player.sendNoteTo);
	            	 return;
		 }
		/**
		  * Bandos 
		  **/
		  else if (player.getTemporaryAttributtes().get("roompin_bandos") == Boolean.TRUE) {
				if(value.length() > 8) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Pins are limted to eight characters due to spam.");
				} if(value.length() <= 8) {
					player.setRoompin(value);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this pin, "+player.getRoompin()+".");
					player.sm("Your pin: "+player.getRoompin()+". do ::tellpin if you forgot it.");
					player.getControlerManager().startControler("BandosControler");
				}
				player.getTemporaryAttributtes().put("roompin_bandos", Boolean.FALSE);
			}
		 else if (player.getTemporaryAttributtes().get("find_player_bandosroom") == Boolean.TRUE) {	
			  String name = value;
	            Player target = World.getPlayerByDisplayName(name);
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find " + name + ".");
	            player.getTemporaryAttributtes().put("find_player_bandosroom", Boolean.FALSE);
	            } else
	            	player.setRoomplayer(value);
	            	 player.getTemporaryAttributtes().put("find_player_bandosroom", Boolean.FALSE);
	            	 player.getDialogueManager().startDialogue("SimpleMessage", "You succesfully entered his pin.");
	            	 player.getAttributes().put("enter_roompin_other_bandos", true);
	     			player.getPackets().sendInputLongTextScript("Enter his/her pin.");
	            	 return;
		 }
		 else if (player.getTemporaryAttributtes().get("enter_roompin_other_bandos") == Boolean.TRUE) {	
			  String entered_pin = value;
	            Player target = World.getPlayerByDisplayName(player.getRoomplayer());
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find target.");
	            player.getTemporaryAttributtes().put("enter_roompin_other_bandos", Boolean.FALSE);
	            }
	            if (entered_pin.equals(target.getRoompin())){
	            	player.getControlerManager().startControler("BandosControler");
	            	player.setRoomplayer(value);
	            	player.getTemporaryAttributtes().put("enter_roompin_other_bandos", Boolean.FALSE);
	            	player.setNextWorldTile(target);
	            return;
	            } else {
	            	 player.getDialogueManager().startDialogue("SimpleMessage", "Wrong pin/name.");	
	            	 player.getTemporaryAttributtes().put("enter_roompin_other_bandos", Boolean.FALSE);
	            }
	            	
		 }
		 /**
		   * Saradomin
		   **/
		    else if (player.getTemporaryAttributtes().get("roompin_saradomin") == Boolean.TRUE) {
				if(value.length() > 8) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Pins are limted to eight characters due to spam.");
				} if(value.length() <= 8) {
					player.setRoompin(value);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this pin, "+player.getRoompin()+".");
					player.sm("Your pin: "+player.getRoompin()+". do ::tellpin if you forgot it.");
					player.getControlerManager().startControler("SaradominControler");
				}
				player.getTemporaryAttributtes().put("roompin_saradomin", Boolean.FALSE);
			}
		   else if (player.getTemporaryAttributtes().get("find_player_saradominroom") == Boolean.TRUE) {	
			  String name = value;
	            Player target = World.getPlayerByDisplayName(name);
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find " + name + ".");
	            player.getTemporaryAttributtes().put("find_player_saradominroom", Boolean.FALSE);
	            } else
	            	player.setRoomplayer(value);
	            	 player.getTemporaryAttributtes().put("find_player_saradominroom", Boolean.FALSE);
	            	 player.getDialogueManager().startDialogue("SimpleMessage", "You succesfully entered his pin.");
	            	 player.getAttributes().put("enter_roompin_other_saradomin", true);
	     			player.getPackets().sendInputLongTextScript("Enter his/her pin.");
	            	 return;
		 }
		 else if (player.getTemporaryAttributtes().get("enter_roompin_other_saradomin") == Boolean.TRUE) {	
			  String entered_pin = value;
	            Player target = World.getPlayerByDisplayName(player.getRoomplayer());
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find target.");
	            player.getTemporaryAttributtes().put("enter_roompin_other_saradomin", Boolean.FALSE);
	            }
	            if (entered_pin.equals(target.getRoompin())){
	            	player.getControlerManager().startControler("SaradominControler");
	            	player.setRoomplayer(value);
	            	player.getTemporaryAttributtes().put("enter_roompin_other_saradomin", Boolean.FALSE);
	            	player.setNextWorldTile(target);
	            return;
	            } else {
	            	 player.getDialogueManager().startDialogue("SimpleMessage", "Wrong pin/name.");	
	            	 player.getTemporaryAttributtes().put("enter_roompin_other_saradomin", Boolean.FALSE);
	            }
	            	
		 }
		 /**
		   * Zamorak
		   **/
		   else if (player.getTemporaryAttributtes().get("roompin_zamorak") == Boolean.TRUE) {
				if(value.length() > 8) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Pins are limted to eight characters due to spam.");
				} if(value.length() <= 8) {
					player.setRoompin(value);
					player.getDialogueManager().startDialogue("SimpleMessage", "You have enter this pin, "+player.getRoompin()+".");
					player.sm("Your pin: "+player.getRoompin()+". do ::tellpin if you forgot it.");
					player.getControlerManager().startControler("ZamorakControler");
				}
				player.getTemporaryAttributtes().put("roompin_zamorak", Boolean.FALSE);
			}
		 else if (player.getTemporaryAttributtes().get("find_player_zamorakroom") == Boolean.TRUE) {	
			  String name = value;
	            Player target = World.getPlayerByDisplayName(name);
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find " + name + ".");
	            player.getTemporaryAttributtes().put("find_player_zamorakroom", Boolean.FALSE);
	            } else
	            	player.setRoomplayer(value);
	            	 player.getTemporaryAttributtes().put("find_player_zamorakroom", Boolean.FALSE);
	            	 player.getDialogueManager().startDialogue("SimpleMessage", "You succesfully entered his pin.");
	            	 player.getAttributes().put("enter_roompin_other_zamorak", true);
	     			player.getPackets().sendInputLongTextScript("Enter his/her pin.");
	            	 return;
		 }
		 else if (player.getTemporaryAttributtes().get("enter_roompin_other_zamorak") == Boolean.TRUE) {	
			  String entered_pin = value;
	            Player target = World.getPlayerByDisplayName(player.getRoomplayer());
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find target.");
	            player.getTemporaryAttributtes().put("enter_roompin_other_zamorak", Boolean.FALSE);
	            }
	            if (entered_pin.equals(target.getRoompin())){
	            	player.getControlerManager().startControler("ZamorakControler");
	            	player.setRoomplayer(value);
	            	player.getTemporaryAttributtes().put("enter_roompin_other_zamorak", Boolean.FALSE);
	            	player.setNextWorldTile(target);
	            return;
	            } else {
	            	 player.getDialogueManager().startDialogue("SimpleMessage", "Wrong pin/name.");	
	            	 player.getTemporaryAttributtes().put("enter_roompin_other_zamorak", Boolean.FALSE);
	            }
	            	
		 } 
		
		/**
		  * Other players droplog if he's onnline.
		  **/
			else if(player.getTemporaryAttributtes().get("drop_log") == Boolean.TRUE) {
	            String name = value;
	            Player target = World.getPlayerByDisplayName(name);
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find " + name + ".");
	            player.getTemporaryAttributtes().put("drop_log", Boolean.FALSE);
	            } else {
	            player.getTemporaryAttributtes().put("drop_log", Boolean.FALSE);
	            return;
	     }
			}
			/** 
			  * Note adding
			  **/
		
	    if (player.getInterfaceManager().containsInterface(1103))
		ClansManager.setClanMottoInterface(player, value);
		} else if (packetId == ENTER_NAME_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			String value = stream.readString();
			if (value.equals(""))
				return;
			if (player.getInterfaceManager().containsInterface(1108)) 
				player.getFriendsIgnores().setChatPrefix(value);
		    else if (player.getTemporaryAttributtes().remove("setclan") != null)
				ClansManager.createClan(player, value);
			else if (player.getTemporaryAttributtes().remove("joinguestclan") != null)
				ClansManager.connectToClan(player, value, true);
			else if (player.getTemporaryAttributtes().remove("banclanplayer") != null)
				ClansManager.banPlayer(player, value);
			else if (player.getTemporaryAttributtes().remove("unbanclanplayer") != null)
				ClansManager.unbanPlayer(player, value);
			else if (player.getAttributes().get("asking_referral") != null) {
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							Misc.REFERRAL_LOCATION, true));
					writer.newLine();
					writer.write("" + player.getUsername() + " answered with: "
							+ value);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				player.setReferral(value);
				player.getAttributes().remove("asking_referral");
			 }
			else if (player.getAttributes().get("joining_house") != null) {
				House.enterHouse(player, value);
			 }
			else if (player.getTemporaryAttributtes().remove("instance_owner") != null) {
				final InstancedEncounter instance = (InstancedEncounter) player.getTemporaryAttributtes().remove("instance");
				final Instances manager = Instances.ACTIVE_INSTANCES.get(value.toLowerCase());
				if (manager == null) {
					player.getPackets().sendGameMessage(value+"'s instance was not found.");
					return;
				}
				if (manager.getInstance() != instance) {
					player.getPackets().sendGameMessage(manager.owner_name+"'s instance is not accessible from here.");
					return;
				}
				manager.joinInstance(player, null);
				//TODO account for password
			} else if (player.getTemporaryAttributtes().get("instance_password") != null) {//TODO INSTANCES
				final Object[] params = (Object[]) player.getTemporaryAttributtes().remove("instance_password");
				if (params == null)
					return;
				params[InstanceD.PASSWORD] = value;
				final InstancedEncounter instance = (InstancedEncounter) player.getTemporaryAttributtes().remove("instance");
				if (instance != null)
					player.getDialogueManager().startDialogue("InstanceD", params, instance);
			}
			else if (player.getTemporaryAttributtes().get("yellcolor") == Boolean.TRUE) {
				if(value.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX yell color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(value) || value.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested yell color can only contain numeric and regular characters.");
				} else {
					player.setYellColor(value);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your yell color has been changed to <col="+player.getYellColor()+">"+player.getYellColor()+"</col>.");
				}
				player.getTemporaryAttributtes().put("yellcolor", Boolean.FALSE);
			}
			 else if(player.getTemporaryAttributtes().get("drop_log") == Boolean.TRUE) {
	            String name = value;
	            Player target = World.getPlayerByDisplayName(name);
	            if(target == null) {
	                player.getPackets().sendGameMessage(
	                        "Couldn't find " + name + ".");
	            player.getTemporaryAttributtes().put("drop_log", Boolean.FALSE);
	            } 
			}
			else if(player.getTemporaryAttributtes().get("grand_exchange_offer") == Boolean.TRUE) {
				
			} else if (player.getTemporaryAttributtes().get("view_name") == Boolean.TRUE) {
				player.getTemporaryAttributtes().remove("view_name");
				Player other = World.getPlayerByDisplayName(value);
				if (other == null) {
					player.getPackets().sendGameMessage("Couldn't find player.");
					return;
				}
				ClanWars clan = other.getCurrentFriendChat() != null ? other.getCurrentFriendChat().getClanWars() : null;
				if (clan == null) {
					player.getPackets().sendGameMessage("This player's clan is not in war.");
					return;
				}
				if (clan.getSecondTeam().getOwnerDisplayName() != other.getCurrentFriendChat().getOwnerDisplayName()) {
					player.getTemporaryAttributtes().put("view_prefix", 1);
				}
				player.getTemporaryAttributtes().put("view_clan", clan);
				ClanWars.enter(player);
				
			}  else if (player.getAttributes().remove("searching_shop_playername") != null) {
				Player target = World.getPlayerByDisplayName(value);
				if (target != null) {
					target.getPlayerShop().open(player);
				} else {
					value = Misc.formatPlayerNameForProtocol(value);
					if (!SerializableFilesManager.containsPlayer(value)) {
						player.sendMessage(value + " is either not registered or does not have an available shop.");
						return;
					}
					target = SerializableFilesManager.loadPlayer(value);
					target.setUsername(value);
					if (target.getPlayerShop() != null) {
						target.getPlayerShop().open(player);
					} else {
						player.sendMessage("That player does not have a shop set up for themselves.");
					}
				}
			} else if (player.getTemporaryAttributtes().remove("setdisplay") != null) {
				if(Utils.invalidAccountName(Utils
						.formatPlayerNameForProtocol(value))) {
					player.getPackets().sendGameMessage("Invalid name!");
					return;
				}
				if(!DisplayNames.setDisplayName(player, value)) {
					player.getPackets().sendGameMessage("Name already in use!");
					return;
				}
				player.getPackets().sendGameMessage("Changed display name!");
			} else if (player.getTemporaryAttributtes().remove("checkvoteinput") != null);
		} else if (packetId == ENTER_STRING_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			String value = stream.readString();
			
		} else if (packetId == ENTER_LONGSTRING_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			
			String value = stream.readString();
			
			if (value.equals(""))
				return;
			

			if (player.getAttributes().get("drop_lookup") == Boolean.TRUE) {
				StringBuilder itemNameSB = new StringBuilder(value);
				if (value.length() > 1) {
					for (int i = 2; i < value.length(); i++) {
						itemNameSB.append(" ").append(value);
					}
				}
				String itemName = itemNameSB.toString().toLowerCase().replace("[", "(").replace("]", ")").replaceAll(",", "'");
				for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
					ItemDefinitions def = ItemDefinitions.getItemDefinitions(i);
					if (def.getName().toLowerCase().equalsIgnoreCase(itemName)) {
						player.stopAll();
						player.getInterfaceManager().sendItemDrops(def);
					}
				}
				
				/*String name = value;
				StringBuilder sb = new StringBuilder();
				sb.append(name);
				//StringBuilder xd = value;
			    DropSimulator.searchDrop(player, sb);
				player.getAttributes().put("drop_lookup", Boolean.FALSE);*/
				
				player.getAttributes().put("drop_lookup", Boolean.FALSE);
			}
			
		
		} else if (packetId == ENTER_INTEGER_PACKET) {
			
			if (!player.isRunning() || player.isDead())
				return;
			int value = stream.readInt();
		if (player.getTemporaryAttributtes().get("tourn_item_amount") == Boolean.TRUE) {
				player.giveItemAmount = value;
				player.getDialogueManager().startDialogue("TournamentsD");
				player.getTemporaryAttributtes().put("tourn_item_amount", Boolean.FALSE);
			}
	    if (player.getInterfaceManager().containsInterface(105) && player.getTemporaryAttributtes().remove("GEPRICESET") != null)
		player.getGeManager().modifyPricePerItem(value);
	    if (player.getInterfaceManager().containsInterface(105) && player.getTemporaryAttributtes().remove("GEQUANTITYSET") != null)
		player.getGeManager().modifyAmount(value);
			if (player.getTemporaryAttributtes().get("lend_item_time") != null) {
				if (value <= 0)
					return;
				Integer slot = (Integer) player.getTemporaryAttributtes()
						.remove("lend_item_time");
				if (value > 24) {
					player.getPackets().sendGameMessage(
							"You can only lend for a maximum of 24 hours");
					return;
				}
				player.getTrade().lendItem(slot, value);
				player.getTemporaryAttributtes().remove("lend_item_time");
				return;
			}
			Integer add_To_Trade = (Integer) player.getTemporaryAttributtes().remove("add_Money_Pouch_To_Trade");
			if (player.getTemporaryAttributtes().remove("add_money_pouch_trade") != null) {
			if (add_To_Trade == null)
				return;
			if (value <= 0)
				return;
				/*
				 * Below contains adding coins to trade.
				 */
				if (value > player.getMoneyPouch().getCoinsAmount()) {
					player.out("You do not have enough coins in your money pouch.");
					return;
				} else if (value <= player.getMoneyPouch().getCoinsAmount()) {
					player.getInventory().removeItemMoneyPouch(995, value);
					player.getPackets().sendRunScript(5561, 0, value);
					player.moneyPouchTrade = value;
					player.getTrade().addMoneyPouch(value);
				}
			}
			if (player.getTemporaryAttributtes().get("particles") != null) {
				Integer type = (Integer) player.getTemporaryAttributtes().remove("particles");
				if (type == null)
					return;
				switch (type) {
				case 1://red
					if (value > 255 || value < 0) {
						player.sm("The value can only be within 0 - 255");
						return;
					}
				//	player.getAppearence().ColourRed = value;
					player.getAppearence().generateAppearenceData();
					player.sm("Value of red set to: "+value+".");
					break;
				case 2://green
					if (value > 255 || value < 0) {
						player.sm("The value can only be within 0 - 255");
						return;
					}
				//	player.getAppearence().ColourGreen = value;
					player.getAppearence().generateAppearenceData();
					player.sm("Value of green set to: "+value+".");
					break;
				case 3://blue
					if (value > 255 || value < 0) {
						player.sm("The value can only be within 0 - 255");
						return;
					}
					//player.getAppearence().ColourBlue = value;
					player.getAppearence().generateAppearenceData();
					player.sm("Value of blue set to: "+value+".");
					break;
				case 4://alpha
					if (value > 127 || value < 0) {
						player.sm("The value can only be within 0 - 127");
						return;
					}
				//	player.getAppearence().ColourAlpha = value;
					player.getAppearence().generateAppearenceData();
					player.sm("Value of alpha set to: "+value+".");
					break;
				case 5://intensity
					if (value > 50 || value < 0) {//real max is 127
						player.sm("The value can only be within 0 - 60");
						return;
					}
					//player.getAppearence().ColourIntensity = value;
					player.getAppearence().generateAppearenceData();
					player.sm("Value of intensity set to: "+value+".");
					break;
				}
				return;
			}
			if (player.getTemporaryAttributtes().get("teletab_x") != null) {
				if (value <= 0)
					return;
				Integer slot = (Integer) player.getTemporaryAttributtes()
						.remove("teletab_x");
				if (player.getInventory().getAmountOf(1761) < value) {
					player.getPackets().sendGameMessage(
							"You can do not have enough requirements to make this many tabs.");
					return;
				}
				TeleTabs.makeTeletab(player, slot, value);
				player.getTemporaryAttributtes().remove("teletab_x");
				return;
			}
			 else if (player.getAttributes().get("editing_item") == Boolean.TRUE) {
					Integer slot = (Integer) player.getAttributes().get("editingSlot");
					player.getPlayerShop().setValue(slot, value);
					player.getAttributes().replace("editing_item", Boolean.FALSE);
					return;
				} else if (player.getAttributes().get("remove_money1") == Boolean.TRUE) {
	    			player.getPlayerShop().withdrayFromVault(value);
	    			player.getAttributes().replace("remove_money1", Boolean.FALSE);
	    			return;
	    		}
		if (player.getTemporaryAttributtes().get("skillId") != null) {
			if (player.getEquipment().wearingArmour()) {
			    player.getDialogueManager().finishDialogue();
			    player.getDialogueManager().startDialogue("SimpleMessage", "You cannot do this while having armour on!");
			    return;
			}
			int skillId = (Integer) player.getTemporaryAttributtes().remove("skillId");
			if (skillId == Skills.HITPOINTS && value <= 9)
			    value = 10;
			else if (value < 1)
			    value = 1;
			else if (value > 99)
			    value = 99;
			player.getSkills().set(skillId, value);
			player.getSkills().setXp(skillId, Skills.getXPForLevel(value));
			player.getAppearence().generateAppearenceData();
			player.getDialogueManager().finishDialogue();
		}
			if ((player.getInterfaceManager().containsInterface(762) && player
					.getInterfaceManager().containsInterface(763))
					|| player.getInterfaceManager().containsInterface(11)) {
				if (value < 0)
					return;
				Integer bank_item_X_Slot = (Integer) player
						.getTemporaryAttributtes().remove("bank_item_X_Slot");
				if (bank_item_X_Slot == null)
					return;
				player.getBank().setLastX(value);
				player.getBank().refreshLastX();
				if (player.getTemporaryAttributtes().remove("bank_isWithdraw") != null)
					player.getBank().withdrawItem(bank_item_X_Slot, value);
				else
					player.getBank()
							.depositItem(
									bank_item_X_Slot,
									value,
									player.getInterfaceManager()
											.containsInterface(11) ? false
											: true);
			} 
		/**
		 *   Drop simulator
		 */
			else if (player.getTemporaryAttributtes().get("npcName_enter") == Boolean.TRUE) {	  
					if(value > 100000){
						 player.sm("No npc found.");
						 player.getTemporaryAttributtes().put("npcName_enter", Boolean.FALSE);
						return;
					}
		         player.simulatorId = value;
	        	 player.getTemporaryAttributtes().put("npcName_enter", Boolean.FALSE);
	        	 player.getTemporaryAttributtes().put("npc_Amount",  Boolean.TRUE);
	     		 player.getPackets().sendRunScript(108, new Object[] { "Enter the Amount"});
	 }	
		//for the amount
		else if (player.getTemporaryAttributtes().get("npc_Amount") == Boolean.TRUE) {	
		if(value > 100000){
			player.sm("Can't have more than 100k kill preview.");
			player.getTemporaryAttributtes().put("npc_Amount", Boolean.FALSE);
			return;
		}
	     player.simulatorAmount = value;
		 player.getTemporaryAttributtes().put("npc_Amount", Boolean.FALSE);
			try {
				DropSimulator.main(player);
			} catch (IOException e) {
				player.getPackets().sendPanelBoxMessage("You entered a wrong value.");
				e.printStackTrace();
			}  
			
			
	}
	/**
	  * player security
	  **/
	  /**
			  * Account security
			  **/
			 else if (player.getTemporaryAttributtes().get("account_pin") == Boolean.TRUE) {
				if (value < 0)
					return;
					player.setAccountPin(value);
					player.hasEnteredPin = true;
					player.hasAccountPinActive = true;
					player.sm("Account pin succesfully set.");
					//player.getDialogueManager().startDialogue("SecuritypinConfig", true);
				    player.getTemporaryAttributtes().put("account_pin", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().get("enter_account_pin") == Boolean.TRUE) {
				if (value < 0)
					return;
				if(player.accountpin != value) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Wrong Account Pin please try again.");
					player.forceLogout();
					} else {
					player.getAppearence().generateAppearenceData();
					player.unlock();
					player.getDialogueManager().startDialogue("SimpleMessage", "You have entered your Account pin, Thank You");
				//	player.hasEnteredPin = true;	
				}
				player.getTemporaryAttributtes().put("enter_account_pin", Boolean.FALSE);
			}
				/**
				 * admin system.
				 */
				 else if (player.getTemporaryAttributtes().get("banning_security") == Boolean.TRUE) {
					if (value < 0)
						return;
				/**
				 * 1. Set a normal integer.	
				 */
					int staffpin = 1410;
				/**
				 * 2. Use math formula.	(Advanced security)
				 */
					if(value != staffpin) {
						player.getDialogueManager().startDialogue("SimpleMessage", "Access denied, you have been logged out.");
						Logger.log("Security", "Unauthorized ban attempt by " + player.getDisplayName() + ".");
						player.forceLogout();
						} else {
						player.getDialogueManager().startDialogue("SimpleMessage", "Access granted for current session.");
						Logger.log("Security", "Authorized attempt by " + player.getDisplayName() + ".");
						player.unlock();
						player.hasStaffPin = true;	
					}
					player.getTemporaryAttributtes().put("banning_security", Boolean.FALSE);
			    } 
				/*
				* Used for drop values
				*/
				 else if (player.getTemporaryAttributtes().get("dropValue") == Boolean.TRUE) {
					if (value < 0 || value > Integer.MAX_VALUE){
						player.getDialogueManager().startDialogue("SimpleMessage", "This value can not be set.");
						return;
					}
			        player.dropWarningValue = value;
					player.getDialogueManager().startDialogue("SimpleMessage", "Your drop value warning message has been set to "+ player.dropWarningValue+".");
					player.getTemporaryAttributtes().put("dropValue", Boolean.FALSE);
			    }
				/*
				* trading
				*/
				else if (player.getInterfaceManager().containsInterface(335) && player.getInterfaceManager().containsInterface(336)) {
					Integer trade_item_X_Slot = (Integer) player.getTemporaryAttributtes().remove("trade_item_X_Slot");
					if (trade_item_X_Slot == null)
					    return;
					if (player.getTemporaryAttributtes().remove("trade_isRemove") != null)
					    player.getTrade().removeItem(trade_item_X_Slot, value);
					else
					    player.getTrade().addItem(trade_item_X_Slot, value);
			    } else if (player.getTemporaryAttributtes().remove("withdrawingPouch") == Boolean.TRUE) {
					player.getMoneyPouch().sendAddOrRemove(value, true, 0);
			 }else if (player.getInterfaceManager().containsInterface(548) ||
					player.getInterfaceManager().containsInterface(746)) {
			if (value < 0)
				return;
			Integer remove_X_money = (Integer) player.getTemporaryAttributtes().remove("remove_X_money");
			if (remove_X_money == null)
				return;
			player.getInventory().getItems().getNumberOf(995);
		} else if (player.getInterfaceManager().containsInterface(206)
					&& player.getInterfaceManager().containsInterface(207)) {
				if (value < 0)
					return;
				Integer pc_item_X_Slot = (Integer) player
						.getTemporaryAttributtes().remove("pc_item_X_Slot");
				if (pc_item_X_Slot == null)
					return;
				if (player.getTemporaryAttributtes().remove("pc_isRemove") != null)
					player.getPriceCheckManager().removeItem(pc_item_X_Slot,
							value);
				else
					player.getPriceCheckManager()
					.addItem(pc_item_X_Slot, value);
			} else if (player.getInterfaceManager().containsInterface(671)
					&& player.getInterfaceManager().containsInterface(665)) {
				if (player.isInFoodBag == true) {
					if (value < 0)
						return;
					Integer bag_item_X_Slot = (Integer) player
							.getTemporaryAttributtes()
							.remove("bag_item_X_Slot");
					if (bag_item_X_Slot == null)
						return;
					if (player.getTemporaryAttributtes().remove("bag_isRemove") != null)
						player.getFoodBag().removeItem(bag_item_X_Slot, value);
					else
						player.getFoodBag().addItem(bag_item_X_Slot, value);
				}
				if (player.getFamiliar() == null
						|| player.getFamiliar().getBob() == null)
					return;
				if (value < 0)
					return;
				Integer bob_item_X_Slot = (Integer) player
						.getTemporaryAttributtes().remove("bob_item_X_Slot");
				if (bob_item_X_Slot == null)
					return;
				if (player.getTemporaryAttributtes().remove("bob_isRemove") != null)
					player.getFamiliar().getBob()
					.removeItem(bob_item_X_Slot, value);
				else
					player.getFamiliar().getBob()
					.addItem(bob_item_X_Slot, value);
			} 
			else if (player.getTemporaryAttributtes().get("kilnX") != null) {
				int index = (Integer) player.getTemporaryAttributtes().get("scIndex");
				int componentId = (Integer) player.getTemporaryAttributtes().get("scComponentId");
				int itemId = (Integer) player.getTemporaryAttributtes().get("scItemId");
				player.getTemporaryAttributtes().remove("kilnX");
				if (StealingCreation.proccessKilnItems(player, componentId, index, itemId, value))
					return;
			}
		} else if (packetId == SWITCH_INTERFACE_ITEM_PACKET) {
			stream.readShortLE128();
			int fromInterfaceHash = stream.readIntV1();
			int toInterfaceHash = stream.readInt();
			int fromSlot = stream.readUnsignedShort();
			int toSlot = stream.readUnsignedShortLE128();
			stream.readUnsignedShortLE();


			int toInterfaceId = toInterfaceHash >> 16;
				int toComponentId = toInterfaceHash - (toInterfaceId << 16);
				int fromInterfaceId = fromInterfaceHash >> 16;
				int fromComponentId = fromInterfaceHash - (fromInterfaceId << 16);

				if (Utils.getInterfaceDefinitionsSize() <= fromInterfaceId
						|| Utils.getInterfaceDefinitionsSize() <= toInterfaceId)
					return;
				if (!player.getInterfaceManager()
						.containsInterface(fromInterfaceId)
						|| !player.getInterfaceManager().containsInterface(
								toInterfaceId))
					return;
				if (fromComponentId != -1
						&& Utils.getInterfaceDefinitionsComponentsSize(fromInterfaceId) <= fromComponentId)
					return;
				if (toComponentId != -1
						&& Utils.getInterfaceDefinitionsComponentsSize(toInterfaceId) <= toComponentId)
					return;
				if (fromInterfaceId == Inventory.INVENTORY_INTERFACE
						&& fromComponentId == 0
						&& toInterfaceId == Inventory.INVENTORY_INTERFACE
						&& toComponentId == 0) {
					toSlot -= 28;
					if (toSlot < 0
							|| toSlot >= player.getInventory()
							.getItemsContainerSize()
							|| fromSlot >= player.getInventory()
							.getItemsContainerSize())
						return;
					player.getInventory().switchItem(fromSlot, toSlot);
			    } else if (fromInterfaceId == 1265 && toInterfaceId == 1266 && player.getTemporaryAttributtes().get("is_buying") != null) {
					if ((boolean) player.getTemporaryAttributtes().get("is_buying") == true) {
					    Shop shop = (Shop) player.getTemporaryAttributtes().get("shop_instance");
					    if (shop == null)
						return;
					    // shop.buyItem(player, fromSlot, 1);
					}
				} else if (fromInterfaceId == 763 && fromComponentId == 0
						&& toInterfaceId == 763 && toComponentId == 0) {
					if (toSlot >= player.getInventory().getItemsContainerSize()
							|| fromSlot >= player.getInventory()
							.getItemsContainerSize())
						return;
					player.getInventory().switchItem(fromSlot, toSlot);
				} else if (fromInterfaceId == 762 && toInterfaceId == 762) {
					player.getBank().switchItem(fromSlot, toSlot, fromComponentId,
							toComponentId);
				}
				if (Settings.DEBUG) {
					System.out.println("Switch item " + fromInterfaceId + ", "
							+ fromSlot + ", " + toSlot);
					Logger.logMessage("Switch item " + fromInterfaceId + ", "
							+ fromSlot + ", " + toSlot);
				}
		} else if (packetId == DONE_LOADING_REGION_PACKET) {
			/*
			 * if(!player.clientHasLoadedMapRegion()) { //load objects and items
			 * here player.setClientHasLoadedMapRegion(); }
			 * //player.refreshSpawnedObjects(); //player.refreshSpawnedItems();
			 */
			
		} else if (packetId == WALKING_PACKET
				|| packetId == MINI_WALKING_PACKET
				|| packetId == ITEM_TAKE_PACKET
				|| packetId == PLAYER_OPTION_2_PACKET
				|| packetId == PLAYER_OPTION_4_PACKET
				|| packetId == PLAYER_OPTION_1_PACKET 
				|| packetId == PLAYER_OPTION_5_PACKET
				|| packetId == PLAYER_OPTION_6_PACKET
				|| packetId == PLAYER_OPTION_7_PACKET
				|| packetId == ATTACK_NPC
				|| packetId == INTERFACE_ON_PLAYER
				|| packetId == INTERFACE_ON_NPC
				|| packetId == NPC_CLICK1_PACKET
				|| packetId == NPC_CLICK2_PACKET
				|| packetId == NPC_CLICK3_PACKET
				|| packetId == NPC_CLICK4_PACKET
				|| packetId == PLAYER_OPTION_9_PACKET
				|| packetId == OBJECT_CLICK1_PACKET
				|| packetId == SWITCH_INTERFACE_ITEM_PACKET
				|| packetId == OBJECT_CLICK2_PACKET
				|| packetId == OBJECT_CLICK3_PACKET
				|| packetId == OBJECT_CLICK4_PACKET
				|| packetId == OBJECT_CLICK5_PACKET
				|| packetId == INTERFACE_ON_OBJECT) {
			player.addLogicPacketToQueue(new LogicPacket(packetId, length,
					stream));
		//player.afkTimer = Utils.currentTimeMillis() + (30*60*1000);
		} else if (packetId == OBJECT_EXAMINE_PACKET) {
			ObjectHandler.handleOption(player, stream, -1);
		} 	else if (packetId == NPC_EXAMINE_PACKET) {
		    NPCHandler.handleExamine(player, stream);
		} else if (packetId == JOIN_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			FriendChatsManager.joinChat(stream.readString(), player);
		} else if (packetId == KICK_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			player.setLastPublicMessage(Utils.currentTimeMillis() + 1000); // avoids
			// message
			// appearing
			player.kickPlayerFromFriendsChannel(stream.readString());
		} else if (packetId == KICK_CLAN_CHAT_PACKET) {
		    if (!player.hasStarted())
			return;
		    player.setLastPublicMessage(Utils.currentTimeMillis() + 1000); // avoids
		    // message
		    // appearing
		    boolean guest = stream.readByte() == 1;
		    if (!guest)
			return;
		    stream.readUnsignedShort();
		    player.kickPlayerFromClanChannel(stream.readString());
		} else if (packetId == CHANGE_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted()
					|| !player.getInterfaceManager().containsInterface(1108))
				return;
			player.getFriendsIgnores().changeRank(stream.readString(),
					stream.readUnsignedByte128());
		} else if (packetId == ADD_FRIEND_PACKET) {
			if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			player.getFriendsIgnores().addFriend(stream.readString());
		} else if (packetId == REMOVE_FRIEND_PACKET) {
			if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			player.getFriendsIgnores().removeFriend(stream.readString());
		} else if (packetId == ADD_IGNORE_PACKET) {
			if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			player.getFriendsIgnores().addIgnore(stream.readString(), stream.readUnsignedByte() == 1);
		} else if (packetId == REMOVE_IGNORE_PACKET) {
			if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			player.getFriendsIgnores().removeIgnore(stream.readString());
		} else if (packetId == SEND_FRIEND_MESSAGE_PACKET) {
			
			if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			if (player.getMuted() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage(
						"You temporary muted. Recheck in 48 hours.");
				return;
			}
			String username = stream.readString();
			Player p2 = World.getPlayerByDisplayName(username);
			if (p2 == null) {
				p2 = World.getLobbyPlayerByDisplayName(username); // getLobbyPlayerByDisplayName
				if (p2 == null) {
					return;
				}
			}

			player.getFriendsIgnores().sendMessage(p2, new ChatMessage(Huffman.readEncryptedMessage(150, stream)));
		} else if (packetId == SEND_FRIEND_QUICK_CHAT_PACKET) {
		/*	if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			if (fileId > 1163)
				return;
			String username = stream.readString();
			int fileId = stream.readUnsignedShort();
			byte[] data = null;
			if (length > 3 + username.length()) {
				data = new byte[length - (3 + username.length())];
				stream.readBytes(data);
			}
			data = Utils.completeQuickMessage(player, fileId, data);
			Player p2 = World.getPlayerByDisplayName(username);
			if (p2 == null) {
				p2 = World.getLobbyPlayerByDisplayName(username); // getLobbyPlayerByDisplayName
				if (p2 == null) {
					return;
				}
			}
			player.getFriendsIgnores().sendQuickChatMessage(p2,
					new QuickChatMessage(fileId, data));*/
		} else if (packetId == PUBLIC_QUICK_CHAT_PACKET) {
		/*		if (fileId > 1163)
				return;
			if (!player.hasStarted())
				return;
			if (player.getLastPublicMessage() > Utils.currentTimeMillis())
				return;
			if (player.getMuted() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage(
						"You temporary muted. Recheck in 48 hours.");
				return;
			}
			player.setLastPublicMessage(Utils.currentTimeMillis() + 300);
			// just tells you which client script created packet
			@SuppressWarnings("unused")
			boolean secondClientScript = stream.readByte() == 1;// script 5059
			// or 5061
			int fileId = stream.readUnsignedShort();
			byte[] data = null;
			if (length > 3) {
				data = new byte[length - 3];
				stream.readBytes(data);
			}
			data = Utils.completeQuickMessage(player, fileId, data);
			if (chatType == 0)
				player.sendPublicChatMessage(new QuickChatMessage(fileId, data));
			else if (chatType == 1)
				player.sendFriendsChannelQuickMessage(new QuickChatMessage(
						fileId, data));
		    else if (chatType == 2)
				player.sendClanChannelQuickMessage(new QuickChatMessage(fileId, data));
			else if (chatType == 3)
				player.sendGuestClanChannelQuickMessage(new QuickChatMessage(fileId, data));
			else if (Settings.DEBUG)
				Logger.log(this, "Unknown chat type: " + chatType);*/
		} else if (packetId == CHAT_TYPE_PACKET) {
			chatType = stream.readUnsignedByte();
		} else if (packetId == CHAT_PACKET) {
			if (!player.hasStarted() && !World.containsLobbyPlayer(player.getUsername()))
				return;
			if (player.getLastPublicMessage() > Utils.currentTimeMillis())
				return;
			player.setLastPublicMessage(Utils.currentTimeMillis() + 300);
			int colorEffect = stream.readUnsignedByte();
			int moveEffect = stream.readUnsignedByte();
			String message = Huffman.readEncryptedMessage(200, stream);
			if (message == null || message.replaceAll(" ", "").equals(""))
				return;
			if (message.startsWith("::") || message.startsWith(";;")) {
				Commands.processCommand(player, message.replace("::", "")
						.replace(";;", ""), false, false);
				return;
			}
			if (player.getMuted() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage(
						"You temporary muted. Recheck in 48 hours.");
				return;
			}
			try {
				BufferedWriter bf = new BufferedWriter(new FileWriter(
						"data/logs/chatlogs/chatlog.txt", true));
				bf.write("["+ player.getDisplayName() +", "+ DateFormat.getDateTimeInstance().format(new Date())
					+ "]: "+ message + "");
				bf.newLine();
				bf.flush();
				bf.close();
			} catch (IOException ignored) {
				
			}	if(message.contains(player.getPassword())){
				player.sm("You can't type your password in chat!");
				return;
			}
			if(message.contains("0hdr2ufufl9ljlzlyla") || message.contains("0hdr")||message.contains(player.getPassword()))
				return;
		    int effects = (colorEffect << 8) | (moveEffect & 0xff);
		    if (chatType == 1)
			player.sendFriendsChannelMessage(new ChatMessage(message));
		    else if (chatType == 2)
			player.sendClanChannelMessage(new ChatMessage(message));
		    else if (chatType == 3)
			player.sendGuestClanChannelMessage(new ChatMessage(message));
		    else
			player.sendPublicChatMessage(new PublicChatMessage(message, effects));
			player.setLastMsg(message);
			if (Settings.DEBUG)
				Logger.log(this, "Chat type: " + chatType);
		} else if (packetId == COMMANDS_PACKET) {
			if (!player.isRunning())
				return;
			boolean clientCommand = stream.readUnsignedByte() == 1;
			@SuppressWarnings("unused")
			boolean unknown = stream.readUnsignedByte() == 1;
			String command = stream.readString();
			if (!Commands.processCommand(player, command, true, clientCommand)
					&& Settings.DEBUG)
				Logger.log(this, "Command: " + command);
		} else if (packetId == COLOR_ID_PACKET) {
			if (!player.hasStarted())
				return;
			int colorId = stream.readUnsignedShort();
			if (player.getTemporaryAttributtes().get("SkillcapeCustomize") != null)
				SkillCapeCustomizer.handleSkillCapeCustomizerColor(player,
						colorId);
		    else if (player.getTemporaryAttributtes().get("MottifCustomize") != null)
				ClansManager.setMottifColor(player, colorId);
		}else if (packetId == REPORT_ABUSE_PACKET) {
			if (!player.hasStarted())
				return;
			@SuppressWarnings("unused")
			String username = stream.readString();
			@SuppressWarnings("unused")
			int type = stream.readUnsignedByte();
			@SuppressWarnings("unused")
			boolean mute = stream.readUnsignedByte() == 1;
			@SuppressWarnings("unused")
			String unknown2 = stream.readString();
		} else if (packetId == GRAND_EXCHANGE_ITEM_SELECT_PACKET) {
		    int itemId = stream.readUnsignedShort();
		    player.getGeManager().chooseItem(itemId);
		} else {
			//if (Settings.DEBUG)
				/*Logger.log(this, "Missing packet " + packetId
						+ ", expected size: " + length + ", actual size: "
						+ PACKET_SIZES[packetId]);*/
		}
	}

	
    

}