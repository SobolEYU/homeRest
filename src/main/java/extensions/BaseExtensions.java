package extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import modules.GuiceApiModules;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class BaseExtensions implements BeforeEachCallback {

    private Injector injector;

    @Override
    public void beforeEach(ExtensionContext context) {
        context.getTestInstance()
                .ifPresent(instance -> {
                    injector = Guice.createInjector(new GuiceApiModules());
                    injector.injectMembers(instance);
                });
    }

}