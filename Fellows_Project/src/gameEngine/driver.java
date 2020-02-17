package gameEngine;


import Main.Game;
import gameEngine.Utilities.Timer;

public class driver implements Runnable{

    public static final int GOAL_FPS = 30;
    public static final int TARGET_UPS = 15;

    private final Window window;
    private final mouseInput MouseInput;
    private final Game gameLogic;
    private Timer timer;

    public driver(String windowTitle, int width, int height, boolean vSync) throws Exception{
        MouseInput= new mouseInput();
        window = new Window(windowTitle, width, height, vSync);
        gameLogic = new Game();
        timer = new Timer();
    }

    @Override
    public void run(){
        try{
            init();
            gameLoop();
        }
        catch (Exception e){
            System.out.println(e);
        }

        finally{
            gameLogic.cleanup();
        }
    }


    protected void init() throws Exception{
        window.init();
        timer.init();
        MouseInput.init(window);
        gameLogic.init(window);
    }

    protected void gameLoop(){
        float elapsedTime;
        float holder = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while(running && !window.windowShouldClose()){
            elapsedTime = timer.getElapsedTime();
            holder += elapsedTime;

            input();
            while(holder>=interval){
                update(interval);
                holder -= interval;
            }
            render();

            if(!window.isvSync()){
                sync();
            }

        }
    }

    private void sync(){
        float slot = 1f/GOAL_FPS;
        double end = timer.getLastLoop() + slot;
        while(timer.getTime() < end) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }

    }

    protected void input(){
        MouseInput.input(window);
        gameLogic.input(window, MouseInput);
    }

    protected void update(float interval){

        gameLogic.update(interval, MouseInput);
        gameLogic.updateWorld(MouseInput, window);
    }

    protected void render(){
        gameLogic.render(window);
        window.update();
    }




}
