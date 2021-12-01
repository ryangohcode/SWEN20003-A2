import bagel.Image;

public class Rock extends AbstractWeapons{
    private final static Image ROCKIMAGE = new Image("res/level-1/rock.png");
    private final int ROCKRANGE = 25;

    /**
     * This is the rock constructor which sends the image of the rock to the superclass and sets the shootingrange of the superclass
     */
    public Rock() {
        super(ROCKIMAGE);
        shootingRange = ROCKRANGE;
    }

    /**
     * This method determines if the pipe can be broken by the rock
     * @param pipe This is the first parameter of the method
     * @return Returns true if the pipe input can be broken by a rock and false if otherwise
     */
    @Override
    public boolean isPipeBreakable(AbstractPipeSet pipe){
        if(pipe instanceof PlasticPipe){
            return true;
        }
        return false;
    }
}
