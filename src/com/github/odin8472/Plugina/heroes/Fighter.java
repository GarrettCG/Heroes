package com.github.odin8472.Plugina.heroes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.attacks.DonkeyPunch;
import com.github.odin8472.Plugina.attacks.ExplosionLine;
import com.github.odin8472.Plugina.attacks.ExplosionSlash;
import com.github.odin8472.Plugina.attacks.ExplosionWave;
import com.github.odin8472.Plugina.attacks.KnockBack;
import com.github.odin8472.Plugina.attacks.Kunai;
import com.github.odin8472.Plugina.attacks.SimpleLightningStrike;
import com.github.odin8472.Plugina.blocks.HealBlock;
import com.github.odin8472.Plugina.blocks.PulseNovaBlock;
import com.github.odin8472.Plugina.threadTypes.BlockWithSword;
import com.github.odin8472.Plugina.threadTypes.CooldownThread;
import com.github.odin8472.Plugina.threadTypes.PluginaThread;
import com.github.odin8472.Plugina.utilities.Th3dtran;

public class Fighter extends Hero{
	public Fighter(Plugina plugin,Player p){
		super(plugin);
		this.plugin = plugin;
		
        setPlayer(p);
		setPower(1);
		setHealth(1000);
		setCurrentHealth(1000);
		setMana(50);
		setCurrentMana(50);
		setSpeed(1);
		setJump(10);
		setWood(2);
		setStone(2);
		setIron(2);
		setGold(2);
		setDiamond(2);
		explosionslashstart=new Vector[7];
		explosionslashend=new Vector[7];
		Vector reference=new Vector(0,0,1);
		Th3dtran tran=new Th3dtran(); 
		explosionslashstart[0]=tran.Rotate(100, -30, reference);
		explosionslashstart[1]=tran.Rotate(80, -25, reference);
		explosionslashstart[2]=tran.Rotate(60, -20, reference);
		explosionslashstart[3]=tran.Rotate(40, -15, reference);
		explosionslashstart[4]=tran.Rotate(28, -10, reference);
		explosionslashstart[5]=tran.Rotate(14, -5, reference);
		explosionslashstart[6]=reference.clone();
		explosionslashend[0]=tran.Rotate(185, -30, reference);
		explosionslashend[1]=tran.Rotate(170,-55,reference);
		explosionslashend[2]=tran.Rotate(80,-80,reference);
		explosionslashend[3]=tran.Rotate(60,-55,reference);
		explosionslashend[4]=tran.Rotate(40,-35,reference);
		explosionslashend[5]=tran.Rotate(20,-12,reference);
		explosionslashend[6]=tran.Rotate(-5,10,reference);
		lvlarray=new float[4];//wtf, this should deff be in hero///now that i think about it i wanted each hero to have different leveling
		lvlarray[0]=2.0f;
		lvlarray[1]=2.66f;
		lvlarray[2]=3.33f;
		lvlarray[3]=4.0f;
		setarray();
		setHeroName("Fighter");
;		setPlayerName(getPlayer().getName());
		inflystate=false;
		infallingstate=false;
		fallvelocity=0;
		setManaregen(.5);
		setListenId(1);
	}

	public void woodRight(){
		System.out.println("wood:right or left hit:woodRight()");
		return;
	}
	public void woodLeft(){
		System.out.println("stone:right or left hit:stoneLeft()");
		if(!swordsOffCD[0])
			return;
		else
			swordsOffCD[0]=false;
		Kunai k = new Kunai(getStrikeDetails());
		PluginaThread throwTask = new PluginaThread(k);
    	throwTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, throwTask, 0, 1));
    	CooldownThread cd=new CooldownThread(getStrikeDetails().getPlayer(),0.5f,0);
    	cd.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, cd, 1, 1));//start after a fifth of a second and execute every fifth of a second
		return;
	}
	public void stoneRight(){
		System.out.println("stone:right or left hit:stoneRight()");
		if(isBlockingArray[1]==true){return;}//if it's in between checks and you block on and off i don't want it to drain mana too fast
		if(Plugina.playerMap.get(getPlayer()).getCurrentMana()<2){
			isBlockingArray[1]=false;			
			return;
		}
		else{//if it has enough mana
			System.out.println("inside the goldRight() actual routine(once it's determined it has the mana)");
			isBlockingArray[1]=true;
			HealBlock hb = new HealBlock(getPlayer());
			BlockWithSword blockTask = new BlockWithSword(hb);
	    	blockTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, blockTask, 1, 5));
		}
	}
	public void stoneLeft(){
		System.out.println("stone:right or left hit:stoneLeft()");
		if(!swordsOffCD[1])
			return;
		else
			swordsOffCD[1]=false;
		ExplosionSlash es = new ExplosionSlash(getStrikeDetails());
		//KnockBack kb=new KnockBack(getStrikeDetails(),1);
		PluginaThread slashTask = new PluginaThread(es);
		//PluginaThread knockTask = new PluginaThread(kb);
    	slashTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, slashTask, 1, 1));
    	//knockTask.setId(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, knockTask, 2));
    	CooldownThread cd=new CooldownThread(getStrikeDetails().getPlayer(),getStone(),1);
    	cd.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, cd, 1, 1));//start after a fifth of a second and execute every fifth of a second
		return;
	}
	public void ironRight(){
		System.out.println("iron:right or left hit:ironRight()");
		return;
	}
	public void ironLeft(){
		if(!swordsOffCD[2])
			return;
		else
			swordsOffCD[2]=false;
		System.out.println("iron:Fighter:ironLeft()");
		ExplosionLine el = new ExplosionLine(getStrikeDetails());
		KnockBack kb=new KnockBack(getStrikeDetails(),1);
		PluginaThread lineTask = new PluginaThread(el);
		PluginaThread knockTask = new PluginaThread(kb);
    	lineTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, lineTask, 2, 2));
    	knockTask.setId(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, knockTask, 2));
    	CooldownThread cd=new CooldownThread(getStrikeDetails().getPlayer(),getIron(),2);
    	cd.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, cd, 1, 1));//start after a fifth of a second and execute every fifth of a second
		return;
	}
	public void goldRight(){
		System.out.println("gold:right hit:goldRight()");
		if(isBlockingArray[3]==true){return;}//if it's in between checks and you block on and off i don't want it to drain mana too fast
		if(Plugina.playerMap.get(getPlayer()).getCurrentMana()<2){
			isBlockingArray[3]=false;			
			return;
		}
		else{//if it has enough mana
			System.out.println("inside the goldRight() actual routine(once it's determined it has the mana)");
			isBlockingArray[3]=true;
			PulseNovaBlock pb = new PulseNovaBlock(getPlayer());
			BlockWithSword blockTask = new BlockWithSword(pb);
	    	blockTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, blockTask, 1, 5));
		}
		return;
	}
	public void goldLeft(){
		System.out.println("stone:right or left hit:stoneLeft()");
		if(getStrikeDetails().getTarget()==null)
			return;
		if(!swordsOffCD[3])
			return;
		else
			swordsOffCD[3]=false;
		DonkeyPunch dp = new DonkeyPunch(getStrikeDetails());
		PluginaThread throwTask = new PluginaThread(dp);
    	throwTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, throwTask, 0, 10));
    	CooldownThread cd=new CooldownThread(getStrikeDetails().getPlayer(),0.5f,3);
    	cd.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, cd, 1, 1));//start after a fifth of a second and execute every fifth of a second
		return;
	}
	
	public void diamondRight(){
		System.out.println("diamond:right or left hit:diamondRight()");	
		return;
	}
	
	public void diamondLeft(){
		if(!swordsOffCD[4])
			return;
		else
			swordsOffCD[4]=false;
		System.out.println("diamond:right or left hit:diamondLeft()");
		ExplosionWave ew = new ExplosionWave(getStrikeDetails());
		KnockBack kb=new KnockBack(getStrikeDetails(),1);
		PluginaThread waveTask = new PluginaThread(ew);
		PluginaThread knockTask = new PluginaThread(kb);
    	waveTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, waveTask, 2, 2));
    	knockTask.setId(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, knockTask, 2));
    	CooldownThread cd=new CooldownThread(getStrikeDetails().getPlayer(),getDiamond(),4);
    	cd.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, cd, 1, 1));//start after a fifth of a second and execute every fifth of a second
		return;
	}
	public float getSpeedLevel(int s){
		return lvlarray[s];
	}
	
	public Vector[] explosionslashstart;
	public Vector[] explosionslashend;
	public boolean inflystate;
	private float[] lvlarray;
    private final Plugina plugin;
}
