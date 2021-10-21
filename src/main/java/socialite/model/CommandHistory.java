package socialite.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import socialite.storage.CommandHistoryStorage;

public class CommandHistory implements ReadOnlyCommandHistory {

    private List<String> history;

    {
        history = new ArrayList<>();
    }

    public CommandHistory() {}

    public CommandHistory(ReadOnlyCommandHistory toBeCopied) {
        this();
        resetData(toBeCopied);
    }



    public void resetData(ReadOnlyCommandHistory newData) {
        requireNonNull(newData);
        this.history = newData.getCommandHistory();
    }


    public void addCommand(String command) {
        history.add(command);
    }

    @Override
    public List<String> getCommandHistory() {
        return history;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandHistory) // instaceof handles nulls
                && history.equals(((CommandHistory) other).history);
    }

    @Override
    public String toString() {
        return this.history.toString();
    }

    @Override
    public int hashCode() {
        return history.hashCode();
    }
}
