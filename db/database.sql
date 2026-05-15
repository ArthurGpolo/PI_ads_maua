-- Script de Criação do Banco de Dados - Jogo de Tabuleiro Mauá
-- Database: jogo_maua
-- Autor: Projeto PI Mauá
-- Data: 2024

-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS jogo_maua;
USE jogo_maua;

-- Tabela de Fases
CREATE TABLE IF NOT EXISTS fases (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    numero INT NOT NULL UNIQUE
);

-- Tabela de Jogadores
CREATE TABLE IF NOT EXISTS jogadores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    posicao INT DEFAULT 1,
    pontos INT DEFAULT 0,
    fase_id INT DEFAULT 1,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fase_id) REFERENCES fases(id)
);

-- Tabela de Desafios
CREATE TABLE IF NOT EXISTS desafios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    pergunta TEXT NOT NULL,
    opcao_a VARCHAR(255) NOT NULL,
    opcao_b VARCHAR(255) NOT NULL,
    opcao_c VARCHAR(255) NOT NULL,
    opcao_d VARCHAR(255) NOT NULL,
    resposta_correta INT NOT NULL,
    pontos INT NOT NULL,
    fase_id INT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fase_id) REFERENCES fases(id)
);

-- Tabela de Partidas
CREATE TABLE IF NOT EXISTS partidas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    jogador_id INT NOT NULL,
    data_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fim TIMESTAMP NULL,
    pontuacao_final INT DEFAULT 0,
    concluida BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (jogador_id) REFERENCES jogadores(id) ON DELETE CASCADE
);

-- Tabela de Respostas dos Desafios
CREATE TABLE IF NOT EXISTS respostas_desafios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    partida_id INT NOT NULL,
    desafio_id INT NOT NULL,
    resposta_escolhida INT NOT NULL,
    acertou BOOLEAN NOT NULL,
    data_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (partida_id) REFERENCES partidas(id) ON DELETE CASCADE,
    FOREIGN KEY (desafio_id) REFERENCES desafios(id)
);

-- Inserir as Fases
INSERT INTO fases (id, nome, numero, descricao) VALUES
(1, 'Explorador', 1, 'Fase inicial - Explore novas possibilidades'),
(2, 'Conector', 2, 'Conecte com outras pessoas e ideias'),
(3, 'Transformador', 3, 'Transforme conhecimento em ação'),
(4, 'Conhecedor', 4, 'Adquira conhecimento profundo'),
(5, 'Planejador', 5, 'Planeje seu futuro com sabedoria'),
(6, 'Realizador', 6, 'Realize seus objetivos e sonhos');

-- Inserir Desafios de Exemplo para a Fase 1 (Explorador)
INSERT INTO desafios (titulo, descricao, pergunta, opcao_a, opcao_b, opcao_c, opcao_d, resposta_correta, pontos, fase_id) VALUES
('Missão Explorador 1', 'Seu primeiro desafio como explorador', 'O que significa exploração profissional?', 
 'Investigar novas oportunidades', 'Ignorar novas áreas', 'Ficar na zona de conforto', 'Não aprender nada novo', 0, 10, 1),
('Missão Explorador 2', 'Continue sua jornada', 'Qual é o primeiro passo para explorar uma carreira?', 
 'Ter curiosidade e fazer perguntas', 'Aguardar oportunidades', 'Desistir de tentar', 'Não se preparar', 0, 10, 1);

-- Inserir Desafios de Exemplo para a Fase 2 (Conector)
INSERT INTO desafios (titulo, descricao, pergunta, opcao_a, opcao_b, opcao_c, opcao_d, resposta_correta, pontos, fase_id) VALUES
('Conectar com Colegas', 'Aprenda sobre networking', 'Por que networking é importante?', 
 'Para compartilhar conhecimento e experiências', 'Para ganhar dinheiro rápido', 'Para evitar isolamento', 'Sem razão específica', 0, 15, 2),
('Desafio de Comunicação', 'Desenvolva suas habilidades', 'Qual é a base de uma boa comunicação?', 
 'Saber ouvir atentamente', 'Falar sempre mais que os outros', 'Usar jargão técnico', 'Ser agressivo', 0, 15, 2);

-- Inserir Desafios de Exemplo para a Fase 3 (Transformador)
INSERT INTO desafios (titulo, descricao, pergunta, opcao_a, opcao_b, opcao_c, opcao_d, resposta_correta, pontos, fase_id) VALUES
('Transformar Ideias', 'Crie impacto positivo', 'Como transformar uma boa ideia em realidade?', 
 'Planejar, executar e avaliar', 'Apenas sonhar', 'Abandonar na primeira dificuldade', 'Esperar que outros façam', 0, 20, 3);

-- Inserir Desafios de Exemplo para a Fase 4 (Conhecedor)
INSERT INTO desafios (titulo, descricao, pergunta, opcao_a, opcao_b, opcao_c, opcao_d, resposta_correta, pontos, fase_id) VALUES
('Aprofundamento', 'Demonstre seu conhecimento', 'Qual é o melhor método para aprender algo novo?', 
 'Estudo constante e prática', 'Ler apenas uma vez', 'Pedir resposta pronta', 'Não estudar', 0, 20, 4);

-- Inserir Desafios de Exemplo para a Fase 5 (Planejador)
INSERT INTO desafios (titulo, descricao, pergunta, opcao_a, opcao_b, opcao_c, opcao_d, resposta_correta, pontos, fase_id) VALUES
('Planejamento Estratégico', 'Organize seu futuro', 'Qual é o primeiro passo de um plano de carreira?', 
 'Definir objetivos claros e mensuráveis', 'Não ter plano', 'Deixar tudo para depois', 'Não refletir sobre o futuro', 0, 25, 5);

-- Inserir Desafios de Exemplo para a Fase 6 (Realizador)
INSERT INTO desafios (titulo, descricao, pergunta, opcao_a, opcao_b, opcao_c, opcao_d, resposta_correta, pontos, fase_id) VALUES
('Alcançar o Sucesso', 'Complete sua jornada', 'O que significa ser um verdadeiro realizador?', 
 'Alcançar objetivos e inspirar outros', 'Ganhar muito dinheiro', 'Trabalhar muito', 'Vencer os outros', 0, 30, 6);

-- Criar índices para melhor performance
CREATE INDEX idx_jogador_email ON jogadores(email);
CREATE INDEX idx_jogador_ativo ON jogadores(ativo);
CREATE INDEX idx_partida_jogador ON partidas(jogador_id);
CREATE INDEX idx_partida_data ON partidas(data_inicio);
CREATE INDEX idx_desafio_fase ON desafios(fase_id);
CREATE INDEX idx_resposta_partida ON respostas_desafios(partida_id);

-- Inserir jogador de teste
INSERT INTO jogadores (nome, email, senha, posicao, pontos, fase_id, ativo) VALUES
('Jogador Teste', 'teste@email.com', 'senha123', 1, 0, 1, TRUE);

-- Mostrar estrutura das tabelas criadas
SHOW TABLES;
