
/**
 * @file EditActivityResult.java
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

@WebServlet("/EditActivityResult")
public class EditActivityResult extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public EditActivityResult() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int selectedActivity = Integer.parseInt(request.getParameter("selectActivity"));
	  String addTitle = request.getParameter("titleInput");
      String addAuthor = request.getParameter("authorInput");
      String addDateStarted = request.getParameter("dateStartedInput");
      String addActivityDate = request.getParameter("activityDateInput");
      int addPagesRead = Integer.parseInt(request.getParameter("pagesReadInput"));

      Connection connection = null;
      String insertSql = " UPDATE readingActivity SET titleFk2 = ?, authorFk2 = ?, dateStartedFk = ?, activityDate = ?, pagesRead = ? WHERE activityId = ?";

      try {
         DBConnectionFutakami.getDBConnection();
         connection = DBConnectionFutakami.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, addTitle);
         preparedStmt.setString(2, addAuthor);
         preparedStmt.setString(3, addDateStarted);
         preparedStmt.setString(4, addActivityDate);
         preparedStmt.setInt(5, addPagesRead);
         preparedStmt.setInt(6, selectedActivity);
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
      String header = "Activity Edit Complete!";
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
      		+ "<section>You changed  activity " + selectedActivity + " to: <br><section>"	
            + "<ul>\n" + // list
            "  <li><b>Book Title</b>: " + addTitle + "\n" + 
            "  <li><b>Book Author</b>: " + addAuthor + "\n" + 
            "  <li><b>Date Started</b>: " + addDateStarted + "\n" + 
            "  <li><b>Activity Date</b>: " + addActivityDate + "\n" + 
            "  <li><b>Pages Read</b>: " + addPagesRead + "\n" +
            "</ul>\n");

      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
