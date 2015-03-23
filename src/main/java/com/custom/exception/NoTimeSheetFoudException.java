package com.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No Timesheet Available") 
public class NoTimeSheetFoudException extends RuntimeException {
    // ...
}
