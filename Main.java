import java.util.*;

// Clase Grafo
class Grafo {
    private int[][] matrizAdyacencia;
    private int numVertices;

    // Constructor
    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        matrizAdyacencia = new int[numVertices][numVertices];
        // Inicializar la matriz de adyacencia con infinito (representado por Integer.MAX_VALUE)
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrizAdyacencia[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    // Método para agregar una arista ponderada
    public void agregarArista(int origen, int destino, int peso) {
        matrizAdyacencia[origen][destino] = peso;
    }

    // Método para imprimir la matriz de adyacencia
    public void imprimirMatrizAdyacencia() {
        System.out.println("Matriz de Adyacencia:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] == Integer.MAX_VALUE) {
                    System.out.print("∞\t");
                } else {
                    System.out.print(matrizAdyacencia[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    // Método para encontrar el camino mínimo utilizando Bellman-Ford
    public void caminoMinimo(int origen) {
        // Inicializar arreglo de distancias con infinito
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        // La distancia al origen es 0
        distancias[origen] = 0;

        // Iterar para encontrar el camino mínimo
        for (int k = 0; k < numVertices - 1; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (matrizAdyacencia[i][j] != Integer.MAX_VALUE) {
                        if (distancias[i] != Integer.MAX_VALUE && distancias[i] + matrizAdyacencia[i][j] < distancias[j]) {
                            distancias[j] = distancias[i] + matrizAdyacencia[i][j];
                        }
                    }
                }
            }
        }

        // Verificar si hay ciclos negativos
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] != Integer.MAX_VALUE) {
                    if (distancias[i] != Integer.MAX_VALUE && distancias[i] + matrizAdyacencia[i][j] < distancias[j]) {
                        System.out.println("El grafo contiene un ciclo negativo.");
                        return;
                    }
                }
            }
        }

        // Imprimir el camino mínimo
        System.out.println("Camino mínimo desde el vértice " + (origen + 1) + ":");
        for (int i = 0; i < numVertices; i++) {
            if (distancias[i] == Integer.MAX_VALUE) {
                System.out.println("No hay camino desde el vértice " + (origen + 1) + " al vértice " + (i + 1));
            } else {
                System.out.println("Distancia al vértice " + (i + 1) + ": " + distancias[i]);
            }
        }
    }

    // Método para determinar si hay ciclos utilizando DFS
    public List<List<Integer>> encontrarCiclos() {
        List<List<Integer>> ciclos = new ArrayList<>();
        boolean[] visitado = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            if (!visitado[i]) {
                List<Integer> caminoActual = new ArrayList<>();
                dfsEncontrarCiclos(i, visitado, new boolean[numVertices], caminoActual, ciclos);
            }
        }
        return ciclos;
    }

    private void dfsEncontrarCiclos(int v, boolean[] visitado, boolean[] enCamino, List<Integer> caminoActual, List<List<Integer>> ciclos) {
        visitado[v] = true;
        enCamino[v] = true;
        caminoActual.add(v);

        for (int i = 0; i < numVertices; i++) {
            if (matrizAdyacencia[v][i] != Integer.MAX_VALUE) {
                if (enCamino[i]) {
                    List<Integer> ciclo = new ArrayList<>();
                    int inicio = caminoActual.indexOf(i);
                    for (int j = inicio; j < caminoActual.size(); j++) {
                        ciclo.add(caminoActual.get(j));
                    }
                    ciclos.add(ciclo);
                } else if (!visitado[i]) {
                    dfsEncontrarCiclos(i, visitado, enCamino, caminoActual, ciclos);
                }
            }
        }

        enCamino[v] = false;
        caminoActual.remove(caminoActual.size() - 1);
    }

    // Método para realizar un recorrido BFS
    public void bfs(int origen) {
        boolean[] visitado = new boolean[numVertices];
        Queue<Integer> cola = new LinkedList<>();
        visitado[origen] = true;
        cola.add(origen);

        System.out.println("Recorrido BFS desde el vértice " + (origen + 1) + ":");
        while (!cola.isEmpty()) {
            int actual = cola.poll();
            System.out.print((actual + 1) + " ");

            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[actual][i] != Integer.MAX_VALUE && !visitado[i]) {
                    visitado[i] = true;
                    cola.add(i);
                }
            }
        }
        System.out.println();
    }
}

// Clase Principal
public class Main {
    public static void main(String[] args) {
        // Crear un objeto Grafo
        Grafo grafo = new Grafo(13);

        // Agregar las aristas con sus pesos
        grafo.agregarArista(0, 1, 200);
        grafo.agregarArista(0, 12, 250);
        grafo.agregarArista(0, 8, 290);
        grafo.agregarArista(1, 5, 360);
        grafo.agregarArista(1, 2, 190);
        grafo.agregarArista(2, 5, 250);
        grafo.agregarArista(2, 4, 190);
        grafo.agregarArista(2, 0, 300);
        grafo.agregarArista(3, 2, 180);
        grafo.agregarArista(4, 5, 300);
        grafo.agregarArista(4, 9, 400);
        grafo.agregarArista(5, 10, 350);
        grafo.agregarArista(5, 11, 300);
        grafo.agregarArista(6, 3, 300);
        grafo.agregarArista(6, 2, 250);
        grafo.agregarArista(6, 0, 150);
        grafo.agregarArista(7, 6, 200);
        grafo.agregarArista(7, 0, 220);
        grafo.agregarArista(8, 7, 180);
        grafo.agregarArista(8, 12, 180);
        grafo.agregarArista(9, 3, 200);
        grafo.agregarArista(10, 9, 700);
        grafo.agregarArista(10, 4, 200);
        grafo.agregarArista(11, 1, 150);
        grafo.agregarArista(12, 11, 100);
        grafo.agregarArista(12, 1, 200);

        // Imprimir la matriz de adyacencia
        grafo.imprimirMatrizAdyacencia();

        // Encontrar y mostrar el camino mínimo desde el vértice 0
        grafo.caminoMinimo(0);

        // Encontrar y mostrar los ciclos en el grafo
        List<List<Integer>> ciclos = grafo.encontrarCiclos();
        System.out.println("Ciclos encontrados:");
        for (List<Integer> ciclo : ciclos) {
            for (int i = 0; i < ciclo.size(); i++) {
                System.out.print((ciclo.get(i) + 1));
                if (i < ciclo.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }

        // Realizar un recorrido BFS desde el vértice 0
        grafo.bfs(0);
    }
}