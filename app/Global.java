import models.Chamada;
import models.Cliente;
import models.GenericDAO;
import models.Usuario;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by caio on 24/03/15.
 */

public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();

    @Override
    public void onStart(Application app) {
        Logger.info("inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {

            public void invoke() throws Throwable {

                List<Usuario> lis = dao.findAllByClassName(Usuario.class.getName());
                if (lis.size() == 0) {
                    Usuario u = new Usuario("Administrador", "a@a.com", "a",1);

                    Usuario t1 = new Usuario("t1", "t1","t1",0);
                    Usuario t2 = new Usuario("t2", "t2","t2",0);

                    dao.persist(u);
                    dao.persist(t1);
                    dao.persist(t2);

                    for(int i=0;i<500;i++){
                        Cliente c = new Cliente("c"+i,"8888-8888","","","Rua "+i,"Campina Grande",
                                "PB", "PLANO 1", "106.986.464-10", "");
                        dao.persist(c);
                        Chamada ch = new Chamada(c, "okok","","",0,t1,new GregorianCalendar(),"","");
                        dao.persist(ch);
                    }

                    for(int i=0;i<1000;i++){
                        Cliente cl = new Cliente("cl"+i,"8888-8888","","","Rua "+i,"Campina Grande",
                                "PB", "PLANO 1", "106.986.464-10", "");
                        dao.persist(cl);
                        Chamada cha = new Chamada(cl, "okok","","",0,t2,new GregorianCalendar(),"","");
                        dao.persist(cha);
                    }


                    dao.flush();
                    Logger.info("Inserindo dados no BD.");
                }
            }
        });
    }

    public void onStop(Application app) {
        Logger.info("desligada...");
    }

}