package com.sap.hana.cloud.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import com.sap.ui5.resource.util.IXSSEncoder;
import com.sap.ui5.resource.util.XSSEncoder;

public class PersonsJpaPersistenceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static DataSource ds;
	private static EntityManagerFactory emf;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = emf.createEntityManager();
		createResponse(response, em);
		em.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = emf.createEntityManager();
		doAdd(request, em);
		createResponse(response, em);
		em.close();
	}

	private void createResponse(HttpServletResponse response, EntityManager em)
			throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head><title>Servlet for Adding and Displaying persisted Person Entities (with JPA)</title></head><body>");

		List<Person> resultList = em.createQuery("SELECT p FROM Person p").getResultList();

		out.write("<form 	action=\"\" method=\"post\">"
				+ "First name: <input id=\"firstNameFieldId\" type=\"text\" name=\"FirstName\" value=\"\">&nbsp;"
				+ "Last name:  <input id=\"lastNameFieldId\" type=\"text\" name=\"LastName\" value=\"\">&nbsp;"
				+ "<input type=\"submit\" value=\"Add Person\">"
				+ "</form><br>");

		out.write("<table border=\"1\"><tr><th colspan=\"3\">"
				+ (resultList.isEmpty() ? "" : resultList.size() + " ")
				+ "Entries in the Database</th></tr>");

		if (resultList.isEmpty()) {
			out.write("<tr><td colspan=\"3\">Database is empty</td></tr>");
		} else {
			out.write("<tr><th>First name</th><th>Last name</th><th>Id</th></tr>");
		}

		IXSSEncoder xssEncoder = XSSEncoder.getInstance();

		for (Person p : resultList) {
			out.write("<tr><td>" + xssEncoder.encodeHTML(p.getFirstName())
					+ "</td><td>" + xssEncoder.encodeHTML(p.getLastName())
					+ "</td><td>" + p.getId() + "</td></tr>");
		}
		out.write("</table>");

		out.write("</body></html>");
	}

	private void doAdd(HttpServletRequest request, EntityManager em)
			throws ServletException, IOException {
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");

		if (firstName != null && lastName != null
				&& !firstName.trim().isEmpty() && !lastName.trim().isEmpty()) {
			Person person = new Person();
			person.setFirstName(firstName.trim());
			person.setLastName(lastName.trim());
			em.getTransaction().begin();
			em.persist(person);
			em.getTransaction().commit();
		}
	}

	@Override
	public void init() throws ServletException {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
		} catch (NamingException e) {
			throw new ServletException(e);
		}

		Map properties = new HashMap();
		properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
		emf = Persistence.createEntityManagerFactory(
				"personslist-model-jpa", properties);
	}
}