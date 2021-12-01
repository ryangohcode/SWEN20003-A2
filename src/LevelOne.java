import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class LevelOne extends AbstractLevels {
    private final Image WING_DOWN_IMAGE = new Image("res/level-1/birdWingDown.png");
    private final Image WING_UP_IMAGE = new Image("res/level-1/birdWingUp.png");
    private final int LEVEL0MAXSCORE =  30;
    private final int INITIALHEALTH = 6;
    private final int RANDOMPIPETYPEROLL = 2;
    private final int RANDOMWEAPONTYPEROLL = 2;
    private final int CURRENTSTAGE = 4;
    private final int WINSTAGE = 5;
    private final int GAMEOVERSTAGE = 6;

    private Random random;
    private int randomPipeType;
    private int randomWeaponType;
    private boolean collisionResult = false;
    private ArrayList weaponList;

    /**
     * This constructor creates a bird, a lifeBar, an Arraylist for weapons, and a random type
     */
    public LevelOne(){
        bird = new Bird(WING_UP_IMAGE, WING_DOWN_IMAGE);
        lifeBar = new LifeBar(INITIALHEALTH);
        random = new Random();
        weaponList = new ArrayList();
    }

    /**
     * This function updates the different game elements
     * @param input This is the first parameter of the method
     * @return  Returns the state of the game to determine if it is game over, or this stage is won, or if it is still level one
     */
    @Override
    public int update(Input input){
        //update items
        bird.update(input);
        updateWeapon(input);
        updatePipe(input);
        lifeBar.update();
        return gameLogic();
    }

    /**
     * This method controls most of the game's logic such as the collision and scoring
     * @return Returns the state of the game to determine if it is game over, or this stage is won, or if it is still level one
     */
    @Override
    public int gameLogic(){
        if(!pipeList.isEmpty()){
            Rectangle birdBox = bird.getBox();
            Rectangle topPipeBox = ((AbstractPipeSet) pipeList.get(0)).getTopBox();
            Rectangle bottomPipeBox = ((AbstractPipeSet) pipeList.get(0)).getBottomBox();

            //collision logic
            if(pipeList.get(0) instanceof MetalPipe && ((MetalPipe) pipeList.get(0)).isFlameOnBoolean()){
                Rectangle topFlameBox = ((MetalPipe) pipeList.get(0)).getTopFlameBox();
                Rectangle bottomFlameBox = ((MetalPipe) pipeList.get(0)).getBottomFlameBox();
                collisionResult = detectCollision(birdBox, topPipeBox, bottomPipeBox, topFlameBox, bottomFlameBox);
            }
            else{
                collisionResult = detectCollision(birdBox, topPipeBox, bottomPipeBox, new Rectangle(0,0,0,0), new Rectangle(0,0,0,0));
            }

            if(collisionResult||birdOutOfBound(bird)) {
                if(birdOutOfBound(bird)){
                    bird.respawnBird();
                }
                if(collisionResult){
                    pipeList.remove(0);
                }
                if (!lifeBar.minusHealth()) {
                    return GAMEOVERSTAGE;
                }
            }
            return (updateScore());
        }
        return CURRENTSTAGE;
    }

    /**
     * This method controls the scoring of the game
     * @return Returns the state of the game to determine if this stage is won, or if it is still level one
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
            return WINSTAGE;
        }
        return CURRENTSTAGE;
    }

    /**
     * This method updates the pipes
     * @param input This is the first parameter of the method
     */
    public void updatePipe(Input input){
        //update pipe
        for(int i=0; i<pipeList.size(); i++){
            ((AbstractPipeSet) pipeList.get(i)).update();
            if(pipeList.get(i) instanceof MetalPipe){
                ((MetalPipe) pipeList.get(i)).flameUpdate();
            }

            //if weapon breaks pipe logic
            if(!weaponList.isEmpty()){
                if( ((AbstractWeapons)weaponList.get(0)).getIsThrown()){
                    if((((AbstractWeapons)weaponList.get(0)).getBox().intersects(((AbstractPipeSet)pipeList.get(i)).getTopBox())||
                            ((AbstractWeapons)weaponList.get(0)).getBox().intersects(((AbstractPipeSet)pipeList.get(i)).getBottomBox()))){
                        if(((AbstractWeapons)weaponList.get(0)).isPipeBreakable((AbstractPipeSet)pipeList.get(i))){
                            score += 1;
                            pipeList.remove(i);
                        }
                        //remove weapon
                        weaponList.remove(0);
                    }
                }
            }
        }
        super.updatePipeSpawnSpeed();
        if(pipeCounter%pipeSpawnFrame==0){
            randomPipeType = random.nextInt(RANDOMPIPETYPEROLL);
            if(randomPipeType==0){
                pipeList.add(new PlasticPipe(CURRENTSTAGE));
            }
            else {
                pipeList.add(new MetalPipe());
            }
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

    /**
     * This method controls thr throwing of the held weapon
     * @param input This is the first parameter of the method
     * @param heldWeapon This is the second parameter of the method and throws the input weapon
     */
    public void throwable(Input input, AbstractWeapons heldWeapon){
        if(input.wasPressed(Keys.S)){
            bird.setIsHolding(false);
            heldWeapon.throwWeapon();
        }
    }

    /**
     * This method updates the weapons
     * @param input This is the first parameter of the method
     */
    public void updateWeapon(Input input){
        //update spawn rate
        if((pipeCounter+(pipeSpawnFrame/2))%(pipeSpawnFrame*2)==0) { //spawned once for every two pipes spawned as seen in demo
            randomWeaponType = random.nextInt(RANDOMWEAPONTYPEROLL);
            if(randomWeaponType==0){
                weaponList.add(new Bomb());
            }else {
                weaponList.add(new Rock());
            }
        }
        //control speed
        if(!weaponList.isEmpty()){
            ((AbstractWeapons) weaponList.get(0)).setWeapontimescale(super.timescale);
        }
        //pick item
        if(!weaponList.isEmpty()&&!bird.getIsHolding()){
            if(((AbstractWeapons) weaponList.get(0)).getBox().intersects(bird.getBox())&&!((AbstractWeapons)weaponList.get(0)).getIsPicked()){
                bird.setIsHolding(true);
            }
        }
        //hold item
        if(bird.getIsHolding()){
            ((AbstractWeapons) weaponList.get(0)).holdWeapon(bird);
            throwable(input,(AbstractWeapons) weaponList.get(0));
        }
        //update all the weapons
        for(int i=0; i<weaponList.size(); i++){
            ((AbstractWeapons)weaponList.get(i)).update();
            //delete from list if weapon goes off-screen
            if (((AbstractWeapons) weaponList.get(i)).getBox().right() <= 0) {
                weaponList.remove(i);
            }
        }
    }
}
