package ws.miaw.commandbinds;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {

    public static void sendMessage(final String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.DARK_PURPLE + "[CB] " + ChatFormatting.WHITE + msg));
    }

    public static void sendErorMessage(final String msg) {
       sendMessage(ChatFormatting.RED + msg);
    }

}
