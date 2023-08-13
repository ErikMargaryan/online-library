package com.library.persistence.entity.joinEntity;

import com.library.persistence.entity.Role;
import com.library.persistence.entity.User;
import com.library.persistence.entity.composite.UserRoleKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "users_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @EmbeddedId
    private UserRoleKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    private Role role;
}
