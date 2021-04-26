package TestCase;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.log.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
public class ConsoleData {
    WebDriver driver;
    boolean flag=false;
    @BeforeTest
    public void setDriver()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    @Test
    public void readConsoleLogs()
    {
        DevTools devTool = ((ChromeDriver)driver).getDevTools();
        devTool.createSession();
        devTool.send(Log.enable());
        List<String> list=new ArrayList<String>();
        devTool.addListener(Log.entryAdded(), entry -> list.add(entry.getText()));
        driver.get("https://www.thehindu.com/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        for(Object log:list)
        {
            System.out.println("Logs are:- "+log);
            if(log.toString().contains("elements with non-unique id"))
            {
                flag=true;
                break;
            }
        }
        Assert.assertTrue(flag, "Console logs appeared.");
    }
}