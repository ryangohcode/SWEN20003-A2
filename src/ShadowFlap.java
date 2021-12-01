import bagel.*;
/**
 * SWEN20003 Project 1, Semester 2, 2021
 *
 * @author Ryan Goh
 */
public class ShadowFlap extends AbstractGame {
    private final Image BACKGROUND_IMAGE_ZERO = new Image("res/level-0/background.png");
    private final Image BACKGROUND_IMAGE_ONE = new Image("res/level-1/background.png");
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final String LEVELUP_MSG = "LEVEL-UP!";
    private final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SCORE_MSG_OFFSET = 75;
    private final int LEVELUPFRAMEEND = 20;
    private final int SHOOTMSGOFFSET = 68;

    //game stages
    private final int GAMEOVERSTAGE = -1;
    private final int INSTRUCTIONSTAGE = 0;
    private final int LEVELZEROSTAGE = 1;
    private final int LEVELUPSTAGE = 2;
    private final int LEVELONEINSTRUCTIONSTAGE = 3;
    private final int LEVELONESTAGE = 4;
    private final int WINSTAGE = 5;
    private final int LEVELONEGAMEOVERSTAGE = 6;

    private int gameStage;
    private int frameCount;
    private LevelZero levelZero;
    private LevelOne levelOne;

    /**
     * Constructor for ShadowFlap
     * Initialises levelZero, levelOne, gameStage and frameCount
     */
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        levelZero = new LevelZero();
        levelOne = new LevelOne();
        gameStage = INSTRUCTIONSTAGE;
        frameCount = 0;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     * changes the state of the game
     * @param input This is the first parameter of the method
     */
    @Override
    public void update(Input input) {
        //set background
        if(gameStage<=LEVELUPSTAGE){
            BACKGROUND_IMAGE_ZERO.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        }
        else {
            BACKGROUND_IMAGE_ONE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        }

        //escape game
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // game has not started
        if (gameStage==INSTRUCTIONSTAGE) {
            renderInstructionScreen(input);
        }

        // game over
        if (gameStage==GAMEOVERSTAGE) {
            renderGameOverScreen(levelZero.getScore());
        }

        // Level-0
        if (gameStage==LEVELZEROSTAGE) {
            gameStage = levelZero.update(input);
        }

        // Leveled up
        if (gameStage==LEVELUPSTAGE) {
            renderLevelUpScreen();
            frameCount+=1;
            if(frameCount==LEVELUPFRAMEEND){
                gameStage=LEVELONEINSTRUCTIONSTAGE;
            }
        }

        // Level-1 Instruction
        if (gameStage==LEVELONEINSTRUCTIONSTAGE) {
            renderLevelOneInstructionScreen(input);
        }

        // Level-1
        if (gameStage==LEVELONESTAGE) {
            gameStage = levelOne.update(input);
        }

        // game won
        if (gameStage==WINSTAGE) {
            renderWinScreen(levelOne.getScore());
        }

        if (gameStage==LEVELONEGAMEOVERSTAGE){
            renderGameOverScreen(levelOne.getScore());
        }
    }

    /**
     * This method draws the instruction message on the screen and changes the gameStage to LEVELZERO if space is pressed
     * @param input This is the first parameter of the method
     */
    public void renderInstructionScreen(Input input) {
        // paint the instruction on screen
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (input.wasPressed(Keys.SPACE)) {
            // do level 0
            gameStage = LEVELZEROSTAGE;
        }
    }

    /**
     * This method draws game over followed by the score achieved
     * @param score This is the first parameter of the method which is the score printed
     */
    public void renderGameOverScreen(int score) {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /**
     * This method draws the congrats message followed by the score achieved
     * @param score This is the first parameter of the method which is the score printed
     */
    public void renderWinScreen(int score) {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /**
     * This method draws the level up message
     */
    public void renderLevelUpScreen() {
        FONT.drawString(LEVELUP_MSG, (Window.getWidth()/2.0-(FONT.getWidth(LEVELUP_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    /**
     * This method draws the instruction screen for level one stage
     * @param input This is the first parameter of the method
     */
    public void renderLevelOneInstructionScreen(Input input){
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        FONT.drawString(SHOOT_MSG, (Window.getWidth()/2.0-(FONT.getWidth(SHOOT_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SHOOTMSGOFFSET);
        if (input.wasPressed(Keys.SPACE)) {
            // do level 0
            gameStage = LEVELONESTAGE;
        }
    }

}
