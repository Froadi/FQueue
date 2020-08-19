package club.froad.queue.utils;

import club.froad.queue.FQueue;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class Message {

    private static final String PREFIX = FQueue.getConfig().getString("messages.prefix");
    private static final String PNOTFOUND = FQueue.getConfig().getString("messages.player-not-found");
    private static final String NOPERMS = FQueue.getConfig().getString("messages.no-perms");

    public static void withPrefix(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + "§7" + message));
    }

    /*public static void playerNotFoundMsg(CommandSender sender, String target) {
        Message.withPrefix(sender, PNOTFOUND.replaceAll("%player%", target));
    }*/

    public static void sendPermissionMessage(CommandSender sender) {
        Message.withPrefix(sender, NOPERMS);
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
