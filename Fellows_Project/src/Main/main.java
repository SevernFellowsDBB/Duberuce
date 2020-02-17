package Main;

import gameEngine.driver;

public class main {
    
    public static void main(String[] not_args){
        try{
            boolean vSync = true;
            driver gameDriver = new driver("MC", 1000, 800, vSync);
            gameDriver.run();
        }
        catch (Exception e){

        }
    }
}
