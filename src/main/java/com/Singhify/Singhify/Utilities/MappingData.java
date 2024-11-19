package com.Singhify.Singhify.Utilities;

import com.Singhify.Singhify.Data.APIResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MappingData<T,V> {
    public APIResponse<V> mappingPageMetaData(Page<T> pageDetail, APIResponse<V> response)
    {
        if (pageDetail == null || response == null) {
            throw new IllegalArgumentException("Page detail or APIResponse cannot be null");
        }

        return new APIResponse<>(
                response.getContent(),
                pageDetail.getNumber(),
                pageDetail.getSize(),
                pageDetail.getTotalPages(),
                pageDetail.getTotalElements(),
                pageDetail.hasNext()
        );
    }
}
