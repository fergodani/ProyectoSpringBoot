package com.capgemini.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//Anotacion para que sigan el orden establecido los tests
public class SeleniumTest {

	private ChromeDriver driver;
	private File logLocation;

	@AfterEach
	public void quit() {
		driver.findElement(By.id("logout")).click();
		if (logLocation != null && logLocation.exists()) {
			logLocation.delete();
		}
		System.clearProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY);
		System.clearProperty(ChromeDriverService.CHROME_DRIVER_LOG_LEVEL_PROPERTY);

		driver.quit();
	}

	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/login");
	}

	@Test
	@Order(1)
	public void registerlogin() {
		driver.manage().window().fullscreen();
		driver.findElement(By.linkText("Registrarse")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("kike");
		driver.findElement(By.id("name")).sendKeys("Quique");
		driver.findElement(By.id("lastName")).sendKeys("Rodriguez");
		driver.findElement(By.id("telefono")).sendKeys("600100200");
		driver.findElement(By.id("direccion")).sendKeys("Calle Quique");
		driver.findElement(By.id("password")).sendKeys("1234");
		driver.findElement(By.id("passwordConfirm")).sendKeys("1234");
		driver.findElement(By.id("submit")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("kike");
		driver.findElement(By.id("password")).sendKeys("1234");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		
		//Comprobacion
		assertEquals(driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/ul/li[2]/a")).getText(), "Mis préstamos");
	}
	
	@Test
	@Order(2)
	  public void anadirAutor() {

		driver.manage().window().fullscreen();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).sendKeys("admin");
	    driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
	    driver.findElement(By.linkText("Añadir autor")).click();
	    driver.findElement(By.id("nombre")).click();
	    driver.findElement(By.id("nombre")).sendKeys("Luis");
	    driver.findElement(By.id("nacionalidad")).click();
	    driver.findElement(By.id("nacionalidad")).sendKeys("Española");
	    driver.findElement(By.id("fechaNacimiento")).click();
	    driver.findElement(By.id("fechaNacimiento")).sendKeys("10-11-2023");
	    driver.findElement(By.cssSelector(".btn-info")).click();
		
	    //Comprobacion
	    String Stringesperado = "Luis";
	    String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
	    boolean stringPresente = Stringdevuelto.contains(Stringesperado);
	    assertEquals(stringPresente, true);
	    
	  }
	
	@Test
	@Order(3)
	  public void anadirLibro() {
	    
	    driver.manage().window().fullscreen();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).sendKeys("admin");
	    driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
	    driver.findElement(By.linkText("Añadir libro")).click();
	    driver.findElement(By.id("titulo")).click();
	    driver.findElement(By.id("titulo")).sendKeys("El señor de los anillos");
	    driver.findElement(By.id("anyo")).click();
	    driver.findElement(By.id("anyo")).sendKeys("2000");
	    driver.findElement(By.id("tipoLibro")).click();
	    {
	      WebElement dropdown = driver.findElement(By.id("tipoLibro"));
	      dropdown.findElement(By.xpath("//option[. = 'Teatro']")).click();
	    }
	    driver.findElement(By.cssSelector(".btn-info")).click();
	    
	    //Comprobacion
	    String Stringesperado = "El señor de los anillos";
	    String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
	    boolean stringPresente = Stringdevuelto.contains(Stringesperado);
	    assertEquals(stringPresente, true);
	  }
	

	 @Test
	 @Order(4)
	  public void editLector() {
	    
	    driver.manage().window().fullscreen();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).sendKeys("admin");
	    driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
	    driver.findElement(By.linkText("Lectores")).click();
	    driver.findElement(By.id("Quique")).click();
	    driver.findElement(By.cssSelector("html")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("Fernando");
	    driver.findElement(By.id("telefono")).click();
	    driver.findElement(By.id("telefono")).clear();
	    driver.findElement(By.id("telefono")).sendKeys("600200200");
	    driver.findElement(By.id("direccion")).clear();
	    driver.findElement(By.id("direccion")).click();
	    driver.findElement(By.id("direccion")).sendKeys("Calle Fernando");
	    driver.findElement(By.cssSelector(".btn-info")).click();
	    driver.findElement(By.linkText("Lectores")).click();
	    
	    driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/ul/li[5]/a")).click();
	    
	    //Comprobacion
	    String Stringesperado = "Fernando";
	    String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
	    boolean stringPresente = Stringdevuelto.contains(Stringesperado);
	    assertEquals(stringPresente, true);
	    
	  }
	 
	 @Test
	 @Order(5)
	  public void editLibro() {
		driver.manage().window().fullscreen();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).sendKeys("admin");
	    driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
	    driver.findElement(By.linkText("Libros")).click();
	    driver.findElement(By.linkText("Editar")).click();
	    driver.findElement(By.id("titulo")).click();
	    driver.findElement(By.id("titulo")).clear();
	    driver.findElement(By.id("titulo")).sendKeys("El señor de los anillos 2");
	    driver.findElement(By.id("tipoLibro")).click();
	    {
	      WebElement dropdown = driver.findElement(By.id("tipoLibro"));
	      dropdown.findElement(By.xpath("//option[. = 'Novela']")).click();
	    }
	    driver.findElement(By.id("anyo")).click();
	    driver.findElement(By.id("anyo")).clear();
	    driver.findElement(By.id("anyo")).sendKeys("1998");
	    driver.findElement(By.cssSelector(".btn")).click();
	    
	    //Comprobacion
	    String Stringesperado = "El señor de los anillos 2";
	    String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
	    boolean stringPresente = Stringdevuelto.contains(Stringesperado);
	    assertEquals(stringPresente, true);
	  }
	 
	  @Test
	  @Order(6)
	  public void editAutor() {
		driver.manage().window().maximize();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).sendKeys("admin");
	    driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
	    driver.findElement(By.linkText("Autores")).click();
	    driver.findElement(By.id("Luis")).click();
	    driver.findElement(By.cssSelector("html")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("Marco Aurelio");
	    driver.findElement(By.id("nacionalidad")).clear();
	    driver.findElement(By.id("nacionalidad")).sendKeys("Macedonio");
	    driver.findElement(By.id("fechaNacimiento")).clear();
	    driver.findElement(By.id("fechaNacimiento")).click();
	    driver.findElement(By.id("fechaNacimiento")).sendKeys("09-07-1234");
	    driver.findElement(By.cssSelector(".btn-info")).click();
	    
	  //Comprobacion
	    String Stringesperado = "Marco Aurelio";
	    String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
	    boolean stringPresente = Stringdevuelto.contains(Stringesperado);
	    assertEquals(stringPresente, true);
	    
	  }
	  
	  
	 
	

}
