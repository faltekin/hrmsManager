package dev.patika.hrmsManager.core.utilities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestPageableEntity <T>{

    private List<T> content;

    private long pageNumber;

    private long pageSize;

    private long totalElement;


}
