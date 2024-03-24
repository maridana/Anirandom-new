package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import org.springframework.boot.web.servlet.error.ErrorController;
@RestController
public class Error extends Base implements ErrorController {

    private static final String PATH = "/error";

    public Map<String, Object> getContext(HttpServletRequest request) {
        Map<String, Object> context = super.getContext(request);

        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode == null) {
            context.put("http_status_code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            context.put("http_status_message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        } else {
            HttpStatus status = HttpStatus.valueOf(statusCode);
            context.put("http_status_code", status.value());
            context.put("http_status_message", status.getReasonPhrase());
        }
        return context;
    }

    @RequestMapping(PATH)
    public String handle(HttpServletRequest request) throws IOException, PebbleException {
        return render("error.html", getContext(request));
    }


}