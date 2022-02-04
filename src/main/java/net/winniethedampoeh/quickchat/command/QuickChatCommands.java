package net.winniethedampoeh.quickchat.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.winniethedampoeh.quickchat.QuickChat;

import java.util.Map;

public class QuickChatCommands {

    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher){
        Map<String, String> quickChats;
        try {
            quickChats = (Map<String, String>) QuickChat.INSTANCE.quickChats.getQuickChats();
        }catch (Exception e){
            quickChats = null;
        }
        assert quickChats != null;
        for (String literal : quickChats.keySet()) {
            String message = quickChats.get(literal);
            registerCommand(dispatcher, literal, message);
        }
    }

    public static void registerCommand(CommandDispatcher<FabricClientCommandSource> dispatcher, String literal, String message){
        dispatcher.register(ClientCommandManager.literal(literal)
                .executes(context -> sendMessage(context, message, ""))
                .then(ClientCommandManager.argument("extra", StringArgumentType.greedyString())
                        .executes(context -> sendMessage(context, message, StringArgumentType.getString(context, "extra")))));
    }

    public static int sendMessage(CommandContext<FabricClientCommandSource> ctx, String message, String extra){
        ctx.getSource().getClient().player.sendChatMessage(message + " " +  extra);
        return 1;
    }
}
