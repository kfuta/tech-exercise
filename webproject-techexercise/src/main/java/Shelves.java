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

@WebServlet("/Shelves")
public class Shelves extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Shelves () {
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
      String tableName2 = "Books You Are Currently Reading";
      String tableName3 = "Books You Plan to Read";
      String tableName4 = "Books You've Read";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"; 
      out.println(docType + "<html>\n" + "<head><title>" + title + "</title>\n"); // start of head; start and end of title
      out.println("<style>\n" // start of style
      		+ "body {\n background-color: #F1FAEE;\n}\n"
            + ".center {\n"	// .center style
            	+ "margin-left: auto;\n"
            	+ "margin-right: auto;\n}\n"
            + "table {\n"	// table style
            	+ "border-collapse: collapse;\n"
            	+ "width: 80%;\n}\n"
            + "th, td {border-bottom: 1px solid;\n"		// table header and data style
            	+ "border-color: #1D3557;\n"
            	+ "padding: 5px;\n"
            	+ "text-align: left;\n}\n"
            + "th {background-color: #A8DADC;\n}\n"		// table header style
            + "tr:hover {background-color: #E63946;\n}\n"	// table row hover feature
            + "header {background-color: #1D3557;\n"	// header style
            	+ "color: #E63946;\n"
            	+ "font-family: Trebuchet MS;\n"
            	+ "text-align:center;"
            	+ "padding:5px;\n}\n"
            + "nav {\n"		// navigation style
            	+ "line-height:30px;\n"
            	+ "background-color: #457B9D;\n"
            	+ "width:100px;\n"
            	+ "height:700px;\n"
            	+ "float:left;\n"
            	+ "padding:5px;\n}\n"
            + "a {color: #F1FAEE\n"		// link style
            + "</style>\n"	// end of style
            + "</head>\n" 	// end of head
            + "<body>\n" 	// start of body
            + "<header><h1 align=\"center\">" + title + "</h1></header>\n"	// first header
            + "<nav> <a href=/webproject-techexercise/Library>Library</a> <br>"	// navigation links; remember to change to 4830- when you deploy
            + "<a href=/webproject-techexercise/Shelves>Shelves</a> <br> </nav>");

     
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         // open database connection
    	 DBConnectionFutakami.getDBConnection();
         connection = DBConnectionFutakami.connection;	

         // print bookInstance table where shelfType = "Currently Reading"
         out.println("<h2 align=\"center\">" + tableName2 + "</h2>\n");
         out.println("<table class=\"center\"> <tr> <th style=\"width:20%\">Book Title</th> <th style=\"width:20%\">Book Author</th>"
         + "<th style=\"width:20%\">Date Started</th> <th style=\"width:20%\">Date Completed</th> <th style=\"width:20%\">Rating</th></tr>");
         String selectSQL = "SELECT * FROM bookInstance WHERE shelfType = 'Currently Reading'";
         preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet rs = preparedStatement.executeQuery();
         
         while (rs.next()) {
             String bookTitleFk = rs.getString("titleFk").trim();
             String bookAuthorFk = rs.getString("authorFk").trim();
             String bookDateStarted = rs.getString("dateStarted").trim();
             String bookDateCompleted = rs.getString("dateCompleted").trim();
             int bookRating = rs.getInt("rating");

             out.println("<tr><td>" + bookTitleFk + "</td>");
             out.println("<td>" + bookAuthorFk + "</td>");
             out.println("<td>" + bookDateStarted + "</td>");
             out.println("<td>" + bookDateCompleted + "</td>");
             out.println("<td>" + bookRating + "</td></tr>");
          }
          out.println("</table>");
          rs.close();
          preparedStatement.close();
          
          //print bookInstance table where shelfType = "To Read"
          out.println("<h2 align=\"center\">" + tableName3 + "</h2>\n");
          out.println("<table class=\"center\"> <tr> <th style=\"width:20%\">Book Title</th> <th style=\"width:20%\">Book Author</th>"
          + "<th style=\"width:20%\">Date Started</th> <th style=\"width:20%\">Date Completed</th> <th style=\"width:20%\">Rating</th></tr>");
          selectSQL = "SELECT * FROM bookInstance WHERE shelfType = 'To Read'";
          preparedStatement = connection.prepareStatement(selectSQL);
          rs = preparedStatement.executeQuery();
          
          while (rs.next()) {
              String bookTitleFk = rs.getString("titleFk").trim();
              String bookAuthorFk = rs.getString("authorFk").trim();
              String bookDateStarted = rs.getString("dateStarted").trim();
              String bookDateCompleted = rs.getString("dateCompleted").trim();
              int bookRating = rs.getInt("rating");

              out.println("<tr><td>" + bookTitleFk + "</td>");
              out.println("<td>" + bookAuthorFk + "</td>");
              out.println("<td>" + bookDateStarted + "</td>");
              out.println("<td>" + bookDateCompleted + "</td>");
              out.println("<td>" + bookRating + "</td></tr>");
           }
           out.println("</table>");
           rs.close();
           preparedStatement.close();
           
           //print bookInstance table where shelfType = "Finished Reading"
           out.println("<h2 align=\"center\">" + tableName4 + "</h2>\n");
           out.println("<table class=\"center\"> <tr> <th style=\"width:20%\">Book Title</th> <th style=\"width:20%\">Book Author</th>"
           		+ "<th style=\"width:20%\">Date Started</th> <th style=\"width:20%\">Date Completed</th> <th style=\"width:20%\">Rating</th></tr>");
           selectSQL = "SELECT * FROM bookInstance WHERE shelfType = 'Finished Reading'";
           preparedStatement = connection.prepareStatement(selectSQL);
           rs = preparedStatement.executeQuery();
           
           while (rs.next()) {
               String bookTitleFk = rs.getString("titleFk").trim();
               String bookAuthorFk = rs.getString("authorFk").trim();
               String bookDateStarted = rs.getString("dateStarted").trim();
               String bookDateCompleted = rs.getString("dateCompleted").trim();
               int bookRating = rs.getInt("rating");

               out.println("<tr><td>" + bookTitleFk + "</td>");
               out.println("<td>" + bookAuthorFk + "</td>");
               out.println("<td>" + bookDateStarted + "</td>");
               out.println("<td>" + bookDateCompleted + "</td>");
               out.println("<td>" + bookRating + "</td></tr>");
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
