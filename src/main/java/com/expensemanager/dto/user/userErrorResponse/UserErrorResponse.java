package com.expensemanager.dto.user.userErrorResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
