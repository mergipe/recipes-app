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

@TestConfiguration(proxyBeanMethods = false)
@Lazy
public class RestClientTestConfiguration {

    @LocalServerPort
    private int port;

    @Bean
    public TestRestTemplate testRestTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter =
                new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(mapper);

        return new TestRestTemplate(
                new RestTemplateBuilder()
                        .rootUri("http://localhost:" + this.port + "/api")
                        .additionalMessageConverters(messageConverter)
        );
    }
}
