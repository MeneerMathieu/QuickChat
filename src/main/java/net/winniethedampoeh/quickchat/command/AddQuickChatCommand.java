package net.winniethedampoeh.quickchat.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.winniethedampoeh.quickchat.QuickChat;

import java.io.IOException;

public class AddQuickChatCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher){
        dispatcher.register(ClientCommandManager.literal("quickchat")
                .then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("name", StringArgumentType.word())
                                .then(ClientCommandManager.argument("QuickChat", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            try {
                                                return addQuickChat(context, dispatcher, StringArgumentType.getString(context, "name"), StringArgumentType.getString(context, "QuickChat"));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                return 1;
                                            }
                                        })))));
    }

    private static int addQuickChat(CommandContext<FabricClientCommandSource> ctx, CommandDispatcher<FabricClientCommandSource> dispatcher, String literal, String message) throws IOException {
        QuickChat.INSTANCE.quickChats.addQuickChat(literal, message);
        QuickChatCommands.registerCommand(dispatcher, literal, message);
        ctx.getSource().sendFeedback(new LiteralText(Formatting.DARK_GREEN + "Command has been added, you can use it now. Restart for full integration."));
        return 1;
    }
}
