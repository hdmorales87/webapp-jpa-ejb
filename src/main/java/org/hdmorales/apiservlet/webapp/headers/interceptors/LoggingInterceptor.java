package org.hdmorales.apiservlet.webapp.headers.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.logging.Logger;

@Logging
@Interceptor
public class LoggingInterceptor {

    @Inject
    private Logger log;

    @AroundInvoke
    public Object logging(InvocationContext invocationContext) throws Exception {

        log.info(" ***** entrando antes de invocar el metodo " +
                invocationContext.getMethod().getName() +
                " de la clase " + invocationContext.getMethod().getDeclaringClass());

        Object resultado = invocationContext.proceed();

        log.info(" ***** Saliendo de la invacion del metodo " +
                invocationContext.getMethod().getName() +
                " de la clase " + invocationContext.getMethod().getDeclaringClass());

        return resultado;
    }
}
