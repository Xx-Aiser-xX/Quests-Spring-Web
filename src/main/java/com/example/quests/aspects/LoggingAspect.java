package com.example.quests.aspects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Aspect
public class LoggingAspect {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Before("execution(* com.example.quests.controllers.*.*(..)) " +
            "|| execution(* com.example.quests.controllers.admin.*.*(..)) " +
            "|| execution(* com.example.quests.exceptions.*.*(..))")
    public void log(JoinPoint joinPoint) throws Throwable{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String methodName = joinPoint.getSignature().getName();
        String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        LOG.log(Level.INFO, "method called: " + methodName + ", url request: " + url + ", user: " + auth.getName());
    }
}
