import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    JFrame janela;
    JButton btnOK;
    JTextArea ta;
    String dataFormatada;
    DefaultTableModel modelo;
    JComboBox cbMoeda;
    JTextField tfValor;
    JButton btnSair;
    JTable tabela;
    String valorLog;

    public GUI() {
        inicializandoComponentes();
        definirEventos();
    }

    public void inicializandoComponentes() {
        janela = new JFrame("Conversor de Moedas");
        janela.setSize(800, 350);

        JPanel panel = new JPanel();
        panel.setSize(800, 600);
        panel.setLayout(null);

        JLabel lbMoeda = new JLabel("Moeda");
        lbMoeda.setBounds(10, 10, 200, 20);

        cbMoeda = new JComboBox();
        cbMoeda.setBounds(10, 30, 200, 20);

        for (var tipoMoeda : TipoMoeda.values()) {
            cbMoeda.addItem(tipoMoeda);
        }

        JLabel lbValor = new JLabel("Valor (Ex: 100.00)");
        lbValor.setBounds(220, 10, 100, 20);

        tfValor = new JTextField(10);
        ((AbstractDocument) tfValor.getDocument()).setDocumentFilter(new FiltroMoeda());
        tfValor.setBounds(220, 30, 120, 22);

        btnOK = new JButton("OK");
        btnOK.setBounds(350, 30, 60, 20);

        String[] colunas = {"Moeda", "Valor"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabela);
        sp.setBounds(10, 60, 400, 200);

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        dataFormatada = agora.format(formatador);

        ta = new JTextArea();
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        JScrollPane spTextArea = new JScrollPane(ta);
        spTextArea.setBounds(420, 60, 360, 200);
        spTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        ta.append(dataFormatada + ": Iniciando\n");

        JLabel lbObs = new JLabel("Após finalizar o sistema, um Log será gerado com a sua consulta.");
        lbObs.setBounds(10, 280, 400, 20);

        btnSair = new JButton("Sair");
        btnSair.setBounds(720, 280, 60, 20);

        panel.add(lbMoeda);
        panel.add(cbMoeda);
        panel.add(lbValor);
        panel.add(tfValor);
        panel.add(btnOK);
        panel.add(btnSair);
        panel.add(sp);
        panel.add(spTextArea);
        panel.add(lbObs);

        janela.add(panel);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }

    public void definirEventos() {
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfValor.getText().equals("")) {
                    ta.append(dataFormatada + ": Processando dados, ajustando variáveis, verificando equações... " +
                            "Respire fundo e aguarde, porque a resposta está a caminho!\n");

                    List<String> listaMoeda = new ArrayList<>();
                    modelo.setRowCount(0);

                    Object[] dados = null;

                    for (TipoMoeda tp : TipoMoeda.values()) {
                        if (!cbMoeda.getSelectedItem().equals(tp)) {
                            listaMoeda.add(tp.moeda);
                            BuscaMoeda busca = new BuscaMoeda(TipoMoeda.valueOf(cbMoeda.getSelectedItem() + ""), tp);
                            String json = null;
                            valorLog = tfValor.getText();

                            try {
                                json = busca.buscaMoedaJson();
                            } catch (IOException | InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                            ConverteMoeda cm = new ConverteMoeda(Double.parseDouble(
                                    tfValor.getText().replace(",", ".")));
                            String vl = cm.converteMoeda(json);

                            dados = new Object[]{tp.moeda, vl};
                            modelo.addRow(dados);
                        }
                    }
                    tfValor.setText("");
                    tfValor.requestFocus();

                    ta.append(dataFormatada + ": Concluído\n");
                    ta.append("____________________________________________________\n");
                } else {
                    JOptionPane.showMessageDialog(
                            null, "Campo valor não pode ser vazio!",
                            "Informação", JOptionPane.INFORMATION_MESSAGE);
                    tfValor.requestFocus();
                }
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarParaArquivoTexto(tabela,
                        "Log" + dataFormatada.substring(0,10).
                                replaceAll("[^0-9]", "") + ".txt", dataFormatada,
                        cbMoeda.getSelectedItem() + " " + valorLog);
                janela.dispose();
            }
        });
    }

    public static void exportarParaArquivoTexto(JTable tabela, String caminhoArquivo, String data, String valorInicial) {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            TableModel model = tabela.getModel();
            writer.append("Data da consulta: " + data + "\n");
            writer.append("Valor Original: " + valorInicial + "\n");

            // Escrevendo os nomes das colunas
            for (int col = 0; col < model.getColumnCount(); col++) {
                writer.append(model.getColumnName(col)).append("\t");
            }
            writer.append("\n");

            // Escrevendo os dados da tabela
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    writer.append(model.getValueAt(row, col).toString()).append("\t");
                }
                writer.append("\n");
            }

           System.out.println("Exportação concluída com sucesso! Verifique Log.txt.");
        } catch (IOException e) {
            System.out.println("Erro ao exportar tabela: " + e.getMessage());
        }
    }
}
