/*
 ==================================================================================================
 Description  : A program to simulate Banker's algorithm and handling the requests of the processes.
 
 Authors      : Omar Ashraf Labib Hassan   -   Hussein Hossam Hussein   -   Youssef Adel El-sayed
 
 ID           :     20170363			   -       20170352		        -         20170345
 
 Version      : 1.0
 
 Group        : CS_4
 ==================================================================================================
*/
import java.util.*;
import java.util.Scanner;
public class Main {

	public static int Av;
	public static int numOfP;
	public static int[][] allocation;
	public static int[][] maximum ;
	public static int[][] need;
	public static int[] sequence;
	public static int[] Available;
	public static Scanner in = new Scanner(System.in);
	public static void initialize()
	{
		
		System.out.println("Enter the number of resources follwed by the available amount");
		Av = in.nextInt();
		in.nextLine();
		 /// avail list = size of resources.
		int UserInput;
		Available = new int[Av];
		for(int i = 0 ; i < Av ; i++)///Entering Resources
		{
			UserInput = in.nextInt();
			Available[i] = UserInput;	
		}
		////___________________________________
		
		System.out.println("Enter the number of Processes");///Entering Processes 
		numOfP = in.nextInt();
		allocation = new int[numOfP][Av];
		
		
		System.out.println("Allocation matrix: ");
		System.out.println();
		for(int i = 0 ; i < numOfP ; i++) 
		{
			System.out.println("Enter for proccess P"+i);
			for(int j = 0 ; j < Av; j++) 
			{
				UserInput = in.nextInt();
				allocation[i][j] = UserInput;
			}
		}
		
		////___________________________________
		
		System.out.println("Maxinum matrix: ");
		maximum = new int [numOfP][Av];
		for(int i = 0 ; i < numOfP ; i++) 
		{
			System.out.println("Enter for proccess P"+i);
			for(int j = 0 ; j < Av; j++) 
			{
				UserInput = in.nextInt();
				maximum[i][j] = UserInput;
			}
		}
		
		////___________________________________

		///calculate Need matrix
				need = new int[numOfP][Av];
				for(int i = 0 ; i < numOfP ; i++) 
				{
					for(int j = 0 ; j < Av ; j++) 
					{
						need[i][j] = maximum[i][j] - allocation[i][j];
					}
				}
		
		
	}
//__________________________________________________________________________
	public static void checkSafe()
	{
		boolean[] visited = new boolean[numOfP];
		int [] work = new int[Av];
		for(int i = 0; i < numOfP; i++) /// was av
		{
			visited[i] = false;
		}
		
		for(int i = 0; i < Av; i++) 
		{
			work[i] = Available[i];
		}
		
		int counter = 0;
		sequence = new int[numOfP];
		while(counter < numOfP)
		{
			boolean check = false;
			for(int i = 0; i < numOfP; i++)
			{
				if(visited[i] == false)
				{
					int j;
					for(j = 0; j < Av; j++)
					{
						if(need[i][j] > work[j])
						{
							break;
						}
					}
					if(j == Av)
					{
						System.out.print("P" + i);
						System.out.print(" Allocated: ");
						for(j = 0; j < Av; j++)
						{
							System.out.print(allocation[i][j] + " ");
						}
						System.out.print(" Need: ");
						for(j = 0; j < Av; j++)
						{
							System.out.print(need[i][j] + " ");
						}
						System.out.print(" Availabe: ");
						for(j = 0; j < Av; j++)
						{
							System.out.print(need[i][j] + " ");
						}
						System.out.println();
						counter++;
						visited[i] = true;
						check = true;
						for(j = 0; j < Av; j++)
						{
							work[j] = work[j] + allocation[i][j];
						}
					}	
				}
			}
			if(!check)
			{
				break;
			}
		}
		if(counter < numOfP)
		{
			System.out.println("no safe sequence found");
		}
		else
		{
			System.out.println("Safe state");
		}	
	}
	public static void handleRequest()
	{
		int pID;
		int req[] = new int[Av];
		int tempNeed[] = new int[Av];
		boolean cond1 = true;
		boolean cond2 = true;
		System.out.println("Enter ID of the requested process: ");
		pID = in.nextInt();
		
		System.out.println("Enter amount of the requested resources: ");
		
		int i;
		for(i = 0; i < Av; i++)
		{
			req[i] = in.nextInt();
		}
		
		/// 1- calculate need.
		for(i = 0; i < Av; i++)
		{
			tempNeed[i] = maximum[pID][i] - allocation[pID][i];
		}
		
		/// first condition:  request <= need.
		for(i = 0; i < Av; i++)
		{
			if(req[i] > tempNeed[i])
			{
				break;
			}
		}
		
		if(i == Av) /// first condition satisfied.
		{
			/// second condition: request <= available
			for(i = 0; i < Av; i++)
			{
				if(req[i] > Available[i])
				{
					break;
				}
			}
			if(i == Av) /// second condition satisfied.
			{
				/// 1- available = available - request
				for(i = 0; i < Av; i++)
				{
					Available[i] -= req[i];
				}
				/// 2- allocation = allocation + request
				for(i = 0; i < Av; i++)
				{
					allocation[pID][i] += req[i];
				}
				/// 3- need = tempneed - request.
				for(i = 0; i < Av; i++)
				{
					need[pID][i] = tempNeed[i] - req[i];
				}
				
			}else /// condition 2 failed
			{
				cond2 = false;
			}
		}else /// condition 1 failed
		{
			cond1 = false;
		}
		if(!cond1 || !cond2)
		{
			System.out.println("This request can't be handled and the requested process have to wait.");
		}else
		{
			checkSafe();
		}	
	}
	
	public static void main(String[] args) 
	{
		System.out.println("\t\t what do want to do?\n");
		int choise;
		while(true)
		{
			System.out.println("\t\t******************   Menu   ******************");
			System.out.println("\t\t*                                              *");
			System.out.println("\t\t*          1- Enter input                      *");
			System.out.println("\t\t*          2- Request                          *");
			System.out.println("\t\t*          3- Quit                             *");
			System.out.println();
			choise = in.nextInt();
			switch (choise) 
			{
			case 1:
				initialize();
				checkSafe();
				break;
			case 2:
				handleRequest();
				break;
			case 3:
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}

}
