package com.bytebite.backend.security;

import com.bytebite.backend.model.User;
import com.bytebite.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("The email provided is not registered. Please check the" + " email address and try again.");
        });
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(id);
        return UserPrincipal.create(user.orElseThrow(() -> new UsernameNotFoundException("User with id " + id + "not present")));
    }
}
