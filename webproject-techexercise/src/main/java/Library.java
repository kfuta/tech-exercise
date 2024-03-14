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

@WebServlet("/Library")
public class Library extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Library () {
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
      String tableName1 = "Your Library";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"; 
      out.println(docType + "<html>\n" + "<head><title>" + title + "</title>\n"); // start of head; start and end of title
      out.println("<link rel=\"stylesheet\" href=\"style.css\">");	// stylesheet
      out.println("</head>\n" 	// end of head
            + "<body>\n" 	// start of body
            + "<header><h1 align=\"center\">&#128218; " + title + " &#128218;</h1></header>\n"	// first header
            + "<nav> <a href=/webproject-techexercise/Library class=mainNav>Library</a> <br>"	// navigation links; remember to change to 4830- when you deploy
            + "<a href=/webproject-techexercise/Shelves class=mainNav>Shelves</a> <br> "
            + "<a href=/webproject-techexercise/Activity class=mainNav>Activity</a> <br></nav>");
      out.println("<div align=\"center\"><a href=/webproject-techexercise/AddToLibrary.html class=otherLink>Add to Library</a>" // other links
      		+ "&emsp;" + "<a href=/webproject-techexercise/FIX.html class=otherLink>Edit Library</a>" + "&emsp;"
      		+ "<a href=/webproject-techexercise/FIX.html class=otherLink>Remove from Library</a></div>");
      out.println("<h2 align=\"center\">" + tableName1 + "</h2>\n");	// first table name

      out.println("<table class=\"center\"> <tr> <th style=\"width:20%\">Book Title</th> "	// first table
      		+ "<th style=\"width:20%\">Book Author</th> <th style=\"width:20%\">Genre</th> "
      		+ "<th style=\"width:20%\">Year First Published</th> <th style=\"width:20%\">Book Page Count</th></tr>");

      
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         // open database connection
    	 DBConnectionFutakami.getDBConnection();
         connection = DBConnectionFutakami.connection;	

         // print books table
         String selectSQL = "SELECT * FROM books ORDER By title";
         preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            String bookTitle = rs.getString("title").trim();
            String bookAuthor = rs.getString("author").trim();
            String bookGenre = rs.getString("genre").trim();
            int bookYearFirstPublished = rs.getInt("yearFirstPublished");
            int bookPageCt = rs.getInt("pageCt");

            out.println("<tr><td>" + bookTitle + "</td>");
            out.println("<td>" + bookAuthor + "</td>");
            out.println("<td>" + bookGenre + "</td>");
            out.println("<td>" + bookYearFirstPublished + "</td>");
            out.println("<td>" + bookPageCt + "</td></tr>");
         }
         out.println("</table>");
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
