package com.ugurhmz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;




public class WebScraping {

	public static void main(String[] args) throws InterruptedException, IOException {
	
		 System.setProperty("webdriver.chrome.driver", "C:/WebDriver/bin/chromedriver.exe");
		
		 //ilanOne();
		// ilanThree();
		 keepRecordsOfTenders();
	
		
	}
	
	
	
	
	// 20 sayfa, 1 sayfada 12  ilan, 8.row REKLAM
	public static void keepRecordsOfTenders() throws InterruptedException, FileNotFoundException {
		
		int howmanyPages=20;
		int howmanyRecords=13;
		List<String> mylist = new ArrayList<>();
		
		for(int j=1 ; j<=howmanyPages ; j++) {

			String  url = "https://www.ilan.gov.tr/ilan/kategori/9/ihale-duyurulari?txv=9&currentPage="+j;
			
			
			
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(url);
			Thread.sleep(3500);
			
			
			JavascriptExecutor  jsexec = (JavascriptExecutor) driver;
			
			WebElement ihaleKayitNo=null;
			WebElement nitelikTurMiktar = null;		
			WebElement isinYapilacakYeri = null;
			
			for(int i=1 ; i<=howmanyRecords; i++) {
				if(i==8) {
					continue;
				}
				
				
				Thread.sleep(5500);
				WebElement el = driver.findElement(By.cssSelector("body > igt-root > main > igt-ad-list > div > div > div.row > div.list-detail-content.col.col-12.col-md-12.col-lg-9.col-xl-9 > div.search-results-table-container.container.mb-4.ng-tns-c7-3.ng-star-inserted > div.search-results-content.row > div > igt-ad-single-list:nth-child("+i+") > ng-component > a > div"));
				el.click();
				Thread.sleep(5500);
				// ÝLAN 1
				String myscript = "return document.querySelector(\"#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(7) > div.list-desc\").textContent;";
				String ihaleTuru = (String) jsexec.executeScript(myscript);
			
				System.out.println("Tür : "+ihaleTuru);
				Thread.sleep(5500);
			
				try  {
					ihaleKayitNo = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(9) > div.list-desc"));
					nitelikTurMiktar = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(10) > div.list-desc"));
					isinYapilacakYeri = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(11) > div.list-desc"));
					Thread.sleep(4500);
					
				
				} catch(Exception ex) {

					if(isinYapilacakYeri == null) {
						
						isinYapilacakYeri = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(12) > div.list-title"));
						
						
					} else if(nitelikTurMiktar == null) {
						nitelikTurMiktar = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(11) > div.list-title"));
					} 
					
					else if(ihaleKayitNo == null) {
						ihaleKayitNo = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(10) > div.list-title"));
					}
				}
					 
				
				
				
				
				System.out.println("Ýhale Kayit No : "+ihaleKayitNo.getText());
				System.out.println("Nitelik Türü Ve Miktarý : "+nitelikTurMiktar.getText());
				System.out.println("Ýþin Yapýlacak Yeri : "+isinYapilacakYeri.getText());
				
				Collections.addAll(mylist,
						"Ýhale Türü : "+ihaleTuru,
						"Kayit No : "+ihaleKayitNo.getText(),
						"Nitelik Tür Miktar : "+nitelikTurMiktar.getText(),
						"Ýþin Yapýlacak Yeri : "+isinYapilacakYeri.getText());
				
				Thread.sleep(4500);
				driver.get(url);
				Thread.sleep(3500);
			}
			
			Thread.sleep(4500);
			System.out.println(mylist);
			
			// Print  .txt file
			Thread.sleep(4500);
			PrintWriter printWriter = new PrintWriter(new FileOutputStream("ugurihalekayit.txt"));
		    for (String s : mylist)
		    	printWriter.println(s);
		    
		    
		    printWriter.close();
			
		}
		
		
		
	
	}
	

}



/* MY EXAMPLE 
public static void ilanOne() throws InterruptedException {
	String  url = "https://www.ilan.gov.tr/ilan/848870/ihale-duyurulari/yapim-isi-ve-insaat-ihaleleri/elektronik-denetleme-sistemi-yapim-isi";

	
	
	WebDriver driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.get(url);
	Thread.sleep(3000);
	
	
	JavascriptExecutor  jsexec = (JavascriptExecutor) driver;
	//String myscript = "return document.querySelector(\"body > igt-root > main > igt-ad-single > div.row.header-ad-single.ng-star-inserted > div > div > igt-breadcrumb > ul > li:nth-child(3) > a\").click();";
	
	//jsexec.executeScript(myscript);
	
	// ÝLAN 1
	String myscript = "return document.querySelector(\"#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(7) > div.list-desc\").textContent;";
	String ihaleTuru = (String) jsexec.executeScript(myscript);
	System.out.println("Tür : "+ihaleTuru);
	
	WebElement ihaleKayitNo = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(9) > div.list-desc"));
	WebElement nitelikTurMiktar = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(10) > div.list-desc"));
	WebElement isinYapilacakYeri = driver.findElement(By.cssSelector("#print-section > div.row.single-ilan-content > div.col-12.col-lg-4 > div > div:nth-child(2) > ul > li:nth-child(11) > div.list-desc"));
	
	System.out.println("Ýhale Kayit No : "+ihaleKayitNo.getText());
	System.out.println("Nitelik Türü Ve Miktarý : "+nitelikTurMiktar.getText());
	System.out.println("Ýþin Yapýlacak Yeri : "+isinYapilacakYeri.getText());
}

*/





