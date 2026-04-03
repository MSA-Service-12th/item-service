package com.loopang.itemservice.domain.exception;


import com.loopang.common.exception.ForbiddenException;

public class ItemForbiddenException extends ForbiddenException {

  public ItemForbiddenException(String message) {
    super(message);
  }
}
