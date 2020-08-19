package club.froad.queue.listeners;

import club.froad.queue.FQueue;
import club.froad.queue.queue.QueueManager;
import club.froad.queue.queue.QueuePlayer;
import club.froad.queue.utils.QueueUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        QueueManager queueManager = FQueue.getInstance().getQueueManager();
        queueManager.leaveQueueOnLeave(player);
    }

}
