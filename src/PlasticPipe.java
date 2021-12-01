import bagel.Image;
import bagel.Window;
import java.util.Random;

public class PlasticPipe extends AbstractPipeSet{
    private final static Image PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");
    private final int PIPEDIFFERENCE = 200;
    private final int RANDOMROLL = 3;
    private final int LEVELZEROGAMESTAGE = 1;
    private int randomResult;
    private Random rand;

    /**
     * This constructor sends the image of the plastic pipe and the stage parameter to the superclass
     * if the stage of the game is level zero, it will randomly produce either a low pipe, mid pipe, or a high pipe.
     * @param stage This is the first parameter of the method which determines what type of pipe will be produced
     */
    public PlasticPipe(int stage){
        super(PLASTIC_PIPE_IMAGE, stage);
        if(stage==LEVELZEROGAMESTAGE){
            rand = new Random();
            randomResult=rand.nextInt(RANDOMROLL);
            if(randomResult==1){
                setTopPipeY(-getPipeGap() / 2.0);
                setBottomPipeY(Window.getHeight() + getPipeGap() / 2.0);
            }
            if(randomResult==0){
                setTopPipeY((-getPipeGap() / 2.0)+PIPEDIFFERENCE);
                setBottomPipeY((Window.getHeight() + getPipeGap() / 2.0)+PIPEDIFFERENCE);
            }
            if(randomResult==2){
                setTopPipeY((-getPipeGap() / 2.0)-PIPEDIFFERENCE);
                setBottomPipeY((Window.getHeight() + getPipeGap() / 2.0)-PIPEDIFFERENCE);
            }
        }
    }

}
