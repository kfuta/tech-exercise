
/**
 * @file EditActivity.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditActivity")
public class EditActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditActivity() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Reading Tracker";
		String header = "Edit Activity";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title>\n"); // start of head; start and end of																					// title
		out.println("<link rel=\"stylesheet\" href=\"style.css\">"); // stylesheet
		out.println("</head>\n" // end of head
				+ "<body>\n" // start of body
				+ "<header><h1 align=\"center\">&#128218; " + title + " &#128218;</h1></header>\n" // first header
				+ "<nav> <a href=/webproject-techexercise/Library class=mainNav>Library</a> <br>" // navigation links
				+ "<a href=/webproject-techexercise/Shelves class=mainNav>Shelves</a> <br> "
				+ "<a href=/webproject-techexercise/Activity class=mainNav>Activity</a> <br></nav>");
		out.println("<h2 align=center>" + header + "</h2>");

		Connection connection = null;
		String selectSQL = null;

		try {
			// open database connection
			DBConnectionFutakami.getDBConnection();
			connection = DBConnectionFutakami.connection;

			// print books table
			selectSQL = "SELECT * FROM readingActivity ORDER By activityId DESC";
			PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			out.println("<section><form action=\"EditActivityResult\" method=\"POST\">");
			out.println("Select the activity you want to edit.<br /><br />");

			while (rs.next()) {
				int activityId = rs.getInt("activityId");
				String bookTitle = rs.getString("titleFk2").trim();
				String bookAuthor = rs.getString("authorFk2").trim();
				String bookDateStarted = rs.getString("dateStartedFk").trim();
				String bookActivityDate = rs.getString("activityDate").trim();
				int bookPagesRead = rs.getInt("pagesRead");
				out.println("<input type=\"radio\" id=\"" + activityId + "\" name=\"selectActivity\" value=\""
						+ activityId + "\">");
				out.println("<label for=\"" + activityId + "\"><b>Activity Number:</b> " + activityId
						+ ", <b>Book Title:</b> " + bookTitle + ", <b>Book Author:</b> " + bookAuthor
						+ ", <b>Date Started:</b> " + bookDateStarted + ", <b>Activity Date:</b> " + bookActivityDate
						+ ", <b>Pages Read:</b> " + bookPagesRead + "</label><br>");
			}
			out.println("<br /><br />Enter the new activity information. All fields are required. The book title, book author, and date started"
					+ " must already exist as a group within your shelves.<br /><br />");
			out.println("<label for=\"titleInput\">Book Title (less than 100 characters): </label>");
			out.println("<input type=\"text\" name=\"titleInput\" maxlength=\"100\" required> <br />");
			out.println("<label for=\"authorInput\">Book Author (less than 50 characters):</label>");
			out.println("<input type=\"text\" name=\"authorInput\" maxlength=\"50\" required> <br />");
			out.println("<label for=\"dateStartedInput\">Date Started (less than 15 characters):</label>");
			out.println("<input type=\"text\" name=\"dateStartedInput\" maxlength=\"15\" required> <br />");
			out.println("<label for=\"activityDateInput\">Activity Date (less than 15 characters):</label>");
			out.println("<input type=\"text\" name=\"activityDateInput\" maxlength=\"15\" required> <br />");
			out.println("<label for=\"pagesReadInput\">Pages Read (positive integer less than 1000):</label>");
			out.println("<input type=\"number\" name=\"pagesReadInput\" min=\"1\" max=\"999\" required> <br /><br />");
			out.println("<input type=\"submit\" value=\"Edit Activity\" />");
			out.println("</form></section>");

			rs.close();
			preparedStatement.close();
			DBConnectionFutakami.getDBConnection();
			connection = DBConnectionFutakami.connection;
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
