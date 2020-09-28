/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.Random;

/**
 *
 * @author User
 */
public class Customer extends Thread {
    int queueNum;
    String name;
    boolean enterFulled = false, Ordered = false;
    int typeOfDrink;
    
    Table t;
    Staff owner,waiter;
    
    Customer(String name, Table t,Staff owner, Staff waiter) {
        this.name = name;
        this.t = t;
        this.owner = owner;
        this.waiter = waiter;
    }
    
    public void drink()
    {
        try
        {
            System.out.println(name + " is drinking.");
            Thread.sleep((new Random().nextInt(2)+1)*1000);
        }catch(Exception e){System.out.println(e.getMessage());}
    }
    
    public boolean isLastCust()
    {
        if(t.totalCount == 0)
        {
            synchronized(owner)
            {
                owner.notify();
            }
            
            synchronized(waiter)
            {
                waiter.notify();
            }
            return true;
        }                
        return false;
    }
    
    @Override
    public void run() {
        try 
        {
            //When the seat not occupied,then come in
            while (enterFulled != true) {
                enterFulled = t.come(this);
            }
            
            //0 = Fruit Juice, 1,2 = Cappuccino
            typeOfDrink= new Random().nextInt(2);
            
            while(true)
            {
                Thread.sleep(10);
                
                //Cappuccino
                if(typeOfDrink != 0)
                { 
                    //if owner available, then take order
                    if (owner.available == true) 
                    {       
                        Ordered = owner.takeOrder(this,"cappuccino");
                        
                        if(Ordered == true)
                        {
                            synchronized(owner)
                            {
                                owner.notify();
                                while(true)
                                {
                                    if(owner.available == false)
                                    {
                                        owner.wait();
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    //if waiter available, then take order
                    }else if(waiter.available == true)
                    {
                        Ordered = waiter.takeOrder(this,"cappuccino");
                        
                        if(Ordered == true){
                        synchronized(waiter)
                        {
                            waiter.notify();
                            while(true)
                                {
                                    if(waiter.available == false)
                                    {
                                        waiter.wait();
                                    }else{
                                        break;
                                    }
                                }
                                break;
                        }}
                    }       
                } 
                else //fruit juice
                { 
                    if (owner.available == true) 
                    {                                          
                        Ordered = owner.takeOrder(this,"fruit juice");
                        if(Ordered == true){
                        synchronized(owner)
                        {
                            owner.notify();
                            while(true)
                                {
                                    if(owner.available == false)
                                    {
                                        owner.wait();
                                    }else{
                                        break;
                                    }
                                }
                                break;
                        }}
                        
                    }else if(waiter.available == true)
                    {
                        Ordered = waiter.takeOrder(this,"fruit juice");
                        if(Ordered == true)
                        {
                            synchronized(waiter)
                            {
                                waiter.notify();
                                while(true)
                                {
                                    if(waiter.available == false)
                                    {
                                        waiter.wait();
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
}
