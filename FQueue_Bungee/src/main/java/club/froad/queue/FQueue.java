package club.froad.queue;

import club.froad.queue.commands.FqueCommand;
import club.froad.queue.commands.LeaveQueueCommand;
import club.froad.queue.commands.SetLimitCommand;
import club.froad.queue.listeners.PlayerListener;
import club.froad.queue.listeners.PluginMessageListener;
import club.froad.queue.queue.QueueManager;
import club.froad.queue.threads.PluginMessageExecutor;
import club.froad.queue.threads.QueueExecutor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Getter
public final class FQueue extends Plugin {

    @Getter
    private static FQueue instance;
    @Getter
    private static Configuration config;

    private QueueManager queueManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        try {
            loadConfig();
        } catch (IOException ex) {
            System.err.println("COULD NOT LOAD CONFIG.YML! MAKE SURE THE FILE EXISTS!");
            ex.printStackTrace();
            return;
        }

        registerListeners();
        registerCommands();

        getProxy().registerChannel("Fque");

        queueManager = new QueueManager();

        new QueueExecutor();
        new PluginMessageExecutor();

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getProxy().unregisterChannel("Fque");
    }

    public void loadConfig() throws IOException {
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
    }

    public void saveConfig() throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
    }

    private void saveDefaultConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) try {
            InputStream in = getResourceAsStream("config.yml");
            Throwable localThrowable3 = null;
            try {
                Files.copy(in, file.toPath());
            } catch (Throwable localThrowable1) {
                localThrowable3 = localThrowable1;
                throw localThrowable1;
            } finally {
                if (in != null) if (localThrowable3 != null) try {
                    in.close();
                } catch (Throwable localThrowable2) {
                    localThrowable3.addSuppressed(localThrowable2);
                }
                else in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void registerCommands(){
        getProxy().getPluginManager().registerCommand(this, new FqueCommand());
        getProxy().getPluginManager().registerCommand(this, new LeaveQueueCommand());
        getProxy().getPluginManager().registerCommand(this, new SetLimitCommand());
    }

    private void registerListeners(){
        getProxy().getPluginManager().registerListener(this, new PlayerListener());
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
    }
}
