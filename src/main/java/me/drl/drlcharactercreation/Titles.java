package me.drl.drlcharactercreation;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Titles {


    static void SendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        player.sendTitle(title,subtitle,fadeIn,stay,fadeOut);
    }
    static void SendTitle(Player player, String title, int fadeIn, int stay, int fadeOut)
    {
        SendTitle(player,title,"",fadeIn,stay,fadeOut);
    }


    static String format(String message)
    {
        return ChatColor.translateAlternateColorCodes('&',message);
    }
}
