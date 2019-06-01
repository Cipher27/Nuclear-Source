package com.rs.game.player.content.commands;

import java.io.File;
import java.io.IOException;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.Player;
import com.rs.game.player.content.LegendaryPet;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.PestInvasion;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.events.DeathEvent;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

/**
 * handles the commands of an moderator
 * @author Zero_Gravity
 *
 */
public class ModeratorCommands {
	
	@SuppressWarnings("unused")
	public static boolean processModCommand(Player player, String[] cmd,
			boolean console, boolean clientCommand) {
		if (clientCommand) {

		} else {
			switch (cmd[0]) {
			 case "checkinvy": {
				 String name;
					if (!player.hasStaffPin) {
			   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
						player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
					} else {
	                name = "";
	                for (int i = 1; i < cmd.length; i++) {
	                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
	                }
	                Player target1 = World.getPlayerByDisplayName(name);
	                try {
	            	player.getInterfaceManager().sendInventoryInterface(670);
	            	player.getPackets().sendItems(93, target1.getInventory().getItems());
	                } catch (Exception e) {
	                	}
					}
	            }
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
					
			 case "pettt":
					player.legendaryPet = new LegendaryPet(17780, player);
					player.getLegendaryPet().spawnPet();
					return true;

				case "envolve":
					player.getLegendaryPet().envolve();
					return true;

			 case "checkbank": {
				 String name;
					if (!player.hasStaffPin) {
			   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
						player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
					} else {
					if(player.isLocked() ||  player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return true;
					}
					if(player.isLocked() || player.getControlerManager().getControler() instanceof PestInvasion){
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return true;
					}
	                name = "";
	                for (int i = 1; i < cmd.length; i++) {
	                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
	                }
	                Player Other = World.getPlayerByDisplayName(name);
	                try {
	                    player.getPackets().sendItems(95, Other.getBank().getContainerCopy());
	                    player.getBank().openPlayerBank(Other);
	                } catch (Exception e) {
	                }
	            }
	            }
	            return true;
			case "unmute":
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(0);
					target.getPackets().sendGameMessage(
							"You've been unmuted by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have unmuted: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setMuted(0);
					player.getPackets().sendGameMessage(
							"You have unmuted: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			case "banhammer": 
						if (player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("liam") || player.getUsername().equalsIgnoreCase("Zero_Gravity")) {
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other = World.getPlayerByDisplayName(username);
					if (other == null)
						return true;
					Magic.sendTrialTeleportSpell(other, 0, 0.0D, new WorldTile(3680, 3616, 0), new int[0]);
					other.stopAll();
					other.lock();
					return true;
				}
				return true;
			case "death1": if (player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("Zero_Gravity") || player.getUsername().equalsIgnoreCase("liam")) {
							String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
							Player other = World.getPlayerByDisplayName(username);
							if (other == null)
								return true;
							other.setNextAnimation(new Animation(17532));
							other.setNextGraphics(new Graphics(3397));
							other.stopAll();
							other.applyHit(new Hit(other, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
							other.stopAll();
							other.unlock();
							return true;
						}
			return true;
			
		
			case "death2": 
				if (player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("Zero_Gravity") || player.getUsername().equalsIgnoreCase("liam")) {
							String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
							Player other = World.getPlayerByDisplayName(username);
							if (other == null)
								return true;
							other.setNextAnimation(new Animation(17523));
							other.setNextGraphics(new Graphics(3396));
							other.stopAll();
							other.applyHit(new Hit(other, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
							other.stopAll();
							other.unlock();
							
							return true;
						}
				return true;
			case "permban":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				Player.bans(player, name);
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					target.setPermBanned(true);
					target.getPackets().sendGameMessage(
							"You've been perm banned by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have perm banned: "+target.getDisplayName()+".");
					target.getSession().getChannel().close();
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc11 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					if (target.getRights() == 2)
						return true;
					target.setPermBanned(true);
					player.getPackets().sendGameMessage(
							"You have perm banned: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc11);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
				return true;

			case "jail":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				Player.jails(player, name);
				if (player.isInDung() || target.isInDung()) {
					return true;
				}
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					target.getControlerManager()
					.startControler("JailControler");
					target.getPackets().sendGameMessage(
							"You've been Jailed for 24 hours by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have Jailed 24 hours: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setJailed(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					player.getPackets().sendGameMessage(
							"You have muted 24 hours: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;

			case "kick":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null) {
					player.getPackets().sendGameMessage(
							Utils.formatPlayerNameForDisplay(name)+" is not logged in.");
					return true;
				}
				target.getSession().getChannel().close();
				player.getPackets().sendGameMessage("You have kicked: "+target.getDisplayName()+".");
				return true;

			case "staffyell":
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Commands.sendYell(player, Utils.fixChatMessage(message), true);
				return true;


			case "hide":
				/*if (player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot hide in a public event!");
					return true;
				}¨¨*/
				player.getAppearence().switchHidden();
				player.getPackets().sendGameMessage("Hidden? " + player.getAppearence().isHidden());
				return true;

			case "unjail":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(0);
					target.getControlerManager()
					.startControler("JailControler");
					target.getPackets().sendGameMessage(
							"You've been unjailed by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have unjailed: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setJailed(0);
					player.getPackets().sendGameMessage(
							"You have unjailed: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;

			case "teleto":
				if (player.isLocked() || player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (Wilderness.isAtWild(player) || Wilderness.isAtWild(target) || player.isInDung() || target.isInDung()) {
					player.sm("Nice try");
					return true;
				}
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else
					player.setNextWorldTile(target);
				return true;
			case "teletome":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (Wilderness.isAtWild(player) || Wilderness.isAtWild(target) || player.isInDung() || target.isInDung()) {
					player.sm("Nice try");
					return true;
				}
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else {
					if (target.isLocked() || target.getControlerManager().getControler() != null) {
						player.getPackets().sendGameMessage("You cannot teleport this player.");
						return true;
					}
					if (target.getRights() > 1) {
						player.getPackets().sendGameMessage(
								"Unable to teleport a developer to you.");
						return true;
					}
					target.setNextWorldTile(player);
				}
				return true;
	
			case "unnull":
			case "sendhome":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (player.isInDung() || target.isInDung()) {
					return true;
				}
				target.unlock();
				target.getControlerManager().forceStop();
				if(target.getNextWorldTile() == null) { //if controler wont tele the player
					
					target.setNextWorldTile(new WorldTile(2332,3679,0));
				}
				player.getPackets().sendGameMessage("You have unnulled: "+target.getDisplayName()+".");
				return true;
			}
		}
		return false;
	}

}
