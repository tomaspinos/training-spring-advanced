package cz.profinit.training.springadvanced.security.service;

import cz.profinit.training.springadvanced.security.model.MagnificentListModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public String getPrincipalName() {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return u.getUsername();
    }

    public boolean canEdit(MagnificentListModel m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        User u = (User) authentication.getPrincipal();

        if (u.getUsername().equals(m.getPrincipal())) {
            return true;
        }

        for (GrantedAuthority grantedAuthority : authorities) {
            if (ROLE_ADMIN.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }


}
