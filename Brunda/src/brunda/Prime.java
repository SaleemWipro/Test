package brunda;

public class Prime {

	public static void main(String[] args) {
		 int m = 31;
		 int n=0;
		for(int i = 2;i<m;i++) 
		
		{
		if(m%i == 0) 
			{
				n=1;
				break;
			}

		}
		
		if(n==1){
			System.out.print("m is not prime");
		}
		else
		
		{
			System.out.print("m is prime");
		
		}
		// TODO Auto-generated method stub

	}

	

}
