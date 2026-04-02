package com.loopang.itemservice.domain.exception;

import jakarta.ws.rs.ForbiddenException;

public class ItemForbbidenException extends ForbiddenException {

  public ItemForbbidenException(String message) {
    super(message);
  }
}
