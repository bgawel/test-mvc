package bgawel.testing.exception.controllers;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import bgawel.testing.fruit.usecases.ValidationException;

@ControllerAdvice
class GlobalSpecificExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    GenericError handleBadRequest(final HttpServletRequest req, final EntityNotFoundException exception) {
        return new GenericError(req.getRequestURL().toString(), exception);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    UnprocessableEntity validationError(final HttpServletRequest req, final ValidationException exception) {
      return new UnprocessableEntity(exception.getField(), exception.getPrettyError());
    }
}
