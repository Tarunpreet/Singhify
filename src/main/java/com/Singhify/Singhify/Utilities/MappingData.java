package com.Singhify.Singhify.Utilities;

import com.Singhify.Singhify.APIResponses.PaginatedAPIResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MappingData<T,V> {
    public PaginatedAPIResponse<V> mappingPageMetaData(Page<T> pageDetail, PaginatedAPIResponse<V> response)
    {
        if (pageDetail == null || response == null) {
            throw new IllegalArgumentException("Page detail or APIResponse cannot be null");
        }

        return new PaginatedAPIResponse<>(
                response.getContent(),
                pageDetail.getNumber(),
                pageDetail.getSize(),
                pageDetail.getTotalPages(),
                pageDetail.getTotalElements(),
                pageDetail.hasNext()
        );
    }
}
