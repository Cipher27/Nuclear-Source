package com.rs.game.minigames.tournament;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Skills;
import com.rs.game.player.controlers.Controler;

public final class TournamentControler extends Controler {
	
	public boolean spawnedEquipment;
	
	@Override
	public void start() {
		spawnedEquipment = (boolean) getArguments()[0];
		player.closeInterfaces();
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		//player.getPackets().sendGameMessage("You can't teleport out of the tournament area!");
		return true;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		//player.getPackets().sendGameMessage("You can't teleport out of the tournament area!");
		return true;
	}
	
	@Override
	public boolean processObjectClick1(WorldObject object) {
		//match.end(player.getDisplayName(), "");
		return true;
	}
	
	@Override
	public void forceClose() {
		//player.setCanPvp(false);
		//match.end(player.getDisplayName(), player.getDisplayName());
	}
	
	@Override
	public boolean logout() {
		//player.setWorldTile(new WorldTile(2992, 9676, 0));
		//match.end(player.getDisplayName(), player.getDisplayName());
		return false; // Keep controler
	}

	public boolean canEnterMatch(int combatIndex, int typeIndex) {
		if (player.getFamiliar() != null) {
			player.sendMessage("You cannot start a match until your familiar is dismissed.");
			return false;
		}
		if (player.getPet() != null) {
			player.sendMessage("You cannot start a match until your pet is dismissed.");
			return false;
		}
		if (!spawnedEquipment) {
			return true;
		}
		if (player.getInventory().getItems().getUsedSlots() + player.getEquipment().getItems().getUsedSlots() > 0) {
			player.sendMessage("Please bank any items you're holding or wearing before you start a match.");
			return false;
		}
		/*if (player.getMoneyPouch().getCoinsAmount() != 0) {
			player.getPackets().sendGameMessage("You're not allowed to bring coins with, sorry.");
			player.getPackets().sendGameMessage("Deposit your money pouch's coins at the local deposit box near you.");
			return false;
		}*/
		if (player.getSkills().getLevelForXp(Skills.ATTACK) < Information.ATTACK_REQUIREMENT[combatIndex][typeIndex]) {
			player.sendMessage("You need an attack level of "+Information.ATTACK_REQUIREMENT[combatIndex][typeIndex]+" to fight this match.");
			return false;
		}
		if (player.getSkills().getLevelForXp(Skills.DEFENCE) < Information.DEFENCE_REQUIREMENT[combatIndex][typeIndex]) {
			player.sendMessage("You need a defence level of "+Information.DEFENCE_REQUIREMENT[combatIndex][typeIndex]+" to fight this match.");
			return false;
		}
		if (player.getSkills().getLevelForXp(Skills.MAGIC) < Information.MAGIC_REQUIREMENT[combatIndex][typeIndex]) {
			player.sendMessage("You need a magic level of "+Information.MAGIC_REQUIREMENT[combatIndex][typeIndex]+" to fight this match.");
			return false;
		}
		if (player.getSkills().getLevelForXp(Skills.RANGE) < Information.RANGE_REQUIREMENT[combatIndex][typeIndex]) {
			player.sendMessage("You need a range level of "+Information.RANGE_REQUIREMENT[combatIndex][typeIndex]+" to fight this match.");
			return false;
		}
		return true;
	}
}