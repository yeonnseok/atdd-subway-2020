package wooteco.security.core.userdetails;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String principal);
}
