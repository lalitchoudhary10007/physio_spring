package com.physio.spring_rest_api.dto.response;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {

    private boolean responseStatus;
    private String responseMessage;
    private int responseCode;
    private T data;

}
