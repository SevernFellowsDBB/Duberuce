package gameEngine.gameModels;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Objects;

public class Chunk {
   
    private ArrayList<Block> renderedBlocks = new ArrayList<Block>();
    private boolean edited;
    private int x;
    private int z;
    private final int h = 64;
    private final int l = 16;
    private int[] ID;
    private Block[][][] blocks = new Block[l][h][l];
    int[] treeX;
    int[] treeY;
    int[] treeZ;

    public Chunk(int x, int z){
        this.x = x;
        this.z = z;
    }

    public Chunk(ArrayList<Vector3f> heights, int biomeID) {
        x = (int) heights.get(heights.size()-1).x;

        z= (int) heights.get(heights.size()-1).y;
        ID = ChunkBlockType.getBlockIds(biomeID);
        for(int i=0;i<blocks.length;i++){
            for(int j = 0;j<blocks[0].length;j++){
                for(int k = 0;k<blocks[0][0].length;k++){
                    Vector3f temp = new Vector3f(i+x*16,j-h/2,k+z*16);
                    if(j<h/4) {
                        blocks[i][j][k] = new Block(ID[4], temp);
                    }
                    else {
                        blocks[i][j][k] = new Block(0, temp);
                    }
                }
            }
        }
      for(int i = 0; i < heights.size()-1; i++)
      {
         Vector3f temp = new Vector3f(heights.get(i).x,heights.get(i).y,heights.get(i).z);
         if(temp.y>=h/2){
             temp.y=h/2-6;
         }
         else if(temp.y <= -h/2){
             temp.y = -h/2+2;
         }
         int numDirt = 3;
         if(blocks[(int) temp.x - x * 16][(int) temp.y + h / 2+1][(int) temp.z - z * 16].getID()==0) {
             blocks[(int) temp.x - x * 16][(int) temp.y + h / 2][(int) temp.z - z * 16] = new Block(ID[0], temp);
         }
         else{
             blocks[(int) temp.x - x * 16][(int) temp.y + h / 2][(int) temp.z - z * 16] = new Block(ID[1], temp);
         }

          for(int j = (int) temp.y+h/2-1; j >= 0; j--)
          {
              if(numDirt>0) {
                  Vector3f temp2 = new Vector3f(heights.get(i).x, j - h / 2, heights.get(i).z);

                      blocks[(int) temp2.x - x * 16][(int) temp2.y + h / 2][(int) temp2.z - z * 16] = new Block(ID[1], temp2);

                  numDirt--;
              }
              else{
                  Vector3f temp2 = new Vector3f(heights.get(i).x, j - h / 2, heights.get(i).z);

                      blocks[(int) temp2.x - x * 16][(int) temp2.y + h / 2][(int) temp2.z - z * 16] = new Block(ID[5], temp2);

              }
          }
      }
      for(int i = 0;i<blocks.length;i++){
          for(int j = 0;j<blocks[0].length;j++){
              for(int k = 0;k<blocks[0][0].length;k++){
                  if(i == 0 || i == blocks.length-1 || k == 0 || k == blocks[0][0].length-1 || j == 0 || j == blocks[0].length-1){
                      if(blocks[i][j][k].getID() != 0) {
                          blocks[i][j][k].setRendered(true);
                          renderedBlocks.add(blocks[i][j][k]);
                      }
                  }
                  else{
                      if(blocks[i-1][j][k].getID() == 0 || blocks[i+1][j][k].getID() == 0 || blocks[i][j+1][k].getID() == 0 || blocks[i][j-1][k].getID() == 0 || blocks[i][j][k-1].getID() == 0 || blocks[i][j][k+1].getID() == 0 || blocks[i-1][j][k].getID() == 5 || blocks[i+1][j][k].getID() == 5 || blocks[i][j+1][k].getID() == 5 || blocks[i][j-1][k].getID() == 5 || blocks[i][j][k-1].getID() == 5 || blocks[i][j][k+1].getID() == 5){
                          if(blocks[i][j][k].getID() != 0) {
                              blocks[i][j][k].setRendered(true);
                              renderedBlocks.add(blocks[i][j][k]);
                          }
                      }

                  }
              }
          }
      }
      int numTrees = (int)(Math.random()*4+1);
      //numTrees = 0;
      treeX = new int[numTrees];
      treeY = new int[numTrees];
      treeZ = new int[numTrees];
      for(int i=0;i<numTrees;i++) {
          if (i == 0) {
              int tX = (int) (Math.random() * 14)+1;
              treeX[i] = tX;
              int tZ = (int) (Math.random() * 14)+1;
              treeZ[i] = tZ;
          } else {
              int tX = (int) (Math.random() * 14)+1;
              treeX[i] = tX;
              int tZ = (int) (Math.random() * 14)+1;
              treeZ[i] = tZ;
              for (int j = i - 1; j >= 0; j--) {
                  if (treeX[i] == treeX[j] && treeZ[i] == treeZ[j]) {
                      treeX[i] = (int) (Math.random() * 14)+1;
                      treeZ[i] = (int) (Math.random() * 14)+1;
                      j = i - 1;
                  }
              }
          }
          int quit = 0;
          int tY = h-8;
          while ((blocks[treeX[i]][tY][treeZ[i]].getID() == 0 || blocks[treeX[i]][tY][treeZ[i]].getID() == 7 || blocks[treeX[i]][tY][treeZ[i]].getID() == 7) && quit < 100) {
              tY--;
              if(tY < h/4) {
                  quit++;
                  int tX = (int) (Math.random() * 14)+1;
                  treeX[i] = tX;
                  int tZ = (int) (Math.random() * 14)+1;
                  treeZ[i] = tZ;
                  for (int j = i - 1; j >= 0; j--) {
                      if (treeX[i] == treeX[j] && treeZ[i] == treeZ[j]) {
                          treeX[i] = (int) (Math.random() * 14)+1;
                          treeZ[i] = (int) (Math.random() * 14)+1;
                          j = i - 1;
                      }
                  }
                  tY = h-8;
              }
              treeY[i] = tY;

          }
          if(quit < 100) {
              Tree t = new Tree(treeX[i] + x * 16, treeY[i] - h / 2, treeZ[i] + z * 16,ID[2],ID[3]);
              ArrayList<Block> treeList = t.getTree();
          for(int j = 0;j<treeList.size();j++) {
              Block temp = treeList.get(j);
              blocks[(int)temp.getPos().x-x*16][(int)temp.getPos().y+h/2][(int)temp.getPos().z-z*16] = temp;
          }
            for(Block b:treeList){
                b.setRendered(true);
            }
              renderedBlocks.addAll(t.getTree());
          }

      }

    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public int getX()
    {
      return x;
    }
    public int getZ()
    {
      return z;
    }
    public Block[][][] getBlocks()
    {
      return blocks;
    }

    public ArrayList<Block> getChunk(){
        return renderedBlocks;
    }

    public Block getBlock(float cX,float cY,float cZ){
        return blocks[(int)cX-x*l][(int)cY+32][(int)cZ-z*l];
    }

    public ArrayList<Block> update(Block selectedBlock){
        ArrayList<Block> newBlocks = new ArrayList<Block>();
        selectedBlock.setId(0);

        int bX = ((int) selectedBlock.getPos().x - x * l);
        int bY = (int) selectedBlock.getPos().y + h / 2;
        int bZ = ((int) selectedBlock.getPos().z - z * l);


        if(bX>0 && blocks[bX-1][bY][bZ].getID()!= 0 && !blocks[bX-1][bY][bZ].isRendered()){
            newBlocks.add(blocks[bX-1][bY][bZ]);
            blocks[bX-1][bY][bZ].setRendered(true);
        }
        if(bX<blocks.length-1 && blocks[bX+1][bY][bZ].getID()!= 0 && !blocks[bX+1][bY][bZ].isRendered()){
            newBlocks.add(blocks[bX+1][bY][bZ]);
            blocks[bX+1][bY][bZ].setRendered(true);
        }
        if(bY>0 && blocks[bX][bY-1][bZ].getID()!= 0 && !blocks[bX][bY-1][bZ].isRendered()){
            newBlocks.add(blocks[bX][bY-1][bZ]);
            blocks[bX][bY-1][bZ].setRendered(true);
        }
        if(bY<blocks[0].length-1 && blocks[bX][bY+1][bZ].getID()!= 0 && !blocks[bX][bY+1][bZ].isRendered()){
            newBlocks.add(blocks[bX][bY+1][bZ]);
            blocks[bX][bY+1][bZ].setRendered(true);
        }
        if(bZ>0 && blocks[bX][bY][bZ-1].getID()!= 0 && !blocks[bX][bY][bZ-1].isRendered()){
            newBlocks.add(blocks[bX][bY][bZ-1]);
            blocks[bX][bY][bZ-1].setRendered(true);
        }
        if(bZ<blocks[0][0].length-1 && blocks[bX][bY][bZ+1].getID()!= 0 && !blocks[bX][bY][bZ+1].isRendered()){
            newBlocks.add(blocks[bX][bY][bZ+1]);
            blocks[bX][bY][bZ+1].setRendered(true);
        }
        for(int i=0;i<renderedBlocks.size();i++) {
            if(renderedBlocks.get(i).getSelected()){
                renderedBlocks.get(i).setSelected(false);
                renderedBlocks.remove(i);
            }
        }
        renderedBlocks.addAll(newBlocks);
        return newBlocks;
    }

    public Block addBlock(int index,Block b){
        int bX = (int) (b.getPos().x - x * l);
        int bY = (int) b.getPos().y + h / 2;
        int bZ = (int) (b.getPos().z - z * l);
        if(index == 0){
            Block add = blocks[bX-1][bY][bZ];
            add.setId(2);
            add.setRendered(true);
            renderedBlocks.add(add);
            return add;
        }
        if(index == 1){
            Block add = blocks[bX+1][bY][bZ];
            add.setId(2);
            add.setRendered(true);
            renderedBlocks.add(add);
            return add;
        }
        if(index == 2){
            Block add = blocks[bX][bY][bZ-1];
            add.setId(2);
            add.setRendered(true);
            renderedBlocks.add(add);
            return add;
        }
        if(index == 3){
            Block add = blocks[bX][bY][bZ+1];
            add.setId(2);
            add.setRendered(true);
            renderedBlocks.add(add);
            return add;
        }
        if(index == 4){
            Block add = blocks[bX][bY-1][bZ];
            add.setId(2);
            add.setRendered(true);
            renderedBlocks.add(add);
            return add;
        }
        if(index == 5){
            Block add = blocks[bX][bY+1][bZ];
            add.setId(2);
            add.setRendered(true);
            renderedBlocks.add(add);
            return add;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chunk)) return false;
        Chunk chunk = (Chunk) o;
        return x == chunk.x &&
                z == chunk.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

    @Override
    public String toString() {
        return x + " " + z;
    }
}
