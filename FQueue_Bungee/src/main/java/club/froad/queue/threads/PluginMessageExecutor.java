package club.froad.queue.threads;

import club.froad.queue.FQueue;
import club.froad.queue.queue.Queue;
import club.froad.queue.queue.QueueServer;
import lombok.Getter;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class PluginMessageExecutor{

    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);


    public PluginMessageExecutor() {
        executorService.scheduleWithFixedDelay(() -> {

            if (!FQueue.getInstance().getProxy().getPlayers().isEmpty()) {

                for (Queue queue : FQueue.getInstance().getQueueManager().getQueues()){

                    QueueServer server = queue.getQueueServer();
                    ServerInfo serverInfo = FQueue.getInstance().getProxy().getServerInfo(server.getName());

                    serverInfo.ping((serverPing, throwable) -> {

                        if (throwable == null) {

                            String desc = serverPing.getDescription();

                            if (desc.contains("WHITELISTED")){
                                server.setWhitelisted(true);
                                server.setLimit(Integer.parseInt(serverPing.getDescription().split(", ")[1]));
                            }else if (!desc.equals("")){
                                server.setWhitelisted(false);
                                server.setLimit(Integer.parseInt(serverPing.getDescription()));
                            }

                            server.setOnline(true);

                        }else{

                            //System.out.println("throwable null");

                            server.setOnline(true);
                            server.setWhitelisted(false);

                        }
                    });
                }
            }

        }, 1L, 2L, TimeUnit.SECONDS);
    }
}
