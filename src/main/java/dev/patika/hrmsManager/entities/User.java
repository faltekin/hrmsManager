package dev.patika.hrmsManager.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "user_name",nullable = false)
    private String name;

    @Column(name = "user_email",unique = true, nullable = false)
    private String email;

    @Column(name = "user_password",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role",nullable = false)
    private Role role;

    @Column(name = "is_user_blocked",nullable = false)
    private boolean blocked;

    @Column(name = "user_failed_login_attempts")
    private int failedLoginAttempts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Salary> salaries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Leave> leaves;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventLog> eventLogs;  // Bir kullanıcının tüm logları

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }


    /*
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
    */

    @Override
    public boolean isAccountNonExpired() {
        return true; // Hesap süresi dolmadıysa true
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.blocked; // blocked false ise true döner → hesap kilitli değil
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Şifre süresi dolmadıysa true
    }

    @Override
    public boolean isEnabled() {
        return true; // Kullanıcı aktifse true
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

    public List<Leave> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<Leave> leaves) {
        this.leaves = leaves;
    }
}
