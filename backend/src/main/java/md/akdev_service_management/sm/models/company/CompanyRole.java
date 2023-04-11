package md.akdev_service_management.sm.models.company;

import lombok.Getter;
import lombok.Setter;
import md.akdev_service_management.sm.models.user.Roles;
import md.akdev_service_management.sm.models.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "company_roles")
@EntityListeners(AuditingEntityListener.class)
public class CompanyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Roles role;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @NotNull
    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "created_by")
    private User createdBy;

    @UpdateTimestamp
    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @NotNull
    @LastModifiedBy
    @Column(name = "changed_by")
    private Integer changedBy;

}