package dev.patika.hrmsManager.dto.request;



import dev.patika.hrmsManager.entities.Role;
import lombok.*;




@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserSaveRequest {

    private String name;

    private String email;

    private String password;

    private Role role;

    private boolean blocked;

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

    public String getPassword() {
        return password;
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
}
