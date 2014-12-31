package bgawel.testing.fruit.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bgawel.testing.exception.controllers.UnprocessableEntity;
import bgawel.testing.fruit.usecases.FruitDTO;
import bgawel.testing.fruit.usecases.FruitUseCases;
import bgawel.testing.fruit.usecases.ValidationException;

@RestController
class FruitController {

    @Autowired
    private FruitUseCases fruitUseCases;

    @RequestMapping(value = "/fruits", method = RequestMethod.GET, produces="application/json")
    public Iterable<FruitDTO> getAll() {
        return fruitUseCases.getAllFruits();
    }

    @RequestMapping(value = "/fruit/{id}", method = RequestMethod.GET, produces="application/json")
    public FruitDTO get(@PathVariable("id") final Long id) {
        return fruitUseCases.getFruit(id);
    }

    @RequestMapping(value = "/fruit", method = RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public FruitDTO create(@RequestBody @Valid final NewFruitCommand newFruit) { // bind to some custom command object
        return fruitUseCases.createFruit(new FruitDTO(newFruit.getFruitName()));
    }

    /**
     * Narrowed error handler
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    UnprocessableEntity validationError(final HttpServletRequest req, final ValidationException exception) {
      return new UnprocessableEntity(businessToClientFieldsConverter(exception.getField()), exception.getPrettyError());
    }

    private String businessToClientFieldsConverter(final String businessField) {
        return businessField == "name" ? "nameWithPadding" : businessField;
    }
}
