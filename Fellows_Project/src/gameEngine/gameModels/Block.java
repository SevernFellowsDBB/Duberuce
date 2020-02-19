package gameEngine.gameModels;

import gameEngine.Mesh;
import org.joml.AABBf;
import org.joml.Vector2f;
import org.joml.Vector3f;


public class Block {
    private Mesh mesh;
    private final Vector3f pos;
    private float scale;
    private final Vector3f rotation;
    int id;
    boolean selected;
    private boolean isRendered;

    public Block(int id, Vector3f position){
        this.id=id;
        selected=false;
        pos = new Vector3f(position.x,position.y,position.z);
        scale=(float).25;
        rotation = new Vector3f(0,0,0);
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(float x, float y, float z){
        this.pos.x=x;
        this.pos.y=y;
        this.pos.z=z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        pos.x*=scale*2;
        pos.y*=scale*2;
        pos.z*=scale*2;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z){
        this.rotation.x=x;
        this.rotation.y=y;
        this.rotation.z=z;
    }

    public int getID()
    {
      return id;
    }

    public boolean getSelected()
    {
      return selected;
    }

    public void setSelected(boolean sel)
    {
      selected=sel;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public boolean isRendered() {
        return isRendered;
    }

    public void setRendered(boolean rendered) {
        isRendered = rendered;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AABBf getAab(){
        AABBf box = new AABBf(pos.x-scale,pos.y-scale,pos.z-scale,pos.x+scale,pos.y+scale,pos.z+scale);
        return box;
    }

    public Vector2f getChunk(){
        return new Vector2f((int)(pos.x/16), (int)(pos.z/16));
    }
}
