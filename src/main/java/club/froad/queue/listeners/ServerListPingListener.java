package club.froad.queue.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(final ServerListPingEvent event){

        int p = Bukkit.getMaxPlayers();

        if (Bukkit.hasWhitelist()){
            event.setMotd("WHITELISTED, " + p);
        }else{
            event.setMotd("" + p);
        }
    }

}
