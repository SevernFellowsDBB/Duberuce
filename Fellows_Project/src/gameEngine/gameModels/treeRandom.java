package gameEngine.gameModels;

public class treeRandom {
    static int number = (int) Math.random() * 20000;

    public int getTS(){
        return number;
    }
}
