package com.wanted.preonboarding.module.common.service;

import java.util.List;

public interface RedisFindService {

    String getValue(String key);
    List<String> getValues(List<String> keys);
    boolean keyExists(String key);
}
