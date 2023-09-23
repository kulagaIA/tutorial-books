package com.tutorial.books.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

import static com.tutorial.books.util.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Role.Name name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @EqualsAndHashCode.Exclude
    private Set<User> users;

    public enum Name {
        ROLE_admin(ADMIN),
        ROLE_user(USER),
        ROLE_librarian(LIBRARIAN),
        ROLE_viewer(VIEWER);

        public final String label;

        private Name(String label) {
            this.label = label;
        }
    }
}
