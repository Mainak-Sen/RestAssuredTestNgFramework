package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.SpecBuilder;
import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojos.Error;
import com.spotify.oauth2.pojos.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.FakerUtils;
import io.qameta.allure.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Spotify Oauth2.0")
@Feature("Playlist Api tests")
public class PlaylistTest extends BaseTest{

    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @Issue("12323")
    @TmsLink("test-1345")
    @Story("Create a Playlist test")
    @Description("Test to create a playlist")
    @Test(description = "Should be able to create a playlist")
    public void createPlaylistTest() {

        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist);

        assertStatusCode(response.statusCode(), StatusCode.STATUS_CODE_201);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Story("Get a Playlist test")
    @Description("Test to get a playlist by playlistId")
    @Test(description = "Should be able to get the playlist by playlistId")
    public void getPlaylistTest() {

        Playlist requestPlaylist = playlistBuilder("My new updated playlist with latest songs ", "This is the latest one", false);

        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());

        assertStatusCode(response.statusCode(), StatusCode.STATUS_CODE_200);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Story("Update a Playlist test")
    @Description("Test to update an existing playlist")
    @Test(description = "Should be able to update an existing playlist")
    public void updatePlaylistTest() {

        Playlist requestUpdatePlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(), false);

        Response response = PlaylistApi.update(requestUpdatePlaylist, DataLoader.getInstance().getUpdatePlaylistId());

        assertStatusCode(response.statusCode(), StatusCode.STATUS_CODE_200);
    }

    //negative test
    @Story("Negative Test")
    @Description("Negative Test to check if error is thrown on trying to create playlist without name")
    @Test(description = "Should throw error when creating playlist without name")
    public void createPlaylistWithoutNameTest() {

        Playlist requestPlaylist = playlistBuilder("", FakerUtils.generateDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist);

        assertStatusCode(response.statusCode(), StatusCode.STATUS_CODE_400);
        assertErrorResponse(response.as(Error.class), StatusCode.STATUS_CODE_400);
    }

    //negative test
    @Story("Negative Test")
    @Description("Negative Test to check if error is thrown on trying to create playlist with invalid access token")
    @Test(description = "Should throw error when creating playlist with invalid token")
    public void createPlaylistWithInvalidTokenTest() {

        String invalidToken = "12345";
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist, invalidToken);

        assertStatusCode(response.statusCode(), StatusCode.STATUS_CODE_401);
        assertErrorResponse(response.as(Error.class), StatusCode.STATUS_CODE_401);
    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public) {
        return Playlist.builder().name(name).description(description)._public(_public).build();
    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }

    @Step
    public void assertErrorResponse(Error errorResponse, StatusCode statusCode) {
        assertThat(errorResponse.getError().getStatus(), equalTo(statusCode.code));
        assertThat(errorResponse.getError().getMessage(), equalTo(statusCode.msg));
    }
}
