package com.rs.game.player.dialogues.impl.items;
import com.rs.game.player.content.items.RareBossBoxes;
import com.rs.game.player.dialogues.Dialogue;
/**
 * 
 * @author paolo
 *
 */
public class RareBossBoxesD extends Dialogue {


	@Override
	public void start() {
		sendOptionsDialogue("Select an option","Nex","Corporeal beast", "Kalphite king","Dark lord");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int option) {
		if(stage == 1){
			if(option == OPTION_1){
				RareBossBoxes.openBox(player, 13447);
				end();
			} else if(option == OPTION_2){
				RareBossBoxes.openBox(player, 8133);
				end();
			} else if(option == OPTION_3){
				RareBossBoxes.openBox(player, 16697);
				end();
			} else if(option == OPTION_4){
				RareBossBoxes.openBox(player, 19553);
				end();
			} 
		}
	}
	

	@Override
	public void finish() {

	}

}
