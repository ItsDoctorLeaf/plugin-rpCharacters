package me.drl.drlcharactercreation;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InteractiveUI {
    public Inventory genderSelector = Bukkit.createInventory(null,27,"Select Gender");
    ItemStack fillerItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    InteractiveUI()
    {
        ItemMeta metaItemFill = fillerItem.getItemMeta();
        metaItemFill.setDisplayName(".");
        fillerItem.setItemMeta(metaItemFill);

        ItemStack maleSelect = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta maleSelectMeta = maleSelect.getItemMeta();
        maleSelectMeta.setDisplayName(Titles.format("&c")+"- MALE -");
        maleSelect.setItemMeta(maleSelectMeta);

        ItemStack femaleSelect = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta femaleSelectMeta = femaleSelect.getItemMeta();
        femaleSelectMeta.setDisplayName(Titles.format("&e")+"- FEMALE -");
        femaleSelect.setItemMeta(femaleSelectMeta);

        ItemStack nbSelect = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta nbSelectMeta = nbSelect.getItemMeta();
        nbSelectMeta.setDisplayName(Titles.format("&a")+"- NON BINARY -");
        nbSelect.setItemMeta(nbSelectMeta);

        ItemStack fluidSelect = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta fluidSelectMeta = fluidSelect.getItemMeta();
        fluidSelectMeta.setDisplayName(Titles.format("&9")+"- GENDER FLUID -");
        fluidSelect.setItemMeta(fluidSelectMeta);

        ItemStack neutralSelect = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
        ItemMeta neutralSelectMeta = neutralSelect.getItemMeta();
        neutralSelectMeta.setDisplayName(Titles.format("&d")+"- GENDER NEUTRAL -");
        neutralSelect.setItemMeta(neutralSelectMeta);

        for (int i = 0; i < 27; i++)
        {
            genderSelector.setItem(i,fillerItem);
        }
        genderSelector.setItem(11,maleSelect);

        genderSelector.setItem(12,femaleSelect);

        genderSelector.setItem(13,nbSelect);

        genderSelector.setItem(14,fluidSelect);

        genderSelector.setItem(15,neutralSelect);



    }
    void OpenGUI(Player player)
    {
        player.openInventory(genderSelector);
    }

    void CloseGUI(Player player)
    {
        player.closeInventory();
    }


}
