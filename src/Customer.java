public class Customer {
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private String dv;
    private int age;

    public Customer(String fname, String lname, String phone, String email, String dv, int age)
    {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.dv = dv;
        this.age = age;
    }


    // getters for updating customer info
    public String getFname(){ return fname; }
    public String getLname(){ return lname; }
    public String getPhone(){ return phone; }
    public String getEmail(){ return email; }
    public String getDv(){ return dv; }
    public int getAge(){ return age; }


    // setters for updating customer info
    public void setFname(String fname){ this.fname = fname; }
    public void setLname(String lname){ this.lname = lname; }
    public void setPhone(String phone){ this.phone = phone; }
    public void setEmail(String email){ this.email = email; }
    public void setDv(String dv){ this.dv = dv; }
    public void setAge(int age){ this.age = age; }


    // returns the surcharge if your age is over 18
    public double calcSurcharge()
    {
        double surcharge = 0;

        if(age > 18)
        {
            surcharge = 25;
        }

        return surcharge;
    }
}
