package life.majiang.community.controller;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
//@Controller我允许这个类去接收前端的请求
public class IndexController {

    /*将HelloController修改为indexController
    @GetMapping("/hello") // @GetMapping只处理get请求,@RequestMapping处理所有请求
    public String hello(@RequestParam(name = "name") String name, Model model) {
        // @RequestParam表示请求的参数,String name定义name的格式,
        model.addAttribute("name", name);// 将name放到model中
        return "index"; // 这句话会自动去找一个叫"hello"的模板,在resources的templates中新建一个hello.html
    }*/
    /*9.2注入userMapper*/
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) { //9.3获取token需要注入HttpServletRequest request是获取
        //9.4获取的数据存入数组中
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) { //9.5用equals表示拿到了token
                String token = cookie.getValue(); //用getValue()方法将token取出来
                //9.6写一个方法,传过去一个token获取一个User对象,如果对象User不为空,那就就是成功登录
                User user = userMapper.findByToken(token); //此方法表示去数据库中查,是否有这条记录
                //9.7验证前端的情况,不等于空,展示页面
                if (user != null) {
                    //有记录将user放到session里,这样前端就能判断是展示"我"还是"登录"
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        return "index";
    }
}
