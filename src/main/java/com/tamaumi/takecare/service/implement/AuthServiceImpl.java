package com.tamaumi.takecare.service.implement;

import com.tamaumi.takecare.common.enums.ERole;
import com.tamaumi.takecare.converter.AuthConverter;
import com.tamaumi.takecare.entity.CustomUserDetails;
import com.tamaumi.takecare.entity.Role;
import com.tamaumi.takecare.entity.User;
import com.tamaumi.takecare.exception.RegistrationFailedException;
import com.tamaumi.takecare.model.request.AuthRequest;
import com.tamaumi.takecare.repository.RoleRepository;
import com.tamaumi.takecare.repository.UserRepository;
import com.tamaumi.takecare.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.tamaumi.takecare.common.enums.ResponseDefinition.REGISTER_FAILED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {
    private final static Logger LOGGER = LogManager.getLogger(AuthServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(user);
    }

    @Transactional
    public UserDetails loadUserById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not found with id: " + userId)
                );

        return new CustomUserDetails(user);
    }

    @Override
    public boolean signUp(AuthRequest authRequest) {
        User user;

        Role role1 = new Role();
        role1.setRoleName(ERole.ROLE_MEMBER);

        roleRepository.save(role1);

        Role role = roleRepository.findByRoleName(ERole.valueOf(authRequest.getRole()))
                .orElseThrow(
                        () -> new RegistrationFailedException(
                                REGISTER_FAILED.getMessage(),
                                REGISTER_FAILED.getDevCode()
                        )
                );
        Set<Role> roles = new HashSet<>();

        roles.add(role);

        LocalDateTime createdAt = LocalDateTime.now();

        user = AuthConverter.toEntity(authRequest);
        user.setCreatedAt(createdAt);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        try {
            userRepository.save(user);
            //send email
            return true;
        } catch (Exception exception) {
            LOGGER.error(REGISTER_FAILED.getMessage() + " : " + exception.getMessage());

            return false;
        }
    }
}
