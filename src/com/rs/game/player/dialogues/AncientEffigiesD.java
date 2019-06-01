package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Skills;
import com.rs.game.player.content.AncientEffigies;
import com.rs.utils.Utils;

/**
 * Ancient effifies dialogue handling.
 * 
 * @author Raghav/Own4g3 <Raghav_ftw@hotmail.com>
 * 
 */
public class AncientEffigiesD extends Dialogue {

    int itemId;
    int skill1;
    int skill2;
    int selected;

    @Override
    public void start() {
	itemId = (Integer) parameters[0];
	sendDialogue(new String[] { "As you inspect the ancient effigy, you begin to feel a", "strange sensation of the relic searching your mind,", "drawing on your knowledge." });
	int random = Utils.random(8);
	if(player.effigySkill1 != 0){
	skill1 = player.effigySkill1; //= AncientEffigies.SKILL_1[random];
	skill2 = player.effigySkill2; //= AncientEffigies.SKILL_2[random];
	} else if (player.effigySkill1 == 0) {
	skill1= AncientEffigies.SKILL_1[random];
	skill2 = AncientEffigies.SKILL_2[random];
	player.effigySkill1 = skill1;
	player.effigySkill2 = skill2;
	}
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == -1) {
	    sendDialogue(new String[] { "Images from your experiences of " + AncientEffigies.getMessage(skill1), "fill your mind." });
	    stage = 0;
	} else if (stage == 0) {
	    sendOptionsDialogue("Which images do you wish to focus on?", Skills.SKILL_NAME[skill1], Skills.SKILL_NAME[skill2]);
	    stage = 1;
	} else if (stage == 1) {
	    int skill = componentId == OPTION_1 ? skill1 : skill2;
	    selected = componentId == OPTION_1 ? OPTION_1 : OPTION_2;
	    if (player.getSkills().getLevelForXp(skill) < AncientEffigies.getRequiredLevel(itemId)) {
		sendDialogue(

		new String[] { "The images in your mind fade; the ancient effigy seems", "to desire knowledge of experiences you have not yet", "had." });
		player.getPackets().sendGameMessage("You require at lest level " + AncientEffigies.getRequiredLevel(itemId) + " " + Skills.SKILL_NAME[skill1] + " to investigate the ancient effigy further.");
		player.setNextAnimation(new Animation(4067));
		stage = -2;
	    } else {
		sendDialogue(new String[] { "As you focus on your memories, you can almost hear a", "voice in the back of your mind whispering to you..." });
		stage = 2;
	    }
	} else if (stage == 2) {
	    int skill = selected == OPTION_1 ? skill1 : skill2;
	    int xp = AncientEffigies.getExp(itemId);
	    player.getSkills().addXp(skill, xp);
	    player.getPackets().sendGameMessage("You have gained " + Skills.SKILL_NAME[skill] + " experience!");
	    AncientEffigies.effigyInvestigation(player, itemId);
		player.effigySkill1 = 0;
		player.effigySkill2 = 0;
	    sendDialogue(new String[] { "The ancient effigy glows briefly; it seems changed", "somehow and no longer responds to the same memories", "as before." });
	    stage = 3;
	} else if (stage == 3) {
	    sendDialogue(new String[] { "A sudden bolt of inspiration flashes through your mind,", "revealing new insight into your experiences!" });
	    stage = -2;
	} else {
	    end();
	}
    }

    @Override
    public void finish() {

    }

}
