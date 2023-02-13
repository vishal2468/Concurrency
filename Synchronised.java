class Counter implements Runnable{
    int count;
    private Integer i=10;
    @Override
    public void run() {
        
        synchronized(i){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // can put any object here
            // can even put this , which will represent the whole object
            // lock is created out of object reference of that object
            count++;
            System.out.println(count+" "+ Thread.currentThread());
            count--;
            System.out.println(count+" "+Thread.currentThread());
        }
    }
}
public class Synchronised {
    public static void main(String[] args) {
        Counter counter=new Counter();
        new Thread(counter,"c1t1").start();
        new Thread(counter,"c1t2").start();
        new Thread(counter,"c1t3").start();
        new Thread(counter,"c1").start();
        Counter counter2=new Counter();
        new Thread(counter2,"c2").start();
    }
}
