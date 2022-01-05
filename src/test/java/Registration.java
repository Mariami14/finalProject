import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Registration extends Projecttests{

    public static Response register (String url, Object user) {
        RequestSpecification specification = given().header("Content-type", "application/json").body(user).when();
        Response response = specification.post(url);
        return response.then().extract().response();
    }

}
