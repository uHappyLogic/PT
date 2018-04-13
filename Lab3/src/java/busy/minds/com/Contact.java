package busy.minds.com;


public class Contact {
    
    String name;
    String surname;
    String city;
    String telephone;
    String group;
        
     boolean editable;
     public boolean isEditable(){
         return editable;
     }
     
     public void setEditable(boolean editable) {
		this.editable = editable;
	}
        
        public Contact(String name, String surname, String city, String telephone,
                String group){
            this.name = name;
            this.surname = surname;
            this.city = city;
            this.telephone = telephone;
            this.group = group;
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
    }    
