package net.winniethedampoeh.quickchat.config;

public abstract class QuickChatConfig {

    private int version;

    public abstract int getVersion();

    public abstract QuickChatConfig readFromFile(String fileName);

    public abstract boolean writeToFile(String fileName);
}
