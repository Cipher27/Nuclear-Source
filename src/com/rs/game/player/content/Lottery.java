package com.rs.game.player.content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

/**
 * 
 */
public class Lottery {

    /**
     * All the players entered in the lottery. (I chose String over Player because of safety
     * reasons (storing the same Player twice, etc.))
     */
    public static List<String> playerList = new ArrayList<String>();

    /**
     * Adds the ticket to the inventory and announces world-wide to the server.
     *
     * @param player The (lucky) Player who got the ticket.
     */
 /*   public static void addTicket(Player player) {
        if (!Settings.LOTTERY_ENABLED)
            return;
        if (player.getLotteryTickets() == 3)
            return;
        if (!player.getInventory().addItem(new Item(29914))) {
            player.getBank().addItem(29914, 1, true);
            player.getPackets().sendGameMessage("<col=FF0000>We couldn't find enough space in your inventory, so we banked the ticket.");
        }
     //   player.getInventory().addItem(29914, 1);
    //    player.setLotteryTickets(player.getLotteryTickets() + 1);
    //    World.sendWorldMessage("<img=6><col=FF8C38>News: " + player.getDisplayName() + " has received a lottery ticket!", false);
    }

    /**
     * Makes the Player enter the lottery via ticket.
     *
     * @param player The Player who has the ticket and wishes to enter.
     */
    public static void enter(Player player) {
        if (!player.getInventory().containsItem(29914, 1)) {
            player.getPackets().sendGameMessage("You need a lottery ticket to enter!");
            return;
        }
        player.getInventory().deleteItem(29914, 1);
        World.addLotteryAmount(player.getDisplayName(), 1000000);
        playerList.add(player.getDisplayName());
        check();
        
    }

    /**
     * Checks the lottery to see if the lottery cap has been reached.
     * 
     *     public void drawLottery() {
        boolean prizeGiven = false;
        int arraySize = lotteryPlayerNames.size() -1;
        int winner = Misc.random(arraySize);
        try {
            String player = lotteryPlayerNames.get(winner);
            Client c = null;
            for(int i = 0; i < Server.playerHandler.players.length; i++) {
                if(Server.playerHandler.players[i] != null) {
                    if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(player)) {
                        c = (Client)Server.playerHandler.players[i];
                        c.sendMessage("You have won the lottery!");
                        prizeGiven = true;
                        if (c.getItems().freeSlots() > 0) {
                            c.getItems().addItem(995,prizeAmount * 1000000);
                        } else {
                            c.sendMessage("You do not have enough room in your inventory to claim your reward!");
                            c.sendMessage("We will try to add your reward again when you next login.");
                            unclaimedWinners.add(c.playerName);
                        }
                    }
                }
            }
     */
    private static void check() {
        if (World.getLotteryAmount() >= Settings.LOTTERY_MAX_AMOUNT) {
            int randomIndex = Utils.random(playerList.size() - 1);
            String name = playerList.get(randomIndex);
            Player player = World.getPlayerByDisplayName(name);
            if (player != null) {
                player.getPackets().sendGameMessage("<col=FF0000>Congratulations " + player.getDisplayName() + "! You are the winner of the Hellion lottery!");
                if (!player.getInventory().addItem(new Item(995, World.getLotteryAmount()))) {
                    player.getBank().addItem(995, World.getLotteryAmount(), true);
                    player.getPackets().sendGameMessage("<col=ff0000>We couldn't find space in your inventory, so we added the money to your bank.");
                }
                SerializableFilesManager.savePlayer(player);
            } else {
                File acc11 = new File("data/playersaves/characters/" + name.replace(" ", "_") + ".p");
                Player player2 = null;
                try {
                    player2 = (Player) SerializableFilesManager.loadSerializedFile(acc11);
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                // player.getPackets().sendGameMessage("<col=FF0000>Congratulations " + player.getDisplayName() + "! You are the winner of the lottery!");
                try {
                if (!player2.getInventory().addItem(new Item(995, World.getLotteryAmount()))) {
                    player2.getBank().addItem(995, World.getLotteryAmount(), true);
                    //player.getPackets().sendGameMessage("<col=ff0000>We couldn't find space in your inventory, so we added the money to your bank.");
                }
                } catch (Exception e) {
                    check();
                    return;
                }
                try {
                    SerializableFilesManager.storeSerializableClass(player2, acc11);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            World.sendWorldMessage("<col=FF0000>" + name + " has joined the Lottery and won the pot of " + NumberFormat.getNumberInstance(Locale.US).format(World.getLotteryAmount()) + " GP! Congratulations!", false);
            World.setLotteryAmount(0);
            playerList.clear();
       //     File[] chars = new File("data/playersaves/characters").listFiles();
       //     assert chars != null;
        //    for (File acc : chars) {
         //       try {
          //          Player character = (Player) SerializableFilesManager.loadSerializedFile(acc);
            //        character.setLotteryTickets(0);
            //    } catch (Throwable e) {
            //        e.printStackTrace();
            //        System.out.println("failed: " + acc.getName());
            //    }
      //      }
       //     for (Player players : World.getPlayers()) {
      //          players.setLotteryTickets(0);
      //      }
        }
    }

    /**
     * Saves the player list.
     */
    public static void save() {
        File output = new File("./data/lottery/data.txt");
        if (output.canWrite()) {
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(output, false));
                out.write("" + World.getLotteryAmount());
                out.newLine();
                for (String player : playerList) {
                    out.write(player);
                    out.newLine();
                }
            } catch (IOException ignored) {
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }
}