package com.Singhify.Singhify.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Roles {

    STORE_ADMIN,
    STORE_USER,
    STORE_SELLER;

    @JsonCreator
    public static Roles fromString(String roleName) {
        for (Roles role : Roles.values()) {
            if (role.name().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + roleName);
    }


    @JsonValue
    public String getRoleName() {
        return name();
    }
}
