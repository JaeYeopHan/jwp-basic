package core.support.servlet;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.Controller;
import next.controller.HomeController;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);

	private Map<String, Controller> controllers = new HashMap<>();
	public RequestMapping() {
		log.info("initializing controllers!");
    	controllers.put("/", new HomeController());
    	controllers.put("/index", new HomeController());
	}
	
	public Controller getController(String path) {
		return controllers.get(path);
	}
}
