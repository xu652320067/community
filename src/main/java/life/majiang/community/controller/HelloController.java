package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@Controller我允许这个类去接收前端的请求
public class HelloController {

    @GetMapping("/hello") // @GetMapping只处理get请求,@RequestMapping处理所有请求
    public String hello(@RequestParam(name = "name") String name, Model model) {
        // @RequestParam表示请求的参数,String name定义name的格式,
        model.addAttribute("name", name);// 将name放到model中
        return "hello"; // 这句话会自动去找一个叫"hello"的模板,在resources的templates中新建一个hello.html
    }
}
