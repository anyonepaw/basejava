package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.storage.Storage;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {

	private Storage storage; // = Config.get().getStorage();

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		super.init(config);
		storage = Config.get().getStorage();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
//		String name = request.getParameter("name");
//		response.getWriter().write(name == null ? "Hello, Resumes!" :  "Hello " + name + '!');
		Writer writer = response.getWriter();
		writer.write("<html>\n" +
				"<head>\n" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
				" <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/" +
				"bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\"" +
				"crossorigin=\"anonymous\">" +
				"<title>Resumes table</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"<br>" +
				"<h2 align =\"center\">Resumes</h2>" +
				"<table class=\"table\" align =\"center\"  border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
				"<thead class=\"thead-dark\">" +
				"    <tr>\n" +
				"        <th>Name</th>\n" +
				"        <th>Contacts</th>\n" +
				"        <th>Personal</th>\n" +
				"        <th>Objective</th>\n" +
				"        <th>Achievement</th>\n" +
				"        <th>Qualifications</th>\n" +
				"    </tr>\n" +
				"</thead>" +
				"</body>\n" +
				"</html>");

		for (Resume resume : storage.getAllSorted()) {
			writer.write(
					"<tr>\n" +
							"<td>" + resume.getFullName() + "</td>\n" +
							"<td>" + "Mail: "+resume.getContact(ContactType.MAIL) + "<br>" +
									"Telephone: "+ resume.getContact(ContactType.TELEPHONE) + "<br>"  +
									"Skype: "+ resume.getContact(ContactType.SKYPE) + "<br>"  +
									"GitHub: "+ resume.getContact(ContactType.GITHUB) + "<br>"  +
									"LinkedIn: "+ resume.getContact(ContactType.LINKEDIN) + "<br>"  +
									"StackOverflow: "+ resume.getContact(ContactType.STATCKOVERFLOW) + "<br>"  +
									"HomePage: "+ resume.getContact(ContactType.HOME_PAGE) + "</td>\n" +
							"<td>" + resume.getSection(SectionType.PERSONAL)+ "</td>\n" +
							"<td>" + resume.getSection(SectionType.OBJECTIVE)+ "</td>\n" +
							"<td>" + resume.getSection(SectionType.ACHIEVEMENT)+ "</td>\n" +
							"<td>" + resume.getSection(SectionType.QUALIFICATIONS)+ "</td>\n" +
							"</tr>\n");
		}
		writer.write("</table>\n" +
				"</section>\n" +
				"</body>\n" +
				"</html>\n");
	}

}
