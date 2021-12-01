import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class MetalPipe extends AbstractPipeSet{
    private final static Image METAL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
    private final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private final static int GAMESTAGE = 4;
    private final int FLAMEOFF = 20; //according to piazza thread
    private final int FLAMEON = 17;
    private final int FLAMEOFFSET = 10;
    private int counter;
    private boolean flameOnBoolean;

    /**
     * This constructor sends the image of a metal pipe and the game stage to the superclass and initialises the counter to zero
     */
    public MetalPipe(){
        super(METAL_PIPE_IMAGE,GAMESTAGE);
        counter=0;
    }

    /**
     * This method controls the flickering of the flame on the metal pipes
     */
    public void flameUpdate(){
        counter+=1;
        if(counter>FLAMEON){
            flameOnBoolean = true;
            FLAME_IMAGE.draw(getBottomBox().left()+FLAME_IMAGE.getWidth()/2,getBottomBox().top()-FLAME_IMAGE.getHeight()/2+FLAMEOFFSET,ROTATOR);
            FLAME_IMAGE.draw(getTopBox().left()+FLAME_IMAGE.getWidth()/2,getTopBox().bottom()+FLAME_IMAGE.getHeight()/2-FLAMEOFFSET);
            if (counter==FLAMEOFF){
                counter=0;
            }
        }
        else{
            flameOnBoolean = false;
        }
    }

    /**
     * This method returns the hitbox of the bottom flame
     * @return Returns the rectange of the flame
     */
    public Rectangle getBottomFlameBox(){
        return FLAME_IMAGE.getBoundingBoxAt(new Point(getBottomBox().left()+FLAME_IMAGE.getWidth()/2,getBottomBox().top()-FLAME_IMAGE.getHeight()/2+FLAMEOFFSET));
    }

    /**
     * This method returns the hitbox of the top flame
     * @return Returns the rectange of the flame
     */
    public Rectangle getTopFlameBox(){
        return FLAME_IMAGE.getBoundingBoxAt(new Point(getTopBox().left()+FLAME_IMAGE.getWidth()/2,getTopBox().bottom()+FLAME_IMAGE.getHeight()/2-FLAMEOFFSET));
    }

    /**
     * Gets the state of the attribute flameOnBoolean
     * @return Returns the state of the attribute flameOnBoolean
     */
    public boolean isFlameOnBoolean() {
        return flameOnBoolean;
    }
}
