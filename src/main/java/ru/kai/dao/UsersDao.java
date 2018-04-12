package ru.kai.dao;

import ru.kai.models.User;
import java.util.List;

/**
 * 14.03.2018
 * UsersRepository
 *
 * Интерфейс, описывающий поведение объекта, предоставляющего доступ к данным (паттерн DAO)
 *
 * @version v1.0
 */
public interface UsersDao extends CrudDao<User>  {

    Integer isExist(String login, String password);

    List<User> findAllByLogin(String login);
}
