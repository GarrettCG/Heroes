package com.github.odin8472.Plugina;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

//import com.google.common.base.Joiner;

/*
 * This is a sample CommandExectuor
 */
public class PluginaCommandExecutor implements CommandExecutor {
    private final Plugina plugin;

    /*
     * This command executor needs to know about its plugin from which it came from
     */
    public PluginaCommandExecutor(Plugina plugin) {
        this.plugin = plugin;
    }

    /*
     * On command set the sample message
     */
    /*public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("sample.message") && args.length > 0) {
            this.plugin.getConfig().set("sample.message", Joiner.on(' ').join(args));
            return true;
        } else {
            return false;
        }
    }*/
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		System.out.println("onCommand");
		if(cmd.getName().equalsIgnoreCase("basic")){ // If the player typed /basic then do the following...
			Player player = (Player) sender;
			//getLogger().info("basic used!");
			PlayerInventory inventory = player.getInventory(); // The player's inventory
		    ItemStack itemstack = new ItemStack(Material.DIAMOND_SWORD, 1); // A stack of diamonds
		    inventory.addItem(itemstack);
		    ItemStack itemstack2 = new ItemStack(Material.GOLD_SWORD, 1); // A stack of diamonds
		    inventory.addItem(itemstack2);
		    ItemStack itemstack3 = new ItemStack(Material.STONE_SWORD, 1); // A stack of diamonds
		    inventory.addItem(itemstack3);
		    ItemStack itemstack4 = new ItemStack(Material.IRON_SWORD, 1); // A stack of diamonds
		    inventory.addItem(itemstack4);
		    ItemStack itemstack5 = new ItemStack(Material.WOOD_SWORD, 1); // A stack of diamonds
		    inventory.addItem(itemstack5);
		    player.sendMessage("Here's a basic class items");
		   // int itemCode = 278;  //use the item code you want here
		    //int effectId = 32;  //use the enchantment code you want here
		    //String bob=new String(args[0]);
		    //int foo = Integer.parseInt(bob);
		    //int enchantmentLevel = foo;
		   // ItemStack myItem = new ItemStack(itemCode);  //new item of item code
		   // Enchantment myEnchantment = new EnchantmentWrapper(effectId);  //new enchantment of effect id
		    //myItem.addEnchantment(myEnchantment, enchantmentLevel);  //enchant the item
		   // inventory.addItem(myItem);
		    player.sendMessage("heres some random shit from the tutorial");
			return true;
		} //If this has happened the function will return true. 
	        // If this hasn't happened the a value of false will be returned.
		return false; 
	}

}