package core.nmvc;

import org.reflections.Reflections;

import java.util.Set;

/**
 * Created by Jbee on 2017. 4. 28..
 */
public class ControllerScanner {
    public Set<Class<?>> getControllers() {
        Reflections reflections = new Reflections("core.nmvc");
        return reflections.getTypesAnnotatedWith(core.annotation.Controller.class);
    }
}
