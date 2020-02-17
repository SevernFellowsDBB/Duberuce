package gameEngine;

import gameEngine.Utilities.Transformation;
import gameEngine.gameModels.Block;
import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class ClickedBlock{
    private final Vector3f max;
    private final Vector3f min;
    private final Vector2f nearFar;
    private Vector3f dir;
    Transformation t = new Transformation();

    public ClickedBlock(){
        max = new Vector3f();
        min = new Vector3f();
        nearFar = new Vector2f();
        dir = new Vector3f();
    }

    public int selectBlock(ArrayList<Block> blocks, Camera camera){
        int index=-1;
        Block selectedBlock = null;
        boolean isBlock = false;
        float closestDistance = 10;
        dir = t.getViewMatrix(camera).positiveZ(dir).negate();
        for(int i = 0;i<blocks.size();i++) {
            blocks.get(i).setSelected(false);
            min.set(blocks.get(i).getPos());
            max.set(blocks.get(i).getPos());
            float x = blocks.get(i).getScale();
            min.add(-x,-x,-x);
            max.add(x,x,x);
            if(Intersectionf.intersectRayAab(camera.getPosition(),dir,min,max,nearFar) && nearFar.x < closestDistance){
                closestDistance = nearFar.x;
                selectedBlock = blocks.get(i);
                isBlock = true;
                index=i;
            }
        }
        if(isBlock) {
            selectedBlock.setSelected(true);
        }
        return index;
    }

}
