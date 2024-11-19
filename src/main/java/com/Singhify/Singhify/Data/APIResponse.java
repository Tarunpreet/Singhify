package com.Singhify.Singhify.Data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class APIResponse<T> {
    private List<T> content;
    int pageNo;
    int pageSize;
    int totalPage;
    Long totalElements;
    Boolean hasNext;
}

