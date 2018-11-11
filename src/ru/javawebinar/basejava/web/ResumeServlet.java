package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.ToHtml;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

	private Storage storage; // = Config.get().getStorage();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		storage = Config.get().getStorage();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String uuid = request.getParameter("uuid");
		String fullName = request.getParameter("fullName");

		final boolean isCreate = (uuid == null || uuid.length() == 0);
		Resume r;
		if (isCreate) {
			r = new Resume(fullName);
		} else {
			r = storage.get(uuid);
			r.setFullName(fullName);
		}

		for (ContactType type : ContactType.values()) {
			String value = request.getParameter(type.name());
			if (ToHtml.isEmpty(value)) {
				r.getContacts().remove(type);
			} else {
				r.setContact(type, value);
			}
		}
		for (SectionType type : SectionType.values()) {
			String value = request.getParameter(type.name());
			String[] values = request.getParameterValues(type.name());
			if (ToHtml.isEmpty(value) && values.length < 2) {
				r.getSections().remove(type);
			} else {
				switch (type) {
					case OBJECTIVE:
					case PERSONAL:
						r.setSection(type, new PlainText(value));
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						r.setSection(type, new StringListText(value.split("\\n")));
						break;
					case EDUCATION:
					case EXPERIENCE:
						List<Organization> orgs = new ArrayList<>();
						String[] urls = request.getParameterValues(type.name() + "url");
						for (int i = 0; i < values.length; i++) {
							String name = values[i];
							if (!ToHtml.isEmpty(name)) {
								List<Organization.Job> positions = new ArrayList<>();
								String pfx = type.name() + i;
								String[] startDates = request.getParameterValues(pfx + "startDate");
								String[] endDates = request.getParameterValues(pfx + "endDate");
								String[] titles = request.getParameterValues(pfx + "title");
								String[] descriptions = request.getParameterValues(pfx + "description");
								for (int j = 0; j < titles.length; j++) {
									if (!ToHtml.isEmpty(titles[j])) {
										positions.add(new Organization.Job(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
									}
								}
								orgs.add(new Organization(new Link(name, urls[i]), positions));
							}
						}
						r.setSection(type, new OrganizationText(orgs));
						break;
				}
			}
		}
		if (isCreate) {
			storage.save(r);
		} else {
			storage.update(r);
		}
		response.sendRedirect("resume");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		String uuid = request.getParameter("uuid");
		String action = request.getParameter("action");
		if (action == null) {
			request.setAttribute("resumes", storage.getAllSorted());
			request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
			return;
		}
		Resume r;
		switch (action) {
			case "delete":
				storage.delete(uuid);
				response.sendRedirect("resume");
				return;
			case "view":
				r = storage.get(uuid);
				break;
			case "add":
				r = Resume.EMPTY;
				break;
			case "edit":
				r = storage.get(uuid);
				for (SectionType type : SectionType.values()) {
					Text section = r.getSection(type);
					switch (type) {
						case OBJECTIVE:
						case PERSONAL:
							if (section == null) {
								section = PlainText.EMPTY;
							}
							break;
						case ACHIEVEMENT:
						case QUALIFICATIONS:
							if (section == null) {
								section = StringListText.EMPTY;
							}
							break;
						case EXPERIENCE:
						case EDUCATION:
							OrganizationText orgSection = (OrganizationText) section;
							List<Organization> emptyFirstOrganizations = new ArrayList<>();
							emptyFirstOrganizations.add(Organization.EMPTY);
							if (orgSection != null) {
								for (Organization org : orgSection.getOrgList()) {
									List<Organization.Job> emptyFirstJobs = new ArrayList<>();
									emptyFirstJobs.add(Organization.Job.EMPTY);
									emptyFirstJobs.addAll(org.getJobList());
									emptyFirstOrganizations.add(new Organization(org.getHomePage(), emptyFirstJobs));
								}
							}
							section = new OrganizationText(emptyFirstOrganizations);
							break;
					}
					r.setSection(type, section);
				}
				break;
			default:
				throw new IllegalArgumentException("Action " + action + " is illegal");
		}
		request.setAttribute("resume", r);
		request.getRequestDispatcher(
				("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
		).forward(request, response);
	}
}