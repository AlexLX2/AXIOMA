package md.akdev_service_management.sm.models.user;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_roles")
public class UserRole implements DTO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Roles role;

}