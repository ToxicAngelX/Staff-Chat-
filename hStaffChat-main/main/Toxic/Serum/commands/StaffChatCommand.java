package havarcoding.commands;

import havarcoding.HStaffChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StaffChatCommand extends Command {
    private Configuration config;
    private HStaffChat hStaffChat;
    private boolean sctoggle;

    public StaffChatCommand(Plugin plugin, HStaffChat main) {
        super("staffchat", "hstaffchat.use", "sc");
        hStaffChat = main;
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            File configFile = new File(plugin.getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                configFile.createNewFile();
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
                config.set("prefix", main.color("&8[&chStaff Chat&8]"));
                config.set("format", main.color("%prefix% %name% %server% %message%"));
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
            } else {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length == 0) {
                player.sendMessage(new TextComponent("Usage: /staffchat <message>"));
                return;
            }
            String message = String.join(" ", args);
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
        }
    }

}
