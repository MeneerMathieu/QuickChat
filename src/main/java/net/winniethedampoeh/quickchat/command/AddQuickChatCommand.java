package net.winniethedampoeh.quickchat.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.winniethedampoeh.quickchat.QuickChat;

import java.io.IOException;
import java.util.Map;

public class AddQuickChatCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher){
        ClientCommandRegistrationCallback.EVENT.register((dispatcher1, registryAccess) -> dispatcher1.register(ClientCommandManager.literal("quickchat")
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
                                        }))))
                .then(ClientCommandManager.literal("help")
                        .executes(context -> {
                            sendHelp(context, dispatcher);
                            return 1;
                        }))
                .then(ClientCommandManager.literal("remove")
                        .then(ClientCommandManager.argument("literal", StringArgumentType.greedyString())
                                .executes(context -> removeQuickChat(context, dispatcher, StringArgumentType.getString(context, "literal"))))))
        );
    }

    private static void sendHelp(CommandContext<FabricClientCommandSource> ctx, CommandDispatcher<FabricClientCommandSource> dispatcher) {
        StringBuilder message = new StringBuilder();
        for(Map.Entry<String, String> entry : QuickChat.INSTANCE.quickChats.getQuickChats().entrySet()){
            message.append("\n").append(entry.getKey()).append(" - ").append(entry.getValue());
        }
        ctx.getSource().sendFeedback(Text.literal(message.toString()));
    }

    private static int addQuickChat(CommandContext<FabricClientCommandSource> ctx, CommandDispatcher<FabricClientCommandSource> dispatcher, String literal, String message) throws IOException {
        QuickChat.INSTANCE.quickChats.addQuickChat(literal, message);
        QuickChatCommands.registerCommand(dispatcher, literal, message);
        ctx.getSource().sendFeedback(Text.literal(Formatting.DARK_GREEN + "Command has been added, you can use it after restart."));
        return 1;
    }

    private static int removeQuickChat(CommandContext<FabricClientCommandSource> ctx, CommandDispatcher<FabricClientCommandSource> dispatcher, String literal){
        try {
            QuickChat.INSTANCE.quickChats.removeQuickChat(literal);
            ctx.getSource().sendFeedback(Text.literal(Formatting.GREEN + "Command has been removed. Restart needed to update register."));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            ctx.getSource().sendFeedback(Text.literal(Formatting.RED + e.getMessage()));
        }
        return 1;
    }
}
