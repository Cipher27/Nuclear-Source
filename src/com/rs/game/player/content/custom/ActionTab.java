package com.rs.game.player.content.custom;

public class ActionTab {
	
/*	public static String getFormattedNumber(double kdr) {
		return new DecimalFormat("#.##").format(kdr).toString ();
	}
	
	public static void sendTab(final Player player) {
		if (!player.isRunning())
			return;
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run(){
				if (player == null || player.hasFinished() || !player.isRunning()) {
					this.stop();
					return;
				}
				if (World.getPlayers().size() > 0 && World.getPlayers().size() >  PersistentData.getPersistentData().getMostPlayersOnline())
				PersistentData.getPersistentData().setMostPlayersOnline(World.getPlayers().size());
				SlayerManager manager = player.getSlayerManager();
				double kill = player.getKillCount();
				double death = player.getDeathCount();
				double kdr = kill / death;
				if (kdr <= 0)
					kdr = 0;
				int ringId = player.getEquipment().getRingId();
				double dropRate = 1.5;
				if (ItemDefinitions.getItemDefinitions(ringId).getName().toLowerCase().contains("ring of wealth"))
					dropRate += 0.04;
				if (player.isDonator())
					dropRate += 0.04;
				if (player.getPrestigeLevel() >= 1)
					dropRate += player.getPrestigeLevel() * 0.01;
				/*if (player.getInstance() != null && player.getControlerManager().getControler() != null) {//TODO
					if (player.getControlerManager().getControler() instanceof InstanceController) {
						if (player.getInstance().respawnTime == 60)
							dropRate -= 0.30;
						else if (player.getInstance().respawnTime == 40)
							dropRate -= 0.40;
						else if (player.getInstance().respawnTime == 20)
							dropRate -= 0.50;
					}
				}*/
			/*final long milliseconds = System.currentTimeMillis() - Launcher.ONLINE;
				//int seconds = (int) (milliseconds / 1000) % 60;
				//int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
				final int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
				final long days = milliseconds / (24 * 60 * 60 * 1000);
				player.getPackets().sendIComponentText(930, 10, "<col=ff0000>Personal Statistics.</col>");
				player.getPackets().sendIComponentText(930, 16, 
					"Rank: <col=FFFFFF>"+(player.getRights() == 4 ? "<img=1><shad=FFFF00>Owner</shad>" :
						player.getRights() == 3 ? "<img=1><shad=FFFF00>Hidden Admin</shad>" : 
						player.getRights() == 2 ? "<img=1><shad=FF0000>Administrator</shad>" :
							player.getRights() == 1 ? "<img=0><shad=D8D8D8>Moderator</shad>" : 
							player.getRights() == 0 && player.isSupporter() ? "<img=14><shad=2E2EFE>Support</shad>" :
								player.isForumModerator() ? "<img=10><shad=3ADF00>Forum Moderator</shad>" :
								player.isGraphicDesigner() ? "<img=9><shad=BFFF00>GFX Artist</shad>" :
								"Player")+"</col><br>" +
								"Uptime:<col=FFFFFF> " + days + " days, " + hours + " hours<br>" +
								"Donator Status: <col=ffffff>"+(player.isPhilanthropist() ? "<img=12><shad=0592e7>Philanthropist</shad>" : player.isExtremeDonator() ? "<img=15><shad=0000ff>Extreme</shad>" : 
									player.isDonator() ? "<img=16><shad=00ff00>Regular</shad>" :
									"N/A")+"</col><br>" +
					"Game Mode: <col=ffffff>"+(player.isUltimate() ? "<img=18><shad=ff0000>Ultimate Ironman Mode</shad>" : player.isIronman() ? 
					"<img=18><shad=ff0000>Ironman Mode</shad>" :
					"<shad=ff0000>Regular Mode</shad>")+"</col><br>" +
					"Playtime: <col=ffffff>"+(player.days+"D "+player.hours+"H "+player.minutes+"M")+"</col><br>"+
					//"Experience Rate: <col=ffffff>"+(player.isEasy() ? "Easy" : player.isHard() ? "Hard" : player.isInsane() ? "Insane" : "Medium")+"<br>" +
					"Drop Rate Modifier: <col=ffffff>"+dropRate+"<br>" +
					//"Most Players Online: <col=ffffff>"+PersistentData.getPersistentData().getMostPlayersOnline()+"</col><br>" +
					"Players Currently Online: <col=FFFFFF>"+World.getPlayers().size()+" </col><br><br>" +
					//"Donator Points: <col=ffffff>"+player.donatorPoints+"</col><br>" +
					//"Donator Points: <col=ffffff>"+player.donatorPoints+"</col><br>" +
					"Trivia Points: <col=ffffff>"+player.getTriviaPoints()+"</col><br>" +
					//"Dominion Factor: <col=ffffff>"+player.getDominionFactor()+"</col><br>"+
					//"Zeals: <col=ffffff>"+player.zeals+"</col><br>"+
					"Void commendations: <col=ffffff>"+player.pestPoints+"</col><br>"+
					//"Hunger Points: <col=ffffff>"+player.getHungerPoints()+"</col><br>" +
					//"PvM Points: <col=ffffff>"+player.getPvmPoints()+"</col><br>" +
					"Loyalty Points: <col=ffffff>"+player.getLoyaltyPoints()+"<br>"+
					//"Spins on the SoF: <col=ffffff>"+player.getSquealOfFortune().getSpinsCount()+"</col><br><br>" +
					//"Players in Wilderness: <col=ffffff>"+World.checkWildernessPlayers()+"</col><br>" +
					//"Players at Duel Arena: <col=ffffff>"+World.checkStakers()+"</col><br>" +
					//"Lucien: <col=ffffff>"+
					//		(LucienAnnouncer.INSTANCE.LUCIEN != null && !LucienAnnouncer.INSTANCE.LUCIEN.hasFinished() 
					//? "Alive" : "Dead")+"</col><br>" +
					//"Warbands Event: <col=ffffff>"+(Warbands.warband == null ? "None" : Utils.getHoursMinsLeft(Warbands.warband.time))+"</col><br>"+
					//"Current Killstreak: <col=ffffff>"+player.getKillstreak()+"</col><br>" +
					//"Highest Killstreak: <col=ffffff>"+player.getHighestKillstreak()+"</col><br>" +
					"PvP Kills: <col=FFFFFF>"+player.getKillCount()+"</col><br>" +
					"PvP Deaths: <col=FFFFFF>"+player.getDeathCount()+"</col><br>" +
					"KDR: <col=FFFFFF>"+(kdr <= 0 ? "0" : getFormattedNumber(kdr))+"</col><br>" +
					//"Pk Points: <col=ffffff>"+player.getPkPoints()+"</col><br><br>" +
				/*	"Daily Task: <col=ffffff>"+(player.getDailyTask() != null ? player.getDailyTask().reformatTaskName(player.getDailyTask().getName()) : "Null; tell Hc747")+"</col><br>"+
					"Amount: <col=ffffff>"+(player.getDailyTask() != null ? player.getDailyTask().getAmountCompleted()+"/"+player.getDailyTask().getTotalAmount() : "Null; tell Hc747")+"</col><br><br>"+
					"Partner: <col=FFFFFF>"+(manager != null && manager.getPartner() != null && manager.getPartner().isRunning() ? manager.getPartner().getDisplayName() : "None")+"<br>"+
					"Slayer Points: <col=ffffff>"+(manager != null ? manager.getSlayerPoints() : 0)+"</col><br>" +
					"Task Streak: <col=ffffff>"+(manager != null ? manager.getTaskStreak() : 0)+"</col><br>" +
					"Slayer Task: <col=ffffff>"+(manager != null && manager.getSlayerTask() != null ? manager.getSlayerTask().getName() : "None")+"</col><br>" +
					"Amount Remaining: <col=ffffff>"+(manager != null && manager.getSlayerTask() != null ? manager.getAmountLeft()+" of "+manager.getMaxTaskAmount() : 0)+"</col><br><br>" +
					"Achieved Trimmed: <col=ffffff>"+(player.isCompletedTrimmedComp() ? "True" : "False")+"</col><br>" +
					"Achieved Comp: <col=ffffff>"+(player.isCompletedComp() ? "True" : "False")+"</col><br>" +
				//	"Achieved Max: <col=ffffff>"+(player.isMaxed() ? "True" : "False")+"</col><br>" +
			//		"Achieved Skiller: <col=ffffff>"+(player.isCompletedSkill() ? "True" : "False")+"</col><br>" +
					//"Prestige: <col=ffffff>"+(player.prestigeNumber > 0 ? player.prestigeNumber : "N/A")+"</col><br><br>" +
					//"Warnings: <col=FFFFFF>"+player.blackMark+"/3</col><br>" +
					//"Mute Status: <col=FFFFFF>"+(IPMute.isMuted(player.getSession().getIP()) ? "IP Muted" : player.getMuted() > Utils.currentTimeMillis() ? "Muted" : "Unmuted")+"</col><br>" +
				//	"Jail Status: <col=FFFFFF>"+(player.getJailed() > Utils.currentTimeMillis() ? "Jailed" : "Unjailed")+"</col><br>"
		/*		);
			}
		}, 0, 5);
	}*/
}
		
	//}
	
	/* private static String getDiffString(int diff) {
	    	if (diff == 1)
	    		return "Very Easy";
	    	if (diff == 2)
	    		return "Easy";
	    	if (diff == 3)
	    		return "Normal";
	    	if (diff == 4)
	    		return "Hard";
	    	if (diff == 5)
	    		return "Extreme";
	    	return "";
	    }*/
//}