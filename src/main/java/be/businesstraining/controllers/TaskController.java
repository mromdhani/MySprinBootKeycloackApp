package be.businesstraining.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import be.businesstraining.dto.TaskDto;
import be.businesstraining.managers.TaskManager;

@Controller
public class TaskController
{
    private final HttpServletRequest request;

    @Autowired
    public TaskController(HttpServletRequest request)
    {
        this.request = request;
    }

    @Autowired
    private TaskManager taskManager;

    @GetMapping(value="/")
    public String home()
    {
        return "index";
    }

    @GetMapping(value="/tasks")
    public String getTasks(Model model)
    {
        List<TaskDto> tasks = taskManager.getAllTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("name", getKeycloakSecurityContext().getIdToken().getGivenName());

        return "tasks";
    }

    private KeycloakSecurityContext getKeycloakSecurityContext()
    {
        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    }

}
