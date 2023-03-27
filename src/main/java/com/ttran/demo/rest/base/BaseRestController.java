package com.ttran.demo.rest.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;

import java.util.List;

public abstract class BaseRestController {
  private static final String EMPTY_ERROR_MESSAGE = "";

  @Autowired
  protected ObjectMapper mapper;

  @Value("${server.port}")
  protected int serverPort;

  protected String getErrorMessage(final List<ObjectError> allErrors) {
    if (CollectionUtils.isEmpty(allErrors)) {
      return EMPTY_ERROR_MESSAGE;
    }
    final ObjectError firstError = allErrors.get(0);
    final String errorMessage = String.format("Object '%s', Message: '%s'", firstError.getObjectName(),
            firstError.getDefaultMessage());
    return StringUtils.hasLength(errorMessage) ? errorMessage : EMPTY_ERROR_MESSAGE;
  }
}
