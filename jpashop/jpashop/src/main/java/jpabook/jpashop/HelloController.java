package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping //데이터를 가져올 때 사용하며 get 요청을 처리하는 메서드를 맵핑
    public String hello(Model model){//Model에 데이터를 실어서 view에 넘김
        model.addAttribute("data","hello!");
        return "hello";//리턴은 화면의 이름
    }
}
