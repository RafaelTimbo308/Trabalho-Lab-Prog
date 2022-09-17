package Principal;

import java.util.ArrayList;
public class Grafo {

    //Atributos:
    private final ArrayList<Aeroportos> ListaAero;
    private final int saida;
    private final int chegada;

    //Construtor:
    public Grafo(ArrayList<Aeroportos> listaAero, int sai, int cheg) {
        ListaAero = listaAero;
        saida = sai;
        chegada = cheg;

        //Para cada aeroporto na lista de aeroportos, cria-se a lista de trajetos (Todos se interligam, exceto a origem ao destino)

        for (int i = 0; i < ListaAero.size(); i++) {

            for (int j = 0; j < ListaAero.size(); j++) {
                if (i != j) {
                    if((i==saida && j==chegada)||(i==chegada && j==saida)){
                        continue;
                    }
                    Aeroportos aero1 = ListaAero.get(i);
                    Aeroportos aero2 = ListaAero.get(j);
                    aero1.getSaidas().add(new Trajetos(aero2));
                }
            }
        }
    }

    //Métodos Getter:
    public int getSaida() {
        return saida;
    }
    public int getChegada() {
        return chegada;
    }
    public ArrayList<Aeroportos> getListaAero() {
        return ListaAero;
    }
}
