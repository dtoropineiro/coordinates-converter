package org.dario.coordinatesconverter.exceptions;

import lombok.Getter;

@Getter
public class BaseRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 3341984808138282663L;

  private final String code;

  public BaseRuntimeException(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }
}
