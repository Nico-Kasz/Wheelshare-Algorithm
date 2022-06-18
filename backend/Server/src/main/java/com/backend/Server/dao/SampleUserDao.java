package com.backend.Server.dao;

import com.backend.Server.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// @Repository is to create an instance of this object and inject this object to other object that need it
// In other word, this class serves as a repository for other to refer to. This acts like third layer in the
// api -> service -> data access
@Repository("SampleDao")
public class SampleUserDao implements UserDao {

    private static List<User> DB = new ArrayList<>();
    @Override
    public int insertUser(UUID id, User user) {
        DB.add(new User(id, user.getUsername()));
//         // this is for testing
//        System.out.println("Testing: " + user.getUsername());
//        // this is for testing
        return 1;  // 1 for success, 0 for failure
    }

    @Override
    public List<User> selectAllUser() {
//        // This is for testing
//        DB.add(new User(UUID.randomUUID(), "Testing"));
//        // This is for testing
        return DB;
    }
}
