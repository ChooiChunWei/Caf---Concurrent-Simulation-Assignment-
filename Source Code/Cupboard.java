/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author User
 */
public class Cupboard {
    Lock lock = new ReentrantLock();
    Lock lock1 = new ReentrantLock();
    Boolean r;
        
    synchronized public int takeCof(Staff b)
    {
        try
        {
            r = lock.tryLock();
            if(r == true)
            {
                Thread.sleep((new Random().nextInt(2)+1)*1000);
                System.out.println(b.name + " take the coffee.");
                return 1;
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return 0;
    }
    
    synchronized public void putCof(Staff b) 
    {
        try
        {
            Thread.sleep((new Random().nextInt(2)+1)*500);
            System.out.println(b.name+" put back the coffee.");
            lock.unlock();
        }catch(Exception e){System.out.println(e.getMessage());}
    }
    
    synchronized public int takeMilk(Staff b)
    {
        try
        {
            r = lock1.tryLock();
            if(r == true)
            {
                Thread.sleep((new Random().nextInt(2)+1)*1000);
                System.out.println(b.name + " take the milk.");
                return 1;
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return 0;
    }
    
    synchronized public void putMilk(Staff b) 
    {
        try
        {
            Thread.sleep((new Random().nextInt(2)+1)*500);
            System.out.println(b.name+" put back the milk.");
            lock1.unlock();
        }catch(Exception e){System.out.println(e.getMessage());}
    }
    
    synchronized public void takeGlass(Staff b)
    {
        try 
        {
            Thread.sleep((new Random().nextInt(2)+1)*500);
            System.out.println(b.name + " took a glass.");
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
    
    synchronized public void takeCup(Staff b)
    {
        try 
        {
            Thread.sleep((new Random().nextInt(2)+1)*500);
            System.out.println(b.name + " took a cup.");
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
}
