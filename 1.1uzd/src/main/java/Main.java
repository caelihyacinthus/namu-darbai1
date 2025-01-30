import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String text = "Yes";
    while (!text.equals("No")) {
      System.out.println("Yes or No");
      text = scan.nextLine();
    }
  }
}
