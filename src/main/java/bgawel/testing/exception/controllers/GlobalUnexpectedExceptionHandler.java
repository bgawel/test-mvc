package bgawel.testing.exception.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class GlobalUnexpectedExceptionHandler extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler, final Exception ex) {
        ModelAndView mv = super.doResolveException(request, response, handler, ex);
        if (mv == null) {
            Map<String, String> params = new HashMap<>();
            params.put("message", "Unexpected error");
            super.applyStatusCodeIfPossible(request, response, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ModelAndView(new MappingJackson2JsonView(), params);
        }
        return mv;
    }
}
