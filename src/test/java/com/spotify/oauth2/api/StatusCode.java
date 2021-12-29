package com.spotify.oauth2.api;

public enum StatusCode {

    STATUS_CODE_200(200, ""),
    STATUS_CODE_201(201, ""),
    STATUS_CODE_400(400, "Missing required field: name"),
    STATUS_CODE_401(401, "Invalid access token");

    public int code;
    public String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
