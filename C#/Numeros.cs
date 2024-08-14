using System;
using System.Collections.Generic;

public class Numeros
{
    public static void PopularAleatorio(List<int> lista, int qtd, string frase)
    {
        Random gerador = new Random();
        for (int i = 0; i < qtd; i++)
        {
            lista.Add(gerador.Next(1000));
        }
        Console.WriteLine("Feito...." + frase);
    }

    public static void Exibir(List<int> lista, string nomeLista)
    {
        Console.WriteLine("Exibindo " + nomeLista + ":");
        foreach (var item in lista)
        {
            Console.WriteLine(nomeLista + ": " + item);
        }
    }
}