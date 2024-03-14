package org.example.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.security.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Este filtro intercepta todas las solicitudes HTTP entrantes y verifica la presencia de un token JWT válido en el encabezado de autorización.
 * Si se encuentra un token válido, autentica al usuario asociado con el token y establece su contexto de seguridad en Spring Security.
 */
@Slf4j
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    /**
     * Este método se ejecuta para cada solicitud HTTP entrante.
     * Intenta extraer y validar el token JWT del encabezado de autorización.
     * Si el token es válido, autentica al usuario asociado con el token y establece su contexto de seguridad.
     * Finalmente, pasa la solicitud y la respuesta al siguiente filtro en la cadena de filtros.
     *
     * @param request     La solicitud HTTP entrante.
     * @param response    La respuesta HTTP que se enviará al cliente.
     * @param filterChain El objeto FilterChain utilizado para invocar el siguiente filtro en la cadena.
     * @throws ServletException Si ocurre un error de servlet durante el procesamiento de la solicitud.
     * @throws IOException      Si ocurre un error de entrada/salida durante el procesamiento de la solicitud.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
    /**
     * Este método analiza el encabezado de autorización de la solicitud para extraer el token JWT.
     *
     * @param request La solicitud HTTP entrante.
     * @return El token JWT si está presente en el encabezado de autorización, o nulo si no se encuentra.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
