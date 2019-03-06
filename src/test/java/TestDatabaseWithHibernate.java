import dao.KnowledgeBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import java.util.List;


public class TestDatabaseWithHibernate {

    @Test
    public void crud() {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        delete(session);
        create(session);
        read(session);
        update(session);


    }

    private void delete(Session session) {
        System.out.println("Deleting record...");
        KnowledgeBase knowledgeBase = session.get(KnowledgeBase.class, "2+3");

        session.beginTransaction();
        try {
            session.delete(knowledgeBase);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        session.getTransaction().commit();
    }

    private void create(Session session) {
        System.out.println("Create record...");

        KnowledgeBase knowledgeBase = new KnowledgeBase ("2+3",5.0);

        session.beginTransaction();
        session.save(knowledgeBase);
        session.getTransaction().commit();
    }

    private void read(Session session) {
        Query q = session.createQuery("select _KnowledgeBase from KnowledgeBase _KnowledgeBase");

        List<KnowledgeBase> knowledgeBases = q.list();

        System.out.println("Reading records...");
        for (KnowledgeBase knowledgeBase : knowledgeBases) {
            System.out.println(knowledgeBase.getExpression() + " = " + knowledgeBase.getResult());
        }
    }

    private void update(Session session) {
        System.out.println("Updating record...");
        KnowledgeBase knowledgeBase = session.get(KnowledgeBase.class, "2+3");
        knowledgeBase.setResult(5.0);

        session.beginTransaction();
        session.saveOrUpdate(knowledgeBase);
        session.getTransaction().commit();
    }

}
