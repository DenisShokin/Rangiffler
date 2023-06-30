package org.rangiffler.config;

public class LocalConfig implements Config {
  //TODO: реализовать

  @Override
  public String getDBHost() {
    return "localhost";
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
    return "http://127.0.0.1:8081";
  }

  @Override
  public String getPhotoUrl() {
    return "http://127.0.0.1:8082";
  }

  @Override
  public String getUsersUrl() {
    return "http://127.0.0.1:8089";
  }

  @Override
  public String getFrontUrl() {
    return "http://127.0.0.1:3001";
  }

  @Override
  public String getAuthUrl() {
    return "http://127.0.0.1:9000";
  }

}
