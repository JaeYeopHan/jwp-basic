package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner cs = new ControllerScanner();
        Set<Class<?>> controllers = cs.getControllers();
        for (Class clazz : controllers) {
            Set<Method> methods = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
            for (Method method : methods) {
                RequestMapping rm = method.getAnnotation(RequestMapping.class);
                handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(clazz, method));
            }
        }
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
