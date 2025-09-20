package com.expensemanager.exception.category;

public class DuplicateCategoryName extends RuntimeException{
    public DuplicateCategoryName(String msg){
        super(msg);
    }
}
