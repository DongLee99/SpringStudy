package com.DongLee99.book.springboot.web;
import com.DongLee99.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 Json을 반환하는 컨트롤러로 만들어준다.
public class HelloController {

    @GetMapping("/hello") // http 메서드인 Get의 요청을 받을수 있는 api 를 만들어줌. /hello로 요청이 들어올시 hello() 실행
    public String hello() {
        return "hello";
    }


    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
