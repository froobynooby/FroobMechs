package com.froobworld.froobmechs.data;

import com.froobworld.froobmechs.managers.PlayerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Playerdata {
    private PlayerManager playerManager;

    private File file;
    private YamlConfiguration config;

    private UUID uuid;

    private boolean pvp;
    private int stone, coal, iron, lapis, diamond, gold, emerald, redstone;

    public Playerdata(File file, PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.file = file;
        config = YamlConfiguration.loadConfiguration(file);
        load();
    }

    public Playerdata(Player player, File file, PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.file = file;
        config = YamlConfiguration.loadConfiguration(file);
        uuid = player.getUniqueId();
        pvp = false;
        stone = 0;
        coal = 0;
        iron = 0;
        lapis = 0;
        diamond = 0;
        gold = 0;
        emerald = 0;
        redstone = 0;

        config.set("uuid", uuid.toString());
        config.set("pvp.enabled", false);
        config.set("ores.stone", 0);
        config.set("ores.coal", 0);
        config.set("ores.iron", 0);
        config.set("ores.lapis", 0);
        config.set("ores.diamond", 0);
        config.set("ores.gold", 0);
        config.set("ores.emerald", 0);
        config.set("ores.redstone", 0);

        save();
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(value * 100);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public UUID getUUID() {

        return uuid;
    }

    public boolean isPvp() {

        return pvp;
    }

    public void togglePvp() {

        pvp = !pvp;
        playerManager.addToSaveQueue(this);
    }

    public void incrementStone() {
        stone += 1;
        playerManager.addToSaveQueue(this);
    }

    public void incrementCoal() {
        coal += 1;
        playerManager.addToSaveQueue(this);
    }

    public void incrementIron() {
        iron += 1;
        playerManager.addToSaveQueue(this);
    }

    public void incrementLapis() {
        lapis += 1;
        playerManager.addToSaveQueue(this);
    }

    public void incrementDiamond() {
        diamond += 1;
        playerManager.addToSaveQueue(this);
    }

    public void incrementGold() {
        gold += 1;
        playerManager.addToSaveQueue(this);
    }

    public void incrementEmerald() {
        emerald += 1;
        playerManager.addToSaveQueue(this);
    }

    public void incrementRedstone() {
        redstone += 1;
        playerManager.addToSaveQueue(this);
    }

    public int getStone() {
        return stone;
    }

    public int getCoal() {
        return coal;
    }

    public int getIron() {
        return iron;
    }

    public int getLapis() {
        return lapis;
    }

    public int getDiamond() {
        return diamond;
    }

    public int getGold() {
        return gold;
    }

    public int getEmerald() {
        return emerald;
    }

    public int getRedstone() {
        return redstone;
    }

    public int getTotal() {
        return stone + coal + iron + lapis + diamond + gold + emerald + redstone;
    }

    public double pct(int qty) {
        if (getTotal() == 0) {

            return 0;
        }
        return round(((double) qty) / ((double) getTotal()));
    }

    public void load() {
        uuid = UUID.fromString(config.getString("uuid"));
        pvp = config.getBoolean("pvp.enabled");

        stone = config.getInt("ores.stone");
        coal = config.getInt("ores.coal");
        iron = config.getInt("ores.iron");
        lapis = config.getInt("ores.lapis");
        diamond = config.getInt("ores.diamond");
        gold = config.getInt("ores.gold");
        emerald = config.getInt("ores.emerald");
        redstone = config.getInt("ores.redstone");
    }

    public void save() {
        config.set("pvp.enabled", pvp);
        config.set("ores.stone", stone);
        config.set("ores.coal", coal);
        config.set("ores.iron", iron);
        config.set("ores.lapis", lapis);
        config.set("ores.diamond", diamond);
        config.set("ores.gold", gold);
        config.set("ores.emerald", emerald);
        config.set("ores.redstone", redstone);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
