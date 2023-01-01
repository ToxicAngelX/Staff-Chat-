package havarcoding.commands;

import havarcoding.HStaffChat;
import jdk.jfr.internal.tool.Main;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToggleStaffChat extends Command {
    private Configuration config;
    private HStaffChat hStaffChat;
    public ToggleStaffChat(Plugin plugin, HStaffChat main) {
        super("staffchattoggle", "hstaffchattoggle.use", "sct");
        hStaffChat = main;
    }
    public void execute(CommandSender sender, String[] args) {
            if (sender instanceof ProxiedPlayer) {
                ProxiedPlayer player = (ProxiedPlayer) sender;
                if (hStaffChat.staffChatEnabled.contains(player)) {
                    hStaffChat.staffChatEnabled.remove(player);
                    player.sendMessage(new TextComponent(ChatColor.GOLD + "Staff chat visibility toggled off"));
                } else {
                    hStaffChat.staffChatEnabled.add(player);
                    player.sendMessage(new TextComponent(ChatColor.GOLD + "Staff chat visibility toggled on"));
                }
            } else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "This command can only be executed by players."));
            }
        }
    }
