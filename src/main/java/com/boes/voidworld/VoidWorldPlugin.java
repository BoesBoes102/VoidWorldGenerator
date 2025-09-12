package com.boes.voidworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class VoidWorldPlugin extends JavaPlugin implements TabExecutor {

    @Override
    public void onEnable() {
        getLogger().info("VoidWorldPlugin enabled.");
        Objects.requireNonNull(Objects.requireNonNull(this.getCommand("generatevoidworld"))).setExecutor(this);
        Objects.requireNonNull(this.getCommand("generatevoidworld")).setTabCompleter(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("VoidWorldPlugin disabled.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("generatevoidworld")) return false;

        if (!sender.hasPermission("voidworld.generate")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        String worldName = (args.length > 0) ? args[0] : "world1";

        if (Bukkit.getWorld(worldName) != null) {
            sender.sendMessage(ChatColor.RED + "World '" + worldName + "' already exists!");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Generating void world: " + worldName);

        WorldCreator creator = new WorldCreator(worldName);
        creator.generator(new ChunkGenerator() {
            @Override
            public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
                return createChunkData(world);
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("world1");
            completions.add("world2");
            completions.add("world3");
        }
        return completions;
    }
}
