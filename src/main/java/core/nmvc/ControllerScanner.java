package core.nmvc;

import org.reflections.Reflections;

import java.util.Set;

/**
 * Created by Jbee on 2017. 4. 28..
 */
public class ControllerScanner {

    private Object[] basePackage;

    public ControllerScanner(Object[] basePackage) {
        this.basePackage = basePackage;
    }

    public Set<Class<?>> getControllers() {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(core.annotation.Controller.class);
    }
}
