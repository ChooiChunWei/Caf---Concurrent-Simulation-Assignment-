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
public class Table {
    int totalCount = 0, max=10, seatNum=0;
    
    public boolean come(Customer cust)
    {
        synchronized(this)
        {
            if(totalCount >= max)
            {
                return false;
            }else
            {
                //Customer come in
                totalCount++;
                
                //Customer take queue number
                seatNum++;
                cust.queueNum = seatNum;
                
                //Customer take a seat
                System.out.println(cust.name + " came into the cafe.");
                System.out.println(cust.name + " take a seat. Count = " + totalCount);
            }  
        }     
       
        return true;
    }
    
    public void left(Customer cust)
    {
        synchronized(this){
            //Customer left the cafe, total customer minus one
            totalCount--;
            System.out.println(cust.name + " finished the drink and left the seat. Count = " + totalCount);
            System.out.println(cust.name + " left the cafe. ");
        }
    }
    
}
