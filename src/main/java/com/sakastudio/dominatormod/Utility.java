package com.sakastudio.dominatormod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class Utility {
    //プレイヤーの犯罪係数をチェック
    public static int GetCrimeCoefficient(EntityPlayer p){
        int num = 0;

        //エンダーチェストの中身をチェック
        InventoryEnderChest ender = p.getInventoryEnderChest();
        for (int i = 0;i<ender.getSizeInventory();i++){
            num += ChcekItemCrimeCoefficient(ender.getStackInSlot(i));
        }

        //メインインベトリをチェック
        NonNullList<ItemStack> inv = p.inventory.mainInventory;
        for(ItemStack itemStack:inv){
            num += ChcekItemCrimeCoefficient(itemStack);
        }

        return num;
    }

    static int ChcekItemCrimeCoefficient(ItemStack item){
        if(item.getDisplayName().equals("TNT")){
            return item.getCount() * 10;
        }
        //Flint and Steel
        //Wither Skeleton Skull
        //Diamond Sword
        return 0;
    }
}
