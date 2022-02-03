package net.winniethedampoeh.quickchat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import java.util.logging.Logger;

public class Quickchat implements ModInitializer {

    public static final Logger LOGGER = Logger.getLogger("quickchat");
    public final MinecraftClient minecraftClient = MinecraftClient.getInstance();

    @Override
    public void onInitialize() {



    }
}
