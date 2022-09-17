package Principal;
import java.sql.*;
import java.lang.Class;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;

public class Conexao {

    //Método que importa a lista de aeroportos do BD e retorna uma ArrayList de objetos do tipo Aeroportos:
    public ArrayList<Aeroportos> ImportarAeroportosSQL() throws SQLException, ClassNotFoundException {

        ArrayList<Aeroportos> listaAeroportos = new ArrayList<>();
        //Estabelece a Conexão com o BD:
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/airportdata?user=root&password=123456");
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from aeroportos");

        try {
            while(resultSet.next()){
                double conv= Math.PI/180;
                Aeroportos A = new Aeroportos();

                A.setIndice(resultSet.getInt("id"));
                A.setNome(resultSet.getString("Nome"));
                A.setLat(conv * resultSet.getDouble("Lat"));
                A.setLong(conv * resultSet.getDouble("Long"));
                A.setEstado(resultSet.getString("Estado"));

                listaAeroportos.add(A);
            }
            resultSet.close();
        } catch (SQLException e) {System.out.println("ERRO");}
        return listaAeroportos;
    }

    //Método que exporta a rota Partida  -> Escala -> Chegada para o BD, caso esta já não esteja contida no BD:
    public void ExportarRotasSQL(Grafo G,String str) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm;

        //Cria a Conexão:

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/airportdata?user=root&password=123456");
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from rotas");

        ArrayList<Rotas> rotas = new ArrayList<>();

        //Cria uma lista de Rotas, contendo Partida e Chegada com as Rotas já armazenadas no BD:

        while(resultSet.next()){
            Rotas R = new Rotas();

            R.setSaida(resultSet.getString("Saída"));
            R.setChegada(resultSet.getString("Chegada"));
            rotas.add(R);
        }

        String sql = "insert into rotas (Saída, Chegada, Escala) values (?,?,?)";

        //teste se o deslocamento já está no BD:

        boolean estaNoBD = false;

        for(Rotas R: rotas){

            if((R.getSaida().equals(G.getListaAero().get(G.getSaida()).getNome())) && (R.getChegada().equals(G.getListaAero().get(G.getChegada()).getNome()))){
                estaNoBD = true;
                break;
            }
        }
        //Caso teste dê falso, adiciona ao BD:
        if (!estaNoBD){

            pstm = connection.prepareStatement(sql);

            pstm.setString(1,G.getListaAero().get(G.getSaida()).getNome());
            pstm.setString(2,G.getListaAero().get(G.getChegada()).getNome());
            pstm.setString(3,str);

            pstm.execute();
            pstm.close();

            System.out.println("Caminho adicionado ao Banco de Dados!");
        }
        else System.out.println("Caminho já está no Banco de Dados!");
    }
}
