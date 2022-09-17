package Principal;

import java.util.ArrayList;
public class Dijkstra {
    //Atributos:
    private final Grafo grafo;
    private final ArrayList<Integer> vertice;
    private final ArrayList<Double> caminho;

    //Construtor:
    public Dijkstra(Grafo grafo) {
        double infinito = Double.POSITIVE_INFINITY;
        this.grafo = grafo;
        this.caminho = new ArrayList<>();
        this.vertice = new ArrayList<>();

        for (int i=0;i<grafo.getListaAero().size();i++){
            if(i==grafo.getSaida()){
                caminho.add(0.0);
                vertice.add(i);
            }
            else {
                caminho.add(infinito);
                vertice.add(-1);
            }
        }
    }

    // Método que executa o algoritmo de Dijkstra e calcula a Escala:
    public String Escala(){
            double infinito = Double.POSITIVE_INFINITY;
            int posicaoMin=0;

            for(int i=0;i<grafo.getListaAero().size();i++) {

                // Verifica o menor caminho não percorrido
                double min = infinito;

                for (int j = 0; j < grafo.getListaAero().size(); j++) {
                    if ((!grafo.getListaAero().get(j).foiExplorado()) && caminho.get(j)<min) {
                        min = caminho.get(j);
                        posicaoMin = j;
                    }
                }
                //Marca o aeroporto a ser trabalhado como visitado:
                grafo.getListaAero().get(posicaoMin).setExplorado(true);

                //atribui o valor do caminho, caso seja menor que o existente.
                for(Trajetos traj:grafo.getListaAero().get(posicaoMin).getSaidas()){
                    if((!traj.getChegada().foiExplorado()) &&
                            (caminho.get(posicaoMin)+grafo.getListaAero().get(posicaoMin).getDist(traj.getChegada())<caminho.get(traj.getChegada().getIndice()))){

                        caminho.set(traj.getChegada().getIndice(),caminho.get(posicaoMin)+grafo.getListaAero().get(posicaoMin).getDist(traj.getChegada()));
                        vertice.set(traj.getChegada().getIndice() , posicaoMin);
                    }
                }
            }

        //Retorna a String com o nome do aeroporto correspondente à escala que minimiza o trajeto.

        return grafo.getListaAero().get(vertice.get(grafo.getChegada())).getNome();
    }
}
