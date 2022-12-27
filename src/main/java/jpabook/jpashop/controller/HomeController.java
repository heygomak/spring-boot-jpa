package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@Slf4j
public class HomeController {

    //Logger log = (Logger) LoggerFactory.getLogger(getClass());
    @RequestMapping("/")
    public String home(Model model) {
        log.info("home controller");
        return "home";
    }
}
