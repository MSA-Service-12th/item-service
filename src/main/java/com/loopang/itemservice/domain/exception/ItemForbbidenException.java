package com.loopang.itemservice.domain.exception;


import com.loopang.common.exception.ForbiddenException;

public class ItemForbidenException extends ForbiddenException {

  public ItemForbidenException(String message) {
    super(message);
  }
}
