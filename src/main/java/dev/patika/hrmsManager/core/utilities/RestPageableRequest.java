package dev.patika.hrmsManager.core.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestPageableRequest {

    private int pageNumber;

    private int pageSize;

    private String columnName;

    private boolean asc;
}
