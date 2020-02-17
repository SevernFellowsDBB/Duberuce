package gameEngine.Utilities;

public class Timer {

    private double lastLoop;

    public void init(){
        lastLoop = getTime();
    }

    public double getTime(){
        return System.nanoTime() / 1000_000_000.0;
    }

    public float getElapsedTime(){
        double time = getTime();
        float elapsedTime = (float) (time-lastLoop);
        lastLoop=time;
        return elapsedTime;
    }

    public double getLastLoop() {
        return lastLoop;
    }
}
