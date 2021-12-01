import bagel.Image;

public class Bomb extends AbstractWeapons{
    private final static Image BOMBIMAGE = new Image("res/level-1/bomb.png");
    private final int BOMBRANGE = 50;

    /**
     * This constructor sends the image of the bomb to the superclass and sets the shooting range of the superclass
     */
    public Bomb() {
        super(BOMBIMAGE);
        shootingRange = BOMBRANGE;
    }

    /**
     * This method returns if the pipe can be breakable by the bomb. Since bomb can break all types of pipes, it is set to true
     * @param pipe This is the first parameter of the method which is any pipe type
     * @return Returns true
     */
    @Override
    public boolean isPipeBreakable(AbstractPipeSet pipe) {
        return true;
    }
}
