import java.util.Scanner;
public class Menu {
    private static Scanner console = new Scanner(System.in);
    public static void main(String[] args)
    {
        menu();
    }

    public static void menu(){
        boolean done = false;

        while(!done)
        {
            String choice = "";
            try {
                System.out.printf("1 if you are a customer\n 2 if you are an employee\n3 to exit");
                choice = console.nextLine();

            } catch(Exception e){
                System.err.println("you did not enter 1, 2, or 3");
            }

            if(choice.equals("1"))
            {

            } else if(choice.equals("2")){



            } else {
                done = true;
                System.out.println("Goodbye");
            }
        }
    }
}
