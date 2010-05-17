import java.awt.Point;

import javax.swing.JOptionPane;

class Hero extends Creature {
    
    public Hero(Point point, String img, int strength, int agility, int endurance) 
    {
        super(point, img, strength, agility, endurance);
        this.str = strength;
        this.agi = agility;
        this.end = endurance;
    }

    public boolean fight(Creature opponent) {
        boolean dies = this.Strike(opponent);
        if(dies) {
        	gainExp(opponent.lvl);
        }
        return true;
    }

    public void act(int key) {
        // kato mihin suuntaan
        Point dest = this.getDestinationPoint(key);
        if (dest==null) {
        	return;
        }
        if(!this.isEmpty(dest)){
        	return;
        }
        // pahis kohteessa?
        Creature villain = this.map.getCell(dest).getCreature();
        if (villain != null) {
            // tappelu
            this.fight(villain);
        } else {
            // koitetaan liikkua kohteeseen
            this.move(dest);
        }
    }
    protected void died(){
    	JOptionPane.showMessageDialog(null, "You and all your friends are dead. You suck... big time. GAME OVER!");
    	System.exit(-1);
    }
}
