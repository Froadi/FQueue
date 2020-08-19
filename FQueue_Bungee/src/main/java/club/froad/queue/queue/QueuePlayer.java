package club.froad.queue.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@AllArgsConstructor
@Getter
@Setter
public class QueuePlayer implements Comparable<QueuePlayer> {

    private ProxiedPlayer player;
    private int rank;
    private long queueJoinTime;

    @Override
    public int compareTo(QueuePlayer qp) {

        if(this.getRank()+1000 < qp.getRank()+1000) {
            return -1000;
        } else if (this.getRank()+1000  > qp.getRank()+1000 ) {
            return 1000;
        } else {
            if (qp.getQueueJoinTime() < this.getQueueJoinTime()){
                return -rank;
            }else{
                return rank;
            }
        }

        //return Integer.compare(this.getRank(), qp.getRank());
    }
}
