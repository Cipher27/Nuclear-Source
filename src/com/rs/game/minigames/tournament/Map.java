package com.rs.game.minigames.tournament;

import com.rs.Settings;
import com.rs.game.RegionBuilder;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.robot.Robot;
import com.rs.game.player.robot.Set;
import com.rs.game.player.robot.SetManager;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class Map
{
	
	private transient int[] mapBaseCoords;
	
	public void createArena() {
		mapBaseCoords = RegionBuilder.findEmptyMap(8, 8);
		RegionBuilder.copyAllPlanesMap(456, 768, mapBaseCoords[0],
				mapBaseCoords[1], 8);
	}
	
	public void destroyArena() {
		RegionBuilder.destroyMap(mapBaseCoords[0], 
				mapBaseCoords[1], 8, 8);
	}
	
	public void teleportToArena(Player player, boolean second, boolean spawnedEquipment, int setId, int switchSetId, Match match) {
		player.setNextFaceWorldTile(new WorldTile(player.getX() + (second ? -1 : 1), player
				.getY(), 0));
		player.getControlerManager().startControler("ArenaControler", match, spawnedEquipment);
		player.lock();
		player.setNextWorldTile(new WorldTile(getBaseX() + 25 + (second ? 13 : 0), getBaseY() + 32,
				2));
		putSetOn(player, spawnedEquipment, setId, switchSetId);
		cutscene(player, match, second);
	}
	
	private void putSetOn(Player player, boolean spawnedEquipment, int setId, int switchSetId) {
		if (!spawnedEquipment && !player.isRobot()) {
			return;
		}
		if (player.isRobot() || player.getRights() == 2) {
			SetManager.getSingleton();
			@SuppressWarnings("static-access")
			Set set = SetManager.list.get(switchSetId);
			Item[] items = set.getInv();
			Item[] items2 = set.getEquip();
			for (int i = 0; i < items.length; i++) {
				if (items[i] == null || items[i].getId() <= 1)
					continue;
				player.getInventory().getItems().set(i, items[i]);
				player.getInventory().refresh(i);
			}
			for (int o = 0; o < items2.length; o++) {
				if (items2[o] == null || items2[o].getId() <= 1)
					continue;
				player.getEquipment().wieldOneItem(o, items2[o]);
			}
			player.enableCheatSwitch();
		}
		SetManager.getSingleton();
		@SuppressWarnings("static-access")
		int length = SetManager.list.size()-1;
		if (setId < 0 || setId > length) {
			player.getPackets().sendGameMessage("This set doesn't exist, try using a number between 0 and "+length+".");
			return;
		}
		player.getTemporaryAttributtes().put("set", setId);
		SetManager.getSingleton();
		@SuppressWarnings("static-access")
		Set set = SetManager.list.get(setId);
		Item[] items = set.getInv();
		Item[] items2 = set.getEquip();
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null || items[i].getId() <= 1)
				continue;
			player.getInventory().getItems().set(i, items[i]);
			player.getInventory().refresh(i);
		}
		for (int o = 0; o < items2.length; o++) {
			if (items2[o] == null || items2[o].getId() <= 1)
				continue;
			player.getEquipment().wieldOneItem(o, items2[o]);
		}
		player.getAppearence().generateAppearenceData();
	}
	
	public void teleportFromArena(Player player, WorldTile startTile) {
		player.getControlerManager().removeControlerWithoutCheck();
		if (player instanceof Robot) {
			player.realFinish();
		}
		//player.getControlerManager().startControler("TournamentControler");
		player.setNextFaceWorldTile(new WorldTile(player.getX(), player.getY() - 1, 0));
		player.lock(5);
		player.setCanPvp(false);
		player.setNextWorldTile(startTile);
	}
	
	public void cutscene(final Player player, final Match match, final boolean second) {
		WorldTasksManager.schedule(new WorldTask() {

			private int count = -2;// Time to load

			@Override
			public void run() {
				if (match.hasEnded()) {
					if (player != null && !player.hasFinished()) {
						player.closeInterfaces();
						player.getPackets().sendResetCamera();
						player.unlock();
					}
					stop();
					return;
				}
				if (count == (second ? 3 : 0)) {
					player.getPackets().sendConfig(1241, 1);
					WorldTile tile = new WorldTile(getBaseX() + 25, getBaseY() + 38,0);
					WorldTile tile2 = new WorldTile(getBaseX() + 25, getBaseY() + 29,0);
					WorldTile tile3 = new WorldTile(getBaseX() + 32, getBaseY() + 38,0);
					player.getPackets().sendCameraPos(
							tile.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()), 1800);
					player.getPackets().sendCameraLook(
							tile2.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile2.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()), 800);
					player.getPackets().sendCameraPos(
							tile3.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile3.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()), 1800, 6, 6);
				} else if (count == (second ? 4 : 1)) {
					//player.setNextForceTalk(new ForceTalk(
					//		getStartFightText(Utils.getRandom(1))));
				} else if (count == (second ? 0 : 3)) {
					player.getPackets().sendConfig(1241, 0);
					WorldTile tile = new WorldTile(getBaseX() + 35, getBaseY() + 37,0);
					WorldTile tile2 = new WorldTile(getBaseX() + 35, getBaseY() + 28,0);
					WorldTile tile3 = new WorldTile(getBaseX() + 42, getBaseY() + 37,0);
					player.getPackets().sendCameraPos(
							tile.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()), 1800);
					player.getPackets().sendCameraLook(
							tile2.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile2.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()), 800);
					player.getPackets().sendCameraPos(
							tile3.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile3.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()), 1800, 6, 6);
				} else if (count == (second ? 1 : 4)) {
					//if (BOSSES[nextBossIndex].text != null)
					//	bosses[0].setNextForceTalk(new ForceTalk(
					//			BOSSES[nextBossIndex].text));
				} else if (count == 6) {
					player.getPackets().sendConfig(1241, 0);
					WorldTile tile = new WorldTile(getBaseX() + 32, getBaseY() + 36,0);
					WorldTile tile2 = new WorldTile(getBaseX() + 32, getBaseY() + 16,0);
					player.getPackets().sendCameraLook(
							tile.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()), 0);
					player.getPackets().sendCameraPos(
							tile2.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
							tile2.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()), 5000);
					player.getPackets().sendVoice(7882);
				} else if (count == 8) {
					player.closeInterfaces();
					player.getPackets().sendResetCamera();
					player.unlock();
					match.setStarted(true);
				} else if (count == 14) {
					if (player.isRobot() || player.isScriptingEnabled()) {
						Player other = match.getOtherPlayer(second);
						if (Settings.DEBUG)
							System.out.println("Robot's trying to attack player");
						if (other != null) {
							player.setAttackedBy(other);
							//player.getScript().setAttackedBy(other);
						}
					}
					stop();
				}
				count++;
			}
		}, 0, 1);
	}
	
	public int getBaseX() {
		return mapBaseCoords[0] << 3;
	}
	
	public int getBaseY() {
		return mapBaseCoords[1] << 3;
	}
	
	public boolean isAtArena(Player player) {
		WorldTile arena = new WorldTile(getBaseX() + 32, getBaseY() + 32, player.getPlane());
		return player.withinDistance(arena, 30);
	}
}