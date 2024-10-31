package cr.ac.una.sigeceunasecurityws.service;

import cr.ac.una.sigeceunasecurityws.model.User;
import cr.ac.una.sigeceunasecurityws.model.UserDto;
import cr.ac.una.sigeceunasecurityws.utility.ResponseCode;
import cr.ac.una.sigeceunasecurityws.utility.Response;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class UserService {
    
    private static final Logger LOG = Logger.getLogger(UserService.class.getName());
    @PersistenceContext(unitName = "SigeceUNASecurityWsPU")
    private EntityManager em;
    
    public Response validateUser(String identification, String password) {
        try {
            Query qryValidate = em.createNamedQuery("User.findByIdentificationPassword", User.class);
            qryValidate.setParameter("usrIdentification", identification);
            qryValidate.setParameter("usrPassword", password);
            return new Response(true, ResponseCode.CORRECT, "","", "User", new UserDto((User) qryValidate.getSingleResult()));
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "User with those credentials does not exist.", "validateUser NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUser NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUser " + ex.getMessage());
        }
    }
    
    public Response validateUserWithTempPassword(String identification, String tempPassword) {
        try {
            Query qryValidate = em.createNamedQuery("User.findByIdentificationTempPassword", User.class);
            qryValidate.setParameter("usrIdentification", identification);
            qryValidate.setParameter("usrTemppassword", tempPassword);
            return new Response(true, ResponseCode.CORRECT, "", "", "User", new UserDto((User) qryValidate.getSingleResult()));
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "User with those credentials does not exist.", "validateUser NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUser NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUser " + ex.getMessage());
        }
    }
    
    public Response isUserWithEmail(String email) {
        try {
            Query qryValidate = em.createNamedQuery("User.findByUsrEmail", User.class);
            qryValidate.setParameter("usrEmail", email);

            return new Response(true, ResponseCode.CORRECT, "", "", "User", new UserDto((User) qryValidate.getSingleResult()));

        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "User with those credentials does not exist.", "validateUser NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUser NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUser " + ex.getMessage());
        }
    }
    
    public Response getUserByID(Long ID) {
        try {
            Query qryUser = em.createNamedQuery("User.findByUsrId", User.class);
            qryUser.setParameter("usrId", ID);
            return new Response(true, ResponseCode.CORRECT, "", "", "User", new UserDto((User) qryUser.getSingleResult()));
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "User with the ID does not exist.", "getUserByID NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "getUserByID NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "getUserByID " + ex.getMessage());
        }
    }
    
    public Response getUsers(){
           try {
            Query qryUsers = em.createNamedQuery("User.findAll", User.class);
            List<User> users = qryUsers.getResultList();
            List<UserDto> usersDto = new ArrayList<>();
            for (User user : users) {
                usersDto.add(new UserDto(user));
            }
            return new Response(true, ResponseCode.CORRECT, "", "", "Users", usersDto);
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "There are no users.", "getUsers NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the users.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the users.", "getUsers " + ex.getMessage());
        }
    }
    
    public Response saveUser(UserDto userDto) {
         try {
            User user;
            if (userDto.getUsrId()!= null && userDto.getUsrId()> 0) {
                user = em.find(User.class, userDto.getUsrId());
                if (user == null) {
                    return new Response(false, ResponseCode.NOTFOUND_ERROR, "User to update not found.", "saveUser NoResultException");
                }
                user.update(userDto);
                user = em.merge(user);
            } else {
                userDto.setRoleCollection(new ArrayList<>());
                user = new User(userDto);
                em.persist(user);
            }
            em.flush();
            return new Response(true, ResponseCode.CORRECT, "", "", "User", new UserDto(user));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the user.", "saveUser " + ex.getMessage());
        }
    }
    
    public Response deleteUserByID(Long ID){
        try {
            User user;
            if (ID != null && ID > 0) {
                user = em.find(User.class, ID);
                if (user == null) {
                    return new Response(false, ResponseCode.NOTFOUND_ERROR, "User to delete not found.", "deleteUserByID NoResultException");
                }
                em.remove(user);
            } else {
                return new Response(false, ResponseCode.NOTFOUND_ERROR, "You should give the ID of the user to delete.", "deleteUserByID NoResultException");
            }
            em.flush();
            return new Response(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Response(false, ResponseCode.INTERNAL_ERROR, "You can't delete the user because it have relation with other elements.", "deleteUserByID " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while deleting the user.", "deleteUserByID " + ex.getMessage());
        }
    }


    public Response validateUserRs(String identification, String passWord) {
        try {
            Query qryActividad = em.createNamedQuery("User.findByIdentificationPassword", User.class);
            qryActividad.setParameter("usrIdentification", identification);
            qryActividad.setParameter("usrPassword", passWord);

            return new Response(true, ResponseCode.CORRECT, "", "", "User", new UserDto((User) qryActividad.getSingleResult()));

        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "There is no user with the credentials entered", "validateUserRs NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUserRs NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUser " + ex.getMessage());
        }
    }
    public Response validateUserWithTempPasswordRs(String identification, String Temppassword) {
        try {
            Query qryActividad = em.createNamedQuery("User.findByIdentificationTempPassword", User.class);
            qryActividad.setParameter("usrIdentification", identification);
            qryActividad.setParameter("usrTemppassword", Temppassword);

            return new Response(true, ResponseCode.CORRECT, "", "", "User", new UserDto((User) qryActividad.getSingleResult()));

        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "There is no user with the credentials entered", "validateUserRs NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUserRs NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the user.", "validateUser " + ex.getMessage());
        }
    }
    public Response saveUserRs(UserDto userDto) {
        try {         
            User user;
            if (userDto.getUsrId() != null && userDto.getUsrId() > 0) {
                user = em.find(User.class, userDto.getUsrId());
                if (user == null) {
                    return new Response(false, ResponseCode.NOTFOUND_ERROR, "User to update not found.", "saveUserRs NoResultException");
                }
                user.update(userDto);
                user = em.merge(user);
            } else {
                user = new User(userDto);
                em.persist(user);
            }
            em.flush();
            return new Response(true, ResponseCode.CORRECT, "", "", "User", new UserDto(user));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while saving the user.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the user.", "saveUserRs" + ex.getMessage());
        }
    }
}
