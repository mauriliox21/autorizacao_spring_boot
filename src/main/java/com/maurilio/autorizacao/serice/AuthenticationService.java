package com.maurilio.autorizacao.serice;

import com.maurilio.autorizacao.security.UserData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserData("maurilio", "maurilio", "$2a$10$G92VyvGNP9dLZ0BlbEmgCeBoE6r9yWjGiAYAEx9A3KU91SaRO74mS", "ADMIN");
    }
}
