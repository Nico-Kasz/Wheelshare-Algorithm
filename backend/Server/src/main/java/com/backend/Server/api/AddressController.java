package com.backend.Server.api;

import com.backend.Server.model.Poi;
import com.backend.Server.model.externalAPI.Feature;
import com.backend.Server.model.externalAPI.JsonResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequestMapping("api/v1/address")  // request go to this link
@RestController  // this says the class serves as the first layer in the stack: api -> service -> data access
// This is a different endpoint prioritized for routing directions
public class AddressController {
    // some constant variables
    private final String API = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
    private final String TOKEN = "pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MGN4djV5MWFuczJwcDFwYW0zMWxhZyJ9.aOdFpJtgU4ow7ascIJDXQA";

    // post request
    @PostMapping
    public Feature getPosition(@RequestBody String address) {
        // Check
        if (address.length() == 0) {
            System.out.println("wrong");
            return null;
        }
        // Prepare
        RestTemplate restTemplate = new RestTemplate();
        JsonResponse response = null;

        // Let's go
        try {
            // encode the address
            address =  URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            // craft the api
            String uri = API + address + ".json?access_token=" + TOKEN;
            // calling external api and
            // return the corresponding point of interest (POI)
            response = restTemplate.getForObject(uri, JsonResponse.class);
        } catch (UnsupportedEncodingException e) {  // invalid string conversion
            System.err.println(e.getMessage());
            return null;
        } catch (Error e) {  // fail to call api
            System.err.println(e.getMessage());
            return null;
        } finally {
            // check and result
            if (response.getFeatures() == null) {
                return null;
            }
            return response.getFeatures().get(0);
        }
    }

    // testing get request
    @GetMapping
    public String getAddress() {
        return "This is for testing in address api";
    }
}
