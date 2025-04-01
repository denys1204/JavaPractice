package org.example.models.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String login;
    private String password;
    private String role;
}
