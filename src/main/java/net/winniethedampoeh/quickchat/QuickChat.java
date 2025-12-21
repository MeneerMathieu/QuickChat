package net.winniethedampoeh.quickchat;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.winniethedampoeh.quickchat.util.ModCommandRegister;
import net.winniethedampoeh.quickchat.util.QuickChatFiles;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class QuickChat implements ClientModInitializer {

    public static QuickChat INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger("quickchat");
    public final Minecraft minecraftClient = Minecraft.getInstance();
    public QuickChatFiles quickChats;

    @Override
    public void onInitializeClient() {

        INSTANCE = this;
        try {
            quickChats = new QuickChatFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModCommandRegister.registerCommands();

    }
}
