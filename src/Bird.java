import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

public class Bird {
    private final double X = 200;
    private final double FLY_SIZE = 6;
    private final double FALL_SIZE = 0.4;
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 10;
    private final double SWITCH_FRAME = 10;
    private int frameCount = 0;
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;
    private final Image WING_DOWN_IMAGE;
    private final Image WING_UP_IMAGE;
    private boolean isHolding;

    /**
     * This constructor initialises the velocity and position of the bird, as well as the wing down and wing up image.
     * It also initialises the hit box of the bird
     * @param wingUpImage This is the first parameter of the method which is the wing up image
     * @param wingDownImage This is the second parameter of the method which is the wing down image
     */
    public Bird(Image wingUpImage, Image wingDownImage) {
        this.WING_DOWN_IMAGE = wingDownImage;
        this.WING_UP_IMAGE = wingUpImage;
        isHolding = false;
        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
    }

    /**
     * This method controls the movement and flapping of the bird
     * @param input This is the first parameter of the method
     * @return Returns the hit box of the bird
     */
    public Rectangle update(Input input) {
        frameCount += 1;
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = -FLY_SIZE;
            WING_DOWN_IMAGE.draw(X, y);
        }
        else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
            if (frameCount % SWITCH_FRAME == 0) {
                WING_UP_IMAGE.draw(X, y);
                boundingBox = WING_UP_IMAGE.getBoundingBoxAt(new Point(X, y));
            }
            else {
                WING_DOWN_IMAGE.draw(X, y);
                boundingBox = WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
            }
        }
        y += yVelocity;

        return boundingBox;
    }

    /**
     * This method returns the y position of the bird
     * @return Returns the y position of the bird
     */
    public double getY() {
        return y;
    }

    /**
     * This method returns the x position of the bird
     * @return Returns the x position of the bird
     */
    public double getX() {
        return X;
    }

    /**
     * This method returns the hitbox of the bird
     * @return Returns the hitbox of the bird
     */
    public Rectangle getBox() {
        return boundingBox;
    }

    /**
     * This method returns true if the bird is holding an item, false if otherwise
     * @return Returns true if the bird is holding an item, false if otherwise
     */
    public boolean getIsHolding(){
        return isHolding;
    }

    /**
     * This method sets the attribute isHolding
     * @param isHolding This is the first parameter of the method which sets the attribute isHolding
     */
    public void setIsHolding(boolean isHolding){
        this.isHolding = isHolding;
    }

    /**
     * This method respawns the bird
     */
    public void respawnBird(){
        y = INITIAL_Y;
        yVelocity = 0;
    }
}