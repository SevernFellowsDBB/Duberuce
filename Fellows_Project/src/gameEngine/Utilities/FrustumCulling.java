package gameEngine.Utilities;
import org.joml.Matrix4f;
import org.joml.FrustumIntersection;


public class FrustumCulling {

  private final Matrix4f projViewMatrix;
  private FrustumIntersection frustumInt;

  public FrustumCulling()
  {
    projViewMatrix = new Matrix4f();
    frustumInt = new FrustumIntersection();
  }

  public void updateFrustum(Matrix4f projMatrix, Matrix4f viewMatrix)
  {
    projViewMatrix.set(projMatrix);
    projViewMatrix.mul(viewMatrix);

    frustumInt.set(projViewMatrix);
  }

  public boolean insideFrustum(float x0, float y0, float z0, float boundingRadius)
  {
    return frustumInt.testSphere(x0, y0, z0, boundingRadius);
  }
}
