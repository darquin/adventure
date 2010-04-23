
import java.awt.Point;
import java.util.Random;
import java.lang.Math;

class Villain extends Creature {
    
    private static Creature hero;

    private String alignment;

    private int sightradius = 5;

    protected Creature target;

    public Villain(Point point, String img, int strength, int endurance, int agility) {
        super(point, img, strength, endurance, agility);
        this.alignment = "neutral";
        this.Name = "Enemy";
        this.lvl = 2;
    }

    public void act()
    {
        // tappelee vain jos tällä on kohde
        Creature target = this.getTarget();
        if (target != null) {
            if (this.fight(target)) {
                return;
            }
    	}

        Point dest;

        // hidastus
        dest = this.chase();
        if (dest == null) {
            dest = this.wander();
        }

        this.move(dest);
    }

    public boolean fight(Creature target) {
        if (this.isInFightRange(target) && this.getAlignment() != "peaceful") {
            Log.write(this.getName()+" is ATTACKING " + target.getName()+".");

            this.Strike(target);
            if(target.Dies())
            {
            	this.gainExp(target.lvl);
            	this.target = null;
            }
            
            return true;
        }
        
        return false;
    }

    public Creature getTarget() {
   // 	if (this.target.Name.equals(this.hero.getName())) && !this.map.getVillains().containsKey(this.target.hashCode())) {
   // 		System.out.println(this.target.Name);
   // 		this.setTarget(null);
   // 	}
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
        
        Log.write(this.getName() + " ("+this.getAlignment()+") sees " + this.getTarget().getName() + ".");

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
        if(target == null) return false;
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
