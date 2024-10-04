package com.example.news_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@Entity
@Table(name = "authorities")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING) // для указания, что БД надо записывать Enum строкового типа
    private RoleType authority;

    @ManyToOne(fetch = FetchType.EAGER) // у одного пользователя может быть много ролей
    @JoinColumn(name = "username")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public GrantedAuthority toAuthority() { // приводит объект нашей роли в объект GrantedAuthority
        return new SimpleGrantedAuthority(authority.name());
    }

    public static Role from(RoleType type) { // создает из типа RoleType нашу роль
        var role = new Role();
        role.setAuthority(type);

        return role;
    }


}
