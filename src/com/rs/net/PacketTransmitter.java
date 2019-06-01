package com.rs.net;

import com.rs.net.encoders.WorldPacketsEncoder;

/**
 * @author _Jordan <citellumrsps@gmail.com>
 * @date Jan 25, 2015
 */
public class PacketTransmitter {
	
	/**
	 * Represents the {@link Session} to use.
	 */
	private final Session session;
	
	/**
	 * Reoresents the {@link GamePacketEncoder} to use.
	 */
	private WorldPacketsEncoder packets;
	
	/**
	 * Constructs a new {@link Session} {@code Object}.
	 * @param session Represents the session to use.
	 */
	public PacketTransmitter(Session session) {
		this.session = session;
		this.setPackets(packets);
	}

	/**
	 * Gets the session.
	 * @return The session.
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Gets the packets.
	 * @return The packets.
	 */
	public WorldPacketsEncoder getPackets() {
		return packets;
	}

	/**
	 * Sets the packets.
	 * @param packets The packets to set.
	 */
	public void setPackets(WorldPacketsEncoder packets) {
		this.packets = packets;
	}

}
