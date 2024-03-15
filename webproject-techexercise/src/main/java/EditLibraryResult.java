
/**
 * @file EditLibraryResult.java
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

@WebServlet("/EditLibraryResult")
public class EditLibraryResult extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public EditLibraryResult() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String selectedBookPk = request.getParameter("selectBook");
      String addTitle = request.getParameter("titleInput");
      String addAuthor = request.getParameter("authorInput");
      String addGenre = request.getParameter("genreInput");
      int addYearFirstPublished = Integer.parseInt(request.getParameter("yearFirstPublishedInput")); 
      int addPageCount = Integer.parseInt(request.getParameter("pageCountInput"));

      String[] selectedBookFields;
      selectedBookFields = selectedBookPk.split(",");
      String selectedTitle =  selectedBookFields[0];
      String selectedAuthor =  selectedBookFields[1];
    		  
      Connection connection = null;
      String insertSql = " UPDATE books SET title = ?, author = ?, genre = ?, yearFirstPublished = ?, pageCt = ? "
      		+ "WHERE title = ? AND author = ?";
      try {
         DBConnectionFutakami.getDBConnection();
         connection = DBConnectionFutakami.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, addTitle);
         preparedStmt.setString(2, addAuthor);
         preparedStmt.setString(3, addGenre);
         preparedStmt.setInt(4, addYearFirstPublished);
         preparedStmt.setInt(5, addPageCount);
         preparedStmt.setString(6, selectedTitle);
         preparedStmt.setString(7, selectedAuthor);         
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
      String header = "Library Edit Complete!";
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
      		+ selectedAuthor + " to: <br><section>"	
      	    + "<ul>\n" + // list
      	    "  <li><b>Book Title</b>: " + addTitle + "\n" + 
      	    "  <li><b>Book Author</b>: " + addAuthor + "\n" + 
      	    "  <li><b>Genre</b>: " + addGenre + "\n" + 
      	    "  <li><b>Year First Published</b>: " + addYearFirstPublished + "\n" + 
      	    "  <li><b>Page Count</b>: " + addPageCount + "\n" + 
      	    "</ul>\n");

      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
