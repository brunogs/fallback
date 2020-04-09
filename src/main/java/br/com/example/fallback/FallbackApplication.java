package br.com.example.fallback;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.ofDefaults;

@SpringBootApplication
public class FallbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FallbackApplication.class, args);
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		CircuitBreakerConfig config = CircuitBreakerConfig.from(ofDefaults())
				.failureRateThreshold(1)
				.build();
		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
				.timeoutDuration(Duration.ofMillis(200))
				.build();
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(config)
				.timeLimiterConfig(timeLimiterConfig)
				.build());
	}

}
