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
    float nx = (float)(x*x)/25 - (float).5;
    float ny = (float)(y*y)/25 - (float).5;

    float ID = (float)(20*(p.noise(nx,ny)+.025));

    if(ID < .33)
    {
      return 0;
    }
    else if(ID < .66)
    {
      return 1;
    }
    else {
      return 2;
    }
  }
}