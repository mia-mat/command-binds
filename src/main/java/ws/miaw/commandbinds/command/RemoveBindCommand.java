package ws.miaw.commandbinds.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import ws.miaw.commandbinds.ChatUtil;
import ws.miaw.commandbinds.CommandBindsMod;

import java.util.Arrays;
import java.util.List;

public class RemoveBindCommand extends CommandBase {


    @Override
    public String getCommandName() {
        return "removebind";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("unbindcommand", "removecommandbind");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            ChatUtil.sendErorMessage("Usage: /removebind <command>");
            return;
        }

        StringBuilder commandBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            commandBuilder.append(args[i]).append(" ");
        }
        String command = commandBuilder.toString().trim();

        if (!CommandBindsMod.getConfig().hasBind(command)) {
            ChatUtil.sendErorMessage("No bind found for command '" + ChatFormatting.WHITE + command + ChatFormatting.RED + "'");
            return;
        }

        int removed = CommandBindsMod.getConfig().removeBind(command);
        if (removed > 1) {
            ChatUtil.sendMessage("Removed " + removed + " binds for '" + command + "'!");
        } else {
            ChatUtil.sendMessage("Removed bind for '" + command + "'!");
        }


    }
}
