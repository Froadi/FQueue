package club.froad.queue.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Queue {

    private String name;
    private QueueServer queueServer;
    private int queueLimit;
    private int sendTime;
    private String header;
    private List<String> message;
    private String whitelistMessage;
    private String footer;
    private int messageTime;
    private List<QueuePlayer> players;

}
