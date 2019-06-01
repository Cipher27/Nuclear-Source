package com.rs.game.player;

import java.io.Serializable;
import java.util.HashMap;

import com.rs.utils.ChatColors;

/**
 *
 * @Author Tristam <Hassan>
 * @Project - 1. Rain
 * @Date - 9 Oct 2016
 *
 **/

public class GameplaySettings implements Serializable {

	private static final long serialVersionUID = 8553388318557724057L;

	private Player player;

	private final int INTERFACE = 1006;
	private final int ENABLED = 2548;
	private final int DISABLED = 2549;

	private String[] Settings;

	public static int ENTITY_FEED = 32, AUTOMACT_SCREENSHOT = 33, X_TEN_DMG = 34;

	private HashMap<Integer, Boolean> GameSettings;

	public GameplaySettings() {
		GameSettings = new HashMap<Integer, Boolean>();
		for (int i = 35; i < 60; i++)
			GameSettings.put(i, false);
		GameSettings.put(32, true);
		GameSettings.put(33, true);
		GameSettings.put(34, true);
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public void Open() {
		/*if (player.isBusy()) {
			player.sm("Please finish with what you are doing before opening the Gameplay Settings.");
			return;
		}*/
		updateOptions();
		player.getInterfaceManager().sendInterface(INTERFACE);
		player.getPackets().sendIComponentText(1006, 13, "Game settings");
		player.getPackets().sendIComponentText(1006, 31,
				ChatColors.GREEN + "Change your gameplay settings! You can change client settings via the cog.");
		for (int i = 14; i < 31; i++)
			player.getPackets().sendIComponentText(1006, i, Settings[i - 14]);
		refresh();

	}

	private void refresh() {
		for (int i = 0; i <= 17; i++) {
			int componentId = ConvertToCID(i);
			player.getPackets().sendIComponentSprite(INTERFACE, componentId,
					GameSettings.get(componentId) ? ENABLED : DISABLED);
		}
	}

	public void handleButtons(final int componentId) {
		if (componentId == 50) {
			player.sm("This button does not work.");
			return;
		}
		if (componentId == 34) {
			player.getPackets().toggleXTenDamage(GameSettings.get(34) ? true : false);
		}
		if (componentId == 35) {
			player.switchItemsLook();
		}
		GameSettings.put(componentId, !GameSettings.get(componentId) ? true : false);
		player.sm(ChatColors.RED + "Game settings: " + Settings[componentId - 32].replace("<br>", " ") + " has been "
				+ (GameSettings.get(componentId) ? "enabled" : "disabled") + ".");
		refresh();

	}

	private void updateOptions() {
		Settings = new String[] { "Entity feed", "Achievement<br>Screenshots", "x10 damage", "Old/new items look",
				"Coming soon", "Coming soon", "Coming soon", "Coming soon", "Coming soon", "Coming soon", "Coming soon",
				"Coming soon", "Coming soon", "Coming soon", "Coming soon", "Coming soon", "Coming soon",
				"Coming soon", };
	}

	public boolean getGameSetting(int id) {
		return GameSettings.get(id);
	}

	public static int ConvertToCID(int i) {
		if (i >= 0 && i <= 19) {
			return 32 + i;
		} else if (i == 16) {
			return 50;
		}
		return 0;
	}

	public static int ConvertFromCID(int i) {
		if (i >= 32 && i <= 47)
			return i - 32;
		else if (i == 50)
			return 16;
		return 0;
	}

}
