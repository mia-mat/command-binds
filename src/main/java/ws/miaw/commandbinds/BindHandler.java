package ws.miaw.commandbinds;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;
import java.util.Set;

public class BindHandler {

    private static boolean waitingForInput;
    private static String currentlyBindingCommand = null;

    // not using keys for a few things due to those potentially changing with bind exclusivity off
    private static Set<String> commandsLastTick = new HashSet<>();

    private static Set<Integer> keysLastTick = new HashSet<>();

    /// sets vars for #onClientTick to listen for binds for the given command
    public static void startBindingProcess(String command) {
        currentlyBindingCommand = command;
        waitingForInput = true;
    }

    private static void attemptBindCommand(Set<Integer> keys, String command) {
        command = normalizeSlash(command);

        String bindString = KeyboardUtil.getBindString(keys);

        if (!CommandBindsMod.getConfig().hasBind(keys)) {
            ChatUtil.sendMessage("Created bind for '" + command + "': " + ChatFormatting.LIGHT_PURPLE + bindString);
            CommandBindsMod.getConfig().addBind(keys, command);
        } else {
            ChatUtil.sendErorMessage("the key combination '"
                    + ChatFormatting.LIGHT_PURPLE + bindString + ChatFormatting.RED +
                    "' is already bound to '" + CommandBindsMod.getConfig().getBind(keys) + "', remove this before rebinding.");
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (Minecraft.getMinecraft().currentScreen != null) return;
        if(Minecraft.getMinecraft().thePlayer == null) return;

        Set<Integer> pressedThisTick = KeyboardUtil.getCurrentlyPressedKeys();

        if (currentlyBindingCommand != null) {

            // don't allow binds to just the return key.
            // this fixes a bug regarding the return key sometimes being registered as the bind as the user presses it to
            // execute the command.
            if (pressedThisTick.size() == 1 && pressedThisTick.contains(Keyboard.KEY_RETURN)) {
                return;
            }

            // cancel on escape
            if (pressedThisTick.size() == 1 && pressedThisTick.contains(Keyboard.KEY_ESCAPE)) {
                ChatUtil.sendMessage("Canceled binding '" + currentlyBindingCommand + "'");
                currentlyBindingCommand = null;
                waitingForInput = false;
                keysLastTick = new HashSet<>();
                commandsLastTick = new HashSet<>();
                return;
            }

            if (pressedThisTick.size() == 0) {
                if (!waitingForInput) { // actually wait for input before deciding that it's finished
                    attemptBindCommand(keysLastTick, currentlyBindingCommand);
                    currentlyBindingCommand = null;
                }
            } else {
                waitingForInput = false;
            }

        } else {
            HashSet<String> commandsThisTick = new HashSet<>();
            CommandBindsMod.getConfig().getBindsForHandling(pressedThisTick).forEach(command -> {
                commandsThisTick.add(command); // don't spam execute the bind every tick while held
                if(!commandsLastTick.contains(command)) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(normalizeSlash(command));
                }

            });
            commandsLastTick = commandsThisTick;
        }

        keysLastTick = pressedThisTick;
    }

    public static String normalizeSlash(String command) {
        if(command.startsWith("/")) return command;
        return "/" + command;
    }

}
