package md.akdev_service_management.sm.services.company;

import md.akdev_service_management.sm.dto.company.CompanyDTO;
import md.akdev_service_management.sm.models.company.Company;
import md.akdev_service_management.sm.repositories.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

   public List<Company> findAll(){
        return companyRepository.findAll();
    }

    public Optional<Company> findById(int id){
        return companyRepository.findById(id);
    }

    @Transactional
    public void newCompany(Company company){
        companyRepository.save(company);
    }

    @Transactional
    public void updateCompany(Company company){
        companyRepository.findById(company.getId()).ifPresent(
                companyToUpdate->{
                    companyToUpdate.setName(company.getName());
                    companyToUpdate.setAddress(company.getAddress());
                    companyToUpdate.setUrl(company.getUrl());
                    companyToUpdate.setComments(company.getComments());

                    companyRepository.save(companyToUpdate);
                }
        );
    }
}
