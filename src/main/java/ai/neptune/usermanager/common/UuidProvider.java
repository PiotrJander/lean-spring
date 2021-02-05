package ai.neptune.usermanager.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidProvider {
    public UUID newUuid() {
        return UUID.randomUUID();
    }
}
