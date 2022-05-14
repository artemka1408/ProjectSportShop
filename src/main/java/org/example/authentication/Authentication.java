package org.example.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Authentication {
    public static final long ID_ANONYMOUS = -1;
    public static final String ROLE_ANONYMOUS = "ANONYMOUS";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";
    private long id;
    private String login;
    private String password;
    private String role;

    public boolean isAnonymous() {
        return id == ID_ANONYMOUS;
    }

}
