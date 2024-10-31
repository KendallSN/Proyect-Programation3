package cr.ac.una.sigeceunacomunicationws.service;

import cr.ac.una.sigeceunacomunicationws.model.User;
import cr.ac.una.sigeceunacomunicationws.model.UserDto;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class UserService {
private static final Logger LOG = Logger.getLogger(UserService.class.getName());
@PersistenceContext(unitName="SigeceUNAComunicationWsPU")
private EntityManager em;
    public LocalResponse getUserByID(Long ID) {
        try {
            Query qryUser = em.createNamedQuery("User.findByUsrId", User.class);
            qryUser.setParameter("usrId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "User", new UserDto((User) qryUser.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "User with the ID does not exist.", "getUserByID NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "getUserByID NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "getUserByID " + ex.getMessage());
        }
    }

    public LocalResponse getUserByEmail(String email) {
        try {
            Query qryEmpleado = em.createNamedQuery("User.findByUsrEmail", User.class);
            qryEmpleado.setParameter("usrEmail", email);

            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "User", new UserDto((User) qryEmpleado.getSingleResult()));

        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "User with the email does not exist.", "getUserByEmail NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "getUserByEmail NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "getUserByEmail " + ex.getMessage());
        }
    }

    public LocalResponse saveUser(UserDto userDto) {
        try {
           User user;
           if (userDto.getUsrId()!= null && userDto.getUsrId()> 0) {
               user = em.find(User.class, userDto.getUsrId());
               if (user == null) {
                   return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "User to update not found.", "saveUser NoResultException");
               }
               user.update(userDto);
               user = em.merge(user);
           } else {
               user = new User(userDto);
               em.persist(user);
           }
           em.flush();
           return new LocalResponse(true, ResponseCode.CORRECT, "", "", "User", new UserDto(user));
       } catch (Exception ex) {
           LOG.log(Level.SEVERE, "An error ocurred while saving the user.", ex);
           return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the user.", "saveUser " + ex.getMessage());
       }
    }
    public LocalResponse isUserWithEmail(String email) {
        try {
            Query qryActividad = em.createNamedQuery("User.findByUsrEmail", User.class);
            qryActividad.setParameter("usrEmail", email);

            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "User", new UserDto((User) qryActividad.getSingleResult()));

        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There is no user with the email entered", "isUserWithEmail NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "isUserWithEmail NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "isUserWithEmail " + ex.getMessage());
        }
    }
    public LocalResponse getUsers(){
           try {
            Query qryUsers = em.createNamedQuery("User.findAll", User.class);
            List<User> users = qryUsers.getResultList();
            List<UserDto> usersDto = new ArrayList<>();
            for (User user : users) {
                usersDto.add(new UserDto(user));
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Users", usersDto);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no users.", "getUsers NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the users.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the users.", "getUsers " + ex.getMessage());
        }
    }
}
