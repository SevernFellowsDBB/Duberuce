package gameEngine;

import org.joml.Vector3f;


public class Block {
    private final Mesh mesh;
    private final Vector3f pos;
    private float scale;
    private final Vector3f rotation;

    public Block(Mesh mesh, Vector3f position){
        this.mesh = mesh;
        pos = new Vector3f(position.x,position.y,position.z);
        scale=1;
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

    public Mesh getMesh() {
        return mesh;
    }
}
