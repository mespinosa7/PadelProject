package org.example.security;


import lombok.RequiredArgsConstructor;
import org.example.security.jwt.AuthEntryPointJwt;
import org.example.security.jwt.AuthTokenFilter;
import org.example.security.jwt.JwtUtils;
import org.example.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * Clase de configuración de Spring Security que define la configuración de seguridad para la aplicación.
 */
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtUtils jwtUtils;
    /**
     * Crea un bean para el filtro AuthTokenFilter, que se encarga de validar los tokens JWT en las solicitudes HTTP.
     *
     * @return El filtro AuthTokenFilter.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    /**
     * Crea un bean para el proveedor de autenticación DaoAuthenticationProvider, que utiliza el servicio de detalles de usuario y un codificador de contraseñas para autenticar a los usuarios.
     *
     * @return El proveedor de autenticación DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    /**
     * Crea un bean para el administrador de autenticación, que es necesario para configurar la autenticación en Spring Security.
     *
     * @param authConfig La configuración de autenticación.
     * @return El administrador de autenticación.
     * @throws Exception Si ocurre un error al obtener el administrador de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    /**
     * Crea un bean para el codificador de contraseñas BCryptPasswordEncoder, que se utiliza para codificar y decodificar contraseñas.
     *
     * @return El codificador de contraseñas BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura las reglas de seguridad para las solicitudes HTTP y registra los filtros de seguridad que se aplicarán a las solicitudes HTTP.
     *
     * @param http La configuración de seguridad HTTP.
     * @return La cadena de filtros de seguridad.
     * @throws Exception Si ocurre un error al configurar la seguridad HTTP.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
