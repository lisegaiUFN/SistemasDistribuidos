import threading
import random
from Numeros import numeros  # Importa a classe numeros do arquivo Numeros.py

def main():
    lista1 = []
    lista2 = []
    lista3 = []

    t1 = threading.Thread(target=numeros.popular_aleatorio, args=(lista1, 10, "lista1"))
    t2 = threading.Thread(target=numeros.popular_aleatorio, args=(lista2, 10, "lista2"))
    t3 = threading.Thread(target=numeros.popular_aleatorio, args=(lista3, 10, "lista3"))

    t1.start()
    t2.start()
    t3.start()

    t1.join()
    t2.join()
    t3.join()

    numeros.exibir(lista1, "lista1")
    numeros.exibir(lista2, "lista2")
    numeros.exibir(lista3, "lista3")

    print("Programa encerrado!!!")

if __name__ == "__main__":
    main()