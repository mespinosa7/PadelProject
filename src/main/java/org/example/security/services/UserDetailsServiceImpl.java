package org.example.security.services;

import lombok.RequiredArgsConstructor;
import org.example.Exceptions.NotFoundException;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JugadorRepository jugadorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        User user = jugadorRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

}
