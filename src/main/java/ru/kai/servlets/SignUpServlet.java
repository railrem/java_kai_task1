package ru.kai.servlets;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kai.dao.UsersDao;
import ru.kai.dao.impl.UsersDaoJdbcTemplateImpl;
import ru.kai.models.User;
import ru.kai.utils.DatabaseHelper;
import ru.kai.utils.SecurityHelper;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 14.03.2018
 * SignUpServlet
 * <p>
 * Сервлет для регистрации
 *
 * @version v1.0
 */
@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        DriverManagerDataSource dataSource = DatabaseHelper.getDataSource(getServletContext());
        usersDao = new UsersDaoJdbcTemplateImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/signUp.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String plainPassword = req.getParameter("password");
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        // создали пользователя и сохранили его в хранилище
        User user = User.builder()
                .name(req.getParameter("name"))
                .login(req.getParameter("login"))
                .surname(req.getParameter("surname"))
                .passwordHash(hashedPassword)
                .build();
        Integer id = usersDao.save(user);
        if (id > 0) {
            SecurityHelper.login(req, id);
            // перенаправляем на страницу home
            req.getServletContext().getRequestDispatcher("/").forward(req, resp);
        } else {
            throw new ServletException("User didn't save");
        }
    }
}
