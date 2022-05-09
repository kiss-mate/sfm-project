package repository;

import java.util.logging.Logger;

public class DeliveryRepository extends RepositoryBase<Delivery> implements IDeliveryRepository{
/**
    * @param log            Logger object
     * @param sessionFactory SessionFactory instance
     * @throws NullPointerException log and sessionFactory cannot be null
 */

    @inject
    public DeliveryRepository(Logger log, SessionsFactory sessionsFactory)
    @Override
    public void UpdateDelivery(int id,String name) {
        var session = _sessionFactory.openSession();
        var delivery = getById(id);
        delivery.setName(name);

        session.beginTransaction();
        session.update(oneDriver);
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void SaveDelivery(int id) {
        var session = _sessionFactory.openSession();
        var delivery = getById(id);

        session.beginTransaction();
        session.save(delivery);
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void DeleteDelivery(int id) {
        var session = _sessionFactory.openSession();
        var delivery = getById(id);

        session.beginTransaction();
        session.delete(delivery);
        session.getTransaction().commit();

        session.close();
    }
    public List<Delivery> getDelivery(){

    }
}
