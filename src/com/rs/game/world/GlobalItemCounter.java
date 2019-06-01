package com.rs.game.world;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.item.Item;



public class GlobalItemCounter {


	static HashMap<Integer, Integer> dropCount;
	
	public static HashMap<Integer, Integer> getdropCount() {
		return dropCount;
	}
	
	public static void Init(){
		if(dropCount == null){
			dropCount = new HashMap<Integer, Integer>();
			for(int items : Settings.RARE_DROP_IDS){
				dropCount.put(items, 0);
			}
		}
	      try
	      {
	         FileInputStream fis = new FileInputStream("dropCount.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         dropCount = (HashMap<Integer, Integer>) ois.readObject();
	         System.out.println("drop count succesfully loaded");
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
                FileOutputStream fos = new FileOutputStream("dropCount.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(dropCount);
                oos.close();
                fos.close();
                System.out.printf("BossCount has been saved.");
         }catch(IOException ioe)
          {
                ioe.printStackTrace();
          }
	}
	/**
	 * saves the drops to the hashmap
	 * @param item
	 */
	public static boolean handleDropCount(Item item){
		
		for (int bossId : Settings.RARE_DROP_IDS) {
			if (item.getId() == bossId ){
				int i  = 1;
				if (dropCount.containsKey(item.getId())) {
					i = dropCount.get(item.getId()) + 1;
				}
			dropCount.put(item.getId(), i);
			return true;
			//World.sendWorldMessage("Count:"+i, false);
			}
		}
		return false;
	}

	
}
