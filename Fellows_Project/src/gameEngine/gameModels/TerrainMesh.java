package gameEngine.gameModels;
import gameEngine.PerlinNoise;
import java.util.ArrayList;

public class TerrainMesh {

//float[][] terrNoise = new float[16][16];
//
// +int[][] terrMesh = new int[16][16];
ArrayList<Integer> terrMesh = new ArrayList<Integer>();
ArrayList<Float> terrNoise = new ArrayList<Float>();
PerlinNoise p;
  public TerrainMesh(PerlinNoise p)
  {
    this.p=p;
  }

  public int getBiome(int x, int y)
  {

     float nx = (float)(x+16*y)/50 - (float).5;
     float ny = (float)(y+16*x)/50 - (float).5;
     float I = (float)(1000*p.noise(nx, ny));
     float ID;

     if(I>1 && I<-1)
     {
        ID=1/I;
     }
     else
     {
        ID=I;
     }

    if(ID > .66)
    {
      return 0;
    }
    else if(ID < .66 && ID > .33)
    {
      return 1;
    }
    else {
      return 2;
    }
  }
}