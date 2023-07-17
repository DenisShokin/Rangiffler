package org.rangiffler.config;

public interface Config {

    static Config getConfig() {
        if ("docker".equals(System.getProperty("test.env"))) {
            return DockerConfig.INSTANCE;
        } else {
            return LocalConfig.INSTANCE;
        }
    }

    String getDBHost();

    String getDBLogin();

    String getDBPassword();

    int getDBPort();

    String getFrontUrl();

    String getAuthUrl();

    String getGeoUrl();

    String getPhotoUrl();

    String getUserdataUrl();
}
