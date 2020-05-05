package gameEngine.gameModels;

public class ChunkBlockType {
    public static int[] getBlockIds(int id){
        int[] ids = new int[6];
        if(id == 1 || id == 2){
            ids[0] = 1;
            ids[1] = 2;
            ids[2] = 3;
            ids[3] = 4;
            ids[4] = 5;
            ids[5] = 6;
        }
        if(id == 0){
            ids[0] = 8;
            ids[1] = 8;
            ids[2] = 9;
            ids[3] = 0;
            ids[4] = 5;
            ids[5] = 6;
        }
        return ids;
    }
}
