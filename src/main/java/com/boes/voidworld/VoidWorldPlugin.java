package com.boes.voidworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.block.Biome;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
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
        creator.generator(new VoidGenerator());
        creator.biomeProvider(new VoidBiomeProvider());
        creator.generateStructures(false);

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

    private static class VoidGenerator extends ChunkGenerator {
        @Override
        public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        }

        @Override
        public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        }

        @Override
        public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        }

        @Override
        public boolean shouldGenerateCaves() {
            return false;
        }

        @Override
        public boolean shouldGenerateDecorations() {
            return false;
        }

        @Override
        public boolean shouldGenerateMobs() {
            return false;
        }

        @Override
        public boolean shouldGenerateStructures() {
            return false;
        }
    }

    private static class VoidBiomeProvider extends BiomeProvider {
        @Override
        public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
            return Biome.THE_VOID;
        }

        @Override
        public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
            return Collections.singletonList(Biome.THE_VOID);
        }
    }
}
