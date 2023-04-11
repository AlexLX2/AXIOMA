package md.akdev_service_management.sm.models.company;

import lombok.Getter;
import lombok.Setter;
import md.akdev_service_management.sm.models.ticket.TicketPriority;
import md.akdev_service_management.sm.models.user.Roles;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.utils.DTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "company")
@EntityListeners(AuditingEntityListener.class)
public class Company implements DTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "name", length = 200)
    private String name;

    @Size(max = 200)
    @Column(name = "address", length = 200)
    private String address;

    @Size(max = 500)
    @Column(name = "url", length = 500)
    private String url;

    @Size(max = 2000)
    @Column(name = "comments", length = 2000)
    private String comments;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @NotNull
    @CreatedBy
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "id")
    private User createdBy;

    @UpdateTimestamp
    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @NotNull
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "changed_by", nullable = false, referencedColumnName = "id")
    private User changedBy;

}