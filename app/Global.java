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
                    Usuario u = new Usuario("Administrador", "administrador@administrador.com", "administrador",1);

                    Usuario t1 = new Usuario("Teste", "teste@teste.com","teste",0);

                    dao.persist(u);
                    dao.persist(t1);
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