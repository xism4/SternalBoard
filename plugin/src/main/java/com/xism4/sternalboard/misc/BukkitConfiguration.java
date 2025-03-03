package com.xism4.sternalboard.misc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

public class BukkitConfiguration {

    private final File file;
    private FileConfiguration config;

    private BukkitConfiguration(final Plugin plugin, final String fileName) {
        final File folder = plugin.getDataFolder();
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IllegalStateException("Plugin folder" + folder.getName() + "cannot be created");
        }

        this.file = new File(folder, fileName + ".yml");

        if (!file.exists()) {
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(file.getName())) {
                if (stream != null) {
                    Files.copy(stream, file.toPath());
                }
            } catch (IOException e) {
                throw new UncheckedIOException("An error occurred while loading file '" + fileName + "'.", e);
            }
        }
        reload();
    }

    public static BukkitConfiguration create(final Plugin plugin, final String fileName) {
        return new BukkitConfiguration(plugin, fileName);
    }

    @NotNull
    public FileConfiguration get() {
        if (config == null) {
            reload();
        }
        return config;
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new UncheckedIOException("An error occurred while saving file '" + file.getName() + "'.", e);
        }
    }
}
