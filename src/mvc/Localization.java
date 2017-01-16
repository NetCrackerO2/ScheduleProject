package mvc;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author AsGreyWolf
 */
public class Localization {
    private static Localization instance;
    Pattern pattern;
    private HashMap<String, String> language = new HashMap<String, String>();

    private Localization() {
        pattern = Pattern.compile("[A-Z_]+");
        language.put("GROUP_LIST", "Список групп:");
        language.put("SUBJECT_LIST", "Список предметов:");
        language.put("CATHEDRA_LIST", "Список кафедр:");
        language.put("FACULTY_LIST", "Список факультетов:");
        language.put("ACCOUNT_LIST", "Список аккаунтов:");
        language.put("ACCOUNT_LIST_NAME", "ФИО: ");
        language.put("ACCOUNT_LIST_GROUP", "Группа: ");
        language.put("ACCOUNT_LIST_CATHEDRA", "Кафедра: ");
        language.put("ACCOUNT_LIST_ROLE", "Роли: ");
        language.put("STATUS_ERROR", "Ошибка");
        language.put("STATUS_SUCCESS", "Операция завершна успешно");

        language.put("CMD_REQUEST", "Ваш выбор: ");
        language.put("ACCOUNT_REQUEST", "Введите ФИО аккаунта: ");
        language.put("GROUP_REQUEST", "Введите номер группы: ");
        language.put("CATHEDRA_REQUEST", "Введите название кафедры: ");
        language.put("FACULTY_REQUEST", "Введите номер факультета: ");
        language.put("NEW_ACCOUNT_REQUEST", "Введите ФИО нового аккаунта: ");
        language.put("NEW_GROUP_REQUEST", "Введите новый номер группы: ");
        language.put("NEW_CATHEDRA_REQUEST", "Введите новое название кафедры: ");
        language.put("NEW_FACULTY_REQUEST", "Введите новый номер факультета: ");
        language.put("NEW_SUBJECT_REQUEST", "Введите новое название предмета: ");

        language.put("CMD_QUIT", "Выход");
        language.put("CMD_ACCOUNT_BIND_TO_GROUP", "Привязать аккаунт к группе.");
        language.put("CMD_ACCOUNT_UNBIND_TO_GROUP", "Удалить аккаунт из группы.");
        language.put("CMD_ACCOUNT_BIND_TO_CATHEDRA", "Привязать аккаунт к кафедре.");
        language.put("CMD_ACCOUNT_UNBIND_TO_CATHEDRA", "Удалить аккаунт из кафедры.");
        language.put("CMD_ACCOUNT_NEW", "Добавить аккаунт.");
        language.put("CMD_ACCOUNT_LIST", "Вывод всех аккаунтов.");
        language.put("CMD_ACCOUNT_REMOVE", "Удалить аккаунт.");
        language.put("CMD_GROUP_NEW", "Добавить новую группу.");
        language.put("CMD_GROUP_EDIT", "Изменить группу.");
        language.put("CMD_GROUP_REMOVE", "Удалить группу.");
        language.put("CMD_GROUP_LIST", "Вывод всех групп.");
        language.put("CMD_FACULTY_NEW", "Добавить новый факультет.");
        language.put("CMD_FACULTY_LIST", "Вывод всех факультетов.");
        language.put("CMD_FACULTY_REMOVE", "Удалить факультет.");
        language.put("CMD_FACULTY_EDIT", "Изменить факультет.");
        language.put("CMD_CATHEDRA_NEW", "Добавить новую кафедру.");
        language.put("CMD_CATHEDRA_EDIT", "Изменить кафедру.");
        language.put("CMD_CATHEDRA_REMOVE", "Удалить кафедру.");
        language.put("CMD_CATHEDRA_LIST", "Вывод всех кафедр.");
        language.put("CMD_SUBJECT_NEW", "Добавить новый предмет.");
        language.put("CMD_SUBJECT_LIST", "Вывод всех предметов.");


        language.put("ERR_INVALID_NUMBER", "Некорректный ввод числа.");
        language.put("ERR_ELEMENT_NOT_EXISTS", "Элемент не существует.");
        language.put("ERR_ELEMENT_ALREADY_EXISTS", "Элемент уже существует.");
        language.put("ERR_ACCOUNT_ALREADY_BOUND_TO_GROUP", "Аккаунт уже привязан к группе.");
        language.put("ERR_ACCOUNT_ALREADY_BOUND_TO_CATHEDRA", "Аккаунт уже привязан к кафедре.");
        language.put("ERR_ACCOUNT_NOT_BOUND_TO_GROUP", "Аккаунт не привязан к группе.");
        language.put("ERR_ACCOUNT_NOT_BOUND_TO_CATHEDRA", "Аккаунт не привязан к кафедре.");
        language.put("ERR_ELEMENT_NOT_EDITED", "Элемент не был изменен.");
        language.put("ERR_ELEMENT_NOT_REMOVED", "Элемент не был удален.");

        language.put("CONN", "Соединение");
        language.put("CONN_CLOSED", "Закрыто.");
        language.put("CONN_INCORRECT_MESSAGE", "Принято и пропущено некорректное сообщение.");
        language.put("CONN_CLOSING_ERROR", "Ошибка закрытия соединения.");
        language.put("CONN_SEND_ERROR", "Ошибка отправки сообщения.");
        language.put("CONN_NOT_EXIST", "Такого соединения не существует.");

        language.put("SERVER_WAIT_CLIENT", "Сервер ожидает подключение клиента...");
        language.put("SERVER_CLIENT_CONNECTED", "Новый клиент подключился к серверу.");
        language.put("SERVER_CLIENT_CONNECTION_ERROR", "Произошла неудачная попытка подключения нового клиента.");
        language.put("SERVER_CLOSING_ERROR", "Ошибка закрытия сервера.");
        language.put("SERVER_INITIALIZATION_ERROR", "Ошибка инициализации сервера.");

        language.put("CLIENT_SERVER_CONNECTED", "Клиент установил соединение с сервером.");
        language.put("CLIENT_INITIALIZATION_ERROR", "Ошибка инициализации клиента.");
    }

    public static Localization getInstance() {
        if (instance == null)
            instance = new Localization();
        return instance;
    }

    public String getString(String key) {
        Matcher matcher = pattern.matcher(key);
        int pos = 0;
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String found = matcher.group();
            if (language.containsKey(found)) {
                result.append(key.substring(pos, matcher.start()));
                result.append(language.get(found));
                pos = matcher.end();
            }
        }
        result.append(key.substring(pos, key.length()));
        return result.toString();
    }
}
