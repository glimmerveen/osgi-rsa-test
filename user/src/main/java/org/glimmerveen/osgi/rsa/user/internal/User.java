package org.glimmerveen.osgi.rsa.user.internal;

import org.glimmerveen.osgi.rsa.api.primary.PrimaryService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class User {

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    @Activate
    public User(@Reference PrimaryService primaryService) {
        LOGGER.info("User()");

        LOGGER.info("Before calling remote service {}", primaryService);
        primaryService.primary();
        LOGGER.info("After calling remote service {}", primaryService);
    }
}
