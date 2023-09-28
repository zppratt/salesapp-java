// CustomerController.java (Controller)
public class CustomerController {
    private Customer model;

    public CustomerController(Customer model) {
        this.model = model;
    }

    public void setCustomerFirstName(String firstName) {
        model.setFirstName(firstName);
    }

    public void setCustomerLastName(String lastName) {
        model.setLastName(lastName);
    }

    public String getCustomerFullName() {
        return model.getFirstName() + " " + model.getLastName();
    }
}
