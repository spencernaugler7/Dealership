import java.util.Scanner;
public class Menu {
    private static Scanner console = new Scanner(System.in);

    public static void main(String[] args)
    {
        Dealer dealer = new Dealer();
        dealer.crawlXml(); // collects xml data before it does anything
        menu(dealer);
    }

    public static void menu(Dealer dealer){
        while(true)
        {
            String choice = "";
            try {
                System.out.println("1 if you are a customer\n2 if you are an employee\n3 to exit\n");
                choice = console.nextLine();

            } catch(Exception e){
                System.err.println("you did not enter 1, 2, or 3");
            }

            // decide based on choice
            if(choice.equals("1"))
            {
                dealer.newcustomer();
            } else if(choice.equals("2")){
                dealer.employee();
            } else {
                System.out.println("Goodbye");
                break;
            }
        }
    }
}
