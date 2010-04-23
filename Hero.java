import java.awt.Point;

class Hero extends Creature {
    
    public Hero(Point point, String img, int strength, int agility, int endurance) 
    {
        super(point, img, strength, agility, endurance);
        this.str = strength;
        this.agi = agility;
        this.end = endurance;
        
        this.dead = false;
    }

    public boolean fight(Creature opponent) {
        this.Strike(opponent);
        if(opponent.Dies())
        {
        	this.gainExp(opponent.lvl);
        }
		return true;
    }

    public void act(int key) {
        // kato mihin suuntaan
        Point dest = this.getDestinationPoint(key);
        
        //Jos painettu jotain muuta nappia, ei tehd‰ vuoron aikana mit‰‰n
        if (dest==null) {
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
}
