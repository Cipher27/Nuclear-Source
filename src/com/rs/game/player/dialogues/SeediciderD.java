package com.rs.game.player.dialogues;



public class SeediciderD extends Dialogue {


    @Override
    public void start() {
	sendOptionsDialogue("Convert options, <col=00FF00> green</col> = convert, <col=FF0000>red </col>= don't convert",
		"Herb seeds.",
		"Tree seeds.",
		"Junk seeds.");
			stage =0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
 if (stage == 1) {
	if (componentId == OPTION_1) {
		sendOptionsDialogue("Convert option",
		"Ranarr seed.",
		"Avantoe seed",
		"Dwarf Weed seed",
		"Lantadyme seed",
		"Torstol seed");
		stage = 2;
	} else if (componentId == OPTION_2) {
		sendOptionsDialogue("Convert option",
		"Willow tree seed.",
		"Maple tree seed",
		"Yew tree seed",
		"Magic tree seed",
		"Papaya seed");
		stage = 5;
	}
	}else if (stage == 2) {
	if (componentId == OPTION_1) {
		
		stage = 2;
	} else if (componentId == OPTION_2) {
		
		stage = 5;
	}
	}
    }

    @Override
    public void finish() {

    }
}
