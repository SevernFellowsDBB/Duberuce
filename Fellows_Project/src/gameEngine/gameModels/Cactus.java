package gameEngine.gameModels;

import org.joml.Vector3f;

import java.util.ArrayList;

public class Cactus {
    int x, y, z;
    ArrayList<Block> cactus;
    public Cactus(int xPos, int yPos, int zPos)
    {
        x = xPos;
        y = yPos;
        z = zPos;
    }
    public void cactus1() {
        cactus = new ArrayList();
        Vector3f t1 = new Vector3f(x, y + 1, z);
        Vector3f t2 = new Vector3f(x, y + 2, z);
        Vector3f t3 = new Vector3f(x, y + 3, z);
        cactus.add(new Block(8, t1));
        cactus.add(new Block(8, t2));
        cactus.add(new Block(8, t3));
    }

    public ArrayList<Block> getCactus()
    {
        cactus1();
        return cactus;
    }
}

