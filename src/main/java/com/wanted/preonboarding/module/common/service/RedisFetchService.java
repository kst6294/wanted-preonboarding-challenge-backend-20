package com.wanted.preonboarding.module.common.service;

import java.util.List;

public interface RedisFetchService {

    String getValue(String key);
    List<String> getValues(List<String> keys);
}
