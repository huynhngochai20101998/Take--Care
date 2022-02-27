package com.tamaumi.takecare.entity;

import com.tamaumi.takecare.common.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private ERole roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
