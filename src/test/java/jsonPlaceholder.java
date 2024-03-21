import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class jsonPlaceholder {
    @BeforeClass
    public void website() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
    @Test
    public void listAllPosts(){
        Response response=given()
                .when()
                .get("/posts")
                .then()
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(),200);
    }
    @Test
    public void getUserOne() {
        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get("/posts?userId={userId}")
                .then()
                .extract()
                .response();
        SoftAssert soft=new SoftAssert();
        soft.assertEquals(response.getStatusCode(), 200);
        soft.assertTrue(response.getBody().asString().contains("title"));
        soft.assertAll();
    }
    @Test
    public void adddPost() {
        String requestBody = "{\"title\":\"Vodafone\",\"body\":\"Vodafone Test\",\"userId\":1}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .extract()
                .response();
        SoftAssert soft=new SoftAssert();
        soft.assertEquals(response.getStatusCode(), 201);
        soft.assertTrue(response.getBody().asString().contains("Vodafone"));
        soft.assertAll();
    }
    @Test
    public void deletePost() {
        Response response = given()
                .pathParam("postId", 50)
                .when()
                .delete("/posts/{postId}")
                .then()
                .extract()
                .response();
        SoftAssert soft=new SoftAssert();
        soft.assertEquals(response.getStatusCode(), 200);
        //soft.assertTrue(response.getBody().asString().isEmpty());
        soft.assertAll();
    }
}
