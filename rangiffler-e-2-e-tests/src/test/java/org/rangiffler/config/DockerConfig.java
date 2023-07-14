package org.rangiffler.config;

public class DockerConfig implements Config {

    //TODO: реализовать
    @Override
    public String getDBHost() {
        return "rangiffler-all-db";
    }

    @Override
    public String getDBLogin() {
        return "postgres";
    }

    @Override
    public String getDBPassword() {
        return "secret";
    }

    @Override
    public int getDBPort() {
        return 5432;
    }

    @Override
    public String getGeoUrl() {
        return "http://rangiffler-geo:8081";
    }

    @Override
    public String getPhotoUrl() {
        return "http://rangiffler-photo:8082";
    }

    @Override
    public String getUserdataUrl() {
        return "http://rangiffler-users:8089";
    }


    @Override
    public String getFrontUrl() {
        return "http://rangiffler-fronend:3000";
    }

    @Override
    public String getAuthUrl() {
        return "http://rangiffler-auth:9000";
    }

}
