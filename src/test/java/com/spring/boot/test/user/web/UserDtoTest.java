package com.spring.boot.test.user.web;

import com.spring.boot.test.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//import org.springframework.boot.test.autoconfigure.json.JsonTest;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureJsonTesters
@JsonTest
public class UserDtoTest {

    private JacksonTester<UserDto> tester;

    @Test
    public void testSerialize() throws IOException {
        String id = UUID.fromString("939d1c2f-b1f4-49c6-9839-f088401f6781").toString();
        String username = "wim.deblauwe";
        UserRole role = UserRole.ADMIN;
        JsonContent<UserDto> content = tester.write(new UserDto(id, username, role));
        assertThat(content).isEqualToJson("userdto.json");
    }
}