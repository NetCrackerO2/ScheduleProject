package account;


import group.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * @author Alexander
 */
public class AccountManager {
    private static AccountManager instance;
    private List<Account> list = new ArrayList<Account>();

    private AccountManager() {
    }

    /**
     * Singletone instance
     *
     * @return new accountManager if called before, buffered one otherwise
     */
    public static AccountManager getInstance() {
        if (instance == null)
            instance = new AccountManager();
        return instance;
    }

    /**
     * Factory method to get new subject instance
     *
     * 
     * @param name
     * @param group
     * @return account instance
     */
    public Account getNewAccount(String name, Group group) {
        if (list.stream().anyMatch(current -> current.getName().equals(name) && current.getGroup().equals(group)))
            throw new IllegalArgumentException("Account already exists");
        Account result = new Account(name, group);
        list.add(result);
        return result;
    }

    /**
     * Remove subject from Manager
     *
     * @param subject
     */
    public void removeAccount(Account account) {
        if (!list.remove(account))
            throw new NoSuchElementException("Account not found!");
    }

    /**
     * Get already generated account by name and group
     *
     * 
     * @param name
     * @param group
     * @return subject instance
     */
    public Account getAccount(String name, Group group) {
        for (Account current : list)
            if (current.getGroup().equals(group) && current.getName().equals(name))
                return current;
        throw new NoSuchElementException("Account not found!");
    }

    /**
     * Get account list by group
     *
     * @param group
     * @return list of subject
     */
    public List<Account> getAccountsByGroup(Group group) {
        return list.stream().filter(current -> current.getGroup().equals(group)).collect(Collectors.toList());
    }

    /**
     * Get all account instances
     *
     * @return list of subject
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<Account>(list);
    }
}