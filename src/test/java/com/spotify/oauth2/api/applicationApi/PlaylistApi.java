package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.api.Routes;
import com.spotify.oauth2.api.SpecBuilder;
import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojos.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Routes.PLAYLISTS;
import static com.spotify.oauth2.api.Routes.USERS;
import static io.restassured.RestAssured.given;

public class PlaylistApi {

    @Step
    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(TokenManager.getToken(), USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, requestPlaylist);
    }

    //overloaded post method to pass invalid token to test error scenarios
    @Step
    public static Response post(Playlist requestPlaylist, String token) {
        return RestResource.post(token, USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, requestPlaylist);
    }

    @Step
    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, TokenManager.getToken());
    }

    @Step
    public static Response update(Playlist requestUpdatePlaylist, String playlistId) {
        return RestResource.update(requestUpdatePlaylist, PLAYLISTS + "/" + playlistId, TokenManager.getToken());
    }
}
