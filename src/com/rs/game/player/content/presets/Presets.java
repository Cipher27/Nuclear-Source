package com.rs.game.player.content.presets; //done 

import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.actions.summoning.Summoning;
import com.rs.game.player.actions.summoning.Summoning.Pouches;

public class Presets {

	public static void Brid (Player player) {
		player.getEquipment().getItems()
		.set(Equipment.SLOT_CAPE, new Item(2412));
player.getEquipment().getItems()
		.set(Equipment.SLOT_HAT, new Item(10828));
player.getEquipment().getItems()
		.set(Equipment.SLOT_CHEST, new Item(4712));
player.getEquipment().getItems()
		.set(Equipment.SLOT_SHIELD, new Item(24100));
player.getEquipment().getItems()
		.set(Equipment.SLOT_LEGS, new Item(4714));
player.getEquipment().getItems()
		.set(Equipment.SLOT_FEET, new Item(6920));
player.getEquipment().getItems()
		.set(Equipment.SLOT_HANDS, new Item(7462));
player.getEquipment().getItems()
		.set(Equipment.SLOT_WEAPON, new Item(15486));
player.getEquipment().getItems()
		.set(Equipment.SLOT_AMULET, new Item(18335));
player.getEquipment().getItems()
		.set(Equipment.SLOT_RING, new Item(6737));
player.getInventory().reset();
Summoning.spawnFamiliar(player, Pouches.WOLPERTINGER);
player.getInventory().addItem(11724, 1);
player.getInventory().addItem(20072, 1);
player.getInventory().addItem(6585, 1);
player.getInventory().addItem(6570, 1);
player.getInventory().addItem(11726, 1);
player.getInventory().addItem(4151, 1);
player.getInventory().addItem(11732, 1);
player.getInventory().addItem(4736, 1);
player.getInventory().addItem(5698, 1);
player.getInventory().addItem(15272, 6);
player.getInventory().addItem(23351, 1);
player.getInventory().addItem(23279, 1);
player.getInventory().addItem(15272, 1);
player.getInventory().addItem(23399, 1);
player.getInventory().addItem(23351, 1);
player.getInventory().addItem(23255, 1);
player.getInventory().addItem(15272, 1);
player.getInventory().addItem(23399, 1);
player.getInventory().addItem(23351, 1);
player.getInventory().addItem(565 , 100000);
player.getInventory().addItem(560 , 100000);
player.getInventory().addItem(555 , 100000);
player.getInventory().addItem(12437, 1000);
player.getCombatDefinitions().setSpellBook(1);
for (int i = 0; i < 15; i++)
player.getEquipment().refresh(i);
		player.getEquipment().refresh();
player.getAppearence().generateAppearenceData();
		return;
	}
	public static void Mage(Player player) {
		player.getEquipment().getItems()
		.set(Equipment.SLOT_CAPE, new Item(20771));
player.getEquipment().getItems()
		.set(Equipment.SLOT_HAT, new Item(20159));
player.getEquipment().getItems()
		.set(Equipment.SLOT_CHEST, new Item(20163));
//player.getEquipment().getItems()
		//.set(Equipment.SLOT_SHIELD, new Item(17880));
player.getEquipment().getItems()
		.set(Equipment.SLOT_LEGS, new Item(20167));
player.getEquipment().getItems()
		.set(Equipment.SLOT_FEET, new Item(25066));
player.getEquipment().getItems()
		.set(Equipment.SLOT_HANDS, new Item(25062));
player.getEquipment().getItems()
		.set(Equipment.SLOT_WEAPON, new Item(17879));
player.getEquipment().getItems()
		.set(Equipment.SLOT_AMULET, new Item(18335));
player.getEquipment().getItems()
		.set(Equipment.SLOT_RING, new Item(15017));
//player.getInventory().addItem(17913, 1);
for (int i = 0; i < 15; i++)
player.getEquipment().refresh(i);
		player.getEquipment().refresh();
player.getAppearence().generateAppearenceData();
		return;
	}
	public static void Range(Player player) {
		player.getEquipment().getItems()
		.set(Equipment.SLOT_CAPE, new Item(20771));
player.getEquipment().getItems()
		.set(Equipment.SLOT_HAT, new Item(20147));
player.getEquipment().getItems()
		.set(Equipment.SLOT_CHEST, new Item(20151));
//player.getEquipment().getItems()
		//.set(Equipment.SLOT_SHIELD, new Item(18637));
player.getEquipment().getItems()
		.set(Equipment.SLOT_LEGS, new Item(20155));
player.getEquipment().getItems()
		.set(Equipment.SLOT_FEET, new Item(25068));
player.getEquipment().getItems()
		.set(Equipment.SLOT_HANDS, new Item(25058));
player.getEquipment().getItems()
		.set(Equipment.SLOT_WEAPON, new Item(18636));
player.getEquipment().getItems()
		.set(Equipment.SLOT_AMULET, new Item(25034));
player.getEquipment().getItems()
		.set(Equipment.SLOT_RING, new Item(15017));
player.getEquipment().getItems()
.set(Equipment.SLOT_ARROWS, new Item(9245, 1000000));
//player.getInventory().addItem(17912, 1);
for (int i = 0; i < 15; i++)
player.getEquipment().refresh(i);
		player.getEquipment().refresh();
player.getAppearence().generateAppearenceData();
		return;
	}
	public static void Melee(Player player) {
		player.getEquipment().getItems()
		.set(Equipment.SLOT_CAPE, new Item(20771));
player.getEquipment().getItems()
		.set(Equipment.SLOT_HAT, new Item(20137));
player.getEquipment().getItems()
		.set(Equipment.SLOT_CHEST, new Item(20139));
player.getEquipment().getItems()
		.set(Equipment.SLOT_SHIELD, new Item(26591));
player.getEquipment().getItems()
		.set(Equipment.SLOT_LEGS, new Item(20143));
player.getEquipment().getItems()
		.set(Equipment.SLOT_FEET, new Item(25064));
player.getEquipment().getItems()
		.set(Equipment.SLOT_HANDS, new Item(22358));
player.getEquipment().getItems()
		.set(Equipment.SLOT_WEAPON, new Item(26587));
player.getEquipment().getItems()
		.set(Equipment.SLOT_AMULET, new Item(25028));
player.getEquipment().getItems()
		.set(Equipment.SLOT_RING, new Item(15017));
//player.getInventory().addItem(17911, 1);
for (int i = 0; i < 15; i++)
player.getEquipment().refresh(i);
		player.getEquipment().refresh();
player.getAppearence().generateAppearenceData();
		return;
	
	}
	public static void Drygores(Player player) {
		player.getInventory().addItem(26587, 1); // longs
		player.getInventory().addItem(26591, 1); // longs  - offhand
		player.getInventory().addItem(26579, 1); // rapier
		player.getInventory().addItem(26583, 1); // rapier - offhand
		player.getInventory().addItem(26595, 1); // mace
		player.getInventory().addItem(26599, 1); // mace - offhand
		return;
	}
	public static void Pets1(Player player) {
		player.getInventory().addItem(28630, 1); 
		player.getInventory().addItem(33808, 1); 
		player.getInventory().addItem(33815, 1); 
		player.getInventory().addItem(33812, 1); 
		player.getInventory().addItem(33816, 1); 
		player.getInventory().addItem(33817, 1);
		player.getInventory().addItem(33818, 1); 
		player.getInventory().addItem(31457, 1); 
		player.getInventory().addItem(33827, 1); 
		player.getInventory().addItem(33826, 1); 
		player.getInventory().addItem(33828, 1); 
		player.getInventory().addItem(33829, 1); 
		player.getInventory().addItem(31461, 1); 
		player.getInventory().addItem(33813, 1); 
		player.getInventory().addItem(33806, 1); 
		player.getInventory().addItem(33804, 1); 
		player.getInventory().addItem(33807, 1); 
		player.getInventory().addItem(33805, 1); 
		player.getInventory().addItem(31459, 1); 
		player.getInventory().addItem(33814, 1); 
		player.getInventory().addItem(33809, 1); 
		player.getInventory().addItem(33811, 1); 
		player.getInventory().addItem(33810, 1); 
		player.getInventory().addItem(33819, 1); 
		player.getInventory().addItem(33820, 1); 
		player.getInventory().addItem(33821, 1); 
		player.getInventory().addItem(33822, 1); 
		player.getInventory().addItem(33823, 1); 
		return;
	}
	public static void Pets2(Player player) {
		player.getInventory().addItem(33824, 1);
		player.getInventory().addItem(33825, 1);
		player.getInventory().addItem(30031, 1);
		player.getInventory().addItem(30032, 1);
		player.getInventory().addItem(30033, 1);
		player.getInventory().addItem(30034, 1);
		player.getInventory().addItem(30035, 1);
		player.getInventory().addItem(30036, 1);
		player.getInventory().addItem(33717, 1);
		player.getInventory().addItem(27493, 1);
		player.getInventory().addItem(24512, 1);
		player.getInventory().addItem(32730, 1);
		player.getInventory().addItem(30749, 1);
		player.getInventory().addItem(31025, 1);
		player.getInventory().addItem(31748, 1);
		player.getInventory().addItem(31749, 1);
		player.getInventory().addItem(31750, 1);
		player.getInventory().addItem(31751, 1);
		player.getInventory().addItem(31752, 1);
		player.getInventory().addItem(31753, 1);
		player.getInventory().addItem(22973, 1);
		player.getInventory().addItem(30380, 1);
		player.getInventory().addItem(14652, 1);
		player.getInventory().addItem(14653, 1);
		player.getInventory().addItem(14654, 1);
		player.getInventory().addItem(14655, 1);
		player.getInventory().addItem(14651, 1);
		player.getInventory().addItem(24511, 1);
		return;
	}
	public static void Pets3(Player player) {
		player.getInventory().addItem(19894, 1);
		player.getInventory().addItem(14627, 1);
		player.getInventory().addItem(14626, 1);
		player.getInventory().addItem(21512, 1);
		player.getInventory().addItem(24511, 1);
		player.getInventory().addItem(28017, 1);
		player.getInventory().addItem(28018, 1);
		player.getInventory().addItem(28019, 1);
	}
	public static void MasterCapes(Player player) {
		player.getInventory().addItem(19709, 1);
		player.getInventory().addItem(31268, 1);
		player.getInventory().addItem(31269, 1);
		player.getInventory().addItem(31270, 1);
		player.getInventory().addItem(31271, 1);
		player.getInventory().addItem(31272, 1);
		player.getInventory().addItem(31273, 1);
		player.getInventory().addItem(31274, 1);
		player.getInventory().addItem(31275, 1);
		player.getInventory().addItem(31276, 1);
		player.getInventory().addItem(31277, 1);
		player.getInventory().addItem(31278, 1);
		player.getInventory().addItem(31279, 1);
		player.getInventory().addItem(31280, 1);
		player.getInventory().addItem(31281, 1);
		player.getInventory().addItem(31282, 1);
		player.getInventory().addItem(31283, 1);
		player.getInventory().addItem(31284, 1);
		player.getInventory().addItem(31285, 1);
		player.getInventory().addItem(31286, 1);
		player.getInventory().addItem(31287, 1);
		player.getInventory().addItem(31288, 1);
		player.getInventory().addItem(31289, 1);
		player.getInventory().addItem(31290, 1);
		player.getInventory().addItem(31291, 1);
		player.getInventory().addItem(31292, 1);
	}
	public static void DyedDrygores(Player player) {
		//barrows
		player.getInventory().addItem(33312, 1);
		player.getInventory().addItem(33315, 1);
		player.getInventory().addItem(33306, 1);
		player.getInventory().addItem(33309, 1);
		player.getInventory().addItem(33300, 1);
		player.getInventory().addItem(33303, 1);
		//shadow
		player.getInventory().addItem(33378, 1);
		player.getInventory().addItem(33381, 1);
		player.getInventory().addItem(33372, 1);
		player.getInventory().addItem(33375, 1);
		player.getInventory().addItem(33366, 1);
		player.getInventory().addItem(33369, 1);
		//3a
		player.getInventory().addItem(33444, 1);
		player.getInventory().addItem(33447, 1);
		player.getInventory().addItem(33438, 1);
		player.getInventory().addItem(33441, 1);
		player.getInventory().addItem(33432, 1);
		player.getInventory().addItem(33435, 1);
		return;
	}
	public static void Noxious(Player player) {
		player.getInventory().addItem(31725, 1);//scythe staff bow
		player.getInventory().addItem(31729, 1);
		player.getInventory().addItem(31733, 1);
		player.getInventory().addItem(33330, 1);//barrows
		player.getInventory().addItem(33333, 1);
		player.getInventory().addItem(33336, 1);
		player.getInventory().addItem(33396, 1);//shaddow
		player.getInventory().addItem(33399, 1);
		player.getInventory().addItem(33402, 1);
		player.getInventory().addItem(33462, 1);//3a
		player.getInventory().addItem(33465, 1);
		player.getInventory().addItem(33468, 1);
		return;
	}
	public static void Seismic(Player player) {
		player.getInventory().addItem(28617, 1);
		player.getInventory().addItem(28621, 1);
		player.getInventory().addItem(33324, 1);
		player.getInventory().addItem(33327, 1);
		player.getInventory().addItem(33390, 1);
		player.getInventory().addItem(33393, 1);
		player.getInventory().addItem(33456, 1);
		player.getInventory().addItem(33459, 1);
		return;
		
	}
	public static void Ascension(Player player) {
		player.getInventory().addItem(28437, 1);
		player.getInventory().addItem(28441, 1);
		player.getInventory().addItem(33318, 1);
		player.getInventory().addItem(33321, 1);
		player.getInventory().addItem(33384, 1);
		player.getInventory().addItem(33387, 1);
		player.getInventory().addItem(33450, 1);
		player.getInventory().addItem(33453, 1);
		return;
	}
	public static void Sirenic(Player player) {
		player.getInventory().addItem(29854, 1);
		player.getInventory().addItem(29857, 1);
		player.getInventory().addItem(29860, 1);
		player.getInventory().addItem(33348, 1);
		player.getInventory().addItem(33351, 1);
		player.getInventory().addItem(33354, 1);
		player.getInventory().addItem(33414, 1);
		player.getInventory().addItem(33417, 1);
		player.getInventory().addItem(33420, 1);
		player.getInventory().addItem(33480, 1);
		player.getInventory().addItem(33483, 1);
		player.getInventory().addItem(33486, 1);
		return;
	}
	public static void Malevolent(Player player) {
		player.getInventory().addItem(30005, 1);
		player.getInventory().addItem(30008, 1);
		player.getInventory().addItem(30011, 1);
		player.getInventory().addItem(33357, 1);
		player.getInventory().addItem(33360, 1);
		player.getInventory().addItem(33363, 1);
		player.getInventory().addItem(33423, 1);
		player.getInventory().addItem(33426, 1);
		player.getInventory().addItem(33429, 1);
		player.getInventory().addItem(33489, 1);
		player.getInventory().addItem(33492, 1);
		player.getInventory().addItem(33495, 1);
		return;
	}
	public static void Tectonic(Player player) {
		player.getInventory().addItem(28608, 1);
		player.getInventory().addItem(28611, 1);
		player.getInventory().addItem(28614, 1);
		player.getInventory().addItem(33339, 1);
		player.getInventory().addItem(33342, 1);
		player.getInventory().addItem(33345, 1);
		player.getInventory().addItem(33405, 1);
		player.getInventory().addItem(33408, 1);
		player.getInventory().addItem(33411, 1);
		player.getInventory().addItem(33471, 1);
		player.getInventory().addItem(33474, 1);
		player.getInventory().addItem(33477, 1);
		return;
	}
	
}
