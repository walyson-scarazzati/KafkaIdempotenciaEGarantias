package br.com.alura.ecommerce;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.Source;

public class GenerateAllReportsServlet extends HttpServlet {
	
	private final KafkaDispatcher<String> batchDispatcher = new KafkaDispatcher<>();

	@Override
	public void destroy() {
		super.destroy();
		batchDispatcher.close();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
            batchDispatcher.send("ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS", "ECOMMERCE_USER_GENERATE_READING_REPORT",
                    new CorrelationId(GenerateAllReportsServlet.class.getSimpleName()),
                    "ECOMMERCE_USER_GENERATE_READING_REPORT");
			System.out.println("Send generate report to all users");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().println("Report request generated");
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
