package gameEngine.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glShaderSource;

public class Utils {

    public static int loadResource(String fileName, int type) throws Exception{
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer);
                result.append("\n");
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
        int shaderID = glCreateShader(type);
        if(shaderID==0){
            throw new Exception("Error creating shader");
        }

        glShaderSource(shaderID, result);
        return shaderID;
    }

    public static List<String> readAllLines(String fileName) throws Exception{
        List<String> list = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                list.add(buffer);
            }
        }
        catch(Exception e){

        }
        return list;
    }
}
