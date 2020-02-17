package gameEngine.Utilities;

import gameEngine.Camera;
import gameEngine.gameModels.Block;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {
    private final Matrix4f projectionMatrix;
    private final Matrix4f modelViewMatrix;
    private final Matrix4f viewMatrix;

    public Transformation(){
        modelViewMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float Zclose, float Zfar){
        float aspectRatio = width/height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, Zclose, Zfar);
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPosition = camera.getPosition();
        Vector3f cameraRotation = camera.getRotation();
        viewMatrix.identity();
        viewMatrix.rotate((float)Math.toRadians(cameraRotation.x), new Vector3f(1,0,0)).rotate((float)Math.toRadians(cameraRotation.y), new Vector3f(0,1,0));
        viewMatrix.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
        return viewMatrix;
    }

    public Matrix4f getModelViewMatrix(Block block, Matrix4f viewMatrix){
        Vector3f rotation = block.getRotation();
        modelViewMatrix.identity().translate(block.getPos()).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(block.getScale());
        Matrix4f currentView = new Matrix4f(viewMatrix);
        return currentView.mul(modelViewMatrix);
    }
}
