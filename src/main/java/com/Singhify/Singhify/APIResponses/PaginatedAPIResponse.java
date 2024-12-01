package com.Singhify.Singhify.APIResponses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PaginatedAPIResponse<T> {
    private List<T> content;
    int pageNo;
    int pageSize;
    int totalPage;
    Long totalElements;
    Boolean hasNext;
}

