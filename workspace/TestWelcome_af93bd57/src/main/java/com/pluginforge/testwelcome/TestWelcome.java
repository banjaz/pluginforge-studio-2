package com.pluginforge.testwelcome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TestWelcome - A simple welcome plugin that sends a message when players join.
 *
 * Plugin Version: 1.0.0
 * Minecraft / Spigot API Target: 1.20.1 (api-version: 1.20)
 *
 * This plugin demonstrates good practices:
 * - Clear separation of concerns (main class + listener)
 * - Usage of configuration file for customizable messages
 * - Safe color code handling
 * - Proper registration of event listeners
 */
public class TestWelcome extends JavaPlugin implements Listener {

    // Cached configuration instance for convenience
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Save default configuration if it does not exist
        saveDefaultConfig();
        this.config = getConfig();

        // Register this class as a listener for player join events
        Bukkit.getPluginManager().registerEvents(this, this);

        // Log a startup message to the console
        getLogger().info("TestWelcome v" + getDescription().getVersion() + " has been enabled.");
    }

    @Override
    public void onDisable() {
        // Log a shutdown message to the console
        getLogger().info("TestWelcome has been disabled.");
    }

    /**
     * Handles the PlayerJoinEvent and sends a welcome message
     * to the player who joined the server.
     *
     * The message is read from the config.yml and supports color codes using '&'.
     * Placeholder {player} is replaced with the joining player's name.
     *
     * @param event the PlayerJoinEvent
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Retrieve the message from configuration with a default value as fallback
        String rawMessage = config.getString("welcome-message", "&aWelcome to the server, {player}!");

        // Replace placeholder with the player's name
        String processedMessage = rawMessage.replace("{player}", player.getName());

        // Translate color codes using '&' to Bukkit color codes
        processedMessage = ChatColor.translateAlternateColorCodes('&', processedMessage);

        // Send the message to the player
        player.sendMessage(processedMessage);
    }
}
