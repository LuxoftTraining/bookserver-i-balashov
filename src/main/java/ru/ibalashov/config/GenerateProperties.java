package ru.ibalashov.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("book-service.generate")
public class GenerateProperties {
    private boolean enabled = false;
    private long size = 10;
}
