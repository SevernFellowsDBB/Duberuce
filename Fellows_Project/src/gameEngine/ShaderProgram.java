package gameEngine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static gameEngine.Utilities.Utils.loadResource;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    private final Map<String, Integer> uniforms;

    public ShaderProgram() throws Exception{
        uniforms = new HashMap<>();
        programID = glCreateProgram();
        if(programID == 0){
            throw new Exception("Could not create shaders");
        }
        loadVertexShader("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\shaders\\vertex.shader");
        loadFragmentShader("C:\\Users\\student\\floobits\\share\\Duberuce\\Fellows_Project_1\\Fellows_Project\\src\\shaders\\fragment.shader");
        glCompileShader(vertexShaderID);
        glCompileShader(fragmentShaderID);
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        glLinkProgram(programID);
        glValidateProgram(programID);
    }

    public void loadVertexShader(String shaderSource) throws Exception{
        vertexShaderID = loadResource(shaderSource, GL_VERTEX_SHADER);
    }

    public void loadFragmentShader(String shaderSource) throws Exception{
        fragmentShaderID = loadResource(shaderSource, GL_FRAGMENT_SHADER);
    }

    public void createUniform(String name) throws Exception{
        int location = glGetUniformLocation(programID, name);
        if(location < 0){
            throw new Exception("Could not find uniform " + name);
        }
        uniforms.put(name, location);
    }

    public void setUniform(String name, Matrix4f value){
        try(MemoryStack stack = MemoryStack.stackPush()){
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(name), false, fb);
        }
    }

    public void setUniform(String name, int value) {
        glUniform1i(uniforms.get(name), value);
    }

    public void setUniform(String name, Vector3f value){
        glUniform3f(uniforms.get(name), value.x, value.y, value.z);
    }

    public void bind(){
        glUseProgram(programID);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void cleanUp(){
        unbind();
        if(programID != 0){
            glDeleteProgram(programID);
        }
    }

}
