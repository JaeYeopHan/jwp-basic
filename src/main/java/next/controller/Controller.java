package next.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

public interface Controller {
	String execute(Map<String, Object> modelAndView) throws ServletException, IOException;	
}
