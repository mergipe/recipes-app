package com.mergipe.recipesapp.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ReferencePriceRepository extends CrudRepository<ReferencePrice, Long> {
}
