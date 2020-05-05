package gameEngine;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


public class Mesh {
    private static final Vector3f DEFAULT_COLOR = new Vector3f(.1f, .1f, .1f);
    private  int vaoID;
    private  List<Integer> vboIdList;
    private  int numVertex;
    private Texture texture;
    private Vector3f color;

    public Mesh(){
        color = DEFAULT_COLOR;
    }

    public Mesh(float[] positions, float[] textCords, float[] normals, int[] indices){
        FloatBuffer posBuffer = null;
        IntBuffer indicesBuff = null;
        FloatBuffer vecNormalsBuffer = null;
        FloatBuffer textChordsBuffer = null;
        try{
            color = DEFAULT_COLOR;
            numVertex = indices.length;
            vboIdList = new ArrayList();

            vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);

            // Position VBO
            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            posBuffer = MemoryUtil.memAllocFloat(positions.length);
            posBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            textChordsBuffer = MemoryUtil.memAllocFloat(textCords.length);
            textChordsBuffer.put(textCords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, textChordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            // Index VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            indicesBuff = MemoryUtil.memAllocInt(indices.length);
            indicesBuff.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuff, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
        finally{
            if(posBuffer != null){
                MemoryUtil.memFree(posBuffer);
            }
            if(indicesBuff != null){
                MemoryUtil.memFree(indicesBuff);
            }
            if(textChordsBuffer != null){
                MemoryUtil.memFree(textChordsBuffer);
            }
            if(vecNormalsBuffer != null){
                MemoryUtil.memFree(vecNormalsBuffer);
            }
        }
    }

    public boolean isTextured(){
        return this.texture!=null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture){
        this.texture=texture;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getColor() {
        return color;
    }


    public int getVaoID(){
        return vaoID;
    }

    public int getNumVertex(){
        return numVertex;
    }

    public void cleanup(){
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for(Integer vboID : vboIdList){
            glDeleteBuffers(vboID);
        }
        if(texture!=null) {
            texture.cleanup();
        }
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoID);
    }

    public void render(){
        if(texture!=null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture.getID());
        }
        glBindVertexArray(getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, numVertex, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
