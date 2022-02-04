package net.winniethedampoeh.quickchat.util;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.winniethedampoeh.quickchat.command.AddQuickChatCommand;
import net.winniethedampoeh.quickchat.command.QuickChatCommands;

public class ModCommandRegister {
    public static void registerCommands(){
        QuickChatCommands.registerCommands(ClientCommandManager.DISPATCHER);
        AddQuickChatCommand.register(ClientCommandManager.DISPATCHER);
    }
}
