package org.betonquest.betonquest.command;

import org.betonquest.betonquest.config.PluginMessage;
import org.betonquest.betonquest.feature.Backpack;
import org.betonquest.betonquest.feature.Backpack.DisplayType;
import org.betonquest.betonquest.util.PlayerConverter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The /compass command. It opens the list of quests.
 */
@SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
public class CompassCommand implements CommandExecutor {

    /**
     * The {@link PluginMessage} instance.
     */
    private final PluginMessage pluginMessage;

    /**
     * Creates a new executor for the /compass command.
     *
     * @param pluginMessage the {@link PluginMessage} instance
     */
    public CompassCommand(final PluginMessage pluginMessage) {
        this.pluginMessage = pluginMessage;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if ("compass".equalsIgnoreCase(cmd.getName())) {
            if (sender instanceof Player) {
                new Backpack(pluginMessage, PlayerConverter.getID((Player) sender), DisplayType.COMPASS);
            }
            return true;
        }
        return false;
    }
}
