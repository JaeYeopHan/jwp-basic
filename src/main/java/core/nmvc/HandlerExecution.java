package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.RequestMapping;
import core.mvc.ModelAndView;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;

public class HandlerExecution {
    private static final Logger logger = LoggerFactory.getLogger(HandlerExecution.class);

    private Method method;
    private Class clazz;

    public HandlerExecution(Class clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(this.clazz.newInstance(), request, response);
    }
}
