package club.froad.queue.commands;

import club.froad.queue.FQueue;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinQueueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }

        if (args.length == 1){
            FQueue.getInstance().getApi().joinQueue((Player)sender, args[0]);
        }

        return false;
    }
}
