import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class Projecttests {


    @Test
    public void test1() {
        //credentials
        JSONObject credentials = new JSONObject();
        credentials.put("userName", "Mariesky");
        credentials.put("password", "Marie#14");

        //A
        WebDriverManager.chromedriver().setup();
        ChromeOptions chrome = new ChromeOptions();
        WebDriver driver = new ChromeDriver(chrome);
        Registration.register("https://bookstore.toolsqa.com/Account/v1/User", credentials.toJSONString());//can't pass username

        //login
        driver.get("https://demoqa.com/login");
        driver.manage().window().maximize();
        WebElement UserN = driver.findElement(By.id("userName"));
        UserN.sendKeys("Mariesky");
        WebElement Password = driver.findElement(By.id("password"));
        Password.sendKeys("Marie#14");
        WebElement Login = driver.findElement(By.id("login"));
        Login.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //deleteUser
        js.executeScript("window.scrollBy(0,300)", "");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement delete = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div/div[3]/div[2]/button"));
        delete.click();
        WebElement deleteB = driver.findElement(By.id("closeSmallModal-ok"));
        deleteB.click();

        WebDriverWait wait = new WebDriverWait(driver, 300);
        if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
            Alert alert = driver.switchTo().alert();
            Assert.assertEquals(alert.getText(), "User Deleted.");
            alert.accept();
        }

        //login
        UserN = driver.findElement(By.id("userName"));
        UserN.sendKeys("Mariesky");
        Password = driver.findElement(By.id("password"));
        Password.sendKeys("Marie#14");
        Login = driver.findElement(By.id("login"));
        Login.click();

    }

    @Test
    public void test2() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chrome = new ChromeOptions();
        WebDriver driver = new ChromeDriver(chrome);
        driver.get("https://demoqa.com/login");

        String Book = "O'Reilly Media";
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/books");
        WebElement searchBox = driver.findElement(By.xpath("//div[@class='rt-td' and contains(text(),'Reilly Media')]"));

        searchBox.sendKeys("O'Reilly Media");

        Response response = given().when().get("https://bookstore.toolsqa.com/BookStore/v1/Books");

          List<WebElement> Elements = driver.findElements(By.xpath("//div[@class='rt-td' and contains(text(),'Reilly Media')]"));
        var str = response.getBody().asString();


        assert Elements.stream().count() == StringUtils.countMatches(str, Book);

        System.out.print(StringUtils.countMatches(str, Book));
    }


}

