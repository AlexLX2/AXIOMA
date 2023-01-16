package md.akdev_service_management.sm.models;

import lombok.*;
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
@RequiredArgsConstructor
@Table(name = "ticket")
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

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

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<TicketBody> ticketBody;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="work_spaces_id", referencedColumnName = "id")
    private WorkSpaces workSpaces;

    public Long getId(){
        return workSpaces.getId();
    }

}
