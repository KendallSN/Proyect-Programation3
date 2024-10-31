package cr.ac.una.wssigeceuna.service;

import cr.ac.una.wssigeceuna.model.User;
import cr.ac.una.wssigeceuna.model.UserDto;
import cr.ac.una.wssigeceuna.utility.LocalResponse;
import cr.ac.una.wssigeceuna.utility.ResponseCode;
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
    @PersistenceContext(unitName="WsSigeceUNAPU")
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
    public LocalResponse getUsers(){
           try {
            Query qryUser = em.createNamedQuery("User.findAll", User.class);
            List<User> users = qryUser.getResultList();
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                UserDto userDto = new UserDto(user);
                userDtos.add(userDto);
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Users", userDtos);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no users.", "getUsers NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the users.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the users.", "getUsers " + ex.getMessage());
        }
    }
}
