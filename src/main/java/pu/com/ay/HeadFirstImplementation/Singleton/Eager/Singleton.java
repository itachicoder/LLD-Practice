package pu.com.ay.HeadFirstImplementation.Singleton.Eager;

public class Singleton {

    public static Singleton instance;
 // not thread Safe
    private Singleton(){
        System.out.println("SingleTon Defined");
    }

    public static Singleton getInstance(){
        if (instance ==null){
            instance = new Singleton();
        }
        return instance;
    } 
    
}
