import java.awt.Point;

class Hero extends Creature {
    
    public Hero(Point point, String img, int strength, int agility, int endurance) 
    {
        super(point, img);
        this.str = strength;
        this.agi = agility;
        this.end = endurance;
        
        this.dead = false;
    }

    public void fight(Creature opponent) {
        this.Strike(opponent);
        opponent.Dies();
    }

    public void act(int key) {
        // kato mihin suuntaan
        Point dest = this.getDestinationPoint(key);

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
