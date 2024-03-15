
/**
 * @file EditShelves.java
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

@WebServlet("/EditShelves")
public class EditShelves extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditShelves() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Reading Tracker";
		String header = "Edit Shelves";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title>\n"); // start of head; start and end of
																					// // title
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
			selectSQL = "SELECT * FROM bookInstance";
			PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			out.println("<section><form action=\"EditShelvesResult\" method=\"POST\">");
			out.println("Select the book in your shelves you want to edit.<br /><br />");

			while (rs.next()) {

				String bookTitleFk = rs.getString("titleFk").trim();
				String bookAuthorFk = rs.getString("authorFk").trim();
				String bookShelfType = rs.getString("shelfType").trim();
				String bookDateStarted = rs.getString("dateStarted").trim();
				String bookDateCompleted = rs.getString("dateCompleted").trim();
				int bookRating = rs.getInt("rating");
				
				out.println("<input type=\"radio\" id=\"" + bookTitleFk + "," + bookAuthorFk + "," + bookDateStarted + "\" name=\"selectBook\" value=\""
						+ bookTitleFk + "," + bookAuthorFk + "," + bookDateStarted + "\">");
				out.println("<label for=\"" + bookTitleFk + "," + bookAuthorFk + "," + bookDateStarted + "\"><b>Book Title:</b> " + bookTitleFk
						+ ", <b>Book Author:</b> " + bookAuthorFk + ", <b>Shelf Type:</b> " + bookShelfType
						+ ", <b>Date Started:</b> " + bookDateStarted + ", <b>Date Completed:</b> " + bookDateCompleted
						+ ", <b>Book Rating:</b> " + bookRating + "</label><br>");
			}
			out.println("<br /><br />Enter the new information for the book in your shelves. All fields are required. The book"
					+ " title and book author must already exist as a group within your library.<br /><br />");
			out.println("<label for=\"titleInput\">Book Title (less than 100 characters): </label>");
			out.println("<input type=\"text\" name=\"titleInput\" maxlength=\"100\" required> <br />");
			out.println("<label for=\"authorInput\">Book Author (less than 50 characters):</label>");
			out.println("<input type=\"text\" name=\"authorInput\" maxlength=\"50\" required> <br />");
			out.println("<label for=\"shelfTypeInput\">Shelf Type:</label>");
			out.println("<select id=\"shelves\" name=\"shelfTypeInput\" required>");
			out.println("<option value=\"To Read\">To Read</option>");
			out.println("<option value=\"Currently Reading\">Currently Reading</option>");			
			out.println("<option value=\"Finished Reading\">Finished Reading</option>");		
			out.println("</select><br />");
			out.println("<label for=\"dateStartedInput\">Date Started (less than 15 characters):</label>");
			out.println("<input type=\"text\" name=\"dateStartedInput\" maxlength=\"15\" required> <br />");
			out.println("<label for=\"dateFinishedInput\">Date Completed (less than 15 characters):</label>");
			out.println("<input type=\"text\" name=\"dateFinishedInput\" maxlength=\"15\" required> <br />");
			out.println("<label for=\"ratingInput\">Rating (integers 0-5):</label>");
			out.println("<input type=\"number\" name=\"ratingInput\" maxlength=\"1\" min=\"0\" max=\"5\" required> <br /><br />");
			out.println("<input type=\"submit\" value=\"Edit Book in Shelves\" />");
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
