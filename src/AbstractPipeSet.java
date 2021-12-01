import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

public abstract class AbstractPipeSet implements TimeScalable {
    private final Image PIPE_IMAGE;
    private final int LEVELONEGAMESTAGE = 4;
    private final int PIPE_GAP = 168;
    private final int RANDOMROLL = 400;
    protected final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private double pipeX = Window.getWidth();
    private boolean passedCheck = false;
    private double topPipeY;
    private double bottomPipeY;

    private static int timescale = 1;
    private static int pipeSpeed = 5;
    private int randomResult;
    private Random rand;

    /**
     * This constructor sets the image of the pipe
     * This constructor makes the pipe spawn at random heights if the game stage is level one
     * @param pipeImage This is the first parameter of the method which sets the image of the pipe from the subclass
     * @param stage This is the second parameter of the method
     */
    public AbstractPipeSet(Image pipeImage, int stage){
        this.PIPE_IMAGE = pipeImage;
        if(stage==LEVELONEGAMESTAGE){
            //spawn randomly
            rand = new Random();
            randomResult = rand.nextInt(RANDOMROLL)-RANDOMROLL/2;
            setTopPipeY((-getPipeGap() / 2.0) + randomResult);
            setBottomPipeY((Window.getHeight() + getPipeGap() / 2.0) + randomResult);
        }
    }

    /**
     * This method draws the pipes
     */
    public void renderPipeSet() {
        PIPE_IMAGE.draw(pipeX, topPipeY);
        PIPE_IMAGE.draw(pipeX, bottomPipeY, ROTATOR);
    }

    /**
     * This method updates the location of the pipes
     */
    public void update() {
        renderPipeSet();
        pipeX -= pipeSpeed;
    }

    /**
     * This method controls the speed of the pipe
     * @param input This is the first parameter of the method
     */
    public void pipeSpeedControl(Input input){
        timescale = scale(input,timescale);
        pipeSpeed = controlSpeed(timescale);
    }

    /**
     * This method returns the hit box of the top pipe
     * @return Returns the hit box of the top pipe
     */
    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, topPipeY));
    }

    /**
     * This method returns the hit box of the bottom pipe
     * @return Returns the hit box of the bottom pipe
     */
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, bottomPipeY));
    }

    /**
     * gets the attribute passedCheck
     * @return Returns the attribute passedCheck
     */
    public boolean isPassedCheck() {
        return passedCheck;
    }

    /**
     * Sets the attribute passedCheck
     * @param passedCheck This is the first parameter of the method which sets passedCheck
     */
    public void setPassedCheck(boolean passedCheck) {
        this.passedCheck = passedCheck;
    }

    /**
     * Gets the attribute PIPE_GAP
     * @return returns the attribute PIPE_GAP
     */
    public int getPipeGap() {
        return PIPE_GAP;
    }

    /**
     * Sets the attribute topPipeY
     * @param topPipeY returns the attribute topPipeY
     */
    public void setTopPipeY(double topPipeY){
        this.topPipeY = topPipeY;
    }

    /**
     * Sets the attribute bottomPipeY
     * @param bottomPipeY returns the attribute bottomPipeY
     */
    public void setBottomPipeY(double bottomPipeY){
        this.bottomPipeY = bottomPipeY;
    }

    /**
     * Gets the attribute timescale
     * @return returns the attribute timescale
     */
    public int getTimeScale(){
        return timescale;
    }

    /**
     * Gets the attribute DEFAULTTIMESCALE
     * @return returns the attribute DEFAULTTIMESCALE
     */
    public int getDefaultTimescale(){
        return DEFAULTTIMESCALE;
    }
}
