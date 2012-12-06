package com.github.odin8472.Plugina.heroes;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;



import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.blocks.ManaRegeneration;
import com.github.odin8472.Plugina.threadTypes.BlockWithSword;
import com.github.odin8472.Plugina.utilities.StrikeDetails;

public abstract class Hero {
	public Hero(Plugina plugin){
		this.plugin = plugin;
		power=1;
		mana=1;
		//do constructor things
	}
 
	public void calibrateHealth(Player p){
		p.setHealth(shownhealth);
	}
	public boolean loseMana(double manaLoss,Player p){
		System.out.println("LOSE MANA BEING CALLED");
		if(manaLoss>currentmana){
			System.out.println("RETURNING FALSE!!!!!!!!!!!!!!!!!!!!");
			return false;
		}
		currentmana-=manaLoss;//any time there is mana loss there is a check to see if a task is already running to take care of the animation and regen part
		p.setExp((float)(Plugina.playerMap.get(p).getCurrentMana()/Plugina.playerMap.get(p).getMana()));
		if(!isAlreadyRecovering){
			System.out.println("right before isAlreadyRecovering=true");
			isAlreadyRecovering=true;
			//create a new task
			ManaRegeneration manaReg=new ManaRegeneration(p);
			BlockWithSword manaTask = new BlockWithSword(manaReg);
	    	manaTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, manaTask, 20, 20));
		}
			
		return true;
	}
	public void gainHealth(double amount,Player p){
		System.out.println("Health before taking damage:"+currenthealth);
		currenthealth+=amount;
		if(currenthealth>health){
			currenthealth=health;
		}
		System.out.println("Health after taking damage:"+currenthealth);
		proportion=currenthealth/health;
		shownhealth=(int)(proportion*20.0);
		if(shownhealth<1){
			shownhealth=1;
		}
		p.setHealth(shownhealth);
	}
	
	public void takeDamage(double damage,Player p){
		if(currenthealth-damage<=0){
			p.setHealth(0);
			System.out.println("Player Died:"+playerName);
			
		}else{
			System.out.println("Health before taking damage:"+currenthealth);
			currenthealth-=damage;
			System.out.println("Health after taking damage:"+currenthealth);
			proportion=currenthealth/health;
			shownhealth=(int)(proportion*20.0);
			if(shownhealth<1){
				shownhealth=1;
			}
			p.setHealth(shownhealth);
		}
	}
	public abstract float getSpeedLevel(int speed);
	public abstract void woodRight();
	public abstract void woodLeft();
	public abstract void stoneRight();
	public abstract void stoneLeft();
	public abstract void ironRight();
	public abstract void ironLeft();
	public abstract void goldRight();
	public abstract void goldLeft();
	public abstract void diamondRight();
	public abstract void diamondLeft();
	
	public double getSpeed(){
		return speed;
	}
	public double getHealth(){
		return health;
	}
	public double getMana(){
		return mana;
	}
	public double getCurrentHealth(){
		return currenthealth;
	}
	public double getCurrentMana(){
		return currentmana;
	}
	public float getPower(){
		return power;
	}
	public void setSpeed(double spe){
		speed=spe;
	}
	public void setHealth(double hea){
		health=hea;
	}
	public void setMana(double man){
		mana=man;
	}
	public void setCurrentHealth(double hea){
		currenthealth=hea;
	}
	public void setCurrentMana(double man){
		currentmana=man;
	}
	public void setPower(float pow){
		power=pow;
	}
	public void setStrikeDetails(StrikeDetails d){
		det=d;
	}
	public StrikeDetails getStrikeDetails(){
		return det;
	}
	public void AnimationKnockback(int power){	//gonna need it to set strike details or it will be stale data or null pointer
		det.getPlayer().setVelocity(det.getPlayer().getLocation().getDirection().multiply(-2));
    }
	public void setJump(int j ){
		jump=j;
	}
	public int getJump(){
		return jump;
	}
	int shownhealth;
	public void crawlInventory(Player p){
		//prepare swordmap
		swordMap.add(new ArrayList<Integer>());
		swordMap.add(new ArrayList<Integer>());
		swordMap.add(new ArrayList<Integer>());
		swordMap.add(new ArrayList<Integer>());
		swordMap.add(new ArrayList<Integer>());
		Inventory inv=p.getInventory();
		for (int x=0;x<9;x++){
			System.out.println("item in "+x+" is:"+inv.getItem(x));
			if (inv.getItem(x)==null)
				break;
			if(inv.getItem(x).getType()==Material.DIAMOND_SWORD){
				swordMap.get(4).add(x);
			}else if(inv.getItem(x).getType()==Material.GOLD_SWORD){
				swordMap.get(3).add(x);
			}else if(inv.getItem(x).getType()==Material.IRON_SWORD){
				swordMap.get(2).add(x);
			}else if(inv.getItem(x).getType()==Material.STONE_SWORD){
				swordMap.get(1).add(x);
			}else if(inv.getItem(x).getType()==Material.WOOD_SWORD){
				swordMap.get(0).add(x);
			}
		}
	}
	public void updateRemove(Player p){
		for(int i=0;i<5;i++){//go through and remove stale data
			ListIterator<Integer> litr = swordMap.get(i).listIterator(); 
			while(litr.hasNext()) {
				Integer element = litr.next(); 
			    if (p.getInventory().getItem(element)==null){
					System.out.println("swordmap.remove due to null item");
					litr.remove();
				}else if(p.getInventory().getItem(element).getType()!=Plugina.materialArray[i]){//if the item in the position that the sword is supposed to be in isn't there then remove it from list
					System.out.println("swordmap.remove due to not equal to the right material");
					litr.remove();//seems really sketchy to try and remove by object... keep an eye on this

				}
				
			}
		}
		System.out.println("PRINTING WHOLE ARRAY in Remove=========================================================================");
		for (int t=0;t<5;t++){
			for(Integer z :swordMap.get(t)){
				if(z!=null){
					System.out.println("array number:"+t+" contents:"+z);
				}
			}
		}
		System.out.println("END OF ARRAY in Remove=========================================================================");
		//en
	}
	public void updateInventory(Player p){//should probably split this into two functions
	//	if(swordMap==null){
		//	System.out.println("swordMap is null, this is updateInventory.You should never see this!");
		//}
		updateRemove(p);
		//fine...lets print the whole array out
		System.out.println("PRINTING WHOLE ARRAY=========================================================================");
		for (int t=0;t<5;t++){
			for(Integer z :swordMap.get(t)){
				if(z!=null){
					System.out.println("array number:"+t+" contents:"+z);
				}
			}
		}
		System.out.println("END OF ARRAY=========================================================================");
		//end of removal code
		//now's the hard part, adding anything new into it
		Inventory inv=p.getInventory();
		for (int j=0;j<9;j++){
			System.out.println("item in "+j+" is:"+inv.getItem(j));
			if(inv.getItem(j)!=null){
				if(inv.getItem(j).getType()==Material.DIAMOND_SWORD){
					if(!swordMap.get(4).contains(j)){
						swordMap.get(4).add(j);
					}
				}else if(inv.getItem(j).getType()==Material.GOLD_SWORD){
					if(!swordMap.get(3).contains(j)){
						swordMap.get(3).add(j);
					}
				}else if(inv.getItem(j).getType()==Material.IRON_SWORD){
					if(!swordMap.get(2).contains(j)){
						swordMap.get(2).add(j);
					}
				}else if(inv.getItem(j).getType()==Material.STONE_SWORD){
					if(!swordMap.get(1).contains(j)){
						swordMap.get(1).add(j);
					}
				}else if(inv.getItem(j).getType()==Material.WOOD_SWORD){
					if(!swordMap.get(0).contains(j)){
						swordMap.get(0).add(j);
					}
				}
			}
		}
	}
	public void setWood(float wood){
		woodcooldown=wood;
	}
	public void setStone(float stone){
		stonecooldown=stone;
	}
	public void setIron(float iron){
		ironcooldown=iron;
	}
	public void setGold(float gold){
		goldcooldown=gold;
	}
	public void setDiamond(float diamond){
		diamondcooldown=diamond;
	}
	public float getWood(){
		return woodcooldown;
	}
	public float getStone(){
		return stonecooldown;
	}
	public float getIron(){
		return ironcooldown;
	}
	public float getGold(){
		return goldcooldown;
	}
	public float getDiamond(){
		return diamondcooldown;
	}
	public float getCDticks(float cd){//not too useful...
		return cd*20;
	}
	public String getHeroName() {
		return HeroName;
	}
	public void setHeroName(String hn) {
		HeroName = hn;
	}

	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getManaregen() {
		return manaregen;
	}
	public void setManaregen(double manaregen) {
		this.manaregen = manaregen;
	}
	public boolean isAlreadyRecovering() {
		return isAlreadyRecovering;
	}
	public void setAlreadyRecovering(boolean isAlreadyRecovering) {
		this.isAlreadyRecovering = isAlreadyRecovering;
	}
	
	public boolean isUnablePickup() {
		return isUnablePickup;
	}
	public void setUnablePickup(boolean isUnablePickup) {
		this.isUnablePickup = isUnablePickup;
	}
	public int getListenId() {
		return listenerid;
	}
	public void setListenId(int id) {
		this.listenerid = id;
	}
	public void setarray(){
		swordsOffCD[0]=true;
		swordsOffCD[1]=true;
		swordsOffCD[2]=true;
		swordsOffCD[3]=true;
		swordsOffCD[4]=true;
		isBlockingArray[0]=false;
		isBlockingArray[1]=false;
		isBlockingArray[2]=false;
		isBlockingArray[3]=false;
		isBlockingArray[4]=false;	
	}
	public ArrayList<ArrayList<Integer>> swordMap = new ArrayList<ArrayList<Integer>>();
	//public boolean wood;
	//public boolean stone;
	//public boolean iron;
	//public boolean gold;
	//public boolean diamond;
	public double manaratio;
	public double fallvelocity;
	public boolean infallingstate;
	public boolean forceflight=false;
	public boolean []swordsOffCD = new boolean[5];
	public boolean []isBlockingArray=new boolean[5];
	public boolean isLocationAware=false;
	public int ground=0;
	public double xcoord=0;
	public double ycoord=0;
	public double zcoord=0;
	public ItemStack []tempinventory;
	private float woodcooldown;
	private float stonecooldown;
	private float ironcooldown;
	private float goldcooldown;
	private float diamondcooldown;
	private int jump;
	private transient Player player;
	private String playerName;
	private String HeroName;
	private double health;
	private double mana;
	private double currenthealth;
	private double currentmana;
	private double manaregen;
	private float power;
	private double speed;
	private double proportion;
	private StrikeDetails det;
	//private int listen;
	private boolean isAlreadyRecovering=false;
	private boolean isUnablePickup=false;
	private final Plugina plugin;
	private int listenerid;

}
