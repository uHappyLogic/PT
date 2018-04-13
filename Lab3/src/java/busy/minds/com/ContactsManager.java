package busy.minds.com;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import javax.faces.bean.ManagedBean;

@SessionScoped
@ManagedBean(name="ContactsManager")
public class ContactsManager implements Serializable {
    
    private static final long serialVersionUID = 1L;
    String name;
    String surname;
    String city;
    String telephone;
    String group;
    
    private boolean sortAscending = true;
    
    public String sortBySurname() {
	    
	   if(sortAscending){
		//ascending order
		Collections.sort(contactList, new Comparator<Contact>() {
			@Override
			public int compare(Contact c1, Contact c2) {
				return c1.getSurname().compareTo(c2.getSurname());
			}
		});
		sortAscending = false;
	   }else{
		//descending order
		Collections.sort(contactList, new Comparator<Contact>() {
			@Override
			public int compare(Contact c1, Contact c2) {
				return c2.getSurname().compareTo(c1.getSurname());		
			}
		});
		sortAscending = true;
	   }

	   return null;
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
    private static final ArrayList<Contact> contactList = new ArrayList<>(
        Arrays.asList(
            new Contact("Tom", "Hardy", "Bromberg", "12533378", "Friends"),
            new Contact("Christian", "Bale", "Danzig", "232323148", "Others"),
            new Contact("Heath", "Ledger", "Posen", "35312674", "Family")
    ));
    
    public ArrayList<Contact> getContactList(){
        return contactList;
    };
    
    public String addAction(){
        Contact contact = new Contact(this.name, this.surname, this.city,
            this.telephone, this.group);
                contactList.add(contact);
                return null;        
            }
    
    public String deleteAction(Contact contact){
                contactList.remove(contact);
                return null;        
            }
    
    public String editAction(Contact contact){
        contact.setEditable(true);
        return null;
    }
    
    public String saveAction(){
        for(Contact contact : contactList){
            contact.setEditable(false);
        }
        return null;
    }
}
