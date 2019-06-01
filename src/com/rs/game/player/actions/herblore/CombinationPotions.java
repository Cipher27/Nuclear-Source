package com.rs.game.player.actions.herblore;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;

/**
 * Handles making of combination potions
 * @author Paolo.
 */
public class CombinationPotions extends Action { 
	
	public static int CRYSTAL_FLASK = 32843;
	public static int OVERLOAD = 15332;
	
	public enum Combinations {
		
		EXTREME_BRAWELERS(91, 367.5, new Item[] { new Item(15308, 1), new Item(15312, 1),new Item(15316, 1), new Item(CRYSTAL_FLASK, 1) }, new Item(33066)),
		BRIGHTFIRE(94, 300, new Item[] { new Item(15304, 1), new Item(21630, 1), new Item(CRYSTAL_FLASK, 1) }, new Item(33174)),
		SEARING_OVERLOAD(97, 350, new Item[] { new Item(OVERLOAD, 1), new Item(15304, 1), new Item(CRYSTAL_FLASK, 1) }, new Item(33258)),
		HOLY_OVERLOAD(97, 350, new Item[] { new Item(OVERLOAD, 1), new Item(21630, 1), new Item(CRYSTAL_FLASK, 1) }, new Item(33246)),
		OVERLOAD_SALVE(97, 500, new Item[] { new Item(OVERLOAD, 1), new Item(21630, 1), new Item(15304, 1),new Item(2434, 1),new Item(2452, 1),new Item(2448, 1), new Item(CRYSTAL_FLASK, 1) }, new Item(33198));
	
		private int levelRequired;
		private double experience;
		private Item[] itemsRequired;
		private Item product;
	
		private Combinations(int levelRequired, double experience, Item[] itemsRequired, Item energyProduce) {
		    this.levelRequired = levelRequired;
		    this.experience = experience;
		    this.itemsRequired = itemsRequired;
		    this.product = energyProduce;
		}
	
		public Item[] getItemsRequired() {
		    return itemsRequired;
		}
	
		public int getLevelRequired() {
		    return levelRequired;
		}
	
		public Item getProduct() {
		    return product;
		}
	
		public double getExperience() {
		    return experience;
		}
    }

    public Combinations mix;
    public int ticks;

    public CombinationPotions(Combinations mix, int ticks) {
		this.mix = mix;
		this.ticks = ticks;
    }

    @Override
    public boolean start(Player player) {
    	if (!process(player))
			return false;
		return true;
    }

    @Override
    public boolean process(Player player) {
		if (mix == null || player == null)
		    return false;
		if (ticks <= 0)
			return false;
		if (player.getSkills().getLevel(Skills.HERBLORE) < mix.getLevelRequired()) {
			player.sendMessage("You need a Herblore level of at least " + mix.getLevelRequired() + " to create a " + mix.getProduct().getDefinitions().getName());
		    return false;
		}
		int length = mix.getItemsRequired().length;
		StringBuilder reqs = new StringBuilder("");
		for(int i = 0; i < length; i ++){
			String name = mix.getItemsRequired()[i].getName();
			if(name.startsWith("E") ||name.startsWith("A") ||name.startsWith("O"))
				reqs.append("an "+mix.getItemsRequired()[i].getName()+", ");
			else 
				reqs.append("a "+mix.getItemsRequired()[i].getName()+", ");
		}
    	int amount = mix.getItemsRequired()[0].getAmount();
		for(int i = 0; i < length; i ++){
		if (!player.getInventory().containsItem(mix.getItemsRequired()[i].getId(), amount)) {
			player.sendMessage("You need " + reqs + " to create "+(mix == Combinations.OVERLOAD_SALVE ? "an " : "a ")+"" + mix.getProduct().getDefinitions().getName() + ".");
		    return false;
			}
		}
		return true;
    }

    @Override
    public int processWithDelay(Player player) {
		ticks--;
		double xp = mix.getExperience();
		for (Item required : mix.getItemsRequired())
		    player.getInventory().deleteItem(required.getId(), required.getAmount());
		int amount = mix.getProduct().getAmount();
		player.getInventory().addItem(mix.getProduct().getId(), amount);
		player.getSkills().addXp(Skills.HERBLORE, xp);
		player.sendMessage("You created a " + mix.getProduct().getDefinitions().getName().toLowerCase());
		player.setNextAnimation(new Animation(363));
		if (ticks > 0)
		    return 1;
		return -1;
    }

    @Override
    public void stop(Player player) {
    	setActionDelay(player, 3);
    }
    
    public static final Combinations[] POTS = new Combinations[] {
    		Combinations.EXTREME_BRAWELERS,
    		Combinations.BRIGHTFIRE,
    		Combinations.SEARING_OVERLOAD,
    		Combinations.HOLY_OVERLOAD,
    		Combinations.OVERLOAD_SALVE
    		};
}