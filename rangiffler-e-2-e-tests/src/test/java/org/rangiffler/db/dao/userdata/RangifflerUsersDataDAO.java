package org.rangiffler.db.dao.userdata;

import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface RangifflerUsersDataDAO {

    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    int createUser(UserDataEntity user);

    String getUserId(String userName);

    int removeUser(UserDataEntity user);

    UserDataEntity getUserByUsername(String userName);

}
