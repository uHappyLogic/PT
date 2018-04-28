/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busy.minds.com;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class UsersController {

    private EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("ModelPU");
        }
        return emf.createEntityManager();
    }

    public List<Users> getUsers() {
        EntityManager em = getEntityManager();
        try {
            javax.persistence.Query q = em.createNamedQuery("Users.findAll", Users.class);
            List<Users> result;
            result = q.getResultList();
            return result;
        } finally {
            em.close();
        }
    }

    public void addUser(Users user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    public void deleteUser(Users user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Users userToDelete = em.find(Users.class, user.getId());
            em.remove(userToDelete);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateUser(Users user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Users userToUpdate = em.find(Users.class, user.getId());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setEmail(user.getEmail());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Credentials> getCredentials() {
        EntityManager em = getEntityManager();
        try {
            javax.persistence.Query q = em.createNamedQuery("Credentials.findAll", Credentials.class);
            List<Credentials> result;
            result = q.getResultList();
            return result;
        } finally {
            em.close();
        }
    }

    public void deleteCredential(Credentials credential) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Credentials credentialToDelete = em.find(Credentials.class, credential.getId());
            em.remove(credentialToDelete);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateCredential(Credentials credential) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Credentials credentialToUpdate = em.find(Credentials.class, credential.getId());
            credentialToUpdate.setName(credential.getName());
            credentialToUpdate.setDescription(credential.getDescription());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void addCredential(Credentials credential) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(credential);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void addCredentialToUser(UsersCreds usercred) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usercred);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void removeCredentialFromUser(UsersCreds usercred) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            UsersCreds credentialToDelete = em.find(UsersCreds.class, usercred.getId());
            em.remove(credentialToDelete);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<UsersCreds> getCredentialsForUser(short userId) {

        EntityManager em = getEntityManager();
        try {
            javax.persistence.Query q = em.createNamedQuery("Usercred.findByUserId", UsersCreds.class).setParameter("id", userId);
            List<UsersCreds> result;
            result = q.getResultList();
            return result;
        } finally {
            em.close();
        }
    }

    public void removeAllCredentialsFromUser(short id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            javax.persistence.Query q = em.createNamedQuery("Usercred.deleteByUserId", UsersCreds.class).setParameter("id", id);
            q.executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void addMissingCredentials(List<UsersCreds> credentials) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (UsersCreds c : credentials) {
                em.persist(c);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
