package net.winniethedampoeh.quickchat.util;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.winniethedampoeh.quickchat.command.StandardQuickChatCommands;
import net.winniethedampoeh.quickchat.command.QuickChatCommands;

public class ModCommandRegister {
    public static void registerCommands(){
        QuickChatCommands.registerCommands(ClientCommandManager.getActiveDispatcher());
        StandardQuickChatCommands.register(ClientCommandManager.getActiveDispatcher());
    }
}
