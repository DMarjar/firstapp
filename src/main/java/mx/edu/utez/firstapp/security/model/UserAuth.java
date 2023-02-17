package mx.edu.utez.firstapp.security.model;

import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.models.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserAuth implements UserDetails {
    private String username;
    private String password;
    private Person person;
    private Collection<? extends GrantedAuthority> authorities;

    public UserAuth(String username, String password, Person person, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.authorities = authorities;
    }

    // Metodo para construir el objeto UserAuth
    public static UserAuth build(User user) {
        List<GrantedAuthority> authorityList = user.getRoles() // Obtenemos los roles del usuario
                .stream() // Convertimos la lista de roles en un stream, un stream es una secuencia de datos que se pueden procesar de forma paralela
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Convertimos cada elemento de la lista en un SimpleGrantedAuthority, que es un objeto que implementa la interfaz GrantedAuthority
                .collect(Collectors.toList()); // Convertimos el stream en una lista de objetos GrantedAuthority
        return new UserAuth(user.getUsername(), user.getPassword(), user.getPerson(), authorityList); // Retornamos un objeto UserAuth, que es el que se utiliza para autenticar al usuario
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
