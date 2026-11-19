package murach.cart;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import murach.business.Cart;
import murach.business.Product;
import murach.data.ProductDB;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private Cart getCart(HttpSession session) {
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            return cart;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = getCart(session);
        synchronized (session) {
            request.setAttribute("message", session.getAttribute("cartMessage"));
            session.removeAttribute("cartMessage");
        }
        synchronized (cart) {
            request.setAttribute("items", cart.getItems());
            request.setAttribute("cartTotal", cart.getTotalCurrencyFormat());
        }
        String action = request.getParameter("action");
        String view = "/WEB-INF/cart.jsp";

        if ("checkout".equals(action)) {
            view = "/WEB-INF/checkout.jsp";
        }

        request.getRequestDispatcher(view).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Cart cart = getCart(session);
        String action = request.getParameter("action");
        String code = request.getParameter("productCode");
        Product product = ProductDB.selectProduct(code);
        try {
            if (product == null) {
                throw new IllegalArgumentException("Please select a valid CD.");
            }

            if ("add".equals(action)) {
                cart.addItem(product);
            } else if ("update".equals(action)) {
                String quantityString = request.getParameter("quantity");
                int quantity = Integer.parseInt(quantityString);
                cart.updateItem(code, quantity);
            } else if ("remove".equals(action)) {
                cart.removeItem(code);
            } else {
                throw new IllegalArgumentException("Unknown cart action.");
            }
        } catch (NumberFormatException ex) {
            session.setAttribute("cartMessage", "Enter a whole number between 1 and 999.");
        } catch (IllegalArgumentException ex) {
            session.setAttribute("cartMessage", ex.getMessage());
        }
        String url = request.getContextPath() + "/cart";
        response.sendRedirect(response.encodeRedirectURL(url));
    }
}
