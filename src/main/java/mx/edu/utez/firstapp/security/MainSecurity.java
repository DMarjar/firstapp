package mx.edu.utez.firstapp.security;

import mx.edu.utez.firstapp.models.user.User;
import mx.edu.utez.firstapp.security.jwt.JwtEntryPoint;
import mx.edu.utez.firstapp.security.jwt.JwtTokenFilter;
import mx.edu.utez.firstapp.security.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Esto activa la seguridad, pero no la configura
@EnableGlobalMethodSecurity(prePostEnabled = true) // Esto hace que se pueda usar @PreAuthorize, que es una anotación que se usa para proteger los endpoints
public class MainSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthService service;
    @Autowired
    JwtEntryPoint entryPoint;
    @Bean
    public JwtEntryPoint jwtEntryPoint() {
        return new JwtEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); // super es la clase padre, en este caso WebSecurityConfigurerAdapter
    }

    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    // Este método es para configurar las peticiones http que son publicas y cuales privadas
    protected void configure(HttpSecurity http) throws Exception {
        // cors es una forma de compartir recursos entre dominios
        // csrf es un mecanismo de protección contra ataques de falsificación de solicitudes entre sitios
        http.cors().and().csrf().disable() // Deshabilitar el cors y el csrf
                .authorizeRequests() // Autorizar las peticiones
                .antMatchers("/api-market/auth/**", "/api-market/contact/**").permitAll() // Permitir el acceso a todos los endpoints que empiecen con /api-market/auth
                .antMatchers("/api-market/category/*").permitAll() // solo un * es para que sea exactamente igual o para que sea igual y tenga una palabra mas
                .antMatchers(HttpMethod.POST, "/api-market/user").permitAll()
                .antMatchers(HttpMethod.GET, "/api-market/product").permitAll()
                .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint) // Manejar las excepciones
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // No crear sesiones ya que se usan tokens
        http.addFilterBefore(JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
