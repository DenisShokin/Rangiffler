package org.rangiffler.config;

import com.codeborne.selenide.Configuration;

public class DockerConfig implements Config {

    static final DockerConfig INSTANCE = new DockerConfig();

    static {
        Configuration.browser = "chrome";
        Configuration.browserVersion = "110.0";
        Configuration.remote = "http://selenoid:4444/wd/hub";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }

    private DockerConfig() {
    }

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
        return "http://geo.rangiffler.dc:8081";
    }

    @Override
    public String getPhotoUrl() {
        return "http://photo.rangiffler.dc:8082";
    }

    @Override
    public String getUserdataUrl() {
        return "http://users.rangiffler.dc:8089";
    }

    @Override
    public String getFrontUrl() {
        return "http://client.rangiffler.dc";
    }

    @Override
    public String getAuthUrl() {
        return "http://auth.rangiffler.dc:9000";
    }

}
