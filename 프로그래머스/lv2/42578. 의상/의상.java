/**
# 문제 요약
1. 매일 다른 옷을 좋바해서 입음
2. 각 종류별로 최대 1가지 의상 착용
3. 하루에 최소 1개의 의상을 입는다.
4. 2차원 배열이 주어질 때 서로 다른 옷의 조합의 수를 구하라
## 문제 풀이
1. map에 넣는다.
2. 전체에서 한개 이상은 고르므로 부분집합을 구한다.
3. 부분집합의 개수는 2^n개
4. 각 부분집합에서 의상 종류의 개수를 모두 곱한뒤 그걸 더하면 된다.

각 집합에서 안입는 방법도 있을테니 하나씩 더해서 2^n-1하면 된다.
*/
import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        
        HashMap<String, List<String>> map = new HashMap<>();
        
        for(int i=0; i< clothes.length; i++){
            String a = clothes[i][0];
            String b = clothes[i][1];
            
            if(!map.containsKey(b)) map.put(b, new ArrayList<String>());
            map.get(b).add(a);
        }
        for(String now : map.keySet()){
            answer *= (map.get(now).size()+1);
        }
        answer--;
        
        return answer;
    }
}