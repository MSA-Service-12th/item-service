package com.loopang.itemservice.domain.exception;

import jakarta.ws.rs.ForbiddenException;

public class ItemForbiddenException extends ForbiddenException {

  public ItemForbiddenException(String message) {
    super(message);
  }
}
