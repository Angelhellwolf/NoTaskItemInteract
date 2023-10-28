package com.remiaft.notaskiteminteract;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public final class NoTaskItemInteract extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this,this);
        // Plugin startup logic
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if (e.getPlayer().hasPermission("nodrop.bypass")) return;
//        if (e.getItemDrop() == null || e.getItemDrop().getItemStack() == null) return;
        if (hasLoreString(e.getItemDrop().getItemStack(),"任务道具")){
            e.setCancelled(true);
            e.getPlayer().sendMessage("§c该物品为任务道具，禁止丢弃");
        }

    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (e.getWhoClicked().hasPermission("nodrop.bypass")) return;
        if (e.getInventory().getType() != InventoryType.CRAFTING && e.getInventory().getType() != InventoryType.MERCHANT){
            if (e.getClickedInventory() == null) return;
            if (e.getClickedInventory().getType() == InventoryType.PLAYER || e.getClick() == ClickType.NUMBER_KEY
                    || e.getClick() == ClickType.CONTROL_DROP){
                if (e.getCurrentItem() != null && hasLoreString(e.getCurrentItem(),"任务道具")){
                    e.setCancelled(true);
                }

            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public boolean hasLoreString(ItemStack item, String searchString) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasLore()) {
                for (String lore : meta.getLore()) {
                    if (lore.contains(searchString)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
