package diaryManagement.view;

import diaryManagement.domain.DiaryVO;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class DiaryInsertView extends JPanel {
    JTextField tfTitle;
    JComboBox<String> comboYear, comboMonth, comboDay;
    JComboBox<String> comboWeather, comboEmotion, comboCategory;
    JTextArea taContent;
    JButton btnAdd;

    String[] catNames = {
            "추억", "자랑", "사랑 이야기", "사람 사는 이야기", "미스터리",
            "리뷰 (영화)", "리뷰 (식당)", "여행", "웃음 꽃핀 날", "우울했던 날"
    };
    String[] emotionCodes = {"N", "H", "A", "S", "F"};
    String[] weatherNames = {"sun", "rain", "cold", "snow", "wind", "hot"};

    public DiaryInsertView(){
        setLayout(new BorderLayout());
    }

    public void initView() {
        removeAll();

        JPanel panCenter = new JPanel(new GridLayout(6, 1));

        JPanel pCategory = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pCategory.add(new JLabel("주제 선택: "));
        comboCategory = new JComboBox<>(catNames);
        pCategory.add(comboCategory);
        panCenter.add(pCategory);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("제목: "));
        tfTitle = new JTextField(20);
        p1.add(tfTitle);
        panCenter.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("날짜: "));

        Vector<String> years = new Vector<>();
        for(int i=2020; i<=2030; i++) years.add(String.valueOf(i));
        comboYear = new JComboBox<>(years);
        comboYear.setSelectedItem("2025"); // 기본값

        Vector<String> months = new Vector<>();
        for(int i=1; i<=12; i++) months.add(String.format("%02d", i));
        comboMonth = new JComboBox<>(months);

        Vector<String> days = new Vector<>();
        for(int i=1; i<=31; i++) days.add(String.format("%02d", i));
        comboDay = new JComboBox<>(days);

        p2.add(comboYear); p2.add(new JLabel("년 "));
        p2.add(comboMonth); p2.add(new JLabel("월 "));
        p2.add(comboDay); p2.add(new JLabel("일"));

        panCenter.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("날씨: "));
        comboWeather = new JComboBox<>(weatherNames);
        p3.add(comboWeather);

        p3.add(new JLabel(" 감정(Code): "));
        comboEmotion = new JComboBox<>(emotionCodes);
        p3.add(comboEmotion);
        panCenter.add(p3);

        JPanel pLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pLabel.add(new JLabel("내용:"));
        panCenter.add(pLabel);

        taContent = new JTextArea(10, 30);
        JScrollPane scroll = new JScrollPane(taContent);

        JPanel panSouth = new JPanel();
        btnAdd = new JButton("일기 저장");
        panSouth.add(btnAdd);

        add(panCenter, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panSouth, BorderLayout.SOUTH);

        revalidate();
    }

    public DiaryVO neededInsertData() {
        DiaryVO vo = new DiaryVO();
        vo.setTitle(tfTitle.getText());

        String dateStr = comboYear.getSelectedItem() + "-" +
                comboMonth.getSelectedItem() + "-" +
                comboDay.getSelectedItem();
        vo.setDay(dateStr);

        vo.setWeather((String)comboWeather.getSelectedItem());
        vo.setEmotion((String)comboEmotion.getSelectedItem());
        vo.setContent(taContent.getText());
        vo.setCategoryNo(comboCategory.getSelectedIndex() + 1);

        return vo;
    }

    public void initInsertData() {
        tfTitle.setText("");
        taContent.setText("");
        comboCategory.setSelectedIndex(0);
        comboEmotion.setSelectedIndex(0);
    }

    public JButton getBtnAdd() { return btnAdd; }
}