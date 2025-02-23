package org.betonquest.betonquest.feature.registry.processor;

import org.betonquest.betonquest.api.config.quest.QuestPackage;
import org.betonquest.betonquest.api.logger.BetonQuestLogger;
import org.betonquest.betonquest.api.quest.QuestException;
import org.betonquest.betonquest.config.PluginMessage;
import org.betonquest.betonquest.feature.QuestCanceler;
import org.betonquest.betonquest.id.QuestCancelerID;
import org.betonquest.betonquest.quest.registry.processor.QuestProcessor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores Quest Canceller.
 */
public class CancellerProcessor extends QuestProcessor<QuestCancelerID, QuestCanceler> {
    /**
     * The {@link PluginMessage} instance.
     */
    private final PluginMessage pluginMessage;

    /**
     * Create a new Quest Canceler Processor to store them.
     *
     * @param log           the custom logger for this class
     * @param pluginMessage the {@link PluginMessage} instance
     */
    public CancellerProcessor(final BetonQuestLogger log, final PluginMessage pluginMessage) {
        super(log);
        this.pluginMessage = pluginMessage;
    }

    @Override
    public void load(final QuestPackage pack) {
        final ConfigurationSection cancelSection = pack.getConfig().getConfigurationSection("cancel");
        if (cancelSection != null) {
            for (final String key : cancelSection.getKeys(false)) {
                try {
                    values.put(new QuestCancelerID(pack, key), new QuestCanceler(pluginMessage, pack, key));
                } catch (final QuestException e) {
                    log.warn(pack, "Could not load '" + pack.getQuestPath() + "." + key + "' quest canceler: " + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Get the loaded Quest Canceler.
     *
     * @return quest cancelers in a new map
     */
    public Map<QuestCancelerID, QuestCanceler> getCancelers() {
        return new HashMap<>(values);
    }
}
