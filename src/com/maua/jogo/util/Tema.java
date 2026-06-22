package com.maua.jogo.util;

import com.maua.jogo.model.Fase;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tema e utilitários de ACESSIBILIDADE centralizados.
 *
 * Concentra fontes (base >= 16px), cores, Alto Contraste, escala de fonte (zoom A-/A+),
 * FONTE PARA DISLÉXICOS (OpenDyslexic empacotada em /fonts) e os rótulos/ícones
 * não-cromáticos das fases. Web usa CSS/ARIA; aqui o equivalente é java.awt.Font/Color
 * + Java Accessibility API (AccessibleContext), lida por leitores de tela (NVDA) via
 * Java Access Bridge.
 */
public final class Tema {

    private Tema() {}

    /** Estado global do tema (compartilhado por todas as telas). */
    public static boolean altoContraste = false;
    public static boolean fonteDislexica = false;
    public static float escala = 1f;

    private static final String FONTE = "Arial";
    private static final String FONTE_DISLEXICA; // resolvida no static init

    // Paleta própria "Jornada do Conhecimento" — petróleo/teal + âmbar.
    // (contrastes validados: branco s/ teal 5.5:1; âmbar s/ teal escuro 4.7:1)
    public static final Color MAUA_AZUL        = new Color(15, 118, 110); // primária (teal)
    public static final Color MAUA_AZUL_BTN    = new Color(13, 110, 103); // botões
    public static final Color MAUA_AZUL_ESCURO = new Color(10, 74, 70);   // faixa lateral / fundo
    public static final Color MAUA_CIANO       = new Color(245, 158, 11); // acento (âmbar)
    public static final Color CINZA_CARD       = new Color(236, 239, 244); // cards de opção
    public static final Color CINZA_TEXTO      = new Color(70, 75, 85);

    private static final String[] COR_KEYS = {
        "control", "nimbusBase", "nimbusLightBackground", "text",
        "nimbusFocus", "nimbusSelectionBackground", "info", "Panel.background"
    };
    private static Map<String, Object> originais;

    static {
        carregarOpenDyslexic();
        FONTE_DISLEXICA = melhorFonteDislexica();
    }

    /** Registra os arquivos da OpenDyslexic (em /fonts) para uso em runtime. */
    private static void carregarOpenDyslexic() {
        String[] arquivos = {
            "OpenDyslexic-Regular.otf", "OpenDyslexic-Bold.otf",
            "OpenDyslexic-Italic.otf", "OpenDyslexic-Bold-Italic.otf"
        };
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (String nome : arquivos) {
            try {
                InputStream in = null;
                File f = new File("fonts/" + nome);
                if (!f.exists()) {
                    // resolve relativo à localização do código (independe do diretório atual)
                    try {
                        File code = new File(Tema.class.getProtectionDomain()
                                .getCodeSource().getLocation().toURI());
                        File dir = code.isFile() ? code.getParentFile() : code; // pasta do jar/bin
                        File cand = new File(dir.getParentFile(), "fonts/" + nome); // raiz do projeto
                        if (cand.exists()) f = cand;
                    } catch (Exception ignore) { /* tenta classpath abaixo */ }
                }
                if (f.exists()) {
                    in = new java.io.FileInputStream(f);
                } else {
                    in = Tema.class.getResourceAsStream("/fonts/" + nome); // fallback classpath
                }
                if (in == null) continue;
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, in));
                in.close();
            } catch (Exception ignored) {
                // se falhar, cai no fallback (Comic Sans MS / Verdana)
            }
        }
    }

    /** Melhor fonte pró-leitura disponível (OpenDyslexic se carregada; senão fallback). */
    private static String melhorFonteDislexica() {
        String[] pref = {"OpenDyslexic", "Lexend", "Atkinson Hyperlegible", "Comic Sans MS", "Verdana", "Tahoma"};
        Set<String> disp = new HashSet<>(Arrays.asList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        for (String p : pref) if (disp.contains(p)) return p;
        return FONTE;
    }

    /** Família de fonte ativa conforme o modo disléxico. */
    public static String familiaAtual() {
        return fonteDislexica ? FONTE_DISLEXICA : FONTE;
    }

    /** Nome da fonte disléxica resolvida (para diagnóstico). */
    public static String familiaDislexica() {
        return FONTE_DISLEXICA;
    }

    /**
     * Cria uma fonte na família ativa, JÁ APLICANDO a escala (zoom A-/A+); em modo
     * disléxico aplica espaçamento extra entre letras. O {@code size} é o tamanho base.
     */
    /** Fator para a OpenDyslexic ter o MESMO tamanho visual da fonte normal. */
    public static final double FATOR_DISLEXICA = 0.82;

    public static Font fonte(int style, int size) {
        double px = size * escala;
        if (fonteDislexica) px *= FATOR_DISLEXICA; // iguala o tamanho visual ao da fonte normal
        return new Font(familiaAtual(), style, Math.max(1, (int) Math.round(px)));
    }

    private static boolean ajusteInstalado = false;

    /**
     * Instala um ajuste GLOBAL de janelas: toda janela aberta (inclusive os modais do
     * sistema, como JOptionPane, que sob Nimbus ignoram a troca de fonte em runtime) tem
     * suas fontes alinhadas à família/escala atuais. Componentes das nossas telas (com
     * tag de fonte) são preservados, pois já têm gestão própria. Chamar uma vez no início.
     */
    public static void instalarAjusteDeJanelas() {
        if (ajusteInstalado) return;
        ajusteInstalado = true;
        Toolkit.getDefaultToolkit().addAWTEventListener(e -> {
            if (e.getID() == WindowEvent.WINDOW_OPENED && e.getSource() instanceof Window) {
                ajustarJanela((Window) e.getSource());
            }
        }, AWTEvent.WINDOW_EVENT_MASK);
    }

    private static void ajustarJanela(Component c) {
        if (c instanceof JComponent) {
            JComponent jc = (JComponent) c;
            if (jc.getClientProperty("temaBaseSize") == null) { // só componentes "de fora"
                Font f = jc.getFont();
                if (f != null) {
                    double px = f.getSize() * escala;
                    if (fonteDislexica) px *= FATOR_DISLEXICA;
                    jc.setFont(new Font(familiaAtual(), f.getStyle(), Math.max(1, (int) Math.round(px))));
                }
            }
        }
        if (c instanceof Container) {
            for (Component ch : ((Container) c).getComponents()) ajustarJanela(ch);
        }
    }

    /**
     * Marca cada componente da árvore com seu tamanho/estilo de fonte BASE, para que
     * o zoom e a fonte disléxica possam ser reaplicados depois (ver {@link #reaplicar}).
     * Chamar uma vez ao final da construção de cada tela.
     */
    public static void registrarFontes(Component raiz) {
        if (raiz instanceof JComponent) {
            JComponent jc = (JComponent) raiz;
            Font f = jc.getFont();
            if (f != null && jc.getClientProperty("temaBaseSize") == null) {
                int base = escala > 0 ? Math.round(f.getSize() / escala) : f.getSize();
                jc.putClientProperty("temaBaseSize", base);
                jc.putClientProperty("temaBaseStyle", f.getStyle());
            }
        }
        if (raiz instanceof Container) {
            for (Component c : ((Container) raiz).getComponents()) registrarFontes(c);
        }
    }

    private static void reaplicarFontes(Component raiz) {
        if (raiz instanceof JComponent) {
            JComponent jc = (JComponent) raiz;
            Object base = jc.getClientProperty("temaBaseSize");
            Object style = jc.getClientProperty("temaBaseStyle");
            if (base instanceof Integer && style instanceof Integer) {
                jc.setFont(fonte((Integer) style, (Integer) base));
            }
        }
        if (raiz instanceof Container) {
            for (Component c : ((Container) raiz).getComponents()) reaplicarFontes(c);
        }
    }

    /** Tamanho da fonte base já considerando a escala (zoom). */
    public static int tamBase() {
        return Math.round(16 * escala);
    }

    private static void capturarOriginais() {
        if (originais == null) {
            originais = new HashMap<>();
            for (String k : COR_KEYS) {
                originais.put(k, UIManager.get(k));
            }
        }
    }

    /** Aplica fontes (>=16px, família ativa) e, se ligado, o Alto Contraste ao UIManager. */
    public static void aplicar() {
        capturarOriginais();

        int b = tamBase();
        Font corpo = fonte(Font.PLAIN, b);
        Font negrito = fonte(Font.BOLD, b);

        String[] fontKeys = {
            "defaultFont", "Label.font", "Button.font", "ToggleButton.font", "TextField.font",
            "PasswordField.font", "FormattedTextField.font", "TextArea.font", "TextPane.font",
            "Table.font", "TableHeader.font", "List.font", "ComboBox.font", "CheckBox.font",
            "RadioButton.font", "ToolTip.font", "OptionPane.font", "OptionPane.messageFont",
            "OptionPane.buttonFont", "TabbedPane.font", "Spinner.font"
        };
        for (String k : fontKeys) {
            UIManager.put(k, corpo);
        }
        UIManager.getLookAndFeelDefaults().put("defaultFont", corpo);
        UIManager.put("TitledBorder.font", negrito);

        if (altoContraste) {
            Color fundo = Color.BLACK;
            Color frente = Color.WHITE;
            UIManager.put("control", fundo);
            UIManager.put("nimbusBase", new Color(20, 20, 20));
            UIManager.put("nimbusLightBackground", fundo);
            UIManager.put("text", frente);
            UIManager.put("nimbusFocus", new Color(255, 215, 0));
            UIManager.put("nimbusSelectionBackground", new Color(0, 120, 110));
            UIManager.put("info", new Color(30, 30, 30));
            UIManager.put("Panel.background", fundo);
        } else {
            for (String k : COR_KEYS) {
                UIManager.put(k, originais.get(k));
            }
        }
    }

    /** Reaplica o tema (cores + fontes) e atualiza a árvore da janela em tempo real. */
    public static void reaplicar(Window janela) {
        aplicar();
        if (janela != null) {
            SwingUtilities.updateComponentTreeUI(janela);
            reaplicarFontes(janela);   // reescala/troca família dos textos com fonte própria
            janela.revalidate();
            janela.repaint();
        }
    }

    /** Define nome e descrição acessíveis (lidos por NVDA via Java Access Bridge). */
    public static void acessivel(JComponent c, String nome, String descricao) {
        c.getAccessibleContext().setAccessibleName(nome);
        if (descricao != null) {
            c.getAccessibleContext().setAccessibleDescription(descricao);
        }
    }

    // -------------------- Imagem do tabuleiro --------------------

    private static java.awt.image.BufferedImage imgNormal, imgDislex;
    private static boolean tentN, tentD;

    /** Imagem de fundo do tabuleiro (versão disléxica quando o modo está ativo). */
    public static java.awt.image.BufferedImage imagemTabuleiro() {
        if (fonteDislexica) {
            if (!tentD) { tentD = true; imgDislex = carregarImagem("imagens/tabuleiro_fundo_dislexico.png"); }
            if (imgDislex != null) return imgDislex;
        }
        if (!tentN) { tentN = true; imgNormal = carregarImagem("imagens/tabuleiro_fundo.png"); }
        return imgNormal;
    }

    private static java.awt.image.BufferedImage carregarImagem(String rel) {
        try {
            File f = new File(rel);
            if (!f.exists()) {
                try {
                    File code = new File(Tema.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                    File dir = code.isFile() ? code.getParentFile() : code;
                    File cand = new File(dir.getParentFile(), rel);
                    if (cand.exists()) f = cand;
                } catch (Exception ignore) { }
            }
            if (f.exists()) return javax.imageio.ImageIO.read(f);
            InputStream in = Tema.class.getResourceAsStream("/" + rel);
            if (in != null) return javax.imageio.ImageIO.read(in);
        } catch (Exception ignored) { }
        return null;
    }

    // -------------------- Fases: cor + redundância não-cromática --------------------

    /** Cor de fundo da fase (esquema claro normal; quase preto em Alto Contraste). */
    public static Color corFase(Fase f) {
        if (altoContraste) {
            return new Color(15, 15, 15);
        }
        switch (f) {
            case EXPLORADOR:    return new Color(220, 255, 220);
            case CONECTOR:      return new Color(220, 220, 255);
            case TRANSFORMADOR: return new Color(255, 255, 220);
            case CONHECEDOR:    return new Color(255, 220, 255);
            case PLANEJADOR:    return new Color(220, 255, 255);
            case REALIZADOR:    return new Color(255, 220, 220);
            default:            return Color.WHITE;
        }
    }

    /** Cor de texto sobre as casas (preto no normal, branco em Alto Contraste). */
    public static Color corTextoCasa() {
        return altoContraste ? Color.WHITE : Color.BLACK;
    }

    /** Sigla legível por fase — alternativa textual à cor (WCAG 1.4.1). */
    public static String siglaFase(Fase f) {
        switch (f) {
            case EXPLORADOR:    return "EXP";
            case CONECTOR:      return "CNT";
            case TRANSFORMADOR: return "TRF";
            case CONHECEDOR:    return "CNH";
            case PLANEJADOR:    return "PLN";
            case REALIZADOR:    return "REA";
            default:            return "?";
        }
    }

    /** Ícone com FORMA distinta por fase — redundância por forma, não só cor. */
    public static Icon iconeFase(Fase f) {
        return new FaseIcon(f);
    }

    private static final class FaseIcon implements Icon {
        private final Fase fase;

        FaseIcon(Fase f) {
            this.fase = f;
        }

        @Override public int getIconWidth() { return 16; }
        @Override public int getIconHeight() { return 16; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(altoContraste ? Color.WHITE : new Color(40, 40, 40));
            int s = 12, ox = x + 2, oy = y + 2;
            switch (fase) {
                case EXPLORADOR:    // círculo
                    g2.fillOval(ox, oy, s, s);
                    break;
                case CONECTOR:      // quadrado
                    g2.fillRect(ox, oy, s, s);
                    break;
                case TRANSFORMADOR: // triângulo
                    g2.fillPolygon(new int[]{ox + s / 2, ox, ox + s}, new int[]{oy, oy + s, oy + s}, 3);
                    break;
                case CONHECEDOR:    // losango
                    g2.fillPolygon(new int[]{ox + s / 2, ox, ox + s / 2, ox + s},
                                   new int[]{oy, oy + s / 2, oy + s, oy + s / 2}, 4);
                    break;
                case PLANEJADOR:    // asterisco (linhas)
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawLine(ox, oy + s / 2, ox + s, oy + s / 2);
                    g2.drawLine(ox + s / 2, oy, ox + s / 2, oy + s);
                    g2.drawLine(ox, oy, ox + s, oy + s);
                    g2.drawLine(ox + s, oy, ox, oy + s);
                    break;
                case REALIZADOR:    // hexágono
                    g2.fillPolygon(new int[]{ox + s / 4, ox + 3 * s / 4, ox + s, ox + 3 * s / 4, ox + s / 4, ox},
                                   new int[]{oy, oy, oy + s / 2, oy + s, oy + s, oy + s / 2}, 6);
                    break;
                default:
                    break;
            }
            g2.dispose();
        }
    }

    // -------------------- Controles de acessibilidade reutilizáveis --------------------

    /**
     * Cria o painel com A- / A+ / Alto Contraste / Fonte Disléxica. Ao alternar, reaplica
     * o tema na janela e chama {@code aoMudar} (para recolorir componentes com cor própria).
     */
    public static JPanel painelAcessibilidade(JFrame janela, Runnable aoMudar) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 2));

        JButton menos = new JButton("A-");
        JButton mais = new JButton("A+");
        JToggleButton contraste = new JToggleButton("Alto Contraste");
        JToggleButton dislexia = new JToggleButton("Fonte Disléxica");
        contraste.setSelected(altoContraste);
        dislexia.setSelected(fonteDislexica);

        acessivel(menos, "Diminuir fonte", "Reduz o tamanho da fonte de toda a interface");
        acessivel(mais, "Aumentar fonte", "Aumenta o tamanho da fonte de toda a interface");
        acessivel(contraste, "Alto contraste", "Liga ou desliga o modo de alto contraste");
        acessivel(dislexia, "Fonte para disléxicos",
                "Alterna a fonte OpenDyslexic, mais legível para pessoas com dislexia");
        dislexia.setToolTipText("Fonte OpenDyslexic (mais legível p/ dislexia)");

        // A-/A+ com largura fixa; os toggles dimensionam pelo conteúdo (não cortam no zoom)
        menos.setPreferredSize(new Dimension(56, 36));
        mais.setPreferredSize(new Dimension(56, 36));
        contraste.setMargin(new java.awt.Insets(6, 12, 6, 12));
        dislexia.setMargin(new java.awt.Insets(6, 12, 6, 12));

        menos.addActionListener(e -> {
            escala = Math.max(0.85f, escala - 0.15f);
            reaplicar(janela);
            if (aoMudar != null) aoMudar.run();
        });
        mais.addActionListener(e -> {
            escala = Math.min(2.0f, escala + 0.15f);
            reaplicar(janela);
            if (aoMudar != null) aoMudar.run();
        });
        contraste.addActionListener(e -> {
            altoContraste = contraste.isSelected();
            reaplicar(janela);
            if (aoMudar != null) aoMudar.run();
        });
        dislexia.addActionListener(e -> {
            fonteDislexica = dislexia.isSelected();
            reaplicar(janela);
            if (aoMudar != null) aoMudar.run();
        });

        p.add(menos);
        p.add(mais);
        p.add(contraste);
        p.add(dislexia);
        return p;
    }

    // -------------------- Helpers de estilo --------------------

    /** Botão primário (cor cheia da marca, texto branco). */
    public static JButton botaoPrimario(String texto) {
        JButton b = new JButton(texto);
        b.setFont(fonte(Font.BOLD, Math.max(16, tamBase())));
        b.setForeground(Color.WHITE);
        b.setBackground(MAUA_AZUL_BTN);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        return b;
    }

    /** Logo textual do IMT (placeholder sem arquivo de imagem). */
    public static JComponent logoMaua(boolean claro) {
        JLabel l = new JLabel("IMT", SwingConstants.CENTER);
        l.setFont(fonte(Font.BOLD, 18));
        l.setForeground(claro ? Color.WHITE : MAUA_AZUL);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(claro ? Color.WHITE : MAUA_AZUL, 2, true),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));
        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrap.setOpaque(false);
        wrap.add(l);
        return wrap;
    }
}
