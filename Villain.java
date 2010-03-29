
import java.awt.Point;
import java.util.Random;
import java.lang.Math;

class Villain extends Creature {
    
    private static Creature hero;

    private String alignment;

    private int sightradius = 5;

    protected Creature target;

    public Villain(int x, int y, String img) {
        super(x, y, img);
        this.alignment = "neutral";
        this.Name = "Enemy";
    }

    public void act()
    {
        this.fight();

        Point dest;

        // hidastus
        dest = this.chase();
        if (dest == null) {
            dest = this.wander();
        }

        this.move(dest);
    }

    public void fight() {
        if (this.getTarget() == null) {
            return;
        }

        if (this.isInFightRange(this.getTarget()) && this.getAlignment() != "peaceful") {
            System.out.println(this.getName()+" is ATTACKING " + this.getTarget().getName()+".");

            ////If target is neutral it becomes aggressive
            //if (this.getTarget().getAlignment()=="neutral") {
            //    this.getTarget().setAlignment("aggressive");
            //    //this.getTarget().setTarget(this.;
            //    System.out.println(this.getTarget().getName()+" is now aggressive!");
            //}
            ////The same applies to attacker
            //if (this.getAlignment()=="neutral") {
            //    this.setAlignment("aggressive");
            //    System.out.println(this.getName() + " is now aggressive!");
            //}
            //
            ////Target will change to attacker
            //if (this.getTarget().getTarget() != this) {
            //    this.getTarget().setTarget(this);
            //    System.out.println(this.getTarget().getName() + " is now ANGRY with " + this.getName()+ ".");
            //}
            
            return;
        }
    }

    public Creature getTarget() {
        return this.target;
    }
    
    public void setTarget(Creature newtarget) {
        this.target = newtarget;
    }

    protected Point chase()
    {
        int move_x = 0;
        int move_y = 0;

        if (! isInSight(this.getTarget()) && (alignment != "berserk" && alignment != "aggressive" && alignment != "hostile")) {
            return null;
        }
        
        System.out.println(this.getName() + " ("+this.getAlignment()+") sees " + this.getTarget().getName() + ".");

        if (target.getX() < this.getX() && this.canMoveTo(this.getX()-1, this.getY())) {
            move_x = -1;
        }
        if (target.getX() > this.getX() && this.canMoveTo(this.getX()+1, this.getY())) {
            move_x = 1;
        }
        if (target.getY() < this.getY() && this.canMoveTo(this.getX(), this.getY()-1)) {
            move_y = -1;
        }
        if (target.getY() > this.getY() && this.canMoveTo(this.getX(), this.getY()+1)) {
            move_y = 1;
        }
        
        Random random = new Random();
        
        int randomInt = random.nextInt(2);
        
        Point new_point = new Point(this.pos);

        if (randomInt == 0 && move_x != 0) {
            new_point.translate(move_x, 0);
            return new_point;
        }

        if (randomInt == 1 && move_y != 0) {
            new_point.translate(0, move_y);
            return new_point;
        }

        // if random fails
        if (move_x != 0) {
            new_point.translate(move_x, 0);
            return new_point;
        }

        if (move_y != 0) {
            new_point.translate(0, move_y);
            return new_point;
        }

        return null;
    }

    public void setHero(Creature hero) {
        this.hero=hero;
    }
    
    public void setHeroAsTarget() {
        this.target = this.hero;
    }

    private boolean isInFightRange(Creature target) {
        
        int distanceX = (int) (this.getX()-target.getX());
        int distanceY = (int) (this.getY()-target.getY());
        int distance = (int) Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2));
        
        if (distance <= 1) {
            return true;
        }

        return false;
    }

    private Point wander() {
        
        Random random = new Random();
        
        Point new_point = new Point(this.pos);

        int randomInt = random.nextInt(8);
        
        if (randomInt == 0) {
            new_point.translate(0, -1);
        }

        if (randomInt == 1) {
            new_point.translate(0, 1);
        }

        if (randomInt == 2) {
            new_point.translate(-1, 0);
        }

        if (randomInt == 3) {
            new_point.translate(1, 0);
        }
        
        return new_point;
    }
    
    private boolean isInSight(Creature target) {
        int radius = this.getSightRadius();
        
        int distanceX = (int) (this.getX()-target.getX());
        int distanceY = (int) (this.getY()-target.getY());
        int distance = (int) Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2));
        
        if (this.getSightRadius() > distance) {
            return true;
        }
        
        return false;
    }

    public String getAlignment() {
        return this.alignment;
    }
    
    public void setAlignment(String align) {
        this.alignment = align;
    }
    
    
    public int getSightRadius() {
        return this.sightradius;
    }
    
    public void setSightRadius(int radius) {
        this.sightradius = radius;
    }
}
