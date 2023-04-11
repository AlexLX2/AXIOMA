package md.akdev_service_management.sm.models.acl;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "acl_entry")
public class AclEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "acl_object_identity", nullable = false)
    private AclObjectIdentity aclObjectIdentity;

    @NotNull
    @Column(name = "ace_order", nullable = false)
    private Integer aceOrder;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sid", nullable = false)
    private AclSid sid;

    @Column(name = "mask", columnDefinition = "INT UNSIGNED not null")
    private Long mask;

    @NotNull
    @Column(name = "granting", nullable = false)
    private Boolean granting = false;

    @NotNull
    @Column(name = "audit_success", nullable = false)
    private Boolean auditSuccess = false;

    @NotNull
    @Column(name = "audit_failure", nullable = false)
    private Boolean auditFailure = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AclObjectIdentity getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    public Integer getAceOrder() {
        return aceOrder;
    }

    public void setAceOrder(Integer aceOrder) {
        this.aceOrder = aceOrder;
    }

    public AclSid getSid() {
        return sid;
    }

    public void setSid(AclSid sid) {
        this.sid = sid;
    }

    public Long getMask() {
        return mask;
    }

    public void setMask(Long mask) {
        this.mask = mask;
    }

    public Boolean getGranting() {
        return granting;
    }

    public void setGranting(Boolean granting) {
        this.granting = granting;
    }

    public Boolean getAuditSuccess() {
        return auditSuccess;
    }

    public void setAuditSuccess(Boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    public Boolean getAuditFailure() {
        return auditFailure;
    }

    public void setAuditFailure(Boolean auditFailure) {
        this.auditFailure = auditFailure;
    }

}