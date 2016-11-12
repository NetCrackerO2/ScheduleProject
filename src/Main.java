import account.Account;
import account.AccountManager;
import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.Faculty;
import faculty.FacultyImpl;
import group.Group;
import group.GroupsManager;
import subject.Subject;
import subject.SubjectManager;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        CathedraManager cathedraManager = CathedraManager.getInstance();
        GroupsManager groupsManager = GroupsManager.getInstance();
        AccountManager accountManager = AccountManager.getInstance();
        SubjectManager subjectManager = SubjectManager.getInstance();

        Faculty faculty1, faculty2;
        faculty1 = new FacultyImpl(1);
        faculty2 = new FacultyImpl(2);

        // Распределяем кафедры по факультетам
        Cathedra cathedra1, cathedra2, cathedra3;
        cathedra1 = cathedraManager.addNewCathedra(faculty1, "Phisics");
        cathedra2 = cathedraManager.addNewCathedra(faculty2, "IT");
        cathedra3 = cathedraManager.addNewCathedra(faculty2, "Garbage");

        // Распределяем группы по факультетам и кафедрам
        Group[] groups = new Group[10];
        for (int i = 0; i < 10; i++)
            groups[i] = groupsManager.getNewGroup(
                    i % 2 == 0 ? faculty1 : faculty2,
                    i % 2 == 0 ? cathedra2 : cathedra1,
                    i + 1000
            );

        // Распределяем аккаунты по группам
        Account[] accounts = new Account[10];
        for (int i = 0; i < 10; i++)
            accounts[i] = accountManager.getNewAccount("" + i, i < 5 ? groupsManager.getGroup(1003) : groupsManager.getGroup(1004));

        // Распределяем предметы по кафедрам
        Subject[] subjects = new Subject[10];
        for (int i = 0; i < 10; i++)
            subjects[i] = subjectManager.getNewSubject(i < 5 ? cathedra1 : cathedra2, "" + i);

        // Тестируем группы ///////////////////////////////////////////////
        // Вывод всех групп
        System.out.println("Группы из факультета №1:");
        groupsManager.getGroupsByFaculty(faculty1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        System.out.println("Группы из факультета №2:");
        groupsManager.getAllGroups().stream().filter(
                currentGroup -> currentGroup.getFaculty().getNumber() == 2
        ).forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        // Получение групп по кафедре
        System.out.println("Группы из кафедры \"" + cathedra1.getName() + "\":");
        groupsManager.getGroupsByCathedra(cathedra1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        System.out.println("Группы из кафедры \"" + cathedra2.getName() + "\":");
        groupsManager.getGroupsByCathedra(cathedra2).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        // Удаление групп
        System.out.println("Удаляем группу \"1000\"...");
        groupsManager.removeGroup(1000);
        System.out.println("Удаляем группу \"1006\"...");
        groupsManager.removeGroup(groups[6]);
        System.out.println("Группы из факультета №1");
        groupsManager.getGroupsByFaculty(faculty1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        System.out.println("Удаляем группу \"1003\"...");
        groupsManager.removeGroup(1003);
        System.out.println("Группы из кафедры \"" + cathedra1.getName() + "\":");
        groupsManager.getGroupsByCathedra(cathedra1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");
        //////////////////////////////////////////////////////////////////////////////



        // Тестируем кафедры /////////////////////////////////////////////////////////
        // Получение всех кафедр
        System.out.println("Все кафедры:");
        cathedraManager.getAllCathedra().stream().forEach(
                (needCathedra) -> System.out.println(needCathedra.getName())
        );
        System.out.println("");

        // Получение кафедр по факультету
        System.out.println("Кафедры из факультета №1:");
        cathedraManager.getListCathedra(faculty1).stream().forEach(
                (needCathedra) -> System.out.println(needCathedra.getName())
        );
        System.out.println("");

        System.out.println("Кафедры из факультета №2:");
        cathedraManager.getListCathedra(faculty2).stream().forEach(
                (needCathedra) -> System.out.println(needCathedra.getName())
        );
        System.out.println("");

        // Удаление кафедр
        System.out.println("Удаляем кафедру " + cathedra3.getName() + "...");
        cathedraManager.removeCathedra(cathedra3);
        System.out.println("Кафедры из факультета №2:");
        cathedraManager.getListCathedra(faculty2).stream().forEach(
                (needCathedra) -> System.out.println(needCathedra.getName())
        );
        System.out.println("");

        // Получение предмета по кафедре и имени
        System.out.print("Получаем кафедру \"" + cathedra2.getName() + "\" по имени: ");
        System.out.println(cathedraManager.getCathedra(cathedra2.getFaculty(), cathedra2.getName()).getName());
        System.out.println("");
        //////////////////////////////////////////////////////////////////////////////




        // Тестируем аккаунты ////////////////////////////////////////////////////////
        // Вывод всех аккаунтов
        System.out.println("Все аккаунты:");
        accountManager.getAllAccounts().stream().forEach(
                (needAccount) -> System.out.println(needAccount.getName())
        );
        System.out.println("");

        // Получение аккаунтов по группе
        System.out.println("Аккаунты из группы \"" + groups[3].getNumber() + "\":");
        accountManager.getAccountsByGroup(groups[3]).stream().forEach(
                (needAccount) -> System.out.println(needAccount.getName())
        );
        System.out.println("");

        System.out.println("Аккаунты из группы \"" + groups[4].getNumber() + "\":");
        accountManager.getAccountsByGroup(groups[4]).stream().forEach(
                (needAccount) -> System.out.println(needAccount.getName())
        );
        System.out.println("");

        // Удаление аккаунтов
        System.out.println("Удаляем аккаунт \"" + accounts[3].getName() + "\"");
        accountManager.removeAccount(accounts[3]);
        System.out.println("Все аккаунты:");
        accountManager.getAllAccounts().stream().forEach(
                (needAccount) -> System.out.println(needAccount.getName())
        );
        System.out.println("");

        // Получение группы аккаунты
        System.out.print("Группа аккаунта \"" + accounts[5].getName() + "\": ");
        System.out.println(accounts[5].getGroup().getNumber());
        System.out.println("");

        // Получение аккаунта по группе и имени
        System.out.print("Получаем аккаунт \"" + accounts[7].getName() + "\" по имени: ");
        System.out.println(accountManager.getAccount(accounts[7].getName(), accounts[7].getGroup()).getName());
        System.out.println("");
        //////////////////////////////////////////////////////////////////////////////
        ////////////// ////////////// ///////////// /////////// ///////////// /////////


        // Тестируем предметы ////////////////////////////////////////////////////////
        // Вывод всех предметов
        System.out.println("Все предметы:");
        subjectManager.getAllSubjects().stream().forEach(
                (needSubject) -> System.out.println(needSubject.getName())
        );
        System.out.println("");

        // Получение предметов по кафедре
        System.out.println("Предметы из кафедры \"" + cathedra1.getName() + "\":");
        subjectManager.getSubjectsByCathedra(cathedra1).stream().forEach(
                (needSubject) -> System.out.println(needSubject.getName())
        );
        System.out.println("");

        System.out.println("Предметы из кафедры \"" + cathedra2.getName() + "\":");
        subjectManager.getSubjectsByCathedra(cathedra2).stream().forEach(
                (needSubject) -> System.out.println(needSubject.getName())
        );
        System.out.println("");

        // Удаление предметов
        System.out.println("Удаляем предмет \"" + subjects[3].getName() + "\"");
        subjectManager.removeSubject(subjects[3]);
        System.out.println("Все предметы:");
        subjectManager.getAllSubjects().stream().forEach(
                (needSubject) -> System.out.println(needSubject.getName())
        );
        System.out.println("");

        // Получение кафедры предмета
        System.out.print("Кафедра предмета \"" + subjects[5].getName() + "\": ");
        System.out.println(subjects[5].getCathedra().getName());
        System.out.println("");

        // Получение предмета по кафедре и имени
        System.out.print("Получаем предмет \"" + subjects[7].getName() + "\" по имени: ");
        System.out.println(subjectManager.getSubject(cathedra2, "7").getName());
        System.out.println("");
        //////////////////////////////////////////////////////////////////////////////


        Command[] commands = new Command[1];
        commands[0] = new ViewListGroupsCommand();

        Scanner s = new Scanner(System.in);
        commands[s.nextInt()].activate();
    }
}
