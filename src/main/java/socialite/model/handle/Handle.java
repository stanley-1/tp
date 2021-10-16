package socialite.model.handle;

import java.util.Optional;

public abstract class Handle {
    public Optional<String> value;

    public String get() {
        return this.value.orElse(null);
    }
}
