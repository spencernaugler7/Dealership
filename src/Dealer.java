// xml and file stuff
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
// others
import java.util.ArrayList;
import java.util.Scanner;

public class Dealer {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private static Scanner console = new Scanner(System.in);

    // scrapes xml and populates vehicles arraylist
    public void crawlXml()
    {
        // init dataset
        String type = null;
        String make = null;
        String model = null;
        String cap = null;
        String mpg = null;
        String trans = null;
        boolean a = true;

        try
        {
            // make new nodelist from the vehicles xml file
            File input = new File("vehicles.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
            NodeList vehicleList = doc.getElementsByTagName("vehicle");

            // loop through the nodelist vehiclelist to get all of the vehicle data,
            // shove that into a vehicle instance and put it on the arraylist vehicles
            for (int i = 0; i < vehicleList.getLength(); i++)
            {
                Node node = vehicleList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {

                    Element element = (Element) node;
                    // for each element get the value and put it into a vehicle object
                    type = element.getAttribute("type");
                    make = element.getElementsByTagName("make")
                        .item(0).getTextContent();
                    model = element.getElementsByTagName("model")
                        .item(0).getTextContent();
                    cap = element.getElementsByTagName("capacity")
                        .item(0).getTextContent();
                    mpg = element.getElementsByTagName("mpg")
                        .item(0).getTextContent();
                    trans = element.getElementsByTagName("trans")
                        .item(0).getTextContent();
                    a = Boolean.parseBoolean(element.getElementsByTagName("availability")
                        .item(0).getTextContent());

                    Vehicle newVehicle = new Vehicle(type, make, model, cap, mpg, trans, a);
                    vehicles.add(newVehicle);
                }
            }

        } catch(Exception e) {
            //TODO: change this later to catch specific exceptions
            e.printStackTrace();
        }
    }

    // method for making a new customer
    public void newcustomer(){
        System.out.println("Welcome to the shady car rental where your money is everything");
        System.out.print("Enter your first name: ");
        String fname = console.nextLine();
        System.out.print("Enter your last name: ");
        String lname = console.nextLine();
        System.out.print("Enter your phone number: ");
        String phone = console.nextLine();
        System.out.print("Enter your email: ");
        String email = console.nextLine();
        System.out.print("Enter you drivers licence number: ");
        String dv = console.nextLine();
        System.out.print("Enter your age: ");
        int age = Integer.parseInt(console.nextLine());

        Customer newCustomer = new Customer(fname, lname, phone, email, dv, age);
        reservation(newCustomer.getId());
        customers.add(newCustomer);
    }

    // method for making a reservation
    private void reservation(int cId)
    {
        System.out.println("===== Reservationator 2000 ========");
        System.out.println("Enter the pickup location");
        String pLocation = console.nextLine();
        System.out.println("Enter the return location");
        String rLocation = console.nextLine();
        System.out.println("Enter pickup date in the form MM/DD/YYYY");
        String pDate = console.nextLine();
        System.out.println("Enter the return date in the form MM/DD/YYYY");
        String rDate = console.nextLine();

        showAvailable();
        System.out.println("Here is the list of available vehicles\n" +
            "Pick one you want, and enter it's Id number to select it\n");
        int choice = Integer.parseInt(console.nextLine());

        int index = searchVehicles(choice);
        if (index != -1)
        {
            Reservation newReservation = new Reservation(pLocation, rLocation, pDate, rDate,
                vehicles.get(index).getId(), cId);
            vehicles.get(index).setAvalible(false);
            System.out.println(newReservation);
            System.out.printf("Total Price: $%.2f", newReservation.calcDaysRented() * vehicles.get(index).calcPrice());
            reservations.add(newReservation);
        } else {
            System.out.println("That Id was not found");
        }


    }

    // provided an id number returns the index of the vehicle or -1 for not found
    private int searchVehicles(int choice)
    {
        for(int i = 0; i < vehicles.size(); i++)
        {
            Vehicle vehicle = vehicles.get(i);
            if(choice == vehicle.getId())
            {
                return vehicles.indexOf(vehicle);
            }
        }
        return -1;
    }

    // only shows which cars are not currently rented
    public void showAvailable()
    {
        for(int i = 0; i < vehicles.size(); i++)
        {
            Vehicle vehicle = vehicles.get(i);
            if(vehicle.avalible())
            {
                System.out.printf("#%d %s%s\nType: %s\nCapacity: %s\nMpg: %s\nTransmission Type: %s\n" +
                        "Price Per Day: %.2f\nId: %d\n",
                    i, vehicle.getMake(), vehicle.getModel(), vehicle.getType(),
                    vehicle.getCapacity(), vehicle.getMpg(), vehicle.getTrans(),
                    vehicle.calcPrice(), vehicle.getId());
            }
        }
    }

    //========================= Employee methods ==============================================
    public void employee() {
        System.out.println("1 to update customer info\n2 to update vehicle info\n ");
    }
}
