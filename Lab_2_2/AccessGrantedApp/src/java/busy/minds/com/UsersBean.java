/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busy.minds.com;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.RowEditEvent;


@ManagedBean(name = "users")
@SessionScoped
public class UsersBean implements Serializable {

    private List<Users> users;
    private List<Credentials> credentials;
    private List<CredentialViewModel> credentialsVM;
    private final UsersController uc = new UsersController();
    private String username;
    private String password;
    private String email;
    private String credentialName;
    private String credentialDescription;

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public String getCredentialDescription() {
        return credentialDescription;
    }

    public void setCredentialDescription(String credentialDescription) {
        this.credentialDescription = credentialDescription;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Users> getUsers() {
        if (users == null || users.isEmpty()) {
            users = uc.getUsers();
        }
        return users;
    }

    public void onRowEdit(RowEditEvent event) {
        Users updatedUser = (Users) event.getObject();
        uc.updateUser(updatedUser);
    }

    public void addUser() {
        uc.addUser(new Users(username, password, email));
        users.clear();
        this.username = "";
        this.password = "";
        this.email = "";
    }

    public void removeUser(Users user) {
        uc.deleteUser(user);
        users.clear();
    }

    public void editCredential(short id) {
        Users user = uc.getUsers().stream().filter(x -> x.getId().equals(id)).findFirst().get();
        List<Userscred> userCred = uc.getCredentialsForUser(id);
        credentialsVM = new ArrayList<>();
        List<Credentials> allCredentials = uc.getCredentials();
        for (Credentials cred : allCredentials) {
            CredentialViewModel credVM = new CredentialViewModel();
            credVM.setId(cred.getId());
            credVM.setName(cred.getName());
            credVM.setUserId(user.getId());
            boolean isSelected = userCred.stream().filter(x -> x.getCredentialId().getId().equals(cred.getId())).findFirst().isPresent();
            credVM.setIsSelected(isSelected);
            credentialsVM.add(credVM);
        }
    }

    public List<CredentialViewModel> getCredentialsVM() {
        return credentialsVM;
    }

    public List<Credentials> getCredentials() {
        if (credentials == null || credentials.isEmpty()) {
            credentials = uc.getCredentials();
        }
        return credentials;
    }

    public void addCredential() {
        uc.addCredential(new Credentials(credentialName, credentialDescription));
        credentials.clear();
        this.credentialName = "";
        this.credentialDescription = "";
    }

    public void onCredRowEdit(RowEditEvent event) {
        Credentials updatedCredential = (Credentials) event.getObject();
        uc.updateCredential(updatedCredential);
    }

    public void removeCredential(Credentials credential) {
        uc.deleteCredential(credential);
        credentials.clear();
    }

    public void saveUsersCredentials() {
        short userId = credentialsVM.stream().findFirst().get().getUserId();
        Users user = users.stream().filter(x -> x.getId().equals(userId)).findFirst().get();
        List<Userscred> userCreds = uc.getCredentialsForUser(userId);;
        List<Credentials> allCredentials = uc.getCredentials();

        for (CredentialViewModel cvm : credentialsVM) {
            boolean userHasCredential = userCreds.stream().filter(x -> x.getCredentialId().getId().equals(cvm.getId())).findFirst().isPresent();
            if (cvm.isIsSelected()) {
                if (!userHasCredential) {
                    Credentials cred = allCredentials.stream().filter(x -> x.getId().equals(cvm.getId())).findFirst().get();
                    uc.addCredentialToUser(new Userscred(user, cred));
                }
            } else {
                if (userHasCredential) {
                    Userscred usercred = userCreds.stream().filter(x -> x.getCredentialId().getId().equals(cvm.getId())).findFirst().get();
                    uc.removeCredentialFromUser(usercred);
                }
            }
        }
        users.clear();
    }

    public void addAdminCredentials() {
        short userId = credentialsVM.stream().findFirst().get().getUserId();
        Users user = users.stream().filter(x -> x.getId().equals(userId)).findFirst().get();
        List<Userscred> userCreds = uc.getCredentialsForUser(userId);
        List<Credentials> allCredentials = uc.getCredentials();
        List<Userscred> credentialsToAdd = new ArrayList<>();
        for(Credentials c : allCredentials){
            Optional<Userscred> usercred = userCreds.stream().filter(x->x.getCredentialId()
                    .getId().equals(c.getId())).findFirst();
            if(!usercred.isPresent()){
                credentialsToAdd.add(new Userscred(user, c));
            }
        }
        if(!credentialsToAdd.isEmpty()){
            uc.addMissingCredentials(credentialsToAdd);
        }
    }

    public void removeAllCredentials() {
        short userId = credentialsVM.stream().findFirst().get().getUserId();
        uc.removeAllCredentialsFromUser(userId);
        users.clear();
    }
}
