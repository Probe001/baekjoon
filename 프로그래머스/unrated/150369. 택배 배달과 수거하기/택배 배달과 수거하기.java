/**
# 문제 요약
1. 일렬로 된 n개의 집에 배달. 배달 상자 크기는 모두 같으며 배달을 다니며 빈 상자 수거
2. 배달할 택배는 물류창고에서 출발
3. i번째 집은 물류창고에서 i만큼 떨어져있음 -> 각 집은 수직선상 정수점 위에 있음
4. 트럭에는 상자를 최대 cap개 실음
5. 각 집마다 배달할 택배 상자 deliveries 와 빈 상자 pickups 개수 주어짐
6. 트럭 하나로 모든 배달과 수거를 마치고 물류창고까지 들어올 수 있는 최소 이동 거리
7. 배달 및 수거 시 원하는 개수만큼 택배 배달 및 수거 가능
# 제약사항
1 <= cap <= 50
1 <= n <= 100000
0 <= deli[k], pick[k] <= 50
# 문제풀이
1. n = 100000 -> 순열 불가능
    1   0   3   1   2
    0   3   0   4   0
2. 택배를 한번 왕복할 때 가장 적게 움직이는게 최고다
-> 멀리 떨어진 집부터 해치워주는게 유리하다
- 그리디?
3. 뒤에서부터 읽어간다. k = n-1
    - 처음에 실어오는 상자 수 : initNum
    - 수거한 택배 수          : curNum
5. 집의 deli[k]를 initNum에 추가, 수거한 택배 수도 추가
    - 만약 둘 중 하나라도 cap을 넘어가면 한번 다녀와야 됨. : answer += 2*k;
    - 둘 중 하나라도 0이 아니라면 다시 시도
    - 둘 다 0이라면 k - 1
*/
import java.util.*;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        
        int ind = n-1;
        A: while(ind >= 0){
            while(deliveries[ind] == 0 && pickups[ind] == 0){
                ind--;
                if(ind < 0) break A;
            }
            answer += 2*(ind+1);          // 어차피 여기까지 한번 와야된다.
            
            int bring = 0, take = 0;
            B: for(int bk=ind; bk>=0; bk--){
                while(deliveries[bk] > 0){
                    deliveries[bk]--;
                    if(++bring >= cap) break B;
                }
            }
            
            C: for(int tk=ind; tk>=0; tk--){
                while(pickups[tk] > 0){
                    pickups[tk]--;
                    if(++take >= cap) break C;
                }
            }
            //System.out.println(answer);
            //System.out.println(Arrays.toString(deliveries));
            //System.out.println(Arrays.toString(pickups));
        }
        return answer;
    }
}