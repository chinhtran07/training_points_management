package com.tps.repositories.impl;


import com.tps.repositories.StatsRepository;
import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.ParameterMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Map<String, Object>> statsTrainingPoint(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        // Execute the stored procedure by creating temporary tables and selecting the final result

        return session.createStoredProcedureQuery("CALL CalculateStudentPoints()").getResultList();
    }
}