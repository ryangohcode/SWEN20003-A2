import bagel.Image;

public class LifeBar {
    private final Image FULLHEART = new Image("res/level/fullLife.png");
    private final Image EMPTYHEART = new Image("res/level/noLife.png");
    private final int HEARTPOSITIONY = 15;
    private final int HEARTPOSITIONX = 100;
    private final int HEARTSPACING = 50;
    private int initialHealth;
    private int currentHealth;

    /**
     * This constructor sets the initial health and the current health of the life bar
     * @param initialHealth This is the first parameter of the method
     */
    public LifeBar(int initialHealth){
        currentHealth = initialHealth;
        this.initialHealth = initialHealth;
    }

    /**
     * This method draws the health bar based on the attribute currentHealth
     */
    public void update(){
        for(int i=0; i<currentHealth; i++){
            FULLHEART.drawFromTopLeft(HEARTPOSITIONX+i*HEARTSPACING, HEARTPOSITIONY);
        }
        for(int i=0; i<(initialHealth-currentHealth); i++){
            EMPTYHEART.drawFromTopLeft(HEARTPOSITIONX+i*HEARTSPACING+currentHealth*HEARTSPACING, HEARTPOSITIONY);
        }
    }

    /**
     * This method controls the state of the health
     * @return Returns false if there is no more heath, Returns true if there is still health
     */
    public boolean minusHealth(){
        currentHealth-=1;
        if(currentHealth==0){
            return false;
        }
        return true;
    }
}
