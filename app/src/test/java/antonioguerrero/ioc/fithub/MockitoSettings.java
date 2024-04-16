package antonioguerrero.ioc.fithub;

import org.mockito.configuration.DefaultMockitoConfiguration;

public @interface MockitoSettings {
    Class<DefaultMockitoConfiguration> mockito();
}
