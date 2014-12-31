test-mvc
========

Overview
--------
Testing with Spring MVC Test framework.
The goal of this project is to demonstrate:
* how to write unit tests for controllers utilizing the DispatcherServlet to process requests thus approximating full integration tests without requiring a running Servlet container;
* how to write integration tests that test integration of all application layers without requiring a running Servlet container.

The structure of the demo application
-------------------------------------
* Spring MVC 4 + Spring Core 4 + Hibernate 4 configured using Java Config;
* REST controllers;
* error handling;
* unit and integration tests with MockMvc, Mockito and DbUnit

Inspiration
-----------
* Spring MVC Test Tutorial - http://www.petrikainulainen.net/spring-mvc-test-tutorial/
* Exception Handling in Spring MVC - http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc

Next steps
----------
If you are interested in testing controllers that produce HTML, you can have a look at Spring MVC Test Tutorial (http://www.petrikainulainen.net/spring-mvc-test-tutorial) and 
learn how to test view and model returned by controllers.
However if you want to test a rendered HTML (still without requiring a running Servlet container), have a look at these blog posts:
* Spring MVC Test with HtmlUnit - https://spring.io/blog/2014/03/25/spring-mvc-test-with-htmlunit
* Spring MVC Test with WebDriver - http://spring.io/blog/2014/03/26/spring-mvc-test-with-webdriver
* Spring MVC Test with Geb - https://spring.io/blog/2014/04/15/spring-mvc-test-with-geb
