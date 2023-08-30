/*
 * package com.adobe.aem.ecart.core.servlets;
 * 
 * import java.io.IOException;
 * 
 * import javax.servlet.Servlet; import javax.servlet.ServletException;
 * 
 * import org.apache.sling.api.SlingHttpServletRequest; import
 * org.apache.sling.api.SlingHttpServletResponse; import
 * org.apache.sling.api.servlets.HttpConstants; import
 * org.apache.sling.api.servlets.SlingSafeMethodsServlet; import
 * org.apache.sling.servlets.annotations.SlingServletResourceTypes;
 * 
 * import org.osgi.service.component.annotations.Component;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * //@Component(service = Servlet.class //, property =
 * "sling.servlet.resourceTypes=e-cart-app/components/article-details" //)
 * 
 * @SlingServletResourceTypes( resourceTypes =
 * "e-cart-app/components/article-details", methods = HttpConstants.METHOD_GET ,
 * selectors = "test", extensions = "html")
 * 
 * public class ArticleDetailsServlet extends SlingSafeMethodsServlet{
 * 
 * 
 * @Override protected void doGet( SlingHttpServletRequest request,
 * SlingHttpServletResponse response) throws ServletException, IOException {
 * Employee emp = new Employee("SunilKumarYadav",1001); String json = new
 * ObjectMapper().writeValueAsString(emp);
 * 
 * response.getWriter().write(json); }
 * 
 * class Employee { private String name;
 * 
 * private int id;
 * 
 * public String getName() { return name; }
 * 
 * public int getId() { return id; }
 * 
 * public void setName(String name) { this.name = name; }
 * 
 * public void setId(int id) { this.id = id; }
 * 
 * public Employee() {}
 * 
 * public Employee(String name, int id) { super(); this.name = name; this.id =
 * id; }
 * 
 * 
 * } }
 */