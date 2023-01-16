package md.akdev_service_management.sm.models;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Roles implements DTO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    List<User> users;
}
