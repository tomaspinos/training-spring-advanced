package cz.profinit.training.springadvanced.security.service;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import cz.profinit.training.springadvanced.security.model.MagnificentListModel;

public class SecurityService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public String getPrincipalName() {
        final User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return u.getUsername();
    }

    public boolean canEdit(final MagnificentListModel m) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        final User u = (User) authentication.getPrincipal();

        if (u.getUsername().equals(m.getPrincipal())) {
            return true;
        }

        for (final GrantedAuthority grantedAuthority : authorities) {
            if (ROLE_ADMIN.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }


}
