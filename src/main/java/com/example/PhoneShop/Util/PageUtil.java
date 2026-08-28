package com.example.PhoneShop.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PageUtil {
    int DEFAULT_PAGE_LIMIT = 2;
    int DEFAULT_PAGE_NUMBER = 1;
    String PAGE_LIMIT = "_limit";
    String PAGE_NUMBER = "_page";

    static Pageable getPageable(int pageNumber, int pageSize) {
        if (pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_LIMIT;
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return pageable;
    }

    static Pageable getPageable(Map<String, String> params) {
        int pageLimit = DEFAULT_PAGE_LIMIT;
        int pageNumber = DEFAULT_PAGE_NUMBER;

        if (params != null) {
            if (params.containsKey(PAGE_LIMIT)) {
                try {
                    pageLimit = Integer.parseInt(params.get(PAGE_LIMIT));
                } catch (NumberFormatException ignored) {}
            }
            if (params.containsKey(PAGE_NUMBER)) {
                try {
                    pageNumber = Integer.parseInt(params.get(PAGE_NUMBER));
                } catch (NumberFormatException ignored) {}
            }
        }
        return getPageable(pageNumber, pageLimit);
    }
}
