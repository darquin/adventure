import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;


public class AI {

	public AI() {
		
	}
	
	public void act(Creature creature) {
		
		//If a creature already has a target
		if (creature.getTarget() != null) {
			
			if (isInFightRange(creature, creature.getTarget())  && creature.getAlignment() != "peaceful") {
				
				System.out.println(creature.getName()+" is ATTACKING " + creature.getTarget().getName()+".");
				
				//If target is neutral it becomes aggressive
				if (creature.getTarget().getAlignment()=="neutral") {
					creature.getTarget().setAlignment("aggressive");
					//creature.getTarget().setTarget(creature);
					System.out.println(creature.getTarget().getName()+" is now aggressive!");
				}
				//The same applies to attacker
				if (creature.getAlignment()=="neutral") {
					creature.setAlignment("aggressive");
					System.out.println(creature.getName() + " is now aggressive!");
				}
				
				
				
				//Target will change to attacker
				
				if (creature.getTarget().getTarget() != creature) {
					
					creature.getTarget().setTarget(creature);
					System.out.println(creature.getTarget().getName() + " is now ANGRY with " + creature.getName()+ ".");
				}
				
				return;
				
			}
			
			if (isInSight(creature, creature.getTarget())) {
				
				System.out.println(creature.getName() + " ("+creature.getAlignment()+") sees " + creature.getTarget().getName() + ".");
				//System.out.println("It seems to be " + creature.getAlignment() + ".");
				
				String alignment = creature.getAlignment();
				
				if (alignment == "berserk" || alignment == "aggressive" || alignment=="hostile") {
					moveTowards(creature, creature.getTarget());
					return;
				}

			}
			else {	//If target not in sight, aggressive becomes neutral
				if (creature.getAlignment()=="aggressive") {
					
					creature.setAlignment("neutral");
					System.out.println(creature.getName() + " calms down and is now neutral.");
					
				}
			}
			
		}
		
		
		if (creature.getAlignment()=="neutral") {
			moveRandom(creature);
		}
		
		if (creature.getAlignment()=="hostile") {
			//
			//creature.setHeroAsTarget();
			moveRandom(creature);
			
		}
		
		if (creature.getAlignment()=="aggressive") {
			
		}

		
		if (creature.getAlignment()=="berserk") {
		
			
		}
		

	}
	
	
	private boolean isInFightRange(Creature creature, Creature target) {
		
		double distanceX = (creature.getX()-target.getX());
		double distanceY = (creature.getY()-target.getY());
		double distance = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2));
		
		if ((int)(distance)<=1) {
			return true;
		}

		return false;
	}

	private void moveRandom(Creature creature) {
		
		Random random= new Random();
		
		int randomInt = random.nextInt(8);
		
		if (randomInt==0)
			creature.move(KeyEvent.VK_UP);

		if (randomInt==1)
			creature.move(KeyEvent.VK_DOWN);

		if (randomInt==2)
			creature.move(KeyEvent.VK_LEFT);

		if (randomInt==3)
			creature.move(KeyEvent.VK_RIGHT);
		
	}
	
	
	private void moveTowards(Creature creature, Creature target) {
		
		int move_x = 0;
		int move_y = 0;
		
		if (target.getX() < creature.getX() && creature.canMoveTo(creature.getX()-1, creature.getY())) {
			move_x = -1;
		}
		if (target.getX() > creature.getX() && creature.canMoveTo(creature.getX()+1, creature.getY())) {
			move_x = 1;
		}
		if (target.getY() < creature.getY() && creature.canMoveTo(creature.getX(), creature.getY()-1)) {
			move_y = -1;
		}
		if (target.getY() > creature.getY() && creature.canMoveTo(creature.getX(), creature.getY()+1)) {
			move_y = 1;
		}
		
		Random random = new Random();
		
		int randomInt = random.nextInt(2);
		
		if (randomInt == 0 && move_x==-1) {
			creature.move(KeyEvent.VK_LEFT);
			return;
		}
		if (randomInt == 0 && move_x==1) {
			creature.move(KeyEvent.VK_RIGHT);
			return;
		}
		if (randomInt == 1 && move_y==-1) {
			creature.move(KeyEvent.VK_UP);
			return;
		}
		if (randomInt == 1 && move_y==1) {
			creature.move(KeyEvent.VK_DOWN);
			return;
		}
		
		
		//IF RANDOM FAILS:
		
		if (move_x==0) {
			if (move_y==-1) {
				creature.move(KeyEvent.VK_UP);
				return;
			}
			if (move_y==1) {
				creature.move(KeyEvent.VK_DOWN);
				return;
			}
		}
		
		if (move_y==0) {
			if (move_x==-1) {
				creature.move(KeyEvent.VK_LEFT);
				return;
			}
			if (move_x==1) {
				creature.move(KeyEvent.VK_RIGHT);
				return;
			}
		}
		
		
		
		
	}
	
	
	private boolean isInSight(Creature creature, Creature target) {
		
		int radius = creature.getSightRadius();
		
		double distanceX = (creature.getX()-target.getX());
		double distanceY = (creature.getY()-target.getY());
		double distance = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2));
		
		if (creature.getSightRadius() > (int)(distance)) {
			return true;
		}
		
		
		return false;
	}
	
	
	
}
