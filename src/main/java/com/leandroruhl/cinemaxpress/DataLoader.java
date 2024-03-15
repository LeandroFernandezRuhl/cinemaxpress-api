package com.leandroruhl.cinemaxpress;

import com.leandroruhl.cinemaxpress.security.model.Role;
import com.leandroruhl.cinemaxpress.security.model.UserEntity;
import com.leandroruhl.cinemaxpress.security.repository.RoleRepository;
import com.leandroruhl.cinemaxpress.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${cinemaxpress.app.demoUsername}")
    private String DEMO_USERNAME;
    @Value("${cinemaxpress.app.demoPassword}")
    private String DEMO_PASSWORD;

    @Autowired
    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if the user already exists (optional)
        // Create a new user
        UserEntity user = new UserEntity();
        user.setUsername(DEMO_USERNAME);

        // Encrypt the password with bcrypt
        String encryptedPassword = passwordEncoder.encode(DEMO_PASSWORD);
        user.setPassword(encryptedPassword);

        // Add the user role
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        user.getRoles().add(userRole);
        user.getRoles().add(adminRole);

        // Save the user to the database
        userRepository.save(user);
    }
}