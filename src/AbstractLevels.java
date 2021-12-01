import bagel.Font;
import bagel.Input;
import bagel.Window;
import bagel.util.Rectangle;
import java.util.ArrayList;

public abstract class AbstractLevels {
    private final int FONT_SIZE = 48;
    private final int DEFAULTSPAWNPIPEFRAME = 100;
    private double TIMESCALEMULTIPLIER = 1.5;
    protected final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    protected final String SCORE_MSG = "SCORE: ";
    protected final int SCORECOORDINATES = 100;

    protected int timescale = 1;
    protected LifeBar lifeBar;
    protected int score;
    protected ArrayList pipeList;
    protected int pipeSpawnFrame;
    protected int pipeCounter;
    protected Bird bird;

    /**
     * This constructor sets attributes and initialises pipeList
     */
    public AbstractLevels(){
        pipeSpawnFrame = DEFAULTSPAWNPIPEFRAME;
        pipeCounter = pipeSpawnFrame;
        pipeList = new ArrayList();
        score = 0;
    }

    /**
     * This abstract method is to be implemented by subclasses
     * This method updates elements of the game
     * @param input This is the first parameter of the method
     * @return Returns the state of the game to determine if it is game over, or this stage is won, or if it is still level zero
     */
    public abstract int update(Input input);

    /**
     * This abstract method is to be implemented by subclasses
     * This method controls the scoring of the game
     * @return Returns the state of the game to determine if this stage is won, or if it is still level zero
     */
    public abstract int updateScore();

    /**
     * This abstract method is to be implemented by subclasses
     * This method controls most of the game's logic such as the collision and scoring
     * @return Returns the state of the game to determine if it is game over, or this stage is won, or if it is still level zero
     */
    public abstract int gameLogic();

    /**
     * This method detects any collision the bird might have
     * @param birdBox This is the first parameter of the method
     * @param topPipeBox This is the second parameter of the method
     * @param bottomPipeBox This is the third parameter of the method
     * @param topFlameBox This is the forth parameter of the method
     * @param bottomFlameBox This is the fifth parameter of the method
     * @return returns true if it has collided with any of the other 4 parameters and false if otherwise
     */
    public boolean detectCollision(Rectangle birdBox, Rectangle topPipeBox, Rectangle bottomPipeBox, Rectangle topFlameBox, Rectangle bottomFlameBox) {
        return birdBox.intersects(topPipeBox) || birdBox.intersects(bottomPipeBox) || birdBox.intersects(topFlameBox) || birdBox.intersects(bottomFlameBox);
    }

    /**
     * This method detects if the bird has fallen out of the screen
     * @param bird This is the first parameter of the method
     * @return Returns true if it has exited the screen, false if otherwise
     */
    public boolean birdOutOfBound(Bird bird) {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    /**
     * This method updates the spawn speed of the pipes
     */
    public void updatePipeSpawnSpeed(){
        if(!pipeList.isEmpty()){
            timescale = ((AbstractPipeSet) pipeList.get(0)).getTimeScale();
            if(timescale==((AbstractPipeSet) pipeList.get(0)).getDefaultTimescale()){
                pipeSpawnFrame = DEFAULTSPAWNPIPEFRAME;
            }
            else {
                pipeSpawnFrame=(int)(DEFAULTSPAWNPIPEFRAME/((timescale-1)*((AbstractPipeSet) pipeList.get(0)).TIMESCALEMULTIPLIER));
            }
        }
    }

    /**
     * This method gets the score
     * @return returns the attribute score
     */
    public int getScore(){return score;}
}
