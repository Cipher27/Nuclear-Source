package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

public class PyramidPlunder {
	
	//note: objects have same ids in every room, it's just diff position
	
	/*
 Emotes:
2246 - passing the spear trap
4340 - urn searching (4341, unsuccessful, 4342 successful)
4238 - golden chest searching
4344 / 4345 - sarcophagus searching
	 */
	
	public enum TombDoorConfigs {
    TOMB_DOOR_1(16540, 10),
    TOMB_DOOR_2(16539, 9),
    TOMB_DOOR_3(16542, 12),
    TOMB_DOOR_4(16541, 11);
 
    private int objectId;
    private int openId;
 
    private static Map<Integer, TombDoorConfigs> configsMap = new HashMap<Integer, TombDoorConfigs>();
 
    static {
        for(TombDoorConfigs urnConfigs : TombDoorConfigs.values())
            configsMap.put(urnConfigs.objectId, urnConfigs);
    }
 
    TombDoorConfigs(int objectId, int openId) {
        this.objectId = objectId;
        this.openId = openId;
    }
 
    public static TombDoorConfigs forId(int objectId) {
        return configsMap.get(objectId);
    }
 
    public int getConfig(){
        return -1; //Todo
    }
 
    public int getObjectId() {
        return objectId;
    }
 
    public int getOpenId() {
        return openId;
    }
 
}

	public enum UrnConfigs {
    URN_1(16529, 22, 23, 0),
    URN_2(16520, 4, 5, 0),
    URN_3(16525, 14, 15, 0),
    URN_4(16530, 24, 25, 0),
    URN_5(16526, 16, 17, 0),
    URN_6(16531, 26, 27, 0),
    URN_7(16522, 8, 9, 0),
    URN_8(16532, 28, 29, 0),
    URN_9(16527, 18, 19, 0),
    URN_10(16518, 0, 1, 0),
    URN_11(16523, 10, 11, 0),
    URN_12(16528, 20, 21, 0),
    URN_13(16519, 2, 3, 0),
    URN_14(16524, 12, 13, 0),
    URN_15(16521, 6, 7, 0);
 
    private int objectId;
    private int openId;
    private int snakeId;
    private int snakeCharmedId;
 
    private static Map<Integer, UrnConfigs> configsMap = new HashMap<Integer, UrnConfigs>();
 
    static {
        for(UrnConfigs urnConfigs : UrnConfigs.values())
            configsMap.put(urnConfigs.objectId, urnConfigs);
    }
 
    UrnConfigs(int objectId, int openId, int snakeId, int snakeCharmedId) {
        this.objectId = objectId;
        this.openId = openId;
        this.snakeId = snakeId;
        this.snakeCharmedId = snakeCharmedId;
    }
 
    public static UrnConfigs forId(int objectId) {
        return configsMap.get(objectId);
    }
 
    public int getConfig(){
               return -1; //todo
    }
 
    public int getObjectId() {
        return objectId;
    }
 
    public int getOpenId() {
        return openId;
    }
 
    public int getSnakeId() {
        return snakeId;
    }
 
    public int getSnakeCharmedId() {
        return snakeCharmedId;
    }
}
}
