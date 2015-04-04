package controllers;

import models.*;
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
        if (u != null) {
            if (u.getTipo() == 1) {
                session().clear();
                session().put("email", email);
                return renderListChamadasGeralAdmin(1);
            } else {
                session().clear();
                session().put("email", email);
                return renderListChamadasPendentesDoUsuario(1);
            }
        }
        return ok(login.render(""));
    }

    @Transactional
    public static Result logout() {
        session().clear();
        return index();
    }

    @Transactional
    public static Result renderprint() {
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(print.render(u, Sistema.getListaGeralDeUsuarios()));
    }

    @Transactional
    public static Result telaBloqueio() {
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(lockscreen.render(u));
    }

    @Transactional
    public static Result enter() {
        DynamicForm r = Form.form().bindFromRequest();
        String senha = r.get("senha");
        Usuario u = Sistema.getUsuario(session().get("email"));
        if (u.getSenha().equals(senha)) {
            if (u.getTipo() == 1) {
                return renderListChamadasGeralAdmin(1);
            } else {
                return renderListChamadasPendentesDoUsuario(1);
            }

        }
        return telaBloqueio();
    }

    @Transactional
    public static Result renderRegister() {
        return ok(register.render(""));
    }

    @Transactional
    public static Result auth() {
        DynamicForm r = Form.form().bindFromRequest();
        String email, senha;
        email = r.get("email");
        senha = r.get("senha");
        Usuario u = Sistema.getUsuario(email);
        if (u != null) {
            if (u.getTipo() == 1) {
                session().clear();
                session().put("email", email);
                return renderListChamadasGeralAdmin(1);
            } else {
                session().clear();
                session().put("email", email);
                return renderListChamadasPendentesDoUsuario(1);
            }
        }
        return index();
    }

    @Transactional
    public static Result register() {
        DynamicForm r = Form.form().bindFromRequest();
        String nome, email, senha, foto;
        nome = r.get("nome");
        email = r.get("email");
        senha = r.get("senha");
        foto = "https://cdn3.iconfinder.com/data/icons/users/100/user_male_1-512.png";
        Usuario u = new Usuario(nome, email, senha, 0, foto);
        if (Sistema.addUsuario(u)) {
            return index();
        }
        return renderRegister();
    }

    @Transactional
    public static Result renderListChamadasPendentesDoUsuario(int ind) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Chamada> l = Sistema.getListaChamadasPendentesDoUsuario(u);
        if (l != null && u != null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashUser.render(u, l));
            } else if (resto == 0 && ind != Sistema.getIndiceChamadasPendentesDoUsuario(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUser.render(u, ln));
            } else if (resto != 0 && ind != Sistema.getIndiceChamadasPendentesDoUsuario(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUser.render(u, ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUser.render(u, ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result renderOperadores() {
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(dashAdminOperadores.render(u, Sistema.getListaGeralDeUsuarios()));
    }

    @Transactional
    public static Result renderListChamadasGeralDoUsuario(int ind) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Chamada> l = Sistema.getListaDeChamadasGeraldoUsuario(u);
        if (l != null && u != null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashUserListChamadas.render(u, l));
            } else if (resto == 0 && ind != Sistema.getIndiceChamadasDoUsuario(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserListChamadas.render(u, ln));
            } else if (resto != 0 && ind != Sistema.getIndiceChamadasDoUsuario(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserListChamadas.render(u, ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserListChamadas.render(u, ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result renderListChamadasGeralAdmin(int ind) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Chamada> l = Sistema.getListaGeralDeChamadas();
        if (l != null && u != null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashAdminChamadas.render(u, l));
            } else if (resto == 0 && ind != Sistema.getIndiceChamadasAdminTable()) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminChamadas.render(u, ln));
            } else if (resto != 0 && ind != Sistema.getIndiceChamadasAdminTable()) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminChamadas.render(u, ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Chamada> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminChamadas.render(u, ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result renderListClientesGeralAdmin(int ind) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Cliente> l = Sistema.getListaGeralDeClientes();
        if (l != null && u != null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashAdminClientes.render(u, l));
            } else if (resto == 0 && ind != Sistema.getIndiceClientesAdminTable()) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Cliente> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminClientes.render(u, ln));
            } else if (resto != 0 && ind != Sistema.getIndiceClientesAdminTable()) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Cliente> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminClientes.render(u, ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Cliente> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminClientes.render(u, ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result search() {
        Usuario u = Sistema.getUsuario(session().get("email"));
        DynamicForm r = Form.form().bindFromRequest();
        String nome, cep, numero1, numero2, cpf;
        nome = r.get("q");
        cep = r.get("q");
        numero1 = r.get("q");
        numero2 = r.get("q");
        cpf = r.get("q");

        List<Cliente> lclientes = Sistema.getListaGeralDeClientes();
        List<Chamada> lchamadas = Sistema.getListaDeChamadasGeraldoUsuario(u);

        for (Cliente cliente : lclientes) {
            if (cliente.getNome().contains(nome) || cliente.getNome().toLowerCase().contains(nome)) {
                Long id = Sistema.getChamadaPorClienteID(cliente.getId()).getId();
                return redirect("/chamadas/update/" + id);
            }
            if (cliente.getCep()!=null && cliente.getCep().equals(cep)) {
                Long id = Sistema.getChamadaPorClienteID(cliente.getId()).getId();
                return redirect("/chamadas/update/" + id);
            }
            if (cliente.getCpf()!=null && cliente.getCpf().equals(cpf)) {
                Long id = Sistema.getChamadaPorClienteID(cliente.getId()).getId();
                return redirect("/chamadas/update/" + id);
            }
            if (cliente.getNumero1()!=null && cliente.getNumero1().equals(numero1)) {
                Long id = Sistema.getChamadaPorClienteID(cliente.getId()).getId();
                return redirect("/chamadas/update/" + id);
            }
            if (cliente.getNumero2()!=null && cliente.getNumero2().equals(numero2)) {
                Long id = Sistema.getChamadaPorClienteID(cliente.getId()).getId();
                return redirect("/chamadas/update/" + id);
            }
        }
        return renderError();

    }

    @Transactional
    public static Result renderAddPlano(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(dashAdminAddPlano.render(u,Sistema.getListaDePlanos()));
    }

    @Transactional
    public static Result addPlano(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        DynamicForm r = Form.form().bindFromRequest();
        String texto = r.get("plano");
        String v = r.get("valor").replaceAll(",",".");
        double valor = Double.parseDouble(v);
        Sistema.addPlano(texto,valor,0);
        return renderAddPlano();
    }

    @Transactional
    public static Result removePlano(Long id){
        Usuario u = Sistema.getUsuario(session().get("email"));
        Sistema.removePlano(id);
        return renderAddPlano();
    }

    @Transactional
    public static Result renderError() {
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(searcherror.render(u));
    }


    @Transactional
    public static Result renderAdd() {
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(dashUserAddChamada.render(u));
    }

    @Transactional
    public static Result addChamada() {
        Usuario u = Sistema.getUsuario(session().get("email"));
        DynamicForm r = Form.form().bindFromRequest();
        String nome = r.get("nome");
        String num1 = r.get("numero1");
        String num2 = r.get("numero2");
        String cep = r.get("cep");
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
        Sistema.addChamada(nome, num1, num2, end, cid, uf, plan, nota, cpf, rg, hi, hf, stat, u, data, hora,cep);
        return renderListChamadasPendentesDoUsuario(1);
    }

    @Transactional
    public static Result renderUpdateChamada(Long id) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        Chamada c = Sistema.getChamada(id);
        return ok(dashUserUpdateChamada.render(u, c));
    }

    @Transactional
    public static Result renderUpdateChamadaAdmin(Long id) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        Chamada c = Sistema.getChamada(id);
        return ok(dashAdminUpdateChamada.render(u, c));
    }

    @Transactional
    public static Result updateChamada() {
        Usuario u = Sistema.getUsuario(session().get("email"));
        DynamicForm r = Form.form().bindFromRequest();
        String end = r.get("endereco");
        String cid = r.get("cidade");
        String plan = r.get("plano");
        String nota = r.get("notas");
        String data = r.get("data");
        String hora = r.get("hora");
        int stat = Integer.parseInt(r.get("status"));
        Long id = Long.parseLong(r.get("id"));
        if (id != null) {
            Chamada c = Sistema.getChamada(id);
            Cliente cl = Sistema.getCliente(c.getIdCliente());
            if (c != null && cl != null) {
                c.setDataAgendamento(data);
                c.setHoraAgendamento(hora);
                c.setNota(nota);
                c.setStatus(stat);
                cl.setEndereco(end);
                cl.setCidade(cid);
                cl.setPlanoGVT(plan);
                Sistema.mergeCliente(cl);
                Sistema.mergeChamada(c);
                return renderListChamadasPendentesDoUsuario(1);
            }
        }
        return renderListChamadasPendentesDoUsuario(1);
    }

    @Transactional
    public static Result renderUsersControl(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(dashAdminUsuarios.render(u,Sistema.getListaGeralDeUsuarios()));
    }
    @Transactional
    public static Result deleteUsuario(Long id){
        Usuario u = Sistema.getUsuario(session().get("email"));
        Sistema.removeUsuario(id);
        return renderUsersControl();
    }

    @Transactional
    public static Result renderTrocarfoto(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(dashTrocarFoto.render(u));
    }

    @Transactional
    public static Result trocarFoto(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        DynamicForm r = Form.form().bindFromRequest();
        String url = r.get("foto");
        if(url!=null && url!=""){
            u.setUrl_foto(url);
            Sistema.mergeUsuario(u);
            if(u.getTipo()==1){
                return renderListChamadasGeralAdmin(1);
            }else{
                return renderListChamadasPendentesDoUsuario(1);
            }
        }
        return renderTrocarfoto();
    }

    @Transactional
    public static Result renderRecadosInboxUsuario(int ind) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Recado> l = Sistema.getListaDeRecadosInboxDoUsuario(u);
        if (l != null && u != null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashUserMailbox.render(u,l));
            } else if (resto == 0 && ind != Sistema.getIndiceRecadosInbox(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserMailbox.render(u,ln));
            } else if (resto != 0 && ind != Sistema.getIndiceRecadosInbox(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserMailbox.render(u,ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashUserMailbox.render(u,ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result renderRecadosEnviadosUsuario(int ind) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Recado> l = Sistema.getListaDeRecadosEnviadosDoUsuario(u);
        if (l != null && u != null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok();
            } else if (resto == 0 && ind != Sistema.getIndiceRecadosEnviados(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok();
            } else if (resto != 0 && ind != Sistema.getIndiceRecadosEnviados(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok();
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok();
            }
        }
        return ok();
    }


    @Transactional
    public static Result renderComposerRecado(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(dashUserComposeRecado.render(u));
    }

    @Transactional
    public static Result composerRecado(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        DynamicForm r = Form.form().bindFromRequest();
        Long idr = Long.parseLong(r.get("receptor"));
        String rec = r.get("mensagem");
        String titulo = r.get("titulo");
        int stats = 0;
        int tipo = 0;
        Recado recado = new Recado(u.getId(),idr,titulo,rec,stats,tipo);
        Sistema.addRecado(recado);
        return renderRecadosInboxUsuario(1);
    }

    @Transactional
    public static Result lerRecado(Long id){
        Usuario u = Sistema.getUsuario(session().get("email"));
        Recado re = Sistema.getRecado(id);
        Sistema.setStatusRecado(id,1);
        if(re!=null){
            return ok(dashUserReadMail.render(u,re));
        }
        return renderRecadosInboxUsuario(1);
    }

    @Transactional
    public static Result deleteRecado(Long id){
        Sistema.removeRecado(id);
        return renderRecadosInboxUsuario(1);
    }

    @Transactional
    public static Result renderRecadosInboxAdmin(int ind) {
        Usuario u = Sistema.getUsuario(session().get("email"));
        List<Recado> l = Sistema.getListaDeRecadosInboxDoUsuario(u);
        if (l != null && u != null) {
            int resto = (l.size()) % 30;
            if (l.size() <= 30) {
                return ok(dashAdminMailbox.render(u,l));
            } else if (resto == 0 && ind != Sistema.getIndiceRecadosInbox(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminMailbox.render(u,ln));
            } else if (resto != 0 && ind != Sistema.getIndiceRecadosInbox(u)) {
                int ini = 30 * (ind - 1);
                int k = ini + 30;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminMailbox.render(u,ln));
            } else {
                int ini = 30 * (ind - 1);
                int k = ini + resto;
                List<Recado> ln = new ArrayList<>();
                for (int i = ini; i < k; i++) {
                    ln.add(l.get(i));
                }
                return ok(dashAdminMailbox.render(u,ln));
            }
        }
        return ok();
    }

    @Transactional
    public static Result renderComposerRecadoAdmin(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        return ok(dashAdminComposeRecado.render(u));
    }

    @Transactional
    public static Result composerRecadoAdmin(){
        Usuario u = Sistema.getUsuario(session().get("email"));
        DynamicForm r = Form.form().bindFromRequest();
        Long idr = Long.parseLong(r.get("receptor"));
        String rec = r.get("mensagem");
        String titulo = r.get("titulo");
        int stats = 0;
        int tipo = 0;
        Recado recado = new Recado(u.getId(),idr,titulo,rec,stats,tipo);
        Sistema.addRecado(recado);
        return renderRecadosInboxAdmin(1);
    }

    @Transactional
    public static Result lerRecadoAdmin(Long id){
        Usuario u = Sistema.getUsuario(session().get("email"));
        Recado re = Sistema.getRecado(id);
        Sistema.setStatusRecado(id,1);
        if(re!=null){
            return ok(dashAdminReadMail.render(u,re));
        }
        return renderRecadosInboxAdmin(1);
    }

    @Transactional
    public static Result deleteRecadoAdmin(Long id){
        Sistema.removeRecado(id);
        return renderRecadosInboxAdmin(1);
    }

    //Sistema de recados nao possui lixeira, apenas Inbox e Enviados.


}
