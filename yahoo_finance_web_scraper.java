package yahoo_webscraper;
//importing necessary libraries
//Selenium
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
//TimeUnit
import java.util.concurrent.TimeUnit;
//Console Scanner
import java.util.Scanner;

/*The following program is a simple Web Scraper, which allows the user to make requests about a specific security.
  The program can find the current price of the specified security as well as download the daily data by scraping
  Yahoo Finance (https://finance.yahoo.com). It uses the Chrome Driver (https://chromedriver.chromium.org/) as well
	as Selenium's libraries for Web Scraping (https://www.selenium.dev/downloads/). */

public class yahoo_finance_web_scraper {

	public static void main(String[] args) {
		//preparing the ChromeDriver
		String path = "/path/to/ChromeDriver";
		System.setProperty("webdriver.chrome.driver", path);

		//Asking user to provide ticker for stock price request
		System.out.println("For which security would you like to get the current price? Please type in the full name:");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		System.out.println("Getting the stock price for "+name+"...");

		//Calling the getPrice function to get price from Yahoo Finance
		System.out.println("The current price of "+name+" is: "+getPriceData(name)+" USD");

		//Downloading stock data
		System.out.println("Please type 'Yes' if you would like to donwload the data.");
		Scanner in_download = new Scanner(System.in);
		String download = in_download.next();
		if (download != "") {
			downloadData(name);
		}
	}


	//Creating a method which takes the name of the security and returns the current price
	public static float getPriceData(String name) {
		//Creating a WebDriver ChromeDriver object
		WebDriver driver = new ChromeDriver();
		//opening Yahoo Finance
		driver.get("https://finance.yahoo.com/");
		//Waiting a few seconds for the page to load
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		//Accepting the cookies
		driver.findElement(By.xpath("//*[@id=\"consent-page\"]/div/div/div/div[3]/div/form/button[1]")).click();
		//Typing in the ticker requested
		driver.findElement(By.xpath("//*[@id=\"yfin-usr-qry\"]")).sendKeys(name);
		//Searching for the security price
		driver.findElement(By.xpath("//*[@id=\"header-desktop-search-button\"]")).click();

		//Getting the stock price
		String response = driver.findElement(By.xpath("//*[@id=\"quote-header-info\"]/div[3]/div/div/span[1]")).getText();
		//Convert price to a float variable
		Float price =Float.parseFloat(response);
		return price;
	}


	//Creating a method which takes the name of the security and downloads the data on that security
	public static void downloadData(String name) {
		//Creating a WebDriver ChromeDriver object
		WebDriver driver = new ChromeDriver();
		//Navigating to the stock page
		driver.get("https://finance.yahoo.com/");
		driver.findElement(By.xpath("//*[@id=\"consent-page\"]/div/div/div/div[3]/div/form/button[1]")).click();
		driver.findElement(By.xpath("//*[@id=\"yfin-usr-qry\"]")).sendKeys(name);
		driver.findElement(By.xpath("//*[@id=\"header-desktop-search-button\"]")).click();
		//Waiting one second for the page to load
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		//Navigating to the download tab
		driver.findElement(By.xpath("//*[@id=\"quote-nav\"]/ul/li[5]/a/span")).click();
		//Waiting one second for the page to load
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		//Downloading the data
		driver.findElement(By.xpath("//*[@id=\"Col1-1-HistoricalDataTable-Proxy\"]/section/div[1]/div[2]/span[2]")).click();
	}


}
