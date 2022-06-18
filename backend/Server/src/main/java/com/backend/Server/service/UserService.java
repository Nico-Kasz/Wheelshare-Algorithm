package com.backend.Server.service;

import com.backend.Server.dao.UserDao;
import com.backend.Server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    // @Autowired is for injecting the repository data into this service
    // @Qualifier("SampleDao") is saying the UserDao interface uses the implementation that has Repository("SampleDao")
    @Autowired
    public UserService(@Qualifier("SampleDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public int addUser(User user) {
        return userDao.addUser(user);
    }

    public List<User> getAllUser() {
        return userDao.selectAllUser();
    }
}
