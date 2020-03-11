package gameEngine.gameModels;

import gameEngine.PerlinNoise;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Terrain {
  PerlinNoise p;
  int biomeID;

public Terrain(PerlinNoise p)
{
  this.p =p;
}

        public ArrayList<Vector3f> getTerr(int xCord, int yCord)
        {
           ArrayList<Vector3f> terr = new ArrayList();
           float[][] map = new float[16][16];
            for (int y = 0; y < map.length; y++){
                for (int x = 0; x < map[0].length; x++){
                    float nx = (float)(x+16*yCord)/50 - (float).5;
                    float ny = (float)(y+16*xCord)/50 - (float).5;
                    map[y][x] = (int)(1000*p.noise(nx, ny));
                }
            }
            for(int i = 0; i < map.length; i++)
            {
                for(int j = 0; j < map[0].length; j++)
                {
                    Vector3f pV = new Vector3f(i+16*xCord, map[i][j]-10, j+16*yCord);
                    terr.add(pV);
                }
            }
            terr.add(new Vector3f(xCord, yCord, 0));
            return terr;
        }

        public ArrayList<Vector3f> getTrees(int xCord, int yCord) {

            ArrayList<Vector3f> trees = new ArrayList();
            float[][] map = new float[16][16];
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    float nx = (float) (x + 16 * yCord) / 100 - (float) .5;
                    float ny = (float) (y + 16 * xCord) / 100 - (float) .5;
                    map[y][x] = (int) (1000 * p.noise(nx, ny));
                }
            }
            return trees;
        }
        public int getBiome(int xCord, int yCord)
        {
          return 0;
        }

}


