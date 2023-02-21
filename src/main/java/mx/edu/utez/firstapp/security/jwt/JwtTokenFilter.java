package mx.edu.utez.firstapp.security.jwt;

import mx.edu.utez.firstapp.security.services.AuthService;
import org.apache.coyote.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(request); // Recuperar el token del header
            // Validar el token
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token); // Recuperar el username del token
                UserDetails userDetails = service.loadUserByUsername(username); // Recuperar el usuario de la base de datos
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // Crear el objeto de autenticación
                SecurityContextHolder.getContext().setAuthentication(auth); // Guardar el objeto de autenticación en el contexto de seguridad
            }
        } catch (Exception e) {
            LOGGER.error("Fail in the method doFilterInternal -> " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    public String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        } else {
            return null;
        }
    }
}
