/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;


/**
 *
 * @author User
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JuiceFT juice = new JuiceFT();
        Cupboard cupBoard = new Cupboard();
        Table t = new Table();
        Staff owner = new Staff("Owner", juice, cupBoard,t);
        Staff waiter = new Staff("Waiter", juice, cupBoard,t);
        owner.start();
        waiter.start();
        
        Clock clock = new Clock(owner);

        Customer cust[] = new Customer[50];
        for (int i = 0; i < cust.length; i++) 
        {
            cust[i] = new Customer("C" + Integer.toString(i), t,owner,waiter);
            cust[i].start();
        }
        clock.start();
    }
}
