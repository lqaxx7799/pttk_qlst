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
 * Servlet implementation class BrowseInvoiceServlet
 */
@WebServlet("/BrowseInvoice")
public class BrowseInvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseInvoiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		InvoiceDAO invoiceDAO = new InvoiceDAO();
		InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
		ArrayList<Invoice> invoices = new ArrayList<>();
		ArrayList<Float> totalMoneys = new ArrayList<>();
		if (status == null || !status.equals("WAITING")) {			
			invoices = invoiceDAO.getAll();
		} else {
			invoices = invoiceDAO.getWaiting();
		}
		
		for(Invoice invoice : invoices) {
			ArrayList<InvoiceDetail> invoiceDetails = invoiceDetailDAO.getByInvoiceId(invoice.getId());
			float money = 0;
			for(InvoiceDetail invoiceDetail : invoiceDetails) {
				money += invoiceDetail.getQuantity() * invoiceDetail.getUnitPrice();
			}
			totalMoneys.add(money);
		}
		
		request.setAttribute("invoices", invoices);
		request.setAttribute("totalMoneys", totalMoneys);
		request.setAttribute("titleName", "Duyệt đơn");
		request.getRequestDispatcher("./browseInvoice.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
