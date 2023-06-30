package org.rangiffler.db.dao;

import org.rangiffler.db.entity.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface RangifflerUsersDAO {

  PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  int createUser(UserEntity user);

  String getUserId(String userName);

  int removeUser(UserEntity user);

  UserEntity getUserByUsername(String userName);

}
