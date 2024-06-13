package com.tps.repositories.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tps.pojo.*;
import com.tps.repositories.MissingReportRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class MissingReportRepositoryImpl implements MissingReportRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public MissingReport getMissingByStudentMission(int studentId, int missionId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MissingReport> criteria = builder.createQuery(MissingReport.class);
        Root<MissingReport> root = criteria.from(MissingReport.class);
        criteria.select(root);

        criteria.where(builder.equal(root.get("student"), studentId));
        criteria.where(builder.equal(root.get("mission"), missionId));

        Query<MissingReport> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    @Override
    public void updateMissingReport(MissingReport missingreport) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.update(missingreport);
    }

    @Override
    public List<Object[]> getMissionReportByFaculty(int facultyId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Student> root = criteria.from(Student.class);
        Join<Student, User> userJoin = root.join("user");
        Join<Student, Faculty> facultyJoin = root.join("faculty");
        Join<Student, MissingReport> missingReportJoin = root.join("missingReports");
        Join<MissingReport, Mission> missionJoin = missingReportJoin.join("mission");

        criteria.multiselect(
                userJoin.get("lastName"),
                userJoin.get("lastName"),
                root.get("studentId"),
                missionJoin.get("id"),
                missingReportJoin.get("updatedDate"),
                missingReportJoin.get("isActive")
        );

        criteria.where(builder.equal(facultyJoin.get("id"), facultyId));

        criteria.orderBy(builder.asc(missionJoin.get("updatedDate")));

        criteria.groupBy(userJoin.get("lastName"),
                userJoin.get("lastName"),
                root.get("studentId"),
                missionJoin.get("id"),
                missingReportJoin.get("updatedDate"),
                missingReportJoin.get("isActive"));

        Query query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public MissingReport addMissingReport(MissingReport missingreport) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.save(missingreport);
        return missingreport;
    }

    @Override
    public void uploadMissingImages(List<MultipartFile> files, int missing_id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
//        MissingReport missingReport = session.get(MissingReport.class, missing_id);
        files.forEach(file -> {
            MissingReportImage missingReportImage = new MissingReportImage();
            Map res = null;
            try {
                res = this.cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            missingReportImage.setUrl((String) res.get("secure_url"));
            missingReportImage.setMissingReport(missing_id);
            session.save(missingReportImage);
        });
    }
}

