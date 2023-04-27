/**
# 문제 풀이
1. 압축 단위만큼 압축을 시도한다.
2. 고정길이만큼 자른다.
3. 
*/

class Solution {
    public int solution(String s) {
        int answer = Integer.MAX_VALUE;
        
        for(int i=1; i<s.length(); i++){
            StringBuilder sb = new StringBuilder();
            String flag = s.substring(0,i);
            int cnt = 1;
            for(int j=i; j<s.length(); j+=i){
                int ind = Math.min(s.length(), j+i);
                String flag2 = s.substring(j, ind);
                if(flag.equals(flag2)){
                    cnt++;
                } else{
                    if(cnt==1) sb.append(flag);
                    else sb.append(cnt).append(flag);
                    flag = flag2;
                    cnt = 1;
                }
            }
            if(cnt==1) sb.append(flag);
            else sb.append(cnt).append(flag);
            //System.out.println(sb.toString());
            answer = Math.min(answer, sb.length());
        }
        if(s.length() == 1){
            answer = 1;
        }
        
        return answer;
    }
}