package com.expensemanager.entity.category;

import com.expensemanager.entity.BaseEntity;
import com.expensemanager.entity.expense.Expense;
import com.expensemanager.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "category",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "category_name"})
)
@Check(constraints = "CHAR_LENGTH(category_name) > 2 AND CHAR_LENGTH(category_name) <= 30")
@NoArgsConstructor
@Getter
public class Category extends BaseEntity {

    @NotBlank(message = "Category name cannot be empty")
    @Column(name = "category_name", nullable = false, length = 30)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Expense> expenses;

    public static Category of(String categoryName,User user){
        Category newCategory = new Category();
        newCategory.categoryName = categoryName;
        newCategory.user = user;
        return newCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryName(), getUser().getId());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Category)) return false;
        Category other = (Category) obj;
        return Objects.equals(this.categoryName, other.getCategoryName()) && Objects.equals(this.getUser().getId(), other.getUser().getId());
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                "} ";
    }
}