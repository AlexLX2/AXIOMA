package md.akdev_service_management.sm.models.user;

import lombok.Data;
import md.akdev_service_management.sm.models.company.Company;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.utils.DTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Roles implements DTO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

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
