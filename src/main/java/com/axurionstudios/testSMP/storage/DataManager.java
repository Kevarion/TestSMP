package com.axurionstudios.testSMP.storage;

import com.axurionstudios.testSMP.TestSMP;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataManager {

    private final File playerDataFile;
    @Getter private final FileConfiguration playerDataConfig;

    public DataManager(TestSMP instance){
        playerDataFile = new File(instance.getDataFolder(), "playerData.yml");

        if (!playerDataFile.exists()){
            try {
                playerDataFile.getParentFile().mkdirs();
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public void saveDataConfig(){
        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initializePlayerDataConfig(){
        if (!playerDataConfig.contains("players")) {
            playerDataConfig.createSection("players");
            saveDataConfig();
        }
    }

    public void reloadPlayerDataConfig(){
        try {
            playerDataConfig.load(playerDataFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
