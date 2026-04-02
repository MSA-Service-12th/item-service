package com.loopang.itemservice.domain.exception;

import com.loopang.common.exception.ErrorCodeSpec;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements ErrorCodeSpec {

  DUPLICATE_ITEM_NAME("DUPLICATE_ITEM_NAME", HttpStatus.BAD_REQUEST,"해당 업체에 이미 동일한 상품명이 존재합니다.", "itemName"),
  ITEM_ALREADY_DELETED("ITEM_ALREADY_DELETED", HttpStatus.BAD_REQUEST, "이미 삭제된 상품입니다.", "itemId");





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
