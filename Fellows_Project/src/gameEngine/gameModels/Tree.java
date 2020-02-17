package gameEngine.gameModels;

import org.joml.Vector3f;

import java.util.ArrayList;

public class Tree {
  int x, y, z;
  int trunkID, leafID;
  ArrayList<Block> tree;
  public Tree(int xPos, int yPos, int zPos, int treeID, int leafID)
  {
    x = xPos;
    y = yPos;
    z = zPos;
    this.trunkID = treeID;
    this.leafID = leafID;
  }
  public void tree1() {
    tree = new ArrayList();
    Vector3f t1 = new Vector3f(x, y + 1, z);
    Vector3f t2 = new Vector3f(x, y + 2, z);
    Vector3f t3 = new Vector3f(x, y + 3, z);
    Vector3f t4 = new Vector3f(x, y + 4, z);
    Vector3f t5 = new Vector3f(x, y + 5, z);
    Vector3f t6 = new Vector3f(x, y + 6, z);
    tree.add(new Block(trunkID, t1));
    tree.add(new Block(trunkID, t2));
    tree.add(new Block(trunkID, t3));
    tree.add(new Block(trunkID, t4));
    tree.add(new Block(trunkID, t5));
    tree.add(new Block(trunkID, t6));
    for (int i = y + 5; i < y + 7;i++) {
      Vector3f l1 = new Vector3f(x - 1, i, z);
      tree.add(new Block(leafID,l1));
      Vector3f l2 = new Vector3f(x - 1, i, z - 1);
      tree.add(new Block(leafID, l2));
      Vector3f l3 = new Vector3f(x - 1, i, z + 1);
      tree.add(new Block(leafID, l3));
      Vector3f l4 = new Vector3f(x, i, z + 1);
      tree.add(new Block(leafID, l4));
      Vector3f l5 = new Vector3f(x, i, z - 1);
      tree.add(new Block(leafID, l5));
      Vector3f l6 = new Vector3f(x + 1, i, z + 1);
      tree.add(new Block(leafID, l6));
      Vector3f l7 = new Vector3f(x + 1, i, z);
      tree.add(new Block(leafID, l7));
      Vector3f l8 = new Vector3f(x + 1, i, z - 1);
      tree.add(new Block(leafID, l8));
    }
    Vector3f top = new Vector3f(x, y + 7, z);
    tree.add(new Block(leafID, top));
  }

  public void tree2()
  {
    tree = new ArrayList();
    Vector3f t1 = new Vector3f(x, y + 1, z);
    Vector3f t2 = new Vector3f(x, y + 2, z);
    Vector3f t3 = new Vector3f(x, y + 3, z);
    Vector3f t4 = new Vector3f(x, y + 4, z);
    tree.add(new Block(trunkID, t1));
    tree.add(new Block(trunkID, t2));
    tree.add(new Block(trunkID, t3));
    tree.add(new Block(trunkID, t4));
    for (int i = y + 4; i < y + 5;i++) {
      Vector3f l1 = new Vector3f(x - 1, i, z);
      tree.add(new Block(leafID, l1));
      Vector3f l2 = new Vector3f(x - 1, i, z - 1);
      tree.add(new Block(leafID, l2));
      Vector3f l3 = new Vector3f(x - 1, i, z + 1);
      tree.add(new Block(leafID, l3));
      Vector3f l4 = new Vector3f(x, i, z + 1);
      tree.add(new Block(leafID, l4));
      Vector3f l5 = new Vector3f(x, i, z - 1);
      tree.add(new Block(leafID, l5));
      Vector3f l6 = new Vector3f(x + 1, i, z + 1);
      tree.add(new Block(leafID, l6));
      Vector3f l7 = new Vector3f(x + 1, i, z);
      tree.add(new Block(leafID, l7));
      Vector3f l8 = new Vector3f(x + 1, i, z - 1);
      tree.add(new Block(leafID, l8));
    }
    Vector3f top = new Vector3f(x, y + 5, z);
    tree.add(new Block(leafID, top));
  }

  public void tree3()
  {
    tree = new ArrayList();
    Vector3f t1 = new Vector3f(x, y+1, z);
    Vector3f t2 = new Vector3f(x, y+2, z);
    Vector3f t3 = new Vector3f(x, y+3, z);
    Vector3f t4 = new Vector3f(x, y+4, z);
    Vector3f t5 = new Vector3f(x, y+5, z);
    tree.add(new Block(trunkID, t1));
    tree.add(new Block(trunkID, t2));
    tree.add(new Block(trunkID, t3));
    tree.add(new Block(trunkID, t4));
    tree.add(new Block(trunkID, t5));
    Vector3f l1 = new Vector3f(x-1, y+1, z-1);
    Vector3f l2 = new Vector3f(x-1, y+2, z);
    Vector3f l3 = new Vector3f(x-1, y+3, z+1);
    Vector3f l4 = new Vector3f(x, y+4, z+1);
    Vector3f l5 = new Vector3f(x+1, y+5, z+1);
    tree.add(new Block(leafID, l1));
    tree.add(new Block(leafID, l2));
    tree.add(new Block(leafID, l3));
    tree.add(new Block(leafID, l4));
    tree.add(new Block(leafID, l5));

  }
  

  public void tree4(){
    tree = new ArrayList<>();
    Vector3f t1 = new Vector3f(x, y+1, z);
    tree.add(new Block(trunkID, t1));
    Vector3f l1 = new Vector3f(x, y+2, z);
    Vector3f l2 = new Vector3f(x, y+3, z);
    Vector3f l3 = new Vector3f(x, y+4, z);
    tree.add(new Block(leafID, l1));
    tree.add(new Block(leafID, l2));
    tree.add(new Block(leafID, l3));

  }
  public void tree5()
  {
    tree = new ArrayList();
    Vector3f t1 = new Vector3f(x, y+1, z);
    Vector3f t2 = new Vector3f(x, y+2, z);
    Vector3f t3 = new Vector3f(x, y+3, z);
    Vector3f t4 = new Vector3f(x-1, y+4, z);
    Vector3f t5 = new Vector3f(x+1, y+4, z);
    Vector3f t6 = new Vector3f(x, y+4, z-1);
    Vector3f t7 = new Vector3f(x, y+4, z+1);
    tree.add(new Block(trunkID, t1));
    tree.add(new Block(trunkID, t2));
    tree.add(new Block(trunkID, t3));
    tree.add(new Block(trunkID, t4));
    tree.add(new Block(trunkID, t5));
    tree.add(new Block(trunkID, t6));
    tree.add(new Block(trunkID, t7));
    Vector3f l1 = new Vector3f(x-1, y+5, z);
    Vector3f l2 = new Vector3f(x+1, y+5, z);
    Vector3f l3 = new Vector3f(x, y+5, z+1);
    Vector3f l4 = new Vector3f(x, y+5, z-1);
    Vector3f l5 = new Vector3f(x, y+4, z);
    Vector3f l6 = new Vector3f(x, y+5, z);
    Vector3f l7 = new Vector3f(x, y+6, z);
    tree.add(new Block(leafID, l1));
    tree.add(new Block(leafID, l2));
    tree.add(new Block(leafID, l3));
    tree.add(new Block(leafID, l4));
    tree.add(new Block(leafID, l5));
    tree.add(new Block(leafID, l6));
    tree.add(new Block(leafID, l7));
  }

    public ArrayList<Block> getTree()
    {
      int treePicker = (int)(Math.random()*5+1);
      if(treePicker == 1)
      {
        tree1();
      }
      if(treePicker == 2)
      {
        tree2();
      }
      if(treePicker == 3)
      {
        tree3();
      }
      if(treePicker == 4)
      {
        tree4();
      }
      if(treePicker == 5)
      {
        tree5();
      }
      return tree;
    }
}
