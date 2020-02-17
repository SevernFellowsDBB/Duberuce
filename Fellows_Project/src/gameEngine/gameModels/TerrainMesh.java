package gameEngine.gameModels;
import gameEngine.PerlinNoise;
import java.util.ArrayList;

public class TerrainMesh {

float[][] terrNoise = new float[16][16];
int[][] terrMesh = new int[16][16];
PerlinNoise p;

  public TerrainMesh(PerlinNoise p)
  {
    this.p=p;
  }

  public void setMesh()
  {
    for(int i=0; i < terrNoise.length; i++)
    {
      for(int j=0; j< terrNoise[0].length; j++)
      {
        int r = (int)(Math.random()*10 +1);

        float nx = (float)(i*i)/25 - (float).5;
        float ny = (float)(j*j)/25 - (float).5;

        terrNoise[i][j]=(float)(20*(p.noise(nx,ny)+.025));
        System.out.println(terrNoise[i][j]);

        if(terrNoise[i][j] < .33)
        {
          terrMesh[i][j]=0;
        }
        else if(terrNoise[i][j] < .66)
        {
          terrMesh[i][j]=1;
        }
        else
        {
          terrMesh[i][j]=2;
        }
      }
    }
  }
  public int getBiome(int x, int y)
  {
    return terrMesh[x][y];
  }
}