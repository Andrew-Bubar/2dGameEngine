package CritHit;

import CritHit.util.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.*;

public class Window {

    int width, height;
    String title;

    private static Window window = null;

    private static Scene currentScene;

    private long glfwWindow;

    public float r, g, b, a;
    private boolean fadeToBlack = false;

    private Window(){

        this.width = 1920;
        this.height = 1080;
        this.title = "Mario Test";

        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }

    public static void changeScene(int newScene){
        switch(newScene){
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                break;
            default:
                assert false : "Unknown Scene" + newScene;
                break;
        }
    }

    public static Window get(){

        if (Window.window == null){
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run(){

        System.out.println("Hellowo LWGL " + Version.getVersion() + " !");

        init();

        loop();

        //free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //terminate glfw and free the error
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){

        //catching errors
        GLFWErrorCallback.createPrint(System.err).set(); //directing the errors to the build log

        if (!glfwInit()){
            throw new IllegalStateException("Unable to init GLFW");
        }

        //configing glfw
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //create the window
        glfwWindow = glfwCreateWindow(this.width,this.height,this.title,NULL,NULL);
        if(glfwWindow == NULL){
            throw new IllegalStateException("Failed to make glfw window");
        }

        //input things to process inputs in the window
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);

        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        //selecting start scene
        Window.changeScene(0);
    }

    public void loop(){

        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)){

            //polling events for inputs
            glfwPollEvents();

            glClearColor(r,g,b,a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
