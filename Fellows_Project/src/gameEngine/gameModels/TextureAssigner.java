package gameEngine.gameModels;

import gameEngine.Mesh;
import gameEngine.Texture;

import static gameEngine.Utilities.OBJLoader.loadMesh;

public class TextureAssigner {
    public Mesh grassMesh;
    public Mesh dirtMesh;
    public Mesh trunkMesh;
    public Mesh treeMesh;
    public Mesh stoneMesh;

    public static void init() {
        try {
            Mesh grassMesh = new Mesh();
            Mesh dirtMesh = new Mesh();
            Mesh trunkMesh = new Mesh();
            Mesh treeMesh = new Mesh();
            grassMesh = loadMesh("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\models\\cube.obj");
            Texture texture = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\grassBlock.png");
            grassMesh.setTexture(texture);
            dirtMesh = loadMesh("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\models\\cube.obj");
            Texture texture2 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\dirt.png");
            dirtMesh.setTexture(texture2);
            trunkMesh = loadMesh("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\models\\cube.obj");
            Texture texture3 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Trunk.png");
            trunkMesh.setTexture(texture3);
            treeMesh = loadMesh("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\models\\cube.obj");
            Texture texture4 = new Texture("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\textures\\Leaf.png");
            treeMesh.setTexture(texture4);
        } catch (Exception e) {

        }
    }

    public Mesh assign(int id) {
        if (id == 1) {
            return grassMesh;
        } else if (id == 2) {
            return dirtMesh;
        } else if (id == 3) {
            return trunkMesh;
        } else if (id == 4) {
            return treeMesh;
        }
        return null;
    }
}