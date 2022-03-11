package logic;

import com.google.inject.Inject;
import data.Sample;
import repository.ISampleRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic implements ILogic {
    private final Logger _log;
    private final ISampleRepository _sampleRepo;

    /**
     * Creates Logic object for the project
     * @param log Logger log
     * @param sampleRepo ISampleRepository sampleRepo
     * @exception NullPointerException
     */
    @Inject
    public Logic(Logger log, ISampleRepository sampleRepo) {
        if ((_log = log) == null) throw new NullPointerException("log");
        if ((_sampleRepo = sampleRepo) == null) throw new NullPointerException("sampleRepo");
    }

    @Override
    public void addOneSample(String value1, int value2) {
        var newSample = new Sample();
        newSample.setValue1(value1);
        newSample.setValue2(value2);
        _sampleRepo.insert(newSample);
        _log.log(Level.INFO, "New sample object created");
    }
}
