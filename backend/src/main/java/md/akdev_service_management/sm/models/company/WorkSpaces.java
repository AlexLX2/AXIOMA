package md.akdev_service_management.sm.models.company;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;
import javax.persistence.*;

@Entity
@Table(name = "work_spaces")
@Data
public class WorkSpaces implements DTO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}

