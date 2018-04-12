package ru.kai.servlets;

import com.sun.org.apache.xpath.internal.operations.String;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kai.dao.CrudDao;
import ru.kai.dao.impl.EventDaoHibernateImpl;
import ru.kai.dao.impl.UsersDaoJdbcImpl;
import ru.kai.models.Event;
import ru.kai.models.User;
import ru.kai.utils.DatabaseHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * 14.03.2018
 * EventServlet
 *
 * Сервлет, который работает со страницей home
 *
 * @version v1.0
 */
@WebServlet("/")
public class EventServlet extends HttpServlet {


    private CrudDao<Event>  eventDao;
    @Override
    public void init() throws ServletException {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", properties.getProperty("db.url"));
        configuration.setProperty("hibernate.connection.username", properties.getProperty("db.username"));
        configuration.setProperty("hibernate.connection.password", properties.getProperty("db.password"));
        configuration.setProperty("hibernate.connection.driver_class", properties.getProperty("db.driverClassName"));
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Event.class);
        configuration.setProperty("hibernate.show_sql", "true");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();

        eventDao = new EventDaoHibernateImpl(session);
    }

    // в случае GET-запроса следует просто отдать страницу home
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Event> events = eventDao.findAll();
        req.setAttribute("events", events);
        req.getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
    }

    // обработка запроса, который должен поменять цвет заголовка
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // получаем параметр запроса
//        String color = req.getParameter("color");
        // создаем Cookie с данным значением
//        Cookie colorCookie = new Cookie("color", color);
        // кладем в ответ
//        resp.addCookie(colorCookie);
        // перенаправляем пользователя обратно на страницу home
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
