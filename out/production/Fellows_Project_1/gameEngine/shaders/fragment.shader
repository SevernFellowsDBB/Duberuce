#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec3 color;
uniform int useColor;

void main()
{
    if(useColor == 1){
    fragColor = texture(texture_sampler, outTexCoord) + vec4(color,1.0);
    }
    else{
    fragColor = texture(texture_sampler, outTexCoord);
    }
}