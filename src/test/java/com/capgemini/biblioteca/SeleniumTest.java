package com.capgemini.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Anotacion para que sigan el orden establecido los tests
public class SeleniumTest {

	private static ChromeDriver driver;
	private static File logLocation;


	
	@AfterAll
	public static void quit() {
//		driver.findElement(By.id("logout")).click();
		if (logLocation != null && logLocation.exists()) {
			logLocation.delete();
		}
		System.clearProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY);
		System.clearProperty(ChromeDriverService.CHROME_DRIVER_LOG_LEVEL_PROPERTY);

		driver.quit();
	}

	@BeforeAll
	public static void setUpAll() {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080");
		driver.manage().window().maximize();
		driver.findElement(By.linkText("Registrarse")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("name")).sendKeys("Admin");
		driver.findElement(By.id("lastName")).sendKeys("Administrador");
		driver.findElement(By.id("telefono")).sendKeys("600100200");
		driver.findElement(By.id("direccion")).sendKeys("Calle Quique");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("passwordConfirm")).sendKeys("admin");
		driver.findElement(By.id("registro")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	}
	
	@Test
	@Order(1)
	public void registerlogin() {
		driver.findElement(By.linkText("Registrarse")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("kike");
		driver.findElement(By.id("name")).sendKeys("Quique");
		driver.findElement(By.id("lastName")).sendKeys("Rodriguez");
		driver.findElement(By.id("telefono")).sendKeys("600100200");
		driver.findElement(By.id("direccion")).sendKeys("Calle Quique");
		driver.findElement(By.id("password")).sendKeys("1234");
		driver.findElement(By.id("passwordConfirm")).sendKeys("1234");
		driver.findElement(By.id("registro")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("kike");
		driver.findElement(By.id("password")).sendKeys("1234");
		driver.findElement(By.id("login")).click();

		// Comprobacion
		String Stringesperado = "Quique";
		String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
		boolean stringPresente = Stringdevuelto.contains(Stringesperado);
		assertEquals(stringPresente, true);
		
		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	}

	@Test
	@Order(2)
	public void anadirAutor() {

		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.id("navbarDropdownAut")).click();
		driver.findElement(By.xpath("//*[@id=\"marks-menu\"]/div/a[1]")).click();
		driver.findElement(By.id("nombre")).click();
		driver.findElement(By.id("nombre")).sendKeys("Luis");
		driver.findElement(By.id("nacionalidad")).click();
		driver.findElement(By.id("nacionalidad")).sendKeys("Española");
		driver.findElement(By.id("fechaNacimiento")).click();
		driver.findElement(By.id("fechaNacimiento")).sendKeys("10-11-2023");
		driver.findElement(By.cssSelector(".btn-info")).click();

		// Comprobacion
		String Stringesperado = "Luis";
		String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
		boolean stringPresente = Stringdevuelto.contains(Stringesperado);
		assertEquals(stringPresente, true);
		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	}

	@Test
	@Order(3)
	public void anadirLibro() {
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.id("navbarDropdownLib")).click();
		driver.findElement(By.linkText("Agregar libro")).click();
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

		// Comprobacion
		String Stringesperado = "El señor de los anillos";
		String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
		boolean stringPresente = Stringdevuelto.contains(Stringesperado);
		assertEquals(stringPresente, true);
		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	}

	@Test
	@Order(4)
	public void editLector() {
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("login")).click();
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

		// Comprobacion
		String Stringesperado = "Fernando";
		String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
		boolean stringPresente = Stringdevuelto.contains(Stringesperado);
		assertEquals(stringPresente, true);
		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	}

	@Test
	@Order(5)
	public void editLibro() {
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.id("navbarDropdownLib")).click();
		driver.findElement(By.linkText("Ver libros")).click();
		driver.findElement(By.id("El señor de los anillos")).click();
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).clear();
		driver.findElement(By.id("titulo")).sendKeys("El señor de los anillos 3");
		driver.findElement(By.id("tipoLibro")).click();
		{
			WebElement dropdown = driver.findElement(By.id("tipoLibro"));
			dropdown.findElement(By.xpath("//option[. = 'Novela']")).click();
		}
		driver.findElement(By.id("anyo")).click();
		driver.findElement(By.id("anyo")).clear();
		driver.findElement(By.id("anyo")).sendKeys("1998");
		driver.findElement(By.id("guardar")).click();

		// Comprobacion
		String Stringesperado = "El señor de los anillos 3";
		String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
		boolean stringPresente = Stringdevuelto.contains(Stringesperado);
		assertEquals(stringPresente, true);
		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	}

	@Test
	@Order(6)
	public void editAutor() {
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.id("navbarDropdownAut")).click();
		driver.findElement(By.linkText("Ver autores")).click();
		driver.findElement(By.id("Luis")).click();
		driver.findElement(By.cssSelector("html")).click();
		driver.findElement(By.id("nombre")).clear();
		driver.findElement(By.id("nombre")).sendKeys("Cervantes");
		driver.findElement(By.id("nacionalidad")).clear();
		driver.findElement(By.id("nacionalidad")).sendKeys("Macedonio");
		driver.findElement(By.id("fechaNacimiento")).clear();
		driver.findElement(By.id("fechaNacimiento")).click();
		driver.findElement(By.id("fechaNacimiento")).sendKeys("09-07-1234");
		driver.findElement(By.cssSelector(".btn-info")).click();

		// Comprobacion
		String Stringesperado = "Cervantes";
		String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
		boolean stringPresente = Stringdevuelto.contains(Stringesperado);
		assertEquals(stringPresente, true);
		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	}
	
	@Test
	@Order(7)
	  public void crearPrestamo() {
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).sendKeys("admin");
	    driver.findElement(By.id("login")).click();
	    driver.findElement(By.linkText("Detalles")).click();
	    driver.findElement(By.cssSelector(".d-flex > .btn")).click();
	    driver.findElement(By.cssSelector(".d-flex > .btn")).click();
	    {
	      WebElement element = driver.findElement(By.cssSelector(".d-flex > .btn"));
	      Actions builder = new Actions(driver);
	      builder.doubleClick(element).perform();
	    }
	    driver.findElement(By.cssSelector(".d-flex > .btn")).click();
	    driver.findElement(By.id("logout")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("kike");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).sendKeys("1234");
	    driver.findElement(By.id("login")).click();
	    driver.findElement(By.linkText("Crear préstamo")).click();
	    driver.findElement(By.id("fecha-fin")).click();
	    driver.findElement(By.id("fecha-fin")).sendKeys("11-30-2023");
	    driver.findElement(By.id("crear")).click();
	    driver.findElement(By.linkText("Devolver")).click();
	    
	 // Comprobacion
	 		String Stringesperado = "DEVUELTO";
	 		String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
	 		boolean stringPresente = Stringdevuelto.contains(Stringesperado);
	 		assertEquals(stringPresente, true);
	 		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	    
	  }
	
	@Test
	@Order(8)
	  public void crearPrestamoError() {
		
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).sendKeys("admin");
	    driver.findElement(By.id("login")).click();
	    driver.findElement(By.linkText("Detalles")).click();
	    driver.findElement(By.cssSelector(".d-flex > .btn")).click();
	    driver.findElement(By.cssSelector(".d-flex > .btn")).click();
	    {
	      WebElement element = driver.findElement(By.cssSelector(".d-flex > .btn"));
	      Actions builder = new Actions(driver);
	      builder.doubleClick(element).perform();
	    }
	    driver.findElement(By.cssSelector(".d-flex > .btn")).click();
	    driver.findElement(By.id("logout")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("kike");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).sendKeys("1234");
	    driver.findElement(By.id("login")).click();
	    driver.findElement(By.linkText("Crear préstamo")).click();
	    driver.findElement(By.id("fecha-fin")).click();
	    driver.findElement(By.id("fecha-fin")).sendKeys("11-30-2023");
	    driver.findElement(By.id("crear")).click();
	    driver.findElement(By.linkText("Volver")).click();
	    driver.findElement(By.linkText("Crear préstamo")).click();
	    driver.findElement(By.id("fecha-fin")).click();
	    driver.findElement(By.id("fecha-fin")).sendKeys("11-29-2023");
	    driver.findElement(By.id("crear")).click();
	    driver.findElement(By.linkText("Volver")).click();
	    driver.findElement(By.linkText("Crear préstamo")).click();
	    driver.findElement(By.id("fecha-fin")).click();
	    driver.findElement(By.id("fecha-fin")).sendKeys("11-18-2023");
	    driver.findElement(By.id("crear")).click();
	    driver.findElement(By.linkText("Volver")).click();
	    driver.findElement(By.linkText("Crear préstamo")).click();
	    driver.findElement(By.id("fecha-fin")).click();
	    driver.findElement(By.id("fecha-fin")).sendKeys("11-19-2023");
	    driver.findElement(By.id("crear")).click();
	    
	    
	 // Comprobacion
	 		String Stringesperado = "El lector Fernando no puede sacar más préstamos";
	 		String Stringdevuelto = driver.findElement(By.tagName("body")).getText();
	 		boolean stringPresente = Stringdevuelto.contains(Stringesperado);
	 		assertEquals(stringPresente, true);
	 		driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
	    
	  }
	

}
