package com.expensemanager.exception.category;

public class CategoryNotFound extends RuntimeException{
    public CategoryNotFound(String msg){
        super(msg);
    }
}
