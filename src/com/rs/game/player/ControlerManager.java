package com.rs.game.player;

import java.io.Serializable;

import com.rs.game.Entity;
import com.rs.game.Hit;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.content.Foods.Food;
import com.rs.game.player.content.Pots.Pot;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.controlers.ControlerHandler;
import com.rs.utils.Logger;

public final class ControlerManager implements Serializable {

	private static final long serialVersionUID = 2084691334731830796L;

	private transient Player player;
	private transient Controler controler;
	private transient boolean inited;
	private Object[] lastControlerArguments;

	private String lastControler;

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Controler getControler() {
		return controler;
	}
	
	public void startControler(Object key, Object... parameters) {
		if (controler != null)
			forceStop();
		controler = (Controler) (key instanceof Controler ? key : ControlerHandler.getControler(key));
		if (controler == null)
			return;
		controler.setPlayer(player);
		lastControlerArguments = parameters;
		lastControler = (String) key;
		controler.start();
		inited = true;
	}

	public void login() {
		if (lastControler == null)
			return;
		controler = ControlerHandler.getControler(lastControler);
		if (controler == null) {
			forceStop();
			return;
		}
		controler.setPlayer(player);
		if (controler.login())
			forceStop();
		else
			inited = true;
	}

	public void logout() {
		if (controler == null)
			return;
		if (controler.logout())
			forceStop();
	}

	public boolean canMove(int dir) {
		if (controler == null || !inited)
			return true;
		return controler.canMove(dir);
	}

	public boolean checkWalkStep(int lastX, int lastY, int nextX, int nextY) {
		if (controler == null || !inited)
			return true;
		return controler.checkWalkStep(lastX, lastY, nextX, nextY);
	}

	public boolean keepCombating(Entity target) {
		if (controler == null || !inited)
			return true;
		return controler.keepCombating(target);
	}

	public boolean canEquip(int slotId, int itemId) {
		if (controler == null || !inited)
			return true;
		return controler.canEquip(slotId, itemId);
	}

	public boolean canAddInventoryItem(int itemId, int amount) {
		if (controler == null || !inited)
			return true;
		return controler.canAddInventoryItem(itemId, amount);
	}

	public void trackXP(int skillId, int addedXp) {
		if (controler == null || !inited)
			return;
		controler.trackXP(skillId, addedXp);
	}

	public boolean canDeleteInventoryItem(int itemId, int amount) {
		if (controler == null || !inited)
			return true;
		return controler.canDeleteInventoryItem(itemId, amount);
	}

	public boolean canUseItemOnItem(Item itemUsed, Item usedWith) {
		if (controler == null || !inited)
			return true;

		return controler.canUseItemOnItem(itemUsed, usedWith);
	}

	public boolean canAttack(Entity entity) {
		if (controler == null || !inited)
			return true;
		return controler.canAttack(entity);
	}

	public boolean canPlayerOption1(Player target) {
		if (controler == null || !inited)
			return true;
		return controler.canPlayerOption1(target);
	}

	public boolean canHit(Entity entity) {
		if (controler == null || !inited)
			return true;
		return controler.canHit(entity);
	}

	public void moved() {
		if (controler == null || !inited)
			return;
		controler.moved();
	}

	public void magicTeleported(int type) {
		if (controler == null || !inited)
			return;
		controler.magicTeleported(type);
	}

	public void sendInterfaces() {
		if (controler == null || !inited)
			return;
		controler.sendInterfaces();
	}

	public void process() {
		if (controler == null || !inited)
			return;
		controler.process();
	}

	public boolean sendDeath() {
		if (controler == null || !inited)
			return true;
		return controler.sendDeath();
	}

	public boolean canEat(Food food) {
		if (controler == null || !inited)
			return true;
		return controler.canEat(food);
	}

	public boolean canPot(Pot pot) {
		if (controler == null || !inited)
			return true;
		return controler.canPot(pot);
	}

	public boolean useDialogueScript(Object key) {
		if (controler == null || !inited)
			return true;
		return controler.useDialogueScript(key);
	}

	public boolean processMagicTeleport(WorldTile toTile) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Teleported to the tile "+toTile.getX()+" "+toTile.getY()+" "+toTile.getPlane()+" using magic.");
		return controler.processMagicTeleport(toTile);
	}

	public boolean processItemTeleport(WorldTile toTile) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Teleported to the tile "+toTile.getX()+" "+toTile.getY()+" "+toTile.getPlane()+" using an item.");
		return controler.processItemTeleport(toTile);
	}

	public boolean processObjectTeleport(WorldTile toTile) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Teleported to the tile "+toTile.getX()+" "+toTile.getY()+" "+toTile.getPlane()+" using an object.");
		return controler.processObjectTeleport(toTile);
	}

	public boolean processObjectClick1(WorldObject object) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked Object 1 - "+object.getId()+".");
		return controler.processObjectClick1(object);
	}

	public boolean processButtonClick(int interfaceId, int componentId,
			int slotId, int packetId) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked the component "+componentId+" of the interface "+interfaceId+".");
		return controler.processButtonClick(interfaceId, componentId, slotId,
				packetId);
	}
	
    public boolean processButtonClick(int interfaceId, int componentId, int slotId, int slotId2, int packetId) {
	if (controler == null || !inited)
	    return true;
	return controler.processButtonClick(interfaceId, componentId, slotId, slotId2, packetId);
    }

	public boolean processNPCClick1(NPC npc) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked Npc 1 - "+npc.getId()+".");
		return controler.processNPCClick1(npc);
	}
	
	public boolean canSummonFamiliar() {
		if (controler == null || !inited)
			return true;
		return controler.canSummonFamiliar();
	}

	public boolean processNPCClick2(NPC npc) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked Npc 2 - "+npc.getId()+".");
		return controler.processNPCClick2(npc);
	}
	public boolean processNPCClick3(NPC npc) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked Npc 3 - "+npc.getId()+".");
		return controler.processNPCClick3(npc);
	}
	public boolean processNPCClick4(NPC npc) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked Npc 4 - "+npc.getId()+".");
		return controler.processNPCClick4(npc);
	}
	public boolean processObjectClick2(WorldObject object) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked Object 2 - "+object.getId()+".");
		return controler.processObjectClick2(object);
	}

	public boolean processObjectClick3(WorldObject object) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked Object 3 - "+object.getId()+".");
		return controler.processObjectClick3(object);
	}

	public boolean processItemOnNPC(NPC npc, Item item) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Used the item - "+item.getId()+" - on npc "+npc.getId()+".");
		return controler.processItemOnNPC(npc, item);
	}
	
    public boolean handleItemOnObject(WorldObject object, Item item) {
	if (controler == null || !inited)
	    return true;
	Logger.logControler("Used the item - "+item.getId()+" - on object "+object.getId()+".");
	return controler.handleItemOnObject(object, item);
    }
	
	public boolean canDropItem(Item item) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Attempted to drop the item - "+item.getId()+".");
		return controler.canDropItem(item);
	}
	
	public boolean processItemOnPlayer(Player usedOn, int itemId) {
		if (controler == null || !inited)
		return true;
		Logger.logControler("Used item on player - "+itemId+".");
		return controler.processItemOnPlayer(usedOn, itemId);
		}

	public void forceStop() {
		if (controler != null) {
			controler.forceClose();
			controler = null;
		}
		lastControlerArguments = null;
		lastControler = null;
		inited = false;
	}

	public void removeControlerWithoutCheck() {
		controler = null;
		lastControlerArguments = null;
		lastControler = null;
		inited = false;
	}

	public Object[] getLastControlerArguments() {
		return lastControlerArguments;
	}

	public void setLastControlerArguments(Object[] lastControlerArguments) {
		this.lastControlerArguments = lastControlerArguments;
	}

	
	public boolean processObjectClick5(WorldObject object) {
		if (controler == null || !inited)
			return true;
		Logger.logControler("Clicked Object 5 - "+object.getId()+".");
		return controler.processObjectClick5(object);
	}
	
    public boolean processObjectClick4(WorldObject object) {
	if (controler == null || !inited)
	    return true;
	Logger.logControler("Clicked Object 4 - "+object.getId()+".");
	return controler.processObjectClick4(object);
    }

    public void processNPCDeath(int id) {
		if (controler == null || !inited)
			return;
		controler.processNPCDeath(id);
	}

	public void processNPCDeath(NPC id) {
		if (controler == null || !inited)
			return;
		controler.processNPCDeath(id.getId());
	}
	
	public void processIncommingHit(Hit hit, Entity target) {
		if (controler == null || !inited)
			return;
		controler.processIncommingHit(hit, target);
	}

	public void processIngoingHit(Hit hit) {
		if (controler == null || !inited)
			return;
		controler.processIngoingHit(hit);
	}

}
