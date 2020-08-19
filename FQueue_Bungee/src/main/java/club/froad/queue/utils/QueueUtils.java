package club.froad.queue.utils;

import club.froad.queue.FQueue;
import club.froad.queue.queue.Queue;
import club.froad.queue.queue.QueueManager;
import club.froad.queue.queue.QueuePlayer;
import club.froad.queue.queue.QueueServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueUtils {

    /*public static boolean checkWhitelisted(ServerInfo server) {
        if (PluginMessageThread.serverMOTD.get(server.getName()) != null){
            return PluginMessageThread.serverMOTD.get(server.getName()).contains("WHITELISTED");
        }else{
            return true;
        }
    }*/

    public static int getRank(ProxiedPlayer player){

        AtomicInteger r = new AtomicInteger();

        QueueManager queueManager = FQueue.getInstance().getQueueManager();
        if (queueManager.getRanks() != null && !queueManager.getRanks().isEmpty()){

            if (queueManager.getQueuePlayer(player) != null){
                //should be sorted by priority
                queueManager.getRanks().forEach((priority,permission)->{

                    if (player.hasPermission(permission)){
                        r.set(priority);
                    }
                });
            }

        }

        return r.get();
    }

    public static int getPosition(Queue queue, QueuePlayer queuePlayer){
        return queue.getPlayers().indexOf(queuePlayer);
    }

    public static boolean isFull(Queue queue, ServerInfo server) {
        QueueServer queueServer = queue.getQueueServer();
        return queueServer.getLimit() <= server.getPlayers().size();
    }


    public <K, V> K getKey(Map<K, V> map, V value) {
        return map.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }

}
