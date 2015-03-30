package controllers;

import models.*;
import play.db.jpa.Transactional;

import java.util.*;

/**
 * Created by Caio on 28/03/2015.
 */
public class Sistema {

    private static GenericDAO dao = new GenericDAO();

    @Transactional
    public static Usuario getUsuario(Long id) {
        List<Usuario> l = dao.findByAttributeName(Usuario.class.getName(), "id", String.valueOf(id));
        if (l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Transactional
    public static Usuario getUsuario(String email) {
        List<Usuario> l = dao.findByAttributeName(Usuario.class.getName(), "email", email);
        if (l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Transactional
    public static boolean addUsuario(Usuario u) {
        if (u != null) {
            dao.persist(u);
            dao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public static boolean removeUsuario(Long id) {
        Usuario u = getUsuario(id);
        if (u != null) {
            dao.remove(u);
            dao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public static boolean addChamada(Chamada c) {
        if (c != null) {
            dao.persist(c);
            dao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public static Chamada getChamada(Long id) {
        List<Chamada> l = dao.findByAttributeName(Chamada.class.getName(), "id", String.valueOf(id));
        if (l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Transactional
    public static boolean removeChamada(Long id) {
        Chamada c = getChamada(id);
        if (c != null) {
            dao.remove(c);
            dao.flush();
            return true;
        }
        return false;
    }


    @Transactional
    public static boolean addCliente(Cliente c) {
        if (c != null) {
            dao.persist(c);
            dao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public static Cliente getCliente(Long id) {
        List<Cliente> l = dao.findByAttributeName(Cliente.class.getName(), "id", String.valueOf(id));
        if (l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Transactional
    public static Cliente getCliente(String cpf) {
        List<Cliente> l = dao.findByAttributeName(Cliente.class.getName(), "cpf", cpf);
        if (l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Transactional
    public static boolean removeCliente(Long id) {
        Cliente c = getCliente(id);
        if (c != null) {
            dao.remove(c);
            dao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public static List<Cliente> getListaGeralDeClientes() {
        return dao.findAllByClassName(Cliente.class.getName());
    }

    @Transactional
    public static List<Chamada> getListaGeralDeChamadas() {
        return dao.findAllByClassName(Chamada.class.getName());
    }

    @Transactional
    public static List<Usuario> getListaGeralDeUsuarios() {
        return dao.findAllByClassName(Usuario.class.getName());
    }

    @Transactional
    public static List<Chamada> getListaDeChamadasGeraldoUsuario(Usuario u) {
        return dao.findByAttributeName(Chamada.class.getName(), "idOperador", String.valueOf(u.getId()));
    }

    @Transactional
    public static void addChamada(String nome, String num1, String endereco, String cidade, String uf, String plano,
                                  String nota, String cpf, String rg, String horainicial, String horafinal,
                                  int status, Usuario operador, String dt, String h) {
        Cliente cliente = new Cliente(nome, num1, null, null, endereco, cidade,
                uf, plano, cpf, rg);
        if (addCliente(cliente)) {
            Chamada c = new Chamada(cliente, nota, horainicial, horafinal, status, operador, new GregorianCalendar(), dt, h);
            addChamada(c);
        }
    }

    @Transactional
    public static List<Chamada> getListaChamadasPendentesDoUsuario(Usuario u) {
        List<Chamada> l = getListaDeChamadasGeraldoUsuario(u);
        List<Chamada> li = new ArrayList<>();
        for (Chamada c : l) {
            int k = c.getStatus();
            if (k == 0 || k == 1 || k == 2 || k == 3 || k == 6) {
                li.add(c);
            }
        }
        return li;
    }

    @Transactional
    public static int getIndiceChamadasPendentesDoUsuario(Usuario u) {
        List<Chamada> l = getListaChamadasPendentesDoUsuario(u);
        int total = l.size();
        int resto = total % 30;
        float conta = ((float) l.size()) / (float) 30;
        if (conta > 1 && resto == 0) {
            return (l.size() / 30);
        } else if (conta > 1 && resto != 0) {
            return (l.size() / 30) + 1;
        } else {
            return 1;
        }
    }

    @Transactional
    public static int getPorcentagemChamadasTotaisDoUsuario(Usuario u) {
        int tu = getListaDeChamadasGeraldoUsuario(u).size();
        int t = getListaGeralDeChamadas().size();
        if (t != 0) {
            return ((tu * 100) / t);
        }
        return 0;
    }

    @Transactional
    public static int getPorcentagemChamadasPendentsDoUsuario(Usuario u) {
        int tu = getListaChamadasPendentesDoUsuario(u).size();
        int t = getListaDeChamadasGeraldoUsuario(u).size();
        if (t != 0) {
            return ((tu * 100) / t);
        }
        return 0;
    }

    @Transactional
    public static List<Chamada> getListaDeChamadasAgendadasDoUsuario(Usuario u) {
        List<Chamada> l = getListaDeChamadasGeraldoUsuario(u);
        List<Chamada> li = new ArrayList<>();
        for (Chamada c : l) {
            if (c.getStatus() == 1) {
                li.add(c);
            }
        }
        return li;
    }

    @Transactional
    public static int getPorcentagemChamadasAgendadasDoUsuario(Usuario u) {
        int tu = getListaDeChamadasAgendadasDoUsuario(u).size();
        int t = getListaDeChamadasGeraldoUsuario(u).size();
        if (t != 0) {
            return ((tu * 100) / t);
        }
        return 0;
    }

    @Transactional
    public static List<Chamada> getListaDeChamadasFechadasDoUsuario(Usuario u) {
        List<Chamada> l = getListaDeChamadasGeraldoUsuario(u);
        List<Chamada> li = new ArrayList<>();
        for (Chamada c : l) {
            if (c.getStatus() == 8) {
                li.add(c);
            }
        }
        return li;
    }

    @Transactional
    public static int getPorcentagemChamadasFechadasDoUsuario(Usuario u) {
        int tu = getListaDeChamadasFechadasDoUsuario(u).size();
        int t = getListaDeChamadasGeraldoUsuario(u).size();
        if (t != 0) {
            return ((tu * 100) / t);
        }
        return 0;
    }

    @Transactional
    public static int getIndiceChamadasAdminTable() {
        List<Chamada> l = getListaGeralDeChamadas();
        int total = l.size();
        int resto = total % 30;
        float conta = ((float) l.size()) / (float) 30;
        if (conta > 1 && resto == 0) {
            return (l.size() / 30);
        } else if (conta > 1 && resto != 0) {
            return (l.size() / 30) + 1;
        } else {
            return 1;
        }
    }

    @Transactional
    public static int getIndiceChamadasDoUsuario(Usuario u) {
        List<Chamada> l = getListaDeChamadasGeraldoUsuario(u);
        int total = l.size();
        int resto = total % 30;
        float conta = ((float) l.size()) / (float) 30;
        if (conta > 1 && resto == 0) {
            return (l.size() / 30);
        } else if (conta > 1 && resto != 0) {
            return (l.size() / 30) + 1;
        } else {
            return 1;
        }
    }

    public static String getDataCurrent(){
        return new Date().toLocaleString();
    }

    @Transactional
    public static int getIndiceClientesAdminTable() {
        List<Cliente> l = getListaGeralDeClientes();
        int total = l.size();
        int resto = total % 30;
        float conta = ((float) l.size()) / (float) 30;
        if (conta > 1 && resto == 0) {
            return (l.size() / 30);
        } else if (conta > 1 && resto != 0) {
            return (l.size() / 30) + 1;
        } else {
            return 1;
        }
    }

    @Transactional
    public static float getEfetividadeDoUsuario(Usuario u) {
        int tco = getListaDeChamadasGeraldoUsuario(u).size();
        int tcfo = getListaDeChamadasFechadasDoUsuario(u).size();
        if (tco != 0) {
            return (((float) tcfo * 100) / (float) tco);
        }
        return 0;
    }

    @Transactional
    public static float getEfetividadeChamadasTotaisDoUsuario(Usuario u) {
        int tc = getListaGeralDeChamadas().size();
        int tco = getListaDeChamadasGeraldoUsuario(u).size();
        if (tc != 0) {
            float c =  (((float) tco * 100) / (float) tc);
            return (int) c;
        }
        return 0;
    }

    @Transactional
    public static int getNumRowsDiv() {
        int to = getListaGeralDeUsuarios().size();
        int resto = to % 4;
        float conta = ((float) to) / (float) 4;
        if (conta > 1 && resto == 0) {
            return (to / 4);
        } else if (conta > 1 && resto != 0) {
            return (to / 4) + 1;
        } else {
            return 1;
        }
    }

    @Transactional
    public static boolean hasResto(){
        int to = getListaGeralDeUsuarios().size();
        int resto = to % 4;
        float conta = ((float) to) / (float) 4;
        if (conta > 1 && resto == 0) {
            return false;
        } else if (conta > 1 && resto != 0) {
            return true;
        } else {
            return false;
        }
    }


    public static String gerColorRandom(){
        List<String> l = new ArrayList<>();
        l.add("#3c8dbc");
        l.add("#f56954");
        l.add("#8dbc3c");
        l.add("#bc3c8d");
        l.add("#6b3cbc");
        l.add("#3cbc6b");
        l.add("#03f55c");
        l.add("#a32c60");
        l.add("#00a65a");
        l.add("#00c0ef");
        Random gerador = new Random();
        int x = gerador.nextInt(10);
        return l.get(x);
    }

    @Transactional
    public static boolean addPlano(String text, double value) {
        Plano p = new Plano(text, value);
        if (p != null) {
            dao.persist(p);
            dao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public static Plano getPlano(Long id) {
        List<Plano> l = dao.findByAttributeName(Plano.class.getName(), "id", String.valueOf(id));
        if (l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Transactional
    public static boolean removePlano(Long id) {
        Plano p = getPlano(id);
        if (p != null) {
            dao.remove(p);
            dao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public static void mergeCliente(Cliente c) {
        dao.merge(c);
        dao.flush();
    }

    @Transactional
    public static void mergeChamada(Chamada c) {
        dao.merge(c);
        dao.flush();
    }
}
