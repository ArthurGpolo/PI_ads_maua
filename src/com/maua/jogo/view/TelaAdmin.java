package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.model.Desafio;
import com.maua.jogo.model.Fase;
import com.maua.jogo.model.Jogador;
import com.maua.jogo.util.DesafioDAO;
import com.maua.jogo.util.JogadorDAO;
import com.maua.jogo.util.Tema;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Área do Professor: administração (CRUD) de Desafios e Jogadores.
 * Acessível ao marcar "Sou Professor" no login.
 */
public class TelaAdmin extends JFrame {
    private final JogoController controller;
    private JTable tabDesafios, tabJogadores;
    private DefaultTableModel modelDesafios, modelJogadores;
    private static final char[] LETRAS = {'A', 'B', 'C', 'D'};

    public TelaAdmin(JogoController controller) {
        this.controller = controller;
        initComponents();
        carregarDesafios();
        carregarJogadores();
    }

    private void initComponents() {
        setTitle("Jornada do Conhecimento - Administração (Professor)");
        setSize(960, 640);
        setMinimumSize(new Dimension(800, 540));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { sair(); }
        });
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Cabeçalho Mauá
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Tema.MAUA_AZUL);
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel titulo = new JLabel("Administração — Professor");
        titulo.setFont(Tema.fonte(Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);
        JButton btnSair = new JButton("Sair");
        btnSair.setMnemonic('S');
        Tema.acessivel(btnSair, "Sair", "Encerra a sessão do professor e volta ao login");
        btnSair.addActionListener(e -> sair());
        JPanel topoDir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topoDir.setOpaque(false);
        topoDir.add(btnSair);
        header.add(topoDir, BorderLayout.EAST);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(Tema.painelAcessibilidade(this, this::repaint), BorderLayout.NORTH);
        norte.add(header, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.setFont(Tema.fonte(Font.BOLD, 15));
        abas.addTab("Desafios", criarAbaDesafios());
        abas.addTab("Jogadores", criarAbaJogadores());
        add(abas, BorderLayout.CENTER);

        Tema.registrarFontes(this);
    }

    // ---------------- Aba Desafios ----------------

    private JComponent criarAbaDesafios() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modelDesafios = new DefaultTableModel(
                new String[]{"ID", "Título", "Fase", "Pontos", "Resp.", "Pergunta"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabDesafios = new JTable(modelDesafios);
        tabDesafios.setRowHeight(26);
        tabDesafios.getAccessibleContext().setAccessibleName("Tabela de desafios");
        p.add(new JScrollPane(tabDesafios), BorderLayout.CENTER);

        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        barra.add(botao("Novo", 'N', e -> editarDesafio(null)));
        barra.add(botao("Editar", 'E', e -> {
            Desafio d = desafioSelecionado();
            if (d != null) editarDesafio(d);
        }));
        barra.add(botao("Excluir", 'X', e -> excluirDesafio()));
        barra.add(botao("Atualizar", 'A', e -> carregarDesafios()));
        p.add(barra, BorderLayout.SOUTH);
        return p;
    }

    private void carregarDesafios() {
        modelDesafios.setRowCount(0);
        List<Desafio> desafios = DesafioDAO.obterTodosDesafios();
        for (Desafio d : desafios) {
            String resp = (d.getRespostaCorreta() >= 0 && d.getRespostaCorreta() < 4)
                    ? String.valueOf(LETRAS[d.getRespostaCorreta()]) : "?";
            modelDesafios.addRow(new Object[]{
                    d.getId(), d.getTitulo(), d.getFase().getNome(), d.getPontos(), resp, d.getPergunta()});
        }
    }

    private Desafio desafioSelecionado() {
        int row = tabDesafios.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um desafio na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int id = (int) modelDesafios.getValueAt(row, 0);
        return DesafioDAO.obterDesafioPorId(id);
    }

    /** Formulário de criação/edição de desafio. */
    private void editarDesafio(Desafio existente) {
        boolean novo = (existente == null);
        JTextField txtTitulo = new JTextField(novo ? "" : existente.getTitulo());
        JTextField txtPergunta = new JTextField(novo ? "" : existente.getPergunta());
        JTextField[] txtOpc = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            txtOpc[i] = new JTextField(novo || existente.getOpcoes() == null ? "" : existente.getOpcoes()[i]);
        }
        JComboBox<String> cbResp = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        if (!novo) cbResp.setSelectedIndex(Math.max(0, existente.getRespostaCorreta()));
        JSpinner spPontos = new JSpinner(new SpinnerNumberModel(novo ? 10 : existente.getPontos(), 0, 1000, 5));
        JComboBox<Fase> cbFase = new JComboBox<>(Fase.values());
        if (!novo) cbFase.setSelectedItem(existente.getFase());

        JPanel form = new JPanel(new GridLayout(0, 1, 4, 4));
        form.add(new JLabel("Título:"));        form.add(txtTitulo);
        form.add(new JLabel("Pergunta:"));      form.add(txtPergunta);
        form.add(new JLabel("Opção A:"));       form.add(txtOpc[0]);
        form.add(new JLabel("Opção B:"));       form.add(txtOpc[1]);
        form.add(new JLabel("Opção C:"));       form.add(txtOpc[2]);
        form.add(new JLabel("Opção D:"));       form.add(txtOpc[3]);
        form.add(new JLabel("Resposta correta:")); form.add(cbResp);
        form.add(new JLabel("Pontos:"));        form.add(spPontos);
        form.add(new JLabel("Fase:"));          form.add(cbFase);
        JScrollPane scroll = new JScrollPane(form);
        scroll.setPreferredSize(new Dimension(460, 420));

        int r = JOptionPane.showConfirmDialog(this, scroll,
                novo ? "Novo Desafio" : "Editar Desafio #" + existente.getId(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r != JOptionPane.OK_OPTION) return;

        if (txtTitulo.getText().trim().isEmpty() || txtPergunta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título e pergunta são obrigatórios.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Desafio d = novo ? new Desafio() : existente;
        d.setTitulo(txtTitulo.getText().trim());
        d.setDescricao(novo ? "" : d.getDescricao());
        d.setPergunta(txtPergunta.getText().trim());
        d.setOpcoes(new String[]{txtOpc[0].getText(), txtOpc[1].getText(), txtOpc[2].getText(), txtOpc[3].getText()});
        d.setRespostaCorreta(cbResp.getSelectedIndex());
        d.setPontos((int) spPontos.getValue());
        d.setFase((Fase) cbFase.getSelectedItem());

        boolean ok = novo ? DesafioDAO.criarDesafio(d) : DesafioDAO.atualizarDesafio(d);
        if (ok) carregarDesafios();
        else JOptionPane.showMessageDialog(this, "Não foi possível salvar o desafio.", "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void excluirDesafio() {
        Desafio d = desafioSelecionado();
        if (d == null) return;
        int r = JOptionPane.showConfirmDialog(this,
                "Excluir o desafio #" + d.getId() + " (" + d.getTitulo() + ")?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            if (DesafioDAO.deletarDesafio(d.getId())) carregarDesafios();
            else JOptionPane.showMessageDialog(this, "Não foi possível excluir (pode haver respostas vinculadas).",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- Aba Jogadores ----------------

    private JComponent criarAbaJogadores() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modelJogadores = new DefaultTableModel(
                new String[]{"ID", "Nome", "E-mail", "Pontos", "Posição", "Fase"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabJogadores = new JTable(modelJogadores);
        tabJogadores.setRowHeight(26);
        tabJogadores.getAccessibleContext().setAccessibleName("Tabela de jogadores");
        p.add(new JScrollPane(tabJogadores), BorderLayout.CENTER);

        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        barra.add(botao("Editar", 'E', e -> editarJogador()));
        barra.add(botao("Excluir (desativar)", 'X', e -> excluirJogador()));
        barra.add(botao("Atualizar", 'A', e -> carregarJogadores()));
        p.add(barra, BorderLayout.SOUTH);
        return p;
    }

    private Jogador jogadorSelecionado() {
        int row = tabJogadores.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int id = (int) modelJogadores.getValueAt(row, 0);
        return JogadorDAO.obterJogadorPorId(id);
    }

    private void editarJogador() {
        Jogador j = jogadorSelecionado();
        if (j == null) return;

        JTextField txtNome = new JTextField(j.getNome());
        JTextField txtEmail = new JTextField(j.getEmail());
        JSpinner spPontos = new JSpinner(new SpinnerNumberModel(j.getPontos(), 0, 1000000, 5));
        JComboBox<Fase> cbFase = new JComboBox<>(Fase.values());
        cbFase.setSelectedItem(j.getFaseAtual());

        JPanel form = new JPanel(new GridLayout(0, 1, 4, 4));
        form.add(new JLabel("Nome:"));   form.add(txtNome);
        form.add(new JLabel("E-mail:")); form.add(txtEmail);
        form.add(new JLabel("Pontos:")); form.add(spPontos);
        form.add(new JLabel("Fase:"));   form.add(cbFase);

        int r = JOptionPane.showConfirmDialog(this, form, "Editar Jogador #" + j.getId(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r != JOptionPane.OK_OPTION) return;

        if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e e-mail são obrigatórios.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Fase fase = (Fase) cbFase.getSelectedItem();
        j.setNome(txtNome.getText().trim());
        j.setEmail(txtEmail.getText().trim());
        j.setPontos((int) spPontos.getValue());
        j.setFaseAtual(fase);
        j.setPosicao(fase.getNumero()); // posição acompanha a fase (lógica de progressão)

        if (JogadorDAO.atualizarJogador(j)) {
            carregarJogadores();
            JOptionPane.showMessageDialog(this, "Jogador atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível atualizar o jogador.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarJogadores() {
        modelJogadores.setRowCount(0);
        List<Jogador> jogadores = JogadorDAO.obterTodosJogadores();
        for (Jogador j : jogadores) {
            modelJogadores.addRow(new Object[]{
                    j.getId(), j.getNome(), j.getEmail(), j.getPontos(), j.getPosicao(), j.getFaseAtual().getNome()});
        }
    }

    private void excluirJogador() {
        int row = tabJogadores.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modelJogadores.getValueAt(row, 0);
        String nome = String.valueOf(modelJogadores.getValueAt(row, 1));
        int r = JOptionPane.showConfirmDialog(this,
                "Desativar o jogador #" + id + " (" + nome + ")?",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            if (JogadorDAO.deletarJogador(id)) carregarJogadores();
            else JOptionPane.showMessageDialog(this, "Não foi possível desativar o jogador.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- util ----------------

    private JButton botao(String texto, char mnem, java.awt.event.ActionListener acao) {
        JButton b = new JButton(texto);
        b.setMnemonic(mnem);
        b.setPreferredSize(new Dimension(Math.max(120, texto.length() * 11), 40));
        Tema.acessivel(b, texto, null);
        b.addActionListener(acao);
        return b;
    }

    private void sair() {
        controller.logout();
        new TelaLogin(controller).setVisible(true);
        dispose();
    }
}
