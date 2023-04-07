class Solution {
    public int solution(int[] arr) {
        
        int gcd = arr[0];
        int lcm = arr[0];
        for(int i=1; i< arr.length; i++){
            gcd = GCD(lcm, arr[i]);
            int l2 = arr[i]/gcd;
            lcm *= l2;
        }
        return lcm;
    }
    static int GCD(int x, int y){
    int n1, n2;
    if(x > y){
        n1 = x;
        n2 = y;
    } else{
        n1 = y;
        n2 = x;
    }
   
    int res = n1 % n2;
    while(res != 0){
        n1 = n2;
        n2 = res;
        res = n1 % n2;
    }
    return n2;
}
}

