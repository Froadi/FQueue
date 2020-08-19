package club.froad.queue.api;

import club.froad.queue.utils.PluginMessageUtils;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class FqueAPI {

    public void joinQueue(Player player, String queue){
        PluginMessageUtils.requestQueue(player, queue);
    }

    public void leaveQueue(Player player){

    }

}
