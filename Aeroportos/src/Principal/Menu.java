package Principal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Menu {

    //Atributos:
    private final ArrayList<Aeroportos> listaAeroportos;

    private final ArrayList<String> listaEstados;

    //Construtor:
    public Menu() throws SQLException, ClassNotFoundException {

        Conexao C = new Conexao();

        listaAeroportos = C.ImportarAeroportosSQL();

        listaEstados = new ArrayList<>();
        for(Aeroportos aero:listaAeroportos){
            if(!listaEstados.contains(aero.getEstado())){
                listaEstados.add(aero.getEstado());
            }
        }
        Collections.sort(listaEstados);
    }

    //Método que Pergunta ao usuário o Aeroporto que ele deseja selecionar e retorna o índice do aeroporto:
    public int selecionarAeroporto(){

        int index=-1;

        //Imprime a lista de Estados:

        for(int i=1;i<=listaEstados.size();i++){
            System.out.println(i + " - " + listaEstados.get(i-1));
        }

        //Pergunta ao usuário o estado do aeroporto a ser selecionado:

        System.out.print("\nSelecione o Estado do Aeroporto: ");

        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        int Estado = sc1.nextInt()-1;
        String aerop;

        //Imprime lista de Aeroportos no Estado Selecionado:

        System.out.println("\nAeroportos em "+ listaEstados.get(Estado) + " :\n");

        int contador = 1;
        for(Aeroportos aero:listaAeroportos){
            if(aero.getEstado().equals(listaEstados.get(Estado))){
                System.out.println( contador + " - " + aero.getNome());
                contador++;
            }
        }

        //Pergunta ao Usuário o Aeroporto a ser selecionado:

        System.out.print("\nSelecione o Aeroporto (Nome): ");
        aerop = sc2.nextLine();
        for (Aeroportos A : listaAeroportos) {
            if ((A.getNome().equals(aerop)) && (A.getEstado().equals(listaEstados.get(Estado))) ) {
                index = A.getIndice();
            }
        }

        //Retorna o índice do aeroporto selecionado:
        return index;
    }

    //Método que seleciona os Aeroportos de partida e chegada, calcula a Rota e armazena no BD:
    public void executar() throws SQLException, ClassNotFoundException {
        //Escolhe os Aeroportos de saída e chegada:
        int c, s;

        System.out.println("\nSelecione o Aeroporto de Saida: \n");

        s = selecionarAeroporto();

        while(s==-1){
            System.out.println("\nAeroporto inválido! Tente novamente.\n");

            System.out.println("Selecione o Aeroporto de Saida: \n");

            s = selecionarAeroporto();
        }

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");

        System.out.println("\nSelecione o Aeroporto de Chegada:\n");

        c = selecionarAeroporto();

        while(c==-1){
            System.out.println("\nAeroporto inválido! Tente novamente.\n");
            System.out.println("Selecione o Aeroporto de Chegada:\n");
            c = selecionarAeroporto();
        }

        if(s==c){
            System.out.println("O aeroporto de saída deve ser diferente do aeroporto de chegada!");
        }

        //Caso os aeroportos selecionados sejam válidos, calcula a Escala e imprime a rota na tela:
        else{
            System.out.println("\n\nA menor rota, considerando uma escala, é:\n");
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");

            Grafo G = new Grafo(listaAeroportos,s,c);

            String escala = new Dijkstra(G).Escala();

            System.out.println("\t\t" + listaAeroportos.get(s).getNome() + " -> " + escala + " -> " + listaAeroportos.get(c).getNome());

            System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");

            //Armazena o trajeto no Banco de Dados:
            Conexao C = new Conexao();
            C.ExportarRotasSQL(G,escala);
        }
    }
}