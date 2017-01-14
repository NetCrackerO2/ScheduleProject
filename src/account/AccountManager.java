package account;


import manager.GenericEntityManager;


public class AccountManager extends GenericEntityManager<Account> {
    private static AccountManager instance;

    private AccountManager() {
        super();
    }

    public static AccountManager getInstance() {
        if (instance == null)
            instance = new AccountManager();
        return instance;
    }

    @Override
    public Account newObject(int index) {
        return new AccountImpl(index);
    }
}
