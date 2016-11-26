import account.Account;
import account.AccountManager;
import account.Permission;
import account.RoleManager;


public class Main {
	public static void main(String[] args) {
		AccountManager accountManager = AccountManager.getInstance();
		RoleManager roleManager = RoleManager.getInstance();

		Account account = accountManager.getNewAccount("This Is ACCOUNT");

		roleManager.createRole("Role#1");
		roleManager.getRole("Role#1").addPermissions(Permission.Student);
		roleManager.createRole("Role#2");
		roleManager.getRole("Role#2").addPermissions(Permission.NoStudent);
		roleManager.createRole("Role#3");

		roleManager.addRole(account, roleManager.getRole("Role#1"));

		System.out.println(roleManager.hasRole(account, "Role#1"));
		System.out.println(roleManager.hasRole(account, "Role#2"));
		System.out.println(roleManager.hasPermission(account, Permission.Student));
		System.out.println(roleManager.hasPermission(account, Permission.NoStudent));
	}
}
