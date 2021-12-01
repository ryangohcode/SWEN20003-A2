import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.util.Rectangle;
import java.util.Random;

public abstract class AbstractWeapons implements TimeScalable{
    private final Image WEAPON_IMAGE;
    private final int RANDOMROLL = 400;
    private final int RANDOMSPAWNYMIN = 100;
    private final int THROWSPEED = 5;

    private double offscreen;
    private double weaponX;
    private double weaponY;
    private Random rand;
    protected int shootingRange;
    private int shootingCounter = 0;
    private boolean isPicked = false;
    private boolean isThrown = false;
    private static int timescale = 1;
    private static int weaponSpeed = 5;

    /**
     * Constructor initialises weapon image, location, and random type;
     * @param weaponImage This is the first parameter of the method which is the image from the subclasses
     */
    public AbstractWeapons(Image weaponImage){
        this.WEAPON_IMAGE = weaponImage;
        rand = new Random();
        offscreen = -WEAPON_IMAGE.getWidth();
        weaponX = Window.getWidth();
        weaponY = rand.nextInt(RANDOMROLL)+RANDOMSPAWNYMIN;
    }

    /**
     * This method updates the position of the weapon
     */
    public void update(){
        WEAPON_IMAGE.draw(weaponX,weaponY);
        if(!isPicked){
            weaponX -= weaponSpeed;;
        }
        if(isThrown){
            weaponX+=THROWSPEED;
            if(shootingCounter>=shootingRange){
                //make weapon disappear
                weaponX = offscreen;
            }
            shootingCounter+=1;
        }
    }

    /**
     * This method makes the bird hold the weapon
     * @param bird This is the first parameter of the method which is the bird
     */
    public void holdWeapon(Bird bird){
        isPicked = true;
        weaponX = bird.getBox().right();
        weaponY = bird.getY();
    }

    /**
     * This method sets isThrown attribute to true
     */
    public void throwWeapon(){
        isThrown = true;
    }


    /**
     * This method returns the hitbox of the weapon
     * @return returns the Rectange of the weapon
     */
    public Rectangle getBox(){
        return WEAPON_IMAGE.getBoundingBoxAt(new bagel.util.Point(weaponX,weaponY));
    }

    /**
     * This method gets the attribute isPicked
     * @return returns isPicked
     */
    public boolean getIsPicked(){
        return isPicked;
    }

    /**
     * This method gets the attribute isThrown
     * @return returns isThrown
     */
    public boolean getIsThrown(){
        return isThrown;
    }

    /**
     * This abstract method is to be implemented by subclasses
     */
    public abstract boolean isPipeBreakable(AbstractPipeSet pipe);


    /**
     * This method sets the weapon timescale
     * @param timescale This is the first parameter of the method
     */
    public void setWeapontimescale(int timescale){
        this.timescale = timescale;
        this.weaponSpeed = controlSpeed(this.timescale);
    }
}
