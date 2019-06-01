package com.rs.game.player.content.commands;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.DungeonControler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.utils.Utils;

public class DonatorCommands {

	public static boolean processNormal(Player player, String[] cmd,boolean console, boolean clientCommand) {
		switch (cmd[0]) {
			case "dz":
			case "donatorzone":
				if(Wilderness.isAtWild(player)){
					player.sm("You can't teleport.");
					return false;
				}
			if(player.getControlerManager().getControler() instanceof DungeonControler){
					player.sm("You can't teleport.");
					return false;
				}
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1813, 5844, 0));
			return true;
			case "bank":

				if (player.isDonator() || player.getRights() >= 1) {
					if (!player.canSpawn()) {
						player.getPackets().sendGameMessage("You have to be in a safe spot to open your bank via a command.");
						return false;
					}
					player.getBank().openBank();
				} else {
					player.getPackets().sendGameMessage("You need to be a donator or Mod+ to access ::bank.");
				}
				return true;
		}
		return clientCommand;
	}
}
