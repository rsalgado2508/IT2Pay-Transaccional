package co.com.it2ex.it2pay.config.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = -2708360353608327880L;

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
