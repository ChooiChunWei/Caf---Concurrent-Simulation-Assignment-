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
public class Staff extends Thread{
    String name, orderedDrink;
    JuiceFT juice;
    Cupboard cupBoard;
    Customer customer;
    Table t;
    
    int ingredient1, ingredient2;
    static int serveNum=1;
    boolean available = true, lastCallNotif = false, closingTimeNotif = false;
    static boolean closingTimeCalled = false, Homed;
    
    Staff(String name,JuiceFT juice,Cupboard cb, Table t)
    {
        this.name = name;
        this.juice = juice;
        this.cupBoard = cb;
        this.t = t;
    }
        
    public boolean takeOrder(Customer cust, String typeOfDrink)
    {
        try 
        {
            Thread.sleep(10);
            
            //When the staff's serve number match with the customer's queue number
            //and staff is available, then the staff take order from the customer
            if(serveNum == cust.queueNum && available == true)
            {
                //Staff is unavailable now
                available = false;
                
                //Taking order
                Thread.sleep(new Random().nextInt(2)*1000);
                System.out.println(cust.name + " ordered " + typeOfDrink + " from " + name + ".");
                
                //Took order
                this.customer = cust;
                this.orderedDrink = typeOfDrink;
                
                //Increase the staff's serve number
                serveNum++;
                return true;
            }  
        } catch (Exception e) {System.out.println(e.getMessage());}
        
        return false;
    }
    
    public void checkOrder()
    {
        if(orderedDrink.equals("fruit juice") )
        {
            prepareJuice(customer);   
        }else if(orderedDrink.equals("cappuccino") )
        {
            prepareCappuccino(customer);
        }
    }
    
    public void prepareJuice(Customer cust)
    {
        try
        {
            //Take glass from cupboard
            cupBoard.takeGlass(this);

            //Take juice from juice fountain tap
            int prepareJuice = juice.use(this);   
            
            //No one using the juice fountain tap
            if(prepareJuice == 1)
            {
                //Filling the juice in the glass
                System.out.println(name+" filling the glass");
                Thread.sleep((new Random().nextInt(4)+2)*1000);
                juice.stop(this);
            }else
            {
                //Someone using the juice fountain tap
                while(prepareJuice == 0)
                {
                    prepareJuice = juice.use(this);
                }
                
                //Filling the juice in the glass    
                System.out.println(name+" filling the glass");
                Thread.sleep((new Random().nextInt(4)+2)*1000);
                juice.stop(this);
            }
            
            //Serve the customer
            serve(cust);
        }catch(Exception e){System.out.println(e.getMessage());}
    }
    
    public void prepareCappuccino(Customer cust)
    {
         try
        {
            //Take cup from cupboard
            cupBoard.takeCup(this);
            
            while(true)
            {
                Thread.sleep(10);
                // Take coffee and milk from cupboard
                ingredient1 = cupBoard.takeCof(this);
                ingredient2 = cupBoard.takeMilk(this);
                    
                //'1' indicating take successfully
                //'0' indicating failed to take
                //***PRIORITY is Owner first***
                //If it is owner
                if(name.equals("Waiter"))
                {
                    if(ingredient1 == 1 && ingredient2 == 1)
                    {
                        break;
                        
                    }else if(ingredient1 == 1 && ingredient2 == 0)
                    {
                        //Put back the coffee, 
                        //and then after a while only continue
                        cupBoard.putCof(this);
                        Thread.sleep((new Random().nextInt(1)+2)*1000);
                        
                    }else if(ingredient1 == 0 && ingredient2 == 1)
                    {
                        //Put back the milk, 
                        //and then after a while only continue
                        cupBoard.putMilk(this);
                        Thread.sleep((new Random().nextInt(1)+2)*1000);
                        
                    }
                }//If it is waiter
                else if(name.equals("Owner"))
                {
                    if(ingredient1 == 1 && ingredient2 == 1)
                    {
                        break;
                        
                    }else if(ingredient1 == 1 && ingredient2 == 0)
                    {
                        while(ingredient2 == 0)
                            ingredient2 = cupBoard.takeMilk(this);
                        break;
                        
                    }else if(ingredient1 == 0 && ingredient2 == 1)
                    {
                        while(ingredient1 == 0)
                            ingredient1 = cupBoard.takeCof(this);
                        break;
                        
                    }
                }
            }
            
            //Mixing the coffee and milk
            System.out.println(name + " is mixing the drink.");
            Thread.sleep((new Random().nextInt(2)+1)*1000);
            
            //put back the coffee and milk
            cupBoard.putCof(this);
            cupBoard.putMilk(this);
            
            //Serve the customer
            serve(cust);

        }catch(Exception e){System.out.println(e.getMessage());}
    }

    public void serve(Customer cust)
    {
        System.out.println(name + " served " + cust.name + ".");
        
        //Customer drinking       
        cust.drink();
        endService(cust);
    }
    
    public void endService(Customer cust)
    {
        //Customer left
        t.left(cust);
        
        //Staff clear order, and become available
        this.orderedDrink = " ";
        available = true;
        
        //Checking if the customer is the last customer
        cust.isLastCust();
    }
    
    public void lastCall()
    {
        try
        {
            //If received last call notification, owner need to declare
            if (lastCallNotif && name.equals("Owner"))
            {
                //Stop the customer come in
                t.max = 0;
                Thread.sleep(10);
                System.out.println("---------------------------------------------------------");
                System.out.println(name + " called last order. No customer can come in anymore.");
                System.out.println("---------------------------------------------------------");

                //Turn off notification
                lastCallNotif = false;
            }
        }catch(Exception e){System.out.println(e.getMessage());}
    }
    
    public void closingTimeCall()
    {
        //Wait until all the customers left, owner only declare closing time
        if(closingTimeNotif && name.equals("Owner"))
        {
                System.out.println("--------------------------------");
                System.out.println(name + " called closing time now.");
                System.out.println("--------------------------------");
                closingTimeCalled = true;
                
                //Turn off notification
                closingTimeNotif = false;
        }
    }
    
    @Override
    public void run()
    {
        synchronized(this)
        {
            while(true)
            {
               try
               {
                    //If customer came in
                    while(t.totalCount > 0)
                    {
                        wait();
                        checkOrder();
                        notify();
                        
                        lastCall();
                    }
                    
                    //If no any customer came in
                    Thread.sleep(10);
                    lastCall();
                    closingTimeCall();

                    // If the closing time is called, waiter going home
                    if(closingTimeCalled && name.equals("Waiter"))
                    {
                        System.out.println(name + " went home.");
                        Homed = true;
                        break;
                    }
                        
                    //if waiter went home, owner clean the table, 
                    //calculate total customer served, 
                    //then close shop and went home
                    if(Homed && name.equals("Owner"))
                    {
                        System.out.println("\n----------------------------------------");
                        System.out.println("The table is clean now.");
                        System.out.println("Total number of customers served: " + (serveNum-1));
                        System.out.println("The shop is closed now!");
                        System.out.println(name + " went home.");
                        System.out.println("----------------------------------------");
                        System.exit(0);
                    }
               }catch(Exception e){System.out.println(e.getMessage());} 
            }
        }
    }
}