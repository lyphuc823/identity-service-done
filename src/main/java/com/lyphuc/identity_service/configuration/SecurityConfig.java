package com.lyphuc.identity_service.configuration;

import com.lyphuc.identity_service.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/users",
            "/auth/token","/auth/introspect","/auth/logout","/auth/refresh"
    };
    // Đã được thay thế bằng CustomJwtDecoder
//    @Value("${jwt.signerKey}")
//    protected String SIGNER_KEY;

    @Autowired
    private CustomJwtDecoder customJwtDecoder;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests(request ->
                    request.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll()
//                            .requestMatchers(HttpMethod.GET,"/users")
//                            .hasAuthority("ROLE_ADMIN")// Đã được thay thế bằng PreAuthorize
                            //or
//                            .hasRole(Role.ADMIN.name())// Because we have converter it to ROLE_
                            .anyRequest().authenticated()
                    );
            httpSecurity.oauth2ResourceServer(oath2
                -> oath2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)// Verify Token và sign token
                    .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                    .authenticationEntryPoint(new JWTAuthenticationEntryPoint())// Response UNAUTHENTICATED to client
            );

            httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
    // CHANGE PREFIX FROM DEFAULT SCOPE_ TO ROLE_ , SET IT TO oauth2ResourceServer converter
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
    // Đã được thay thế bằng CustomJwtDecoder
//    @Bean
//    JwtDecoder jwtDecoder() {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(),"HS512");
//
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}