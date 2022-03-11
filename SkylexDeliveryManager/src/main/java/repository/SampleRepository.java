package repository;

import com.google.inject.Inject;
import data.Sample;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class SampleRepository extends RepositoryBase<Sample> implements ISampleRepository{

    @Inject
    public SampleRepository(Logger log, SessionFactory sessionFactory) {
        super(log, sessionFactory);
    }

    @Override
    public void update(int id, String value1, int value2) {
        var session = _sessionFactory.openSession();

        // Todo: Implement method

        session.close();
    }
}
