package francescocristiano.U5_W3_D2.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import francescocristiano.U5_W3_D2.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "role", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class Employee implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;
    @Setter
    private String username;
    @Setter
    private String name;
    @Setter
    private String surname;
    @Setter
    private String email;
    @Setter
    private String avatarUrl;
    @Setter
    private String password;
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "employee")
/*
    @JsonManagedReference
*/
    @JsonBackReference // Annotazione trovata che permette a delle entità bidirezionali di non andare in StackOverFlow,
    // ho invertito la referenza per il tipo di applicazione che volevo creare, ovvero potendo vedere all'interno della
    // lista di device l'id dell'utente a cui facessero riferimento e non viceversa
    private List<Device> devices;


    public Employee(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatarUrl = "https://ui-avatars.com/api/?name=" + name + "+" + surname;
        this.password = password;
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
