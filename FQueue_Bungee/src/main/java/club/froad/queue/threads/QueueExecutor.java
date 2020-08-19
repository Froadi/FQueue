package club.froad.queue.threads;

import club.froad.queue.FQueue;
import club.froad.queue.queue.Queue;
import club.froad.queue.queue.QueueManager;
import club.froad.queue.queue.QueuePlayer;
import club.froad.queue.utils.Message;
import club.froad.queue.utils.QueueUtils;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QueueExecutor {

    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private int num = -1;
    private final boolean losePos = FQueue.getConfig().getBoolean("settings.lose-queue-pos");

    public QueueExecutor() {
        executorService.scheduleWithFixedDelay(() -> {

            num++;

            QueueManager queueManager = FQueue.getInstance().getQueueManager();

            if (!FQueue.getInstance().getProxy().getPlayers().isEmpty()) {

                for (Queue queue : queueManager.getQueues()) {

                    Collections.sort(queue.getPlayers());

                    for (QueuePlayer queuePlayer : queue.getPlayers()) {

                        if (queue.getMessageTime() == num) {

                            if (!queue.getHeader().equals("")) {
                                Message.send(queuePlayer.getPlayer(), queue.getHeader());
                            }

                            int pos = QueueUtils.getPosition(queue, queuePlayer)+1;

                            queue.getMessage().forEach(m -> Message.send(queuePlayer.getPlayer(), m.
                                    replaceAll("%pos%", "" + pos).
                                    replaceAll("%max%", "" + queue.getPlayers().size())));

                            //TODO

                            if (queue.getQueueServer().isWhitelisted()){
                                if (!queue.getWhitelistMessage().equals("")) {
                                    Message.send(queuePlayer.getPlayer(), queue.getWhitelistMessage());
                                }
                            }

                            if (!queue.getFooter().equals("")) {
                                Message.send(queuePlayer.getPlayer(), queue.getFooter());
                            }

                        }

                        if (QueueUtils.getPosition(queue, queuePlayer) == 0) {

                            if (queue.getQueueServer().isOnline()){

                                if (!QueueUtils.isFull(queue, FQueue.getInstance().getProxy().getServerInfo(queue.getQueueServer().getName()))){

                                    if (!queue.getQueueServer().isWhitelisted() || queuePlayer.getPlayer().hasPermission("queue.admin")){

                                        queuePlayer.getPlayer().connect(FQueue.getInstance().getProxy().getServerInfo(queue.getQueueServer().getName()));

                                        queue.getPlayers().remove(queuePlayer);
                                        queueManager.getQueuePlayers().remove(queuePlayer);
                                        Message.withPrefix(queuePlayer.getPlayer(), FQueue.getConfig().getString("messages.sending")
                                                .replaceAll("%servername%", queue.getQueueServer().getName()));

                                    }else{
                                    }

                                }else{
                                    Message.withPrefix(queuePlayer.getPlayer(), FQueue.getConfig().getString("messages.server-full"));
                                }

                            }else{
                                if (losePos){
                                    queue.getPlayers().forEach(lose -> queueManager.leaveQueue(queuePlayer.getPlayer()));
                                }
                            }

                        }

                    }
                }

                //enne getrank
                //kaikki server ja full
                //if first
                //FQueue.getInstance().getQueueManager().getQueuePlayers().remove(player);
            }

            if (num == 5) {
                num = -1;
            }

        }, 1L, 1L, TimeUnit.SECONDS);
    }
}
