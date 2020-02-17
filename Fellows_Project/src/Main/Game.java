package Main;

import gameEngine.*;
import gameEngine.Utilities.FrustumCulling;
import gameEngine.Utilities.Physics;
import gameEngine.Utilities.Transformation;
import gameEngine.gameModels.Block;
import gameEngine.gameModels.Chunk;
import gameEngine.gameModels.Terrain;
import org.joml.AABBf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static gameEngine.Utilities.OBJLoader.loadMesh;
import static org.joml.Intersectionf.testAabAab;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game {
    private boolean transparent=false;
    private static float MOUSE_SENSITIVITY = 2f/3;
    private Vector3f cameraInc;
    private final Camera camera;
    private ArrayList<Mesh> meshes = new ArrayList();
    private final Renderer renderer;
    private boolean dev=true;
    private ClickedBlock clicker = new ClickedBlock();
    private ClickedFace cFace = new ClickedFace();
    public boolean wasNotButNowItIs=false;
    private ArrayList<Block> blocks = new ArrayList();
    ArrayList<Block> finalList = new ArrayList();
    ArrayList<Block> BWHDTRBOAAMBW = new ArrayList();
    private static float CAMERA_POS_STEP = .25f;
    int numChunks = 16;
    Chunk[][] chunks = new Chunk[numChunks][numChunks];
    Vector2f currentChunk;
    Transformation tr = new Transformation();
    FrustumCulling fr = new FrustumCulling();
    int renderDistance = 30;
    int selectedType = 1;

    public Game(){
        renderer = new Renderer();
        camera=new Camera();
        cameraInc = new Vector3f(0,0,0);
    }


    public void init(Window window) throws Exception {
        renderer.init(window);
        Terrain terrain = new Terrain(new PerlinNoise(256, 256));
        for(int i = 0; i < 9; i++){
            meshes.add(loadMesh("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\models\\cube.obj"));
        }
        Texture texture = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Grass.png");
        meshes.get(1).setTexture(texture);
        Texture texture2 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Dirt.png");
        meshes.get(2).setTexture(texture2);
        Texture texture3 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Trunk.png");
        meshes.get(3).setTexture(texture3);
        Texture texture4 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Leaf.png");
        meshes.get(4).setTexture(texture4);
        Texture texture5 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Water.png");
        meshes.get(5).setTexture(texture5);
        Texture texture6 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Stone.png");
        meshes.get(6).setTexture(texture6);
        Texture texture7 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Selector.png");
        meshes.get(7).setTexture(texture7);
        Texture texture8 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Cactus.jpeg");
        meshes.get(8).setTexture(texture8);
        ArrayList<Block> map = new ArrayList();
        for (int i = 0; i < numChunks; i++) {
            for (int k = 0; k < numChunks; k++) {
                Chunk hold = new Chunk(terrain.getTerr(i, k));
                map.addAll(hold.getChunk());
                chunks[i][k] = hold;
            }
        }
        int spawnChunk = numChunks*numChunks;
        int spawn = (int) (Math.random() * 256) * spawnChunk;
        int pickSpawn = 0;
        blocks.addAll(map);
        for (int i = 0; i < blocks.size(); i++) {
            if (map.get(i).getID() == 1) {
                blocks.get(i).setMesh(meshes.get(1));
                if (pickSpawn == spawn) {
                    camera.setPosition(blocks.get(i).getPos().x, blocks.get(i).getPos().y + 20, blocks.get(i).getPos().z);
                    //change if height does
                    if(camera.getPosition().y > 64){
                        camera.setPosition(camera.getPosition().x, 63, camera.getPosition().z);
                    }
                }
                pickSpawn++;
            }
            if (map.get(i).getID() == 2) {
                blocks.get(i).setMesh(meshes.get(2));
            }
            if (map.get(i).getID() == 3) {
                blocks.get(i).setMesh(meshes.get(3));
            }
            if (map.get(i).getID() == 4) {
                blocks.get(i).setMesh(meshes.get(4));
            }
            if (map.get(i).getID() == 5) {
                blocks.get(i).setMesh(meshes.get(5));
            }
            if (map.get(i).getID() == 6) {
                blocks.get(i).setMesh(meshes.get(6));
            }
            if (map.get(i).getID() == 8) {
                blocks.get(i).setMesh(meshes.get(8));
            }
        }
        currentChunk = getCurrentPlayerChunk();

    }


    public void input(Window window, mouseInput mouseInput) {
        if(window.keyPressed(GLFW_KEY_T)){
            transparent=!transparent;
            if(transparent){
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            }
            else{
                glDisable(GL_BLEND);
            }
        }
        if(dev) {
            currentChunk=getCurrentPlayerChunk();
            cameraInc.set(0, 0, 0);
            if (window.keyPressed(GLFW_KEY_W)) {
                cameraInc.z = -CAMERA_POS_STEP*3;
            } else if (window.keyPressed(GLFW_KEY_S)) {
                cameraInc.z = CAMERA_POS_STEP*3;
            }
            if (window.keyPressed(GLFW_KEY_A)) {
                cameraInc.x = -CAMERA_POS_STEP*3;
            } else if (window.keyPressed(GLFW_KEY_D)) {
                cameraInc.x = CAMERA_POS_STEP*3;
            }
            if (window.keyPressed(GLFW_KEY_Z)) {
                cameraInc.y = -CAMERA_POS_STEP*3;
            } else if (window.keyPressed(GLFW_KEY_X)) {
                cameraInc.y = CAMERA_POS_STEP*3;
            }
        }
        if(!dev){
            cameraInc.set(controlMovements(window));
        }

        if (window.keyPressed(GLFW_KEY_1)) {
            selectedType=1;
        } else if (window.keyPressed(GLFW_KEY_2)) {
            selectedType=2;
        }
        else if (window.keyPressed(GLFW_KEY_3)) {
            selectedType=3;
        }
        else if (window.keyPressed(GLFW_KEY_4)) {
            selectedType=4;
        }
        else if (window.keyPressed(GLFW_KEY_5)) {
            selectedType=5;
        }
        else if (window.keyPressed(GLFW_KEY_6)) {
            selectedType=6;
        }
    }

    public Vector3f controlMovements(Window window){
        Vector3f finalPos = new Vector3f();
        finalPos.set(0,cameraInc.y,0);
        float scale = blocks.get(0).getScale();

        boolean w = window.keyPressed(GLFW_KEY_W);
        boolean a = window.keyPressed(GLFW_KEY_A);
        boolean s = window.keyPressed(GLFW_KEY_S);
        boolean d = window.keyPressed(GLFW_KEY_D);
        boolean jump = window.keyPressed(GLFW_KEY_SPACE);

        Camera c = new Camera();
        c.set(camera);

        float z = 0;
        if(w){
            z = -(float)(0.5);
            c.movePosition(0,0,z*CAMERA_POS_STEP);
            AABBf p = new AABBf(c.getPosition().x-scale,c.getPosition().y-scale*8,c.getPosition().z-scale,c.getPosition().x+scale,c.getPosition().y+scale,c.getPosition().z+scale);
            for(Block b: blocks){
                if(z!=0) {
                    if (testAabAab(p, b.getAab())) {
                        c.movePosition(0, 0, (-CAMERA_POS_STEP) * z);
                        z = 0;
                    }
                }
            }
        }
        else if(s){
            z = (float)(0.5);
            c.movePosition(0,0,z*(CAMERA_POS_STEP));
            AABBf p = new AABBf(c.getPosition().x-scale,c.getPosition().y-scale*8,c.getPosition().z-scale,c.getPosition().x+scale,c.getPosition().y+scale,c.getPosition().z+scale);
            for(Block b: blocks){
                if(z!=0) {
                    if (testAabAab(p, b.getAab())) {
                        c.movePosition(0, 0, z * (-CAMERA_POS_STEP));
                        z = 0;
                    }
                }
            }
        }

        float x = 0;
        if(d){
            x = (float)(0.5);
            c.movePosition(x*CAMERA_POS_STEP,0,0);
            AABBf p = new AABBf(c.getPosition().x-scale,c.getPosition().y-scale*8,c.getPosition().z-scale,c.getPosition().x+scale,c.getPosition().y+scale,c.getPosition().z+scale);
            for(Block b: blocks){
                if(x!=0) {
                    if (testAabAab(p, b.getAab())) {
                        c.movePosition((-CAMERA_POS_STEP) * x, 0, 0);
                        x = 0;
                    }
                }
            }
        }
        else if(a){
            x = -(float)(0.5);
            c.movePosition(x*(CAMERA_POS_STEP),0,0);
            AABBf p = new AABBf(c.getPosition().x-scale,c.getPosition().y-scale*8,c.getPosition().z-scale,c.getPosition().x+scale,c.getPosition().y+scale,c.getPosition().z+scale);
            for(Block b: blocks){
                if(x!=0) {
                    if (testAabAab(p, b.getAab())) {
                        c.movePosition(x * (-CAMERA_POS_STEP), 0, 0);
                        x = 0;
                    }
                }
            }
        }

        float y = finalPos.y-((float)0.2);
        c.movePosition(0,y*.5f,0);
        AABBf p = new AABBf(c.getPosition().x-scale,c.getPosition().y-scale*8,c.getPosition().z-scale,c.getPosition().x+scale,c.getPosition().y+scale,c.getPosition().z+scale);
        for(Block b: blocks){
            if(y!=0) {
                if (testAabAab(p, b.getAab())) {
                    c.movePosition(0, y * (-.5f), 0);
                    y = 0;
                }
            }
        }
        if(jump){
            if(y==0){
                y=1;
            }
        }

        finalPos.set(x,y,z);
        return finalPos;
    }


    public int blockBelow(Vector3f pos){
        return chunks[(int)getCurrentPlayerChunk().x][(int)getCurrentPlayerChunk().y].getBlock(pos.x,(float)(camera.getPosition().y-2),pos.z).getID();


    }

    public void update(float interval, mouseInput MouseInput) {
        camera.movePosition(cameraInc.x*CAMERA_POS_STEP, cameraInc.y*CAMERA_POS_STEP, cameraInc.z*CAMERA_POS_STEP);
        if(MouseInput.isInWindow() && MouseInput.wasInWindow()){
            Vector2f rotVec = MouseInput.getDisplayVec();
            if(wasNotButNowItIs){
                rotVec= new Vector2f(0,0);
            }
            camera.moveRotation(rotVec.x*MOUSE_SENSITIVITY, rotVec.y*MOUSE_SENSITIVITY, 0);
        }
        wasNotButNowItIs=false;
        if(MouseInput.isInWindow()){
            if(!MouseInput.wasInWindow()){
                wasNotButNowItIs=true;
            }
        }
        MouseInput.setInWindow();
    }

    public void updateWorld(mouseInput MouseInput, Window window) {
        //pick the selected block

        blocks=new ArrayList<Block>();
        Vector2f playerPos = getCurrentPlayerChunk();
        for(int i=-(renderDistance/16+1);i<renderDistance/16+2;i++){
            for(int k=-(renderDistance/16+1);k<renderDistance/16+2;k++){
                if((int)playerPos.x+i>-1 && (int)playerPos.x+i<numChunks){
                    if((int)playerPos.y+k>-1 && (int)playerPos.y+k<numChunks){
                        blocks.addAll(chunks[i+(int)playerPos.x][k+(int)playerPos.y].getChunk());
                    }
                }
            }
        }
        int index=clicker.selectBlock(blocks, camera);
            if(index!=-1) {
                if (MouseInput.isLeftButtonPressed()) {
                    currentChunk = getCurrentChunkPos(index);
                    ArrayList<Block> newRenderedBlocks = chunks[(int) currentChunk.x ][(int) currentChunk.y].update(blocks.get(index));
                    assignMesh(newRenderedBlocks);
                    blocks.remove(index);
                    blocks.addAll(newRenderedBlocks);
                }
                if(MouseInput.isRightButtonPressed()){
                    currentChunk = getCurrentChunkPos(index);
                    int face = cFace.selectFace(camera,blocks.get(index));
                    Block b = chunks[(int) currentChunk.x ][(int) currentChunk.y].addBlock(face,blocks.get(index));
                    b.setId(selectedType);
                    assignMesh(b);
                    blocks.add(b);
                }
            }
        //update corresponding meshes
        //check for mouse click
        //change chunk
        BWHDTRBOAAMBW = new ArrayList<Block>();
        for (int i = 0; i < blocks.size(); i++) {
        fr.updateFrustum(tr.getProjectionMatrix((float)Math.toRadians(60), (float)window.getWidth(), window.getHeight(), .01f, 1000), tr.getViewMatrix(camera));
          if(fr.insideFrustum(blocks.get(i).getPos().x, blocks.get(i).getPos().y, blocks.get(i).getPos().z, 1) && Math.abs(camera.getPosition().x - blocks.get(i).getPos().x) < renderDistance && Math.abs(camera.getPosition()
                  .z - blocks.get(i).getPos().z) < renderDistance && Math.abs(camera.getPosition().y - blocks.get(i).getPos().y) < renderDistance)
          {
            BWHDTRBOAAMBW.add(blocks.get(i));
          }
        }
        finalList=new ArrayList<>();
        if(transparent) {
            int rotY=(int)camera.getRotation().y;
            System.out.println(rotY);
            if ((rotY % 360 >= 45 && rotY % 360 < 135) || (rotY%360<=-225 && rotY%360>-315)) {
                for (int k = renderDistance; k > -renderDistance; k--) {
                    for (int i = 0; i < BWHDTRBOAAMBW.size(); i++) {
                        if (BWHDTRBOAAMBW.get(i).getPos().x - k == (int) camera.getPosition().x) {
                            finalList.add(BWHDTRBOAAMBW.remove(i));
                            i--;
                        }
                    }
                }
            }
            else if ((rotY % 360 >= 135 && rotY % 360 < 225) || (rotY%360<=-135 && rotY%360>-225)) {
                for (int k = renderDistance; k > -renderDistance; k--) {
                    for (int i = 0; i < BWHDTRBOAAMBW.size(); i++) {
                        if (BWHDTRBOAAMBW.get(i).getPos().z - k == (int) camera.getPosition().z) {
                            finalList.add(BWHDTRBOAAMBW.remove(i));
                            i--;
                        }
                    }
                }
            }
            else if ((rotY % 360 >= 225 && rotY % 360 < 315) || (rotY%360 <=-45 && rotY%360>-135)) {
                for (int k = -renderDistance; k < renderDistance; k++) {
                    for (int i = 0; i < BWHDTRBOAAMBW.size(); i++) {
                        if (BWHDTRBOAAMBW.get(i).getPos().x - k == (int) camera.getPosition().x) {
                            finalList.add(BWHDTRBOAAMBW.remove(i));
                            i--;
                        }
                    }
                }
            }
            else{
                for (int k = -renderDistance; k < renderDistance; k++) {
                    for (int i = 0; i < BWHDTRBOAAMBW.size(); i++) {
                        if (BWHDTRBOAAMBW.get(i).getPos().z - k == (int) camera.getPosition().z) {
                            finalList.add(BWHDTRBOAAMBW.remove(i));
                            i--;
                        }
                    }
                }
            }
        }
        else{
            finalList = BWHDTRBOAAMBW;
        }
    }

    public void render(Window window)
    {
        renderer.render(window, camera, finalList);
    }


    public void cleanup() {
        renderer.cleanup();
        for (Block block : finalList) block.getMesh().cleanup();
    }

    public Vector2f getCurrentChunkPos(int index){
        return new Vector2f(blocks.get(index).getPos().x/16, blocks.get(index).getPos().z/16);
    }

    public Vector2f getCurrentPlayerChunk(){
        return new Vector2f((camera.getPosition().x+cameraInc.x)/16, (camera.getPosition().z+cameraInc.z)/16);
    }

    public void assignMesh(ArrayList<Block> passed){
        for (int i = 0; i < passed.size(); i++) {
            if (passed.get(i).getID() == 1) {
                passed.get(i).setMesh(meshes.get(1));
            }
            if (passed.get(i).getID() == 2) {
                passed.get(i).setMesh(meshes.get(2));
            }
            if (passed.get(i).getID() == 3) {
                passed.get(i).setMesh(meshes.get(3));
            }
            if (passed.get(i).getID() == 4) {
                passed.get(i).setMesh(meshes.get(4));
            }
            if (passed.get(i).getID() == 5) {
                passed.get(i).setMesh(meshes.get(5));
            }
            if (passed.get(i).getID() == 6) {
                passed.get(i).setMesh(meshes.get(6));
            }
            if (passed.get(i).getID() == 8) {
                passed.get(i).setMesh(meshes.get(8));
            }
        }
    }

    public void assignMesh(Block passed){
            if (passed.getID() == 1) {
                passed.setMesh(meshes.get(1));
            }
            if (passed.getID() == 2) {
                passed.setMesh(meshes.get(2));
            }
            if (passed.getID() == 3) {
                passed.setMesh(meshes.get(3));
            }
            if (passed.getID() == 4) {
                passed.setMesh(meshes.get(4));
            }
            if (passed.getID() == 5) {
                passed.setMesh(meshes.get(5));
            }
            if (passed.getID() == 6) {
                passed.setMesh(meshes.get(6));
            }
            if (passed.getID() == 8) {
                passed.setMesh(meshes.get(8));
            }

    }
}