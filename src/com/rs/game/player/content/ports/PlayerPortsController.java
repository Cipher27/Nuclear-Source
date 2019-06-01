package com.rs.game.player.content.ports;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.dialogues.Dialogue;

/**
 * Handles the Player Owned Ports controller.
 * 
 * @author Vichy
 */
public class PlayerPortsController extends Controler {

	/**
	 * Removes stuff when exiting controller.
	 */
	private void remove() {
		player.getInterfaceManager().closeChatBoxInterface();
		Dialogue.closeNoContinueDialogue(player);
		player.getHintIconsManager().removeUnsavedHintIcon();
				removeControler();
	}

	@Override
	public boolean login() {
		start();
		player.getPorts().spawnObjects();
		return false;
	}

	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public void magicTeleported(int type) {
		remove();
	}

	@Override
	public boolean sendDeath() {
		remove();
		return true;
	}

	@Override
	public boolean processObjectClick1(final WorldObject object) {
		if (object.getX() == 4087 && object.getY() == 7285 && player.getPorts().getWorkShop() != null) {
			player.getBank().openBank();
			return false;
		}
		else if(object.getX() == 4081 && object.getY() == 7277 && player.getPorts().getWorkShop() != null){
			player.getDialogueManager().startDialogue("TetsuSmithingD");
			return false;
		}
		else if(object.getX() == 4086 && object.getY() == 7279 && player.getPorts().getWorkShop() != null){
			player.getDialogueManager().startDialogue("RocktailSoupCookingD");
			return false;
		}
		else if(object.getX() == 4084 && object.getY() == 7281 && player.getPorts().getWorkShop() != null){
			player.getDialogueManager().startDialogue("DeathLotusCraftingD");
			return false;
		}
		else if(object.getX() == 4091 && object.getY() == 7278 && player.getPorts().getWorkShop() != null){
			player.getDialogueManager().startDialogue("SeaSingerCraftingD");
			return false;
		} else if(object.getId() == 81847)
			  player.getPorts().leavePorts();
		if (object.getId() == 72695) {
			if (player.getPorts().firstTimer)
				player.getDialogueManager().startDialogue("SimpleMessage", "Please finish the tutorial first.");
			else
				player.getPorts().openNoticeboard();
			return false;
		}
		return true;
	}

	@Override
	public boolean processNPCClick1(final NPC npc) {
		if (npc.getId() == 16554) {
			player.getDialogueManager().startDialogue("JohnStrumD", npc.getId());
			return false;
		}
		if (npc.getId() == 16518) {
			player.getDialogueManager().startDialogue("SeasingerUmiD", npc.getId());
			return false;
		}
		if (npc.getId() == 16550) {
			player.getDialogueManager().startDialogue("BarmaidD", npc.getId());
			return false;
		}
		if (npc.getId() == 18891) {
			player.getDialogueManager().startDialogue("TheTraderD", npc.getId());
			return false;
		}if (npc.getId() == 16594) {
			player.getDialogueManager().startDialogue("MegD", npc.getId());
			return false;
		}
		return true;
	}

	@Override
	public boolean processNPCClick2(final NPC npc) {
		if (npc.getId() == 18891) {
			if (player.getPorts().firstTimer)
				player.getDialogueManager().startDialogue("SimpleMessage", "The Trader has no goods "
						+ "for sale at the moment. Check bank once you have completed the Port tutorial.");
			else
				player.getDialogueManager().startDialogue("TheTraderRewardsD", npc.getId());
			return false;
		}
		return true;
	}

	@Override
	public void start() {
		player.setNextAnimation(new Animation(-1));
		player.setNextWorldTile(new WorldTile(4063, 7269, 0));
	//	player.getPorts().spawnObjects();
		if (!player.getPorts().meetRequirements())
			player.getDialogueManager().startDialogue("SimpleMessage",
					"Welcome to Player-Ports. You "
							+ "currently don't have the level to start your port. You must have level 90 or higher "
							+ "in at least ONE of these skills: Agility, Construction, Cooking, Divination, "
							+ "Dungeoneering, Fishing, Herblore, Hunter, Prayer, Runecrafting, Slayer or Thieving.");
		else {
			if (player.getPorts().firstTimer) {
				player.getHintIconsManager().addHintIcon(4060, 7264, 0, 120, 0, 0, -1, false);
				Dialogue.sendNPCDialogueNoContinue(player, 16554, Dialogue.ANGERY, "Come talk to me..");
			} else
				player.sendMessage("Welcome to Player-Ports.");
			player.setNextFaceWorldTile(new WorldTile(4063, 7268, 0));
		}
	}
}