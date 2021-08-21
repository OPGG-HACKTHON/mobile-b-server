package co.mobile.b.server.config.security;

import co.mobile.b.server.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security 인증 유저 객체
 */
public class SecurityUser implements UserDetails {

    private Long userSeq;
    private String email;
    private String password;
    private User user;

    /**
     * Instantiates a new Security user.
     */
    public SecurityUser() {
    }

    /**
     * Instantiates a new Security user.
     *
     * @param user the user
     */
    public SecurityUser(User user) {
        this.userSeq = user.getUserSeq();
        this.email = user.getEmail();
        this.password = "{noop}"+user.getPassword();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Long getSeq() {
        return this.userSeq;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
