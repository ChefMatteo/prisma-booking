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

    public UserService(ConfigurationComponent configurationComponent, Gson gson) {
        super.config = configurationComponent;
        super.gson = gson;
        super.getterOfPrimaryKey = "getId";
    }

    @PostConstruct
    private void init() {
        init(User.class, "/users.json");
    }

    public PagedResponse<User> findUsersPage(Integer offset, Integer limit) {
        return findPage(list, offset, limit);
    }
}
