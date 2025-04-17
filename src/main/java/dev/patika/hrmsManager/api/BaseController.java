package dev.patika.hrmsManager.api;

import dev.patika.hrmsManager.core.utilities.PagerUtil;
import dev.patika.hrmsManager.core.utilities.RestPageableEntity;
import dev.patika.hrmsManager.core.utilities.RestPageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BaseController {

    public static Pageable toPageable(RestPageableRequest pageable) {
        return PagerUtil.toPageable(pageable);
    }

    public static <T> RestPageableEntity<T> toPageableResponse(Page<?> page , List<T> content) {
        return PagerUtil.toPageableResponse(page, content);
    }

}
