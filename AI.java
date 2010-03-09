import java.awt.event.KeyEvent;
import java.util.Random;


public class AI {

	public AI() {
		
	}
	
	public void act(Creature creature) {
		
		if (creature.getTarget() != null) {
			moveTowards(creature, creature.getTarget());
		}
		
		if (creature.getAlignment()=="neutral") {
			moveRandom(creature);
		}
		

	}
	
	
	private void moveRandom(Creature creature) {
		
		Random random= new Random();
		
		int randomInt = random.nextInt(5);
		
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
		
		if (target.getX() < creature.getX() ) {
			
			move_x = -1;
		}
		if (target.getX() > creature.getX() ) {
			
			move_x = 1;
		}
		if (target.getY() < creature.getY() ) {
			
			move_y = -1;
		}
		if (target.getY() > creature.getY() ) {
			
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
		
		
		
	}
	
	
	
}
