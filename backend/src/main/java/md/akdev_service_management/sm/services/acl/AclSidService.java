package md.akdev_service_management.sm.services.acl;

import md.akdev_service_management.sm.models.acl.AclSid;
import md.akdev_service_management.sm.repositories.acl.AclSidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class AclSidService {
    private final AclSidRepository aclSidRepository;

    @Autowired
    public AclSidService(AclSidRepository aclSidRepository) {
        this.aclSidRepository = aclSidRepository;
    }

    public Optional<AclSid> findBySid(String sid){
      return  aclSidRepository.findAclSidBySid(sid);
    }

    @Transactional
    public void updateAcl(AclSid aclSid){
        aclSidRepository.save(aclSid);
    }
}
