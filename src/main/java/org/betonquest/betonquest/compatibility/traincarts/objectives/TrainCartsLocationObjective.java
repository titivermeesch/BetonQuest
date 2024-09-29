package org.betonquest.betonquest.compatibility.traincarts.objectives;

import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.api.profiles.OnlineProfile;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;
import org.betonquest.betonquest.instruction.variable.location.VariableLocation;
import org.betonquest.betonquest.objectives.AbstractLocationObjective;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * This {@link AbstractLocationObjective} is completed when a player is inside a certain location while riding a train.
 */
public class TrainCartsLocationObjective extends AbstractLocationObjective implements Listener {
    /**
     * The {@link VariableLocation} that stores the location the player has to be inside.
     */
    private final VariableLocation loc;

    /**
     * Creates a new {@link TrainCartsLocationObjective}.
     *
     * @param instruction the Instruction object to be used in the constructor
     * @throws InstructionParseException if there is an error while parsing the instruction
     */
    public TrainCartsLocationObjective(final Instruction instruction) throws InstructionParseException {
        super(BetonQuest.getInstance().getLoggerFactory().create(TrainCartsLocationObjective.class), instruction);
        this.loc = instruction.getLocation();
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
    public String getProperty(final String name, final Profile profile) {
        return "";
    }

    @Override
    protected boolean isInside(final OnlineProfile onlineProfile, final Location location) throws QuestRuntimeException {
        final Entity entity = onlineProfile.getPlayer().getVehicle();
        if (entity == null) {
            return false;
        }
        final MinecartMember<?> minecartMember = MinecartMemberStore.getFromEntity(entity);
        if (minecartMember == null) {
            return false;
        }

        final Location targetLocation = loc.getValue(onlineProfile);
        return targetLocation.getWorld().equals(location.getWorld()) || location.distanceSquared(targetLocation) <= 1.0;
    }
}
