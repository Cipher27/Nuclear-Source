package com.rs.game.player.dialogues;

public class Particles extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("What would you like to do?", "Toggle particle customisation", "Customise particles");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			switch (componentId) {
			case OPTION_1:
				//player.getAppearence().ColourEnabled = !player.getAppearence().ColourEnabled;
				player.getAppearence().generateAppearenceData();
			//	player.sm("Particle customiation is now "+(player.getAppearence().ColourEnabled ? "en" : "dis")+"abled.");
				end();
				break;
			case OPTION_2:
				sendOptionsDialogue("What would you like to do?","Change the red value.", "Change the green value.", "Change the blue value.", "Change the alpha value.", "Change the intensity value.");
				stage = 1;
				break;
			}
			break;
		case 1:
			String value = "";
			switch (componentId) {
			case OPTION_1://red
				value = "Red (0 - 255): ";
				player.getTemporaryAttributtes().put("particles", 1);
				player.getPackets().sendInputIntegerScript(value);
				end();
				break;
			case OPTION_2://green
				value = "Green (0 - 255): ";
				player.getTemporaryAttributtes().put("particles", 2);
				player.getPackets().sendInputIntegerScript(value);
				end();
				break;
			case OPTION_3://blue
				value = "Blue (0 - 255): ";
				player.getTemporaryAttributtes().put("particles", 3);
				player.getPackets().sendInputIntegerScript(value);
				end();
				break;
			case OPTION_4://alpha
				value = "Alpha (0 - 127): ";
				player.getTemporaryAttributtes().put("particles", 4);
				player.getPackets().sendInputIntegerScript(value);
				end();
				break;
			case OPTION_5://intensity
				value = "Intensity (0 - 50): ";
				player.getTemporaryAttributtes().put("particles", 5);
				player.getPackets().sendInputIntegerScript(value);
				end();
				break;
			}
			break;
		}
	}

	@Override
	public void finish() {

	}

}
