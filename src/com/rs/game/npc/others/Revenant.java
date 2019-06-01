package com.rs.game.npc.others;

import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;

@SuppressWarnings("serial")
public class Revenant extends NPC {

	public Revenant(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		// TODO Auto-generated constructor stub
		setForceTargetDistance(4);
	}
	
	@Override
	public void spawn() {
		super.spawn();
		setNextAnimation(new Animation(getSpawnAnimation()));
	}
	public static void useForinthryBrace(Player player, Item item, int slot) {
		//to make unagro + item message
		int newId = item.getId() == 11103 ? -1 : item.getId() + 2;
		player.getInventory().getItems().set(slot, newId == -1 ? null : new Item(newId));
		player.getInventory().refresh(slot);
		player.getPackets().sendGameMessage("For one minute, revenants cannot damage you.");
		player.getPackets().sendGameMessage("For one hour, revenants will be unagressive to you.");
		
	}
	
	public int getSpawnAnimation() {
		switch(getId()) {
		case 13465: return 7410;
		case 13466:
		case 13467:
		case 13468:
		case 13469: return 7447;
		case 13470:
		case 13471: return 7485;
		case 13472: return -1;
		case 13473: return 7426;
		case 13474: return 7403;
		case 13475: return 7457;
		case 13476: return 7464;
		case 13477: return 7478;
		case 13478: return 7416;
		case 13479: return 7471;
		case 13480: return 7440;
		case 13481:
		default: return -1;
		}
	}

}
