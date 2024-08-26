// Fazer um programa na sua linguagem de estudo de preferência que trate nomes sorteados aleatoriamente e armazenados em um arquivo texto.
// A ideia é gerar e salvar mais de 1000000 nomes tanto no arquivo, quanto na lista. Depois, aplicar dois métodos de ordenação (sort da linguagem
//  e um estudado em Pesquisa e ordenação: pente, merge, quick). E cada processo (armazenar em arquivo; armazenar em lista; ordenar sort; ordenar método escolhido).
// De fato, fazer dois programas, um sem uso de threads e outro com uso de threads, aplicando temporizador geral.

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeradorNomes {

    /**
     * Essa classe vai se encarregar de gerar os nomes a partir da variável caracteres, onde foram dadas todas letras e números para serem utilizadas na criação dos nomes e o tamanho foi estipulado em 8 caracteres.
     * A quantidade de nomes geradas ficou estabelecida em 1000 para fins de melhor visualização. Mas, para o exercício, recomenda-se modificar essa quantidade para 1 milhão como foi pedido.
     * No arquivo nomes, o nome do arquivo texto que vai ser criado ficou estabelecido como "nomes" e vai ser encontrado na área de trabalho, muito provavelmente, ou onde o programa ficou salvo, assim como vai ser 
     * criado um outro arquivo onde vão ficar salvos os nomes criados através do método de ordenação collections.sort, enquanto o nomes_sorted_merge vão ficar os nomes ordenados através do merge
     */

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TAMANHO_NOME = 8; // Tamanho dos nomes gerados
    private static final int QUANTIDADE_NOMES = 1000; // Quantidade de nomes a serem gerados
    private static final String ARQUIVO_NOMES = "nomes.txt"; // Nome do arquivo de saída
    private static final String ARQUIVO_NOMES_SORTED_COLLECTIONS = "nomes_sorted_collections.txt";
    private static final String ARQUIVO_NOMES_SORTED_MERGE = "nomes_sorted_merge.txt";

    /**
     * Na classe main, é onde a thread para a geração dos nomes vai ser criada, chamando a classe NomeGenerator onde os nomes vão ser criados e, através de .start() vai ser iniciada, passando a criar
     * esses nomes pedidos. E o join() vai aguardar a conclusão da thread antes de continuar com o programa.
     * Assim que os nomes forem gerados, vai ser chamada a classe salvar em arquivo onde vão passar a variável nomes (que armazenou cada uma das strings criadas anteriormente) e, em seguida, o nome do arquivo definido
     * acima. No caso, nomes.txt, onde os nomes vão ser armazenados
     * Feito isso, vamos utilizar o métopdo collections.sort para ordenar os nomes gerados em uma lista automaticamente, apenas passando a lista onde os nomes ficaram armazenados na linha abaixo da chamada do método main.
     * Em seguida, vamos salvar os nomes gerados através do método de ordenação merge em outro arquivo de texto. O método merge consiste na teoria do dividir para conquistar. Nele, a lista é dividida pela metade até que
     * contenha apenas um elemento e, em seguida, é aplicado o método antes que a mesma seja ordenada mais uma vez, sempre de duas em duas
     * No final disso, o programa vai exibir uma mensagem dizendo que os nomes foram devidamente gerados, ordenados e salvos  
     * @param args
     */
    public static void main(String[] args) {
        List<String> nomes = new ArrayList<>();
        
        // Criar e iniciar a thread para gerar nomes
        Thread threadGeracao = new Thread(new NomeGenerator(nomes));
        threadGeracao.start();

        try {
            threadGeracao.join(); // Aguarda a conclusão da thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Salvar nomes em arquivo
        salvarEmArquivo(nomes, ARQUIVO_NOMES);

        // Ordenar e salvar com Collections.sort
        List<String> nomesSortedCollections = new ArrayList<>(nomes);
        Collections.sort(nomesSortedCollections);
        salvarEmArquivo(nomesSortedCollections, ARQUIVO_NOMES_SORTED_COLLECTIONS);

        // Ordenar e salvar com Merge Sort
        List<String> nomesSortedMerge = new ArrayList<>(nomes);
        mergeSort(nomesSortedMerge);
        salvarEmArquivo(nomesSortedMerge, ARQUIVO_NOMES_SORTED_MERGE);

        // Exibir nomes na lista
        System.out.println("Nomes gerados e ordenados foram salvos em arquivos.");
    }


    /**
     * Esse método vai se responsabilizar por salvar os nomes gerados em um arquivo
     * @param nomes: faz referência para o nome da lista onde os nomes foram gerados e salvos anteriormente
     * @param nomeArquivo: faz referência ao nome do arquivo texto onde os nomes da lista vão ser salvo
     */
    private static void salvarEmArquivo(List<String> nomes, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (String nome : nomes) {
                writer.write(nome);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Nessa classe, o Runnable vai ser implementado e os nomes vão ser criados um por um, dentro de um for que vai definir a quantidade máxima de nomes a ser gerado.
     * Ela vai ser chamada novamente no método main.
     * Também podemos encontrar a chamada da lista de strings nomes, assim como a inicialização do random para que os nomes sejam criados de maneira randomica.
     */
    private static class NomeGenerator implements Runnable {
        private List<String> nomes;
        private Random random = new Random();

        public NomeGenerator(List<String> nomes) {
            this.nomes = nomes;
        }

        @Override
        public void run() {
            for (int i = 0; i < QUANTIDADE_NOMES; i++) {
                String nome = gerarNomeAleatorio();
                nomes.add(nome);
                System.out.println("Nome gerado: " + nome);
                try {
                    Thread.sleep(10); // Pausa de 10ms para simular processamento
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private String gerarNomeAleatorio() {
            StringBuilder sb = new StringBuilder(TAMANHO_NOME);
            for (int i = 0; i < TAMANHO_NOME; i++) {
                int index = random.nextInt(CARACTERES.length());
                sb.append(CARACTERES.charAt(index));
            }
            return sb.toString();
        }
    }
    /**
     * Nesse método, a ordenação sort vai ser aplicada, sendo que, se a lista estiver vazia, nada vai ser ordenado. 
     * Podemos observar que, ao pegar o tamanho da lista com o lista.size(), dividimos ela em duas diferentes, criando uma lista da esquerda e uma da direita, sendo que a da esquerda vai do
     * nome na posição 0 (primeiro nome) até a posição recebida pela variável meio ao dividirmos ela em 2 partes aproximadamente iguais. Já a da direita, parte do nome localizado no meio até 
     * aquele encontrado na posição final, caracterizada por assumir o tamanho da lista. Em seguida, vamos chamar o método merge para reunificá-las, tendo como parâmetro a lista e as 2 metades
     * @param lista: passa como parâmetro a lista criada anteriormente
     */
    private static void mergeSort(List<String> lista) {
        if (lista.size() <= 1) {
            return;
        }

        int meio = lista.size() / 2;
        List<String> esquerda = new ArrayList<>(lista.subList(0, meio));
        List<String> direita = new ArrayList<>(lista.subList(meio, lista.size()));

        mergeSort(esquerda);
        mergeSort(direita);

        merge(lista, esquerda, direita);
    }

    /**
     * No método merge, vamos passar como parâmetro a lista de nomes, a lista da esquerda e a lista da direita. Considerando que i e j são 0, se a lista da esquerda e direita tiverem tamanho maior que isso,
     * indicando que não estão vazias, começamos então a ordenar, pegando o nome na posição i (inicialmente 0, ou seja, primeiro nome) da lista da esquerda e o da posição j (inicialmente 0, ou seja, primeiro nome)
     * da direita e comparando os dois. Se o elemento da esquerda for menor que o elemento da direita, ele deve vir primeiro na lista, caracterizando a condição do merge para a criação da nova lista ordenada
     * @param lista: passamos como atributo a lista onde as duas metades vão ser armazenadas
     * @param esquerda: passamos como atributo a lista da esquerda criada acima
     * @param direita: passamos como atributo a lista da direita criada acima no método mergeSort 
     */
    private static void merge(List<String> lista, List<String> esquerda, List<String> direita) {
        int i = 0, j = 0, k = 0;

        while (i < esquerda.size() && j < direita.size()) {
            if (esquerda.get(i).compareTo(direita.get(j)) <= 0) {
                lista.set(k++, esquerda.get(i++));
            } else {
                lista.set(k++, direita.get(j++));
            }
        }

        while (i < esquerda.size()) {
            lista.set(k++, esquerda.get(i++));
        }

        while (j < direita.size()) {
            lista.set(k++, direita.get(j++));
        }
    }
}
