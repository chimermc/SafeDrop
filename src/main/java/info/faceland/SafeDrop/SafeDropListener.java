/**
 * The MIT License
 * Copyright (c) 2015 Teal Cube Games
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.faceland.SafeDrop;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SafeDropListener implements Listener {

    private final SafeDropPlugin plugin;

    public SafeDropListener(SafeDropPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDrop(PlayerDropItemEvent event){
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        if(!plugin.playersInInventory.contains((event.getPlayer().toString())) && (!plugin.playersWithPreferenceSet.contains(event.getPlayer().toString()) == plugin.getConfig().getBoolean("settings.drops-disabled-by-default"))){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.YELLOW + plugin.getConfig().getString("settings.on-drop-stop-message"));
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInventoryClick(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        if(!plugin.playersInInventory.contains(event.getWhoClicked().toString())) {
            plugin.playersInInventory.add(event.getWhoClicked().toString());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInventoryClose(InventoryCloseEvent event){
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        if(plugin.playersInInventory.contains(event.getPlayer().toString())) {
            plugin.playersInInventory.remove(event.getPlayer().toString());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event){
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        if(plugin.playersInInventory.contains(event.getPlayer().toString())) {
            plugin.playersInInventory.remove(event.getPlayer().toString());
        }
    }

}
