package org.rangiffler.api;

import org.rangiffler.model.UserJson;

import java.io.IOException;

public class UserdataRestClient extends BaseRestClient {

    public UserdataRestClient() {
        super(CFG.getUserdataUrl());
    }

    private final UserdataService userdataService = retrofit.create(UserdataService.class);

    public UserJson currentUser(String username) {
        try {
            return userdataService.currentUser(username).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
