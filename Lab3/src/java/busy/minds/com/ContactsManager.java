package busy.minds.com;

import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class ContactsManager {
    
    String name;
    String surname;
    String city;
    String telephone;
    String group;
    
    private int sortDirection = 1;
    
    public void sortBySurname() {
	    
        contactList.sort(
            (Contact c1, Contact c2) -> c1.getSurname().compareTo(c2.getSurname()) * sortDirection
        );

        sortDirection *= -1;
    }
    
    @PostConstruct
    public void fillDb() {
        contactList = new ArrayList<>(
            Arrays.asList(
                new Contact("Tom", "Hardy", "Bromberg", "12533378", "Friends"),
                new Contact("Christian", "Bale", "Danzig", "232323148", "Others"),
                new Contact("Heath", "Ledger", "Posen", "35312674", "Family")
        ));
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getTelephone(){
        return telephone;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public String getGroup(){
        return group;
    }
    public void setGroup(String group){
        this.group = group;
    }
    
    //name, surname, city, telephone nr, group
    private ArrayList<Contact> contactList;
    
    public ArrayList<Contact> getContactList(){
        return contactList;
    };
    
    public String deleteAction(Contact contact){
                contactList.remove(contact);
                return null;        
            }
    
    public void editAction(Contact contact){
        contact.setEditable(true);
    }
    
    public String saveAction(){
        for(Contact contact : contactList){
            contact.setEditable(false);
        }
        return null;
    }
}
