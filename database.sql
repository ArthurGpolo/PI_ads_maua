-- Apaga e recria o banco do zero
DROP DATABASE IF EXISTS jogo_maua;
CREATE DATABASE jogo_maua;
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

-- FASE 1: Fundamentos da Disciplina e Autoconhecimento (Casas 1-5)
('Casa 1', 'O que e autoconhecimento na disciplina CDP?', 'Reflexao sobre si mesmo', 'Tecnica de vendas', 'Metodo de calculo', 'Tipo de software', 0, 10, 1),
('Casa 2', 'O que significa a sigla SWOT?', 'Forcas Fraquezas Oport. Ameacas', 'Sistema Web Online Total', 'Modelo de negocios', 'Ferramenta de codigo', 0, 10, 1),
('Casa 3', 'O que e a Missao de uma pessoa ou organizacao?', 'Razao de existir e proposito', 'Meta financeira anual', 'Lista de tarefas diarias', 'Cargo profissional', 0, 10, 1),
('Casa 4', 'O que e a Visao em planejamento estrategico?', 'Estado futuro desejado', 'Capacidade de enxergar', 'Relatorio de resultados', 'Tipo de reuniao', 0, 10, 1),
('Casa 5', 'Valores sao definidos como:', 'Principios que guiam condutas', 'Precos de produtos', 'Metas numericas', 'Regras do governo', 0, 10, 1),

-- FASE 2: Ferramentas de Planejamento (Casas 6-10)
('Casa 6', 'Na Matriz de Urgencia, tarefa importante e urgente deve ser:', 'Feita agora', 'Delegada', 'Ignorada', 'Adiada', 0, 15, 2),
('Casa 7', 'Na Matriz de Prioridades, o que se deve DELEGAR?', 'Urgente mas nao importante', 'Importante e urgente', 'Nao urgente e importante', 'Nao urgente nao importante', 0, 15, 2),
('Casa 8', 'O que significa BSC na disciplina?', 'Balanced Scorecard', 'Business Software Code', 'Base de Suporte ao Cliente', 'Banco de Solucoes Criativas', 0, 15, 2),
('Casa 9', 'A Matriz CSD organiza informacoes em:', 'Certezas Suposicoes e Duvidas', 'Custos Servicos e Dados', 'Clientes Sistemas e Demandas', 'Competencias Skills e Desafios', 0, 15, 2),
('Casa 10', 'O que e o Business Model You?', 'Ferramenta de autoconhecimento profissional', 'Software de contabilidade', 'Plataforma de vendas online', 'Modelo de contrato de trabalho', 0, 15, 2),

-- FASE 3: Gestao de Projetos (Casas 11-15)
('Casa 11', 'No Project Model Canvas, o que sao Stakeholders?', 'Partes interessadas no projeto', 'Ferramentas de software', 'Etapas do projeto', 'Custos do projeto', 0, 20, 3),
('Casa 12', 'O que sao as Premissas no Project Model Canvas?', 'Condicoes assumidas como verdadeiras', 'Resultados esperados', 'Membros da equipe', 'Riscos identificados', 0, 20, 3),
('Casa 13', 'Na gestao de projetos, o que e um Risco?', 'Evento incerto que pode impactar', 'Custo fixo do projeto', 'Meta de entrega', 'Papel do lider', 0, 20, 3),
('Casa 14', 'O que e o OBJ SMART em projetos?', 'Objetivo especifico mensuravel e com prazo', 'Objeto de arte', 'Software de monitoramento', 'Orcamento basico', 0, 20, 3),
('Casa 15', 'O que e a Linha do Tempo em um projeto?', 'Cronograma de atividades', 'Historia da empresa', 'Lista de custos', 'Perfil da equipe', 0, 20, 3),

-- FASE 4: Inteligencia Emocional e Social (Casas 16-20)
('Casa 16', 'A Janela de Johari e usada para:', 'Ampliar autoconhecimento interpessoal', 'Calcular orcamentos', 'Organizar arquivos', 'Criar sites', 0, 25, 4),
('Casa 17', 'O que sao Soft Skills?', 'Competencias socioemocionais', 'Habilidades de programacao', 'Ferramentas digitais', 'Tecnicas de vendas', 0, 25, 4),
('Casa 18', 'Inteligencia emocional envolve:', 'Reconhecer e gerir emocoes', 'Calcular dados numericos', 'Programar sistemas', 'Gerenciar financas', 0, 25, 4),
('Casa 19', 'A Analise Transacional estuda:', 'Relacoes e comunicacao entre pessoas', 'Transacoes bancarias', 'Contratos empresariais', 'Operacoes matematicas', 0, 25, 4),
('Casa 20', 'Trabalho em equipe exige principalmente:', 'Empatia e colaboracao', 'Competicao individual', 'Hierarquia rigida', 'Isolamento de tarefas', 0, 25, 4),

-- FASE 5: Carreira e Mundo Profissional (Casas 21-25)
('Casa 21', 'O que e Elevator Pitch?', 'Apresentacao profissional rapida e objetiva', 'Tipo de elevador industrial', 'Ferramenta de design', 'Relatorio financeiro', 0, 30, 5),
('Casa 22', 'Networking e definido como:', 'Construcao de rede de relacionamentos', 'Tipo de rede de internet', 'Software de comunicacao', 'Protocolo de seguranca', 0, 30, 5),
('Casa 23', 'O que e Storytelling no contexto profissional?', 'Arte de contar historias com proposito', 'Tecnica de programacao', 'Modelo de planilha', 'Sistema de arquivos', 0, 30, 5),
('Casa 24', 'A Matriz de Risco avalia eventos pelo seu:', 'Impacto e probabilidade', 'Custo e prazo', 'Tamanho e formato', 'Numero e codigo', 0, 30, 5),
('Casa 25', 'O que e prototipacao no contexto de projetos?', 'Criacao de versao inicial para testar ideias', 'Producao final do produto', 'Contratacao de equipe', 'Analise de concorrentes', 0, 30, 5),

-- FASE 6: Identidade Profissional e Comunicacao (Casas 26-30)
('Casa 26', 'A Capsula do Tempo na disciplina tem como objetivo:', 'Registrar reflexoes e projecoes de futuro', 'Guardar documentos antigos', 'Arquivar projetos concluidos', 'Calcular tempo de projeto', 0, 35, 6),
('Casa 27', 'A analise SWOT de fatores INTERNOS inclui:', 'Forcas e Fraquezas', 'Oportunidades e Ameacas', 'Custos e Lucros', 'Clientes e Fornecedores', 0, 35, 6),
('Casa 28', 'O que e etica profissional universitaria?', 'Uso consciente de recursos com responsabilidade', 'Tecnica de estudo avancado', 'Metodo de avaliacao', 'Regra de formatacao de trabalhos', 0, 35, 6),
('Casa 29', 'O BSC (Balanced Scorecard) organiza objetivos em:', 'Perspectivas com indicadores e metas', 'Planilhas de custos mensais', 'Listas de tarefas diarias', 'Relatorios de vendas', 0, 35, 6),
('Casa 30', 'A trilha da disciplina CDP encerra com a etapa do:', 'Realizador: colocar em pratica', 'Explorador: conhecer o ambiente', 'Conector: identificar competencias', 'Planejador: definir metas', 0, 35, 6);