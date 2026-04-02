package com.loopang.itemservice.domain.exception;

import com.loopang.common.exception.BadRequestException;

public class ItemBadRequestException extends BadRequestException {
  public ItemBadRequestException(String message) {
    super(message);
  }
}
