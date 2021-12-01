import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;

public class LevelZero extends AbstractLevels {
    private final Image WING_DOWN_IMAGE = new Image("res/level-0/birdWingDown.png");
    private final Image WING_UP_IMAGE = new Image("res/level-0/birdWingUp.png");
    private final int LEVEL0MAXSCORE =  10;
    private final int INITIALHEALTH = 3;
    private final int CURRENTGAMESTAGE = 1;
    private final int GAMEOVERSTAGE = -1;
    private final int NEXTSTAGE = 2;

    /**
     * This constructor initialises a new bird and lifebar
     */
    public LevelZero(){
        bird = new Bird(WING_UP_IMAGE, WING_DOWN_IMAGE);
        lifeBar = new LifeBar(INITIALHEALTH);
    }

    /**
     * This method updates the state of the game in level zero
     * @param input This is the first parameter of the method
     * @return Returns the state of the game to determine if it is game over, or this stage is won, or if it is still level zero
     */
    @Override
    public int update(Input input){
        //update items
        bird.update(input);
        updatePipe(input);
        lifeBar.update();
        return gameLogic();
    }

    /**
     * This method controls most of the game's logic such as the collision and scoring
     * @return Returns the state of the game to determine if it is game over, or this stage is won, or if it is still level zero
     */
    @Override
    public int gameLogic(){
        if(!pipeList.isEmpty()) {
            Rectangle birdBox = bird.getBox();
            Rectangle topPipeBox = ((AbstractPipeSet) pipeList.get(0)).getTopBox();
            Rectangle bottomPipeBox = ((AbstractPipeSet) pipeList.get(0)).getBottomBox();
            if(detectCollision(birdBox, topPipeBox, bottomPipeBox, new Rectangle(0,0,0,0), new Rectangle(0,0,0,0))||birdOutOfBound(bird)) {
                if(birdOutOfBound(bird)){
                    bird.respawnBird();
                }
                if(detectCollision(birdBox, topPipeBox, bottomPipeBox, new Rectangle(0,0,0,0), new Rectangle(0,0,0,0))){
                    pipeList.remove(0);
                }
                if (!lifeBar.minusHealth()) {
                    return GAMEOVERSTAGE;
                }
            }
            return (updateScore());
        }
        return CURRENTGAMESTAGE;
    }

    /**
     * This method controls the scoring of the game
     * @return Returns the state of the game to determine if this stage is won, or if it is still level zero
     */
    @Override
    public int updateScore() {
        if (bird.getX() > ((AbstractPipeSet) pipeList.get(0)).getTopBox().right() && !((AbstractPipeSet) pipeList.get(0)).isPassedCheck()) {
            ((AbstractPipeSet) pipeList.get(0)).setPassedCheck(true);
            score += 1;
        }
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, SCORECOORDINATES, SCORECOORDINATES);

        // detect win
        if (score == LEVEL0MAXSCORE) {
            //set gamestage to 2
            return NEXTSTAGE;
        }
        return CURRENTGAMESTAGE;
    }

    /**
     * This method updates the pipes
     * @param input This is the first parameter of the method
     */
    public void updatePipe(Input input){
        //update pipe
        for(int i=0; i<pipeList.size(); i++){
            ((AbstractPipeSet) pipeList.get(i)).update();
        }
        super.updatePipeSpawnSpeed();
        if(pipeCounter%pipeSpawnFrame==0){
            pipeList.add(new PlasticPipe(CURRENTGAMESTAGE));
        }
        if(!pipeList.isEmpty()) {
            //if pipe goes beyond screen, delete from list
            if (((AbstractPipeSet) pipeList.get(0)).getTopBox().right() <= 0) {
                pipeList.remove(0);
            }
            ((AbstractPipeSet) pipeList.get(0)).pipeSpeedControl(input);
        }
        pipeCounter+=1;
    }
}
