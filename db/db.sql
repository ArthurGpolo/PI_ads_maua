DROP DATABASE IF EXISTS jogo_tabuleiro;
CREATE DATABASE jogo_tabuleiro CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE jogo_tabuleiro;

-- =========================
-- USUÁRIOS (SEGURANÇA BÁSICA)
-- =========================
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(180) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- FASES DO JOGO
-- =========================
CREATE TABLE fases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    descricao TEXT,
    nivel INT NOT NULL
);

-- =========================
-- PARTIDAS
-- =========================
CREATE TABLE partidas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    fase_atual_id INT,
    data_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fim TIMESTAMP NULL,

    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (fase_atual_id) REFERENCES fases(id) ON DELETE SET NULL
);

-- =========================
-- PERGUNTAS
-- =========================
CREATE TABLE perguntas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fase_id INT NOT NULL,
    enunciado TEXT NOT NULL,
    resposta_correta VARCHAR(255) NOT NULL,

    FOREIGN KEY (fase_id) REFERENCES fases(id) ON DELETE CASCADE
);

-- =========================
-- RESPOSTAS DO JOGADOR
-- =========================
CREATE TABLE respostas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    partida_id INT NOT NULL,
    pergunta_id INT NOT NULL,
    resposta_dada VARCHAR(255) NOT NULL,
    correta TINYINT(1) NOT NULL,
    data_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (partida_id) REFERENCES partidas(id) ON DELETE CASCADE,
    FOREIGN KEY (pergunta_id) REFERENCES perguntas(id) ON DELETE CASCADE,

    UNIQUE(partida_id, pergunta_id)
);

-- =========================
-- PONTUAÇÃO
-- =========================
CREATE TABLE pontuacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    partida_id INT NOT NULL UNIQUE,
    pontos INT NOT NULL DEFAULT 0,

    FOREIGN KEY (partida_id) REFERENCES partidas(id) ON DELETE CASCADE
);

-- =========================
-- ÍNDICES (PERFORMANCE)
-- =========================
CREATE INDEX idx_usuario_email ON usuarios(email);
CREATE INDEX idx_partida_usuario ON partidas(usuario_id);
CREATE INDEX idx_pergunta_fase ON perguntas(fase_id);
CREATE INDEX idx_resposta_partida ON respostas(partida_id);

-- =========================
-- VIEW DE RANKING (PROFISSIONAL)
-- =========================
CREATE VIEW ranking AS
SELECT 
    u.id,
    u.nome,
    COALESCE(SUM(p.pontos), 0) AS total_pontos,
    COUNT(pa.id) AS total_partidas
FROM usuarios u
LEFT JOIN partidas pa ON pa.usuario_id = u.id
LEFT JOIN pontuacoes p ON p.partida_id = pa.id
GROUP BY u.id, u.nome;