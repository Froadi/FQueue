package club.froad.queue.listeners;

import club.froad.queue.FQueue;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PluginMessageListener implements Listener {

    @EventHandler
    public void onMessage(PluginMessageEvent e){
        if (e.getTag().equalsIgnoreCase("Fque")) {

            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));

            try {
                String channel = in.readUTF();
                if (channel.equals("JoinQueue")) {
                    String input = in.readUTF();
                    ProxiedPlayer player = (ProxiedPlayer) e.getReceiver();

                    if (player != null) {
                        if (player.isConnected()) {
                            FQueue.getInstance().getQueueManager().joinQueue(player, input);
                        }
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

}
