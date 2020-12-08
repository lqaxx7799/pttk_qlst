package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InvoiceDAO;
import dao.InvoiceDetailDAO;
import model.Invoice;
import model.InvoiceDetail;

/**
 * Servlet implementation class WaitingInvoiceDetail
 */
@WebServlet("/WaitingInvoiceDetail")
public class WaitingInvoiceDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WaitingInvoiceDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InvoiceDAO invoiceDAO = new InvoiceDAO();
		InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
		
		String invoiceIdString = request.getParameter("id");
		int invoiceId = Integer.parseInt(invoiceIdString);
		
		Invoice invoice = invoiceDAO.getById(invoiceId);
		ArrayList<InvoiceDetail> invoiceDetails = invoiceDetailDAO.getByInvoiceId(invoiceId);
		
		float totalMoney = 0;
		for(InvoiceDetail invoiceDetail : invoiceDetails) {
			totalMoney += invoiceDetail.getQuantity() * invoiceDetail.getUnitPrice();
		}
		
		request.setAttribute("invoice", invoice);
		request.setAttribute("invoiceDetails", invoiceDetails);
		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("titleName", "Xuất kho");
		request.getRequestDispatcher("./waitingInvoiceDetail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
