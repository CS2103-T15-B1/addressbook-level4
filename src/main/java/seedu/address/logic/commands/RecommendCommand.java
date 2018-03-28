package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

import java.util.List;

public class RecommendCommand extends Command {

    public static final String COMMAND_WORD = "recommend";

    public static final String MESSAGE_SUCCESS = "Recommendations for: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the products most likely to be bought by the " +
            "person identified by the index number used in the last person listing.\n" +
            "Parameters: INDEX (must be a positive integer)\n" +
            "Example:" + COMMAND_WORD + " 1";

    private final Index targetIndex;

    private Person personToRecommendFor;

    public RecommendCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        personToRecommendFor = lastShownList.get(targetIndex.getZeroBased());

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToRecommendFor.getName()));
    }
}
