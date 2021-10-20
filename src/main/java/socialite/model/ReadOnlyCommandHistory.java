package socialite.model;

import java.util.List;

public interface ReadOnlyCommandHistory {
    List<String> getCommandHistory();
}
