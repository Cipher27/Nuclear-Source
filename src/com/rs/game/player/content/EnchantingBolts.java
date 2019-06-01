package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 */
public class EnchantingBolts {
    /**
     * Handles the button of the enchanting interfac.e
     *
     * @param player The Player enchanting.
     * @param button The component ID.
     * @param amount The amount to remove (based on packet ID. A "STACK" is "10").
     */
    public static void handleButton(Player player, int button, int amount) {
        Bolt bolt = Bolt.forId(button);
        if (bolt == null)
        	return;
        if (!player.getInventory().containsItem(bolt.getOriginal(), amount)) {
            player.getPackets().sendGameMessage("You need " + amount + " " + new Item(bolt.getOriginal()).getName() + " to enchant!");
            return;
        }
        for (Item rune : bolt.getRunes()) {//checks runes incorporating staff - doesnt delete
        	if (!Magic.checkSpellRequirements(player, bolt.getLevelRequired(), false, rune.getId(), rune.getAmount() * (amount / 10)))
        		return;
        }
        for (Item rune : bolt.getRunes()) {//if first check is successful, deletes runes, accounts for staff
        	Magic.checkSpellRequirements(player, bolt.getLevelRequired(), true, rune.getId(), rune.getAmount() * (amount / 10));
        }
        player.getInventory().deleteItem(bolt.getOriginal(), amount);
        player.setNextAnimation(new Animation(24471));
        player.getInventory().addItem(bolt.getProduct(), amount);
        player.getSkills().addXp(Skills.MAGIC, amount);
        player.getPackets().sendGameMessage("The magic of the runes coaxes out the true nature of the gem tips.");
    }

    /**
     * A singular bolt in the crossbow bolt menu.
     */
    public enum Bolt {
        OPAL(14, 4, new Item[]{new Item(564), new Item(556, 2)}, 879, 9236, 9),
        SAPPHIRE(29, 7, new Item[]{new Item(564), new Item(555), new Item(558)}, 9337, 9240, 17),
        JADE(18, 14, new Item[]{new Item(564), new Item(557, 2)}, 9335, 9237, 19),
        PEARL(22, 24, new Item[]{new Item(564), new Item(556, 2)}, 880, 9238, 29),
        EMERALD(32, 27, new Item[]{new Item(564), new Item(556, 3), new Item(561)}, 9338, 9241, 37),
        RED_TOPAZ(26, 29, new Item[]{new Item(564), new Item(554, 2)}, 9336, 9239, 33),
        RUBY(35, 49, new Item[]{new Item(564), new Item(554, 5), new Item(565)}, 9339, 9242, 59),
        DIAMOND(38, 57, new Item[]{new Item(564), new Item(557, 10), new Item(563, 2)}, 9340, 9243, 67),
        DRAGONSTONE(41, 68, new Item[]{new Item(564), new Item(557, 15), new Item(566)}, 9341, 9244, 78),
        ONYX(44, 87, new Item[]{new Item(564), new Item(554, 20), new Item(560)}, 9342, 9245, 97);
        private final int componentId;
        private final int levelRequired;
        private final Item[] runes;
        private final int original;
        private final int product;
        private final double xp;

        Bolt(int componentId, int levelRequired, Item[] runes, int original, int product, double xp) {
            this.componentId = componentId;
            this.levelRequired = levelRequired;
            this.runes = runes;
            this.original = original;
            this.product = product;
            this.xp = xp;
        }

        public static Bolt forId(int id) {
            for (Bolt bolt : Bolt.values()) {
                if (bolt.getComponentId() == id)
                    return bolt;
            }
            return null;
        }

        public int getComponentId() {
            return componentId;
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        public Item[] getRunes() {
            return runes;
        }

        public int getProduct() {
            return product;
        }

        public int getOriginal() {
            return original;
        }

        public double getXp() {
            return xp;
        }
    }
}
