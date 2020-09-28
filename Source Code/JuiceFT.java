/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author User
 */
public class JuiceFT {
    Lock loc=new ReentrantLock();
    boolean r;   
    
    public int use(Staff b)
    {
        r=loc.tryLock();
        if(r == true)
        {
            System.out.println(b.name+" started to use fruit juice fountain tap");
            return 1;
        }
        return 0;
    }
    
    public void stop(Staff b)
    {
        System.out.println(b.name+" finished using fruit juice fountain tap");
        loc.unlock();
    }
}
