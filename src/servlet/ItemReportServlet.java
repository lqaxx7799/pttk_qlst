package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InvoiceDAO;
import dao.InvoiceDetailDAO;
import dao.ItemDAO;
import model.InvoiceDetail;
import model.Item;
import model.ItemReport;

/**
 * Servlet implementation class ItemReportServlet
 */
@WebServlet("/ItemReport")
public class ItemReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fromDateString = request.getParameter("txtFrom");
		String toDateString = request.getParameter("txtTo");
		
		if (fromDateString == null || toDateString == null) {
			request.setAttribute("titleName", "Xem báo cáo mặt hàng theo doanh thu");
			request.getRequestDispatcher("./itemReport.jsp").forward(request, response);
			return;
		}
		
		if (fromDateString.equals("")) {
			request.setAttribute("titleName", "Xem báo cáo mặt hàng theo doanh thu");
			request.setAttribute("errorMessage", "Ngày bắt đầu không được để trống");
			request.getRequestDispatcher("./itemReport.jsp").forward(request, response);
			return;
		}
		
		if (toDateString.equals("")) {
			request.setAttribute("titleName", "Xem báo cáo mặt hàng theo doanh thu");
			request.setAttribute("errorMessage", "Ngày kết thúc không được để trống");
			request.getRequestDispatcher("./itemReport.jsp").forward(request, response);
			return;
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {			
			Date fromDate = df.parse(fromDateString);
			Date toDate = df.parse(toDateString);
			
			if (toDate.before(fromDate)) {
				request.setAttribute("titleName", "Xem báo cáo mặt hàng theo doanh thu");
				request.setAttribute("errorMessage", "Ngày không hợp lệ");
				request.getRequestDispatcher("./itemReport.jsp").forward(request, response);
				return;
			}
			
			InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
			
			ArrayList<InvoiceDetail> invoiceDetails = invoiceDetailDAO.getAll();
			
			Map<Integer, ItemReport> itemReports = new LinkedHashMap<>();
			
			for(InvoiceDetail invoiceDetail : invoiceDetails) {
				if (invoiceDetail.getInvoice().getCreatedTime().after(toDate) || invoiceDetail.getInvoice().getCreatedTime().before(fromDate)) {
					break;
				}
				
				if (!itemReports.containsKey(invoiceDetail.getItem().getId())) {
					ItemReport itemReport = new ItemReport();
					itemReport.setItem(invoiceDetail.getItem());
					itemReport.setQuantity(invoiceDetail.getQuantity());
					itemReports.put(invoiceDetail.getItem().getId(), itemReport);
				} else {
					ItemReport itemReport = itemReports.get(invoiceDetail.getItem().getId());
					itemReport.setQuantity(itemReport.getQuantity() + invoiceDetail.getQuantity());
					itemReports.put(invoiceDetail.getItem().getId(), itemReport);
				}
			}
			
			ArrayList<ItemReport> result = new ArrayList<>();

			for (ItemReport e : itemReports.values()) {
				result.add(e);
			}
			
			result.sort((c1, c2) -> (int)(c2.getQuantity() * c2.getItem().getUnitPrice() - c1.getQuantity() * c1.getItem().getUnitPrice()));
			
			request.setAttribute("titleName", "Xem báo cáo mặt hàng theo doanh thu");
			request.setAttribute("itemReports", result);
			request.getRequestDispatcher("./itemReport.jsp").forward(request, response);
			
		} catch (ParseException e) {
			request.setAttribute("titleName", "Xem báo cáo mặt hàng theo doanh thu");
			request.setAttribute("errorMessage", "Ngày sai định dạng");
			request.getRequestDispatcher("./itemReport.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
