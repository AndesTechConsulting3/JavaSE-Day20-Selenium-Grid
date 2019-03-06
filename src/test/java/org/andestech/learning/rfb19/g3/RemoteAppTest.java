package org.andestech.learning.rfb19.g3;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.assertTrue;

public class RemoteAppTest
{
    private WebDriver wd = null;
    private DesiredCapabilities caps = null;

    @BeforeClass
    public void initData(){
    System.setProperty("webdriver.chrome.driver",
            "E:\\drivers\\selenium\\chromedriver.exe");
    System.out.println("+++ Class: " + this);

    caps = new DesiredCapabilities();
    ChromeOptions opt = new ChromeOptions();
    opt.setExperimentalOption("useAutomationExtension",false);

   //opt.merge(caps);


   // caps.setBrowserName("firefox");




    }

    @Test
    public void testCaseChrome01() throws MalformedURLException,InterruptedException
    {
        caps.setBrowserName("chrome");
        caps.setPlatform(Platform.LINUX);

        wd = new RemoteWebDriver(new URL("http://172.20.20.20:4444/wd/hub"), caps);
        wd.get("http://google.com");
        WebElement element = wd.findElement(By.name("q"));
        element.sendKeys("Saturn");
        element.submit();

        Thread.sleep(2_000);

        File f1 = ((RemoteWebDriver)wd).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(new FileInputStream(f1), Paths.get("e:\\screens\\" +
                    "remote_screen_" + System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        ((RemoteWebDriver)wd).getCapabilities().asMap().forEach(
                (k,v) -> System.out.println(k + " -> " + v)
        );

       // Thread.sleep(2_000);
        assertTrue( true );
    }


    @AfterClass
    public void tearDown()
    {
      if(wd != null) wd.quit();
      System.out.println("--- Class: " + this);
    }

}
