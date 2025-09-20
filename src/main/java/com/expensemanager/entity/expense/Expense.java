package com.expensemanager.entity.expense;

import com.expensemanager.entity.BaseEntity;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Table(name = "expenses")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Expense extends BaseEntity {
    @Min(value = 1, message = "Amount should be Greater than Zero.")
    @Column(nullable = false , precision = 10 , scale = 2)
    @Check(constraints = "amount > 0")
    private BigDecimal amount;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 100, message = "Description must be at most 100 characters")
    @Column(nullable = false)
    @Check(constraints = "LENGTH(description) > 0 AND LENGTH(description) <= 100")
    private String description;

    @NotNull(message = "can not be null")
    private OffsetDateTime expenseDateTime;

//    @PrePersist
//    public void prePersist(){
//        if(expenseDateTime == null){
//            expenseDateTime = OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5,30));
//        }
//        log.debug("Expense recorded Time::: {}", expenseDateTime);
//    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    public static Expense of(BigDecimal amount,String description,User user, Category category){
        Expense expense = new Expense();
        expense.amount = amount;
        expense.description = description;
        expense.user = user;
        expense.category = category;
        expense.expenseDateTime = OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5,30));
        return expense;
    }
}
