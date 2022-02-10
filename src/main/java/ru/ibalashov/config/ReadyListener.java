package ru.ibalashov.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import ru.ibalashov.service.GenerateService;

@Configuration
@RequiredArgsConstructor
public class ReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    private final GenerateService generateService;
    private final GenerateProperties generateProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (generateProperties.isEnabled()) {
            generateService.generateData(generateProperties.getSize());
        }
    }
}
