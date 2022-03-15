package CritHit;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {

    private static MouseListener instance;
    private double scrollx, scrolly;
    private double xPos, yPos, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener(){
        this.scrollx = 0.0;
        this.scrolly = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get(){

        if(instance == null){
            instance = new MouseListener();
        }

        return instance;
    }

    //looking for position
    public static void mousePosCallback(long window, double xpos, double ypos){

        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;

        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    //looking for inputs
    public static void mouseButtonCallback(long window, int button, int action, int mods){

        if(action == GLFW_PRESS) {
            if(button < get().mouseButtonPressed.length){
                get().mouseButtonPressed[button] = true;
            }
        }else if (action == GLFW_RELEASE){
            if(button < get().mouseButtonPressed.length){
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    //looking for scroll value
    public static void mouseScrollCallback(long window, double xOffset, double yOffset){
        get().scrollx = xOffset;
        get().scrolly = yOffset;
    }

    //cleaning up the inputs
    public static void endFrame(){
        get().scrollx = 0.0;
        get().scrolly = 0.0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    //mouse positions
    public static float getX(){
        return (float) get().xPos;
    }
    public static float getY(){
        return (float) get().yPos;
    }

    public static float getDX(){
        return (float) (get().lastX - get().xPos);
    }
    public static float getDY(){
        return (float) (get().lastY - get().yPos);
    }

    //scroll values
    public static float getScrollX(){
        return(float)get().scrollx;
    }
    public static float getScrollY(){
        return(float)get().scrolly;
    }

    //dragging?
    public static boolean isDragging(){
        return get().isDragging;
    }

    //button inputs
    public static boolean mouseButtonDown(int button){
        if(button < get().mouseButtonPressed.length){
            return get().mouseButtonPressed[button];
        }else {
            return false;
        }
    }
}
