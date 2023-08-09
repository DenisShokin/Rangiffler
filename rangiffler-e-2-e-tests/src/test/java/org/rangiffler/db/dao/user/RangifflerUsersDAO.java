package org.rangiffler.db.dao.user;

import org.rangiffler.db.entity.user.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface RangifflerUsersDAO {

    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    int createUser(UserEntity user);

    String getUserId(String userName);

    int removeUser(UserEntity user);

    UserEntity getUserByUsername(String userName);

}
