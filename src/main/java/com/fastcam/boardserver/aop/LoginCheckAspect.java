package com.fastcam.boardserver.aop;

import com.fastcam.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Log4j2
@Aspect
public class LoginCheckAspect {
    @Around("@annotation(com.fastcam.boardserver.aop.LoginCheck) && @ annotation(loginCheck)")
    // aop는 joinpoint로 전달할 값을 만들어줌
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = (HttpSession)((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        String id = null;
        int idIndex = 0;

        String userType = loginCheck.type().toString();
        switch (userType) {
            case "ADMIN" :
                id = SessionUtil.getLoginAdminId(session);
                break;
            case "USER" :
                id = SessionUtil.getLoginMemberId(session);
                break;
        }
        if (id == null) {
            log.error(proceedingJoinPoint.toString() + "accountName: " + id);
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 ID값을 확인해주세요."){};
        }

        Object[] modefiedArgs = proceedingJoinPoint.getArgs();

        if(proceedingJoinPoint.getArgs() != null) modefiedArgs[idIndex] = id;

        return proceedingJoinPoint.proceed(modefiedArgs);
     }
}
