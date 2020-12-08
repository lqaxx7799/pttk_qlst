package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ItemDAO;
import model.Cart;
import model.Item;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/AddToCart")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartServlet() {
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
		String itemIdString = request.getParameter("itemId");
		String quantityString = request.getParameter("quantity");
		ItemDAO itemDAO = new ItemDAO();
		
		int itemId = Integer.parseInt(itemIdString);
		int quantity = Integer.parseInt(quantityString);
	
		Item item = itemDAO.getById(itemId);
		HttpSession session = request.getSession();
		if (session.getAttribute("cart") == null) {
			ArrayList<Cart> carts = new ArrayList<>();
			Cart cart = new Cart();
			cart.setItem(item);
			cart.setQuantity(quantity);
			carts.add(cart);
			session.setAttribute("cart", carts);
		} else {
			@SuppressWarnings("unchecked")
			ArrayList<Cart> carts = (ArrayList<Cart>) session.getAttribute("cart");
			boolean found = false;
			for(Cart cart : carts) {
				if (cart.getItem().getId() == itemId) {
					cart.setQuantity(cart.getQuantity() + quantity);
					found = true;
					break;
				}
			}
			if (!found) {
				Cart cart = new Cart();
				cart.setItem(item);
				cart.setQuantity(quantity);
				carts.add(cart);
			}
			session.setAttribute("cart", carts);
		}
		
		System.out.println(itemId + " " + quantity);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write("{}");
	}

}
