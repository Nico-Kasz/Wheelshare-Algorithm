package com.backend.Server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String username;

    public User(@JsonProperty("id") UUID id,
                @JsonProperty("name") String username) {
        this.id = id;
        this.username = username;
    }

    // getter
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
