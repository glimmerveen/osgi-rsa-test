package org.glimmerveen.osgi.rsa.provider.internal;

import org.glimmerveen.osgi.rsa.api.primary.PrimaryService;
import org.glimmerveen.osgi.rsa.api.secondary.SecondaryService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ExportedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = {PrimaryService.class, SecondaryService.class},
        property = "aries.rsa.port=50123",
        immediate = true
)
@ExportedService(service_exported_interfaces = { PrimaryService.class, SecondaryService.class })
public class Provider implements PrimaryService, SecondaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Provider.class);

    @Activate
    public Provider() {
        LOGGER.info("Provider()");
    }

    @Override
    public void primary() {
        LOGGER.info("Provider.primary()");
    }

    @Override
    public void secondary() {
        LOGGER.info("Provider.secondary()");
    }
}
