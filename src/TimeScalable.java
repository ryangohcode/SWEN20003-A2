import bagel.Input;
import bagel.Keys;

public interface TimeScalable{
    int MINIMUMTIMESCALE = 1;
    int MAXIMUMTIMESCALE = 5;
    int DEFAULTTIMESCALE = 1;
    int DEFAULTSPEED = 5; //originally 3 in specs
    double TIMESCALEMULTIPLIER = 1.5;

    /**
     * This method changes the timeScale accordingly if the appropriate button is pressed
     * @param input This is the first parameter of the method which detects keys pressed
     * @param timescale This is the second parameter of the method which is the current timescale
     * @return This returns the timescale after the operation
     */
    default int scale(Input input, int timescale){
        if (input.wasPressed(Keys.K)&&timescale>MINIMUMTIMESCALE){
            //decrease
            timescale-=1;
        }
        if(input.wasPressed(Keys.L)&&timescale<MAXIMUMTIMESCALE){
            //increase
            timescale+=1;
        }
        return timescale;
    }

    /**
     * This method controls the speed of the object given the timescale
     * @param timescale This is the first parameter of the method
     * @return returns the new speed
     */
    default int controlSpeed(int timescale){
        int speed;
        if(timescale==DEFAULTTIMESCALE){
            speed = DEFAULTSPEED;
        }
        else {
            speed=(int)(DEFAULTSPEED*(timescale-1)*TIMESCALEMULTIPLIER);
        }
        return speed;
    }
}
