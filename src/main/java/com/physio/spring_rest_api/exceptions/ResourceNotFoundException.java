package com.physio.spring_rest_api.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    String resourceName, resourceFieldName;
    UUID resourceId;

    public ResourceNotFoundException(String rName, String resourceFieldName, UUID rId){
        super(String.format("%s not found with %s : %UUID", rName, resourceFieldName, rId));
        this.resourceName = rName;
        this.resourceFieldName = resourceFieldName;
        this.resourceId = rId;
    }

}
