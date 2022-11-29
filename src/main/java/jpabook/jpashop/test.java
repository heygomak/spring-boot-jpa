package jpabook.jpashop;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class test {
    static int[] fees = {180, 5000, 10, 600};
    static String[] records =  {"05:34 5961 IN", "06:34 5961 OUT", "07:34 5961 IN", "08:34 5961 OUT", "09:34 5961 IN", "10:34 5961 OUT", "11:34 5961 IN", "12:34 5961 OUT"};

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution()));
    }
    public static int[] solution() {
        Map<String, String> map = new TreeMap<>();
        for(String record : records) {
            String carNumber = record.split(" ")[1];
            String time = record.split(" ")[0];
            if(map.containsKey(carNumber)) {
                map.put(carNumber, map.get(carNumber) + "|" + time);
            } else {
                map.put(carNumber, time);
            }
        }

        int idx = 0;
        int[] answer = new int[map.size()];
        for(String key : map.keySet()) {
            int time = 0;
            String[] arr = map.get(key).split("\\|");
            for(int i = 0; i < arr.length; i++) {
                if((i == 0 && arr.length > 1) || (i < arr.length - 1 && i % 2 == 0)) {
                    time += calcTimes(arr[i], arr[i + 1]);
                } else if (i == arr.length - 1 && i % 2 == 0){
                    time += calcTimes(arr[i], "23:59");
                }
            }
            answer[idx] = calcFees(time);
            idx ++;
        }

        return answer;
    }

    public static int calcTimes(String inTime, String outTime) {
        return 60 * (Integer.parseInt(outTime.split(":")[0]) - Integer.parseInt(inTime.split(":")[0])) +
                (Integer.parseInt(outTime.split(":")[1]) - Integer.parseInt(inTime.split(":")[1]));
    }

    public static int calcFees(int time) {
        return (time <= fees[0]) ? fees[1] :
                fees[1] + (int) Math.ceil((double)(time - fees[0]) / (double)fees[2]) * fees[3];
    }
}
