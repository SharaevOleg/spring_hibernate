package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getByCarModelAndSeries(String carModel, int series) throws NoResultException {
      TypedQuery<Car> carTypedQuery = sessionFactory.getCurrentSession().createQuery(
              "from Car where model = :carModel and series = :series"
      );
      carTypedQuery.setParameter("carModel", carModel);
      carTypedQuery.setParameter("series", series);
      Car car = carTypedQuery.getSingleResult();

      TypedQuery<User> userTypedQuery = sessionFactory.getCurrentSession().createQuery(
              "from User where cars_series = :carSeries"
      );
      userTypedQuery.setParameter("carSeries", car.getSeries());
      return userTypedQuery.getSingleResult();
   }


}
