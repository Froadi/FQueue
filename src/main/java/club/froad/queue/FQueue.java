package club.froad.queue;

import club.froad.queue.api.FqueAPI;
import club.froad.queue.commands.JoinQueueCommand;
import club.froad.queue.listeners.ServerListPingListener;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

@Getter
public final class FQueue extends JavaPlugin implements PluginMessageListener {

    @Getter public static FQueue instance;
    @Getter private FqueAPI api;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        registerCommands();
        registerListeners();

        api = new FqueAPI();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "Fque");
        getServer().getMessenger().registerIncomingPluginChannel(this, "Fque", this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands(){
        Server s = getServer();

        s.getPluginCommand("joinqueue").setExecutor(new JoinQueueCommand());
    }

    private void registerListeners(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new ServerListPingListener(), this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

    }
}
