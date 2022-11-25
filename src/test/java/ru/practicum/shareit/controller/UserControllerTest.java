package ru.practicum.shareit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private TestData td;

    @AfterEach
    void tearDown() throws Exception {
        if (td.userOwner.getId() != null) {
            td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userOwner.getId(), status().isOk());
        }

        if (td.userBooker.getId() != null) {
            td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userBooker.getId(), status().isOk());
        }
    }

    @Test
    void add_return200AndSameUser() throws Exception {
        UserDto userDto = td.userUtils.performPostDto(UserController.BASE_PATH, null, td.userOwner, status().isOk());

        assertEquals(td.userOwner.getName(), userDto.getName());
        assertEquals(td.userOwner.getEmail(), userDto.getEmail());
    }

    @Test
    void add_emailAlreadyExists_return500() throws Exception {
        td.addUserOwner();

        td.userUtils.performPost(UserController.BASE_PATH, null, td.userOwner, status().isInternalServerError());
    }

    @Test
    void add_emailIsIncorrect_return400() throws Exception {
        User userWithIncorrectEmail = new User();
        userWithIncorrectEmail.setName(td.userOwner.getName());
        userWithIncorrectEmail.setEmail(td.userOwner.getEmail() + "@");

        td.userUtils.performPost(UserController.BASE_PATH, null, userWithIncorrectEmail, status().isBadRequest());
    }

    @Test
    void add_emailIsMissing_return400() throws Exception {
        User userWithoutEmail = new User();
        userWithoutEmail.setName(td.userOwner.getName());

        td.userUtils.performPost(UserController.BASE_PATH, null, userWithoutEmail, status().isBadRequest());
    }

    @Test
    void add_update_return200AndUpdatedUser() throws Exception {
        td.addUserOwner();

        UserDto userUpdateDto = td.userUtils.performPatchDto(UserController.BASE_PATH + "/" + td.userOwner.getId(),
                null, td.userBooker, status().isOk());

        assertEquals(td.userOwner.getId(), userUpdateDto.getId());
        assertEquals(td.userBooker.getName(), userUpdateDto.getName());
        assertEquals(td.userBooker.getEmail(), userUpdateDto.getEmail());
    }

    @Test
    void add_get_return200AndSameUser() throws Exception {
        td.addUserOwner();

        UserDto userGetDto = td.userUtils.performGetDto(UserController.BASE_PATH + "/" + td.userOwner.getId(), null,
                status().isOk());

        assertEquals(td.userOwner.getId(), userGetDto.getId());
        assertEquals(td.userOwner.getName(), userGetDto.getName());
        assertEquals(td.userOwner.getEmail(), userGetDto.getEmail());
    }

    @Test
    void add_getAll_return200AndListOfAllUsers() throws Exception {
        td
                .addUserOwner()
                .addUserBooker();

        List<UserDto> usersGetAllDto = td.userUtils.performGetDtoList(UserController.BASE_PATH, null, status().isOk());

        assertEquals(2, usersGetAllDto.size());
        assertEquals(td.userOwner.getId(), usersGetAllDto.get(0).getId());
        assertEquals(td.userOwner.getEmail(), usersGetAllDto.get(0).getEmail());
        assertEquals(td.userOwner.getName(), usersGetAllDto.get(0).getName());
        assertEquals(td.userBooker.getId(), usersGetAllDto.get(1).getId());
        assertEquals(td.userBooker.getEmail(), usersGetAllDto.get(1).getEmail());
        assertEquals(td.userBooker.getName(), usersGetAllDto.get(1).getName());
    }

    @Test
    void add_delete_get_return404() throws Exception {
        td.addUserOwner();

        td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userOwner.getId(), status().isOk());

        td.userUtils.performGet(UserController.BASE_PATH + "/" + td.userOwner.getId(), null, status().isNotFound());

        td.userOwner.setId(null); //помечаем тестовый объект удалённым, что бы не падал tearDown()
    }
}