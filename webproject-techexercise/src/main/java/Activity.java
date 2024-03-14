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
            + "<a href=/webproject-techexercise/Shelves>Shelves</a> <br> "
            + "<a href=/webproject-techexercise/Activity>Activity</a> <br> </nav>"
            + "<h2 align=\"center\">" + tableName1 + "</h2>\n");	// first table name
      out.println("<table class=\"center\"> <tr> <th style=\"width:20%\">Book Title</th> "	// first table
      		+ "<th style=\"width:20%\">Book Author</th> <th style=\"width:20%\">Date Started</th> "
      		+ "<th style=\"width:20%\">Activity Date</th> <th style=\"width:20%\">Pages Read</th></tr>");

      
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
            String bookTitle = rs.getString("titleFk2").trim();
            String bookAuthor = rs.getString("authorFk2").trim();
            String bookDateStarted = rs.getString("dateStartedFk").trim();
            String bookActivityDate = rs.getString("activityDate").trim();
            int bookPagesRead = rs.getInt("pagesRead");

            out.println("<tr><td>" + bookTitle + "</td>");
            out.println("<td>" + bookAuthor + "</td>");
            out.println("<td>" + bookDateStarted + "</td>");
            out.println("<td>" + bookActivityDate + "</td>");
            out.println("<td>" + bookPagesRead + "</td></tr>");
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
