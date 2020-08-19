package club.froad.queue.commands;

import club.froad.queue.FQueue;
import club.froad.queue.queue.QueueManager;
import club.froad.queue.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;

public class FqueCommand extends Command {

    public FqueCommand(){
        super("fque", "fque.admin", "fqueue", "reloadqueue", "que");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1){

            sendHelp(sender);

        }else if (args.length == 1 && args[0].equalsIgnoreCase("reload")){

            Message.withPrefix(sender, "&7Reloading...");

            FQueue.getInstance().getQueueManager().refreshQueues();
            FQueue.getInstance().getQueueManager().refreshRanks();
            try {
                FQueue.getInstance().saveConfig();
                FQueue.getInstance().loadConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Message.withPrefix(sender, "&7Successfully reloaded...");

        }else if (args.length == 1 && args[0].equalsIgnoreCase("test")){

            /*Message.withPrefix(sender, "&7Sorting...");


            Message.withPrefix(sender, "&7Successfully reloaded...");*/

        }else{

            sendHelp(sender);

        }
    }

    private void sendHelp(CommandSender sender){
        Message.send(sender, "&eRunning Fque" + FQueue.getInstance().getProxy().getPluginManager().getPlugin("FqueBungee").getDescription().getVersion() +  "made by Froad#3222");
        Message.send(sender, "&7/fque reload");
    }

}
