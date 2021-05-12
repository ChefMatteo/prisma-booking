package com.prisma.prismabooking.service;

import com.google.gson.Gson;
import com.prisma.prismabooking.component.ConfigurationComponent;
import com.prisma.prismabooking.model.PagedResponse;
import com.prisma.prismabooking.model.User;
import com.prisma.prismabooking.model.room.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class UserService extends BaseService<User>{

    public static Resource userFile;

    public UserService(ConfigurationComponent configurationComponent, Gson gson) {
        super.config = configurationComponent;
        super.gson = gson;
        super.getterOfPrimaryKey = "getId";
    }

    @PostConstruct
    private void init() {
        userFile = init(User.class, "/users.json");
    }

    public User findUser(String userId) {
        return getSingle(userId);
    }

    public User createUser(User user) {
        return createNew(user, userFile);
    }

    public User updateUser(String userId, User user) {
        return updateSingle(user, userId, userFile);
    }

    public void deleteFromFile(String userId) {
        deleteSingle(userId, userFile);
    }

    public PagedResponse<User> findUsersPage(Integer offset, Integer limit) {
        return findPage(list, offset, limit);
    }
}
