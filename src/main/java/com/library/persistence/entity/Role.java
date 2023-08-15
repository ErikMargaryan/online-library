package com.library.persistence.entity;

import com.library.persistence.entity.joinEntity.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role-id-generator")
    @SequenceGenerator(name = "role-id-generator", sequenceName = "my_sequence_name", initialValue = 4)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
//    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<UserRole> userRoles = new ArrayList<>();
}
