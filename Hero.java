import java.awt.Point;

class Hero extends Creature {
    
    public Hero(Point point, String img) 
    {
        super(point, img);
    }

    public void fight() {
    }

    public void act(int key) {
        // kato mihin suuntaan
        Point dest = this.getDestinationPoint(key);

        // pahis kohteessa?
        Creature villain = this.map.getCell(dest).getCreature();
        if (villain != null) {
            // tappelu
            this.fight();
            return;
        }
        
        // koitetaan liikkua kohteeseen
        this.move(dest);
    }
}
