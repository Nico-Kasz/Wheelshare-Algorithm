package com.backend.Server.api;

import com.backend.Server.model.Direction;
import com.backend.Server.model.Poi;
import com.backend.Server.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("api/v1/direction")  // request go to this link
@RestController  // this says the class serves as the first layer in the stack: api -> service -> data access
// This is a different endpoint prioritized for routing directions
public class DirectionController {
    private final DirectionService dirService;

    @Autowired
    public DirectionController(DirectionService dirService) {
        this.dirService = dirService;
    }

    // This serves as the method handles Post request
    @PostMapping
    public List<Poi> findDirection(@RequestBody Direction dir) {
        return dirService.findDirection(dir);
    }

    // This serves as the method handles Get request
    // This is for testing
    @GetMapping
    public String getDirection() {
        return "This is for testing";
    }
}
