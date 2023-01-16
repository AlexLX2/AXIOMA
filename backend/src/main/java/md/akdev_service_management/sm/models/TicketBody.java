package md.akdev_service_management.sm.models;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket_body")
@Data
public class TicketBody {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    @Column(name = "ab_from")
    private String from;

    @Column(name="ab_to")
    private String to;

    @Column(name="ab_subject")
    private String subject;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "ab_body")
    private String body;

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

}
