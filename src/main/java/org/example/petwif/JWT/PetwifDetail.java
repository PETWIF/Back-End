package org.example.petwif.JWT;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Getter
public class PetwifDetail implements UserDetails {
    private String username;
    private String password;
    private String memberId;
    private String oauthProvider;
    private Collection<? extends GrantedAuthority> authorities;

    public PetwifDetail(String username, String oauthProvider, String memberId, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.oauthProvider = oauthProvider;
        this.memberId = memberId;
        this.authorities = authorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
