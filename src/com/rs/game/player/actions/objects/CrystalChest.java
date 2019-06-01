package com.rs.game.player.actions.objects;

import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.impl.CrystalChestAchievement;
import com.rs.game.world.RecordHandler;
import com.rs.utils.Utils;

public class CrystalChest {
	
	public static void Chest(Player player, final WorldObject object) {
        int random[][] = {{995, 10000}, {995, 70000}, {995, 25000},
        		{995, 200000},
        		{1616, 10}, //dragonstone unctu
				{4151, 1}, //whip
				{21773, 250}, //armadyl runes
				{563, 150},
        		{563, 100}, 
				{565, 100},
                {561, 100}, 
        		{561, 100}, 
				{28545, 1}, //end
				{535, 42},//babydbnones
				{537, 32}, //bdragon b
				{537, 26}, //bdragon b
				{537, 41}, //bdragon b
				{2364, 50}, //rune bar
				{2364, 40}, //rune bar
				{2364, 55}, //rune bar
				{989, 5},//crystal key
				{6920, 1},//infin boots
				{23716, 1},//huge lamp
				{23716, 1},//huge lamp
				{454, 150},//coal
				{454, 120},//coal
				{454, 130},//coal
        		{10034, 100},//chin
        		{10034, 120},//chin
				{1149,1}, //dmed helm
				{5289,1},
				{220, 12}, //torstol
				{220, 15}, //torstol
				{220, 22}, //torstol
				{212,20}, //grimmy avantoe
				{218,20}, //Grimy dwarfweed
				{214,20}, //grimy kwuarm
				{30372, 30}, //note paper
				{30372, 32}, //note paper
				{1514, 42}, //magic log
				{34927,1}, //boss lootbox x1
				{67, 42}, //yew longbow (u)
				{1516, 62}, //yew log
				{1516, 72}, //yew log
				{1516, 49}, //yew log
				{1618, 12}, //uncut diamon
				{1618, 26}, //uncut diamon
				{1632,25}, //uncut dstone
				{6573,1}, //onyx
				{6688, 36}, //saradomin brew(3)
				{3027,36}, //supper restore
				{2,120}, //cball
				{2,150}, //cball
				{2,170}, //cball
				{7671, 1}};//boxing gloves
        int rand = Utils.random(random.length);
		if(Utils.random(100) <= 1)
		 HandelSpecialReward(player);
		if(hasDragonstoneEquipped(player) > 0 && Utils.random(10) < hasDragonstoneEquipped(player)){
			 player.getInventory().addItem(random[rand][0],random[rand][1]);
			 player.getInventory().addItem(random[rand][0],random[rand][1]);
        player.sm("Your dragonstone armour doubled your loot.");	 
		}else 
        player.getInventory().addItem(random[rand][0],random[rand][1]);
		player.crystalChest++;
		player.getAchievementManager().notifyUpdate(CrystalChestAchievement.class);
	    RecordHandler.getRecord().handleCrystalKey(player);
	
	}
	public static int dragonStoneArmour[] = {28537,28539,28541,28543};
	private static int luck;
	
    public static final int hasDragonstoneEquipped(Player player) {
    	luck = 0;
		int helmId = player.getEquipment().getHatId();
		int chestId = player.getEquipment().getChestId();
		int legsId = player.getEquipment().getLegsId();
		int glovesId = player.getEquipment().getGlovesId();
		int bootsId = player.getEquipment().getBootsId();
		if (helmId == 28541)
			 luck++;
		if(chestId ==28539)
			 luck++;
		if (legsId == 28543)
			 luck++;
		if(glovesId == 28537)
			 luck++;
		if (bootsId == 28545)
			 luck++;
		
		return luck;
	
	}
	public static void HandelSpecialReward(Player player){
		player.sm("You have a received a new dragonstone armour piece in your bank.");
		player.getBank().addItem(new Item(dragonStoneArmour[Utils.random(0,dragonStoneArmour.length)],1), true);
	}
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 2380) {
			CrystalChest.Chest(player, object);
		}
	}

}
