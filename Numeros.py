import threading
import random

class numeros:
    @staticmethod
    def popular_aleatorio(lista, qtd, frase):
        for _ in range(qtd):
            lista.append(random.randint(0, 999))
        print("Feito...." + frase)

    @staticmethod
    def exibir(lista, nome_lista):
        print("Exibindo " + nome_lista + ":")
        for item in lista:
            print(f"{nome_lista}: {item}")