import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation {
    private String pickupLoc;
    private String returnLoc;
    private Date pickupDate;
    private Date returnDate;

    public Reservation(String pickupLoc, String returnLoc, Date pickupDate, Date returnDate)
    {
        this.pickupLoc = pickupLoc;
        this.returnLoc = returnLoc;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
    }


    public int calcDaysRented()
    {
        long diff = returnDate.getTime() - pickupDate.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
