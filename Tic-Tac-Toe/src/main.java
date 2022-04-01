import java.util.Scanner;
public class main {

public static void main(String[] args) {
Scanner input = new Scanner(System.in);
System.out.println("Welcome To Tic Tac Toe, please start by entering the dimensions for your board (n*m), starting with n and then m. Keep in mind that player 1 is 'o' and player 2 is 'x'");
int n = input.nextInt();
int m = input.nextInt();
System.out.println("Now enter how many in a row a player must get to win (k)");
int k = input.nextInt();
Tic_Tac_Toe game= new Tic_Tac_Toe(n,m,k); //intitalizing object
game.play_game(); //play game


}
}