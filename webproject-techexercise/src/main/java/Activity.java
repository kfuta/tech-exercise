import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Activity")
public class Activity extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Activity () {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      tableContents(response);
   }

   void tableContents(HttpServletResponse response) throws IOException {
      // HTML and CSS formatting
	  response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Reading Tracker";
      String tableName1 = "Your Activity";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"; 
      out.println(docType + "<html>\n" + "<head><title>" + title + "</title>\n"); // start of head; start and end of title
      out.println("<link rel=\"stylesheet\" href=\"style.css\">");	// stylesheet
      out.println("</head>\n" 	// end of head
            + "<body>\n" 	// start of body
            + "<header><h1 align=\"center\">&#128218; " + title + " &#128218;</h1></header>\n"	// first header
            + "<nav> <a href=/webproject-techexercise/Library class=mainNav>Library</a> <br>"	// navigation links
            + "<a href=/webproject-techexercise/Shelves class=mainNav>Shelves</a> <br> "
            + "<a href=/webproject-techexercise/Activity class=mainNav>Activity</a> <br></nav>");
      out.println("<div align=\"center\"><a href=/webproject-techexercise/AddActivity.html class=otherLink>Add Activity</a>" // other links
              + "&emsp;" + "<a href=/webproject-techexercise/EditActivity class=otherLink>Edit Activity</a>" + "&emsp;"
              + "<a href=/webproject-techexercise/RemoveActivity.html class=otherLink>Remove Activity</a></div>");
      out.println("<h2 align=\"center\">" + tableName1 + "</h2>\n");	// first table name
      out.println("<table class=\"center\"> <thead><tr> <th style=\"width:16.66%\">Activity Number</th> <th style=\"width:16.66%\">Book Title</th> "	// first table
      		+ "<th style=\"width:16.66%\">Book Author</th> <th style=\"width:16.66%\">Date Started</th> "
      		+ "<th style=\"width:16.66%\">Activity Date</th> <th style=\"width:16.66%\">Pages Read</th></tr></thead><tbody>");

      
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         // open database connection
    	 DBConnectionFutakami.getDBConnection();
         connection = DBConnectionFutakami.connection;	

         // print books table
         String selectSQL = "SELECT * FROM readingActivity ORDER By activityId DESC";
         preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
        	int activityId = rs.getInt("activityId");
            String bookTitle = rs.getString("titleFk2").trim();
            String bookAuthor = rs.getString("authorFk2").trim();
            String bookDateStarted = rs.getString("dateStartedFk").trim();
            String bookActivityDate = rs.getString("activityDate").trim();
            int bookPagesRead = rs.getInt("pagesRead");
            
            out.println("<tr><td>" + activityId + "</td>");
            out.println("<td>" + bookTitle + "</td>");
            out.println("<td>" + bookAuthor + "</td>");
            out.println("<td>" + bookDateStarted + "</td>");
            out.println("<td>" + bookActivityDate + "</td>");
            out.println("<td>" + bookPagesRead + "</td></tr>");
         }
         out.println("</tbody></table>");
         rs.close();
         preparedStatement.close();
         
         out.println("</body></html>");
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
