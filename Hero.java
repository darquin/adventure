import java.awt.Point;

class Hero extends Creature {
    
	Creature target;
    public Hero(Point point, String img, int strength, int agility, int endurance) 
    {
        super(point, img);
        this.str = strength;
        this.agi = agility;
        this.end = endurance;
        
        this.dead = false;
    }

    public void fight() {
    	this.Strike(target);
    	target.Dies();
    }

    public void act(int key) {
        // kato mihin suuntaan
        Point dest = this.getDestinationPoint(key);

        // pahis kohteessa?
        Creature villain = this.map.getCell(dest).getCreature();
        if (villain != null) {
            // tappelu
        	target = this.map.getCell(dest).getCreature();
        	this.fight();
            return;
        }
        
        // koitetaan liikkua kohteeseen
        this.move(dest);
    }
}
