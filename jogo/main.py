from jogador import Jogador


def main():
    print("=== SISTEMA JOGO TABULEIRO ===")

    nome = input("Nome: ")
    email = input("Email: ")
    senha = input("Senha: ")

    jogador = Jogador(nome, email, senha)
    jogador.cadastrar()

    print("\n📋 Usuários cadastrados:")
    for u in jogador.listar():
        print(u)


if __name__ == "__main__":
    main()