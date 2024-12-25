package com.Singhify.Singhify.Exception;

public class JwtTokenNotValid extends RuntimeException{

    String role=null;
    public JwtTokenNotValid(String role) {
        super("Given User is not authorized for this action "+ role);
        this.role=role;
    }

    public JwtTokenNotValid() {
        super("Given User is not valid please check and try again ");

    }
}
