package com.mergipe.recipesapp;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class TestRestTemplateWrapper<T> {

    private final TestRestTemplate template;
    private final String resourcePath;
    private final ParameterizedTypeReference<EntityModel<T>> entityModelTypeReference;
    private final ParameterizedTypeReference<PagedModel<T>> pagedModelTypeReference;

    public TestRestTemplateWrapper(Class<T> entityType, TestRestTemplate template,
                                   String resourcePath) {
        this.template = template;
        this.resourcePath = "/" + resourcePath;
        this.entityModelTypeReference = ParameterizedTypeReference.forType(
                ResolvableType.forClassWithGenerics(EntityModel.class, entityType).getType()
        );
        this.pagedModelTypeReference = ParameterizedTypeReference.forType(
                ResolvableType.forClassWithGenerics(PagedModel.class, entityType).getType()
        );
    }

    public ResponseEntity<PagedModel<T>> getAll() {
        return this.template.exchange(
                this.resourcePath,
                HttpMethod.GET,
                null,
                pagedModelTypeReference
        );
    }

    public ResponseEntity<EntityModel<T>> getOne(Long id) {
        return this.template.exchange(
                this.resourcePath + "/" + id,
                HttpMethod.GET,
                null,
                entityModelTypeReference
        );
    }
}
