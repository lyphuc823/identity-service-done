package com.lyphuc.identity_service.configuration;

import com.lyphuc.identity_service.entity.User;
import com.lyphuc.identity_service.enums.Role;
import com.lyphuc.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {

    private PasswordEncoder passwordEncoder;
    // CREATE USER ADMIN WHEN APP FIRST RUN
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if (!userRepository.existsByUsername("admin")){
                var roles = new HashSet<String >();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, pls change it");
            }
        };
    }
}
