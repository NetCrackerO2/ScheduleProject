package mvc;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AsGreyWolf
 */
public class Localization {
    private static Localization instance;
    private HashMap<String, String> language = new HashMap<String, String>();
    Pattern pattern;

    private Localization() {
        pattern = Pattern.compile("[A-Z_]+");
        language.put("GROUP_LIST", "Список групп:");
        language.put("SUBJECT_LIST", "Список предметов:");
        language.put("CATHEDRA_LIST", "Список кафедр:");
        language.put("FACULTY_LIST", "Список факультетов:");
        language.put("ACCOUNT_LIST", "Список аккаунтов:");
        language.put("ACCOUNT_LIST_NAME", "ФИО: ");
        language.put("ACCOUNT_LIST_GROUP", "Группа: ");
        language.put("STATUS_ERROR", "Ошибка");
        language.put("STATUS_SUCCESS", "Операция завершна успешно");

        language.put("CMD_REQUEST", "Ваш выбор: ");
        language.put("ACCOUNT_REQUEST", "Введите ФИО аккаунта: ");
        language.put("GROUP_REQUEST", "Введите номер группы: ");
        language.put("CATHEDRA_REQUEST", "Введите название кафедры: ");
        language.put("FACULTY_REQUEST", "Введите номер факультета: ");
        language.put("NEW_ACCOUNT_NAME_REQUEST", "Введите ФИО нового аккаунта: ");
        language.put("NEW_GROUP_REQUEST", "Введите номер новой группы: ");
        language.put("NEW_CATHEDRA_REQUEST", "Введите название новой кафедры: ");
        language.put("NEW_FACULTY_REQUEST", "Введите номер нового факультета: ");
        language.put("NEW_SUBJECT_REQUEST", "Введите название нового предмета: ");

        language.put("CMD_QUIT", "Выход");
        language.put("CMD_ACCOUNT_BIND_TO_GROUP", "Привязать аккаунт к группе.");
        language.put("CMD_NEW_ACCOUNT", "Добавить аккаунт.");
        language.put("CMD_NEW_GROUP", "Добавить новую группу.");
        language.put("CMD_NEW_FACULTY", "Добавить новый факультет.");
        language.put("CMD_NEW_CATHEDRA", "Добавить новую кафедру.");

        language.put("ERR_INVALID_NUMBER", "Некорректный ввод числа.");
        language.put("ERR_ELEMENT_NOT_EXISTS", "Элемент не существует.");
        language.put("ERR_ELEMENT_ALREADY_EXISTS", "Элемент уже существует.");
        language.put("ERR_ACCOUNT_ALREADY_BOUND_TO_GROUP", "Аккаунт уже привязан к группе.");
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
