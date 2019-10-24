package com.cerbansouto.compucar.tracing;

import com.cerbansouto.compucar.api.TraceService;
import com.cerbansouto.compucar.model.Trace;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Slf4j
@Component
public class RestTracingAspect {

    @Autowired
    private TraceService service;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }

    @Before("controller()")
    public void traceRestRequest(JoinPoint joinPoint) {
        log.info("###### Tracing request ######");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Trace trace = new Trace();
        trace.setDate(new Date());
        trace.setOperation(joinPoint.getSignature().toString());
        trace.setUsername(request.getHeader(TraceService.USERNAME_HEADER));
        trace.setRequestInfo(String.format("%s %s (%s)", request.getMethod(), request.getRequestURI(), request.getQueryString()));

        try {
            service.create(trace);
        } catch (InvalidEntityException e) {
            log.error("Failed to save trace!", e);
        }
    }
}
