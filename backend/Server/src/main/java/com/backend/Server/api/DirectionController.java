package com.backend.Server.api;

import com.backend.Server.model.Direction;
import com.backend.Server.model.GeoJson;
import com.backend.Server.model.Poi;
import com.backend.Server.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("api/v1/direction")  // request go to this link
@RestController  // this says the class serves as the first layer in the stack: api -> service -> data access
// This is a different endpoint prioritized for routing directions
public class DirectionController implements Filter {
    private final DirectionService dirService;

    @Autowired
    public DirectionController(DirectionService dirService) {
        this.dirService = dirService;
    }

    // Set this header response for all controller
//    @ModelAttribute
//    public void setResponseHeader(HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
//        response.setHeader("Access-Control-Max-Age", "3600");
//    }

    // This serves as the method handles Post request
    @PostMapping
    public GeoJson findDirection(@RequestBody Direction dir) {
        System.out.println("received post request");
//        return new GeoJson(new ArrayList<>(Arrays.asList(new Poi(-84.73494, 39.50966))));
        return dirService.findDirection(dir);
    }

    // This serves as the method handles Get request
    // This is for testing
    @GetMapping
    public String getDirection() {
//        return new GeoJson(new ArrayList<Poi>(Arrays.asList(new Poi(12, 24))));
        System.out.println("received get request");
        return "This is for testing in direction api";
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
