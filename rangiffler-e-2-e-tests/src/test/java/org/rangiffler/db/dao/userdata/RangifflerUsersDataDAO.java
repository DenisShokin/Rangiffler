package org.rangiffler.db.dao.userdata;

import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface RangifflerUsersDataDAO {

    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    int createUser(UserDataEntity user);

    String getUserId(String userName);

    int removeUser(UserDataEntity user);

    UserDataEntity getUserByUsername(String userName);

    List<UserDataEntity> getAllUsers();

    List<UserDataEntity> findByUsernameNot(String userName);

}
