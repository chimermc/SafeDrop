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

import com.tealcube.minecraft.bukkit.facecore.plugin.FacePlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import io.pixeloutlaw.minecraft.spigot.config.MasterConfiguration;
import io.pixeloutlaw.minecraft.spigot.config.SmartYamlConfiguration;
import io.pixeloutlaw.minecraft.spigot.config.VersionedConfiguration;
import io.pixeloutlaw.minecraft.spigot.config.VersionedSmartYamlConfiguration;
import com.tealcube.minecraft.bukkit.facecore.logging.PluginLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.HandlerList;
import se.ranzdo.bukkit.methodcommand.CommandHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class SafeDropPlugin extends FacePlugin {

    //private PluginLogger debugPrinter;
    private VersionedSmartYamlConfiguration configYAML;
    private VersionedSmartYamlConfiguration dataYAML;

    protected List<String> playersWithPreferenceSet;
    protected List<String> playersInInventory;

    @Override
    public void enable() {
        //debugPrinter = new PluginLogger(this);
        configYAML = new VersionedSmartYamlConfiguration(new File(getDataFolder(), "config.yml"),
                getResource("config.yml"),
                VersionedConfiguration.VersionUpdateType
                        .BACKUP_AND_UPDATE);
        /*if (configYAML.update()) {
            getLogger().info("Updating config.yml");
            debug("Updating config.yml");
        }*/
        dataYAML = new VersionedSmartYamlConfiguration(new File(getDataFolder(), "data.yml"),
                getResource("data.yml"),
                VersionedConfiguration.VersionUpdateType
                        .BACKUP_AND_UPDATE);
        /*if (dataYAML.update()) {
            getLogger().info("Updating data.yml");
            debug("Updating data.yml");
        }*/
        playersWithPreferenceSet = dataYAML.getStringList("PlayersWithPreferenceSet");
        playersInInventory = new ArrayList<String>();
        Bukkit.getPluginManager().registerEvents(new SafeDropListener(this), this);
        CommandHandler commandHandler = new CommandHandler(this);
        commandHandler.registerCommands(new SafeDropCommand(this));
    }

    @Override
    public void disable() {
        dataYAML.set("PlayersWithPreferenceSet", playersWithPreferenceSet);
        dataYAML.save();

        HandlerList.unregisterAll(this);

    }

    /*
    public void debug(String... messages) {
        debug(Level.INFO, messages);
    }

    public void debug(Level level, String... messages) {
        if (debugPrinter != null && (configYAML == null || configYAML.getBoolean("settings.debug", false))) {
            debugPrinter.log(level, Arrays.asList(messages));
        }
    }*/
}
