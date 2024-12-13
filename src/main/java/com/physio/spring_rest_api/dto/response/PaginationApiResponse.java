package com.physio.spring_rest_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationApiResponse extends ApiResponse<Object>{

    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean isLastPage;

}
