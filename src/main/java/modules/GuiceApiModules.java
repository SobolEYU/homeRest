package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import services.PetsApi;

public class GuiceApiModules extends AbstractModule {

    @Singleton
    @Provides
    public PetsApi getPetsApi() {
        return new PetsApi();
    }
}
