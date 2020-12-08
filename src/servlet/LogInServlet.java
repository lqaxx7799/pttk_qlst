package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import model.Account;
import utilities.CommonUtilities;
import viewModel.LogInViewModel;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/LogIn")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("titleName", "Log In");
		request.setAttribute("logInViewModel", new LogInViewModel());
		request.getRequestDispatcher("./logIn.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("txtEmail");
		String password = request.getParameter("txtPassword");
		LogInViewModel viewModel = new LogInViewModel(email, password);
		Account account = validate(viewModel, request);
		if (account != null) {
			HttpSession session = request.getSession();
			session.setAttribute("email", account.getEmail());
			session.setAttribute("userName", account.getUserName());
			session.setAttribute("role", account.getRole().getRoleName());

			response.sendRedirect("./Index");
		} else {
			request.setAttribute("titleName", "Log In");
			request.setAttribute("logInViewModel", viewModel);
			request.getRequestDispatcher("./logIn.jsp").forward(request, response);
		}
	}
	
	private Account validate(LogInViewModel viewModel, HttpServletRequest request) {
		boolean isValid = true;

		if (viewModel.getEmail().equals("")) {
			isValid = false;
			request.setAttribute("errEmail", "Email is required.");
		} else if (!CommonUtilities.checkEmailFormat(viewModel.getEmail())) {
			isValid = false;
			request.setAttribute("errEmail", "Wrong email format.");
		}

		if (viewModel.getPassword().equals("")) {
			isValid = false;
			request.setAttribute("errPassword", "Password is required.");
		}

		if (!isValid) {
			return null;
		}
		AccountDAO accountDAO = new AccountDAO();
		ArrayList<Account> accounts = accountDAO.getAll();
		for (Account account : accounts) {
			if (account.getEmail().equals(viewModel.getEmail())) {
				if (account.getPassword().equals(CommonUtilities.generateSHA1(viewModel.getPassword()))) {
					return account;
				}
				request.setAttribute("errPassword", "Wrong password.");
				return null;
			}
		}
		request.setAttribute("errEmail", "Email does not exist.");
		return null;
	}

}
