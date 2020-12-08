package viewModel;

public class LogInViewModel {
	private String email;
	private String password;

	public LogInViewModel(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public LogInViewModel() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
