import java.util.Scanner;
import java.util.Random;
enum CellState {
O,
X,
Empty;
}

enum BoardState {
InProgress,
Draw,
WinX,
WinO;
}

public class Tic_Tac_Toe {
private BoardState state;
private CellState board[][];
private int n, m, k ;
private int countTurns;
private CellState  StoreBoard [][][] ; //for game replay


Tic_Tac_Toe (int n, int m, int k){
this.n = n;
this.m = m;
this.k = k;


board = new CellState [n][m]; 
state = BoardState.InProgress; 
countTurns = 0;
StoreBoard = new CellState [n*m][n][m];
for(int i = 0 ; i < n; i ++) { //initializing board to empty
	for(int j = 0; j < m;j++){
		board[i][j] = CellState.Empty;
		StoreBoard[countTurns][i][j] = CellState.Empty;
}
}
}


Tic_Tac_Toe (){
this (3,3,3);
}



Tic_Tac_Toe (int n, int m){
this (n,m,3);
}


public void Setn(int n) {
this.n = n;
}


public int Getn () {
return n;
}


public void Setm(int m) {
this.m = m;
}


public int Getm () {
return m;
}


public void Setk(int k) {
this.k = k;
}


public int Getk () {
return k;
}


public void SetCountTurns(int CountTurns) {
countTurns = CountTurns;
}


public int GetCountTurns () {
return countTurns;
}


public void SetBoardState (BoardState s) {
state = s;
}


void print () {
	
for(int i =0; i <n; i++) {
	for (int j =0; j<m; j++) {
		if(board[i][j] == CellState.X) { //printing of symbol
			System.out.print('X');}
		else if (board[i][j] == CellState.O) {
			System.out.print('O');}
		else {
			System.out.print(" ");}
		if(j != m-1) {
			System.out.print(" | ");} //print vertical border
	}
System.out.println();
System.out.print('-');
for(int l = 0; l < m-1;l++) //print horizontal border based on size of board
	System.out.print(" - -");
	System.out.println();
	}
}
int generate_index()
{
	Random rand = new Random();
for(int p = k; p > 1;p--) // for loop changing from k to 2, since the bot will search for k-patterns, then k-1 patterns, until patterns of 2
{
	//check if any possible pth patterns
	for(int i = 0; i < n; i++)
		for(int j = 0; j < m; j++)
			if(board[i][j] == CellState.Empty)
			{
				board[i][j] = CellState.X; //if empty, try playing there
				if((check_right(p)) ||(check_under(p)) || (check_diagonal1(p)) || (check_diagonal2(p))) // check if there is a pth pattern that has been created as a result of change in board
					{
					board[i][j] = CellState.Empty; //returns played index to empty, since it will be changed in play function
					return ((i * m) + j)+1; //returns index of there is
					}
				else
					board[i][j] = CellState.Empty; //no p patterns possible, test fails
			}
	//check if any possible pth pattern to stop
	for(int i = 0; i < n; i++) //same test as above but checks for the opossing player to block and patterns or losses
		for(int j = 0; j < m; j++)
			if(board[i][j] == CellState.Empty)
			{
				board[i][j] = CellState.O;
				if((check_right(p)) ||(check_under(p)) || (check_diagonal1(p)) || (check_diagonal2(p)))
					{
					print();
						board[i][j] = CellState.Empty;
						
						
						return ((i * m) + j)+1;
					}
				else
					board[i][j] = CellState.Empty;
			}

}
	int random_index = rand.nextInt((n*m)-1)+1; //if there are no patterns ranging from k until 2, place a random index
			while(board[(random_index-1)/m][(random_index-1)%m] != CellState.Empty) //until a random variable is pointing at an empty board cell
			{
				random_index = rand.nextInt((n*m)-1)+1;
			}
	return random_index;
	
}



void play(int index)
{
	while((board[(index-1)/m][(index-1)%m] == CellState.O) || (board[(index-1)/m][(index-1)%m] == CellState.X)) //checks if inputed index is already played or not
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Index already played, try again");
		index = input.nextInt();
	}
if(this.player1_turn()) { //chooses which symbol to put on board based on player turn
board[(index-1)/m][(index-1)%m] = CellState.O;}
else {
board[(index-1)/m][(index-1)%m] = CellState.X;}


countTurns++;
	for(int i = 0; i < n;i++) //storing of board with new turn
		for(int j = 0; j < m;j++)
			StoreBoard[countTurns][i][j] = board[i][j];
}

public boolean player1_turn () {
if (countTurns% 2 == 0) {
return true;
}
else {
return false;
}
}

public void replay_game(int index)
{
	while((index > n*m) || (index > countTurns )|| (index < 0)) //checks to make sure index is already less than the curent turn, less than the maximum possible amount of turns, and greater than zero
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Invalid turn to return to, input again");
		index = input.nextInt();
	}
	for(int i = 0; i < n;i++) //loads board with stored board at turn = index
		for(int j = 0; j < m;j++)
			board[i][j] = StoreBoard[index][i][j];
	
	countTurns = index; //modifies number of turn to suit the new loaded board
					
}
public boolean check_right(int in_a_row) //checks any vertical pattern with in_a_row beinmg the variable of number of consequent symbols 
{

	for(int i =0; i<n; i++) {
		for (int j =0 ; j<m-in_a_row+1; j++) { //doesnt have to loop until whole board can stop at m-in_a_row
			if(board[i][j] == CellState.Empty) //do nothing if cell is empty
				{
					
				}
			else
			{
				boolean won = true;
				for(int l = 1; l < in_a_row;l++) //check consequent cells if they are equal to current examined cell
				{
					
					if(board[i][j] != board[i][j+l])
						won = false;
						
				}
				if(won == true)
					return true;
				
			}
		}
		}	
	return false;
}

public boolean check_under(int in_a_row) //same as check_right but checks vertical
{

	for(int i =0; i<n-in_a_row+1; i++) { //can stop at i-in_a_row
		for (int j =0 ; j<m; j++) {
			
			if(board[i][j] == CellState.Empty)
				{
			
				}
			else
			{
				boolean won = true;
				for(int l = 1; l < in_a_row;l++)
				{
					
					if(board[i][j] != board[i+l][j])
						won = false;
				
				}
				if(won == true)
					return true;
			}
		}
		}
		
	return false;
}

public boolean check_diagonal1(int in_a_row)
{
	for(int i =0; i<n-in_a_row+1; i++) { //can examine only the upper left_half of the board since it checks pattern going from upperleft to bottom right
		for (int j =0 ; j<m-in_a_row+1; j++) {
			
			if(board[i][j] == CellState.Empty)
				{
				
				}
			else
			{
				boolean won = true;
				for(int l = 1; l < in_a_row;l++)
				{
					
					if(board[i][j] != board[i+l][j+l])
						won = false;
				
				}
				if(won == true)
					return true;

			}
		}
		}
		
	return false;
}

public boolean check_diagonal2(int in_a_row)
{

	for(int i =in_a_row-1; i<n; i++) {
		for (int j =0 ; j<m-in_a_row + 1; j++) { //examines on a portion of the board from the bottom left to the upper right

			if(board[i][j] == CellState.Empty)
				{
				
				}
			
			else
			{
				boolean won = true;
				for(int l = 1; l < in_a_row;l++)
				{
					if(board[i][j] != board[i-l][j+l])
						won = false;
				
				}
				if(won == true)
					return true;
			}
		}
		}
	return false;
}

public void Win() {
if(check_right(k)) //if there is a horizontal pattern found with number k
{

	if(player1_turn()) //winner based on last player played
		state = BoardState.WinO;
	else
		state = BoardState.WinX;
}
else if(check_under(k)) //if there is a vertical pattern found with number k
{

	if(player1_turn())
		state = BoardState.WinO;
	else
		state = BoardState.WinX;
}
else if(check_diagonal1(k)) //if there is a diagonal pattern found with number k
{

	if(player1_turn())
		state = BoardState.WinO;
	else
		state = BoardState.WinX;
}
else if(check_diagonal2(k))//if there is another diagonal pattern found with number k
{

	if(player1_turn())
		state = BoardState.WinO;
	else
		state = BoardState.WinX;
}
else
	if(countTurns == n*m) // if countturns equal number of cells on board, game ends in draw
		state = BoardState.Draw;

}

public void play_game() //main function
{
	Scanner input = new Scanner(System.in);
	String generated;
	System.out.println("would you like player 2 to be a player or a bot, enter 0 for player and 1 for bot");
	generated = input.next();
	
	print();
	while(state == BoardState.InProgress) //keeps taking turns if game is in progress
	{
	if(player1_turn())
	{
		System.out.println("player 1's turn:");
	}
	else
	{
		System.out.println("Player 2's turn");
	}
	if((generated.equals("1")) && (player1_turn() == false)) //if player wants bot to play
	{
		int number = generate_index();
		play(number);
		for(int i = 0; i < 20; i++) //prints new lines to create a new page  every time a turn is played
			System.out.println();
		print();
		Win();
		
	}
	else // manual mode
	{
		System.out.println("Enter an index ranging from 1-n*m, or enter replay to return to a certain move");
		String answer = input.next();
		if(answer.equals("replay")) //wants to replay
		{
			int index;
			System.out.println("Enter the turn number you wish to return to");
			index = input.nextInt();
			replay_game(index);
			print();
		}
		else
		{
			play(Integer.parseInt(answer)); //integer representation of string answer
			for(int i = 0; i < 20; i++) 
				System.out.println();
			print();
			Win();
		}
	}
	if(state == BoardState.Draw) //checks states.
		System.out.println("Game ended in draw");
	else if (state == BoardState.WinO)
		System.out.println("Player 2 is the winner");
	else if (state == BoardState.WinX)
		System.out.println("Player 1 is the winner");
	}
}





}