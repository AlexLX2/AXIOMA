package md.akdev_service_management.sm.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@MappedSuperclass
public abstract class Catalogue implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "valid_id")
    private boolean valid;

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
}
