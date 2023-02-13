class Counter implements Runnable{
    int count;
    private Integer i=10;
    @Override
    public void run() {
        synchronized(i){
            // can put any object here
            // can even put this , which will represent the whole object
            // lock is created out of object reference of that object
            count++;
            System.out.println(count);
            count--;
            System.out.println(count);
        }
    }
}
public class Synchronised {
    public static void main(String[] args) {
        Counter counter=new Counter();
        new Thread(counter).start();
        new Thread(counter).start();
        new Thread(counter).start();
        new Thread(counter).start();
    }
}
