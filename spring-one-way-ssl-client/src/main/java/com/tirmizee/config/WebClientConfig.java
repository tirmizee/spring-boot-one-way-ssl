package com.tirmizee.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.Arrays;


@Configuration
public class WebClientConfig {

    @Value("${secure-server.base-url}")
    private String baseUrl;

    @Value("${secure-server.ssl-bundle}")
    private String sslBundleName;

    @Bean
    public WebClient webClient(SslBundles sslBundles) throws SSLException {

        var sslBundle = sslBundles.getBundle(sslBundleName);
        var sslBundleOptions = sslBundle.getOptions();

        var sslContextBuilder = SslContextBuilder.forClient()
                .keyManager(sslBundle.getManagers().getKeyManagerFactory())
                .trustManager(sslBundle.getManagers().getTrustManagerFactory());

        if (sslBundleOptions.getEnabledProtocols() != null) {
            sslContextBuilder.protocols(sslBundleOptions.getEnabledProtocols());
        }

        if (sslBundleOptions.getCiphers() != null) {
            sslContextBuilder.ciphers(Arrays.stream(sslBundleOptions.getCiphers()).toList());
        }

        var nettySslContext = sslContextBuilder.build();

        var httpClient = HttpClient.create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(nettySslContext));

        return WebClient.builder()
                .baseUrl(baseUrl)
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
