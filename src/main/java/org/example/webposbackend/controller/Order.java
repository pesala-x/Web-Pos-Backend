package org.example.webposbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webposbackend.bo.custom.OrderBO;
import org.example.webposbackend.bo.OrderBOIMPL;
import org.example.webposbackend.dao.custom.OrderDAO;
import org.example.webposbackend.dao.OrderDAOIMPL;
import org.example.webposbackend.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/orders", loadOnStartup = 1)
public class Order extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(Order.class);
    private OrderBO orderBO;

    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/customer");
            OrderDAO orderDAO = new OrderDAOIMPL(pool);
            this.orderBO = new OrderBOIMPL(orderDAO);
        } catch (NamingException e) {
            logger.error("DB connection not initialized", e);
            throw new ServletException("DB connection not initialized", e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getContentType() == null || !request.getContentType().toLowerCase().startsWith("application/json")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (var reader = request.getReader()) {
            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO order = jsonb.fromJson(reader, OrderDTO.class);

            if (order == null || order.getItems() == null || order.getItems().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order data");
                return;
            }

            orderBO.placeOrder(order);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Order placed successfully");

        } catch (SQLException e) {
            logger.error("Error while placing order", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to place order: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to parse request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to parse request: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            List<OrderDTO> orders = orderBO.getAllOrders(); // Implement this method in your BO layer
            Jsonb jsonb = JsonbBuilder.create();
            String json = jsonb.toJson(orders);
            resp.getWriter().write(json);
        } catch (SQLException e) {
            logger.error("Error while fetching orders", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch orders: " + e.getMessage());
        }
    }
}
