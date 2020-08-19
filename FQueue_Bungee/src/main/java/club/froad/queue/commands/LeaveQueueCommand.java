package club.froad.queue.commands;

import club.froad.queue.FQueue;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LeaveQueueCommand extends Command {

    public LeaveQueueCommand(){
        super("leavequeue");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer){
            FQueue.getInstance().getQueueManager().leaveQueue((ProxiedPlayer)sender);
        }
    }
}
