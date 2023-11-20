package net.winniethedampoeh.quickchat.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.winniethedampoeh.quickchat.QuickChat;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class QuickChatFiles {

    private static final String directoryPath = "./config";
    private static final String filePath = "./config/quickchat.json";
    private static final String defaultConfig = """
        {
            "credits": "This was said by the QuickChat mod by WinnieTheDampoeh.",
            "tableflip": "(╯°□°）╯︵ ┻━┻",
            "unflip": "┬─┬ ノ( ゜-゜ノ)",
            "shrug": "¯\\\\_(ツ)_/¯",
            "sleep": "(∪.∪ )...zzz",
            "party": "(づ￣ 3￣)づ",
            "cry": "`(*>﹏<*)′",
            "hypnotized": "◑﹏◐",
            "gimme": "༼ つ ◕_◕ ༽つ",
            "bear": "ฅʕ•̫͡•ʔฅ",
            "doggo": "(ʘᴥʘ)",
            "cat":  "./ᐠ｡ꞈ｡ᐟ\\\\.",
            "panic": "(っ °Д °;)っ",
            "crying": "（；´д｀）ゞ",
            "vertigo": "{{{(>_<)}}}",
            "inspect": "ಠಿ_ಠ",
            "facepalm": "(>ლ)",
            "muscels": "ᕦ(ò_óˇ)ᕤ",
            "fart": "○|￣|_ =3",
            "shocked": "(⊙_(⊙_⊙)_⊙)",
            "what?": "(⊙_⊙)？",
            "stare": "◉_◉",
            "how": "¯\\\\(°_o)/¯",
            "wave": "(●>ω<)ﾉﾞ"
        }
        """;

    private Map<String, String> quickChats;

    public QuickChatFiles() throws IOException {
        File directory = new File(directoryPath);
        directory.mkdir();

        File file = new File(filePath);
        if (file.createNewFile()){
            writeDefaultConfig();
        }

        String config = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String str;
        while ((str = reader.readLine()) != null){
            config = config.concat(str);
        }
        reader.close();

        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {}.getType();

        try {
            this.quickChats = gson.fromJson(config, mapType);
        } catch (Exception e) {
            QuickChat.LOGGER.warn("Unable to load config file. Loading default config. (Incorrect JSON)");
            this.quickChats = gson.fromJson(defaultConfig, mapType);
        }
    }

    private void writeDefaultConfig() throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));
        writer.write(defaultConfig);
        writer.close();
    }

    public Map<String, String> getQuickChats(){
        return this.quickChats;
    }

    public void addQuickChat(String literal, String message) {
        Map<String, String> map = this.quickChats;
        map.put(literal, message);
        this.quickChats = map;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(filePath, StandardCharsets.UTF_8)) {
            gson.toJson(map, writer);
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeQuickChat(String literal) throws IOException, NullPointerException {
        Map<String, String> map = this.quickChats;
        if (map.get(literal) == null){
            throw new NullPointerException(literal + " is not a used command.");
        }
        map.remove(literal);
        this.quickChats = map;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(filePath, StandardCharsets.UTF_8)){
            gson.toJson(map, writer);
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
