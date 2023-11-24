package org.drl.wokcharactercreation;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Titles {

    static void SendActionBar(Player player, String text)
    {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
    }
    static void SendActionBar(List<Player> players, String text)
    {
        for (Player player : players)
        {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
        }

    }
    @SuppressWarnings("unchecked")
    static void SendActionBarAll(String text)
    {
        SendActionBar((List<Player>) Bukkit.getOnlinePlayers(), text);
    }
    static void SendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        player.sendTitle(title,subtitle,fadeIn,stay,fadeOut);
    }
    static void SendTitle(Player player, String title, int fadeIn, int stay, int fadeOut)
    {
        SendTitle(player,title,"",fadeIn,stay,fadeOut);
    }

    static void SendTitle(Player player, String title, int stay)
    {
        SendTitle(player,title,"",10,stay,10);
    }
    static void SendTitle(Player player, String title, String subtitle, int stay)
    {
        SendTitle(player,title,subtitle,10,stay,10);
    }
    static void SendTitle(Player player, String title, String subtitle)
    {
        SendTitle(player,title,subtitle,10,70,10);
    }
    static void SendTitle(Player player, String title)
    {
        SendTitle(player,title,"",10,70,10);
    }


    static void SendTitle(List<Player> players, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        for (Player player : players)
        {
            SendTitle(player,title,subtitle,fadeIn,stay,fadeOut);
        }

    }

    @SuppressWarnings("unchecked")
    static void SendTitleToAll(String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        SendTitle((List<Player>)Bukkit.getOnlinePlayers(),title,subtitle,fadeIn,stay,fadeOut);
    }

    static String format(String message)
    {
        return ChatColor.translateAlternateColorCodes('&',message);
    }
}
