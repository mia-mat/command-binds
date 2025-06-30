package ws.miaw.commandbinds.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import ws.miaw.commandbinds.BindHandler;
import ws.miaw.commandbinds.ChatUtil;
import ws.miaw.commandbinds.CommandBindsMod;
import ws.miaw.commandbinds.KeyboardUtil;

import java.util.*;

public class AddBindCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "addbind";
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
        return Arrays.asList("bindcommand", "addcommandbind");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length == 0) {
            ChatUtil.sendErorMessage("Usage: /addbind <command>");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        String command = builder.toString().trim();
        command = BindHandler.normalizeSlash(command);

        BindHandler.startBindingProcess(command);
        ChatUtil.sendMessage("Press, then release, the keys to bind to '" + command  + "'");

    }



}
