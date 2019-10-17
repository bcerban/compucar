package com.cerbansouto.compucar;

import com.cerbansouto.compucar.context.WebContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            AppServer webServer = new AppServer("/", WebContext.class);
            webServer.run();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
