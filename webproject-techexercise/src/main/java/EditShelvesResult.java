
/**
 * @file EditShelvesResult.java
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

@WebServlet("/EditShelvesResult")
public class EditShelvesResult extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public EditShelvesResult() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String selectedBookPk = request.getParameter("selectBook");
      String addTitle = request.getParameter("titleInput");
      String addAuthor = request.getParameter("authorInput");
      String addShelfType = request.getParameter("shelfTypeInput");
      String addDateStarted = request.getParameter("dateStartedInput");
      String addDateFinished = request.getParameter("dateFinishedInput"); 
      int addRating = Integer.parseInt(request.getParameter("ratingInput"));

      String[] selectedBookFields;
      selectedBookFields = selectedBookPk.split(",");
      String selectedTitle =  selectedBookFields[0];
      String selectedAuthor =  selectedBookFields[1];
      String selectedDateStarted =  selectedBookFields[2];
    		  
      Connection connection = null;
      String insertSql = " UPDATE bookInstance SET titleFk = ?, authorFk = ?, shelfType = ?, dateStarted = ?, dateCompleted = ?, "
      		+ "rating = ? WHERE titleFk = ? AND authorFk = ? AND dateStarted = ?";
      try {
         DBConnectionFutakami.getDBConnection();
         connection = DBConnectionFutakami.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, addTitle);
         preparedStmt.setString(2, addAuthor);
         preparedStmt.setString(3, addShelfType);
         preparedStmt.setString(4, addDateStarted);
         preparedStmt.setString(5, addDateFinished);
         preparedStmt.setInt(6, addRating);
         preparedStmt.setString(7, selectedTitle);
         preparedStmt.setString(8, selectedAuthor);
         preparedStmt.setString(9, selectedDateStarted);
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
      String header = "Shelf Edit Complete!";
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
      		+ "<section>You changed the book <i>" + selectedTitle + "</i> written by "
      		+ selectedAuthor + " and started " + selectedDateStarted + " to: <br><section>"	
      	    + "<ul>\n" + // list
      	    "  <li><b>Book Title</b>: " + addTitle + "\n" + 
      	    "  <li><b>Book Author</b>: " + addAuthor + "\n" + 
      	    "  <li><b>Shelf</b>: " + addShelfType + "\n" + 
      	    "  <li><b>Date Started</b>: " + addDateStarted + "\n" + 
      	    "  <li><b>Date Completed</b>: " + addDateFinished + "\n" + 
      	    "  <li><b>Rating</b>: " + addRating + "\n" +
      	    "</ul>\n");

      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
