#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;
uniform int changePos;

void main()
{
    if(changePos == 0){
        gl_Position = projectionMatrix * modelViewMatrix * vec4(position*2, 1.0);
        outTexCoord = texCoord;
    }
    else{
        gl_Position = vec4(position*2, 1.0);
        outTexCoord = texCoord;
    }
}