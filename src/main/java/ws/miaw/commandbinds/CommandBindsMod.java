package ws.miaw.commandbinds;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ws.miaw.commandbinds.command.*;

@Mod(modid = CommandBindsMod.MODID, version = CommandBindsMod.VERSION)
public class CommandBindsMod
{
    public static final String MODID = "commandbinds";
    public static final String VERSION = "1.3.1";

    private static CBConfig config;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if(config == null) {
            config = CBConfig.create();
        }

        MinecraftForge.EVENT_BUS.register(new BindHandler());

        ClientCommandHandler.instance.registerCommand(new AddBindCommand());
        ClientCommandHandler.instance.registerCommand(new ClearCommandBindsCommand());
        ClientCommandHandler.instance.registerCommand(new CommandBindsCommand());
        ClientCommandHandler.instance.registerCommand(new RemoveBindCommand());
        ClientCommandHandler.instance.registerCommand(new ToggleBindExclusivityCommand());
    }

    public static Logger getLogger() {
        return logger;
    }

    public static CBConfig getConfig() {
        return config;
    }



}
