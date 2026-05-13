from jogador import Jogador
from partida import Partida
from tabuleiro import Tabuleiro
from desafio import Desafio


def exibir_menu():
    """Exibe o menu principal"""
    print("\n" + "="*50)
    print("🎮 SISTEMA DE JOGO TABULEIRO 🎮".center(50))
    print("="*50)
    print("1. Cadastrar novo jogador")
    print("2. Iniciar nova partida")
    print("3. Ver ranking")
    print("4. Ver histórico de jogadores")
    print("5. Sair")
    print("="*50)
    return input("Escolha uma opção: ").strip()


def cadastrar_jogador():
    """Cadastra um novo jogador"""
    print("\n📝 CADASTRO DE JOGADOR")
    nome = input("Nome do jogador: ").strip()
    email = input("Email: ").strip()
    senha = input("Senha: ").strip()
    
    if not nome or not email or not senha:
        print("❌ Todos os campos são obrigatórios!")
        return
    
    jogador = Jogador(nome, email, senha)
    if jogador.cadastrar():
        print(f"✅ Bem-vindo, {nome}!")


def iniciar_partida():
    """Inicia uma nova partida"""
    print("\n🎮 INICIAR PARTIDA")
    email = input("Digite seu email: ").strip()
    
    jogador = Jogador("", "", "")
    if not jogador.buscar_por_email(email):
        print(f"❌ Jogador com email '{email}' não encontrado!")
        return
    
    print(f"\n✅ Bem-vindo, {jogador.nome}!")
    
    # Criar partida
    partida = Partida(jogador.id)
    if not partida.iniciar():
        print("❌ Erro ao iniciar partida!")
        return
    
    # Criar tabuleiro
    tabuleiro = Tabuleiro()
    
    print("\n📋 FASES DISPONÍVEIS:")
    fases = Desafio.obter_fases()
    for fase in fases:
        print(f"  Fase {fase['id']}: {fase['nome']} - {fase['descricao']}")
    
    # Loop de jogo
    while True:
        print("\n" + "-"*50)
        continuar = input("Pressione ENTER para jogar o próximo turno (ou 'sair' para encerrar): ").strip().lower()
        
        if continuar == "sair":
            partida.finalizar_partida()
            break
        
        venceu = partida.jogar_turno(tabuleiro)
        if venceu:
            break


def exibir_ranking():
    """Exibe o ranking de jogadores"""
    print("\n🏆 RANKING DE JOGADORES")
    ranking = Jogador.obter_ranking()
    
    if not ranking:
        print("❌ Nenhum dado de ranking disponível!")
        return
    
    print(f"\n{'Pos':<5} {'Jogador':<25} {'Pontos':<10} {'Partidas':<10}")
    print("-" * 50)
    
    for i, jogador in enumerate(ranking, 1):
        print(f"{i:<5} {jogador['nome']:<25} {jogador['total_pontos']:<10} {jogador['total_partidas']:<10}")


def exibir_historico():
    """Exibe o histórico de jogadores"""
    print("\n📜 HISTÓRICO DE JOGADORES")
    jogadores = Jogador.listar_todos()
    
    if not jogadores:
        print("❌ Nenhum jogador cadastrado!")
        return
    
    print(f"\n{'ID':<5} {'Nome':<25} {'Email':<30}")
    print("-" * 60)
    
    for jogador in jogadores:
        print(f"{jogador['id']:<5} {jogador['nome']:<25} {jogador['email']:<30}")


def main():
    """Loop principal do programa"""
    while True:
        opcao = exibir_menu()
        
        if opcao == "1":
            cadastrar_jogador()
        elif opcao == "2":
            iniciar_partida()
        elif opcao == "3":
            exibir_ranking()
        elif opcao == "4":
            exibir_historico()
        elif opcao == "5":
            print("\n👋 Até logo!")
            break
        else:
            print("❌ Opção inválida!")


if __name__ == "__main__":
    main()