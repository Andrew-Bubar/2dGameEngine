package CritHit;


import static org.lwjgl.opengl.GL20.*;

public class LevelEditorScene extends Scene{

    private String vertexShaderSource = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout(location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSource = "#version 330\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main(){\n" +
            "    color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderprogram;

    private float[] vertexArray = {

            //position          //color
            0.5f, -0.5f, 0.0f,  1.0f,0.0f,1.0f,1.0f,    //Bottom Right   Red
            -0.5f, 0.5f, 0.0f,  0.0f,1.0f,0.0f,1.0f,    //Top Left  Green
            0.5f, 0.5f, 0.0f,   0.0f,0.0f,1.0f,1.0f,    //Top Right Blue
            -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, //Bottom Left
    };

    private int[] elementArray = {

    };

    public LevelEditorScene(){
        System.out.println("Entering Level Editor");
    }

    @Override
    public void init() {
        //load + compile vertex
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        //pass shader to the gpu
        glShaderSource(vertexID, vertexShaderSource);
        glCompileShader(vertexID);

        //error check
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tVertex Shader complation failed");
            System.out.println(glGetShaderInfoLog(vertexID, len));

            assert false : "";
        }

        //load + compile fragment
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        //pass shader to the gpu
        glShaderSource(fragmentID, fragmentShaderSource);
        glCompileShader(fragmentID);

        //error check
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tFragment Shader complation failed");
            System.out.println(glGetShaderInfoLog(fragmentID, len));

            assert false : "";
        }

        //linking the shaders and further error checks
        shaderprogram = glCreateProgram();
        glAttachShader(shaderprogram, vertexID);
        glAttachShader(shaderprogram, fragmentID);
        glLinkProgram(shaderprogram);

        success = glGetProgrami(shaderprogram, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int len = glGetProgrami(shaderprogram, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tLinking the shaders together failed");
            System.out.println(glGetProgramInfoLog(shaderprogram, len));

            assert false : "";
        }
    }

    @Override
    public void update(float dt) {

    }
}
