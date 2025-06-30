package ws.miaw.commandbinds.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import ws.miaw.commandbinds.BindHandler;
import ws.miaw.commandbinds.ChatUtil;
import ws.miaw.commandbinds.CommandBindsMod;
import ws.miaw.commandbinds.KeyboardUtil;

import java.util.*;

public class CommandBindsCommand extends CommandBase {


    @Override
    public String getCommandName() {
        return "commandbinds";
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
        return Collections.singletonList("binds");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        List<String> binds = getBindsList();

        if (binds.size() == 0) {
            ChatUtil.sendMessage("Use /addbind to add a bind!");
            return;
        }

        ChatUtil.sendMessage("Bind exclusivity is " + ChatFormatting.LIGHT_PURPLE
                + (CommandBindsMod.getConfig().areBindingsExclusive() ? "enabled" : "disabled"));
        ChatUtil.sendMessage("Your current command binds are:");
        for (String bind : binds) {
            ChatUtil.sendMessage(bind);
        }
    }

    // human readable
    private List<String> getBindsList() {
        final Map<Set<Integer>, String> binds = CommandBindsMod.getConfig().getBindMap();

        List<String> str = new ArrayList<>();
        for (Set<Integer> key : binds.keySet()) {
            String bind = BindHandler.normalizeSlash(binds.get(key));
            str.add("[" + ChatFormatting.LIGHT_PURPLE + KeyboardUtil.getBindString(key) + ChatFormatting.WHITE + "]: " + bind);
        }
        return str;
    }
}
