package controllers;

import models.Chamada;
import models.Cliente;
import models.Usuario;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application extends Controller {

    @Transactional
    public static Result index() {
        String email = session().get("email");
        Usuario u = Sistema.getUsuario(email);
        if(u!=null){
            if(u.getTipo()==1){
                session().clear();
                session().put("email",email);
                return ok();
            }else{
                session().clear();
                session().put("email",email);
                return renderListChamadasPendentesDoUsuario(1);
            }
        }
        return ok(login.render(""));
    }

    @Transactional
    public static Result logout(){
        session().clear();
        return index();
    }

    @Transactional
    public static Result telaBloqueio(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(lockscreen.render(u));
    }

    @Transactional
    public static Result enter(){
        DynamicForm r = Form.form().bindFromRequest();
        String senha = r.get("senha");
        Usuario u = Sistema.getUsuario(session().get("email"));
        if(u.getSenha().equals(senha)){
            if(u.getTipo()==1){
                return ok();
            }else{
                return renderListChamadasPendentesDoUsuario(1);
            }

        }
        return telaBloqueio();
    }

    @Transactional
    public static Result renderRegister(){
        return ok(register.render(""));
    }

    @Transactional
    public static Result auth(){
        DynamicForm r = Form.form().bindFromRequest();
        String email, senha;
        email = r.get("email");
        senha = r.get("senha");
        Usuario u = Sistema.getUsuario(email);
        if(u!=null){
            if(u.getTipo()==1){
                session().clear();
                session().put("email",email);
                return ok();
            }else{
                session().clear();
                session().put("email",email);
                return renderListChamadasPendentesDoUsuario(1);
            }
        }
        return index();
    }

    @Transactional
    public static Result register(){
        DynamicForm r = Form.form().bindFromRequest();
        String nome, email,senha;
        nome = r.get("nome");
        email = r.get("email");
        senha = r.get("senha");
        Usuario u = new Usuario(nome,email,senha,0);
        if(Sistema.addUsuario(u)){
            return index();
        }
        return renderRegister();
    }

    @Transactional
    public static Result renderListChamadasPendentesDoUsuario(int ind){
        Logger.info("INDICE:"+ ind);
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Chamada> l = Sistema.getListaChamadasPendentesDoUsuario(u);
        if(l!=null && u!=null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashUser.render(u,l));
            } else if (resto == 0 && ind != Sistema.getIndiceChamadasPendentesDoUsuario(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUser.render(u,ln));
            } else if (resto != 0 && ind != Sistema.getIndiceChamadasPendentesDoUsuario(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUser.render(u,ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUser.render(u,ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result renderListChamadasGeralDoUsuario(int ind){
        Logger.info("INDICE:"+ ind);
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Chamada> l = Sistema.getListaDeChamadasGeraldoUsuario(u);
        if(l!=null && u!=null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashUserListChamadas.render(u,l));
            } else if (resto == 0 && ind != Sistema.getIndiceChamadasDoUsuario(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserListChamadas.render(u,ln));
            } else if (resto != 0 && ind != Sistema.getIndiceChamadasDoUsuario(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserListChamadas.render(u,ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserListChamadas.render(u,ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result renderListChamadasGeralAdmin(int ind){
        Logger.info("INDICE:"+ ind);
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Chamada> l = Sistema.getListaGeralDeChamadas();
        if(l!=null && u!=null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashAdminChamadas.render(u,l));
            } else if (resto == 0 && ind != Sistema.getIndiceChamadasAdminTable()) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminChamadas.render(u,ln));
            } else if (resto != 0 && ind != Sistema.getIndiceChamadasAdminTable()) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminChamadas.render(u,ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminChamadas.render(u,ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result renderListClientesGeralAdmin(int ind){
        Logger.info("INDICE:"+ ind);
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Cliente> l = Sistema.getListaGeralDeClientes();
        if(l!=null && u!=null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashAdminClientes.render(u,l));
            } else if (resto == 0 && ind != Sistema.getIndiceClientesAdminTable()) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Cliente> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminClientes.render(u,ln));
            } else if (resto != 0 && ind != Sistema.getIndiceClientesAdminTable()) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Cliente> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminClientes.render(u,ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Cliente> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminClientes.render(u,ln));
            }
        }
        return ok();
    }


    @Transactional
    public static Result renderAdd(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(dashUserAddChamada.render(u));
    }

    @Transactional
    public static Result addChamada(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        DynamicForm r = Form.form().bindFromRequest();
        String nome = r.get("nome");
        String num1 = r.get("numero1");
        String end = r.get("endereco");
        String cid = r.get("cidade");
        String uf = r.get("uf");
        String plan = r.get("plano");
        String nota = r.get("notas");
        String cpf = r.get("cpf");
        String rg = r.get("rg");
        String hi = r.get("horainicial");
        String data = r.get("data");
        String hora = r.get("hora");
        String hf = new Date().toLocaleString();
        int stat = Integer.parseInt(r.get("status"));
        Sistema.addChamada(nome,num1,end,cid,uf,plan,nota,cpf,rg,hi,hf,stat,u,data,hora);
        return renderListChamadasPendentesDoUsuario(1);
    }



}
