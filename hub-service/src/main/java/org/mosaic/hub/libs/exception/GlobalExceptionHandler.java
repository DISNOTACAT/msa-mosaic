package org.mosaic.hub.libs.exception;

import org.mosaic.hub.libs.util.ApiResponseUtils;
import org.mosaic.hub.libs.util.ApiResponseUtils.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CommonResponse<String>> handleCustomException(CustomException e) {
		return ApiResponseUtils.failed(e.getStatus(), e);
	}
}