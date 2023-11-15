package Reverse;

import java.util.Scanner;
public class JavaProgram {
	
	
	public static void main(System args[]) {
		int x;
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("ENTER ANY NUMBER:  ");
			x=sc.nextInt();
		}
		if(x%2==0) 
			System.out.println("GIVEN NO IS EVEN");
			else
				System.out.println("GIVEN NO IS ODD");
				
		
	}
	
	public static void Reverse() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Input String");
		char[] letters = sc.nextLine().toCharArray();
		System.out.println("Reverse String:");
		for(int i=letters.length-1;i>=0;i--) {
			System.out.print(letters[i]);
		}
			
	}
	

}
