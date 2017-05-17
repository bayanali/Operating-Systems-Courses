package diskscheduling;

import java.util.*;

/**
 *
 * @author Bayan and Menna
 */
public class DiskScheduling {

    public static class Output {

        ArrayList<Integer> sequence = new ArrayList<Integer>();
        int headMovements = 0;
    }

    public static Output FCFS(ArrayList<Integer> queue, int initial) {
        Output out = new Output();
        out.headMovements = Math.abs(initial - queue.get(0));
        for (int i = 0; i < queue.size(); i++) {
            if (i < queue.size() - 1) {
                out.headMovements += (Math.abs(queue.get(i) - queue.get(i + 1)));
            }
        }
        out.sequence = new ArrayList(queue); 
        out.sequence.add(0, initial);
        
        System.out.println("Total Head Movements for FCFS: " + out.headMovements);

         for (int i = 0; i < out.sequence.size(); i++) {
            System.out.print(out.sequence.get(i) + " ");
        }
         
         System.out.println();
         System.out.println();

        return out;
    }

    public static Output SSTF(ArrayList<Integer> q, int initial) {
        Output out = new Output();
        ArrayList<Integer> queue = new ArrayList(q);
        out.sequence.add(initial);
        out.headMovements = 0;
        int Head = initial, size = queue.size(), Min = 1000, index = 0, op = 0;
        
        while (size >= 1) {

            for (int i = 0; i < queue.size(); i++) {

                if (queue.get(i) == -1)
                    continue;
                
                op = Math.abs(Head - queue.get(i));

                if (op < Min) {
                    Min = op;
                    index = i;
                }
            }

            Head = queue.get(index);
            out.sequence.add(Head);
            out.headMovements += Min;
            Min = 1000;
            queue.set(index, -1);
            size--;

        }
        
        System.out.println("Total Head Movements for SSTF: " + out.headMovements);

        for (int i = 0; i < out.sequence.size(); i++) {
            System.out.print(out.sequence.get(i) + " ");
        }

        System.out.println();
        System.out.println();
        return out;
    }
    
    public static Output Scan(ArrayList<Integer> q, int initial) {
        Output out = new Output();
        ArrayList<Integer> queue = new ArrayList(q);
        queue.add(initial);
        Collections.sort(queue);
        int index = queue.indexOf(initial);
    
        for (int i = index; i >= 0; i--){
            out.sequence.add(queue.get(i));
            if (i > 0)
            out.headMovements += Math.abs(queue.get(i)-queue.get(i-1));
        }
       
        out.headMovements += queue.get(0);
        out.sequence.add(0);
        out.headMovements += queue.get(index+1);
        
        for (int i = index+1; i < queue.size(); i++){
            out.sequence.add(queue.get(i));
            if (i < queue.size()-1)
                out.headMovements += Math.abs(queue.get(i)-queue.get(i+1)); 
        }
        
        
        System.out.println("Total Head Movements for Scan: " + out.headMovements);
        
        for (int i = 0; i < out.sequence.size(); i++) {
            System.out.print(out.sequence.get(i) + " ");
        }
        
        System.out.println();
        System.out.println();
        return out;
    }
    
     public static Output Look(ArrayList<Integer> q, int initial) {
        Output out = new Output();
        ArrayList<Integer> queue = new ArrayList(q);
        queue.add(initial);
        Collections.sort(queue);
        int index = queue.indexOf(initial);
    
        for (int i = index; i >= 0; i--){
            out.sequence.add(queue.get(i));
            if (i > 0)
            out.headMovements += Math.abs(queue.get(i)-queue.get(i-1));
        }
       
        out.headMovements += Math.abs(queue.get(0)-queue.get(index+1));
        
        for (int i = index+1; i < queue.size(); i++){
            out.sequence.add(queue.get(i));
            if (i < queue.size()-1)
                out.headMovements += Math.abs(queue.get(i)-queue.get(i+1)); 
        }
        
        System.out.println("Total Head Movements for Look: " + out.headMovements);
        
        for (int i = 0; i < out.sequence.size(); i++) {
            System.out.print(out.sequence.get(i) + " ");
        }
        
        System.out.println();
        System.out.println();

        return out;
    }
    
    public static Output Cscan(ArrayList<Integer> q, int initial) {
        Output out = new Output();
        ArrayList<Integer> queue = new ArrayList(q);
        queue.add(initial);
        Collections.sort(queue);
        int index = queue.indexOf(initial), end = 199; 
    
        for (int i = index; i < queue.size(); i++){
            out.sequence.add(queue.get(i));
            if (i < queue.size()-1)
            out.headMovements += Math.abs(queue.get(i)-queue.get(i+1));
        }
       
        out.sequence.add(end);
        out.headMovements += Math.abs(queue.get(queue.size()-1)-end);
        out.headMovements += end+1; 
        out.sequence.add(0);
        out.headMovements += queue.get(0);
        
        for (int i = 0; i < index; i++){
            out.sequence.add(queue.get(i));
            if (i < index - 1)
                out.headMovements += Math.abs(queue.get(i)-queue.get(i+1)); 
        }
        
        System.out.println("Total Head Movements for Cscan: " + out.headMovements);
        
        for (int i = 0; i < out .sequence.size(); i++) {
            System.out.print(out.sequence.get(i) + " ");
        }
        
        System.out.println();
        System.out.println();

        return out;
    }
    
    public static Output Clook(ArrayList<Integer> q, int initial) {
        Output out = new Output();
        ArrayList<Integer> queue = new ArrayList(q);
        queue.add(initial);
        Collections.sort(queue);
        int index = queue.indexOf(initial); 
    
        for (int i = index; i < queue.size(); i++){
            out.sequence.add(queue.get(i));
            if (i < queue.size()-1)
            out.headMovements += Math.abs(queue.get(i)-queue.get(i+1));
        }
       
        out.headMovements += Math.abs(queue.get(queue.size()-1)-queue.get(0));
       
        for (int i = 0; i < index; i++){
            out.sequence.add(queue.get(i));
            if (i < index - 1)
                out.headMovements += Math.abs(queue.get(i)-queue.get(i+1)); 
        }
        
        System.out.println("Total Head Movements for Clook: " + out.headMovements);
        
        for (int i = 0; i < out .sequence.size(); i++) {
            System.out.print(out.sequence.get(i) + " ");
        }
        
        System.out.println();

        return out;
    }
    

    public static void main(String[] args) {

        ArrayList<Integer> queue = new ArrayList<Integer>();
        queue.add(98);
        queue.add(183);
        queue.add(37);
        queue.add(122);
        queue.add(14);
        queue.add(124);
        queue.add(65);
        queue.add(67);
        
        
        FCFS(queue, 53);
        SSTF(queue, 53);
        Scan(queue, 53);
        Look(queue, 53);
        Cscan(queue, 53);
        Clook(queue, 53);

    }

}
