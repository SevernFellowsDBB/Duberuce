package Main;

import gameEngine.Camera;
import gameEngine.Mesh;
import gameEngine.ShaderProgram;
import gameEngine.Utilities.Transformation;
import gameEngine.Window;
import gameEngine.gameModels.Block;
import org.joml.Matrix4f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL20C.glViewport;
import static org.lwjgl.opengles.GLES20.GL_DEPTH_BUFFER_BIT;

public class Renderer {
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static float Z_CLOSE = .01f;
    private static float Z_FAR = 1000.0f;
    private final Transformation transformation;
    private ShaderProgram shaders;
    public Renderer() {
        transformation=new Transformation();
    }

    public void init(Window window) throws Exception{
        shaders = new ShaderProgram();
        shaders.createUniform("color");
        shaders.createUniform("changePos");
        shaders.createUniform("useColor");
        shaders.createUniform("projectionMatrix");
        shaders.createUniform("modelViewMatrix");
        shaders.createUniform("texture_sampler");

    }

    public void clear(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Camera camera, ArrayList<Block> blocks){
        clear();
        if(window.isResized()){
            glViewport(0,0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
        shaders.bind();
        Matrix4f projectionMat = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_CLOSE, Z_FAR);
        shaders.setUniform("projectionMatrix", projectionMat);
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        shaders.setUniform("texture_sampler", 0);

        int len = 0;
        for(Block block : blocks){
            if(block.getMesh()!=null) {
                Mesh mesh = block.getMesh();
                Matrix4f modelViewMatrix = transformation.getModelViewMatrix(block, viewMatrix);
                shaders.setUniform("modelViewMatrix", modelViewMatrix);
                shaders.setUniform("color", mesh.getColor());
                shaders.setUniform("useColor", block.getSelected() ? 1 : 0);
                shaders.setUniform("changePos", 0);


                block.getMesh().render();
                len++;
            }
        }
        shaders.unbind();

    }

    public void cleanup(){
        if(shaders != null){
            shaders.cleanUp();
        }
    }
}
