package me.zhuravlev.elibraryextractor.helper;

import org.jsoup.Jsoup;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class SeleniumHelper {

    public WebDriver getWebdriver(){
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
        var options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1400,800");

        options.setPageLoadTimeout(Duration.ofSeconds(90));
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));

        var driver = new ChromeDriver(options);
        return driver;
    }

    public WebElement fluentWait(final WebDriver driver, final By locator) {
        var wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        return wait.until(x -> x.findElement(locator));
    }

    // NONne Null
    public String ownText(WebElement element) {
        var result = Jsoup.parse(element.getAttribute("outerHTML"))
                .selectFirst(element.getTagName()).ownText();
        return result;
    }
    public String previousSibling(WebDriver driver, WebElement element) {
        var result = (String)((JavascriptExecutor)driver).executeScript(
                "var result = arguments[0].previousSibling; return result == null? \"\": result.textContent", element);
        return result;
    }
}
