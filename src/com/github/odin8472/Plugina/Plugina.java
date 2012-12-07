package com.github.odin8472.Plugina;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.heroes.Hero;
import com.github.odin8472.Plugina.listeners.PluginaListener;
import com.github.odin8472.Plugina.utilities.ExploData;

public final class Plugina extends JavaPlugin{
	@Override
	public void onEnable(){
		System.out.println("onEnable");
		getLogger().info("onEnable has been invoked!");
        saveDefaultConfig();
		System.out.println("setting up player hashmap");
		playerMap = new HashMap<Player, Hero>();
		//playerMap.put(key, value)
        // Create the PluginaListener
        new PluginaListener(this);
        // set the command executor for sample
        this.getCommand("basic").setExecutor(new PluginaCommandExecutor(this));
        //shitload of constants that i initialize... i would final most of them but i don't know how
        floatingGrid=new ArrayList<Vector>();
        explosionList=new LinkedList<ExploData>();
        arrowMap=new HashMap<Entity,Boolean>();
        jumpLevelArray=new float[10];
        jumpLevelArray[0]=.5F;
        jumpLevelArray[1]=.6F;
        jumpLevelArray[2]=.7F;
        jumpLevelArray[3]=.75F;
        jumpLevelArray[4]=.8F;
        jumpLevelArray[5]=.9F;
        jumpLevelArray[6]=1.0F;
        jumpLevelArray[7]=1.1F;
        jumpLevelArray[8]=1.2F;
        jumpLevelArray[9]=1.3F;     
        durabilityArray=new float[5];
        durabilityArray[0]=100;
        durabilityArray[1]=200;
        durabilityArray[2]=400;
        durabilityArray[3]=50;
        durabilityArray[4]=800;
        materialArray=new Material[5];
        materialArray[0]=Material.WOOD_SWORD;
        materialArray[1]=Material.STONE_SWORD;
        materialArray[2]=Material.IRON_SWORD;
        materialArray[3]=Material.GOLD_SWORD;
        materialArray[4]=Material.DIAMOND_SWORD;
        floatingGrid.add(new Vector(0,0,0));
        floatingGrid.add(new Vector(0,0,1));
        floatingGrid.add(new Vector(1,0,0));
        floatingGrid.add(new Vector(1,0,1));
        floatingGrid.add(new Vector(0,0,-1));
        floatingGrid.add(new Vector(-1,0,0));
        floatingGrid.add(new Vector(-1,0,-1));
        floatingGrid.add(new Vector(1,0,-1));
        floatingGrid.add(new Vector(-1,0,1));
        floatingGrid.add(new Vector(2,0,0));
        floatingGrid.add(new Vector(0,0,2));
        floatingGrid.add(new Vector(-2,0,0));
        floatingGrid.add(new Vector(0,0,-2));
        floatingGrid.add(new Vector(0,-1,0));
        floatingGrid.add(new Vector(0,-1,1));
        floatingGrid.add(new Vector(1,-1,0));
        floatingGrid.add(new Vector(0,-1,-1));
        floatingGrid.add(new Vector(-1,-1,0));


        
        
        System.out.println("After the last statement in onEnable");
        if(!playerMap.isEmpty()){
        	System.out.println("in plugina, starting the server with a non empty playermap! VERY BAD");
        }
	}
 
	public void onDisable(){
		System.out.println("onDisable");
		getLogger().info("onDisable has been invoked!");
	}
	public static ArrayList<Vector> floatingGrid;//putting this here cuz i'm not sure what hero it's going into, it's just another constant
	public static ExploData selfharm;
	public static Queue<ExploData> explosionList;
	public static HashMap<Entity,Boolean> arrowMap;
	public static float []durabilityArray;
	public static HashMap<Player, Hero> playerMap;
	public static float [] jumpLevelArray;
	public static Material[] materialArray;
}
 