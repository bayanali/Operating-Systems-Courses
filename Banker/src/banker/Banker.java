package banker;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Banker {

    public static ArrayList<Integer> Available = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> Maximum = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> Allocation = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> Need = new ArrayList<>();
    public static int Rows = 0;
    public static int numOfResources = 0;
    
    public static boolean isSafe(int processNum, ArrayList<Integer> requests)
    {
        for(int i = 0; i < requests.size(); i++){
                //Allocation.get(processNum).get(i) += request.get(i);
                int old = Allocation.get(processNum).get(i);
                int newv = old + requests.get(i);
                Allocation.get(processNum).set(i, newv);
                Available.set(i, Available.get(i) - requests.get(i));
                Need.get(processNum).set(i, Maximum.get(processNum).get(i) - Allocation.get(processNum).get(i));
            }
        
       int Work[] = new int[numOfResources];
       boolean Finish[] = new boolean[Rows];
       
       for (int i = 0; i < numOfResources; i++)
            Work[i] = Available.get(i);
       
       
       for (int i = 0; i < Rows; i++)
           Finish[i] = false; 
        
      int NotFinish = 0, finish = 0; 
      
       while (NotFinish < Rows && finish < Rows)
       {
       for (int i = 0; i < Rows; i++)  
       {
           boolean need = true;
           if (Finish[i] == false)
           {
               
               for (int j = 0; j < numOfResources; j++)
               {
                   if (Need.get(i).get(j) > Work[j])
                       need = false;
                   NotFinish++;
               }
    
               if (need)
               {
                   for (int j = 0; j < numOfResources; j++)
                       Work[j] += Allocation.get(i).get(j);
                   Finish[i] = true;
                   finish++; 
               }
           }
       }
       }
       boolean isSafe = true; 
       if (finish == Rows)
            isSafe = true;
       if (NotFinish == Rows)
       {
           isSafe = false;
           for(int i = 0; i < requests.size(); i++){
                //Allocation.get(processNum).get(i) += request.get(i);
                int old = Allocation.get(processNum).get(i);
                int newv = old - requests.get(i);
                Allocation.get(processNum).set(i, newv);
                Available.set(i, Available.get(i) + requests.get(i));
                Need.get(processNum).set(i, Maximum.get(processNum).get(i) + Allocation.get(processNum).get(i));
            }
       }
    
       
       return isSafe;
    }

    public static void request(int processNum, ArrayList<Integer> requests) {
        boolean safe = true;
        for(int i = 0; i < requests.size() && safe; i++) {
            if(requests.get(i) > Available.get(i)) {
                System.out.println("Request more resources than available.");
                safe = false;
            }
            else if(requests.get(i) > Maximum.get(processNum).get(i)) {
                System.out.println("Request more resources than the process claim.");
                safe = false;
            }
            else if(requests.get(i) > Need.get(processNum).get(i)) {
                System.out.println("Request resources more than needed.");
                safe = false;
            } 
        }
        
        if(safe)
        {
           if (isSafe(processNum,requests) == false)
            System.out.println("Request resources that lead to unsafe state");
            
           else 
            System.out.println("Safe!");
        }
        
    }

    public static void release(int processNum, ArrayList<Integer> releases) {
        boolean safe = true;
        for (int i = 0; i < releases.size(); i++) {
            if (releases.get(i) > Need.get(processNum).get(i)) {
                System.out.println("Release more resources than acquired by the process");
                safe = false;
            }
        }

        if (safe) {
            for (int i = 0; i < releases.size(); i++) {
                //Allocation.get(processNum).get(i) += request.get(i);
                int old = Allocation.get(processNum).get(i);
                int newv = old - releases.get(i);
                Allocation.get(processNum).set(i, newv);

            }
            for (int i = 0; i < releases.size(); i++)
                Available.set(i, Available.get(i) + releases.get(i));

            for (int i = 0; i < releases.size(); i++)
                Need.get(processNum).set(i, Maximum.get(processNum).get(i) - Allocation.get(processNum).get(i));
            System.out.println("Safe!");
        }
    }

    public static void PrintAll()
    {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        System.out.println("Available: ");
        for (int i = 0; i < Available.size(); i++)
            System.out.println(abc.charAt(i) + " " + Available.get(i) + " ");


        System.out.println("Allocation: ");
        for (int i = 0; i < Rows; i++)
        {
            for (int j = 0; j < Allocation.get(i).size(); j++)
                System.out.print(Allocation.get(i).get(j) + " ");
            System.out.println();
        }

        System.out.println("Need: ");
        for (int i = 0; i < Rows; i++)
        {
            for (int j = 0; j < Need.get(i).size(); j++)
                System.out.print(Need.get(i).get(j) + " ");
            System.out.println();
        }

        System.out.println("Maximum: ");
        for (int i = 0; i < Rows; i++)
        {
            for (int j = 0; j < Maximum.get(i).size(); j++)
                System.out.print(Maximum.get(i).get(j) + " ");
            System.out.println();
        }

    }


    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Enter the number of types of resources");
        Scanner in = new Scanner(System.in);
        numOfResources = in.nextInt();

        System.out.println("Enter the number of instances for each type");
        for (int i = 0; i < numOfResources; i++)
            Available.add(i, in.nextInt());

        System.out.println("Enter the name of file that contains the maximum need from the " +
                numOfResources + " resources for each process");
        String FileName = "",Path = "";
        FileName = in.next();

        Path =  "C:\\Users\\bayan\\Desktop\\Coding\\Java Coding\\Banker\\src\\banker\\" + FileName;
        File read = new File (Path);
        Scanner inputFile = new Scanner (read);

        String Line = "";

        while(inputFile.hasNextLine())
        {
            Line = inputFile.nextLine().replaceAll("\\s+","");
            
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < numOfResources; i++)
                numbers.add(i, Character.getNumericValue(Line.charAt(i)));
            Need.add(Rows, numbers);
            
            ArrayList<Integer> zeros = new ArrayList<>();
            for (int i = 0; i < numOfResources; i++)
                zeros.add(i,0);
            Allocation.add(Rows, zeros);
            
            numbers = new ArrayList<>();
              for (int i = 0; i < numOfResources; i++)
                numbers.add(i, Character.getNumericValue(Line.charAt(i)));
            Maximum.add(Rows, numbers);
            
            Rows++;
            //System.out.println(Rows);
        }
        inputFile.close();

        //PrintAll();

        boolean quit = false;
        while(!quit) {
            System.out.println("Enter option:\n1-State\n2-Request\n3-Release\n4-Quit\n");
            int option = in.nextInt();
            switch (option) {
                case 1:
                    // state
                    PrintAll();
                    break;
                case 2:
                    // request
                    ArrayList<Integer> request = new ArrayList<>();
                    int processNum = in.nextInt();
                    for(int i = 0; i < numOfResources; i++) {
                        request.add(in.nextInt());
                    }
                    request(processNum, request);
                    break;
                case 3:
                    //release(processNum, releases);
                    ArrayList<Integer> release = new ArrayList<>();
                    int processNum2 = in.nextInt();
                    for(int i = 0; i < numOfResources; i++) {
                        release.add(in.nextInt());
                    }
                    release(processNum2, release);
                    break;
                case 4:
                    quit = true;
            }
        }
    }


}
