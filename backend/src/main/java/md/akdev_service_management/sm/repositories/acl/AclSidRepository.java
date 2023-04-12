package md.akdev_service_management.sm.repositories.acl;

import md.akdev_service_management.sm.models.acl.AclSid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AclSidRepository extends JpaRepository<AclSid,Integer> {

    public Optional<AclSid> findAclSidBySid(String sid);

}
