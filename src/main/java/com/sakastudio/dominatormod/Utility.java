package com.sakastudio.dominatormod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class Utility {
    public static int GetCrimeCoefficient(EntityPlayer p){
        int num = 0;

        InventoryEnderChest ender = p.getInventoryEnderChest();
        for (int i = 0;i<ender.getSizeInventory();i++){
            num += ChcekItemCrimeCoefficient(ender.getStackInSlot(i));
        }

        NonNullList<ItemStack> inv = p.inventory.mainInventory;
        for(ItemStack itemStack:inv){
            num += ChcekItemCrimeCoefficient(itemStack);
        }
        NonNullList<ItemStack> offhand = p.inventory.offHandInventory;
        for(ItemStack itemStack:offhand){
            num += ChcekItemCrimeCoefficient(itemStack);
        }

        return num;
    }

    static int ChcekItemCrimeCoefficient(ItemStack item){
        if(item.getDisplayName().equals("TNT")){
            return item.getCount() * 10;
        }
        if(item.getDisplayName().equals("Flint and Steel")){
            return item.getCount() * 30;
        }
        if(item.getDisplayName().equals("Wither Skeleton Skull")){
            return item.getCount() * 50;
        }
        if(item.getDisplayName().equals("Diamond Sword")){
            return item.getCount() * 20;
        }
        if(item.getDisplayName().equals("Bow")){
            return item.getCount() * 20;
        }
        if(item.getDisplayName().equals("Arrow")){
            return item.getCount() * 4;
        }
        return 0;
    }
}
