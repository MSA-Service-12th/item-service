package com.loopang.itemservice.domain.exception;

import com.loopang.common.exception.ErrorCodeSpec;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements ErrorCodeSpec {

;

  private final String code;
  private final HttpStatus status;
  private final String message;
  private final String field;

  ErrorCode(String code, HttpStatus status, String message, String field) {
    this.code = code;
    this.status = status;
    this.message = message;
    this.field = field;

  }


}
