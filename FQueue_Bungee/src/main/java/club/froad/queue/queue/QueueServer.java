package club.froad.queue.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QueueServer {

    private String name;
    private int limit;
    private boolean online;
    private boolean whitelisted;

}
