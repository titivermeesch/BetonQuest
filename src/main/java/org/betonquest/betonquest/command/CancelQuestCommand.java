package org.betonquest.betonquest.command;

import org.betonquest.betonquest.Backpack;
import org.betonquest.betonquest.Backpack.DisplayType;
import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.api.profile.ProfileProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The /cancelquest command. It opens the list of quests.
 */
@SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
public class CancelQuestCommand implements CommandExecutor {

    /**
     * Creates a new executor for the /cancelquest command.
     */
    public CancelQuestCommand() {
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if ("cancelquest".equalsIgnoreCase(cmd.getName())) {
            if (sender instanceof Player) {
                final ProfileProvider profileProvider = BetonQuest.getInstance().getProfileProvider();
                new Backpack(profileProvider.getProfile((Player) sender), DisplayType.CANCEL);
            }
            return true;
        }
        return false;
    }
}