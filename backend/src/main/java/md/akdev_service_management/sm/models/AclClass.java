package md.akdev_service_management.sm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "acl_class")
public class AclClass {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "class", nullable = false, length = 100)
    private String classField;

    @Size(max = 100)
    @Column(name = "class_id_type", length = 100)
    private String classIdType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassField() {
        return classField;
    }

    public void setClassField(String classField) {
        this.classField = classField;
    }

    public String getClassIdType() {
        return classIdType;
    }

    public void setClassIdType(String classIdType) {
        this.classIdType = classIdType;
    }

}