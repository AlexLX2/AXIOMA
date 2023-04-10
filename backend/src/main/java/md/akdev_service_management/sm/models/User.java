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
import java.util.Objects;

//@Getter
//@Setter
//@RequiredArgsConstructor
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
    private boolean isValid = true;

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

    public User(int id, String login, String password, String firstName, String lastName, String email, boolean isValid, List<Roles> roles, User createdBy, User changedBy, LocalDateTime createdAt, LocalDateTime changed_at) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isValid = isValid;
        this.roles = roles;
        this.createdBy = createdBy;
        this.changedBy = changedBy;
        this.createdAt = createdAt;
        this.changed_at = changed_at;
    }

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getChanged_at() {
        return changed_at;
    }

    public void setChanged_at(LocalDateTime changed_at) {
        this.changed_at = changed_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isValid == user.isValid && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(changed_at, user.changed_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstName, lastName, email, isValid, createdAt, changed_at);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isValid=" + isValid +
                ", createdAt=" + createdAt +
                ", changed_at=" + changed_at +
                '}';
    }
}
