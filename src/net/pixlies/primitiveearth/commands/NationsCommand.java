package net.pixlies.primitiveearth.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class NationsCommand extends Command {
	
	public NationsCommand() {
		super("nations");
		
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		switch (args.length) {
		
			case 2:
				if (args[0].equalsIgnoreCase("form")) {
					
				}
				break;
		
		}
		return false;
	}

}
