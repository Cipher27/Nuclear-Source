package com.rs.game.player.dialogues;

import com.rs.game.player.content.presets.PresetSetups;

public class PresetQuickLoad extends Dialogue {

	@Override
	public void start() {
		if (player.getPresetSetupByName(player.preset1) == null) {
                    	player.sm("No preset could be found. Your quickload preset will be the preset at the top.");
                    	end();
                    }
				PresetSetups set = player.getPresetSetupByName(player.presetone);
				 PresetSetups.giveSet(player, set);
               end();
			}

	@Override
	public void run(int interfaceId, int componentId) {
	}

	@Override
	public void finish() {

	}

}
