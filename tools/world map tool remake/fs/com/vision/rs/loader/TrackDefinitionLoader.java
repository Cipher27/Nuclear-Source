package com.vision.rs.loader;

import java.util.ArrayList;
import java.util.HashMap;

import com.vision.rs.definition.EnumDefinition;
import com.vision.rs.definition.TrackDefinition;

public class TrackDefinitionLoader {
	private static HashMap<Integer, ArrayList<TrackDefinition>> tracksets = new HashMap<>();
	private static TrackDefinition[] tracks;
	private static int[] defaultUnlocks;
	private static int defaultUnlockCount = 0;
	// private ArrayList<Integer> defaults = new ArrayList<>();

	public TrackDefinitionLoader(EnumLoader enumLoader) {
		EnumDefinition nameContainer = enumLoader.loadDefinition(1345);// Names
		EnumDefinition fileIndexContainer = enumLoader.loadDefinition(1351);// ids
		EnumDefinition setContainer = enumLoader.loadDefinition(1346);
		EnumDefinition defaultContainer = enumLoader.loadDefinition(1350);// Defaults

		EnumDefinition unlock = enumLoader.loadDefinition(1349);
		EnumDefinition unlock2 = enumLoader.loadDefinition(952);
		defaultUnlockCount = defaultContainer.getEntryCount();
		defaultUnlocks = new int[defaultUnlockCount];
		// for (int i = 0; i < defaultUnlockCount; i++) {
		// int id = defaultContainer.getIntValue(i);
		// defaults.add(id);
		// }
		// 952 trackunlock2
		tracks = new TrackDefinition[nameContainer.getEntryCount() + 1];
		for (int trackId = 0; trackId <= nameContainer.getEntryCount(); trackId++) {
			if (trackId == 500) {
				if (unlock.getStringValue(500).equalsIgnoreCase("default info")) {
					unlock = unlock2;
				}
			}
			String trackName = nameContainer.getStringValue(trackId);
			System.out.println(trackName + " " + trackId);
			tracks[trackId] = new TrackDefinition(trackId, trackName, fileIndexContainer.getIntValue(trackId),
					setContainer.getIntValue(trackId));
			tracks[trackId].setUnlock(unlock.getStringValue(trackId));
			if (/* defaults.contains(trackId) || */ tracks[trackId].getUnlock().toLowerCase().contains("automatical")) {
				tracks[trackId].setDefault(true);
			}
			int setId = setContainer.getIntValue(trackId);
			if (setId != -1 && setId != 255) {
				ArrayList<TrackDefinition> trackset = tracksets.get(setId);
				if (trackset == null) {
					trackset = new ArrayList<>();
					tracksets.put(setId, trackset);
				}
				trackset.add(tracks[trackId]);
			}
		}
	}

	public int[] getDefaultUnlocks() {
		return defaultUnlocks;
	}

	public String getMusicName(int id) {
		if (tracks.length <= id)
			return null;
		if (tracks[id] == null)
			return null;
		String name = getTracks()[id].getName();
		if (name.equalsIgnoreCase(""))
			return null;
		return name;
	}

	public TrackDefinition[] getTracks() {
		return tracks;
	}

	/**
	 * @return the defaultUnlockCount
	 */
	public int getDefaultUnlockCount() {
		return defaultUnlockCount;
	}
}
