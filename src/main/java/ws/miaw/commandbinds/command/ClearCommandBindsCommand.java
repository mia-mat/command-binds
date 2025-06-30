package ws.miaw.commandbinds.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import ws.miaw.commandbinds.CBConfig;
import ws.miaw.commandbinds.ChatUtil;
import ws.miaw.commandbinds.CommandBindsMod;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClearCommandBindsCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "clearcommandbinds";
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
        return Collections.singletonList("clearbinds");
    }

    private static final long CONFIRM_TIMEOUT = 10 * 1000; // ms

    private boolean confirmFlag = false;

    private Timer timeoutTimer = new Timer();

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        CBConfig config = CommandBindsMod.getConfig();

        int bindsSize = config.getBindMap().size();

        if (!confirmFlag) {
            ChatUtil.sendMessage("You currently have "
                    + ChatFormatting.LIGHT_PURPLE + bindsSize
                    + ChatFormatting.WHITE + " binds!");
            ChatUtil.sendMessage("To confirm this action, run /clearbinds again within 10 seconds.");

            confirmFlag = true;
            timeoutTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    confirmFlag = false;
                    ChatUtil.sendMessage("Clear binds action has timed out.");
                }
            }, CONFIRM_TIMEOUT);

            return;
        }

        timeoutTimer.cancel();
        timeoutTimer = new Timer(); // previous timer is discarded

        ChatUtil.sendMessage("Removed " + ChatFormatting.LIGHT_PURPLE + bindsSize + ChatFormatting.WHITE + " binds.");
        config.clearBinds();
        confirmFlag = false;

    }
}
