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
    public String getGeoUrl() {
        return "rangiffler-spend";
    }

    @Override
    public String getPhotoUrl() {
        return null;
    }

    @Override
    public String getUserdataUrl() {
        return null;
    }

    @Override
    public int getDBPort() {
        return 5432;
    }

    @Override
    public String getFrontUrl() {
        return "http://rangiffler-fronend:3000/";
    }

    @Override
    public String getAuthUrl() {
        return "http://rangiffler-auth:9000/";
    }

}
