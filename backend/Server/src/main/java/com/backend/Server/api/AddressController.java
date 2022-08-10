package com.backend.Server.api;

import com.backend.Server.model.Poi;
import com.backend.Server.model.externalAPI.Feature;
import com.backend.Server.model.externalAPI.JsonResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequestMapping("api/v1/external")  // request go to this link
@RestController  // this says the class serves as the first layer in the stack: api -> service -> data access
// This is a different endpoint prioritized for routing directions
public class AddressController {
    // some constant variables
    private final String API = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
    private final String TOKEN = "pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MGN4djV5MWFuczJwcDFwYW0zMWxhZyJ9.aOdFpJtgU4ow7ascIJDXQA";

    // Set this header response for all controller
    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
    }

    // get request for the file
//    @GetMapping(value = "/file/{fileName}",
//                produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public @ResponseBody byte[] getFile(@PathVariable String fileName) {
//        try {
//            // Return as byte array
//            InputStream in = getClass().getResourceAsStream("/Users/namnbk/Desktop/haanhtuan");
//            // Get the file from the system
//            return IOUtils.toByteArray(in);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Do not see the specified file on the server", e);
//        }
//    }

    // post request for getting an address's location
    @PostMapping("/address")
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

    // testing get request for address
    @GetMapping("/address")
    public String getAddress() {
        return "This is for testing in address api";
    }

    // testing put request for address
    @PutMapping("/address")
    public void postAddress(String test) {
        // testing something out
        System.out.println("This is for testing");
    }
}
