/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Commands;

import mvc.Command;
import mvc.View;

/**
 *
 * @author Dmi3
 */
public class ViewListSubjectsCommand implements Command {

    @Override
    public void activate() {
        View.request("SUBJECT_LIST");
    }

    @Override
    public String getTitle() {
        return "CMD_SUBJECT_LIST";
    }
    
}
