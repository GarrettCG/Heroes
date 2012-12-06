package com.github.odin8472.Plugina.attacks;
import org.bukkit.Effect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.utilities.StrikeDetails;


public class Kunai implements SwordMove{//as it stands this class will cause memory leaks because it will never stop moving, i should add a condition to make it disappear after a set amount of iterations
	//arrow.getWorld().playEffect(arrow.getLocation(),Effect.BOW_FIRE, 25);
	public Kunai(StrikeDetails strike){
		info=strike;
		isdone=false;
		arrow=info.getPlayer().launchProjectile(Arrow.class);
		arrow.getWorld().playEffect(arrow.getLocation(),Effect.BOW_FIRE, 25);
		velocity=arrow.getVelocity().multiply(.5f);
		arrow.setVelocity(velocity);
		Plugina.arrowMap.put(arrow, false);
	}
	@Override
	public void execute() {
		//arrow.
		//Entity arrow=info.getPlayer().launchProjectile(Arrow.class);
		if(Plugina.arrowMap.get(arrow)==true){
			Plugina.arrowMap.remove(arrow);
			isdone=true;
			return;
		}
		arrow.setVelocity(velocity);
		arrow.getWorld().playEffect(arrow.getLocation(),Effect.BOW_FIRE, 25);
		//System.out.println("in Kunai execute, arrow velocity is:"+arrow.getVelocity());
		
		//arrow.setVelocity(arrow.getVelocity().multiply(2f));
		//isdone=true;//Plugina.playerMap.get(info.getPlayer()).getPower()));//(float)Plugina.playerMap.get(info.getPlayer()).getPower());
		//Vector v1=new Vector(0.16726334032609813,-0.13084788862266095,0.20471972707426644);
		//System.out.println("velocity one"+v1.length());
		//Vector v2=new Vector(-0.09956673576483688,-0.104599720542085,-0.08550856404421125);
		//System.out.println("velocity two"+v2.length());
		
	}
	@Override
	public boolean isDone() {
		return isdone;
	}
	private Vector velocity;
	private Entity arrow;
	private boolean isdone;
	private StrikeDetails info;
}
