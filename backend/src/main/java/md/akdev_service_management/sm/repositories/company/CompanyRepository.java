package md.akdev_service_management.sm.repositories.company;

import md.akdev_service_management.sm.models.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
}
