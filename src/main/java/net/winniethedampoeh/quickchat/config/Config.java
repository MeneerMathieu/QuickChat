package net.winniethedampoeh.quickchat.config;

public class Config extends QuickChatConfig {

    public int version = 1;

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Config readFromFile(String fileName) {
        return null;
    }

    @Override
    public boolean writeToFile(String fileName) {
        return false;
    }
}
