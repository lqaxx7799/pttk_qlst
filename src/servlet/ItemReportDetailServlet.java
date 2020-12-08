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
import dao.ItemDAO;
import model.Invoice;
import model.InvoiceDetail;
import model.Item;

/**
 * Servlet implementation class ItemReportDetailServlet
 */
@WebServlet("/ItemReportDetail")
public class ItemReportDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemReportDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int itemId = Integer.parseInt(request.getParameter("id"));
		
		InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
		InvoiceDAO invoiceDAO = new InvoiceDAO();
		ItemDAO itemDAO = new ItemDAO();
		
		Item item = itemDAO.getById(itemId);
		
		ArrayList<Invoice> invoices = invoiceDAO.getAll();
		
		ArrayList<Invoice> details = new ArrayList<>();
		ArrayList<Integer> quantities = new ArrayList<>();
		
		for(Invoice invoice : invoices) {
			ArrayList<InvoiceDetail> invoiceDetails = invoiceDetailDAO.getByInvoiceId(invoice.getId());
			for(InvoiceDetail invoiceDetail : invoiceDetails) {
				if (invoiceDetail.getItem().getId() == itemId) {
					details.add(invoice);
					quantities.add(invoiceDetail.getQuantity());
				}
				break;
			}
		}
		
		request.setAttribute("titleName", "Xem chi tiết báo cáo");
		request.setAttribute("item", item);
		request.setAttribute("invoices", details);
		request.setAttribute("quantities", quantities);
		request.getRequestDispatcher("./itemReportDetail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
