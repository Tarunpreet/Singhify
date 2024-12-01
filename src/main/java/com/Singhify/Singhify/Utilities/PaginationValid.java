package com.Singhify.Singhify.Utilities;

import com.Singhify.Singhify.Exception.PaginationParameterNotValid;

public class PaginationValid {

    public static void checkParameters(int pageSize,int pageNumber)
    {

        if(pageNumber<0)
        {
            throw new PaginationParameterNotValid("Page Number");
        }
        if(pageSize<0)
        {
            throw new PaginationParameterNotValid("Page Size");
        }
    }
}
