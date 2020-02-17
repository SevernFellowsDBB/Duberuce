package gameEngine;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glGenerateMipmap;
import static org.lwjgl.opengles.EXTSparseTexture.GL_TEXTURE_2D;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private int ID;

    public Texture(String fileName) throws Exception {
        this(loadTexture(fileName));
    }

    public Texture(int id) {
        this.ID=id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, ID);
    }

    public int getID() {
        return ID;
    }

    private static int loadTexture(String fileName) throws Exception{
        int width;
        int height;
        ByteBuffer buffer;
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            buffer = stbi_load(fileName, w, h, channels, 4);
            if (buffer == null) {
                throw new Exception("Image file " + fileName + " not loaded: " + stbi_failure_reason());
            }
            width = w.get();
            height = h.get();
        }
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
        stbi_image_free(buffer);
        return textureID;

    }

    public void cleanup(){
        glDeleteTextures(ID);
    }

}
