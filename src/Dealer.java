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
        String type;
        String make;
        String model;
        String cap;
        String mpg;
        String trans;
        boolean a;

        try {
            // make new nodelist from the vehicles xml file
            File input = new File("vehicles.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
            NodeList vehicleList = doc.getElementsByTagName("vehicle");

            // loop through the nodelist vehiclelist to get all of the vehicle data,
            // shove that into a vehicle instance and put it on the arraylist vehicles
            for (int i = 0; i < vehicleList.getLength(); i++) {
                Node node = vehicleList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

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

        } catch (Exception e) {
            //TODO: change this later to catch specific exceptions
            e.printStackTrace();
        }
    }

    // method for making a new customer
    public void newcustomer()
    {
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
    {   while(true)
        {
            System.out.println("Enter 1 to make a new reservation or 2 to quit");
            String keepGoing = console.nextLine();
            if(keepGoing.equals("1")){
                System.out.println("===== Reservationator 2000 ========");
                System.out.print("Enter the pickup location: ");
                String pLocation = console.nextLine();
                System.out.print("Enter the return location: ");
                String rLocation = console.nextLine();
                System.out.print("Enter pickup date in the form MM/DD/YYYY: ");
                String pDate = console.nextLine().trim();
                System.out.print("Enter the return date in the form MM/DD/YYYY: ");
                String rDate = console.nextLine().trim();

                showAvailable();
                System.out.println("Here is the list of available vehicles\n" +
                    "Pick one you want, and enter it's Id number to select it\n");
                int choice = Integer.parseInt(console.nextLine());
                int index = searchVehicles(choice);

                if (index != -1) {
                    Reservation newReservation = new Reservation(pLocation, rLocation, pDate, rDate, vehicles.get(index).getId(), cId);
                    vehicles.get(index).setAvalible(false);
                    showReservation(newReservation);
                    System.out.printf("Total Price: $%.2f", newReservation.calcDaysRented() * vehicles.get(index).calcPrice());
                    reservations.add(newReservation);
                } else {
                    System.out.println("That Id was not found");
                }
            } else if(keepGoing.equals("2")){
                break;
            }

        }


    }

    // provided an id number returns the index of the vehicle or -1 for not found
    private int searchVehicles(int choice)
    {
        for (int i = 0; i < vehicles.size(); i++)
        {
            Vehicle vehicle = vehicles.get(i);
            if (choice == vehicle.getId()) {
                return vehicles.indexOf(vehicle);
            }
        }
        return -1;
    }

    private int searchCustomers(int choice)
    {
        for(int i = 0; i < customers.size(); i++)
        {
            Customer customer = customers.get(i);
            if(customer.getId() == choice)
            {
                return i;
            }
        }
        return -1;
    }

    // only shows which cars are not currently rented
    private void showAvailable()
    {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            if (vehicle.avalible()) {
                System.out.printf("#%d %s %s\nType: %s\nCapacity: %s\nMpg: %s\nTransmission Type: %s\n" +
                        "Price Per Day: %.2f\nId: %d\n\n",
                    i, vehicle.getMake(), vehicle.getModel(), vehicle.getType(),
                    vehicle.getCapacity(), vehicle.getMpg(), vehicle.getTrans(),
                    vehicle.calcPrice(), vehicle.getId());
            }
        }
    }

    // copy of show avalible but only shows cars that have avalible set to false
    public void showNonAvalible()
    {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            if (!vehicle.avalible()) {
                System.out.printf("#%d %s %s\nType: %s\nCapacity: %s\nMpg: %s\nTransmission Type: %s\n" +
                        "Price Per Day: %.2f\nId: %d\n\n",
                    i, vehicle.getMake(), vehicle.getModel(), vehicle.getType(),
                    vehicle.getCapacity(), vehicle.getMpg(), vehicle.getTrans(),
                    vehicle.calcPrice(), vehicle.getId());
            }
        }
    }

    public void showCustomers()
    {
        for(int i = 0; i < customers.size(); i++){
            Customer customer = customers.get(i);
            System.out.printf("#%d %s %s\nPhone: %s\nEmail: %s\nDrivers Licence: %s\nAge: %d\nId: %d\n\n",
                i, customer.getFname(), customer.getLname(), customer.getPhone(), customer.getEmail(),
                customer.getDv(), customer.getAge(), customer.getId());
        }
    }

    private void showReservation(Reservation reservation)
    {
        System.out.printf("Customer id: %s\nPickup Location: %s\nReturn Location: %s\n" +
            "Pickup Date: %s\nReturn Date: %s\nTotal Days Rented %d", reservation.getCustomerid(),
            reservation.getPickupLoc(), reservation.getReturnLoc(), reservation.getPickupDate(),
            reservation.getReturnDate(), reservation.calcDaysRented());
    }

    //========================= Employee methods ==============================================
    public void employee() {
        while (true) {
            System.out.println("1 to update customer info\n2 update reservation info\n" +
                "3 to search for reservations by customer\n4 to search for reservations by vehicle\n" +
                "5 to return to the main menu");
            String choice = console.nextLine();

            if (choice.equals("1")) {
                updateCustomer();
            } else if (choice.equals("2")) {
                updateReservation();
            } else if (choice.equals("3")) {
                searchReservationCustomer();
            } else if (choice.equals("4")) {
                searchReservationVehicle();
            } else if (choice.equals("5")) {
                break;
            } else {
                System.out.println("That input was not 1-5 or a number");
            }
        }
    }

    private void searchReservationVehicle() {
        System.out.println("Enter the vehicle type (as specified on canvas instructions): ");
        String type = console.nextLine();

        for(Vehicle vehicle : vehicles)
        {
            if(vehicle.getType().equals(type))
            {
                System.out.println(vehicle);
            }
        }
    }

    private void searchReservationCustomer() {
        System.out.println("Enter the First Name of the customer: ");
        String name = console.nextLine();

        int id = 0;
        for(Customer customer : customers)
        {
            if(name.equals(customer.getFname()))
            {
                id = customer.getId();
            }
        }

        for(Reservation reservation : reservations)
        {
            if(reservation.getCustomerid() == id)
            {
                showReservation(reservation);
            }
        }
    }

    private void updateCustomer()
    {
        showCustomers();
        System.out.println("Printed available customers\n" +
            "Enter the id number of the customer you would like to update");
        int choice = Integer.parseInt(console.nextLine());

        int index = searchCustomers(choice);
        if(index != -1)
        {
            customerEditor(index);
        }
    }

    private void updateReservation()
    {
        showNonAvalible();
        System.out.print("Enter the number of the reservation you would like to exit (after #): ");
        int index = Integer.parseInt(console.nextLine().trim());
        reservationEditor(index);
    }

    private void customerEditor(int index)
    {
        while(true){
            System.out.println("1 to change phone number\n2 to change Email\n3 to change drivers licence\n" +
                "4 to change age\n5 to return to employee menu");
            String choice = console.nextLine();

            if(choice.equals("1")){
                System.out.print("Enter the new Phone number: ");
                String newPhone = console.nextLine();
                customers.get(index).setPhone(newPhone);
            } else if(choice.equals("2")) {
                System.out.print("Enter the new email: ");
                String newEmail = console.nextLine();
                customers.get(index).setEmail(newEmail);
            } else if(choice.equals("3")) {
                System.out.print("Enter the new Drivers licence: ");
                String newDv = console.nextLine();
                customers.get(index).setDv(newDv);
            } else if(choice.equals("4")) {
                System.out.print("Enter the new age: ");
                int newAge = Integer.parseInt(console.nextLine());
                customers.get(index).setAge(newAge);
            } else if(choice.equals("5")){
                break;
            } else {
                System.out.println("You did not enter 1-5 or a number");
            }
        }
    }

    private void reservationEditor(int index)
    {
        boolean done = false;
        while(!done){
            System.out.println("1 to change the Pickup Location\n2 to change Return Location\n" +
                "3 to change Pickup Date\n4 to change Return Date\n5 to exit back to employee menu\n");
            int choice = Integer.parseInt(console.nextLine());

            switch(choice)
            {
                case 1:
                    System.out.println("Enter the new Pickup Location: ");
                    String newPLocation = console.nextLine();
                    reservations.get(index).setPickupLoc(newPLocation);
                    break;
                case 2:
                    System.out.println("Enter the new Return Location: ");
                    String newRLocation = console.nextLine();
                    reservations.get(index).setReturnLoc(newRLocation);
                    break;
                case 3:
                    System.out.println("Enter the new Pickup Date: ");
                    String newPDate = console.nextLine();
                    reservations.get(index).setPickupDate(newPDate);
                    break;
                case 4:
                    System.out.println("Enter the new Return Date: ");
                    String newRDate = console.nextLine();
                    reservations.get(index).setReturnDate(newRDate);
                    break;
                case 5:
                    done = true;
                    break;
            }
        }
    }
}