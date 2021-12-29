package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/* This class renews the token at the onset of test execution
        and checks for token expiry in the subsequent requests during the execution and renews the same if required */


public class TokenManager {

    private static String accessToken;
    private static Instant expiryTime;

    public synchronized static String getToken() {

        try {
            if (accessToken == null || Instant.now().isAfter(expiryTime)) {
                System.out.println("Renewing token.....");
                Response response = renewToken();
                int expiryDuration = response.path("expires_in");
                accessToken = response.path("access_token");
                expiryTime = Instant.now().plusSeconds(expiryDuration - 300); //adding 300 secs of buffer
            } else {
                System.out.println("Existing token is good to use!!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Aborting Execution!!! Failed to get token "+e.getMessage());
        }

        return accessToken;
    }


    private static Response renewToken() {

        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());

        try {
            return RestResource.postAccount(formParams);
        } catch (Exception e) {
            throw new RuntimeException("Abort!! Failed to renew token...   " + e.getMessage());
        }

    }
}
