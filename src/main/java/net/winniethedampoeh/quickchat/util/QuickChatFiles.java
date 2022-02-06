package net.winniethedampoeh.quickchat.util;

import com.google.gson.Gson;
import net.winniethedampoeh.quickchat.QuickChat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class QuickChatFiles {

    private static final String directoryPath = "./config";
    private static final String filePath = "./config/quickchat.json";
    File file = new File(filePath);
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
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));
            writer.write(defaultConfig);
            writer.close();
        }

        String config = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String str;
        while ((str = reader.readLine()) != null){
            config = config.concat(str);
        }
        reader.close();


        Object gson;
        try {
            gson = new Gson().fromJson(config, Object.class);
        }catch (Exception e){
            gson = null;
        }

        this.quickChats = (Map<String, String>) gson;
    }

    public Object getQuickChats(){
        return this.quickChats;
    }

    public void addQuickChat(String literal, String message) throws IOException {
        Map<String, String> map = this.quickChats;
        map.put(literal, message);
        this.quickChats = map;
        Object o = map;
        Gson gson = new Gson();
        QuickChat.LOGGER.warn(map);
        try(FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(o, writer);
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
