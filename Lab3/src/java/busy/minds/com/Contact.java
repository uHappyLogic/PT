package busy.minds.com;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;


@RequestScoped
@ManagedBean
public class Contact implements Serializable {
    
    @ManagedProperty(value = "#{contactsManager}")
    ContactsManager contactsManager;
    
    private static final long serialVersionUID = 1L;
    
    String name;
    String surname;
    String city;
    String telephone;
    String group;
        
    boolean editable;
     
    public Contact() {
        
    } 
     
    public Contact(String name, String surname, String city, String telephone, String group){
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.telephone = telephone;
        this.group = group;
    } 
     
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
         
    public String addAction(){
        System.err.println("Conta is null" + contactsManager == null ? "yes" : "no");
        contactsManager.getContactList().add(this);
        return null;        
    }
    
    public boolean isEditable(){
        return editable;
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
    
    public void setContactsManager(ContactsManager contactsManager) {
        this.contactsManager = contactsManager;
    }
}    
