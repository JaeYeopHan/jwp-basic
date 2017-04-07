package next.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import core.db.DataBase;

public class HomeController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
	public String execute(Map<String, Object> modelAndView) throws ServletException, IOException {
    	modelAndView.put("users", DataBase.findAll());
    	return "index.jsp";
    }
}
