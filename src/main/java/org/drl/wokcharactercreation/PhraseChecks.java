package org.drl.wokcharactercreation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.A;

public class PhraseChecks {
    Plugin plugin;
    PhraseChecks(Plugin plugin)
    {
        this.plugin = plugin;
        CreateBannedPhraseLists();
    }
    ArrayList<String> bannedWords = new ArrayList<String>();



    public void CreateBannedPhraseLists(){

        File file = new File(plugin.getDataFolder(), "bannedWords.txt");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            BufferedReader br  = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
            {
                bannedWords.add(line);
            }
            br.close();

        }
        catch (IOException exception)
        {
            plugin.getLogger().info(exception.getMessage());
        }


    }


    boolean ContainsBannedWord(String message)
    {
        for (String slur : bannedWords)
        {
            if (message.toLowerCase().contains(slur.toLowerCase())) {return true;}


        }
        return false;
    }

}
