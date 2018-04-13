/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingbai5.Event;

import swingbai5.databases.xulyDB;

/**
 *
 * @author Admin
 */
public interface LoginEventListenner {
    public void onLogin(xulyDB db);
}
