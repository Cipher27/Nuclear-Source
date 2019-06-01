package com.rs.game.player.content.instances;

import com.rs.game.WorldObject;
import com.rs.game.player.controlers.Controler;

public class InstanceController extends Controler {

	private Instances instance;
	
	@Override
	public void start() {
		if (getArguments().length > 0 && getArguments()[0] instanceof Instances)
			instance = (Instances) getArguments()[0];
		else if (instance == null)
			removeControler();
	}

	@Override
	public boolean logout() {
		if (instance != null)
			instance.removePlayer(player, false);
		return false; // so doesnt remove script
	}

	@Override
	public boolean login() {
		if (instance != null)
			instance.removePlayer(player, false);
		return true; // so doesnt remove script
	}

	@Override
	public boolean processObjectClick2(final WorldObject object) {
		if (instance == null)
			return false;
		if (object.getId() == instance.getInstance().getExitObjectId() || object.getId() == 82016) {
			player.getPackets().sendGameMessage("The "+object.getDefinitions().name.toLowerCase()+" transports you outside of the boss room.", true);
			instance.removePlayer(player, false);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean sendDeath() {
		if (instance != null)
			instance.removePlayer(player, true);
		removeControler();
		return true;
	}

	@Override
	public void magicTeleported(int type) {
		if (instance != null)
			instance.removePlayer(player, true);
		removeControler();
	}

	
}

