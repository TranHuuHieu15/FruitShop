package poly.edu.asmjava5.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import poly.edu.asmjava5.auth.Role;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {

    @Id
    @Column(columnDefinition = "nvarchar(50)")
    private String username;

    @Column(columnDefinition = "nvarchar(50) not null")
    private String fullname;

    @Column(columnDefinition = "nvarchar(100) not null")
    private String email;

    @Column(length = 250, nullable = false)
    private String password;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // lấy quyền user
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
