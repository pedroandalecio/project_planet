package br.com.planet.exceptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import br.com.planet.constants.ErrorCodes;
import lombok.Getter;

@Getter
public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private List<String> details;

    public ExceptionResponse(final ErrorCodes errorCode, String details) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.details = Collections.singletonList(details);
    }

    public ExceptionResponse(ErrorCodes errorCode, List<String> details) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.details = details;
    }
}
