package life.majiang.community.controller;

import life.majiang.community.dto.AccesstokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}") //5去配置文件中读取括号中的内容
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper; //8注入UserMapper

    /**
     * 调用callback的时候拿到code,state,调用setClient...全部传回去,
     * 然后通过GithubProvider的调用accesstokenDTO打印出来
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           //http是在HttpServletRequest拿到的,写此方法的时候,spring会自动将上下文中的request放到这个里面来
                           HttpServletRequest request,
                           //9.1注入response
                           HttpServletResponse response) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);//写入数据库方法
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //System.out.println(user.getName());
        // 6判断user不为空
        if(githubUser!=null){ //登录成功
            //8.向UserMapper中添加数据
            User user = new User();
            String token = UUID.randomUUID().toString();//获取用户信息生成token
            user.setToken(token); //使用UUID的形式,将token放到user对象中使用下面set存到数据库中
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //9通过response写入cookie,使用github登录成功以后
            response.addCookie(new Cookie("token", token));//将cookie放到token里面

            //6登录成功,写cokie和session,在callback方法中添加HttpServletRequest
            //request.getSession().setAttribute("user",githubUser); //将user对象放到session中,就相当于已经创建成功了一张银行卡,但是还并没有给前端银行卡,如果不手动给签前端,前端也会自动创建,但是不能选号
            return "redirect:/"; //使用redirect重定向到index,下一步写index.html
        }else {
            //6登录失败,重新登录
            return "redirect:/"; //失败也跳转回index页面
        }
    }
}
