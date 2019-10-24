package com.cerbansouto.compucar.tracing;

import com.cerbansouto.compucar.api.ProfileService;
import com.cerbansouto.compucar.api.TraceService;
import com.cerbansouto.compucar.model.Profile;
import com.cerbansouto.compucar.model.Trace;
import com.cerbansouto.compucar.services.InvalidEntityException;
import com.cerbansouto.compucar.services.dataAccess.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
public class ServiceProfilingAspect {

    @Autowired
    private ProfileService service;

    @Autowired
    private ProfileRepository repository;

    @Around("execution(* com.cerbansouto.compucar.services.*.*(..) ) " +
            "&& !execution(* com.cerbansouto.compucar.services.ProfileServiceImpl.*(..) ) " +
            "&& !execution(* com.cerbansouto.compucar.services.TraceServiceImpl.*(..) )")
    public Object profileService(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("###### Profiling service invocation ######");
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        saveProfile(joinPoint, start);

        return result;
    }

    private void saveProfile(ProceedingJoinPoint joinPoint, long start) {
        long elapsed = (System.currentTimeMillis() - start);

        Date today = new Date();
        Profile profile = service.fetch(today, joinPoint.getSignature().toString());
        if (profile == null) {
            profile = new Profile();
            profile.setDate(today);
            profile.setOperation(joinPoint.getSignature().toString());
            profile.setAverageResponseTime(elapsed);
            profile.setNumInvocations(1);
            repository.create(profile);
        } else {
            profile.setNumInvocations(profile.getNumInvocations() + 1);
            profile.setAverageResponseTime((profile.getAverageResponseTime() + elapsed) / 2);
            repository.update(profile);
        }
    }
}
