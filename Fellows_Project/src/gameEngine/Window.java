package gameEngine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private final String title;
    private int width;
    private int height;
    private long windowHandle;
    private boolean resized;
    private boolean vSync;

    public Window(String title, int width, int height, boolean vSync){
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.resized = false;
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()){
            throw new IllegalStateException("Could not initialize");
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);


        windowHandle = glfwCreateWindow(width, height, title, 0l, 0l);
        if(windowHandle == 0l){
            throw new RuntimeException("Failed to create window");
        }

        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            this.width=width;
            this.height=height;
            this.setResized(true);
        });

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE){
                glfwSetWindowShouldClose(window, true);
            }
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int setXPos = (vidmode.width()-width)/2;
        int setYPos = (vidmode.height()-height)/2;
        glfwSetWindowPos(windowHandle, setXPos, setYPos);

        glfwMakeContextCurrent(windowHandle);

        if(isvSync()){
            glfwSwapInterval(1);
        }

        glfwShowWindow(windowHandle);
        GL.createCapabilities();
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glClearColor(0.4f, 0.4f, 1f, 0.8f);
        glfwSetInputMode(windowHandle,GLFW_CURSOR,GLFW_CURSOR_HIDDEN);
        glEnable(GL_DEPTH_TEST);
    }

    public void setColor(float r, float g, float b, float alpha){
        glClearColor(r,g,b,alpha);
    }

    public boolean keyPressed(int keyCode){
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public boolean windowShouldClose(){
        return glfwWindowShouldClose(windowHandle);
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public String getTitle(){
        return title;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isResized() {
        return resized;
    }


    public boolean isvSync() {
        return vSync;
    }

    public void setClearColor(float r, float g, float b, float a){
        glClearColor(r, g, b, a);
    }

    public void setResized(boolean resized){
        this.resized = resized;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public void update(){
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }



}
