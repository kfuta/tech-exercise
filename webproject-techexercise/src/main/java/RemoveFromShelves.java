
/**
 * @file RemoveFromShelves.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveFromShelves")
public class RemoveFromShelves extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public RemoveFromShelves() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	   String removeTitle = request.getParameter("titleInput");
	   String removeAuthor = request.getParameter("authorInput");
	   String removeDateStarted = request.getParameter("dateStartedInput");

      Connection connection = null;
      String removeSql = "DELETE FROM bookInstance WHERE titleFk=? AND authorFk=? AND dateStarted=?";

      try {
         DBConnectionFutakami.getDBConnection();
         connection = DBConnectionFutakami.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(removeSql);
         preparedStmt.setString(1, removeTitle);
         preparedStmt.setString(2, removeAuthor);
         preparedStmt.setString(3, removeDateStarted);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      //
      String title = "Reading Tracker";
      String header = "Shelf Removal Complete!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"; 
      out.println(docType + "<html>\n" + "<head><title>" + title + "</title>\n"); // start of head; start and end of title
      out.println("<link rel=\"stylesheet\" href=\"style.css\">");	// stylesheet
      out.println("</head>\n" 	// end of head
            + "<body>\n" 	// start of body
            + "<header><h1 align=\"center\">&#128218; " + title + " &#128218;</h1></header>\n"	// first header
            + "<nav> <a href=/webproject-techexercise/Library class=mainNav>Library</a> <br>"	// navigation links
            + "<a href=/webproject-techexercise/Shelves class=mainNav>Shelves</a> <br> "
            + "<a href=/webproject-techexercise/Activity class=mainNav>Activity</a> <br></nav>");
      out.println("<h2 align=\"center\">" + header + "</h2>\n"	// header
        	+ "<section>You removed the following book from your shelves: <br><section>"	
            + "<ul>\n" + // list
            "  <li><b>Book Title</b>: " + removeTitle + "\n" + 
            "  <li><b>Book Author</b>: " + removeAuthor + "\n" + 
            "  <li><b>Date Started</b>: " + removeDateStarted + "\n" + 
            "</ul>\n");

      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
