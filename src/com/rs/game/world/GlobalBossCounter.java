package com.rs.game.world;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.rs.Settings;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;



public class GlobalBossCounter {


	static HashMap<Integer, Integer> bossCount = new HashMap<Integer, Integer>();
	
	public static void Init(){
		
	      try
	      {
	         FileInputStream fis = new FileInputStream("bossCount.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         bossCount = (HashMap) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	    	  System.out.println("Class not found");
	          c.printStackTrace();
	          return;
	       }
	}
	
	public static void Save(){
		try
         {
                FileOutputStream fos = new FileOutputStream("bossCount.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(bossCount);
                oos.close();
                fos.close();
                System.out.printf("BossCount has been saved.");
         }catch(IOException ioe)
          {
                ioe.printStackTrace();
          }
	}
	
	public static void handleBossCount(NPC npc,Player player){
		for (int bossId : Settings.BOSS_IDS) {
			if (npc.getId() == bossId ){
				int i  = 1;
				if (bossCount.containsKey(npc.getId())) {
					i = bossCount.get(npc.getId()) + 1;
				}
			bossCount.put(npc.getId(), i);
					}
				}
	}
}
