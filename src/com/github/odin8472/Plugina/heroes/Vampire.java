package com.github.odin8472.Plugina.heroes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.attacks.FloatingIsland;
import com.github.odin8472.Plugina.attacks.SimpleLightningStrike;
import com.github.odin8472.Plugina.attacks.TransformBat;
import com.github.odin8472.Plugina.blocks.HoverBlock;
import com.github.odin8472.Plugina.threadTypes.BlockWithSword;
import com.github.odin8472.Plugina.threadTypes.CooldownThread;
import com.github.odin8472.Plugina.threadTypes.PluginaThread;

public class Vampire extends Hero{
	public Vampire(Plugina plugin,Player p){
		super(plugin);
        this.plugin = plugin;
        setPlayer(p);
		setPower(1);
		setHealth(500);
		setCurrentHealth(500);
		setMana(100);
		setCurrentMana(100);
		setSpeed(2);
		setJump(10);
		setWood(2);
		setStone(2);
		setIron(2);
		setGold(2);
		setDiamond(2);
		lvlarray=new float[4];//wtf, this should deff be in hero///now that i think about it i wanted each hero to have different leveling
		lvlarray[0]=2.0f;
		lvlarray[1]=2.66f;
		lvlarray[2]=3.33f;
		lvlarray[3]=4.0f;
		setarray();
		//PlayerMove fm=new VampireMove();
		setHeroName("Vampire");
		//setPlayMove(fm);
		setPlayerName(getPlayer().getName());
		inflystate=false;
		infallingstate=false;
		fallvelocity=0;
		setManaregen(.5);
		setListenId(2);
	}

	public void woodRight(){
		System.out.println("wood:right or left hit:woodRight()");
		return;
	}
	public void woodLeft(){
		if(!swordsOffCD[0])
			return;
		else
			swordsOffCD[0]=false;
		System.out.println("wood:right or left hit:woodLeft()");
		FloatingIsland fl= new FloatingIsland(getStrikeDetails());
		PluginaThread floatingTask = new PluginaThread(fl);
    	floatingTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, floatingTask, 0, 20));
    	CooldownThread cd=new CooldownThread(getStrikeDetails().getPlayer(),getWood(),0);//i use an accessor and a public variable, i was too lazy to make another accessor, 0 refers to wood, i should probably enum it
    	cd.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, cd, 1, 1));//start after a fifth of a second and execute every fifth of a second
    	return;
	}
	public void stoneRight(){
		System.out.println("stone:right or left hit:stoneRight()");
		return;
	}
	public void stoneLeft(){
		if(!swordsOffCD[1])
			return;
		else
			swordsOffCD[1]=false;
		System.out.println("wood:right or left hit:woodLeft()");
		SimpleLightningStrike sl= new SimpleLightningStrike(getStrikeDetails());
		PluginaThread simpleTask = new PluginaThread(sl);
    	simpleTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, simpleTask, 2, 2));
    	CooldownThread cd=new CooldownThread(getStrikeDetails().getPlayer(),getStone(),1);//i use an accessor and a public variable, i was too lazy to make another accessor, 0 refers to wood, i should probably enum it
    	cd.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, cd, 1, 1));//start after a fifth of a second and execute every fifth of a second
    	return;
	}
	public void ironRight(){
		System.out.println("iron:right or left hit:ironRight()");
		return;
	}
	public void ironLeft(){

		return;
	}
	public void goldRight(){
		System.out.println("gold:right hit:goldRight()");
		if(isBlockingArray[3]==true){return;}//if it's in between checks and you block on and off i don't want it to drain mana too fast
		if(Plugina.playerMap.get(getPlayer()).getMana()<2){
			isBlockingArray[3]=false;
			
			getPlayer().setFlying(false);
			getPlayer().setAllowFlight(false);
			
			return;
		}
		else{//if it has enough mana
			System.out.println("inside the goldRight() actual routine(once it's determined it has the mana)");
			isBlockingArray[3]=true;
			getPlayer().setAllowFlight(true);
			getPlayer().setFlying(true);
			HoverBlock hb = new HoverBlock(getPlayer());
			BlockWithSword blockTask = new BlockWithSword(hb);
	    	blockTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, blockTask, 10/5, 10/5));
		}
		return;
	}
	public void goldLeft(){
		System.out.println("gold:left hit:goldLeft()");
		if(!swordsOffCD[3])
			return;
		else
			swordsOffCD[3]=false;
		TransformBat bt = new TransformBat(getPlayer(),plugin);
		PluginaThread batTask = new PluginaThread(bt);
    	batTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, batTask, 1,60*20));
    	CooldownThread cd=new CooldownThread(getStrikeDetails().getPlayer(),getGold(),3);
    	cd.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, cd, 60*20+20,1 ));//start after a fifth of a second and execute every fifth of a second
	}
	
	public void diamondRight(){
		System.out.println("diamond:right or left hit:diamondRight()");	

		return;
	}
	
	public void diamondLeft(){

		return;
	}
	public float getSpeedLevel(int s){
		return lvlarray[s];
	}
	

	public boolean inflystate;
	private float[] lvlarray;
    private final Plugina plugin;
}
