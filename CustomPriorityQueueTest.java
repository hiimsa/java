import java.util.*;
class Node<S, I extends Number>{
    String key;
    int count;
    Node(String k, int c){
        key = k;
        count = c;
    }
}
public class CustomPriorityQueueTest {
    public static void main(String args[]){
        int n = 6;
        int [][]connections = new int[][]{{0,1},{1,3},{2,3},{4,0},{4,5}};
        //minReorder(n, connections);
        //merge(new int[][]{{1,3},{2,6},{8,10},{15,18}});
        //PQTest();
        int []testArray = new int[]{1,3,5,7,9,11};
        int []testArray2 = new int[]{1,3,3,3,3,3,3,9,11};
        int smallestValueGreaterThanTarget = findCeil(testArray2,2);
        System.out.println("smallestValueGreaterThanTarget : " + testArray[smallestValueGreaterThanTarget]);
        int largestValueSmallerThanTarget = findFloor(testArray2,2);
        System.out.println("largestValueSmallerThanTarget : " + testArray[largestValueSmallerThanTarget]);
    }
    static int minReorder(int n, int[][] connections) {
        Map<Integer, List<List<Integer>>> adj = new HashMap<>();
        for (int[] connection : connections) {
            adj.computeIfAbsent(connection[0], k -> new ArrayList<List<Integer>>()).add(Arrays.asList(connection[1], 1));
            adj.computeIfAbsent(connection[1], k -> new ArrayList<List<Integer>>()).add(Arrays.asList(connection[0], 0));
        }
        return 0;
    }
    static public int[][] merge(int[][] intervals) {
        if (intervals.length == 1) {
            return intervals;
        }
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        for (int[] interval : intervals) {
            queue.add(interval);
        }
        List<int[]> result = new ArrayList<>();
        int[] prevInterval = queue.poll();
        result.add(prevInterval);
        for (int[] currInterval: intervals) {
            // if curr start is smaller than last end, it means it is an overlap
            // we update the last value's end in the list with max of curr or last end.
            if (currInterval[0] <= prevInterval[1]) {
                result.get(result.size() - 1)[1] = Math.max(prevInterval[1], currInterval[1]);
            } else {
                // if there is no overlap then, insert the current as it is
                result.add(new int[]{currInterval[0], currInterval[1]});
            }
            // update the prev end with max of prev and cure for next iteration
            prevInterval[1] = Math.max(prevInterval[1], currInterval[1]);
        }
        return result.toArray(new int[][]{});
    }
    static public void PQTest(){
        PriorityQueue<Node<String, Integer>> PQ = new PriorityQueue<>((a, b)->b.count-a.count);
        Map<String, Integer> map = new HashMap<>();
        map.put("a",1);map.put("b",1);map.put("c",5);map.put("d",6);
        map.put("e",7);map.put("f",8);map.put("g",9);map.put("h",10);
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            PQ.add(new Node(entry.getKey(),entry.getValue()));
        }
        System.out.println("Before removal");
        System.out.println(PQ.peek().key);
        PQ.remove("h");
        System.out.println("After removal");
        System.out.println(PQ.peek().key);


    }
    static int findCeil(int arr[], int target){
        int start = 0;
        int end = arr.length - 1;
        while(start <= end) {
            int mid = start + (end - start) / 2;
            if(target > arr[mid]){
                start = mid + 1;
            }
            else{
                end = mid - 1;
            }
        }
        return start;
    }
    static int findFloor(int []arr, int target){
        return findCeil(arr,target) - 1;
    }
}
