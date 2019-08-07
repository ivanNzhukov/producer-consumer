package dom.workers;

import java.util.function.Supplier;

public interface Client extends Supplier<String> {

    String makeMessage();
}
