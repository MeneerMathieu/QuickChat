package net.winniethedampoeh.quickchat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.winniethedampoeh.quickchat.util.ModCommandRegister;
import net.winniethedampoeh.quickchat.util.QuickChatFiles;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class QuickChat implements ModInitializer {

    public static QuickChat INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger("quickchat");
    public final MinecraftClient minecraftClient = MinecraftClient.getInstance();
    public QuickChatFiles quickChats;

    @Override
    public void onInitialize() {

        INSTANCE = this;
        try {
            quickChats = new QuickChatFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModCommandRegister.registerCommands();

    }
}
