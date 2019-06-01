package com.rs.game.minigames.tournament;


import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.PublicChatMessage;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public final class ArenaControler extends Controler {
	
	private transient Match match;
	
	private boolean spawnedEquipment;
	private WorldTile startTile;
	
	@Override
	public void start() {
		match = (Match) getArguments()[0];
		spawnedEquipment = (boolean) getArguments()[1];
		getArguments()[0] = null;
		startTile = new WorldTile(player.getX(), player.getY(), player.getPlane());
		player.setCanPvp(true);
	}
	
	private final static String[] messages = {"Good fight", "gf", "gg", "get wrecked", "that was easy", "bye bye", "that's what I thought", "come again soon", "peace", "peace out", "I don't like noobs", "you just got served", "nice try", "haha"};
	
	@Override
	public boolean sendDeath() {
		player.lock(7);
		player.stopAll();
		final Player killer = match.getOtherPlayer(player);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (match.hasEnded()) {
					if (player != null && !player.hasFinished()) {
						player.reset();
						player.getAppearence().transformIntoNPC(-1);
						player.setNextAnimation(new Animation(-1));
					}
					stop();
					return;
				}
				if (loop == 0) {
					player.setNextAnimation(new Animation(9040));
				} else if (loop == 1) {
					player.getAppearence().transformIntoNPC(2715);//test
					player.setNextAnimation(new Animation(5491));//test
				} else if (loop == 2) {
					player.getPackets().sendGameMessage("Oh dear, you have died.");
					if (killer != null) {
						killer.lock(4);
						if (killer.isRobot()) {
							if (Utils.getRandom(1) == 1) {
								String message = messages[Utils.getRandom(messages.length - 1)];
								killer.sendPublicChatMessage(new PublicChatMessage( Utils
										.fixChatMessage(message), 0));
							}
						}
					}
				} else if (loop == 6) {
					//if (killer != null)
					//	killer.increaseKillCount(player);
					player.reset();
					player.getAppearence().transformIntoNPC(-1);
					player.setNextAnimation(new Animation(-1));
					match.end(startTile, player.getDisplayName(), "");
				} else if (loop == 8) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1/2);
		return false;
	}
	
	@Override
	public void process() {
		if (match.hasStarted() && !match.getMap().isAtArena(player)) {
			match.end(startTile, player.getDisplayName(), "");
		}
	}
	
/*	@Override
	public boolean canPlayerOption4(Player target) {
		player.getPackets().sendGameMessage("You can't trade in Tournament matches.");
		return false;
	}*/
	
	@Override
	public boolean canDropItem(Item item) {
		ItemDefinitions defs = ItemDefinitions.getItemDefinitions(item.getId());
		if ((defs.getValue() * item.getAmount()) > 5000) {
			player.getPackets().sendGameMessage("You can't drop your "+defs.name+(item.getAmount() > 1 ? "s" : "")+" because it's too expensive.");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		player.getPackets().sendGameMessage("If you want to leave, click on the gates.");
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		player.getPackets().sendGameMessage("If you want to leave, click on the gates.");
		return false;
	}
	
	@Override
	public boolean processObjectClick1(WorldObject object) {
		match.end(startTile, player.getDisplayName(), "");
		return true;
	}
	
	@Override
	public boolean processButtonClick(int interfaceId, int componentId, int slotId, int slotId2, int packetId) {
		if ((interfaceId == 746 && componentId == 207)
				|| (interfaceId == 548 && componentId == 159)) {//money pouch stuff
		/*	if (packetId == WorldPacketsDecoder.BUTTON2) {
				player.sm("You can't do this here.");
				return false;
			}
			if (packetId == WorldPacketsDecoder.BUTTON4) {
				player.sm("You can't do this here.");
				return false;
			} */
		}
		if (interfaceId == 335) {
			player.sm("You're not allowed to trade here.");
			return false;
		}
		if (interfaceId == 762 || interfaceId == 11) {
			player.sm("You're not allowed to use the bank.");
			return false;
		}
		return true;
	}
	
	@Override
	public void forceClose() {
		player.setCanPvp(false);
		//match.end(startTile, player.getDisplayName(), player.getDisplayName());
		match.end(startTile, player.getDisplayName(), "");
	}
	
	@Override
	public boolean logout() {
		player.setNextWorldTile(startTile);
		match.end(startTile, player.getDisplayName(), player.getDisplayName());
		return true;
	}
	
	@Override
	public boolean login() {
		player.getControlerManager().removeControlerWithoutCheck();
		if (spawnedEquipment) {
			player.getInventory().reset();
			player.getEquipment().reset();
		}
		player.reset();
		//player.getControlerManager().startControler("TournamentControler");
		player.setNextFaceWorldTile(new WorldTile(player.getX(), player.getY() - 1, 0));
		player.setCanPvp(false);
		player.setNextWorldTile(startTile);
		player.lock(5);
		return false;
	}

}