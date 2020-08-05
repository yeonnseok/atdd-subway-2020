package wooteco.security.core.authentication;

import wooteco.security.core.Authentication;

public interface AuthenticationManager {
    Authentication authenticate(AuthenticationToken authenticationToken);
}
