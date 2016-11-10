/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

/**
 *
 * @author Александр
 */
public class RoleAssigment {
    private Account account;
    private Role role;
    public RoleAssigment(Account account, Role role){
        this.account = account;
        this.role = role;
    }
    
}
