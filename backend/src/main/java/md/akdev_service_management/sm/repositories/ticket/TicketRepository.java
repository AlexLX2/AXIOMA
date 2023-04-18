package md.akdev_service_management.sm.repositories.ticket;

import md.akdev_service_management.sm.models.ticket.Ticket;
import md.akdev_service_management.sm.models.ticket.TicketBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {

    Ticket findByTicketId(Integer id);

    @Query("select  distinct t from AclObjectIdentity aoi" +
            " inner join AclClass cls on aoi.objectIdClass.id = cls.id" +
            " inner join AclEntry ent on aoi.id = ent.aclObjectIdentity.id" +
            " inner join AclSid sid on ent.sid.id = sid.id" +
            " inner join Ticket t on t.roles.id= aoi.objectIdIdentity" +
            " inner join Roles rl on sid.sid = rl.name" +
            " inner join UserRole ur on rl.id = ur.role.id" +
            " inner join User us on us.id = ur.user.id" +
            " where ent.granting = true " +
            " and ent.mask = 1" +
            " and us.login = ?#{principal.username}" +
            " and cls.classField = 'md.akdev_service_management.sm.models.ticket.Ticket'" )
    Page<Ticket> findAll(Pageable pageable);

}