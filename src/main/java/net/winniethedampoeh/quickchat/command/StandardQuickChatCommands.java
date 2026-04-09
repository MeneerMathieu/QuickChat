package net.winniethedampoeh.quickchat.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.winniethedampoeh.quickchat.QuickChat;

import java.io.IOException;
import java.util.Map;

public class StandardQuickChatCommands {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher){
        ClientCommandRegistrationCallback.EVENT.register((dispatcher1, registryAccess) -> dispatcher1.register(ClientCommands.literal("quickchat")
                .then(ClientCommands.literal("add")
                        .then(ClientCommands.argument("name", StringArgumentType.word())
                                .then(ClientCommands.argument("QuickChat", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            try {
                                                return addQuickChat(context, dispatcher, StringArgumentType.getString(context, "name"), StringArgumentType.getString(context, "QuickChat"));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                return 1;
                                            }
                                        }))))
                .then(ClientCommands.literal("help")
                        .executes(context -> {
                            sendHelp(context, dispatcher);
                            return 1;
                        }))
                .then(ClientCommands.literal("remove")
                        .then(ClientCommands.argument("literal", StringArgumentType.greedyString())
                                .executes(context -> removeQuickChat(context, dispatcher, StringArgumentType.getString(context, "literal"))))))
        );
    }

    private static void sendHelp(CommandContext<FabricClientCommandSource> ctx, CommandDispatcher<FabricClientCommandSource> dispatcher) {
        StringBuilder message = new StringBuilder();
        for(Map.Entry<String, String> entry : QuickChat.INSTANCE.quickChats.getQuickChats().entrySet()){
            message.append("\n").append(entry.getKey()).append(" - ").append(entry.getValue());
        }
        ctx.getSource().sendFeedback(Component.literal(message.toString()));
    }

    private static int addQuickChat(CommandContext<FabricClientCommandSource> ctx, CommandDispatcher<FabricClientCommandSource> dispatcher, String literal, String message) throws IOException {
        QuickChat.INSTANCE.quickChats.addQuickChat(literal, message);
        QuickChatCommands.registerCommand(dispatcher, literal, message);
        ctx.getSource().sendFeedback(Component.literal(ChatFormatting.DARK_GREEN + "Command has been added, you can use it after restart.") );
        return 1;
    }

    private static int removeQuickChat(CommandContext<FabricClientCommandSource> ctx, CommandDispatcher<FabricClientCommandSource> dispatcher, String literal){
        try {
            QuickChat.INSTANCE.quickChats.removeQuickChat(literal);
            ctx.getSource().sendFeedback(Component.literal(ChatFormatting.GREEN + "Command has been removed. Restart needed to update register."));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            ctx.getSource().sendFeedback(Component.literal(ChatFormatting.RED + e.getMessage()));
        }
        return 1;
    }
}
