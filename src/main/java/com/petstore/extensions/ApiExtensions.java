package com.petstore.extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.petstore.config.PetStoreModule;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;


public class ApiExtensions implements TestInstancePostProcessor {

    private final Injector injector = Guice.createInjector(new PetStoreModule());

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        injector.injectMembers(testInstance);
    }
}
