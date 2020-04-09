package br.com.example.fallback.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

@Component
public class FallbackGatewayFilterFactory extends AbstractGatewayFilterFactory<FallbackGatewayFilterFactory.Config> {

    public FallbackGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            Map<String, String> uriVariables = exchange
                    .getAttributeOrDefault(URI_TEMPLATE_VARIABLES_ATTRIBUTE, Collections.emptyMap());

            URI fallbackUri = UriComponentsBuilder
                    .fromUriString(config.getFallbackUri())
                    .build(uriVariables);

            ServerHttpRequest request = exchange.getRequest().mutate().uri(fallbackUri).build();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
        private String fallbackUri;

        public void setFallbackUri(String fallbackUri) {
            this.fallbackUri = fallbackUri;
        }

        public String getFallbackUri() {
            return fallbackUri;
        }
    }
}
