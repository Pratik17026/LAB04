package servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Note;

public class Noteservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = getServletContext().getRealPath("/WEB-INF/note.txt");
       
        // to read files
        BufferedReader br;               
        br = new BufferedReader(new FileReader(new File(path)));
        Note note = new Note(br.readLine(), br.readLine());        
        request.setAttribute("note", note);
        
        br.close();
        
        if(request.getParameter("edit") != null && request.getParameter("edit").equals("")) {
             getServletContext().getRequestDispatcher("/WEB-INF/editnote.jsp").forward(request, response);
             return;
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String path = getServletContext().getRealPath("/WEB-INF/note.txt");
        
        // to write to a file
        PrintWriter pw; 
        pw = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
        pw.println(request.getParameter("title"));           
        pw.println(request.getParameter("contents"));   
        pw.close();

        BufferedReader br;
        br = new BufferedReader(new FileReader(new File(path)));
        Note note = new Note(br.readLine(), br.readLine());
        
        br.close();
        
        request.setAttribute("note", note);

        getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
        return;
    }
}