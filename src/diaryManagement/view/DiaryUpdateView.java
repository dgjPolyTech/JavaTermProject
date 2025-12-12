package diaryManagement.view;

import diaryManagement.domain.DiaryVO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class DiaryUpdateView extends JPanel {
    JTable table;
    DefaultTableModel model;
    ArrayList<DiaryVO> diaryVOList;

    String[] header = {"번호", "작성자", "제목", "날짜", "날씨", "감정"};
    String[] emotionCodes = {"N", "H", "A", "S", "F"};
    String[] weatherNames = {"sun", "rain", "cold", "snow", "wind", "hot"};

    JPanel panS;
    JTextField tfNo, tfTitle;
    JComboBox<String> comboYear, comboMonth, comboDay; // 날짜용
    JComboBox<String> comboWeather, comboEmotion;
    JTextArea taContent;
    JButton btnUpdate, btnDelete;

    public DiaryUpdateView(){
        setLayout(new BorderLayout());
    }

    public void initView(){
        removeAll();

        if(diaryVOList == null) diaryVOList = new ArrayList<>();
        model = new DefaultTableModel(header, 0){
            public boolean isCellEditable(int row, int column){ return false; }
        };
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(table);

        tfNo = new JTextField(5); tfNo.setEditable(false);
        tfTitle = new JTextField(15);

        Vector<String> years = new Vector<>();
        for(int i=2020; i<=2030; i++) years.add(String.valueOf(i));
        comboYear = new JComboBox<>(years);

        Vector<String> months = new Vector<>();
        for(int i=1; i<=12; i++) months.add(String.format("%02d", i));
        comboMonth = new JComboBox<>(months);

        Vector<String> days = new Vector<>();
        for(int i=1; i<=31; i++) days.add(String.format("%02d", i));
        comboDay = new JComboBox<>(days);

        comboWeather = new JComboBox<>(weatherNames);
        comboEmotion = new JComboBox<>(emotionCodes);
        taContent = new JTextArea(3, 30);

        btnUpdate = new JButton("일기 수정");
        btnDelete = new JButton("일기 삭제");

        panS = new JPanel(new BorderLayout());
        JPanel panFields = new JPanel(new GridLayout(3, 1));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("번호:")); p1.add(tfNo);
        p1.add(new JLabel("제목:")); p1.add(tfTitle);
        panFields.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("날짜:"));
        p2.add(comboYear); p2.add(new JLabel("-"));
        p2.add(comboMonth); p2.add(new JLabel("-"));
        p2.add(comboDay);
        p2.add(new JLabel(" 날씨:")); p2.add(comboWeather);
        p2.add(new JLabel(" 감정:")); p2.add(comboEmotion);
        panFields.add(p2);

        JPanel p3 = new JPanel(new BorderLayout());
        p3.add(new JLabel("내용: "), BorderLayout.WEST);
        p3.add(new JScrollPane(taContent), BorderLayout.CENTER);

        JPanel pBtn = new JPanel();
        pBtn.add(btnUpdate);
        pBtn.add(btnDelete);

        panS.add(panFields, BorderLayout.NORTH);
        panS.add(p3, BorderLayout.CENTER);
        panS.add(pBtn, BorderLayout.SOUTH);

        putSearchResult();

        add(scrollPane, BorderLayout.CENTER);
        add(panS, BorderLayout.SOUTH);
        revalidate();
    }

    public void setFields(DiaryVO vo) {
        tfNo.setText(String.valueOf(vo.getDiaryNo()));
        tfTitle.setText(vo.getTitle());

        try {
            String fullDate = vo.getDay();
            String datePart = fullDate.split(" ")[0];
            String[] parts = datePart.split("-");
            comboYear.setSelectedItem(parts[0]);
            comboMonth.setSelectedItem(parts[1]);
            comboDay.setSelectedItem(parts[2]);
        } catch (Exception e) {
        }

        comboWeather.setSelectedItem(vo.getWeather());
        comboEmotion.setSelectedItem(vo.getEmotion());
        taContent.setText(vo.getContent());
    }

    public void initUpdateData() {
        tfNo.setText("");
        tfTitle.setText("");
        taContent.setText("");
    }

    public void putSearchResult() {
        if (model == null) return;
        model.setRowCount(0);
        for(DiaryVO vo : diaryVOList){
            model.addRow(new Object[]{vo.getDiaryNo(), vo.getWriterNo(), vo.getTitle(), vo.getDay(), vo.getWeather(), vo.getEmotion()});
        }
    }

    public DiaryVO neededUpdateData() {
        DiaryVO vo = new DiaryVO();
        vo.setDiaryNo(Integer.parseInt(tfNo.getText()));
        vo.setTitle(tfTitle.getText());

        String dateStr = comboYear.getSelectedItem() + "-" +
                comboMonth.getSelectedItem() + "-" +
                comboDay.getSelectedItem();
        vo.setDay(dateStr);

        vo.setContent(taContent.getText());
        vo.setWeather((String) comboWeather.getSelectedItem());
        vo.setEmotion((String) comboEmotion.getSelectedItem());
        return vo;
    }

    public void setList(ArrayList<DiaryVO> list){ this.diaryVOList = list; }
    public JTable getTable() { return table; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public String getNo() { return tfNo.getText(); }
}