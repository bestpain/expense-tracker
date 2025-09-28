package com.expensemanager.entity.user;

import com.expensemanager.entity.BaseEntity;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.expense.Expense;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseEntity {
    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String role = "USER";

    @Setter
    @Column(precision = 19, scale = 2)
    private BigDecimal incomeLimit = BigDecimal.ZERO;

    @Setter
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Category> categories = new HashSet<>();

    public static User of(String name, String email, String passwordHash, BigDecimal incomeLimit) {
        User u = new User();
        u.name = name;
        u.email = email;
        u.password = passwordHash;
        u.incomeLimit = incomeLimit == null ? BigDecimal.ZERO : incomeLimit;
        return u;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", incomeLimit=" + incomeLimit +
                "} "
                ;
    }
}
