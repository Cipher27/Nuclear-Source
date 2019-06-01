package com.rs.game;

import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class WorldObject extends WorldTile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1137044005288770270L;
	private int id;
	private final int type;
	private int rotation;
	private int life;
	private int amount = Utils.random(10);
	private boolean permanentSpawn;
	public Player owner;


	public Player getOwner() {
	    return owner;
	}
	public WorldObject(int id, int type, int rotation, int x, int y, int plane, Player owner) {
		super(x, y, plane);
	    this.owner = owner;
		this.id = id;
		this.type = type;
		this.rotation = rotation;
		this.life = 1;
	}
	public WorldObject(int id, int type, int rotation, int x, int y, int plane) {
		super(x, y, plane);
		this.id = id;
		this.type = type;
		this.rotation = rotation;
		this.life = 1;
	}

	public WorldObject(int id, int type, int rotation, int x, int y, int plane,
			int life) {
		super(x, y, plane);
		this.id = id;
		this.type = type;
		this.rotation = rotation;
		this.life = life;
	}

	public WorldObject(int id, int type, int rotation, WorldTile tile) {
		super(tile.getX(), tile.getY(), tile.getPlane());
		this.id = id;
		this.type = type;
		this.rotation = rotation;
		this.life = 1;
	}

	public WorldObject(WorldObject object) {
		super(object.getX(), object.getY(), object.getPlane());
		this.id = object.id;
		this.type = object.type;
		this.rotation = object.rotation;
		this.life = object.life;
	}

	public void decrementAmount() {
		this.amount--;
	}

	public void decrementObjectLife() {
		this.life--;
	}

	public int getAmount() {
		return amount;
	}

	public ObjectDefinitions getDefinitions() {
		return ObjectDefinitions.getObjectDefinitions(id);
	}

	public int getId() {
		return id;
	}

	public int getLife() {
		return life;
	}

	public int getRotation() {
		return rotation;
	}

	public int getType() {
		return type;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public WorldObject setPermanentSpawn(boolean permanentSpawn) {
		this.permanentSpawn = permanentSpawn;
		return this;
	}
	
	public boolean isPermanentSpawn() {
		return permanentSpawn;
	}
}