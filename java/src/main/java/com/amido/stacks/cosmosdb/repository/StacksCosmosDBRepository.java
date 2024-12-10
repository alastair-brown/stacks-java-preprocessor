package com.amido.stacks.cosmosdb.repository;

#if USE_COSMOSDB
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.data.repository.CrudRepository;
#endif
import com.amido.stacks.core.repository.StacksPersistence;
import org.springframework.data.repository.NoRepositoryBean;

#if USE_COSMOSDB
@NoRepositoryBean
public interface StacksCosmosDBRepository<T>
        extends StacksPersistence<T>, CrudRepository<T, String>, CosmosRepository<T, String> {}
#else
@NoRepositoryBean
public interface StacksCosmosDBRepository<T> extends StacksPersistence<T> {
}
#endif