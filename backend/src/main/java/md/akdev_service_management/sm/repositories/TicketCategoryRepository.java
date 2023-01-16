package md.akdev_service_management.sm.repositories;

import md.akdev_service_management.sm.models.TicketCategory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "category", path = "category")
public interface TicketCategoryRepository extends PagingAndSortingRepository<TicketCategory,Integer> {
}
