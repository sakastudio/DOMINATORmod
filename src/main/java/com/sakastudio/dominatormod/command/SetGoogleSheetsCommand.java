package com.sakastudio.dominatormod.command;

import com.sakastudio.dominatormod.CustomObjects;
import com.sakastudio.dominatormod.GoogleSheets;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SetGoogleSheetsCommand extends CommandBase {

    public SetGoogleSheetsCommand(){}

    @Override
    public String getName() {
        return "setGoogleSheets";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    @SideOnly(Side.SERVER)
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // 引数不足
        if (args.length < 2) {
            sender.sendMessage(new TextComponentString("/setGoogleSheets <sheetID> <sheetName>"));
            return;
        }
        CustomObjects.Instance.killLogList = GoogleSheets.GetStringValue(args[0],args[1]);

    }
}
