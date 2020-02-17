package gameEngine;


import gameEngine.Utilities.Transformation;
import gameEngine.gameModels.Block;
import org.joml.*;

public class ClickedFace {
    private Vector3f dir;
    private Vector3f point1;
    private Vector3f point2;
    private Vector3f point3;
   // private Vector3f normal;
    private float e;
    Transformation t = new Transformation();
    private Planef p;
    private Rayf r;
    private Vector4f dest;
    private float[] faceDist = new float[6];
    private Vector3f[] normal = new Vector3f[6];

    public ClickedFace(){
        dir = new Vector3f();
        for(int i=0;i<normal.length;i++){
        normal[i] =new Vector3f();
        }
        /*
        point1 = new Vector3f();
        point2 = new Vector3f();
        point3 = new Vector3f();
        normal = new Vector3f();
        p = new Planef();
        r = new Rayf();
        dest = new Vector4f();
         */

    }

    public int selectFace(Camera c, Block b){
        dir = t.getViewMatrix(c).positiveZ(dir).negate();
       // r = new Rayf(c.getPosition(),dir);

        normal[0].set(-1,0,0);
        normal[1].set(1,0,0);
        normal[2].set(0,0,-1);
        normal[3].set(0,0,1);
        normal[4] = new Vector3f(0,-1,0);
        normal[5] = new Vector3f(0,1,0);
        for(int i=0;i<faceDist.length;i++) {
            faceDist[i] = normal[i].dot(dir);
        }
        /*
        //x negative side: 0
        point1.x = b.getPos().x;point1.y = b.getPos().y;point1.z = b.getPos().z;
        point2.x = b.getPos().x;point2.y = b.getPos().y;point2.z = b.getPos().z;
        point3.x = b.getPos().x;point3.y = b.getPos().y;point3.z = b.getPos().z;
        float s = b.getScale();

        System.out.println(point1.x+" " + point1.y+" "+point1.z);
        System.out.println(point2.x+" " + point2.y+" "+point2.z);
        System.out.println(point3.x+" " + point3.y+" "+point3.z);
        point1.add(-s,-s,-s);
        point2.add(-s,-s,s);
        point3.add(-s,s,-s);
        System.out.println(point1.x+" " + point1.y+" "+point1.z);
        System.out.println(point2.x+" " + point2.y+" "+point2.z);
        System.out.println(point3.x+" " + point3.y+" "+point3.z);
        p.equationFromPoints(point1,point2,point3,dest);
        System.out.println(dest.x + " " + dest.y + " " + dest.z + " " + dest.w);
        faceDist[0] = Intersectionf.intersectRayPlane(r,p,e);

        //x positive side: 1
        point1.x = b.getPos().x;point1.y = b.getPos().y;point1.z = b.getPos().z;
        point2.x = b.getPos().x;point2.y = b.getPos().y;point2.z = b.getPos().z;
        point3.x = b.getPos().x;point3.y = b.getPos().y;point3.z = b.getPos().z;
        point1.add(s,-s,-s);
        point2.add(s,s,-s);
        point3.add(s,s,s);
        p = new Planef();
        p.equationFromPoints(point1,point2,point3,dest);
        faceDist[1] = Intersectionf.intersectRayPlane(r,p,e);

        //z negative size: 2
        point1.x = b.getPos().x;point1.y = b.getPos().y;point1.z = b.getPos().z;
        point2.x = b.getPos().x;point2.y = b.getPos().y;point2.z = b.getPos().z;
        point3.x = b.getPos().x;point3.y = b.getPos().y;point3.z = b.getPos().z;
        point1.add(-s,-s,-s);
        point2.add(s,-s,-s);
        point3.add(-s,s,-s);
        p = new Planef();
        p.equationFromPoints(point1,point2,point3,dest);
        faceDist[2] = Intersectionf.intersectRayPlane(r,p,e);
        //z positive side: 3
        point1.x = b.getPos().x;point1.y = b.getPos().y;point1.z = b.getPos().z;
        point2.x = b.getPos().x;point2.y = b.getPos().y;point2.z = b.getPos().z;
        point3.x = b.getPos().x;point3.y = b.getPos().y;point3.z = b.getPos().z;
        point1.add(s,s,s);
        point2.add(s,-s,s);
        point3.add(-s,-s,s);
        p = new Planef();
        p.equationFromPoints(point1,point2,point3,dest);
        faceDist[3] = Intersectionf.intersectRayPlane(r,p,e);

        //y negative side: 4
        point1.x = b.getPos().x;point1.y = b.getPos().y;point1.z = b.getPos().z;
        point2.x = b.getPos().x;point2.y = b.getPos().y;point2.z = b.getPos().z;
        point3.x = b.getPos().x;point3.y = b.getPos().y;point3.z = b.getPos().z;
        point1.add(-s,-s,-s);
        point2.add(-s,-s,s);
        point3.add(s,-s,-s);
        p = new Planef();
        p.equationFromPoints(point1,point2,point3,dest);
        faceDist[4] = Intersectionf.intersectRayPlane(r,p,e);
        System.out.println(e);

        //y positive side: 5
        point1.x = b.getPos().x;point1.y = b.getPos().y;point1.z = b.getPos().z;
        point2.x = b.getPos().x;point2.y = b.getPos().y;point2.z = b.getPos().z;
        point3.x = b.getPos().x;point3.y = b.getPos().y;point3.z = b.getPos().z;
        point1.add(s,s,s);
        point2.add(-s,s,s);
        point3.add(s,s,-s);
        p = new Planef();
        System.out.println(point1.x+" " + point1.y+" "+point1.z);
        System.out.println(point2.x+" " + point2.y+" "+point2.z);
        System.out.println(point3.x+" " + point3.y+" "+point3.z);
        p.equationFromPoints(point1,point2,point3,dest);
        System.out.println(p.a + " " + p.b + " " + p.c + " " + p.d);
        //faceDist[5] = Intersectionf.intersectRayPlane(c.getPosition().x,c.getPosition().y,c.getPosition().z,dest.x,dest.y,dest.z,dest.w);

         */

        System.out.println(faceDist[0] + " " + faceDist[1] + " "+faceDist[2] + " "+faceDist[3] + " " +faceDist[4] + " "+faceDist[5]);
        int closestFace = -1;
        float dist = 100;
        for(int i=0;i<faceDist.length;i++){
            if(faceDist[i] < dist){
                closestFace = i;
                dist = faceDist[i];
            }
        }
        return closestFace;
    }
}
