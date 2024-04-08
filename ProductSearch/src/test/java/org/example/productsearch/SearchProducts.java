package org.example.productsearch;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.attribute;
import static org.assertj.core.api.Assert.*;

public class SearchProducts {
    public static void main(String[] args) {
        SearchProducts test = new SearchProducts();
        test.searchForProduct();
        test.verifyProductText();
        test.addLastItemToCart();
    }

    @Test
    public void searchForProduct() {
        WebDriver browser = new ChromeDriver();
        browser.get("https://www.webstaurantstore.com/");
        WebElement searchBar =  browser.findElement(By.xpath("//*[@id=\"searchval\"]"));
        searchBar.sendKeys("stainless work table");
        WebElement searchButton =  browser.findElement(By.xpath("//*[@id=\"searchForm\"]/div/button"));
        searchButton.click();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
        List<WebElement> listOfProducts = browser.findElements(By.xpath("//*[@id=\"product_listing\"]"));
        Assert.assertFalse(listOfProducts.isEmpty());
    }

    @Test
    public void verifyProductText() {
        WebDriver browser = new ChromeDriver();
        browser.get("https://www.webstaurantstore.com/");
        WebElement searchBar =  browser.findElement(By.xpath("//*[@id=\"searchval\"]"));
        searchBar.sendKeys("stainless work table");
        WebElement searchButton =  browser.findElement(By.xpath("//*[@id=\"searchForm\"]/div/button"));
        searchButton.click();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
        List<WebElement> paginationList = browser.findElements(By.xpath("//*[@id=\"paging\"]/nav/ul"));
        int lastPaginationElement = paginationList.size() - 1;
        for (int i = 0; i < lastPaginationElement; i++) {
            List<WebElement> searchResultList = browser.findElements(By.xpath("//*[@id=\"ProductBoxContainer\"]/div/a/span"));
            for (WebElement searchResult : searchResultList) {
                Assert.assertTrue(searchResult.getText().contains("table"));
            }
            paginationList.get(lastPaginationElement).click();
            browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
        }
    }

    @Test
    public void addLastItemToCart() {
        WebDriver browser = new ChromeDriver();
        browser.get("https://www.webstaurantstore.com/");
        WebElement searchBar =  browser.findElement(By.xpath("//*[@id=\"searchval\"]"));
        searchBar.sendKeys("stainless work table");
        WebElement searchButton =  browser.findElement(By.xpath("//*[@id=\"searchForm\"]/div/button"));
        searchButton.click();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
        browser.findElement(By.xpath("//*[@id=\"paging\"]/nav/ul/li[7]")).click();
        browser.findElement(By.xpath("//*[@id=\"ProductBoxContainer\"]/div[4]/form/div/div/input[2]")).click();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
        browser.findElement(By.xpath("//*[@id=\"watnotif-wrapper\"]/div/p/div[2]/div[2]/a[1]")).click();
        WebElement itemElement = browser.findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/div[2]/ul/li[2]/div/div[2" +
                "]/span"));
        Assert.assertTrue(itemElement.getAttribute("innerText").contains("Table"));
        browser.findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/div[2]/ul/li[2]/div/div[3]/div/button[1]")).click();
        WebElement emptyCartElement = browser.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div/div[2]/p[1]"));
        Assert.assertTrue(emptyCartElement.getAttribute("innerText").contains("Your cart is empty."));
        browser.quit();
    }
}
