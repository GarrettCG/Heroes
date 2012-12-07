package com.github.odin8472.Plugina.listeners;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;


//import org.bukkit.Bukkit;



import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.heroes.Fighter;
import com.github.odin8472.Plugina.heroes.Hero;
import com.github.odin8472.Plugina.heroes.Vampire;
import com.github.odin8472.Plugina.utilities.ExploData;
import com.github.odin8472.Plugina.utilities.StrikeDetails;


/*
 * This is a sample event listener
 */
public class PluginaListener implements Listener {
    private final Plugina plugin;
    private ArrayList<Listener> otherListeners=new ArrayList<Listener>();
    public PluginaListener(Plugina plugin) {
    	otherListeners.add(new FighterListener(plugin));
    	otherListeners.add(new VampireListener(plugin));
        // Register the listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        //HandlerList.unregisterAll(this);
        this.plugin = plugin;
    }

    public void unregisterOtherListener(Player player){//should make this a boolean
    	HandlerList.unregisterAll(otherListeners.get(Plugina.playerMap.get(player).getListenId()-1));//made the id's start at 1 for some reason
    }
    public void registerOtherListener(Player player){//should make this a boolean
    	plugin.getServer().getPluginManager().registerEvents(otherListeners.get(Plugina.playerMap.get(player).getListenId()-1), plugin);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	System.out.println("Beginning of onPlayerJoin");
    	Hero testfighter=new Vampire(plugin,event.getPlayer());
    	Plugina.playerMap.put(event.getPlayer(),testfighter );//gonna just set everyone to fighter for now
    	System.out.println("right before crash, listen is:"+Plugina.playerMap.get(event.getPlayer()).getListenId());
    	if(!HandlerList.getRegisteredListeners(plugin).contains(otherListeners.get(Plugina.playerMap.get(event.getPlayer()).getListenId()-1))){
    		registerOtherListener(event.getPlayer());
    	}
    	
    	event.getPlayer().setWalkSpeed((float) 0.2);//.2 seems to be default so i guess i'll use 2.0=1 2.66=2 3.33=3, 4.0=4
    	event.getPlayer().setExhaustion(0);
    	event.getPlayer().setFlying(false);
    	event.getPlayer().setSaturation(5);
    	Plugina.playerMap.get(event.getPlayer()).crawlInventory(event.getPlayer());
    	
        //event.getPlayer().sendMessage(this.plugin.getConfig().getString("This is the onPlayerJoin message!"));
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	System.out.println("Beginning of onPlayerQuit");
    	int count=0;
    	for(Player x:Plugina.playerMap.keySet()){
    		if(Plugina.playerMap.get(x).getListenId()==Plugina.playerMap.get(event.getPlayer()).getListenId()){
    			count++;
    		}
    	}
    	if(count==1){
    		unregisterOtherListener(event.getPlayer());//don't want to unregister if there are two of a class, unregister if there is only one
    	}else{
    		System.out.println("won't unregister since there are more than one of these classes on the field");
    	}
    	
    	//need to make sure you're not removing the listener if theres more than one of that class in play
    	Plugina.playerMap.remove(event.getPlayer());
    }
    

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent evt){
    	
    	if(evt instanceof BetterPlayerInteractEvent){
    		System.out.println("evt is instance of BetterPlayerInteractEvent...evt.getplayer.getiteminhand is"+evt.getPlayer().getItemInHand());
    		StrikeDetails sd=new StrikeDetails(evt.getPlayer().getLocation(),evt.getPlayer().getTargetBlock(null, 500).getLocation(),evt.getPlayer().getWorld(),evt.getPlayer());
    		BetterPlayerInteractEvent truevent=(BetterPlayerInteractEvent)evt;
    		sd.setTarget(truevent.getTarget());
    		Plugina.playerMap.get(evt.getPlayer()).setStrikeDetails(sd);
    	}else{
    		Plugina.playerMap.get(evt.getPlayer()).setStrikeDetails(new StrikeDetails(evt.getPlayer().getLocation(),evt.getPlayer().getTargetBlock(null, 500).getLocation(),evt.getPlayer().getWorld(),evt.getPlayer()));
    	}
    	if (evt.getPlayer()==null){
    		System.out.println("evt.getPlayer() is null for some reason");
    	}
    	evt.getAction();
        if(evt.getPlayer().getItemInHand().getTypeId() == Material.IRON_SWORD.getId()){
        	if (evt.getAction().equals(Action.RIGHT_CLICK_AIR)||evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
        		Plugina.playerMap.get(evt.getPlayer()).ironRight();
        	}else{
            	Plugina.playerMap.get(evt.getPlayer()).ironLeft();
        	}
        }
        else if(evt.getPlayer().getItemInHand().getTypeId() == Material.DIAMOND_SWORD.getId()){
        	if (evt.getAction().equals(Action.RIGHT_CLICK_AIR)||evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
        		Plugina.playerMap.get(evt.getPlayer()).diamondRight();
        	}else{
        		Plugina.playerMap.get(evt.getPlayer()).diamondLeft();
        	}
        }
        else if(evt.getPlayer().getItemInHand().getTypeId() == Material.GOLD_SWORD.getId()){
        	if (evt.getAction().equals(Action.RIGHT_CLICK_AIR)||evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
        		Plugina.playerMap.get(evt.getPlayer()).goldRight();
        	}else{
        		Plugina.playerMap.get(evt.getPlayer()).goldLeft();
        	}
        }
        else if(evt.getPlayer().getItemInHand().getTypeId() == Material.STONE_SWORD.getId()){
        	if (evt.getAction().equals(Action.RIGHT_CLICK_AIR)||evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
        		System.out.println("stone sword blocking");
        		Plugina.playerMap.get(evt.getPlayer()).stoneRight();
        	}else{
        		Plugina.playerMap.get(evt.getPlayer()).stoneLeft();
        	}
        }
        else if(evt.getPlayer().getItemInHand().getTypeId() == Material.WOOD_SWORD.getId()){
        	if (evt.getAction().equals(Action.RIGHT_CLICK_AIR)||evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
        		Plugina.playerMap.get(evt.getPlayer()).woodRight();
        	}else{
        		Plugina.playerMap.get(evt.getPlayer()).woodLeft();
        	}
        }
    }
    /*
     * Another example of a event handler. This one will give you the name of
     * the entity you interact with, if it is a Creature it will give you the
     * creature Id.
     */
    @EventHandler
    public void onPlayerRegularAttack(EntityDamageByEntityEvent event) {
        //final EntityType entityType = event.getRightClicked().;
    	if(event.getEntity() instanceof LivingEntity){
    		if(event.getDamager() instanceof Player){
    			if(event.getCause()==DamageCause.ENTITY_ATTACK){
    				event.setCancelled(true);
    				PlayerInteractEvent eve=new BetterPlayerInteractEvent((Player) event.getDamager(),Action.LEFT_CLICK_AIR,null,null,null, event.getEntity());//heh, repurpose the block variable
    				//Event eve=new PlayerInteractEvent();
    				onPlayerInteractBlock(eve);
    			}
    		}
    	}

        //event.getPlayer().sendMessage(MessageFormat.format(
              //  "You interacted with a {0} it has an id of {1}",
              //  entityType.getName(),
               // entityType.getTypeId()));
    }
    
    @EventHandler
    public void onHeal(EntityRegainHealthEvent event) {
    	event.setCancelled(true);
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
    	if(event.getEntity() instanceof Arrow){
    		Plugina.arrowMap.put(event.getEntity(), true);
    	}
    }
    
    
    @EventHandler// (priority = EventPriority.HIGH)
    public void onExplosion(EntityDamageByBlockEvent event) {
      // System.out.println("-------------------------------onExplosion(EntityDamageByBlockEvent)--------------------------------"+event.getCause()+"----"+event.getDamager()+"-----"+event.getDamage()+"---------------------");
	   if (event.getEntityType()==EntityType.PLAYER&&event.getCause()==DamageCause.BLOCK_EXPLOSION){
		   Player play=(Player)event.getEntity();

		   //just gonna let it null pointer if it fails to peek, then i won't have to clutter the console with println dubugging
		   Plugina.selfharm=Plugina.explosionList.peek();
		   //if(Plugina.selfharm!=null){
				if(play!=Plugina.selfharm.getPlayer()){
					play.damage(0);//play a damage animation
					Plugina.playerMap.get(event.getEntity()).takeDamage(event.getDamage(),play);
					event.setCancelled(true);
					//System.out.println("Player takes damage because it's not the same player as in the exploData so it can't be immune.....play:"+play+" selfharm.getPlayer()"+Plugina.selfharm.getPlayer());
				}
				else if(!Plugina.selfharm.getDoesSelfDamage()){
					//System.out.println("Player not damaged by explosion because it's self inflicted(and it's set not to damage self)");
					event.setCancelled(true);
				}else{
					play.damage(0);
					Plugina.playerMap.get(event.getEntity()).takeDamage(event.getDamage(),play);
					event.setCancelled(true);
				}
			//}
			/*else{
				System.out.println("damaged by explosion because it's the list is null");
				play.damage(0);
				Plugina.playerMap.get(event.getEntity()).takeDamage(event.getDamage(),play);
				event.setCancelled(true);
				return;
			}*/
	   }else if(event.getEntityType()==EntityType.SLIME||event.getEntityType()==EntityType.CREEPER||event.getEntityType()==EntityType.ZOMBIE||event.getEntityType()==EntityType.PIG||event.getEntityType()==EntityType.SKELETON||event.getEntityType()==EntityType.ENDERMAN||event.getEntityType()==EntityType.SPIDER){
		   if(!Plugina.explosionList.isEmpty())
			   System.out.println("not a player taking damage yet the explosionList isn't empty, this should never happen");			   
		   return;
	   }else{
		   if(!Plugina.explosionList.isEmpty())
			   System.out.println("not a player taking damage yet the explosionList isn't empty, this should never happen");
		  // System.out.println("in the final else after the if it's a player and damagecause is blockexplosion, theirfore it's probably failing the check if it's entity_explosion instead");
		   //this should be things like blocks
		   return;
	   }
    }
    @EventHandler
    public void onEntityExplosion(EntityExplodeEvent event) {
    	//System.out.println("---------------------------------onEntityExplosion(EntityExplodeEvent)--------------------"+event.getEntity()+"-----------------");
 	   if(event.getEntity()==null&&!Plugina.explosionList.isEmpty()){
 		   //System.out.println("event.getEntity()==null and explosionlist isn't empty");
 		   ExploData ed=Plugina.explosionList.remove();
 		  //if(!ed.getDoesSelfDamage()){
 			   //Plugina.selfharm=ed;//if it's a reference i'm null pointer fucked
 		   //}
 		   if(!ed.getDoesBlockDamage()){
 			   event.blockList().clear();
 		   }
 		   if(!ed.getDoesAnimation()){
 			   event.setCancelled(true);
 		   }
 	   }
 	   else
 		   System.out.println("entityexplosion thats not of entityType null (or explosionlist is empty) both of these will probably occur with a creeper");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
    	if((event.getTo().getY()-event.getFrom().getY())==0.41999998688697815){
      		//vely=.5;//vel of 1.0 goes 5 blocks//.8 is enough for 4 blocks//.6 is two and a half
    		Vector truevel=event.getPlayer().getVelocity();
    		double xcomponentvel=event.getTo().getX()-event.getFrom().getX();
    		double zcomponentvel=event.getTo().getZ()-event.getFrom().getZ();
    		Vector newvel=new Vector(truevel.getX()+xcomponentvel,Plugina.jumpLevelArray[(Plugina.playerMap.get(event.getPlayer()).getJump())-1],truevel.getZ()+zcomponentvel);
    		event.getPlayer().setVelocity(newvel);//goes up 5 blocks at -1, 0.8 is 4, 0.6 is
    	}
    }
    @EventHandler
    public void onAnimation(PlayerAnimationEvent event){
    	System.out.println("animation:"+event.getAnimationType());
    	event.setCancelled(true);
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
    	//System.out.println("animation:"+event.getAnimationType());
    	Player p=(Player)event.getPlayer();
    	Plugina.playerMap.get(p).updateInventory(p);
    	//event.setCancelled(true);
    }
    public void onInventoryDrop(PlayerDropItemEvent event){//can really optimize this by splitting the remove and add functions in updateInventory
    	//System.out.println("animation:"+event.getAnimationType());
    	Player p=(Player)event.getPlayer();
    	Plugina.playerMap.get(p).updateInventory(p);
    	//event.setCancelled(true);
    }
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event){//only useful to add in swords you've picked up on the ground...doing so will give you a cooled down sword. so you can use that as an exploit for now
    	if(Plugina.playerMap.get(event.getPlayer()).isUnablePickup()){
    		event.setCancelled(true);
    		return;
    	}
    	Material itype=event.getPlayer().getInventory().getItemInHand().getType();
    	if(itype==Material.DIAMOND_SWORD||itype==Material.GOLD_SWORD||itype==Material.IRON_SWORD||itype==Material.STONE_SWORD||itype==Material.DIAMOND_SWORD){
    		//if it's a sword then check to see if it's been added to the hotbar and if it has then add it to the queue and maybe call an update function to update it's durability
    		//i'm just gonna call update anyway as long as it's a sword
        	Player p=(Player)event.getPlayer();
        	Plugina.playerMap.get(p).updateInventory(p);
    	}
    	System.out.println();
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTakeDamage(EntityDamageEvent event)
    {
    	//System.out.println("--------------------------------------onPlayerTakeDamage--------------------------entityType is:"+event.getEntityType()+"-------------cause:"+event.getCause()+"--------------------------");
    	//System.out.println("first line in onplayertakedamage, right before check to see if it's canceled");
        if (!event.isCancelled())
        {
         	int dam=event.getDamage();
        	EntityType ent=event.getEntityType();
        	DamageCause dc =event.getCause();
        	event.setDamage(0);
        	if(event.getEntityType()==EntityType.PLAYER){
        		//System.out.println("in onplayertakedamage, right after check to see if it's a player");
    			Player play=(Player)event.getEntity();
        		if (dc==DamageCause.FALL){
        			float sadpanda=event.getEntity().getFallDistance();
        			dam=((int)sadpanda-3)-Plugina.playerMap.get(event.getEntity()).getJump();//this is the fall damage formula as far as i can tell... now that i think about it i don't need to mimic notch on this one
        			if (dam<=0){
        				event.setCancelled(true);
        				return;
        			}
        		}
        		if (dc==DamageCause.BLOCK_EXPLOSION){
        			System.out.println("dc=BLOCK_EXPLOSION trying to take damage on onPlayerTakeDamage from the explosion, but no doubledipping, entitydamagedbyblock should take care of it");
        			return;
        		}
        		if (dc==DamageCause.ENTITY_EXPLOSION){
        			System.out.println("dc=ENTITY_EXPLOSION trying to take damage on onPlayerTakeDamage from the explosion, but no doubledipping, entitydamagedbyblock should take care of it");
        			return;
        		}
        		/*if (dc==DamageCause.BLOCK_EXPLOSION){
        		System.out.println("in OnPlayerDamage, DamageCause is Block explosion*************i don't know if i've ever seen this happen");
        		if(Plugina.selfharm!=null){
    				if(play!=Plugina.selfharm.getPlayer()){
    					play.damage(0);//play a damage animation
    					Plugina.playerMap.get(event.getEntity()).takeDamage(event.getDamage(),play);
    					System.out.println("Player damaged by explosion");
    					return;
    				}
    			}
    			else{
    				System.out.println("Player not damaged by explosion because it's self inflicted");
    				//play.damage(0);
    				event.setCancelled(true);
    				return;
    			}
        		}*/
        		System.out.println("not damaged by explosion or fall, generic damage");
        		Plugina.playerMap.get(event.getEntity()).takeDamage(dam,(Player)event.getEntity());
         		play.damage(0);
        		System.out.println("Player damaged by damageType:"+dc+" Damage would have been:"+dam);
        	}else if(ent ==EntityType.ZOMBIE||ent ==EntityType.WITHER||ent ==EntityType.WOLF||ent ==EntityType.WITCH||ent ==EntityType.VILLAGER||ent ==EntityType.SQUID||ent ==EntityType.SPIDER||ent ==EntityType.SNOWMAN||ent ==EntityType.SLIME||ent ==EntityType.SKELETON||ent ==EntityType.SILVERFISH||ent ==EntityType.SHEEP||ent ==EntityType.OCELOT||ent ==EntityType.BLAZE||ent ==EntityType.MUSHROOM_COW||ent ==EntityType.IRON_GOLEM||ent ==EntityType.PIG||ent ==EntityType.MAGMA_CUBE||ent ==EntityType.ENDERMAN||ent ==EntityType.ENDER_DRAGON||ent ==EntityType.CREEPER||ent ==EntityType.COW||ent ==EntityType.CHICKEN||ent ==EntityType.CAVE_SPIDER){
        		event.setDamage(dam);
        		return;
        	}else{
        		event.setDamage(dam);
        		return;
        	}
        }
    }
}
        	//if (dc==DamageCause.BLOCK_EXPLOSION&&event.getEntityType()==EntityType.PLAYER){
        		//System.out.println("Damage that isn't being canceled is: EXPLOSION");
        		//Plugina.playerMap.get(event.getEntity()).takeDamage(dam,(Player)event.getEntity());
        		//System.out.println("this line will probably crash it :Health:"+Plugina.playerMap.get(event.getEntity()).getCurrentHealth());
        	/*
        	if (dc==DamageCause.ENTITY_ATTACK&&event.getEntityType()==EntityType.PLAYER){
        		System.out.println("probably about to crash");
        		Plugina.playerMap.get(event.getEntity()).takeDamage(dam,(Player)event.getEntity());
        		System.out.println("this line will probably crash it :Health:"+Plugina.playerMap.get(event.getEntity()).getCurrentHealth());
        	}
        	
        	else if (dc==DamageCause.FALL&&event.getEntityType()==EntityType.PLAYER){
        		System.out.println("in FALL");
        		Plugina.playerMap.get(event.getEntity()).takeDamage(dam,(Player)event.getEntity());
        		System.out.println("this line will probably crash it :Health:"+Plugina.playerMap.get(event.getEntity()).getCurrentHealth());
        	}
        	else if (dc==DamageCause.DROWNING&&event.getEntityType()==EntityType.PLAYER){
        		System.out.println("in DROWNING");
        		Plugina.playerMap.get(event.getEntity()).takeDamage(dam,(Player)event.getEntity());
        		System.out.println("this line will probably crash it :Health:"+Plugina.playerMap.get(event.getEntity()).getCurrentHealth());
        	}
        	else if (dc==DamageCause.FIRE&&event.getEntityType()==EntityType.PLAYER){
        		System.out.println("in FIRE");
        		Plugina.playerMap.get(event.getEntity()).takeDamage(dam,(Player)event.getEntity());
        		event.setDamage(0);
        		System.out.println("this line will probably crash it :Health:"+Plugina.playerMap.get(event.getEntity()).getCurrentHealth());
        	}*/
        	//event.setDamage(0);
        	//event.setCancelled(true);
        	//Plugina.playerMap.get(event.getEntity()).calibrateHealth();

            // PLAYER ATTACK PLAYER
            //if ((event.getEntity() instanceof HumanEntity) && (event.getDamager() instanceof HumanEntity))
            //{
                // GET VICTIM AND ATTACKER
              //  Player victim = (Player) event.getEntity();
                //Player attacker = (Player) event.getDamager();
                
                // GET EVENT DAMAGE
                //int damage = event.getDamage();
                
                // SEND DAMAGE TO PLAYER - BECAUSE EVENT IS CANCELING
                //victim.damage(damage);
                
                // CANCEL EVENT - TO STOP KNOCKBACK EFFECT
                
            
   
    //@EventHandler
   // public void onPlayerTakeDamage(PlayerDamagePlayer event)
//System.out.println("x velocity is:"+event.getPlayer().getVelocity().getX());
//System.out.println("y velocity is:"+event.getPlayer().getVelocity().getY());
//System.out.println("z velocity is:"+event.getPlayer().getVelocity().getZ());
//0.11760000228882461 is the y change for ladders(or vines at least)
//0.41999998688697815 is the y change for jump
//if((event.getTo().getY()-event.getFrom().getY())==0.11760000228882461){
//	System.out.println("Climbing");
//}

