package club.froad.queue.queue;

import club.froad.queue.FQueue;
import club.froad.queue.utils.Message;
import club.froad.queue.utils.QueueUtils;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.util.*;

@Getter
public class QueueManager {

    @Getter private final List<Queue> queues = new ArrayList<>();
    //player sorting by rank
    @Getter private final Collection<QueuePlayer> queuePlayers = new ArrayList<>();
    //rank sorting by integer
    @Getter private final Map<Integer, String> ranks = new LinkedHashMap<>();

    public QueueManager(){
        refreshQueues();
        refreshRanks();
    }

    public void refreshQueues(){
        Configuration config = FQueue.getConfig();
        if (config.getSection("queues") != null){
            queues.clear();
            for (String name : config.getSection("queues").getKeys()){
                this.queues.add(new Queue(
                        name,

                        new QueueServer(config.getString("queues." + name + ".server-name"),
                                1000,
                                true, true),

                        config.getInt("queues." + name + ".queue-limit"),
                        config.getInt("queues." + name + ".send-time"),
                        config.getString("queues." + name + ".reminder-message.header"),
                        config.getStringList("queues." + name + ".reminder-message.message"),
                        config.getString("queues." + name + ".reminder-message.whitelist-message"),
                        config.getString("queues." + name + ".reminder-message.footer"),
                        config.getInt("queues." + name + ".reminder-message.time"),
                        new ArrayList<>()));
            }
            FQueue.getInstance().getProxy().getConsole().sendMessage("§eCreated §f" + queues.size() + "§e queue(s).");
        }
    }

    public void refreshRanks(){
        Configuration config = FQueue.getConfig();
        if (config.getSection("ranks") != null){
            ranks.clear();
            for (String rank : config.getSection("ranks").getKeys()){

                this.ranks.put(
                        config.getInt("ranks." + rank + ".priority"),
                        config.getString("ranks." + rank + ".permission"));

            }

            FQueue.getInstance().getProxy().getConsole().sendMessage("§eRegistered §f" + ranks.size() + "§e rank(s).");
        }
    }

    public QueuePlayer getQueuePlayer(ProxiedPlayer player){
        for (QueuePlayer p : this.getQueuePlayers()){
            if (p.getPlayer() == player){
                return p;
            }
        }
        return null;
    }

    public void leaveQueue(ProxiedPlayer player){
        for (Queue queue : this.getQueues()){
            if (queue.getPlayers().contains(getQueuePlayer(player))){
                queue.getPlayers().remove(getQueuePlayer(player));
                Message.withPrefix(player, FQueue.getConfig().getString("messages.leave-queue").replaceAll("%name%", queue.getName()));
                getQueuePlayers().remove(getQueuePlayer(player));
                return;
            }
        }
        getQueuePlayers().remove(getQueuePlayer(player));
        Message.withPrefix(player, FQueue.getConfig().getString("messages.not-in-queue"));
    }

    public void leaveQueueOnLeave(ProxiedPlayer player){
        for (Queue queue : this.getQueues()){
            if (queue.getPlayers().contains(getQueuePlayer(player))){
                queue.getPlayers().remove(getQueuePlayer(player));
                getQueuePlayers().remove(getQueuePlayer(player));
                return;
            }
        }
        getQueuePlayers().remove(getQueuePlayer(player));
    }

    public void joinQueue(ProxiedPlayer player, String queueName){
        if (isInQueue(player)){
            Message.withPrefix(player, FQueue.getConfig().getString("messages.already-in-queue"));
            return;
        }

        for (Queue queue : this.getQueues()) {
            if (queue.getName().equalsIgnoreCase(queueName)){
                if (queue.getQueueLimit() == queue.getPlayers().size()){
                    Message.withPrefix(player, FQueue.getConfig().getString("messages.queue-full"));
                    return;
                }

                getQueuePlayers().add(new QueuePlayer(player, QueueUtils.getRank(player), System.currentTimeMillis()));
                queue.getPlayers().add(getQueuePlayer(player));
                Message.withPrefix(player, FQueue.getConfig().getString("messages.join-queue").replaceAll("%name%", queue.getName()));

            }else{
                Message.withPrefix(player, FQueue.getConfig().getString("messages.no-queue"));
                return;
            }
        }
    }

    public boolean isInQueue(ProxiedPlayer player){
        for (Queue queue : this.getQueues()) {
            if (queue.getPlayers().contains(getQueuePlayer(player))) {
                return true;
            }
        }
        return false;
    }

}
