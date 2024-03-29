package md.akdev_service_management.sm.models.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import md.akdev_service_management.sm.models.acl.AclObjectIdentity;
import md.akdev_service_management.sm.models.user.Roles;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.utils.DTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ticket")
@EntityListeners(AuditingEntityListener.class)
public class Ticket implements DTO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @Column(name = "author")
    private String author;

    @Column(name="title")
    private String title;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private User createdBy;

    @UpdateTimestamp
    @Column(name="changed_at")
    private LocalDateTime changedAt;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name="changed_by", referencedColumnName = "id")
    private User changedBy;

    @ManyToOne
    @JoinColumn(name="status", referencedColumnName = "id")
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name="priority", referencedColumnName = "id")
    private TicketPriority priority;

    @Column(name="responsible")
    private int responsible;

    @ManyToOne
    @JoinColumn(name ="category", referencedColumnName = "id")
    private TicketCategory category;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketBody> ticketBody;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="acl_id", referencedColumnName = "id")
    private AclObjectIdentity acl;

    public Long getId(){
        return acl.getId();
    }

}
