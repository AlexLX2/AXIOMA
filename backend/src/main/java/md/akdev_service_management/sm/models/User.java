package md.akdev_service_management.sm.models;

import lombok.*;
import md.akdev_service_management.sm.utils.DTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements DTO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "pw")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "valid_id")
    private boolean isValid;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private List<Roles> roles;


    @OneToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @CreatedBy
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "changed_by", referencedColumnName = "id")
    @LastModifiedBy
    private User changedBy;

    @CreationTimestamp
    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "changed_at")
    private LocalDateTime changed_at;

    public String getFullName(){
        return getFirstName() + ' ' + getLastName();
    }


}
