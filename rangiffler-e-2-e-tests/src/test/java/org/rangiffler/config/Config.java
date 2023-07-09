package org.rangiffler.config;

public interface Config {

    static Config getConfig() {
        if ("docker".equals(System.getProperty("env"))) {
            return new DockerConfig();
        }
        return new LocalConfig();
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
