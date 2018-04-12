package ru.kai.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityHelper {
    
    public static void login(HttpServletRequest req,Integer id){
        // создаем для него сессию
        HttpSession session = req.getSession();
        // кладем в атрибуты сессии атрибут user с именем пользователя
        session.setAttribute("user_id", id);
    }
}
