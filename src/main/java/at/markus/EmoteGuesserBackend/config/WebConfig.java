package at.markus.EmoteGuesserBackend.config;

import at.markus.EmoteGuesserBackend.Routes;
import at.markus.EmoteGuesserBackend.middleware.NormalChecker;
import at.markus.EmoteGuesserBackend.middleware.StatsChecker;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NormalChecker()).addPathPatterns(Routes.MAIN+Routes.VERSION+"**");
        registry.addInterceptor(new StatsChecker()).addPathPatterns(Routes.MAIN+"private/**");
    }
}
