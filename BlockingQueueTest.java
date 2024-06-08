import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTest {
    static BufferedReader readFile(String fileName){
        BufferedReader bf = null;
        try
        {
            bf = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bf;
    }
    static void ThreadTest() throws InterruptedException {
        Thread []threads = new Thread[10];
        for( int i=0; i < threads.length; i++) {
            threads[i] =new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Inside the Thread : " + Thread.currentThread().getName());
                }
            });
            threads[i].start();
        }
        for(Thread t : threads){
            t.join();
        }
        System.out.println("All Child thread completed. Now Main Thread is Exiting");
    }
    public static void main(String ars[]) throws InterruptedException {
        BufferedReader bf = readFile("input.txt");
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Thread producerThread = new Thread(new Producer(queue, "input.txt"));
        Thread consumerThread = new Thread(new Consumer(queue));
        System.out.println("Start the File Read and Process");
        producerThread.start();
        consumerThread.start();
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Producer implements Runnable{
    BlockingQueue<String> queue;
    private final String inputFileName;
    Producer(BlockingQueue<String> bq, String inputFileName){
        queue = bq;
        this.inputFileName = inputFileName;
    }
    public void run(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println("Push the line in Queue: " + line);
                queue.put(line);
            }
        }
        catch(Exception e){
        }
    }
}
class Product<S, I extends Number> {
    String name;
    Integer count;
    Product(String s, Integer c){
        name = s;
        count = c;
    }

    public boolean equals(Object obj){
        Product prod = (Product)obj;
        return (prod.name.equals(this.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
class Consumer implements Runnable{
    BlockingQueue<String> queue;
    Map<String, Integer> freqMap = new HashMap<>();
    PriorityQueue<Product<String, Integer>> topFive = new PriorityQueue<>((a,b) -> b.count - a.count );
    Consumer(BlockingQueue<String> bq){
        queue = bq;
    }
    public void run(){
        System.out.println("Consumer Thread Starts");
        String line;
        try {
            while ((line = queue.take()) != null) {
                //System.out.println("Pop the line from Queue : " + line);
                String[] strArr = line.trim().split(" ");
                for (String str : strArr) {
                    int prevCount = freqMap.getOrDefault(str, 0);
                    freqMap.put(str, prevCount + 1);
                    if (!topFive.isEmpty() && topFive.contains(new Product(str, prevCount))) {
                        topFive.remove(new Product(str, prevCount));
                        topFive.add(new Product(str, prevCount + 1));
                    }
                    else{
                        topFive.add(new Product(str, prevCount + 1));
                    }
                }
                System.out.println("Top Product : " + topFive.peek().name);
            }
        }catch(Exception e){

        }
    }
}