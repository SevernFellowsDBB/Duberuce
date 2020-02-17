package gameEngine;

import org.joml.Vector2d;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class mouseInput {
    private final Vector2d previouPos;
    private final Vector2d currentPos;
    private final Vector2f displayVec;
    private boolean inWindow=false;
    private boolean previousInWindow=false;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;

    public mouseInput(){
        previouPos = new Vector2d(-1,-1);
        currentPos=new Vector2d(0,0);
        displayVec=new Vector2f();
    }

    public void init(Window window){
        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
            currentPos.x=xpos;
            currentPos.y=ypos;
        });
        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            inWindow=entered;
        });
        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            leftButtonPressed = button ==  GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public Vector2f getDisplayVec(){
        return displayVec;
    }

    public void input(Window window){
        displayVec.x=0;
        displayVec.y=0;
        if(previouPos.x>0 && previouPos.y > 0 && inWindow){
            double deltax = currentPos.x-previouPos.x;
            double deltay = currentPos.y-previouPos.y;
            boolean rotateX = deltax !=0;
            boolean rotateY = deltay !=0;
            if(rotateX){
                displayVec.y=(float)deltax;
            }
            if(rotateY){
                displayVec.x=(float)deltay;
            }
        }
        previouPos.x=currentPos.x;
        previouPos.y=currentPos.y;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public boolean isInWindow() {
        return inWindow;
    }
    public void setInWindow() {
        previousInWindow = isInWindow();
    }
    public boolean wasInWindow() {
        return previousInWindow;
    }

    public void setPastPos(){
        previouPos.x=currentPos.x;
        previouPos.y=currentPos.y;
    }
}
