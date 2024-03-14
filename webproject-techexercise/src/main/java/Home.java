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

@WebServlet("/Home")
public class Home extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Home () {
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
      String tableName2 = "Books You Are Currently Reading";
      String tableName3 = "Books You Plan to Read";
      String tableName4 = "Books You've Read";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"; 
      out.println(docType 
            + "<html>\n" 
            + "<head><title>" + title + "</title>\n" // start and end of title
            + "<style> body {\n"	// start of style
            + "background-color: #F1FAEE;\n}\n"
            + ".center {\n"
            + "margin-left: auto;\n"
            + "margin-right: auto;\n}\n"
            + "table {\n"
            + "border-collapse: collapse;\n"
            + "width: 80%;\n}\n"
            + "th, td {border-bottom: 1px solid;\n"
            + "border-color: #1D3557;\n"
            + "padding: 5px;\n"
            + "text-align: left;\n}\n"
            + "th {background-color: #A8DADC;\n}\n"
            + "tr:hover {background-color: #E63946;\n}\n"
            + "header {background-color: #1D3557;\n"
            + "color: #F1FAEE;\n"
            + "text-align:center;"
            + "padding:5px;\n}\n"
            + "section {\n"
            + "width: 350px;\n"
            + "float: left;\n"
            + "padding 10px;\n}\n"
            + "nav {\n"
            + "line-height:30px;\n"
            + "background-color: #457B9D;\n"
            + "width:100px;\n"
            + "height:700px;\n"
            + "float:left;\n"
            + "padding:5px;\n}\n"
            + "a {color: #F1FAEE\n"
            + "</style>\n"	// end of style
            + "</head>\n" 
            + "<body>\n" 
            + "<header><h1 align=\"center\">" + title + "</h1></header>\n"	// first headers and table
            + "<nav> <a href=/4830-webproject-techexercise/Home>Home</a> <br>"
            + "<a href=/4830-webproject-techexercise/library_search.html>Search Data</a> <br> </nav>"
            + "<h2 align=\"center\">" + tableName1 + "</h2>\n");
      out.println("<table class=\"center\"> <tr> <th style=\"width:33.3%\">Book Title</th> "
      		+ "<th style=\"width:33.3%\">Book Author</th> <th style=\"width:33.3%\">Book Page Count</th> </tr>");
    
      /*
nav {
    line-height:30px;
    background-color:#eeeeee;
    height:300px;
    width:100px;
    float:left;
    padding:5px;	      
}
section {
    width:350px;
    float:left;
    padding:10px;	 	 
}
footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
    padding:5px;	 	 
}
       */

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
            int bookPageCt = rs.getInt("pageCt");

            out.println("<tr><td>" + bookTitle + "</td>");
            out.println("<td>" + bookAuthor + "</td>");
            out.println("<td>" + bookPageCt + "</td></tr>");
         }
         out.println("</table>");
         rs.close();
         preparedStatement.close();
         
         //print bookInstance table where shelfType = "Currently Reading"
         out.println("<h2 align=\"center\">" + tableName2 + "</h2>\n");
         out.println("<table class=\"center\"> <tr> <th style=\"width:20%\">Book Title</th> <th style=\"width:20%\">Book Author</th>"
         + "<th style=\"width:20%\">Date Started</th> <th style=\"width:20%\">Date Completed</th> <th style=\"width:20%\">Rating</th></tr>");
         selectSQL = "SELECT * FROM bookInstance WHERE shelfType = 'Currently Reading'";
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
