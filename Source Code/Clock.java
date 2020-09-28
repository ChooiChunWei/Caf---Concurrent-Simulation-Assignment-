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
public class Clock{
    long pastTimeMillis,startTime;
    float pastTimeMin;
    Staff owner;
     
    Clock(Staff owner)
    {
        //Get the starting time for the clock
        long start = System.currentTimeMillis();
        this.startTime = start;
        this.owner = owner;
    }
    
    public void lastCallNotif(Staff owner)
    {
        //Send last call notification to the owner
        System.out.println("---------------------------------------");
        System.out.println("Clock send the last order notification.");
        System.out.println("---------------------------------------");
        owner.lastCallNotif = true;
    }
    
    public void closingTimeNotif(Staff owner)
    {
        //Send closing time notification to the owner
        System.out.println("-----------------------------------------");
        System.out.println("Clock send the closing time notification.");
        System.out.println("-----------------------------------------");
        owner.closingTimeNotif = true;
    }
    
    public void start()
    {
        while(true)
        {
            //Past time in milliseconds = Current time - Start time
            pastTimeMillis = System.currentTimeMillis()-startTime;
            //Convert milliseconds to minute
            pastTimeMin = pastTimeMillis/(60*1000F);
            
            //If the time exceed 0.5 minute(30 seconds), send last call notification
            if(pastTimeMin > 0.5)
            {
                lastCallNotif(owner);
                break;
            }
        }
        
        while(true)
        {            
            //Past time in milliseconds = Current time - Start time
            pastTimeMillis = System.currentTimeMillis()-startTime;
            //Convert milliseconds to minute
            pastTimeMin = pastTimeMillis/(60*1000F);
            
            //After 10 minutes from last call time, then send closing time notification
            if(pastTimeMin > 0.7)
            {
                closingTimeNotif(owner);
                break;
            }
        }
    }
}
