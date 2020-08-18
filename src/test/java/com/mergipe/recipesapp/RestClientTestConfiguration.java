package com.mergipe.recipesapp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@TestConfiguration(proxyBeanMethods = false)
@Lazy
public class RestClientTestConfiguration {

    public static final MediaType MEDIA_TYPE = MediaType.parseMediaType("application/hal+json");

    private final String rootUri;

    public RestClientTestConfiguration(@LocalServerPort int port) {
        this.rootUri = "http://localhost:" + port + "/api";
    }

    @Bean
    public TestRestTemplate testRestTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter =
                new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(List.of(MEDIA_TYPE));
        messageConverter.setObjectMapper(mapper);

        return new TestRestTemplate(new RestTemplateBuilder()
                .rootUri(this.rootUri)
                .additionalMessageConverters(messageConverter)
        );
    }
}
