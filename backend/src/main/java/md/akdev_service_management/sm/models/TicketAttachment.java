package md.akdev_service_management.sm.models;

import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ticket_attachment")
public class TicketAttachment implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_body_id", referencedColumnName = "id")
    @ToString.Exclude
    private TicketBody ticketBody;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "file_content", columnDefinition = "BLOB")
    @ToString.Exclude
    private byte[] fileContent;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @CreatedBy
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private User createdBy;

    @UpdateTimestamp
    @Column(name="changed_at")
    private LocalDateTime changedAt;

    @ManyToOne
    @LastModifiedBy
    @JoinColumn(name="changed_by", referencedColumnName = "id")
    private User changedBy;

}
