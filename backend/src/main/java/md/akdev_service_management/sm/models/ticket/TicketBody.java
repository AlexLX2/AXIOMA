package md.akdev_service_management.sm.models.ticket;
import lombok.Data;
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
@Table(name = "ticket_body")
@Data
@EntityListeners(AuditingEntityListener.class)
public class TicketBody implements DTO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    @Column(name = "body")
    private String body;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name="created_by", referencedColumnName = "id")
    @CreatedBy
    private User createdBy;

    @UpdateTimestamp
    @Column(name="changed_at")
    private LocalDateTime changedAt;

    @OneToOne
    @JoinColumn(name="changed_by", referencedColumnName = "id")
    @LastModifiedBy
    private User changedBy;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ticketBody")
    private List<TicketAttachment> ticketAttachment;

    public TicketBody(Ticket ticket) {
        this.ticket = ticket;
    }

    public TicketBody() {

    }
}
