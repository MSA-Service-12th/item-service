package com.loopang.itemservice.domain.exception;

import com.loopang.common.exception.ConflictException;

public class ItemConflictException extends ConflictException {

  public ItemConflictException(String message) {
    super(message);
  }
}
