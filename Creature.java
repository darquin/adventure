
import java.awt.Image;
import java.awt.event.KeyEvent;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import javax.swing.JPanel;

import javax.swing.ImageIcon;

abstract class Creature {
    
    private String originalImage;

    private String image_path;
    
    protected Map map;
    
    /**
     * Logger instance
     * 
     */
    protected Log log;

    protected Point pos;

    protected Image image;

    protected String Name;
    
    //Statit creatureille
    protected int str;
    protected int end;
    protected int agi;
    
    //Attack = 10 + Str;
    //Defense = agi / 2;
    //Health = End * 2
    //Skill kertoo mahdollisuuden critical hitiin. Pitää keksiä joku logiikka, millä
    //statit vaikuttaa sen kehitykseen.
    //Vois myös miettiä jotain maxHealth tyyppistä juttua...
    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;
    protected int skill;
    protected int currentExp;
    protected double expToLvl;
    protected int lvl;
    
    //Muuttujia tappelua varten
    private int dealtDamage;
    private int criticalChance;
    private Random genRandom = new Random();
    
    public Creature(Point point, String img, int strength, int endurance, int agility) 
    {
        this.image_path = "images/" + img + ".png";
        this.originalImage = img;
        
        ImageIcon ii = new ImageIcon(this.image_path);
        this.image = ii.getImage();
        this.pos = point;

        this.str = strength;
        this.agi = agility;
        this.end = endurance;
        
		this.setAttack();
		this.setDefense();
		this.setHealth();
		
		this.lvl = 1;
		this.currentExp = 0;
		this.expToLvl = 100;
    }
    
    public void setMap(Map map)
    {
        this.map = map;
    }
    
    public int getY() 
    {
        return (int) this.pos.getY();
    }

    public int getX() 
    {
        return (int) this.pos.getX();
    }
    
    public Image getImage() 
    {
        return this.image;
    }

    abstract public boolean fight(Creature creature);

    public Point getDestinationPoint(int key)
    {
        Point dest = new Point();

        switch (key) {
        case KeyEvent.VK_LEFT:
            this.image_path = "images/"+originalImage+".png";
            dest.setLocation(this.getX() - 1, this.getY());
            break;
        case KeyEvent.VK_RIGHT:
            this.image_path = "images/"+originalImage+"Right.png";
            dest.setLocation(this.getX() + 1, this.getY());
            break;
        case KeyEvent.VK_UP:
            dest.setLocation(this.getX(), this.getY() - 1);
            break;
        case KeyEvent.VK_DOWN:
            dest.setLocation(this.getX(), this.getY() + 1);
            break;
        default:
        	
        	return null;

        }

        return dest;
    }
    
    public boolean move(Point point)
    {
        if (this.canMoveTo(point)) {
        	
        	//Poistetaan paikkamerkintä cellistä josta astutaan pois
        	Cell oldcell = this.map.getCell(pos);
        	oldcell.setCreature(null);
        	
        	//Lisätään paikkamerkintä celliin johon astutaan
        	Cell newcell = this.map.getCell(point);
        	newcell.setCreature(this);

            ImageIcon ii = new ImageIcon(this.image_path);
            this.image = ii.getImage();

			this.pos = point;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean canMoveTo(Point dest)
    {
    	if(!this.isEmpty(dest) || this.creatureInTheWay(dest)){
    		return false;
    	}
        return true;
    }
    
    public boolean isEmpty(Point dest){
    	int x = (int) dest.getX();
    	int y = (int) dest.getY();
       

    	if (x < 0 || y < 0 || x >= Map.TOTALCOLS || y >= Map.TOTALROWS) {
    		return false;
       }
       return this.map.getCell(dest).canPenetrate();
   }
    
    
    
    public boolean creatureInTheWay(Point coord) {
    	
    	Cell cell = this.map.getCell(coord);
    	
    	if (cell.getCreature()!=null) {
    		return true;
    	}
    	return false;
    }
    
    public boolean canMoveTo(int x, int y) {
        return this.canMoveTo(new Point(x, y));
    }

    public void paint(Point area, Graphics g, JPanel p)
    {
        int x = (int) (this.getX() - area.getX()) * Map.CELL;
        int y = (int) (this.getY() - area.getY()) * Map.CELL;
        
        g.drawImage(this.getImage(), x, y, p);
    }
    

    public Point getPos(){
    	return this.pos;
    }
    public String getName() {
        return this.Name;
    }
    
    public void setName(String name) {
        this.Name = name;
    }
    //Stat handling creatureille
    public void setAttack(){
    	this.attack = 10 + this.str;
    }
    public int getAttack(){
    	return this.attack;
    }
    public void setDefense(){
    	this.defense = this.agi / 2;
    }
    public int getDefense(){
    	return this.defense;
    }
    public void setHealth(){
    	this.maxHealth = this.end * 2;
    	this.health = this.maxHealth;
    }
    public int getHealth(){
    	return this.health;
    }
    public void setSkill(){
    	this.skill = 50;
    }
    public int getSkill(){
    	return this.skill;
    }
    public boolean assignDamage(int damage){
    	this.health -= damage;
    	if(this.health <= 0){
    		Log.write(this.Name+" is DEAD!");
    		this.map.terminateCreature(this);
    		this.died();
    		return true;
    	}
    	else {
    		Log.write(this.Name +", health: "+this.health);
    		return false;
    	}
    }
    //End stat handling
    
    public int getCode()
    {
    	return this.hashCode();
    }
    
    //Metodit tappelua varten:
    //Tarkistetaan mahdollinen critical hit.
    public int CriticalHitCheck(int creatureSkill){
    	criticalChance = genRandom.nextInt(100);
    	if(criticalChance <= creatureSkill){
    		Log.write("A CRITICAL HIT!");
    		return 2;
    	}
    	return 1;
    }
    protected boolean Strike(Creature target){
    	dealtDamage = this.attack * CriticalHitCheck(this.skill) - target.defense;
    	if(dealtDamage < 0) dealtDamage = 0;
    	Log.write(this.Name+" strikes "+target.Name+" and deals "+dealtDamage+" points of damage!");
    	return target.assignDamage(dealtDamage);
    }

    public void gainExp(int opponentLVL)
    {
    	double tempOpLVL = opponentLVL;
    	double tempLVL = this.lvl;
    	double expMultiplier = (tempOpLVL / tempLVL);
    	double gainedExp = 50 * expMultiplier;
    	double roundedExp = Math.round(gainedExp);
    	this.currentExp += roundedExp;
    	Log.write(this.Name + " gains " + roundedExp + " points of experience.");
    	if(this.currentExp >= this.expToLvl) this.LevelUp();
    }
    public void LevelUp()
    {
    	int tempStatStr = 0;
    	int tempStatAgi = 0;
    	int tempStatEnd = 0;
    	this.lvl += 1;
    	for(int i = 0; i < 15; i++)
    	{
    		int temp = genRandom.nextInt(3);
    		switch(temp) 
    		{
    		case 0: tempStatStr++;
    			break;
    		case 1: tempStatAgi++;
    			break;
    		case 2: tempStatEnd++;
    			break;
    		}
    	}
    	Log.write(this.Name + " has gained a level!!");
    	Log.write(this.Name + "'s strength has increased by " + tempStatStr);
    	this.str += tempStatStr;
    	Log.write(this.Name + "'s agility has increased by " + tempStatAgi);
    	this.agi += tempStatAgi;
    	Log.write(this.Name + "'s endurance has increased by " + tempStatEnd);
    	this.end += tempStatEnd;
    	this.currentExp -= this.expToLvl;
		this.setAttack();
		this.setDefense();
		this.setHealth();
		Log.write(this.Name + " has a new maximum health of " +this.maxHealth);
		Log.write(this.Name + " has a new attack of " +this.attack);
		Log.write(this.Name + " has a new defense of " +this.defense);
    	this.expToLvl = this.expToLvl * 1.5;
    	System.out.println("New exp to lvl: " + this.expToLvl);
    	Log.write(this.Name + " current exp: " + this.currentExp);
    }
    abstract protected void died();
}
