package br.com.alura.ecommerce;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.ecommerce.dispacher.KafkaDispatcher;

public class   NewOrderServlet extends HttpServlet {

	private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

	@Override
	public void destroy() {
		super.destroy();
		orderDispatcher.close();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			var email = req.getParameter("email");

			var orderId = req.getParameter("uuid");
			var amount = new BigDecimal(req.getParameter("amount"));

			var order = new Order(orderId, amount, email);

			try(var database = new OrdersDatabase()) {
				if (database.saveNew(order)) {

					orderDispatcher.send("ECOMMERCE_NEW_ORDER", email,
							new CorrelationId(NewOrderServlet.class.getSimpleName()), order);

					resp.setStatus(HttpServletResponse.SC_OK);
					System.out.println("New order created successfully!");
					resp.getWriter().println("New order sent!");
				} else {
					System.out.println("Old order received!");
					resp.setStatus(HttpServletResponse.SC_OK);
					resp.getWriter().println("Old order received!");
				}
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
