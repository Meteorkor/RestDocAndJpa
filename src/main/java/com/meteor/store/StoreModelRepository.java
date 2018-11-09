package com.meteor.store;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "store", path = "store")
public interface StoreModelRepository extends PagingAndSortingRepository<StoreModel, String>{
	StoreModel findByKey(@Param("key") String name);
	StoreModel save(StoreModel model);
	
}
