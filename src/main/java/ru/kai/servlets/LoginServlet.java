package ru.kai.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kai.dao.UsersDao;
import ru.kai.dao.impl.UsersDaoJdbcImpl;
import ru.kai.utils.DatabaseHelper;
import ru.kai.utils.SecurityHelper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 14.03.2018
 * LoginServlet
 * Сервлет, позволяющий польователю войти в систему
 *
 * @version v1.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        DriverManagerDataSource dataSource = DatabaseHelper.getDataSource(getServletContext());
        usersDao = new UsersDaoJdbcImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // вытаскиваем из запроса имя пользователя и его пароль
        String name = req.getParameter("login");
        String password = req.getParameter("password");
        Integer id = usersDao.isExist(name, password);
        // если пользователь есть в системе
        if (id > 0) {
            SecurityHelper.login(req, id);
            // перенаправляем на страницу home
            req.getServletContext().getRequestDispatcher("/").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

    }
}
