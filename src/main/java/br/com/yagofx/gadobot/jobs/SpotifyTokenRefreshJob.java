package br.com.yagofx.gadobot.jobs;

import br.com.yagofx.gadobot.service.SpotifyService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@EnableAsync
@EnableScheduling
@Configuration
@DependsOn("SpotifyServiceProxy")
public class SpotifyTokenRefreshJob {

    private final SpotifyService service;

    public SpotifyTokenRefreshJob(
            @Qualifier("SpotifyServiceProxy")
            SpotifyService service) {
        this.service = service;
    }

    @Async
    @Scheduled(initialDelay = 0, fixedRate = 3000, timeUnit = TimeUnit.SECONDS)
    public void refreshToken() {
        service.refreshToken();
    }

}
