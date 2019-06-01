package com.rs.game.player.content.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

/**
 * Commands class
 * todo write it into a plugin system.
 * @author paolo
 *
 */

public final class Commands {
	
	public static boolean processCommand(Player player, String command,boolean console, boolean clientCommand) {
		if (command.length() == 0)
			return false;
		String[] cmd = command.toLowerCase().split(" ");
		if (cmd.length == 0)
			return false;
		if (player.getRights() == 2){
			if(AdminCommands.processAdminCommand(player, cmd, console, clientCommand))
			return true;
		}
		if(player.isDonator() ||player.getRights() == 2 ){
			if(DonatorCommands.processNormal(player, cmd, console, clientCommand))
			return true;
		}
		if (player.getRights() >= 1 ||player.getRights() == 2){
			if(ModeratorCommands.processModCommand(player, cmd, console, clientCommand))
			return true;
		}if ((player.isSupporter() || player.getRights() >= 1 ||player.getRights() == 2) ){
			if(SupportCommands.processSupportCommands(player, cmd, console, clientCommand))
			return true;
		}
		if(NormalCommands.processNormalCommand(player, cmd, console, clientCommand))
			return true;
		return false;
	}

	public static void sendYell(Player player, String message,
            boolean isStaffYell) {
		//message = Censor.getFilteredMessage(message);
        if (player.getMuted() > Utils.currentTimeMillis()) {
            player.getPackets().sendGameMessage("You temporary muted. Recheck in 28 hours and post on forums on Appeal.");
            return;
        }
        if (player.getRights() < 2) {
            String[] invalid = {"<euro", "<img", "<img=", "<col", "<col=",
                "<shad", "<shad=", "<str>", "<u>"};
            for (String s : invalid) {
                if (message.contains(s)) {
                    player.getPackets().sendGameMessage("You cannot add additional code to the message.");
                    return;
                }
            }
        }
        for (Player players : World.getPlayers()) {
            if (players == null || !players.isRunning()) {
                continue;
            }
            if (isStaffYell) {
                if (players.getRights() > 2) {
                    players.getPackets().sendGameMessage("<col=FFFFFF>[Admin] <img=0><col=FFFFFF>" + Utils.formatPlayerNameForDisplay(player.getUsername()) + ": " + message + ".", true);
                }
                return;
            }
			
			/**
			* || player.getUsername().equalsIgnoreCase("")
			*/

            /**
             * Lead Developer
             */
            
            /**
             * Lead Developer
             */
            
            if (player.getUsername().equalsIgnoreCase("paolo")) {
                players.getPackets().sendGameMessage(
                        "<col=00E633><shad=000000>[Main Owner]<img=10><col=00E633>"
                        + player.getDisplayName() + ": </col><col=00E633><shad=000000>"
                        + message + "</col>");
            }
			
		
            if (player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
            	players.getPackets().sendGameMessage(
                        "<col=ffffff><shad=000000>[Developer]<img=10><col=000000>"
                        + player.getDisplayName() + ": </col><col=ff0000><shad=000000>"
                        + message + "</col>");
            }
        
            /**
             * Lead Developer
             */
            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=0099CC><shad=000000>[Co-Owner]<img=10><col=0099CC>"
                        + player.getDisplayName() + ": </col><col=0099CC><shad=000000>"
                        + message + "</col>");
            }
            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=00FFFB><shad=c0c0c0>[Developer] </shad></col><img=1><col=00FFFB>"
                        + player.getDisplayName() + ": </col><col=00FFFB><shad=c0c0c0>"
                        + message + "</col>");
            }
            /**
             * Master Hardcore
             */
            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=000000><shad=000000>[Player]<col=FF0000>"
                        + player.getDisplayName() + ": </col><col=00CCFF><shad=000000>"
                        + message + "</col>");
            }
            
            
            

            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=8A2BE2><shad=000000>[Head-Moderator]<col=8A2BE2>"
                        + player.getDisplayName() + ": "
                        + message + "</col><shad>");
            }
            
            /**
             * end 
             */


            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=000000><shad=00ffff>[Server Helper]<col=0000ff><shad=00ffff>"
                        + player.getDisplayName() + ": </col=FF0000><shad=000000>"
                        + message + "</col>");
                
           
            }

            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=0000ff><shad=00ffff>[Moderator]<col=0000ff><col=0000ff><shad=00ffff>"
                        + player.getDisplayName() + ": </col><col=0000ff><shad=00ffff>"
                        + message + "</col>");
          
            } else if (player.getRights() == 0 && !player.getUsername().equalsIgnoreCase("")  && !player.isDonator() && !player.isExtremeDonator() && !player.isLegendaryDonator() && !player.isSupremeDonator() && !player.isDivineDonator() && !player.isAngelicDonator() && !player.isSupporter()) {
                players.getPackets().sendGameMessage(
                        "<col=ff0033>[Player] <col=ff0033>"
                        + player.getDisplayName() + ": </col><col=ff0033>"
                        + message + "</col>");
            } else if (player.getRights() == 1) {
                players.getPackets().sendGameMessage(
                        "<col=C0C0C0><shad=000000>[Moderator]<img=0><col=FFFFFF><shad=000000>"
                        + player.getDisplayName() + ": </col><col=C0C0C0><shad=000000>"
                        + message + "</col>");
            } else if (player.isAngelicDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=ffffff>[Angelic]<img=15><col=ffffff>"
                        + player.getDisplayName() + ": </col><col=357EC7>"
                        + message + "</col>");
            } else if (player.isDivineDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=ffffff>[Divine]<img=16><col=6C21ED>"
                        + player.getDisplayName() + ": </col><col=357EC7>"
                        + message + "</col>");
            } else if (player.isSupremeDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=ffa34c>[Supreme]<img=13><col=ffa34c>"
                        + player.getDisplayName() + ": </col><col=357EC7>"
                        + message + "</col>");
            } else if (player.isLegendaryDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage( "<col="+player.getYellColor()+"><shad=>[Legendary]<img=10>"+ player.getDisplayName() + ": </col>"+ message + "</col>");
            } else if (player.isExtremeDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage( "<col="+player.getYellColor()+"><shad=>[Extreme]<img=8>" + player.getDisplayName() + ":" + message + "</col>");
            } else if (player.isDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "col="+player.getYellColor()+"[Donator]" + player.getDisplayName() + ":" + message + "</col>");
            } else if (player.getRights() == 2 && !player.getUsername().equalsIgnoreCase("paolo") && !player.getUsername().equalsIgnoreCase("") && !player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=ffff00><shad=000000>[Administrator]<img=1><col=ffff00><shad=000000>"
                        + player.getDisplayName() + ": </col><col=ffff00>"
                        + message + "</col>");
            } else if (player.isSupporter()) {
                players.getPackets().sendGameMessage(
                        "<col=3299CC><shad=3333FF>[Support-Team]<img=14> <col=3299CC><shad=3333FF>"
                        + player.getDisplayName() + ": </col><col=3299CC><shad=3333FF>"
                        + message + "</col>");
            }
            }
        }
	public static void archiveLogs(Player player, String[] cmd) {
		try {
			if (player.getRights() < 1)
				return;
			String location = "";
			if (player.getRights() == 2) {
				location = Settings.LOG_PATH +
						"" +
						"" +
						"/" + player.getUsername() + ".txt";
			} else if (player.getRights() == 1) {
				location = Settings.LOG_PATH + "mod/" + player.getUsername() + ".txt";
			}
			String afterCMD = "";
			for (int i = 1; i < cmd.length; i++)
				afterCMD += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			BufferedWriter writer = new BufferedWriter(new FileWriter(location,
					true));
			writer.write("[" + currentTime("dd MMMMM yyyy 'at' hh:mm:ss z") + "] - ::"
					+ cmd[0] + " " + afterCMD);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String currentTime(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
}