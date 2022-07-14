package org.dario.coordinatesconverter.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = BAD_REQUEST)
public class BadRequestException extends BaseRuntimeException {

  private static final long serialVersionUID = 7664935486448971628L;

  public BadRequestException(String code, String message, Throwable cause) {
    super(code, message, cause);
  }
}
