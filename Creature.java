
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
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
    protected int skill;
    protected int currentExp;
    protected int expToLvl;
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
    	this.health = this.end * 2;
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
    public void assignDamage(int damage){
    	this.health -= damage;
    	Log.write(this.Name +", health: "+this.health);
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
    protected void Strike(Creature target){
    	dealtDamage = this.attack * CriticalHitCheck(this.skill) - target.defense;
    	if(dealtDamage < 0) dealtDamage = 0;
    	Log.write(this.Name+" strikes "+target.Name+" and deals "+dealtDamage+"points of damage!");
    	target.assignDamage(dealtDamage);
    }
    public boolean Dies(){
    	if(this.health <= 0){
    		Log.write(this.Name+" is DEAD!");
    		this.map.terminateCreature(this);
    		return true;
    	}
    	else return false;
    }
    public void gainExp(int opponentLVL)
    {
    	int gainedExp = 50 * (opponentLVL / this.lvl);
    	this.currentExp += gainedExp;
    	Log.write(this.Name + " gains " + gainedExp + " points of experience.");
    	if(currentExp >= expToLvl) this.LevelUp();
    }
    public void LevelUp()
    {
    	int tempStat;
    	this.lvl += 1;
    	Log.write(this.Name + " has gained a level!!");
    	tempStat = 1 + genRandom.nextInt(6);
    	Log.write(this.Name + "'s strength has increased by " + tempStat);
    	this.str += tempStat;
    	tempStat = 1 + genRandom.nextInt(6);
    	Log.write(this.Name + "'s agility has increased by " + tempStat);
    	this.agi += tempStat;
    	tempStat = 1 + genRandom.nextInt(6);
    	Log.write(this.Name + "'s endurance has increased by " + tempStat);
    	this.end += tempStat;
    	this.currentExp = 0;
    }
}
