package ws.miaw.commandbinds.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import ws.miaw.commandbinds.ChatUtil;
import ws.miaw.commandbinds.CommandBindsMod;

public class ToggleBindExclusivityCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "togglebindexclusivity";
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
    public void processCommand(ICommandSender sender, String[] args) {
        CommandBindsMod.getConfig().setBindExclusivity(!CommandBindsMod.getConfig().areBindingsExclusive());

        ChatUtil.sendMessage("Toggled bind exclusivity " + ChatFormatting.LIGHT_PURPLE +
                ((CommandBindsMod.getConfig().areBindingsExclusive()) ? "on" : "off"));
    }
}
