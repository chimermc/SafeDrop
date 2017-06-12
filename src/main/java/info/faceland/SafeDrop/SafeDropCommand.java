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

import com.tealcube.minecraft.bukkit.facecore.utilities.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import se.ranzdo.bukkit.methodcommand.Command;

public class SafeDropCommand {

    private final SafeDropPlugin plugin;

    public SafeDropCommand(SafeDropPlugin plugin) {
        this.plugin = plugin;
    }

    @Command(identifier = "drop", permissions = "safedrop.drop")
    public void dropSubcommand(Player sender){
        if(plugin.playersWithPreferenceSet.contains(sender.getUniqueId().toString())){
            plugin.playersWithPreferenceSet.remove((sender.getUniqueId().toString()));
            //sender.sendMessage(ChatColor.GREEN + plugin.getConfig().getString("settings.on-disable-message"));
            MessageUtils.sendMessage(sender, plugin.getConfig().getString("settings.on-disable-message"));
        }
        else
        {
            plugin.playersWithPreferenceSet.add((sender.getUniqueId().toString()));
            //sender.sendMessage(ChatColor.GREEN + plugin.getConfig().getString("settings.on-enable-message"));
            MessageUtils.sendMessage(sender, plugin.getConfig().getString("settings.on-enable-message"));
        }
    }

}
