package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.InvoiceDAO;
import dao.InvoiceDetailDAO;
import dao.ItemDAO;
import model.Account;
import model.Employee;
import model.Invoice;
import model.InvoiceDetail;
import model.Item;

/**
 * Servlet implementation class CreateInvoiceServlet
 */
@WebServlet("/CreateInvoice")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class CreateInvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateInvoiceServlet() {
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
		AccountDAO accountDAO = new AccountDAO();
		CustomerDAO customerDAO = new CustomerDAO();
		ItemDAO itemDAO = new ItemDAO();
		InvoiceDAO invoiceDAO = new InvoiceDAO();
		InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
		
		String paymentMethod = request.getParameter("txtPaymentMethod");
		String receiveAddress = request.getParameter("txtReceiveAddress");
		
		String[] itemIds = request.getParameterValues("txtItemId");
		String[] quantities = request.getParameterValues("txtQuantity");
		
		System.out.println(paymentMethod + " " + receiveAddress);
		System.out.println(itemIds[0] + " " + itemIds[1]);
		System.out.println(quantities[0] + " " + quantities[1]);
		
		HttpSession session = request.getSession();
		Account account = accountDAO.getByEmail((String) session.getAttribute("email"));
		
		Invoice invoice = new Invoice();
		invoice.setCreatedTime(new Date());
		invoice.setCustomer(customerDAO.getById(account.getId()));
		invoice.setDeleted(false);
		invoice.setStatus("WAITING");
		invoice.setExportEmployee(null);
		
		int invoiceId = invoiceDAO.insert(invoice);
		invoice.setId(invoiceId);
		
		for(int i = 0; i < itemIds.length; i++) {
			int itemId = Integer.parseInt(itemIds[i]);
			int quantity = Integer.parseInt(quantities[i]);
			
			Item item = itemDAO.getById(itemId);
			
			InvoiceDetail invoiceDetail = new InvoiceDetail();
			invoiceDetail.setInvoice(invoice);
			invoiceDetail.setItem(item);
			invoiceDetail.setQuantity(quantity);
			invoiceDetail.setUnitPrice(item.getUnitPrice());
			
			invoiceDetailDAO.insert(invoiceDetail);
		}
		
		session.removeAttribute("cart");
		
		response.setContentType("application/json");
		response.getWriter().write("{}");
	}

}
