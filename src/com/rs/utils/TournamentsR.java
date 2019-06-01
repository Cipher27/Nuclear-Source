package com.rs.utils;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import com.rs.game.player.Player;

public final class TournamentsR implements Serializable {

	private static final long serialVersionUID = 5403480618483552509L;

	private String username;
	private int totalXp;

	private static TournamentsR[] ranks;

	private static final String PATH = "C:/data/tournament.ser";

	public TournamentsR(Player player) {
		this.username = player.getUsername();
		this.totalXp = player.tournamentXp;
	}

	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				ranks = (TournamentsR[]) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		ranks = new TournamentsR[300];
	}
	
	public static final String getLeader() {
		try {
			return ranks[0].username;
		} catch(NullPointerException e) {
			
		}
		return "Nobody";
	}
	
	public static final void reset() {
		ranks = new TournamentsR[300];
	}

	public static final void save() {
		try {
			SerializableFilesManager.storeSerializableClass(ranks, new File(
					PATH));
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static void showRanks(Player player) {
		for (int i = 0; i < 316; i++)
			player.getPackets().sendIComponentText(275, i, "");
		for (int i = 0; i < ranks.length; i++) {
			if (ranks[i] == null)
				break;
			String text;
			if (i >= 0 && i <= 2)
				text = "<img=4><col=ff9900>";
			else if (i <= 9)
				text = "<img=5><col=ff0000>";
			else if (i <= 50)
				text = "<img=6><col=38610B>";
			else
				text = "<img=3><col=000000>";
			player.getPackets()
					.sendIComponentText(
							275,
							i + 16,
							text
									+ "Top "
									+ (i + 1)
									+ " - "
									+ Utils.formatPlayerNameForDisplay(ranks[i].username)
									+ " - XP: " + ranks[i].totalXp + "M");
		}
		player.getPackets().sendIComponentText(275, 2,
				"Total XP Hiscores");
		player.getInterfaceManager().sendInterface(275);
	}

	public static void sort() {
		Arrays.sort(ranks, new Comparator<TournamentsR>() {
			@Override
			public int compare(TournamentsR arg0, TournamentsR arg1) {
				if (arg0 == null)
					return 1;
				if (arg1 == null)
					return -1;
				if (arg0.totalXp < arg1.totalXp)
					return 1;
				else if (arg0.totalXp > arg1.totalXp)
					return -1;
				else
					return 0;
			}

		});
	}

	public static void checkRank(Player player) {
		try {
			int xp = player.tournamentXp;
		/*	if(player.getRights() == 2) 
				return;*/
			for (int i = 0; i < ranks.length; i++) {
				TournamentsR rank = ranks[i];
				if (rank == null)
					break;
				if (rank.username.equalsIgnoreCase(player.getUsername())) {
					ranks[i] = new TournamentsR(player);
					sort();
					return;
				}
			}
			for (int i = 0; i < ranks.length; i++) {
				TournamentsR rank = ranks[i];
				if (rank == null) {
					ranks[i] = new TournamentsR(player);
					sort();
					return;
				}
			}
			for (int i = 0; i < ranks.length; i++) {
				if (ranks[i].totalXp < xp) {
					ranks[i] = new TournamentsR(player);
					sort();
					return;
				}
			}
		} catch(NullPointerException e) {
			
		}
	}

}
