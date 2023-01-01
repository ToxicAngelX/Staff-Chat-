package havarcoding.listeners;

import havarcoding.HStaffChat;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;

public class Chat implements Listener {
    private HStaffChat hStaffChat;
    private Configuration config;

    public Chat(HStaffChat main, Plugin plugin){
        this.hStaffChat = main;
        try {
            File configFile = new File(plugin.getDataFolder(), "config.yml");
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @EventHandler//s
    public void onchat(ChatEvent event) {
        if (event.getMessage().startsWith("/")) return;
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (hStaffChat.staffChatEnabled.contains(player)) {
            event.setCancelled(true);
            String message = String.join(" ", event.getMessage());
            TextComponent chatComponent = new TextComponent(config.getString("format")
                    .replace("%name%" , player.getDisplayName())
                    .replace("%message%" , message)
                    .replace("%prefix%" ,config.getString("prefix"))
                    .replace("%server%" , player.getServer().getInfo().getName())

            );
            for (ProxiedPlayer p : hStaffChat.getProxy().getPlayers()) {
                if (p.hasPermission("hstaffchat.use")) {
                    p.sendMessage(chatComponent);
                }
            }
        } else {
            event.setCancelled(false);
            return;
        }

    }


}
