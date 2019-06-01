package com.vision.rs.definition;

public class TrackSet {
	public static TrackSet[] tracksets = new TrackSet[43];
	private static int tracksetCount = 0;
	public static int[] arrayIds = new int[60];

	private int setId;
	private String setName;
	private int color;

	public TrackSet(String setName, int setId) {
		this.setName = setName;
		this.setId = setId;
	}

	public String toString() {
		return setId + " - " + setName + " trackset";
	}

	static {
		addTrackset(new TrackSet("\"Only defined\"", 0));
		addTrackset(new TrackSet("Wilderness", 1));
		addTrackset(new TrackSet("Draynor Manor", 2));
		addTrackset(new TrackSet("Varrock", 3));
		addTrackset(new TrackSet("Lumbridge", 4));
		addTrackset(new TrackSet("Spooky", 5));

		addTrackset(new TrackSet("Fremennik", 7));
		addTrackset(new TrackSet("Kharidian", 8));
		addTrackset(new TrackSet("Miscellania", 9));
		addTrackset(new TrackSet("Jungle", 10));
		addTrackset(new TrackSet("Elven", 11));
		addTrackset(new TrackSet("Tree Gnome Stronghold", 12));
		addTrackset(new TrackSet("Ogre", 13));
		addTrackset(new TrackSet("Falador", 14));
		addTrackset(new TrackSet("Port", 15));
		addTrackset(new TrackSet("Feldip hills", 16));
		addTrackset(new TrackSet("Yanille(?) ", 17));
		addTrackset(new TrackSet("Eastern ardougne", 18));
		addTrackset(new TrackSet("Seers Village & Camelot", 19));
		addTrackset(new TrackSet("Waterfall (?)", 20));
		addTrackset(new TrackSet("Hunter (?)", 21));
		addTrackset(new TrackSet("Iceberg/Asgarnian ice dungeon/muspah's tomb",
				22));
		addTrackset(new TrackSet("Zanaris", 23));
		addTrackset(new TrackSet("Abyss", 24));
		addTrackset(new TrackSet("Morytania", 25));
		addTrackset(new TrackSet("Easter", 26));
		addTrackset(new TrackSet("Christmas", 27));
		addTrackset(new TrackSet("Halloween", 28));

		addTrackset(new TrackSet("Unknown. Troll?", 30));
		addTrackset(new TrackSet("Tirannwn", 31));
		addTrackset(new TrackSet("Castle wars", 32));
		addTrackset(new TrackSet("Cave", 33));
		addTrackset(new TrackSet("Goblin", 34));
		addTrackset(new TrackSet("Dwarf", 35));
		addTrackset(new TrackSet("TzHaar", 36));
		addTrackset(new TrackSet("Construction", 37));
		addTrackset(new TrackSet("Fremennik Isles", 38));
		addTrackset(new TrackSet("Track 547", 39));
		addTrackset(new TrackSet("Northwest Ardougne", 40));
		addTrackset(new TrackSet("Pest Control", 41));
		addTrackset(new TrackSet("Dorgeshuun", 42));
		addTrackset(new TrackSet("Bar Music", 43));

		addTrackset(new TrackSet("Wizards Tower", 50));

	}

	private static void addTrackset(TrackSet trackSet) {
		trackSet.color = ((int) ((tracksetCount * 50 % 1 * 100) * Math.random()) & 0xffff);
		tracksets[tracksetCount] = trackSet;
		arrayIds[trackSet.setId] = tracksetCount;
		tracksetCount++;
	}

	public int getColor() {
		return color & 0xFFFFFF;
	}

	/**
	 * @return the setName
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * @param setName
	 *            the setName to set
	 */
	public void setSetName(String setName) {
		this.setName = setName;
	}
}
