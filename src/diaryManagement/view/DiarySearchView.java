package diaryManagement.view;

import diaryManagement.domain.DiaryVO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DiarySearchView extends JPanel {
    JTable table;
    DefaultTableModel model;
    ArrayList<DiaryVO> diaryVOList;
    String[] header = {"번호", "제목", "날짜", "날씨", "감정"};

    JTextField textSearch;
    JButton btnSearch;
    JComboBox<String> combo;
    JTextArea taContent;

    public DiarySearchView(){
        setLayout(new BorderLayout());
        JPanel panN = new JPanel();
        combo = new JComboBox<>(new String[]{"제목", "내용"});
        textSearch = new JTextField(15);
        btnSearch = new JButton("검색");

        panN.add(combo);
        panN.add(new JLabel("검색어:"));
        panN.add(textSearch);
        panN.add(btnSearch);
        add(panN, BorderLayout.NORTH);
    }

    public void initView(){
        if(diaryVOList == null) diaryVOList = new ArrayList<>();

        model = new DefaultTableModel(header, 0){
            public boolean isCellEditable(int r, int c){ return false; }
        };
        table = new JTable(model);
        JScrollPane scrollTable = new JScrollPane(table);

        taContent = new JTextArea();
        taContent.setEditable(false);
        taContent.setLineWrap(true);
        JScrollPane scrollContent = new JScrollPane(taContent);
        scrollContent.setBorder(BorderFactory.createTitledBorder("일기 내용 미리보기"));
        scrollContent.setPreferredSize(new Dimension(100, 150));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTable, scrollContent);
        split.setDividerLocation(300);

        putSearchResult();
        add(split, BorderLayout.CENTER);
    }

    public void putSearchResult() {
        if(model == null) return;
        model.setRowCount(0);
        for(DiaryVO vo : diaryVOList){
            model.addRow(new Object[]{vo.getDiaryNo(), vo.getTitle(), vo.getDay(), vo.getWeather(), vo.getEmotion()});
        }
    }

    public void setDetailText(String text) { taContent.setText(text); }

    public void clearDetail() { taContent.setText(""); }

    public JTable getTable() { return table; }
    public String getSearchWord() { return textSearch.getText(); }
    public JButton getBtnSearch() { return btnSearch; }
    public JComboBox<String> getCombo() { return combo; }
    public void setDiaryVOList(ArrayList<DiaryVO> list) { this.diaryVOList = list; }
}