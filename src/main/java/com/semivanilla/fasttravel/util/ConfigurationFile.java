package com.semivanilla.fasttravel.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigurationFile {
    private String name;
    private JavaPlugin plugin;
    private File customConfigFile;
    private FileConfiguration customConfig;

    public ConfigurationFile(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        customConfigFile = new File(plugin.getDataFolder(), name);
        if(!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        }catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCustomConfig() {
        return customConfig;
    }
}
