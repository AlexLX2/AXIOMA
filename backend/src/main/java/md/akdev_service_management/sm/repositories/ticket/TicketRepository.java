package md.akdev_service_management.sm.repositories.ticket;

import md.akdev_service_management.sm.models.ticket.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {

    Ticket findByTicketId(Integer id);

    @Query(value = "select t from AclObjectIdentity aoi\n" +
            "    inner join AclClass cls on aoi.objectIdClass.id = cls.id\n" +
            "    inner join AclEntry ae on aoi.id = ae.aclObjectIdentity.id\n" +
            "    inner join AclSid sid on ae.sid.id = sid.id\n" +
            "    inner join Ticket t on aoi.id = t.acl.id\n" +
            "    inner join Roles rl on rl.name = sid.sid\n" +
            "    inner join UserRole ur on rl.id = ur.role.id\n" +
            "    inner join User us on ur.user.id = us.id\n" +
            "where ae.granting = true\n" +
            "and ae.mask = 1 and us.login =?#{principal.username}\n" +
            "and cls.classField = 'md.akdev_service_management.sm.models.ticket.Ticket'")
    Page<Ticket> findAll(Pageable pageable);

    @Query(value = "select count(t) from AclObjectIdentity aoi\n" +
            "    inner join AclClass cls on aoi.objectIdClass.id = cls.id\n" +
            "    inner join AclEntry ae on aoi.id = ae.aclObjectIdentity.id\n" +
            "    inner join AclSid sid on ae.sid.id = sid.id\n" +
            "    inner join Ticket t on aoi.id = t.acl.id\n" +
            "    inner join Roles rl on rl.name = sid.sid\n" +
            "    inner join UserRole ur on rl.id = ur.role.id\n" +
            "    inner join User us on ur.user.id = us.id\n" +
            "where ae.granting = true\n" +
            "and ae.mask = 1 and us.login =?#{principal.username}\n" +
            "and cls.classField = 'md.akdev_service_management.sm.models.ticket.Ticket'")
    long count();

}