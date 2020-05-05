package gameEngine;

import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;

    public Camera(){
        position=new Vector3f(0,0,0);
        rotation=new Vector3f(0,0,0);
    }

    public Camera(Vector3f position, Vector3f rotation){
        this.position=position;
        this.rotation=rotation;
    }

    public Vector3f getPosition() {
     /* position.x += .5f;
      position.y += .5f;
      position.z += .5f;
      */
        return position;
    }

    public void setPosition(float x, float y, float z){
        position.x=x;
        position.y=y;
        position.z=z;
    }

    public void movePosition(float offSetX, float offSetY, float offSetZ){
        if(offSetZ!=0){
            position.x+=(float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offSetZ;
            position.z+=(float)Math.cos(Math.toRadians(rotation.y)) * offSetZ;
        }
        if(offSetX!=0){
            position.x+=(float)Math.sin(Math.toRadians(rotation.y-90)) * -1.0f * offSetX;
            position.z+=(float)Math.cos(Math.toRadians(rotation.y-90)) * offSetX;
        }
        position.y+=offSetY;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z){
        rotation.x=x;
        rotation.y=y;
        rotation.z=z;
    }

    public void moveRotation(float offSetX, float offSetY, float offSetZ){
        rotation.x+=offSetX;
        rotation.y+=offSetY;
        rotation.z+=offSetZ;
    }

    public void set(Camera c) {
        rotation = c.getRotation();
        position = c.getPosition();
    }
}
