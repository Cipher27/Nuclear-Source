package com.rs.game.player.content;
 
import java.util.Random;

import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Wilderness;
import com.rs.utils.Utils;
 
public class WildernessArtefacts {
   
   
    /* credits to
     * Tristam - Rain & Paolo - omglolomghi
     */
   
    public enum Artefacts {

    
    	
    	AncientStatuette(2000000, 14876, 320, 10,"Ancient Statuette"),
        SerenStatuette(10000000, 14877, 300, 8,"Seren Statuette"),
        ArmadylStatuette(4000000, 14878, 290, 5,"Armadyl Stauette"),
        ZamorakStatuette(4000000, 14879, 290, 5,"Zamorak Statuette"),
        SaradominStatuette(3000000, 14880, 295, 4,"Saradomin Statuette"),
        BandosStatuette(3000000, 14881, 270, 5,"Bandos Statuette"),
        RubyChalice(2500000, 14882, 260, 5,"Ruby Chalice"),
        GuthixianBrazier(2000000, 14883, 210, 4,"Guthixian Brazier"),
        ArmadylTotem(1500000, 14884, 200, 3,"Armadyl Totem"),
        ZamorakMedalion(1000000, 14885, 197, 3,"Zamorak Medalion"),
        SaradominCarving(750000, 14886, 177, 3,"Saradomin Carving"),
        BandosScrimshaw(500000, 14887, 155, 3,"Bandos Scrimshaw"),
        SaradominAmphora(400000, 14888, 136, 2,"Saradomin Amphora"),
        AncientPsaltaryBridge(300000, 14889, 122, 2,"Ancient Psaltary Bridge"),
        BronzedDragonClaw(200000, 14890, 111, 1,"Bronzed Dragon Claw"),
        ThirdAgeCarafe(150000, 14891, 109, 1,"Third-Age Carafe"),
        BrokenStatueHeaddress(100000, 14892, 105, 1,"Broken Statue Headdress");
    	
       
       
       
        private int price, id, chance, points;
        private String name;
       
        private Artefacts(int price, int id, int chance,int points, String name) {
            this.price = price;
            this.id = id;
            this.chance = chance;
            this.setPoints(points);
            this.name = name;
        }
    	
        public int getPrice() {
            return price;
        }
       
        public int getId() {
            return id;
        }
       
        public int getChance() {
            return chance;
        }
       
        public String getName() {
            return name;
        }

		public int getPoints() {
			return points;
		}

		public void setPoints(int points) {
			this.points = points;
		}
       
    }
   
       
    public static boolean useOnMandrith(Player player) {
       for (Artefacts artefacts : Artefacts.values()) {
            int amount = player.getInventory().getNumerOf(artefacts.getId());
            if (amount > 0 ) {
                String formattedNumber = Utils.getFormattedNumber(artefacts.getPrice() * amount)+ " Coins";
                player.getDialogueManager().startDialogue("SimpleMessage", "You sold "+amount+ " artefacts for a total of "
                        +formattedNumber); 
                player.getInventory().deleteItem(artefacts.getId(), amount);
                player.getInventory().addItem(995, artefacts.getPrice() * amount);
                player.getPointsManager().setWildernessTokens(player.getPointsManager().getWildernessTokens() + artefacts.points);
            } else if (amount > 0 && player.getMoneyPouch().getTotal() < Integer.MAX_VALUE) {
                String formattedNumber = Utils.getFormattedNumber(artefacts.getPrice() * amount)+ " Coins";
                player.getDialogueManager().startDialogue("SimpleMessage", "You sold "+amount+ " artefacts for a total of "
                        +formattedNumber); 
                player.getInventory().deleteItem(artefacts.getId(), amount);
                player.getPointsManager().setWildernessTokens(player.getPointsManager().getWildernessTokens() + artefacts.points);
                player.getInventory().addItem(995,artefacts.getPrice() * amount);
            }
        }
        return false;
    }
   
    public static boolean trade(Player player) {
       for (Artefacts artefacts : Artefacts.values()) {
            int amount = player.getInventory().getNumerOf(artefacts.getId());
            if (amount > 0 ) {
                String formattedNumber = Utils.getFormattedNumber(artefacts.getPrice() * amount)+ " Coins";
                player.getDialogueManager().startDialogue("SimpleMessage", "You sold " +amount+ " "+artefacts.getName()+ " for a total of "
                        +formattedNumber); 
                player.getInventory().deleteItem(artefacts.getId(), amount);
                player.getPointsManager().setWildernessTokens(player.getPointsManager().getWildernessTokens() + artefacts.points);
                player.getInventory().addItem(995, artefacts.getPrice() * amount);
                player.sm("You traded the "+artefacts.name+ " for "+artefacts.points+" points.");
            }  if (amount == 0) {
            player.getDialogueManager().startDialogue("SimpleMessage", "You have no artifacts in your inventory."); 
            }
        }
        return false;
    }
    
    private static Artefacts[] artefact =Artefacts.values();
    
    private static Random random = new Random();
	final static Artefacts random(){
      return artefact [random.nextInt(artefact.length)];
	}
    /**
     * handles rewards when killing a npc in the wildy
     * @param player
     * @param npc
     * @return
     */
    public static boolean handelDrop(Player player, NPC npc){
    	Artefacts art = random();
    	int combatLevel = npc.getCombatLevel();
    	if(!Wilderness.isAtWild(player.getTile()))
    		return false;
    	if(Utils.random(art.chance) <= (combatLevel /100)){
    		player.getInventory().addItem(art.id,1);
    		player.sm("You have received an "+art.name+", trade it with the wildy manager.");
    		return true;
    	}
    		
    	return false;
    }
    /**
     * handles rewards when killing a player
     * @param player
     * @return
     */
    public static boolean handelPlayerKill(Player player){
    	if(!Wilderness.isAtWild(player.getTile()))
    		return false;
    	if(Utils.random(10) == 0){
    		player.getInventory().addItem(random().id,1);
    		player.sm("You received an Artefact.");
    	}
    		
    	return true;
    }
    
    public static boolean handelShred(Player player){
    	if(Utils.random(500) == 0){
    		player.getInventory().addItem(random().id,1);
    		player.sm("You received an Artefact.");
    	}
    		
    	return true;
    }
 
}