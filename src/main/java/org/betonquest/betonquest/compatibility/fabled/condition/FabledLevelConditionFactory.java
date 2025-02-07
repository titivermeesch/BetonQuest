package org.betonquest.betonquest.compatibility.fabled.condition;

import org.betonquest.betonquest.api.quest.QuestException;
import org.betonquest.betonquest.api.quest.condition.PlayerCondition;
import org.betonquest.betonquest.api.quest.condition.PlayerConditionFactory;
import org.betonquest.betonquest.compatibility.auraskills.condition.AuraSkillsLevelCondition;
import org.betonquest.betonquest.instruction.Instruction;
import org.betonquest.betonquest.instruction.variable.VariableNumber;
import org.betonquest.betonquest.instruction.variable.VariableString;
import org.betonquest.betonquest.quest.PrimaryServerThreadData;
import org.betonquest.betonquest.quest.condition.PrimaryServerThreadPlayerCondition;

/**
 * Factory to create {@link AuraSkillsLevelCondition}s from {@link Instruction}s.
 */
public class FabledLevelConditionFactory implements PlayerConditionFactory {
    /**
     * The data used for primary server access.
     */
    private final PrimaryServerThreadData data;

    /**
     * Create a new Factory to create AuraSkills Stats Conditions.
     *
     * @param data the data used for primary server access.
     */
    public FabledLevelConditionFactory(final PrimaryServerThreadData data) {
        this.data = data;
    }

    @Override
    public PlayerCondition parsePlayer(final Instruction instruction) throws QuestException {
        final VariableString classNameVar = instruction.get(VariableString::new);
        final VariableNumber levelVar = instruction.get(VariableNumber::new);
        return new PrimaryServerThreadPlayerCondition(new FabledLevelCondition(classNameVar, levelVar), data);
    }
}
