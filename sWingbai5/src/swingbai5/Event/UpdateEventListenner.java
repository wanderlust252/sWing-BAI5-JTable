/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingbai5.Event;

/**
 *
 * @author Admin
 */
public interface UpdateEventListenner {
    public void onUpdate(int row,String[] Old,String[] New);
}
