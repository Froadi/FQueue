package club.froad.queue.utils;

import club.froad.queue.FQueue;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

public class PluginMessageUtils {

    public static void requestQueue(Player player, String queue) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("JoinQueue");
        out.writeUTF(queue);

        player.sendPluginMessage(FQueue.getInstance(), "Fque", out.toByteArray());
    }

}
