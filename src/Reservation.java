import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation
{
    private String pickupLoc;
    private String returnLoc;
    private String pickupDate;
    private String returnDate;
    private int customerid;
    private int vehicleid;

    // declaration for dateformating
    private DateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY");

    public Reservation(String pickupLoc, String returnLoc, String pickupDate, String returnDate,
                       int vehicleid, int customerid)
    {
        this.pickupLoc = pickupLoc;
        this.returnLoc = returnLoc;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.vehicleid = vehicleid;
        this.customerid = customerid;
    }

    // getters for updating data, via employee class
    public String getPickupLoc() { return this.pickupLoc; }
    public String getreturnLoc() { return this.returnLoc; }
    public String getPickupDate() { return this.pickupDate; }
    public String getReturnDate() { return this.returnDate; }

    // setters for updating data, via employee class
    public void setPickupLoc(String pickupLoc){ this.pickupLoc = pickupLoc; }
    public void setReturnLoc(String returnLoc){ this.pickupLoc = returnLoc; }
    public void setPickupDate(String pickupDate){ this.pickupDate = pickupDate; }
    public void setReturnDate(String returnDate){ this.returnDate = returnDate; }

    public int calcDaysRented()
    {
        Date returnDate = null;
        Date pickupDate = null;
        try {
            returnDate = dateFormat.parse(this.returnDate);
            pickupDate = dateFormat.parse(this.pickupDate);
        } catch(Exception e){
            e.printStackTrace();
        }
        long diff = returnDate.getTime() - pickupDate.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

    }

    @Override
    public String toString()
    {
        return String.format("Customer id renting: %s\nPickup Location: %s\nReturn Location: %s\n" +
            "Pickup Date: %s\nReturn Date: %s\nTotal Days Rented %d", customerid, pickupLoc, returnLoc,
            pickupDate, returnDate, calcDaysRented());
    }
}