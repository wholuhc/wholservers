package fr.curiosow.sad.discordWebhooks;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

public class DiscordWebhookGames {

    public static void execute(String color, String header, String text) {
        ///////////////////////////////////////////////
        // CONFIG
        String tokenWebhook = "https://discord.com/api/webhooks/997230527679566024/b_r6ri6UlYBRm5Wwh11dD9wuCu89f0BOX6E87HItZQlTYxSoHiYsAeDRxnQ_Pxgonn2L";
        String title = header;
        String message = text;
        ///////////////////////////////////////////////
        String jsonBrut = "";
        jsonBrut += "{\"embeds\": [{"
                + "\"title\": \""+ title +"\","
                + "\"description\": \""+ message +"\","
                + "\"color\": " + color
                + "}]}";
        try {
            URL url = new URL(tokenWebhook);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.addRequestProperty("Content-Type", "application/json");
            con.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStream stream = con.getOutputStream();
            stream.write(jsonBrut.getBytes());
            stream.flush();
            stream.close();
            con.getInputStream().close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}