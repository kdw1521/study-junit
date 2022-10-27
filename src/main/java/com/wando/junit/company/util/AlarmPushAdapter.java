package com.wando.junit.company.util;

import org.springframework.stereotype.Component;

@Component
public class AlarmPushAdapter implements AlarmPush {

    @Override
    public boolean push() {
        return true;
    }
}
