package com.DongLee99.book.springboot.web.dto;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;
        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount); // HelloResponseDto 에서 선언한 메서드를 객체로 생

        //then
        assertThat(dto.getName()).isEqualTo(name); // 테스트 검증 라이브러리의 검증 메소드
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
