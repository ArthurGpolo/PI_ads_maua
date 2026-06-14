-- Script de Banco de Dados Completo com 30 Desafios
CREATE DATABASE IF NOT EXISTS jogo_maua;
USE jogo_maua;

-- Tabela de Jogadores
CREATE TABLE IF NOT EXISTS jogadores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    posicao INT DEFAULT 1,
    pontos INT DEFAULT 0,
    fase_id INT DEFAULT 1,
    ativo BOOLEAN DEFAULT TRUE
);

-- Tabela de Desafios
CREATE TABLE IF NOT EXISTS desafios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    pergunta TEXT NOT NULL,
    opcao_a VARCHAR(255) NOT NULL,
    opcao_b VARCHAR(255) NOT NULL,
    opcao_c VARCHAR(255) NOT NULL,
    opcao_d VARCHAR(255) NOT NULL,
    resposta_correta INT NOT NULL,
    pontos INT DEFAULT 10,
    fase_id INT NOT NULL
);

-- Tabela de Partidas
CREATE TABLE IF NOT EXISTS partidas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jogador_id INT NOT NULL,
    data_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fim TIMESTAMP NULL,
    pontuacao_final INT DEFAULT 0,
    concluida BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (jogador_id) REFERENCES jogadores(id)
);

-- Inserir 30 desafios (1 para cada casa)
INSERT INTO desafios (titulo, pergunta, opcao_a, opcao_b, opcao_c, opcao_d, resposta_correta, pontos, fase_id) VALUES
('Casa 1', 'O que e Java?', 'Linguagem', 'Cafe', 'Ilha', 'Carro', 0, 10, 1),
('Casa 2', 'O que e JDK?', 'Kit de Desenvolvedor', 'Jogo', 'Dados', 'Teclado', 0, 10, 1),
('Casa 3', 'O que e JRE?', 'Ambiente de Execucao', 'Editor', 'Internet', 'Nuvem', 0, 10, 1),
('Casa 4', 'O que e JVM?', 'Maquina Virtual', 'Video', 'Musica', 'Voz', 0, 10, 1),
('Casa 5', 'Extensao de arquivo Java?', '.java', '.txt', '.exe', '.pdf', 0, 10, 1),
('Casa 6', 'O que e JDBC?', 'Conexao com Banco', 'Desenho', 'Som', 'Cor', 0, 15, 2),
('Casa 7', 'O que e SQL?', 'Linguagem de Consulta', 'Jogo', 'Site', 'App', 0, 15, 2),
('Casa 8', 'O que e MySQL?', 'Banco de Dados', 'Browser', 'OS', 'Hardware', 0, 15, 2),
('Casa 9', 'O que e SELECT?', 'Comando de Busca', 'Deletar', 'Criar', 'Sair', 0, 15, 2),
('Casa 10', 'O que e INSERT?', 'Inserir Dados', 'Ler', 'Mover', 'Copiar', 0, 15, 2),
('Casa 11', 'O que e DAO?', 'Objeto de Acesso', 'Data', 'Hora', 'Dia', 0, 20, 3),
('Casa 12', 'O que e POJO?', 'Objeto Simples', 'Po', 'Jo', 'App', 0, 20, 3),
('Casa 13', 'O que e JFrame?', 'Janela Swing', 'Foto', 'Texto', 'Botao', 0, 20, 3),
('Casa 14', 'O que e JPanel?', 'Painel de Layout', 'Papel', 'Porta', 'Placa', 0, 20, 3),
('Casa 15', 'O que e JButton?', 'Botao de Clique', 'Texto', 'Icone', 'Menu', 0, 20, 3),
('Casa 16', 'O que e Heranca?', 'Reuso de Codigo', 'Dinheiro', 'Casa', 'Carro', 0, 25, 4),
('Casa 17', 'O que e Polimorfismo?', 'Muitas Formas', 'Uma Forma', 'Sem Forma', 'Cor', 0, 25, 4),
('Casa 18', 'O que e Interface?', 'Contrato de Metodos', 'Tela', 'Mouse', 'Cabo', 0, 25, 4),
('Casa 19', 'O que e Encapsulamento?', 'Protecao de Dados', 'Caixa', 'Capa', 'Saco', 0, 25, 4),
('Casa 20', 'O que e Construtor?', 'Metodo de Criacao', 'Pedreiro', 'Engenheiro', 'Arquiteto', 0, 25, 4),
('Casa 21', 'O que e Exception?', 'Tratamento de Erro', 'Saida', 'Entrada', 'Fim', 0, 30, 5),
('Casa 22', 'O que e Try-Catch?', 'Capturar Erro', 'Tentar', 'Pegar', 'Lancar', 0, 30, 5),
('Casa 23', 'O que e Finally?', 'Sempre Executa', 'Fim', 'Ultimo', 'Depois', 0, 30, 5),
('Casa 24', 'O que e List?', 'Colecao Ordenada', 'Lista', 'Papel', 'Livro', 0, 30, 5),
('Casa 25', 'O que e Map?', 'Chave-Valor', 'Mapa', 'GPS', 'Bussola', 0, 30, 5),
('Casa 26', 'O que e Thread?', 'Execucao Paralela', 'Linha', 'Corda', 'Fio', 0, 35, 6),
('Casa 27', 'O que e Maven?', 'Gerenciador de Dep.', 'Site', 'Blog', 'Wiki', 0, 35, 6),
('Casa 28', 'O que e Git?', 'Controle de Versao', 'Jogo', 'Editor', 'Nuvem', 0, 35, 6),
('Casa 29', 'O que e GitHub?', 'Hospedagem de Codigo', 'Rede Social', 'Loja', 'Banco', 0, 35, 6),
('Casa 30', 'O que e Deploy?', 'Publicacao', 'Deletar', 'Mover', 'Sair', 0, 35, 6);
