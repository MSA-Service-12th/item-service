package com.loopang.itemservice.domain.exception;

import com.loopang.common.exception.NotFoundException;

public class ItemNotFoundException extends NotFoundException {

  public ItemNotFoundException(String message) {
    super(message);
  }
}
