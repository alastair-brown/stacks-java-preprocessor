package com.amido.stacks.dynamodb.repository;

#if USE_DYNAMODB
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
#endif
import com.amido.stacks.core.repository.StacksPersistence;
import org.springframework.data.repository.NoRepositoryBean;


#if USE_DYNAMODB
@NoRepositoryBean
public interface StacksDynamoDBRepository<T>
        extends StacksPersistence<T>,
        CrudRepository<T, String>,
        PagingAndSortingRepository<T, String> {}
#else
@NoRepositoryBean
public interface StacksDynamoDBRepository<T> extends StacksPersistence<T> {
}
#endif