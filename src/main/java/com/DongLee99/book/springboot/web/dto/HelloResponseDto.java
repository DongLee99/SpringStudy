package com.DongLee99.book.springboot.web.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Getter //선언된 모든 필드의 Get 메소드 추가 (LomBok의 기능 이다)
@RequiredArgsConstructor // 선언된 모든 final 필드가 포함된 생성자를 생성 (final 이 아닌 경우는 생성 X)
public class HelloResponseDto {
    private final String name;
    private final int amount;


}
