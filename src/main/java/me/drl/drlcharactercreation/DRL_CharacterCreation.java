package me.drl.drlcharactercreation;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

enum Gender
{
    Male,
    Female,
    NonBinary,
    GenderFluid,
    GenderNeutral,
    Other

}
public final class DRL_CharacterCreation extends JavaPlugin implements Listener, CommandExecutor {

    ArrayList<Player> currentlySelectingName = new ArrayList<>();
    ArrayList<Player> currentlySelectingGender = new ArrayList<>();
    ArrayList<Player> inProcessOfJoining = new ArrayList<>();

    ArrayList<ItemStack[]> playersInventories = new ArrayList<>();


    Location spawnLocation = new Location(null,0,100,0);
    Location chooseLocation = new Location(null,0,0,0);
    InteractiveUI chestUI = new InteractiveUI();

    Plugin plugin = this;
    PhraseChecks checks = new PhraseChecks(this);

    boolean spawnParticles = false;
    boolean doCreateNewCharacters = true;

    FileConfiguration config = this.getConfig();

    String serverName = "";

    boolean doWelcomeMessage;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this,this);
        LoadConfig();
        getLogger().info("- CHARACTER CREATOR ENABLED -");

        // Temporary: Removes any saved data from the player to do with RP
        this.getCommand("new-character").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                if (!(commandSender instanceof Player))
                {
                    return false;
                }
                Player p = (Player) commandSender;
                if (!doCreateNewCharacters) {p.sendMessage("Server has currently disabled the creation of new characters!"); return true;}
                ResetPlayerCharacter(p);
                IntroductorySequence(p);
                inProcessOfJoining.add(p);
                p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,999999,255));
                p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,999999,255));

                return true;
            }
        });

        this.getCommand("realname").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                Collection<? extends Player> playersOnline = Bukkit.getOnlinePlayers();
                if (!(commandSender instanceof Player)) {return false;}
                Player player = (Player)commandSender;
                if (strings[0] == "")
                {
                    player.sendMessage(Titles.format("&c") + "[RP] You didn't specify a name!");
                    return true;
                }
                String input = "";
                for (String st : strings)
                {
                    input +=  st + " ";
                }
                input = input.substring(0,input.length()-1);
                for (Player p : playersOnline)
                {
                    PersistentDataContainer container = p.getPersistentDataContainer();
                    if (container.has(NamespacedKey.fromString("rpName"),PersistentDataType.STRING))
                    {
                        String name = container.get(NamespacedKey.fromString("rpName"),PersistentDataType.STRING);
                        if (input.equals(name)) {
                            player.sendMessage(Titles.format("&a") + "[RP] The real name of " + input + " is " + p.getName());
                            return true;
                        }
                    }

                }
                player.sendMessage(Titles.format("&c") + "[RP] There are no online players that match the name of " + input + "! Please enter their RP name, as seen in tablist.");

                return true;
            }
        });

        this.getCommand("playerinfo").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                Collection<? extends Player> playersOnline = Bukkit.getOnlinePlayers();
                if (!(commandSender instanceof Player)) {return false;}
                Player player = (Player)commandSender;
                if (strings[0] == "")
                {
                    player.sendMessage(Titles.format("&c") + "[RP] You didn't specify a name!");
                    return true;
                }
                String input = "";
                for (String st : strings)
                {
                    input += st + " ";
                }
                input = input.substring(0,input.length()-1);
                for (Player p : playersOnline)
                {
                    PersistentDataContainer data = p.getPersistentDataContainer();
                    if (data.has(NamespacedKey.fromString("rpName"),PersistentDataType.STRING)) {
                        String name = data.get(NamespacedKey.fromString("rpName"),PersistentDataType.STRING);
                        if (input.equals(name)) {
                            NamespacedKey rpGender = new NamespacedKey(plugin, "rpGender");
                            player.sendMessage(Titles.format("&a") + Titles.format("&l") + "--- " + input + " [" + p.getName() + "] ---\n" + Titles.format("&r") + Titles.format("&a") + "Gender: " + Titles.format("&r") + IntegerToGender(data.get(rpGender, PersistentDataType.INTEGER)).toString() + "\n");
                            return true;
                        }
                    }

                }
                player.sendMessage(Titles.format("&c") + "[RP] There are no online players that match the name of " + input + "! Please specify their RP name.");

                return true;
            }
        });
        this.getCommand("rpname").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                Collection<? extends Player> playersOnline = Bukkit.getOnlinePlayers();
                if (!(commandSender instanceof Player)) {return false;}
                Player player = (Player)commandSender;
                if (strings[0] == "")
                {
                    player.sendMessage(Titles.format("&c") + "[RP] You didn't specify a name!");
                    return true;
                }


                for (Player p : playersOnline)
                {
                    PersistentDataContainer container = p.getPersistentDataContainer();
                    if (container.has(NamespacedKey.fromString("rpName"),PersistentDataType.STRING))
                    {
                        String name = container.get(NamespacedKey.fromString("rpName"),PersistentDataType.STRING);
                        if (strings[0].equals(name)) {
                            player.sendMessage(Titles.format("&a") + "[RP] The role play of " + strings[0] + " is " + p.getDisplayName());
                            return true;
                        }
                    }


                }
                player.sendMessage(Titles.format("&c") + "[RP] There are no online players that match the name of " + strings[0] + "!");

                return true;
            }
        });
        this.getCommand("reloadBannedWords").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                if (commandSender.isOp())
                {
                    spawnParticles = Boolean.parseBoolean(strings[0]);
                }
                else
                {
                    commandSender.sendMessage("You don't have permission to do this!");
                }
                return true;
            }
        });

        this.getCommand("cc-reloadconfig").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                if (commandSender.isOp())
                {
                    RefreshConfig();
                }
                else
                {
                    commandSender.sendMessage("You don't have permission to do this!");
                }
                return true;
            }
        });

    }

    @Override
    public void onDisable() {
        getLogger().info("- CHARACTER CREATOR DISABLED -");
    }



    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event)
    {

        // Checks if the player has RP data set, if not, then it creates it
        // If the player does have RP data, it renames them etc
        PersistentDataContainer data = event.getPlayer().getPersistentDataContainer();
        NamespacedKey rpName = new NamespacedKey(this,"rpName");
        chooseLocation.setWorld(event.getPlayer().getWorld());
        if (data.has(rpName, PersistentDataType.STRING))
        {
            PlayerPostSetData(event.getPlayer());
            return;
        }
        if (!doCreateNewCharacters) {return;}
        event.getPlayer().addPotionEffect(PotionEffectType.WEAKNESS.createEffect(999999,255));
        IntroductorySequence(event.getPlayer());
        inProcessOfJoining.add(event.getPlayer());
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,999999,255));
    }

    @EventHandler
    public void SendChatMessage(AsyncPlayerChatEvent event)
    {
        if (inProcessOfJoining.contains(event.getPlayer())) {event.setCancelled(true);} else {return;}
        // Detects whether the person sending the message is selecting their Username
        // If they are, stop the message and save it
        Player p = event.getPlayer();
        event.getRecipients().remove(p);
        if (currentlySelectingName.contains(p))
        {
            String username = event.getMessage();
            SetPlayerName(p, username);
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void PlayerMoveAttempt(PlayerMoveEvent event)
    {
        // Stops the player from being able to move when in section mode
        if (inProcessOfJoining.contains(event.getPlayer()))
        {
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(),0,0,0));
        }
    }

    @EventHandler
    public void InventoryInteraction(InventoryClickEvent event)
    {
        if (inProcessOfJoining.contains((Player)event.getWhoClicked()))
        {
            Inventory inventory = event.getInventory();
            if (event.getInventory() == chestUI.genderSelector)
            {
                if (event.getSlotType() != InventoryType.SlotType.OUTSIDE)
                {
                    int slot = event.getSlot();
                    if (slot == 11 || slot == 12 || slot == 13 || slot == 14 || slot == 15)
                    {

                        Player player = (Player)event.getWhoClicked();
                        currentlySelectingGender.remove(player);
                        int point = slot-11;

                        Gender gender = IntegerToGender(point);
                        FinaliseToList(player,gender);

                    }
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void InventoryClose(InventoryCloseEvent event)
    {
        if (currentlySelectingGender.contains((Player)event.getPlayer())) {
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    chestUI.OpenGUI((Player) event.getPlayer());
                }
            };
            runnable.runTaskLater(this,5);
        }

    }

    void IntroductorySequence(Player player)
    {
        // Introduces the player
        playersInventories.add(player.getInventory().getContents());
        player.getInventory().clear();
        player.teleport(new Location(player.getWorld(),0,0,0));
        player.playSound(player.getLocation(),Sound.ITEM_GOAT_HORN_SOUND_0,1,1);
        Titles.SendTitle(player,Titles.format("&6")+serverName,10,60,30);
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                StartSelectCharacterName(player);

            }
        };

        runnable.runTaskLater(this,110);
    }

    void StartSelectCharacterName(Player player)
    {
        // Adds the player to being able to select their name
        PingSound(player,.5f);
        player.sendMessage("Welcome to "+Titles.format("&6")+Titles.format("&l")+serverName + "\n"+Titles.format("&r")+"Lets create you an awesome character!\n\n"+Titles.format("&a")+"Enter your new name!\n");
        currentlySelectingName.add(player);
    }

    void SetPlayerName(Player player, String name)
    {
        if (checks.ContainsBannedWord(name)) {
            FailSound(player, 1);
            player.sendMessage("You cannot name your character " + name + "! Please try something else");
            return;
        }

        // Tries to set the players name, if fails, then re-prompts them to et their name
        if (name.length() <= 20)
        {
            if (name.length() < 3)
            {
                FailSound(player, 1);
                player.sendMessage("Name too short! Must be 3 characters or more...");
                return;
            }
            PingSound(player, 1);
            currentlySelectingName.remove(player);
            player.setDisplayName(name);
            player.setPlayerListName(name);
            player.setCustomName(name);
            player.setCustomNameVisible(true);
            StartSelectGender(player);
        }
        else
        {
            FailSound(player, 1);
            player.sendMessage("Name too long! Must be 20 characters or less...");
        }

    }

    void StartSelectGender(Player player)
    {
        // Prompts the player to select their characters gender
        currentlySelectingGender.add(player);
        player.sendMessage(Titles.format("&a")+"Now Select your Gender!\n");
        BukkitRunnable waitToOpen = new BukkitRunnable() {
            @Override
            public void run() {
                chestUI.OpenGUI(player);
            }
        };
        waitToOpen.runTaskLater(this,20);
    }


    void FinaliseToList(Player player, Gender gender)
    {

        PingSound(player,1);
        player.closeInventory();
        int point = inProcessOfJoining.indexOf(player);
        player.getInventory().setContents(playersInventories.get(point));

        // Sets the players data
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey rpName = new NamespacedKey(this,"rpName");
        NamespacedKey rpGender = new NamespacedKey(this,"rpGender");
        data.set(rpName,PersistentDataType.STRING,player.getDisplayName());
        data.set(rpGender,PersistentDataType.INTEGER,GenderToInteger(gender));

        // Tells the player the gender they selected and removes them from lists
        player.sendMessage(Titles.format("&a")+"You have selected " + gender.toString() + ". You can change this at any time with /new-character");


        inProcessOfJoining.remove(player);
        playersInventories.remove(point);
        // Sends an alert to the server so people can welcome the player
        // Sends them to spawn region
        if (doWelcomeMessage) {
            getServer().broadcastMessage(Titles.format("&e") + Titles.format("&l") + "[!] Everyone welcome " + player.getDisplayName() + " [" + player.getName() + "] to the server!");
        }
        Titles.SendTitle(player,Titles.format("&e")+"Welcome, " + player.getDisplayName(),Titles.format("&e")+ " to "+serverName+"\n",10,40,20);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.removePotionEffect(PotionEffectType.WEAKNESS);
        // Runs a cool particle affect and teleports the player
        PlayerJoinParticle(player);

        BukkitRunnable waitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                spawnLocation.setWorld(player.getWorld());
                player.teleport(spawnLocation);

                BukkitRunnable tpAgainChecker = new BukkitRunnable() {
                    @Override
                    public void run() {
                        // Runs a cool particle affect and teleports the player
                        spawnLocation.setWorld(player.getWorld());
                        player.teleport(spawnLocation);
                        WelcomeSound(player, 1);

                    }
                };

                tpAgainChecker.runTaskLater(plugin,5);
            }
        };
        waitRunnable.runTaskLater(this,40);

    }

    Gender IntegerToGender(int index)
    {
        Gender g;
        switch (index)
        {
            case 0: g =Gender.Male;
                break;
            case 1: g=  Gender.Female;
                break;
            case 2: g = Gender.NonBinary;
                break;
            case 3: g = Gender.GenderFluid;
                break;
            case 4: g = Gender.GenderNeutral;
                break;
            default: g= Gender.Other;
                break;
        }
        return g;
    }
    int GenderToInteger(Gender gender)
    {
        if (gender == Gender.Male) {return 0; }
        if (gender == Gender.Female) {return 1; }
        if (gender == Gender.NonBinary) {return 2; }
        if (gender == Gender.GenderFluid) {return 3; }
        if (gender == Gender.GenderNeutral) {return 4; }
        return 5;
    }


    void ResetPlayerCharacter(Player player)
    {
        // Clears persistent data
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey rpName = new NamespacedKey(this,"rpName");
        NamespacedKey rpGender = new NamespacedKey(this,"rpGender");
        data.remove(rpName);
        data.remove(rpGender);
        inProcessOfJoining.remove(player);
        currentlySelectingGender.remove(player);
        currentlySelectingName.remove(player);
    }

    void PlayerPostSetData(Player player)
    {
        // Sets the players data from the persistent container
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey rpName = new NamespacedKey(this,"rpName");
        NamespacedKey rpGender = new NamespacedKey(this,"rpGender");
        player.setDisplayName(data.get(rpName,PersistentDataType.STRING));
        player.setPlayerListName(data.get(rpName,PersistentDataType.STRING));
        player.setCustomName(data.get(rpName,PersistentDataType.STRING));

        player.setCustomNameVisible(true);

    }



    void PingSound(Player player, float pitch)
    {
        player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_CHIME,1,pitch);
    }

    void FailSound(Player player, float pitch)
    {
        player.playSound(player.getLocation(),Sound.ENTITY_BLAZE_DEATH,1,pitch);

    }
    void WelcomeSound(Player player, float pitch)
    {
        player.playSound(player.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,1,pitch);

    }

    void PlayerJoinParticle(Player player)
    {
        if (!spawnParticles) {return;}
        BukkitRunnable spawnParticleBeam = new BukkitRunnable() {

            int count = 0;
            @Override
            public void run() {
                spawnLocation.setWorld(player.getWorld());
                player.getWorld().spawnParticle(Particle.END_ROD,spawnLocation,10);
                count = count + 1;
                if (count >= 10)
                {
                    this.cancel();
                }
            }
        };
        spawnParticleBeam.runTaskTimer(this,0,4);

        BukkitRunnable spawnParticleExplosion = new BukkitRunnable() {
            @Override
            public void run() {

                spawnLocation.setWorld(player.getWorld());
                player.getWorld().spawnParticle(Particle.TOTEM,spawnLocation,100,1,1,1);
            }
        };
        spawnParticleExplosion.runTaskLater(this,40);
    }


    void LoadConfig()
    {
        reloadConfig();
        config = this.getConfig();
        config.addDefault("serverDisplayName","SERVERNAME");
        config.addDefault("doWelcomeMessage",true);
        config.addDefault("startX", 0);
        config.addDefault("startY", 0);
        config.addDefault("startZ", 0);
        config.addDefault("spawnX", 0);
        config.addDefault("spawnY", 100);
        config.addDefault("spawnZ", 0);
        config.addDefault("spawnParticles", false);
        config.addDefault("characterCreation", true);
        config.options().copyDefaults(true);
        saveConfig();
        RefreshConfig();
    }

    void RefreshConfig()
    {
        reloadConfig();
        config = getConfig();
        chooseLocation.setX(config.getDouble("startX"));
        chooseLocation.setY(config.getDouble("startY"));
        chooseLocation.setZ(config.getDouble("startZ"));

        spawnLocation.setX(config.getDouble("spawnX"));
        spawnLocation.setY(config.getDouble("spawnY"));
        spawnLocation.setZ(config.getDouble("spawnZ"));

        spawnParticles = config.getBoolean("spawnParticles");
        doCreateNewCharacters = config.getBoolean("characterCreation");

        serverName = config.getString("serverDisplayName");
        doWelcomeMessage = config.getBoolean("doWelcomeMessage");
    }
}
