import java.util.Random;

public class Vehicle
{
    private String type;
    private String make;
    private String model;
    private String capacity;
    private String mpg;
    private String trans;
    private boolean avalible;
    private int id; // 6 digit random id

    public Vehicle(String type, String make, String model, String capacity,
                   String mpg, String trans, boolean avalible)
    {
        this.type = type;
        this.make = make;
        this.model = model;
        this.capacity = capacity;
        this.mpg = mpg;
        this.trans = trans;
        this.avalible = avalible;

        Random rand = new Random();
        id = rand.nextInt(999999);

    }

    // getters for variables
    public String getMake(){ return this.make; }
    public String getModel(){ return this.model; }
    public String getCapacity(){ return this.capacity; }
    public String getType(){ return this.type; }
    public String getTrans(){ return this.trans; }
    public String getMpg(){ return this.mpg; }
    public boolean avalible() { return this.avalible; }
    public int getId() { return this.id; }

    // set avalibility
    public void setAvalible(boolean b)
    {
        this.avalible = b;
    }

    /*pricing scheme for vehicles
    Economy
    Compact
    Standard
    SUV
    Pickup
    Minivan*/
    public double calcPrice()
    {
        double price;
        if(type.equals("Economy"))
        {
            price = 24.00;
        } else if(type.equals("Compact")) {
            price = 15.00;
        } else if(type.equals("Standard")) {
            price = 20.00;
        } else if(type.equals("SUV")) {
            price = 30.00;
        } else if(type.equals("Pickup")) {
            price = 32.00;
        } else {
            price = 35.00;
        }
        return price;
    }

    @Override
    public String toString(){
        return String.format("%s %s\nType: %s\nCapacity: %s\nMpg: %s\nTransmission Type: %s\n" +
                "Price Per Day: %.2f\nId: %d\n",
            this.getMake(), this.getModel(), this.getType(),
            this.getCapacity(), this.getMpg(), this.getTrans(),
            this.calcPrice(), this.getId());
    }
}