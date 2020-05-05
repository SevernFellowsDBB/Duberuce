package gameEngine.Utilities;

import gameEngine.Mesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class OBJLoader {
    public static Mesh loadMesh(String fileName) throws Exception{
        List<String> lines = Utils.readAllLines(fileName);
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();
        for(String line: lines){
            String[] tokens = line.split("\\s+");
            switch(tokens[0]){
                case "v":
                    Vector3f vec3f = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    vertices.add(vec3f);
                    break;
                case "vt":
                    Vector2f vec2f = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));
                    textures.add(vec2f);
                    break;
                case"vn":
                    Vector3f vec3Norm = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    normals.add(vec3Norm);
                    break;
                case "f":
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                    break;
                default:
                    break;
            }
        }
        return reorderLists(vertices, textures, normals, faces);
    }

    private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> textChordList,
                                     List<Vector3f> normList, List<Face> facesList){
        List<Integer> indices = new ArrayList<>();
        float[] posArr = new float[posList.size()*3];
        int i=0;
        for(Vector3f pos : posList){
            posArr[i*3] = pos.x;
            posArr[i*3 + 1] = pos.y;
            posArr[i*3 + 2] = pos.z;
            i++;
        }

        float[] textChordArr = new float[posList.size() * 2];
        float[] normArr = new float[posList.size() * 3];

        for(Face face: facesList){
            IDxGroup[] faceVertexIndices = face.getFaceVertexIndices();
            for(IDxGroup indValue : faceVertexIndices){
                processFaceVertex(indValue, textChordList, normList, indices, textChordArr, normArr);
            }
        }

        int[] indicesArr = new int[indices.size()];
        indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
        Mesh mesh = new Mesh(posArr, textChordArr, normArr, indicesArr);
        return mesh;
    }

    private static void processFaceVertex(IDxGroup indices, List<Vector2f> textChordList,
                                          List<Vector3f> normList, List<Integer> indicesList,
                                            float[] textChordArr, float[] normArr){
        int posIndex = indices.idxPos;
        indicesList.add(posIndex);

        if(indices.idxTextChord>=0){
            Vector2f textChord = textChordList.get(indices.idxTextChord);
            textChordArr[posIndex*2] = textChord.x;
            textChordArr[posIndex*2 + 1] = 1-textChord.y;
        }
        if(indices.idxVecNormal>=0){
            Vector3f vecNorm = normList.get(indices.idxVecNormal);
            normArr[posIndex * 3] = vecNorm.x;
            normArr[posIndex*3 +1]= vecNorm.y;
            normArr[posIndex*3 + 2]= vecNorm.z;
        }
    }

    protected static class Face{
        private IDxGroup[] idxGroups = new IDxGroup[3];
        public Face(String v1, String v2, String v3){
            idxGroups = new IDxGroup[3];
            idxGroups[0]=parseLine(v1);
            idxGroups[1]=parseLine(v2);
            idxGroups[2]=parseLine(v3);
        }

        private IDxGroup parseLine(String line){
            IDxGroup idxGroup = new IDxGroup();

            String[] lineTokens = line.split("/");
            int length=lineTokens.length;
            idxGroup.idxPos=Integer.parseInt(lineTokens[0])-1;
            if(length>1){
                String textChord = lineTokens[1];
                idxGroup.idxTextChord = textChord.length()>0 ? Integer.parseInt(textChord)-1 : IDxGroup.NO_VALUE;
                if(length>2){
                    idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2])-1;
                }
            }
            return idxGroup;
        }

        public IDxGroup[] getFaceVertexIndices() {
            return idxGroups;
        }
    }

    protected static class IDxGroup{
        public static int NO_VALUE = -1;
        public int idxPos;
        public int idxTextChord;
        public int idxVecNormal;
        public IDxGroup(){
            idxPos=NO_VALUE;
            idxTextChord=NO_VALUE;
            idxVecNormal=NO_VALUE;
        }
    }
}

