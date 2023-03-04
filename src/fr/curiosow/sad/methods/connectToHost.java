package fr.curiosow.sad.methods;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.curiosow.sad.main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class connectToHost
{

    public static void process(Player p, String server)
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);
        p.sendMessage(messages.getString("menu-host-messageConnexion").replace("&", "§"));
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(main.getInstance(), "BungeeCord", out.toByteArray());
    }

}
