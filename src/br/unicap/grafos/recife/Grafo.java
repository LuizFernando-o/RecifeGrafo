package br.unicap.grafos.recife;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Grafo {

    static LinkedList vertices = new LinkedList();
    static LinkedList<LinkedList<String>> grafo = new LinkedList();
    static String cor[];
    static String linha;
    static int ordem, tamanho;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        //indicar caminho do txt Spara realizar leitura e criar o grafo
        leitorTxt("C:\\Users\\luizn\\Documents\\NetBeansProjects\\RecifeGrafo\\src\\br\\unicap\\grafos\\recife\\grafoRecife.txt");
        exibirListaAdj(grafo);
        System.out.println("");

        
        //escolher vértice inicial
        System.out.println("\nDigite o vertice de inicio: ");
        int inicio = sc.nextInt();
        
        
        //escolher inicio e destino
        System.out.println("Digite o vertice de destino: ");
        int destino = sc.nextInt();

        System.out.println("\n\nExibindo menor caminho de [" + vertices.get(inicio) + "] para [" + vertices.get(destino) + "]:\n");
        BFS(inicio, destino);

    }

    public static void leitorTxtCapturaVertice(String caminho) throws FileNotFoundException, IOException {
        BufferedReader buffRead
                = new BufferedReader(new FileReader(caminho));

        buffRead.readLine();
        buffRead.readLine();
        //lendo txt e adicionando arestas
        linha = buffRead.readLine();

        while (linha != null) {
            String vu[] = linha.split(" ");
            String v = vu[0];
            String u = vu[1];

            if (!vertices.contains(v)) {
                vertices.add(v);
            }
            if (!vertices.contains(u)) {
                vertices.add(u);
            }
            linha = buffRead.readLine();
        }
        buffRead.close();
    }

    public static void leitorTxt(String caminho) throws FileNotFoundException, IOException {
        leitorTxtCapturaVertice(caminho);
        BufferedReader buffRead
                = new BufferedReader(new FileReader(caminho));

        linha = buffRead.readLine();
        ordem = Integer.parseInt(linha);   //capturar ordem (nº de vértices)

        linha = buffRead.readLine();
        tamanho = Integer.parseInt(linha); //capturar tamanho (nº de arestas)

        for (int i = 0; i < ordem; ++i) {  //criando lista de adjacência
            grafo.add(new LinkedList<>());
        }

        //lendo txt e adicionando arestas
        linha = buffRead.readLine();

        while (linha != null) {
            String vu[] = linha.split(" ");
            String v = vu[0];
            String u = vu[1];

            grafo.get(vertices.indexOf(v)).add(u);
            grafo.get(vertices.indexOf(u)).add(v);
            linha = buffRead.readLine();
        }
        buffRead.close();
    }

    static void BFS(int inicio, int destino) {

        // Criando Queue que guarda os caminhos
        Queue<LinkedList<String>> queue = new LinkedList<>();

        // Vetor caminho para guardar o caminho atual
        LinkedList<String> caminho = new LinkedList<>();

        caminho.add((String) vertices.get(inicio));
        queue.add(caminho);

        while (!queue.isEmpty()) {
            caminho = queue.poll();
            String ultimo = caminho.get(caminho.size() - 1);
            String dest = (String) vertices.get(destino);
            if (ultimo.equals(dest)) {
                exibirCaminho(caminho);
                break; //retirar o 'break' caso queira exibir todos os caminhos possíveis
            }
            LinkedList<String> verticePai = grafo.get(vertices.indexOf(ultimo));

            for (int i = 0; i < verticePai.size(); i++) {
                if (naoVisitado(verticePai.get(i), caminho)) {
                    LinkedList<String> novoCaminho = new LinkedList<>(caminho);
                    novoCaminho.add(verticePai.get(i));
                    queue.add(novoCaminho);
                }
            }
        }
    }

    //metodos auxiliares encontraMenorCaminho()
    private static boolean naoVisitado(String x, LinkedList<String> caminho) {
        for (int i = 0; i < caminho.size(); i++) {
            if (caminho.get(i) == null ? x == null : caminho.get(i).equals(x)) {
                return false;
            }
        }
        return true;
    }

    static void exibirCaminho(LinkedList<String> caminho) {
        for (String v : caminho) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
    //==========

    static void exibirListaAdj(LinkedList<LinkedList<String>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.print("\n[" + i + "] " + vertices.get(i) + " -> ");
            for (String v : adj.get(i)) {
                System.out.print(v + " ");
            }
        }
    }
}
