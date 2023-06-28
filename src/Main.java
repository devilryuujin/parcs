import parcs.*;
import java.util.*;
import java.io.*;

public class Main implements AM {
    public static void main(String[] args){
        System.out.print("class Main start method main\n");	
        task mainTask = new task();
        mainTask.addJarFile("Algorithm.jar");
        mainTask.addJarFile("Main.jar");
        System.out.print("class Main method main added jars\n");
        (new Main()).run(new AMInfo(mainTask, (channel)null));
        System.out.print("class Main method main finished work\n");
        mainTask.end();
    }
    
    @Override
    public void run(AMInfo info){
        long number, iters;
        int threads;
        try
        {
        	BufferedReader in = new BufferedReader(new FileReader(info.curtask.findFile("input.txt")));
            number = Long.parseLong(in.readLine());
            iters = Long.parseLong(in.readLine());
            threads = Integer.parseInt(in.readLine());
        }
        catch (IOException e)
        {
            System.out.print("Error while reading input\n");
            return;
        }
        System.out.print("class Main method run read data from file\nNumber = " + number + "\nIterations = " + iters + "\nThreads = " + threads + "\n");
        long tStart = System.nanoTime();
        boolean res = solve(info, number, iters, threads);
        long tEnd = System.nanoTime();
        if (!res){
            System.out.println("The number " + number + " is COMPOSITE");
        }
        else{
            System.out.println("The number " + number + " is PROBABLY PRIME");
        }
        System.out.println("time = " + ((tEnd - tStart) / 1000000) + "ms");
    }
    
    public static boolean solve(AMInfo info, long number, long iters, int threads){
        List<Integer> reses = new ArrayList<>();
        List<point> points = new ArrayList<>();
        List<channel> channels = new ArrayList<>();
        
        long iterPerThread = iters/threads;
        
        for (int i = 0; i < threads; i++){
            points.add(info.createPoint());
            channels.add(points.get(i).createChannel());
            points.get(i).execute("Algorithm");
            channels.get(i).write(number);
            channels.get(i).write(iterPerThread);
        }
        
        boolean res = true;
        for(int i = 0; i < threads; i++){
            reses.add(channels.get(i).readInt());
        }
        
        for(int i = 0; i < reses.size(); i++){
            if(reses.get(i) == -1){
            	res = false;
            }
        }
        return res;
    }
}