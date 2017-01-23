package account;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cathedra.CathedraManager;
import group.GroupManager;
import manager.GenericEntityManager;

public class AccountManager extends GenericEntityManager<Account> {
    private static volatile AccountManager instance;

    private AccountManager() {
        super();
    }

    public static AccountManager getInstance() {
        if (instance == null)
            synchronized (AccountManager.class) {
                if (instance == null)
                    instance = new AccountManager();
            }
        return instance;
    }

    @Override
    public Account newObject(int index) {
        return new AccountImpl(index);
    }

    public List<Account> find(AccountSelector selector) {
        return selector.process(getAllObjects());
    }

    public static class AccountSelector {
        protected AccountSelector() {
        }

        public List<Account> process(List<Account> input) {
            return input;
        }

        public AccountSelector and(AccountSelector second) {
            return new And(this, second);
        }

        public AccountSelector or(AccountSelector second) {
            return new Or(this, second);
        }
    }

    public static class NameIs extends AccountSelector {
        private String s;

        public NameIs(String query) {
            s = query;
        }

        @Override
        public List<Account> process(List<Account> input) {
            if (s == null)
                return input;
            return input.stream().filter(a -> validate(a.getName(), s)).collect(Collectors.toList());
        }
    }

    /**
     * Selects accounts with cathedra specified WARNING: locks cathedra manager
     * 
     * @author asgreywolf
     *
     */
    public static class CathedraIs extends AccountSelector {
        private String s;

        public CathedraIs(String query) {
            s = query;
        }

        @Override
        public List<Account> process(List<Account> input) {
            if (s == null)
                return input;
            synchronized (CathedraManager.getInstance()) {
                return input.stream().filter(new Predicate<Account>() {
                    @Override
                    public boolean test(Account t) {
                        int id = t.getCathedraIndex();
                        if (id < 0)
                            return false;
                        return validate(CathedraManager.getInstance().getObject(id).getName(), s);
                    }
                }).collect(Collectors.toList());
            }
        }
    }

    /**
     * Selects accounts with group specified WARNING: locks group manager
     * 
     * @author asgreywolf
     *
     */
    public static class GroupIs extends AccountSelector {
        private String s;

        public GroupIs(String query) {
            s = query;
        }

        @Override
        public List<Account> process(List<Account> input) {
            if (s == null)
                return input;
            synchronized (GroupManager.getInstance()) {
                return input.stream().filter(new Predicate<Account>() {
                    @Override
                    public boolean test(Account t) {
                        int id = t.getGroupIndex();
                        if (id < 0)
                            return false;
                        return validate(GroupManager.getInstance().getObject(id).getNumber() + "", s);
                    }
                }).collect(Collectors.toList());
            }
        }
    }

    private static class And extends AccountSelector {
        private AccountSelector a, b;

        public And(AccountSelector a, AccountSelector b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public List<Account> process(List<Account> input) {
            if (a == null && b == null)
                return input;
            if (a == null)
                return b.process(input);
            if (b == null)
                return a.process(input);
            return b.process(a.process(input));
        }
    }

    private static class Or extends AccountSelector {
        private AccountSelector a, b;

        public Or(AccountSelector a, AccountSelector b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public List<Account> process(List<Account> input) {
            if (a == null || b == null)
                return input;
            List<Account> buffer = a.process(input);
            return b.process(input).stream().filter(buffer::contains).collect(Collectors.toList());
        }
    }

    private static boolean validate(String data, String query) {
        int d_l = 0;
        int d_r = data.length() - 1;
        int q_l = 0;
        int q_r = query.length() - 1;
        while (q_l < query.length() && query.charAt(q_l) != '*') {
            if (d_l >= data.length())
                return false;
            if (data.charAt(d_l) != query.charAt(q_l) && query.charAt(q_l) != '.') {
                return false;
            }
            d_l++;
            q_l++;
        }
        while (q_r >= 0 && query.charAt(q_r) != '*') {
            if (d_r < 0)
                return false;
            if (data.charAt(d_r) != query.charAt(q_r) && query.charAt(q_r) != '.') {
                return false;
            }
            d_r--;
            q_r--;
        }
        if (q_l == query.length()) {
            if (d_l == data.length())
                return true;
            else
                return false;
        }
        if (d_l > d_r + 1)
            return false;
        if (q_l == q_r)
            return true;
        String[] subqueries = query.substring(q_l + 1, q_r).split("\\*");
        String dataM = data.substring(d_l, d_r + 1);
        d_l = 0;
        for (String subquery : subqueries) {
            int found = indexOf(dataM, subquery, d_l);
            if (found == -1)
                return false;
            d_l = found + subquery.length();
        }
        return true;
    }

    /**
     * indexOf with target "." pattern support. Patched by AsGreyWolf. Copyright
     * 1994-2006 Sun Microsystems, Inc. All Rights Reserved. GPLv2
     */
    private static int indexOf(String source, String target, int fromIndex) {
        int sourceCount = source.length();
        int targetCount = target.length();
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = target.charAt(0);
        int max = (sourceCount - targetCount);

        for (int i = fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source.charAt(i) != first && first != '.') {
                while (++i <= max && source.charAt(i) != first)
                    ;
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = 1; j < end && (source.charAt(j) == target.charAt(k) || target.charAt(k) == '.'); j++, k++)
                    ;

                if (j == end) {
                    /* Found whole string. */
                    return i;
                }
            }
        }
        return -1;
    }
}
