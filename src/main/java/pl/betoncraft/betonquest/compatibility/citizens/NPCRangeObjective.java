package pl.betoncraft.betonquest.compatibility.citizens;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.VariableNumber;
import pl.betoncraft.betonquest.api.Objective;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

/**
 * Player has to walk towards/away form a npc
 * <p>
 * Created on 30.09.2018.
 */
public class NPCRangeObjective extends Objective implements Listener {

    private final int npcId;
    private final Trigger trigger;
    private final VariableNumber radius;

    public NPCRangeObjective(final Instruction instruction) throws InstructionParseException {
        super(instruction);
        super.template = ObjectiveData.class;
        npcId = instruction.getInt();
        if (npcId < 0) {
            throw new InstructionParseException("NPC ID cannot be less than 0");
        }
        trigger = instruction.getEnum(Trigger.class);
        radius = instruction.getVarNum();
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(final PlayerMoveEvent event) {
        qreHandler.handle(() -> {
            final Player player = event.getPlayer();
            final String playerID = PlayerConverter.getID(player);
            if (!containsPlayer(playerID)) {
                return;
            }
            final NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
            if (npc == null) {
                throw new QuestRuntimeException("NPC with ID " + npcId + " does not exist");
            }
            final Entity npcEntity = npc.getEntity();
            if (npcEntity == null) {
                return;
            }
            if (!npcEntity.getWorld().equals(event.getTo().getWorld())) {
                return;
            }
            final double radius = this.radius.getDouble(playerID);
            final double distanceSqrd = npcEntity.getLocation().distanceSquared(event.getTo());
            final double radiusSqrd = radius * radius;
            if (trigger == Trigger.ENTER && distanceSqrd <= radiusSqrd
                    || trigger == Trigger.LEAVE && distanceSqrd >= radiusSqrd) {
                if (checkConditions(playerID)) {
                    completeObjective(playerID);
                }
            }
        });
    }

    @Override
    public void start() {
        Bukkit.getPluginManager().registerEvents(this, BetonQuest.getInstance());
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public String getDefaultDataInstruction() {
        return "";
    }

    @Override
    public String getProperty(final String name, final String playerID) {
        return "";
    }

    private enum Trigger {
        ENTER,
        LEAVE
    }
}
