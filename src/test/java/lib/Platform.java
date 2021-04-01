package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";

    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;
    private Platform() {}

    public static Platform getInstance( )
    {
        if (instance == null)
        {
            instance = new Platform();
        }
        return instance;
    }

    public RemoteWebDriver getDriver()throws Exception
    {
        URL URL = new URL(APPIUM_URL);
        if(isAndroid()){
            return new AndroidDriver(URL, getAndroidCapabilities());
        } else if(isIOS()){
            return new IOSDriver(URL, getiOSCapabilities());
        } else if(isMW()){
            return new ChromeDriver(getMWChromeOptions());//getMWChromeService(),
        } else {
            throw new Exception(("Cannot detect type of the Driver/ Platform value: " + getPlatformVar()));
        }
    }

    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    public boolean isMW()
    {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    private ChromeOptions getMWChromeOptions()
    {
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Chrome/18.0.1025.166 Mobile Safari/535.19");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=340,640");

        return chromeOptions;
    }

    private ChromeDriverService getMWChromeService() {
        int portForChrome = 43450;
        ChromeDriverService service =
                new ChromeDriverService.Builder().usingDriverExecutable(new File("C:/Users/Zhanna/IdeaProjects/mobile_mw/chromedriver.exe")).usingPort(portForChrome).build();
        return service;
    }

    private DesiredCapabilities getAndroidCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Nexus6_v8");//Pixel_v8
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app",
                "C:/Users/Zhanna/IdeaProjects/mobile2/apks/org.wikipedia_50345_apps.evozi.com.apk");

        return capabilities;
    }

    private DesiredCapabilities getiOSCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhoneSE");
        capabilities.setCapability("platformVersion", "11.3");
        capabilities.setCapability("app",
                "C:/Users/Zhanna/IdeaProjects/mobile2/apks/org.wikipedia.app");

        return capabilities;
    }

    private boolean isPlatform(String myPlatform)
    {
        return myPlatform.equals(getPlatformVar());
    }

    public String getPlatformVar()
    {
        String s = System.getenv("PLATFORM");
        return System.getenv("PLATFORM");
    }
}
