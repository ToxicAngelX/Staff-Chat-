package havarcoding;

import havarcoding.commands.StaffChatCommand;
import havarcoding.commands.ToggleStaffChat;
import havarcoding.listeners.Chat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;

public final class HStaffChat extends Plugin {
    public ArrayList<ProxiedPlayer> staffChatEnabled = new ArrayList<>();
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new StaffChatCommand(this, this));
        getProxy().getPluginManager().registerCommand(this, new ToggleStaffChat(this, this));
        getProxy().getPluginManager().registerListener(this, new Chat(this, this));
    }

    public String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
