package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InvoiceDAO;
import model.Invoice;

/**
 * Servlet implementation class ExportInvoiceServlet
 */
@WebServlet("/ExportInvoice")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class ExportInvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportInvoiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String invoiceIdString = request.getParameter("txtInvoiceId");
		String delivery = request.getParameter("txtDelivery");
		String status = request.getParameter("txtStatus");
		
		int invoiceId = Integer.parseInt(invoiceIdString);
		
		InvoiceDAO invoiceDAO = new InvoiceDAO();
		
		Invoice invoice = invoiceDAO.getById(invoiceId);
		invoice.setStatus(status);
		
		invoiceDAO.update(invoice);
		
		response.setContentType("application/json");
		response.getWriter().write("{}");
	}

}
