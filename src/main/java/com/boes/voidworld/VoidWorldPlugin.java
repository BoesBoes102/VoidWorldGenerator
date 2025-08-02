package com.boes.voidworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class VoidWorldPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("VoidWorldPlugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("VoidWorldPlugin disabled.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("generatevoidworld")) {
            String worldName = "world1";

            if (Bukkit.getWorld(worldName) != null) {
                sender.sendMessage(ChatColor.RED + "World '" + worldName + "' already exists!");
                return true;
            }

            sender.sendMessage(ChatColor.YELLOW + "Generating void world: " + worldName);

            WorldCreator creator = new WorldCreator(worldName);
            creator.generator(new ChunkGenerator() {
                @Override
                public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
                    return createChunkData(world); // returns an empty chunk (void)
                }
            });

            World world = creator.createWorld();

            if (world != null) {
                sender.sendMessage(ChatColor.GREEN + "Void world '" + worldName + "' created successfully!");
            } else {
                sender.sendMessage(ChatColor.RED + "Failed to create the world.");
            }
            return true;
        }
        return false;
    }
}
