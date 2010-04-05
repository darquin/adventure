
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
    
    //Muuttujia tappelua varten
    private int dealtDamage;
    private int criticalChance;
    private Random chanceToHit = new Random();
    
    protected boolean dead;
    
    public Creature(Point point, String img) 
    {
        this.image_path = "images/" + img + ".png";
        this.originalImage = img;
        
        ImageIcon ii = new ImageIcon(this.image_path);
        this.image = ii.getImage();
        this.pos = point;
    }
    
    public Creature(int x, int y, String img) 
    {
        this(new Point(x, y), img);
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


    abstract public void fight();

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
            break;
        }

        return dest;
    }
    
    public boolean move(Point point)
    {
        if (this.canMoveTo(point)) {
            this.pos = new Point(point);
            ImageIcon ii = new ImageIcon(this.image_path);
            this.image = ii.getImage();

            return true;
        } else {
            return false;
        }
    }
    
    public boolean canMoveTo(Point dest)
    {
        int x = (int) dest.getX();
        int y = (int) dest.getY();

        if (x < 0 || y < 0) {
            return false;
        }
        
        Cell cell = this.map.getCell(y, x);
        return cell.canPenetrate();
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
    
    //Metodit tappelua varten:
    //Tarkistetaan mahdollinen critical hit.
    public int CriticalHitCheck(int creatureSkill){
    	criticalChance = chanceToHit.nextInt(100);
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
    public void Dies(){
    	if(this.health <= 0){
    		Log.write(this.Name+" is DEAD!");
    		this.dead = true;
    	}
    }
}
