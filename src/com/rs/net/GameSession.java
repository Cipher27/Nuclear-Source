package com.rs.net;


import org.jboss.netty.channel.Channel;

import com.rs.game.player.Player;

/**
 * @author _Jordan <citellumrsps@gmail.com>
 * @date Jan 21, 2015
 */
public class GameSession extends Session {

	/**
	 * Represents the {@link Player} to connect for the session.
	 */
	private Player player;

	/**
	 * Constructs a new {@code GameSession} {@link Object}.
	 * 
	 * @param channel
	 *            The connected {@link Channel} to construct.
	 * @param The
	 *            {@link Player} to construct.
	 */
	public GameSession(Channel channel, Player player) {
		super(channel);
		this.player = player;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.elveron.network2.attachment.session.Session#channelRead(java.lang.
	 * Object)
	 */
	

}
