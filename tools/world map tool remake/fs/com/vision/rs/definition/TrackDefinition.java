package com.vision.rs.definition;

public class TrackDefinition {
	private final int arrayId;
	private final int trackset;
	private final int fileId;
	private String name;
	private String unlock;
	private boolean isDefault = false;

	public TrackDefinition(int arrayId, String name, int fileId, int trackset) {
		this.arrayId = arrayId;
		this.name = name;
		this.fileId = fileId;
		this.trackset = trackset;
		if (trackset == 255)
			trackset = -1;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		int tracksetId = trackset == -1 || trackset == 255 ? -1 : TrackSet.arrayIds[trackset];
		return "TrackDefinition [arrayId=" + arrayId + ", trackset="
				+ (tracksetId != -1 ? "Trackset: " + TrackSet.tracksets[tracksetId].getSetName() + ", " : "")
				+ ", fileId=" + fileId + ", name=" + name + ", unlock=" + unlock + "]";
	}

	public int getFileId() {
		return fileId;
	}

	public int getArrayId() {
		return arrayId;
	}

	public String toSimpleString() {
		return arrayId + ": " + name;
	}

	/**
	 * @return the trackset
	 */
	public int getSet() {
		return trackset;
	}

	/**
	 * @return the unlock place, in textual form.
	 */
	public String getUnlock() {
		return unlock;
	}

	/**
	 * sets the unlock place for this track.
	 * 
	 * @param unlock
	 *            the unlock place of this track.
	 */
	public void setUnlock(String unlock) {
		this.unlock = unlock;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
