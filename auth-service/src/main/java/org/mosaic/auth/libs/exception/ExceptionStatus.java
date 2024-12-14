package org.mosaic.auth.libs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {

  // User
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),


  // Company
  COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "업체가 존재하지 않습니다.");


  private final int status;
  private final String message;
  private final String err;

  ExceptionStatus(HttpStatus httpStatus, String message) {
    this.status = httpStatus.value();
    this.message = message;
    this.err = httpStatus.getReasonPhrase();
  }

}