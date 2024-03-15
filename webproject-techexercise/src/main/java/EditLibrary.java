
/**
 * @file EditLibrary.java
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

@WebServlet("/EditLibrary")
public class EditLibrary extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditLibrary() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Reading Tracker";
		String header = "Edit Library";
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
			selectSQL = "SELECT * FROM books";
			PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			out.println("<section><form action=\"EditLibraryResult\" method=\"POST\">");
			out.println("Select the book in your library you want to edit.<br /><br />");

			while (rs.next()) {

	            String bookTitle = rs.getString("title").trim();
	            String bookAuthor = rs.getString("author").trim();
	            String bookGenre = rs.getString("genre").trim();
	            int bookYearFirstPublished = rs.getInt("yearFirstPublished");
	            int bookPageCt = rs.getInt("pageCt");
				
				out.println("<input type=\"radio\" id=\"" + bookTitle + "," + bookAuthor + "\" name=\"selectBook\" value=\""
						+ bookTitle + "," + bookAuthor + "\">");
				out.println("<label for=\"" + bookTitle + "," + bookAuthor + "\"><b>Book Title:</b> " + bookTitle
						+ ", <b>Book Author:</b> " + bookAuthor + ", <b>Genre:</b> " + bookGenre
						+ ", <b>Year First Published:</b> " + bookYearFirstPublished + ", <b>Page Count:</b> " + bookPageCt
						+ "</label><br>");
			}
			out.println("<br /><br />Enter the new information for the book in your library. All fields are required. The book title and book author "
					+ "must already exist as a group within your library.<br /><br />");
			out.println("<label for=\"titleInput\">Book Title (less than 100 characters): </label>");
			out.println("<input type=\"text\" name=\"titleInput\" maxlength=\"100\" required> <br />");			
			out.println("<label for=\"authorInput\">Book Author (less than 50 characters):</label>");
			out.println("<input type=\"text\" name=\"authorInput\" maxlength=\"50\" required> <br />");			
			out.println("<label for=\"genreInput\">Genre (less than 50 characters):</label>");
			out.println("<input type=\"text\" name=\"genreInput\" maxlength=\"50\" required> <br />");			
			out.println("<label for=\"yearFirstPublishedInput\">Year First Published (positive integer less than 2025):</label>");
			out.println("<input type=\"number\" name=\"yearFirstPublishedInput\" min=\"1\" max=\"2024\" required> <br />");			
			out.println("<label for=\"pageCountInput\">Book Page Count (positive integer less than 10000):</label>");
			out.println("<input type=\"number\" name=\"pageCountInput\" min=\"1\" max=\"9999\" required> <br /><br />");		
			out.println("<input type=\"submit\" value=\"Edit Book in Library\" />");
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
